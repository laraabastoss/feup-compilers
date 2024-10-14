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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvalidConditions extends AnalysisVisitor {

    private String currentMethod;
    private String currentClass;
    private String extended_class = "";
    private List<String> imported_class = new ArrayList<>();

    @Override
    public void buildVisitor() {
        addVisit(Kind.IMPORT_DECL, this::visitImportedDecl);
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.CLASS_DECL, this::visitClassDecl);
        addVisit(Kind.WHILE_STMT, this::visitWhileCall);
        addVisit(Kind.IF_STMT, this::visitIfCall);
    }

    private Void visitMethodDecl(JmmNode method, SymbolTable table) {
        currentMethod = method.get("name");
        return null;
    }


    private Void visitClassDecl(JmmNode method, SymbolTable table) {
        currentClass = method.get("className");
        if (method.hasAttribute("superclassName")) extended_class = method.get("superclassName");
        return null;
    }

    private Void visitImportedDecl(JmmNode method, SymbolTable table) {


        String value = method.get("value").substring(1, method.get("value").length() - 1);

        String[] wordsArray = value.split("[\\s,]+");


        List<String> wordsList = Arrays.asList(wordsArray);


        imported_class.add(wordsList.get(wordsList.size()-1));


        return null;

    }



    private Void visitIfCall(JmmNode statement, SymbolTable table) {

        JmmNode expr1 = statement.getChildren().get(0);

        while (expr1.getKind().equals("ParenthExpr")){
            expr1 = expr1.getChildren().get(0);
        }

        Symbol symbol = getSymbol(expr1,table);


        if (symbol!=null && symbol.getType().getName().equals("boolean") ) {

            return null;
        }
        var message = String.format("If must use boolean as condition",symbol.getType());
        addReport(Report.newError(
                Stage.SEMANTIC,
                NodeUtils.getLine(statement),
                NodeUtils.getColumn(statement),
                message,
                null)
        );

        return null;

    }

    private Void visitWhileCall(JmmNode statement, SymbolTable table) {

        JmmNode expr1 = statement.getChildren().get(0);

        while (expr1.getKind().equals("ParenthExpr")){
            expr1 = expr1.getChildren().get(0);
        }

        Symbol symbol = getSymbol(expr1,table);



        if (symbol!=null && symbol.getType().getName().equals("boolean") ) {

            return null;
        }


        var message = String.format("While loop must use boolean as condition",symbol.getType());
        addReport(Report.newError(
                Stage.SEMANTIC,
                NodeUtils.getLine(statement),
                NodeUtils.getColumn(statement),
                message,
                null)
        );

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


            if (!(var.size()==0 || var.get(0)==null) ) return var.get(0);
            else return null;

        }

        Type type = new Type(TypeUtils.getIntTypeName(), true);
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


            if (var.size()==0 || var.get(0)==null ) return null;
            return new Symbol(new Type(var.get(0).getName(),false),expr.get("name"));

        }
        else if (expr.getKind().equals("MethodCall")){

            var classCalled = getSymbol(expr.getChildren().get(0), table);

            if (classCalled == null && imported_class.contains( expr.getChildren().get(0).get("name") )){

                return new Symbol(new Type(TypeUtils.getBooleanTypeName(),false),expr.get("name"));

            }

            if (classCalled == null && extended_class.equals( expr.getChildren().get(0).get("name") )){

                return new Symbol(new Type(TypeUtils.getBooleanTypeName(),false),expr.get("name"));

            }


            if (classCalled == null){
                return null;
            }

            if (extended_class.contains(classCalled.getType().getName())){
                return new Symbol(new Type(TypeUtils.getBooleanTypeName(),false),expr.get("name"));
            }

            if (imported_class.contains(classCalled.getType().getName())){
                return new Symbol(new Type(TypeUtils.getBooleanTypeName(),false),expr.get("name"));

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



}
