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
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InvalidMethodCalls extends AnalysisVisitor {
    boolean doesExtend = false;

    boolean imports = false;
   HashMap<String, String> varArgs = new HashMap<>();

    private JmmNode currentMethod;
    private List<String> imported_class = new ArrayList();
    private List<String> extended_class = new ArrayList();

    private String currentClass;


    @Override
    public void buildVisitor() {
        addVisit(Kind.IMPORT_DECL, this::visitImportDecl);
        addVisit(Kind.CLASS_DECL, this::visitClassDecl);
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.METHOD_CALL, this::visitMethodCall);
        addVisit(Kind.FUNCTION_CALL, this::visitFunctionCall);

    }

    private Void visitImportDecl(JmmNode expression, SymbolTable table) {

        String value = expression.get("value").substring(1, expression.get("value").length() - 1);

        String[] wordsArray = value.split("[\\s,]+");


        List<String> wordsList = Arrays.asList(wordsArray);


        imported_class.add(wordsList.get(wordsList.size()-1));


        return null;

    }




    private Void visitClassDecl(JmmNode expression, SymbolTable table) {


        if (expression.hasAttribute("superclassName")){
            extended_class.add(expression.get("superclassName"));
            doesExtend = true;

        }

        for (JmmNode method : expression.getChildren()){
            if (method.getKind().equals("MethodDecl")){
                boolean found = false;
                for (JmmNode param : expression.getChildren()){
                    if (param.getKind().equals("Param")){
                        if (param.get("isVarArgs").equals("true")){
                            varArgs.put(method.get("name"),"true");
                            found = true;
                        }

                    }
                    if (!found)  varArgs.put(method.get("name"),"false");
                }

                //params.put(method.get("name"),)
            }
        }



        currentClass = expression.get("className");

        return null;

    }

    private Void visitMethodDecl(JmmNode method, SymbolTable table) {



        currentMethod = method;

       /* hasVarArgs.put(method.get("name"),"false") ;

        for (var elem : method.getChildren()){

            if (elem.getKind().equals("Param") && elem.getChildren().get(0).get("isVarArgs").equals("true") ){
                hasVarArgs.remove(method.get("name"));
                hasVarArgs.put(method.get("name"),"true") ;

            }

        }*/

        Type methodReturnType = getSymbol(method,table).getType();



       if (method.getChildren().size()>0 ){

           Symbol u = getSymbol(method.getChildren().get(method.getChildren().size() - 1),table);


           if (u!=null && !u.getType().equals(methodReturnType)){

               var message = String.format("Incorrect return type on function");
               addReport(Report.newError(
                       Stage.SEMANTIC,
                       NodeUtils.getLine(method),
                       NodeUtils.getColumn(method),
                       message,
                       null)
               );
           }
       }




        return null;
    }

    private Void visitMethodCall(JmmNode expression, SymbolTable table) {

        Symbol method = getSymbol(expression,table);



        if (expression.getChildren().get(0).getKind().equals("This") && currentMethod.get("isMainMethod").equals("true")) {
            var message = String.format("This should not be used in main method");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );
            return null;
        }


        if (method!=null){


                List<Symbol> params = table.getParameters(method.getName());

                boolean hasVarArgs = false;
                if (params.size()>0 && params.get( params.size()-1).getType().isArray()) hasVarArgs = true;

                if (expression.getChildren().size() -1 < params.size()) {

                    var message = String.format("Incorrect params on function call");
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(expression),
                            NodeUtils.getColumn(expression),
                            message,
                            null)
                    );
                    return null;

                }

                boolean breakvarArgs = false;

                for (var i = 1; i< expression.getChildren().size();i++){


                    if (breakvarArgs == true){
                        var message = String.format("Incorrect params on function call");
                        addReport(Report.newError(
                                Stage.SEMANTIC,
                                NodeUtils.getLine(expression),
                                NodeUtils.getColumn(expression),
                                message,
                                null)
                        );
                        return null;
                    }

                    Symbol curr_symbol = getSymbol(expression.getChildren().get(i),table);


                    if (params.size()>=i && !params.get(params.size()-1).getType().isArray()==true && !(curr_symbol.getType().equals(params.get(i-1).getType()))){

                        var message = String.format("Incorrect params on function call");
                        addReport(Report.newError(
                                Stage.SEMANTIC,
                                NodeUtils.getLine(expression),
                                NodeUtils.getColumn(expression),
                                message,
                                null)
                        );
                        return null;
                    }


                  else  if (expression.getChildren().get(i).getKind().equals("MethodCall") && expression.getChildren().get(i).getChildren().get(0).hasAttribute("name") && imported_class.contains(expression.getChildren().get(i).getChildren().get(0).get("name"))){
                      continue;
                    }


                 else if (params.size()<=i  && params.size()>0 && hasVarArgs==true  &&!(curr_symbol.getType().getName().equals(params.get(params.size()-1).getType().getName()))){
                        var message = String.format("Incorrect params on function call");
                        addReport(Report.newError(
                                Stage.SEMANTIC,
                                NodeUtils.getLine(expression),
                                NodeUtils.getColumn(expression),
                                message,
                                null)
                        );
                    }

                    else if (params.size()<expression.getChildren().size() && hasVarArgs ==true && curr_symbol.getType().isArray()==true){
                        breakvarArgs = true;
                    }


                }

        }


    // imported_class.contains(getSymbol(expression.getChildren().get(0),table).getType().getName())

        if (imported_class.size()>0 && getSymbol(expression.getChildren().get(0),table)!=null && !imported_class.contains(getSymbol(expression.getChildren().get(0),table).getType().getName()) && !doesExtend && method==null){

            var message = String.format("Var '%s' is not defined.", expression.getChildren().get(0).get("name"));
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );

        }
        if (imported_class.size()==0 && !doesExtend && method==null){
            var message = String.format("Function '%s' is not defined.", expression.get("name"));
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );

        }



