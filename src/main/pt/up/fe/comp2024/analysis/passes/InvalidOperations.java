package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;
import pt.up.fe.specs.util.SpecsCheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvalidOperations extends AnalysisVisitor {


    private String currentMethod;
    private String currentClass;
    private List<String> imported_class = new ArrayList();
    private String extended_class = "";


    @Override
    public void buildVisitor() {
        addVisit(Kind.IMPORT_DECL, this::visitImportedDecl);
        addVisit(Kind.CLASS_DECL, this::visitClassDecl);
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.BOOLEAN_EXPR, this::visitBooleanExpression);
        addVisit(Kind.BINARY_EXPR, this::visitBinaryExpression);
        addVisit(Kind.NOT, this::visitNotExpression);
        addVisit(Kind.ARRAY_SUB, this::visitArraySub);
        addVisit(Kind.LENGTH, this::visitLength);
    }

    private Void visitMethodDecl(JmmNode method, SymbolTable table) {



        currentMethod = method.get("name");

        return null;
    }

    private Void visitLength(JmmNode expression, SymbolTable table) {


        while(expression.getChildren().get(0).getKind().equals("ParenthExpr")){
            expression = expression.getChildren().get(0);
        }

        Symbol before = getSymbol(expression.getChildren().get(0),table,false );

        if (before!=null && !before.getType().isArray()){
            var message = String.format("Length must be applied to an array");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );

        }



        return null;
    }

    private Void visitImportedDecl(JmmNode method, SymbolTable table) {


        String value = method.get("value").substring(1, method.get("value").length() - 1);

        String[] wordsArray = value.split("[\\s,]+");


        List<String> wordsList = Arrays.asList(wordsArray);


        imported_class.add(wordsList.get(wordsList.size()-1));


        return null;

    }

    private Void visitClassDecl(JmmNode method, SymbolTable table) {

        currentClass = method.get("className");
        if (method.hasAttribute("superclassName")) extended_class = method.get("superclassName");


        return null;
    }

    private Void visitNotExpression(JmmNode expression, SymbolTable table) {

       while(expression.getChildren().get(0).getKind().equals("ParenthExpr")){
           expression = expression.getChildren().get(0);
       }

        if (!getSymbol(expression.getChildren().get(0),table,true).getType().getName().equals("boolean")){

            var message = String.format("Negations must be used with boolean values");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );

        }
        return null;

    }

    private Void visitBinaryExpression(JmmNode expression, SymbolTable table) {

        JmmNode expr1 = expression.getChildren().get(0);

        while (expr1.getKind().equals("ParenthExpr")){
            expr1 = expr1.getChildren().get(0);
        }

        JmmNode expr2 = expression.getChildren().get(1);

        while (expr2.getKind().equals("ParenthExpr")){
            expr2 = expr2.getChildren().get(0);
        }


        Symbol operand1 = getSymbol(expr1,table,false);
        Symbol operand2 = getSymbol(expr2,table,false);


        // Operands of an operation must have types compatible with the operation (both be integers)

        if (!(operand1.getType().getName().equals("int") && operand2.getType().getName().equals("int") && operand1.getType().isArray()==false &&  operand2.getType().isArray()==false)){

            var message = String.format("Invalid binary operation between non integers", operand1.getType().getName(),operand2.getType().getName());
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );
            return null;

        }

        return null;

    }

    private Void visitBooleanExpression(JmmNode expression, SymbolTable table) {


        JmmNode expr1 = expression.getChildren().get(0);

        while (expr1.getKind().equals("ParenthExpr")){
            expr1 = expr1.getChildren().get(0);
        }

        JmmNode expr2 = expression.getChildren().get(1);

        while (expr2.getKind().equals("ParenthExpr")){
            expr2 = expr2.getChildren().get(0);
        }

        Symbol operand1= getSymbol(expr1,table,true); ;
        Symbol operand2= getSymbol(expr1,table,true);;
        if (expression.get("op").equals("&&")){
            operand1 = getSymbol(expr1,table,true);
            operand2 = getSymbol(expr2,table,true);
        }
        else if (expression.get("op").equals("<")){
             operand1 = getSymbol(expr1,table,false);
          operand2 = getSymbol(expr2,table,false);
        }


        // Operands of an operation must have types compatible with the operation (both be integers)

        if (expression.get("op").equals("&&") && !(operand1.getType().getName().equals("boolean") && operand2.getType().getName().equals("boolean") && operand1.getType().isArray()==false &&  operand2.getType().isArray()==false)){

            var message = String.format("Invalid boolean operation between non boolean", operand1.getType().getName(),operand2.getType().getName());
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );
            return null;

        }

        else if (expression.get("op").equals("<") && !(operand1.getType().getName().equals("int") && operand2.getType().getName().equals("int") && operand1.getType().isArray()==false &&  operand2.getType().isArray()==false)){

            var message = String.format("Invalid < operation between non integer", operand1.getType().getName(),operand2.getType().getName());
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );
            return null;

        }


        return null;

    }

    private Void visitArraySub(JmmNode expression, SymbolTable table) {



        Symbol array = getSymbol(expression.getChildren().get(0), table,false);
        Symbol index = getSymbol(expression.getChildren().get(1), table,false);

        //Array access is done over an array

        System.out.println("array");
        System.out.println(array);

        if  (array.getType().isArray()==false){

            var message = String.format("Array subscription on a non array");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );
            return null;

        }

        // Array access index is an expression of type integer

        if ( !(index.getType().getName().equals("int") && index.getType().isArray()==false)){
            var message = String.format("Subscription on an array must be done with an int");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );
            return null;
        }

        return null;



    }

    private Symbol getSymbol(JmmNode expr, SymbolTable table, boolean bool){



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


            if (! (var.size()==0 || var.get(0)==null)) return var.get(0);
            else if (imported_class.contains(expr.get("name"))) return new Symbol( new Type(expr.get("name"),false),expr.get("name"));
            else return null;

        }

        if (expr.getKind().equals("ArrayDecl") || expr.getKind().equals("NewArrayDecl")){

            String arrayType = getSymbol(expr.getChildren().get(0),table,false).getType().getName();
            for (JmmNode elem : expr.getChildren()){
                if (!getSymbol(elem,table,false).getType().getName().equals(arrayType)) return null;

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


            if (var.size()==0 || var.get(0)==null ) return null;
            return new Symbol(new Type(var.get(0).getName(),false),expr.get("name"));

        }

        else if (expr.getKind().equals("MethodCall")){

            var classCalled = getSymbol(expr.getChildren().get(0), table,bool);

            if (classCalled == null && imported_class.contains( expr.getChildren().get(0).get("name") )){

                return new Symbol(new Type(bool?TypeUtils.getBooleanTypeName():TypeUtils.getIntTypeName(),false),expr.get("name"));

            }

            if (classCalled == null && extended_class.equals( expr.getChildren().get(0).get("name") )){

                return new Symbol(new Type(bool?TypeUtils.getBooleanTypeName():TypeUtils.getIntTypeName(),false),expr.get("name"));

            }


            if (classCalled == null){
                return null;
            }

            if (extended_class.contains(classCalled.getType().getName())){
                return new Symbol(new Type(bool?TypeUtils.getBooleanTypeName():TypeUtils.getIntTypeName(),false),expr.get("name"));
            }

            if (imported_class.contains(classCalled.getType().getName())){
                return new Symbol(new Type(bool?TypeUtils.getBooleanTypeName():TypeUtils.getIntTypeName(),false),expr.get("name"));

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
        else if (expr.getKind().equals("ParenthExpr")) return new Symbol(getSymbol(expr.getChildren().get(0),table,bool).getType(), "this" ) ;
        else if (expr.getKind().equals("ArraySub")) return new Symbol(new Type("int",false), "") ;
        else if (expr.getKind().equals("Not"))  return new Symbol(new Type(TypeUtils.getBooleanTypeName(), false),  expr.hasAttribute("value")?expr.get("value"):"") ;
        else if (expr.getKind().equals("Length"))  return new Symbol(new Type(TypeUtils.getIntTypeName(), false),  expr.hasAttribute("value")?expr.get("value"):"") ;
        else if (expr.getKind().equals("NewArrayDecl"))  return new Symbol(new Type(TypeUtils.getIntTypeName(), true),  expr.hasAttribute("name")?expr.get("name"):"") ;
        else if (expr.getKind().equals("ArrayDecl"))  return new Symbol(new Type(TypeUtils.getIntTypeName(), true),  expr.hasAttribute("name")?expr.get("name"):"") ;
        else if (expr.getKind().equals("ObjectDecl")) return new Symbol(new Type(expr.get("name"), false), "" ) ;


        return null;
    }

}
