package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;

import java.util.*;
import java.util.stream.Collectors;

public class InvalidDeclarations extends AnalysisVisitor {

    List<String> imports = new ArrayList<>();
    String currClass = "";
    String does_extend = "";

    @Override
    public void buildVisitor() {
        addVisit(Kind.IMPORT_DECL, this::visitImportDecl);
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.CLASS_DECL, this::visitClassDecl);
        addVisit(Kind.VAR_DECL, this::visitVarDecl);
        addVisit(Kind.OBJECT_DECL, this::visitObjectDecl);


    }

    private Void visitImportDecl(JmmNode method, SymbolTable table) {


        String value = method.get("value").substring(1, method.get("value").length() - 1);

        String[] wordsArray = value.split("[\\s,]+");


        List<String> wordsList = Arrays.asList(wordsArray);


        if (!imports.contains(wordsList.get(wordsList.size()-1))) imports.add(wordsList.get(wordsList.size()-1));

        else{
            var message = String.format("%s being imported over onceImporting repeated libraries",wordsList.get(wordsList.size()-1) );
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(method),
                    NodeUtils.getColumn(method),
                    message,
                    null)
            );

        }


        return null;

    }


    private Void visitMethodDecl(JmmNode method, SymbolTable table) {


        List<JmmNode> params = method.getChildren().stream().filter(varDecl -> varDecl.getKind().equals("Param")).collect(Collectors.toList());
        if (params!=null){

            for (int i = 0 ; i< params.size() -1 ; i++){

                if (params.get(i).getChildren().get(0).get("isVarArgs").equals("true")){

                    var message = String.format("VarArgs should always be last element of function declaration" );
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(method),
                            NodeUtils.getColumn(method),
                            message,
                            null)
                    );

                    break;

                }

            }
        }

        Map<String, Integer> localCounts = new HashMap<>();
        Map<String, Integer> paramCounts = new HashMap<>();


        for (var field : table.getLocalVariables(method.get("name"))) {

            localCounts.put(field.getName(), localCounts.getOrDefault(field.getName(), 0) + 1);

        }

        for (var field : table.getParameters(method.get("name"))) {

            paramCounts.put(field.getName(), paramCounts.getOrDefault(field.getName(), 0) + 1);

        }

        for (var entry : localCounts.entrySet()) {
            String field = entry.getKey();
            int count = entry.getValue();

            if (count > 1) {
                var message = String.format("Repeated local variable declaration" );
                addReport(Report.newError(
                        Stage.SEMANTIC,
                        NodeUtils.getLine(method),
                        NodeUtils.getColumn(method),
                        message,
                        null)
                );
            }
        }

        for (var entry : paramCounts.entrySet()) {
            String field = entry.getKey();
            int count = entry.getValue();

            if (count > 1) {
                var message = String.format("Repeated parameter declaration" );
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

    private Void visitVarDecl(JmmNode var, SymbolTable table) {


       if ( var.getChildren().get(0).get("isVarArgs").equals("true")){

           var message = String.format("Variable declaration cannot be varargs" );
           addReport(Report.newError(
                   Stage.SEMANTIC,
                   NodeUtils.getLine(var),
                   NodeUtils.getColumn(var),
                   message,
                   null)
           );

       }
        return null;
    }

    private Void visitObjectDecl(JmmNode var, SymbolTable table) {



        if ( !(var.get("name").equals(currClass) || imports.contains(var.get("name")) || does_extend.equals(var.get("name"))  )){

            var message = String.format("Object doesnt exist in scope" );
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(var),
                    NodeUtils.getColumn(var),
                    message,
                    null)
            );

        }
        return null;
    }

    private Void visitClassDecl(JmmNode currclass, SymbolTable table) {

        if (currclass.hasAttribute("superclassName")) does_extend = currclass.get("superclassName") ;

        currClass = currclass.get("className");

        Map<String, Integer> fieldCounts = new HashMap<>();

        if (table.getFields()!=null){

            for (var field : table.getFields()) {

                fieldCounts.put(field.getName(), fieldCounts.getOrDefault(field.getName(), 0) + 1);

            }

            for (var entry : fieldCounts.entrySet()) {
                String field = entry.getKey();
                int count = entry.getValue();

                if (count > 1) {
                    var message = String.format("Repeated field declaration" );
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(currclass),
                            NodeUtils.getColumn(currclass),
                            message,
                            null)
                    );
                }
            }
        }


        Map<String, Integer> methodCounts = new HashMap<>();

        if (table.getMethods()!=null){
            for (var field : table.getMethods()) {

                methodCounts.put(field, methodCounts.getOrDefault(field, 0) + 1);

            }

            for (var entry : methodCounts.entrySet()) {
                String field = entry.getKey();
                int count = entry.getValue();

                if (count > 1) {
                    var message = String.format("Repeated method declaration" );
                    addReport(Report.newError(
                            Stage.SEMANTIC,
                            NodeUtils.getLine(currclass),
                            NodeUtils.getColumn(currclass),
                            message,
                            null)
                    );
                }
            }
        }




        return null;
    }






}