/*


        Symbol calledClass = getSymbol(expression.getChildren().get(0),table);

        if (calledClass == null){

            var message = String.format("Class '%s' is not defined.", expression.getChildren().get(0).get("name"));
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(expression),
                    NodeUtils.getColumn(expression),
                    message,
                    null)
            );

            return null;

        }

        if (doesExtend && table.getMethods().stream().anyMatch(field -> field.equals(expression.get("name")))){

            return null;

        }

        else if (doesExtend){

            return null;

        }

        JmmNode object = expression.getChildren().get(0);

        if (object.hasAttribute("name")){
            var object_type = Stream.of(
                            table.getLocalVariables(currentMethod).stream()
                                    .filter(param -> param.getName().equals(object.get("name"))),
                            table.getParameters(currentMethod).stream()
                                    .filter(param -> param.getName().equals(object.get("name"))),
                            table.getFields().stream()
                                    .filter(param -> param.getName().equals(object.get("name")))
                    )
                    .flatMap(Function.identity())
                    .collect(Collectors.toList());


            if (object_type.size()>0 && imported_class.contains("[" + object_type.get(0).getType().getName() + "]")){
                return null;
            }

            if (imported_class.contains("[" + calledClass.getName() + "]")){
                return null;
            }

            else{
                var message = String.format("Function '%s' is not defined.", expression.get("name"));
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(expression),
                        NodeUtils.getColumn(expression),
                        message,
                        null)
                );


            }
        }*/


        return null;

    }

    private Void visitFunctionCall(JmmNode expression, SymbolTable table) {

       if (!table.getMethods().contains(expression.get("name"))){
            var message = String.format("Function '%s' is not defined.", expression.get("name"));
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

    private Symbol getSymbol(JmmNode expr, SymbolTable table){

        if (expr.getKind().equals("VarRefExpr")){

            var var = Stream.of(
                            table.getLocalVariables(currentMethod.get("name")).stream()
                                    .filter(param -> param.getName().equals(expr.get("name"))),
                            table.getParameters(currentMethod.get("name")).stream()
                                    .filter(param -> param.getName().equals(expr.get("name"))),
                            table.getFields().stream()
                                    .filter(param -> param.getName().equals(expr.get("name")))

                    )
                    .flatMap(Function.identity())
                    .collect(Collectors.toList());


            if (! ( var.size()==0 || var.get(0)==null )) return var.get(0);
            else {

                if (table.getImports().stream().anyMatch(param -> param.equals("[" + expr.get("name") + "]"))){
                    return new Symbol(new Type(expr.get("name"), false), expr.get("name"));
                }

                return null;
            }

        }

        else if (expr.getKind().equals("MethodDecl")){



            for (var elem: expr.getChildren()){

                if (elem.getKind().equals("Type")){
                    boolean isArray = elem.get("isArray").equals("true")? true:false;
                    return new Symbol(new Type(elem.get("name"),isArray),expr.get("name"));
                }


            }

           return new Symbol(new Type("void",false),expr.get("name"));

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


            if (var.get(0)==null || var.size()==0) return null;

            return new Symbol(new Type(var.get(0).getName(),var.get(0).isArray()),expr.get("name"));

        }

        Type type = new Type(TypeUtils.getIntTypeName(), true);;

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
