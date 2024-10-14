package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;

import pt.up.fe.comp.jmm.analysis.table.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvalidAssignments extends AnalysisVisitor {

    List<JmmNode> operands;
    private String currentMethod="";
    private Type beforeequal;

    private String currentClass="";

    private boolean has_import = false;
    private List<String> imported_class = new ArrayList();
    private String extended_class = "";




    @Override
    public void buildVisitor() {
        addVisit(Kind.IMPORT_DECL, this::visitImporDecl);
        addVisit(Kind.CLASS_DECL, this::visitClassDecl);
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.ASSIGN_STMT, this::visitAssignment);
    }

    private Void visitMethodDecl(JmmNode method, SymbolTable table) {

        if (method.get("isMainMethod").equals("false") && method.getChildren().get(0).get("isVarArgs").equals("true")){

            var message = String.format("Method declaration cannot be varargs");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(method),
                    NodeUtils.getColumn(method),
                    message,
                    null));
        }
        currentMethod = method.get("name");
        return null;
    }
    private Void visitClassDecl(JmmNode classVar, SymbolTable table) {

        if (classVar.hasAttribute("superclassName"))
            extended_class=classVar.get("superclassName");

        currentClass = classVar.get("className");


        return null;
    }

    private Void visitImporDecl(JmmNode method, SymbolTable table) {

        String value = method.get("value").substring(1, method.get("value").length() - 1);

        String[] wordsArray = value.split("[\\s,]+");

        List<String> temp = Arrays.asList(wordsArray);

        for (String a : temp){
            imported_class.add(a);
        }

        has_import = true;
        return null;

    }


    private Void visitAssignment(JmmNode statement, SymbolTable table) {


        JmmNode expr1 = statement.getChildren().get(0);

        while (expr1.getKind().equals("ParenthExpr")){
            expr1 = expr1.getChildren().get(0);
        }

        JmmNode expr2 = statement.getChildren().get(1);

        while (expr2.getKind().equals("ParenthExpr")){

            expr2 = expr2.getChildren().get(0);
        }

        operands = statement.getChildren();


        if (!expr1.getKind().equals("VarRefExpr") &&  !(expr1.getKind().equals("ArraySub") && expr1.getChildren().get(0).getKind().equals("VarRefExpr")) ){

            var message = String.format("Non id on left of assignment");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(statement),
                    NodeUtils.getColumn(statement),
                    message,
                    null));

            return null;
        }

        if (expr1.hasAttribute("name") && (currentClass.equals(expr1.get("name")) || extended_class.equals(expr1.get("name")) || imported_class.contains(expr1.get("name") ))){

            var message = String.format("Error");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(statement),
                    NodeUtils.getColumn(statement),
                    message,
                    null));

            return null;
        }


        Symbol operand1 = getSymbol(expr1,table);

        if (operand1==null &&   statement.getChildren().get(1).getKind().equals("ObjectDecl")){
           return null;

        }

        if (operand1==null){
            var message = String.format("Class not imported");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(statement),
                    NodeUtils.getColumn(statement),
                    message,
                    null));

            return null;
        }

        beforeequal = operand1.getType();


        Symbol operand2 = getSymbol(expr2,table);




        if (operand2==null){



            var message = String.format("Invalid Array");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(statement),
                    NodeUtils.getColumn(statement),
                    message,
                    null));

            return null;
        }


        if (operand1.getType().getName().equals(operand2.getType().getName()) && operand1.getType().isArray()==operand2.getType().isArray()){

            return null;
        }

        else if (operands.get(1).getKind().equals("VarRefExpr") && validAssignmentClass(operand1, operand2 )){
            return null;

        }

        else if (imported_class.contains(operand1.getType().getName()) && imported_class.contains(operand2.getType().getName())){
            return null;

        }


        var message = String.format("Invalid Assignment");
        addReport(Report.newError(
                Stage.SEMANTIC,
                NodeUtils.getLine(statement),
                NodeUtils.getColumn(statement),
                message,
                null));

        return null;


    }

    private Symbol getSymbol(JmmNode expr, SymbolTable table){

        if (expr.getKind().equals("VarRefExpr")){

            var var = Stream.of(
                            table.getLocalVariables(currentMethod).stream()
                                    .filter(param -> param.getName().equals(expr.get("name"))),
                            table.getParameters(currentMethod).stream()
                                    .filter(param -> param.getName().equals(expr.get("name"))),
                            table.getFields().stream()
                                    .filter(param -> param.getName().equals(expr.get("name")))
                    )
                    .flatMap(Function.identity())
                    .collect(Collectors.toList());


            if (!  (var.size()==0 || var.get(0)==null )) return var.get(0);
            else return null;

        }

        if (expr.getKind().equals("ArrayDecl") || expr.getKind().equals("NewArrayDecl")){

            String arrayType = getSymbol(expr.getChildren().get(0),table).getType().getName();
            for (JmmNode elem : expr.getChildren()){
                if (!getSymbol(elem,table).getType().getName().equals(arrayType)) return null;

            }
            return new Symbol (new Type(arrayType, true),"");
        }
        if (expr.getKind().equals("ObjectDecl")){
            return new Symbol (new Type(expr.get("name"), false),"");
        }
        else if (expr.getKind().equals("FunctionCall")){

            var var = Stream.of(
                            table.getReturnType(expr.get("name"))
                    )
                    .collect(Collectors.toList());



            if (var.size()==0 || var.get(0)==null) return null;
            return new Symbol(new Type(var.get(0).getName(),false),expr.get("name"));

        }
        else if (expr.getKind().equals("MethodCall")){

            var classCalled = getSymbol(expr.getChildren().get(0), table);

            if (classCalled == null && imported_class.contains( expr.getChildren().get(0).get("name") )){

                return new Symbol(beforeequal,expr.get("name"));

            }

            if (classCalled == null && extended_class.equals( expr.getChildren().get(0).get("name") )){

                return new Symbol(beforeequal,expr.get("name"));

            }

            if (classCalled.getType().getName().equals(currentClass) && !extended_class.equals("")){
                return new Symbol(beforeequal,expr.get("name"));
            }


                if (classCalled == null){
                    return null;
                }

            if (extended_class.contains(classCalled.getType().getName())){
                return new Symbol(beforeequal,expr.get("name"));
            }

            if (imported_class.contains(classCalled.getType().getName())){
                return new Symbol(beforeequal,expr.get("name"));

            }
            var var = Stream.of(
                            table.getReturnType(expr.get("name"))
                    )
                    .collect(Collectors.toList());


            if (var.size()==0 || var.get(0)==null ) return null;

            return new Symbol(new Type(var.get(0).getName(),var.get(0).isArray()),expr.get("name"));

        }

        if (expr.getKind().equals("BooleanLiteral")) return new Symbol(new Type(TypeUtils.getBooleanTypeName(), false), "" ) ;
        else if (expr.getKind().equals("BinaryExpr")) return new Symbol(new Type(TypeUtils.getIntTypeName(), false), "") ;
        else if (expr.getKind().equals("BooleanExpr")) return new Symbol(new Type(TypeUtils.getBooleanTypeName(), false), "") ;
        else if (expr.getKind().equals("IntegerLiteral")) return new Symbol(new Type(TypeUtils.getIntTypeName(), false), expr.hasAttribute("value")?expr.get("value"):"") ;
        else if (expr.getKind().equals("String")) return new Symbol( new Type(TypeUtils.getStringTypeName(), false), expr.hasAttribute("value")?expr.get("value"):"");
        else if (expr.getKind().equals("This")) return new Symbol(new Type(currentClass, false), "this" ) ;
        else if (expr.getKind().equals("ParenthExpr")) return new Symbol(getSymbol(expr.getChildren().get(0),table).getType(), "this" ) ;
        else if (expr.getKind().equals("ArraySub")) return new Symbol(new Type("int",false), "") ;
        else if (expr.getKind().equals("Not"))  return new Symbol(new Type(TypeUtils.getBooleanTypeName(), false),  expr.hasAttribute("value")?expr.get("value"):"") ;
        else if (expr.getKind().equals("Length"))  return new Symbol(new Type(TypeUtils.getIntTypeName(), false),  expr.hasAttribute("value")?expr.get("value"):"") ;
        else if (expr.getKind().equals("NewArrayDecl"))  return new Symbol(new Type(TypeUtils.getIntTypeName(), true),  expr.hasAttribute("name")?expr.get("name"):"") ;
        else if (expr.getKind().equals("ArrayDecl"))  return new Symbol(new Type(TypeUtils.getIntTypeName(), true),  expr.hasAttribute("name")?expr.get("name"):"") ;
        else if (expr.getKind().equals("ObjectDecl")) return new Symbol(new Type(expr.get("name"), false), "" ) ;



        return null;
    }

    private boolean validAssignmentClass(Symbol expr1, Symbol expr2){


        if (expr1.getType().getName()== expr2.getType().getName()){

            return true;
        }

        else if (imported_class.contains("[" + expr1.getType().getName() + "]") && imported_class.contains("[" + expr2.getType().getName() + "]")){


            return true;
        }

        else if (extended_class.contains(expr1.getType().getName())){

            return true;
        }

        return false;
    }
}
