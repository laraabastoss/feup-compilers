package pt.up.fe.comp2024.optimization;

import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.JmmNodeImpl;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConstantFoldingVisitor {

    private static boolean optimized;


    public static JmmSemanticsResult ConstantFoldingVisitor(JmmSemanticsResult semanticsResult){

        optimized = false;
        List<JmmNode> operations = new ArrayList<>();
        findOperations( semanticsResult.getRootNode(), operations);

        for (JmmNode node : operations) {

            performConstantFolding(node);
        }

        printTree(semanticsResult.getRootNode(), 0);

        return semanticsResult;


    }

    private static void findOperations(JmmNode node, List<JmmNode> operations) {

        if (node.getKind().equals(Kind.BINARY_EXPR.toString())) {
            JmmNode operand1 = node.getChildren().get(0);
            JmmNode operand2 = node.getChildren().get(1);

            if (operand1.getKind().equals(Kind.INTEGER_LITERAL.toString()) && operand2.getKind().equals(Kind.INTEGER_LITERAL.toString())) {
                operations.add(node);
            }
        }

        if (node.getKind().equals(Kind.PARENTH_EXPR.toString())) {
            if (node.getChild(0).getKind().equals(Kind.INTEGER_LITERAL.toString())) {
                int remove = node.getIndexOfSelf();
                JmmNode parentNode = node.getParent();
                parentNode.removeJmmChild(remove);
                parentNode.add(node.getChild(0), remove);
            }
        }

        // for booleans
        /*if (node.getKind().equals(Kind.BOOLEAN_EXPR.toString())) {
            JmmNode operand1 = node.getChildren().get(0);
            JmmNode operand2 = node.getChildren().get(1);

            if (operand1.getKind().equals(Kind.BOOLEAN_LITERAL.toString()) && operand2.getKind().equals(Kind.BOOLEAN_LITERAL.toString())) {
                operations.add(node);
            }
        }*/

        for (JmmNode child : node.getChildren()) {
            findOperations(child, operations );
        }
    }

    public static void performConstantFolding(JmmNode node) {

        JmmNode operand1 = node.getChildren().get(0);

        JmmNode operand2 = node.getChildren().get(1);


        if ( node.getKind().equals(Kind.BINARY_EXPR.toString()) && operand1.getKind().equals(Kind.INTEGER_LITERAL.toString()) && operand2.getKind().equals(Kind.INTEGER_LITERAL.toString())){

            JmmNode newNode = new JmmNodeImpl(Kind.INTEGER_LITERAL.toString());
            int remove = node.getIndexOfSelf();

            if (node.get("op").equals("+")){
                newNode.put("value", String.valueOf(Integer.valueOf(operand1.get("value")) + Integer.valueOf(operand2.get("value"))));
            }
            else if (node.get("op").equals("-")){
                newNode.put("value", String.valueOf(Integer.valueOf(operand1.get("value")) - Integer.valueOf(operand2.get("value"))));
            }
            else if (node.get("op").equals("*")){
                newNode.put("value", String.valueOf(Integer.valueOf(operand1.get("value")) * Integer.valueOf(operand2.get("value"))));
            }
            else if (node.get("op").equals("/")){
                newNode.put("value", String.valueOf(Integer.valueOf(operand1.get("value")) / Integer.valueOf(operand2.get("value"))));
            }

            JmmNode parentNode = node.getParent();

            parentNode.removeJmmChild(remove);
            parentNode.add(newNode, remove);

            optimized = true;

        }
        //for booleans
           /* if ( node.getKind().equals(Kind.BOOLEAN_EXPR.toString()) && operand1.getKind().equals(Kind.BOOLEAN_LITERAL.toString()) && operand2.getKind().equals(Kind.BOOLEAN_LITERAL.toString())){

                JmmNode newNode = new JmmNodeImpl(Kind.BOOLEAN_LITERAL.toString());

                if (node.get("op").equals("&&")){
                    newNode.put("value", String.valueOf(Boolean.valueOf(operand1.get("value")) && Boolean.valueOf(operand2.get("value"))));
                }

                node.removeChild(1);
                node.removeChild(0);
                node.replace(newNode);
            }

            if ( node.getKind().equals(Kind.BOOLEAN_EXPR.toString()) && operand1.getKind().equals(Kind.INTEGER_LITERAL.toString()) && operand2.getKind().equals(Kind.INTEGER_LITERAL.toString())){

                JmmNode newNode = new JmmNodeImpl(Kind.BOOLEAN_LITERAL.toString());

                if (node.get("op").equals("<")){
                    newNode.put("value", String.valueOf(Integer.valueOf(operand1.get("value")) < Integer.valueOf(operand2.get("value"))));
                }

                node.removeChild(1);
                node.removeChild(0);
                node.replace(newNode);
            }*/



    }

    public static boolean wasOptimized() {
        return optimized;
    }
    public static void setOptimized() {
        optimized = false;
    }


    private static void printTree(JmmNode node, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
        System.out.println(node);

        for (JmmNode child : node.getChildren()) {
            printTree(child, indent + 1);
        }
    }




}
