package pt.up.fe.comp2024.optimization;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.Kind;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class ConstantPropagationVisitor extends AJmmVisitor<SymbolTable , Void> {

    private static boolean optimized = false;
    private static boolean on = false;
    Map<String, JmmNode> constantMap;
    ArrayList<String> constantAssign;


    @Override
    public void buildVisitor() {
        optimized = false;
        this.constantMap = new HashMap<>();
        this.constantAssign = new ArrayList<String>();
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.ASSIGN_STMT, this::visitAssignment);
        addVisit(Kind.VAR_REF_EXPR, this::visitVarRefExpr);
        addVisit(Kind.IF_STMT, this::visitIfStatement);
        addVisit(Kind.WHILE_STMT, this::visitWhileStmt);


        setDefaultVisit(this::start);
    }
    private Void start(JmmNode node, SymbolTable table) {
        for(JmmNode child : node.getChildren()){
            visit(child, table);
        }
        return null;
    }

    private Void visitMethodDecl(JmmNode node, SymbolTable table) {

        this.constantMap = new HashMap<>();

        for(JmmNode child : node.getChildren()) {
            visit(child);
        }
        return null;
    }

    private Void visitVarRefExpr(JmmNode node, SymbolTable table) {

        int remove = node.getIndexOfSelf();
        if ( !(node.getParent().getKind().equals(Kind.ASSIGN_STMT.toString()) && node.getParent().getChildren().get(0).equals(node)) && constantMap.containsKey(node.get("name"))) {

            JmmNode newNode = this.constantMap.get(node.get("name"));
            JmmNode parent = node.getParent();
            parent.removeChild(remove);
            parent.add(newNode, remove);

            optimized = true;

        }

        else if (node.getParent().getKind().equals(Kind.ASSIGN_STMT.toString()) && node.getParent().getChildren().get(0).equals(node) && node.getParent().getChildren().get(1).equals(node) && constantMap.containsKey(node.get("name"))){
            JmmNode newNode = constantMap.get(node.get("name"));
            JmmNode parent = node.getParent();
            parent.removeChild(remove);
            parent.add(newNode, remove);

            optimized = true;
        }

        return null;
    }

    private Void visitAssignment(JmmNode node, SymbolTable table){

        JmmNode varName = node.getChildren().get(0);
        if (this.on) constantAssign.add(varName.get("name"));

        if (isInteger(node)) {

            JmmNode constantValue = node.getChildren().get(1);

            constantMap.put(varName.get("name"), constantValue);

        } else if (isBoolean(node)) {

            JmmNode constantValue = node.getChildren().get(1);
            constantMap.put(varName.get("name"), constantValue);


        }
        else if (node.getChildren().get(1).hasAttribute("name") && node.getChildren().get(0).get("name").equals(node.getChildren().get(0).get("name")) && node.getChildren().get(1).get("name").equals(node.getChildren().get(0).get("name"))){

        }
        else if (!isInteger(node) && !isBoolean(node) && !isBooleanExpression(node) && !isIntegerExpression(node) && constantMap.containsKey(varName.get("name")   )) {

            constantMap.remove(varName.get("name"));

        }

        visit(node.getChildren().get(0));
        visit(node.getChildren().get(1));
        return null;

    }

    private Void visitIfStatement(JmmNode node, SymbolTable table) {


        JmmNode condition = node.getChildren().get(0);
        visit(condition, table);
        this.on = true;
        JmmNode thenBlock = node.getChildren().get(1);
        Map<String, JmmNode> originalConstantMap = new HashMap<>(this.constantMap);
        JmmNode elseBlock = node.getChildren().get(2);
        visit(thenBlock, table);

        Map<String, JmmNode> thenConstantMap = new HashMap<>(this.constantMap);

        this.constantMap = new HashMap<>(originalConstantMap);
        visit(elseBlock, table);
        Map<String, JmmNode> elseConstantMap = new HashMap<>(constantMap);

        this.constantMap = new HashMap<>(originalConstantMap);
        for (String key : originalConstantMap.keySet()) {
            boolean inThenBranch = thenConstantMap.containsKey(key);

            boolean inElseBranch = elseConstantMap.containsKey(key);

            boolean thenChanged = inThenBranch && !thenConstantMap.get(key).get("value").equals(originalConstantMap.get(key).get("value"));
            boolean elseChanged = inElseBranch && !elseConstantMap.get(key).get("value").equals(originalConstantMap.get(key).get("value"));

            if (thenChanged || elseChanged || this.constantAssign.contains(key)) {
                this.constantMap.remove(key);
            }
        }

        this.on = false;
        return null;
    }

    private Void visitWhileStmt(JmmNode node, SymbolTable table) {
        JmmNode condition = node.getJmmChild(0);

        JmmNode statements = node.getJmmChild(1);

        for(JmmNode child: statements.getChildren()){
            if(child.getKind().equals(Kind.ASSIGN_STMT.toString())){
                this.constantMap.remove(child.getChildren().get(0).get("name"));
            }
        }

        //TODO: ver o caso em que fica igual
        visit(condition, table);
        this.on = true;
        visit(statements,table);
        this.on = false;


        return null;
    }
    public static boolean isInteger(JmmNode node) {
        if( node.getChildren().get(1).getKind().equals(Kind.INTEGER_LITERAL.toString())) return true;
        return false;
    }
    public static boolean isBoolean(JmmNode node) {
        if( node.getChildren().get(1).getKind().equals(Kind.BOOLEAN_LITERAL.toString())) return true;
        return false;
    }
    public static boolean isIntegerExpression(JmmNode node) {
        if( node.getChildren().get(1).getKind().equals(Kind.BINARY_EXPR.toString())) return true;
        return false;
    }
    public static boolean isBooleanExpression(JmmNode node) {
        if( node.getChildren().get(1).getKind().equals(Kind.BOOLEAN_EXPR.toString())) return true;
        return false;
    }

    public static boolean wasOptimized() {
        return optimized;
    }


}


