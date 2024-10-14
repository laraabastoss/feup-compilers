package pt.up.fe.comp2024.optimization;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.AJmmVisitor;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;

import java.util.ArrayList;
import java.util.List;

import static pt.up.fe.comp2024.ast.Kind.*;

/**
 * Generates OLLIR code from JmmNodes that are not expressions.
 */
public class OllirGeneratorVisitor extends AJmmVisitor<Void, String> {

    private static final String SPACE = " ";
    private static final String ASSIGN = ":=";
    private final String END_STMT = ";\n";
    private final String NL = "\n";
    private final String L_BRACKET = " {\n";
    private final String R_BRACKET = "}\n";


    private final SymbolTable table;

    private final OllirExprGeneratorVisitor exprVisitor;

    public OllirGeneratorVisitor(SymbolTable table) {
        this.table = table;
        exprVisitor = new OllirExprGeneratorVisitor(table);
    }


    @Override
    protected void buildVisitor() {

        addVisit(PROGRAM, this::visitProgram);
        addVisit(CLASS_DECL, this::visitClass);
        addVisit(METHOD_DECL, this::visitMethodDecl);
        addVisit(VAR_DECL, this::visitVarDecl);
        addVisit(PARAM, this::visitParam);
        addVisit(RETURN_STMT, this::visitReturn);
        addVisit(INTEGER_LITERAL, this::visitReturn);
        addVisit(BOOLEAN_LITERAL, this::visitReturn);
        addVisit(VAR_REF_EXPR, this::visitReturn);
        addVisit(BINARY_EXPR, this::visitReturn);
        addVisit(BOOLEAN_EXPR, this::visitReturn);
        addVisit(PARENTH_EXPR, this::visitReturn);
        addVisit(ARRAY_SUB, this::visitReturn);
        addVisit(IMPORT_DECL, this::visitImport);
        addVisit(ASSIGN_STMT, this::visitAssignStmt);
        addVisit(SIMPLE_STMT, this::visitSimpleStmt);
        addVisit(IF_STMT, this::visitIfStmt);
        addVisit(WHILE_STMT, this::visitWhileStmt);

        setDefaultVisit(this::defaultVisit);
    }


    private String visitAssignStmt(JmmNode node, Void unused) {


        StringBuilder code = new StringBuilder();

        Type thisType = TypeUtils.getExprType(node.getJmmChild(0), table);
        String typeString = OptUtils.toOllirType(thisType);



        var lhs = exprVisitor.visit(node.getJmmChild(0));

        if (lhs.getComputation().equals("skip")) return lhs.getCode();

        code.append(lhs.getComputation());


        OllirExprResult rhs;

        rhs = exprVisitor.visit(node.getJmmChild(1));
        code.append(rhs.getComputation());


        code.append(lhs.getCode());
        code.append(SPACE);

        code.append(ASSIGN);
        code.append(typeString);
        code.append(SPACE);

        code.append(rhs.getCode());

        code.append(END_STMT);

        return code.toString();
    }

    private String visitImport(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        var imports = node.get("value");

        // Remove square brackets if they exist
        imports = imports.substring(1, imports.length() - 1)
                .replaceAll("\\s+", "")
                .replaceAll(",", ".");

        code.append("import ");
        code.append(imports);
        code.append(";");
        code.append(NL);

        return code.toString();
    }

    private String visitIfStmt(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();

        OllirExprResult stmtCode = exprVisitor.visit(node.getJmmChild(0));
        code.append(stmtCode.getComputation());

        var ifNum = OptUtils.getVarIf();

        code.append("if (").append(stmtCode.getCode()).append(") goto if").append(ifNum).append(END_STMT);

        var firstBracket = node.getJmmChild(1);
        var secondBracket = node.getJmmChild(2);

        if (secondBracket.getKind().equals("BracketsStmt")){
            for (int i = 0; i < secondBracket.getNumChildren(); i++) {
                var child = secondBracket.getJmmChild(i);
                var childCode = visit(child);

                code.append(childCode);
            }
        }
        else{
            var childCode = visit(secondBracket);
            code.append(childCode);
        }


        code.append("goto endif").append(ifNum).append(END_STMT);
        code.append("if").append(ifNum).append(":\n");

        if(firstBracket.getKind().equals("BracketsStmt")){
            for (int i = 0; i < firstBracket.getNumChildren(); i++) {
                var child = firstBracket.getJmmChild(i);
                var childCode = visit(child);

                code.append(childCode);
            }
        }
        else{
            var childCode = visit(firstBracket);
            code.append(childCode);
        }

        code.append("endif").append(ifNum).append(":\n");


        return code.toString();
    }

    private String visitWhileStmt(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();
        StringBuilder whileCond = new StringBuilder();

        OllirExprResult stmtCode = exprVisitor.visit(node.getJmmChild(0));
        var whileNum = OptUtils.getVarWhile();
        String resOllirType = OptUtils.toOllirType("boolean");

        if(node.getJmmChild(0).getKind().equals("BooleanExpr")){
            var lhs = exprVisitor.visit(node.getJmmChild(0).getJmmChild(0));
            var rhs = exprVisitor.visit(node.getJmmChild(0).getJmmChild(1));

            whileCond.append(lhs.getCode()).append(SPACE).append(node.getJmmChild(0).get("op")).append(resOllirType).append(SPACE)
                    .append(rhs.getCode());
        }
        else{
            whileCond.append(stmtCode.getCode());
        }


        code.append("if(").append(whileCond).append(") goto whileBody").append(whileNum).append(END_STMT);
        code.append("goto whileEnd").append(whileNum).append(END_STMT);
        code.append("whileBody").append(whileNum).append(":\n");


        var firstBracket = node.getJmmChild(1);


        for (int i = 0; i < firstBracket.getNumChildren(); i++) {
            var child = firstBracket.getJmmChild(i);
            var childCode = visit(child);

            code.append(childCode);
        }

        code.append("if(").append(whileCond).append(") goto whileBody").append(whileNum).append(END_STMT);
        code.append("whileEnd").append(whileNum).append(":\n");


        return code.toString();
    }

    private String visitSimpleStmt(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();

        OllirExprResult stmtCode = exprVisitor.visit(node.getJmmChild(0));
        code.append(stmtCode.getComputation());
        code.append(stmtCode.getCode());

        return code.toString();
    }


    private String visitReturn(JmmNode node, Void unused) {

        String methodName = node.getAncestor(METHOD_DECL).map(method -> method.get("name")).orElseThrow();
        Type retType = table.getReturnType(methodName);



        StringBuilder code = new StringBuilder();

        var expr = OllirExprResult.EMPTY;

        if (node.getNumChildren() > 0) {
            expr = exprVisitor.visit(node.getJmmChild(0));
        }

        // add this to visitReturn (BINARY_EXPR, INTEGER_LITERAL, VAR_REF_EXPR, BOOLEAN_EXPR, PARENTH_EXPR)
        var nodeKind = node.getKind();



        OllirExprResult exprNode;
        switch (nodeKind) {
            case "BinaryExpr":

                exprNode = exprVisitor.visit(node);

                code.append(exprNode.getComputation());

                code.append("ret");
                code.append(OptUtils.toOllirType(retType));
                code.append(SPACE);

                code.append(exprNode.getCode());

                break;
            case "ParenthExpr":
            case "VarRefExpr":

                exprNode = exprVisitor.visit(node);

                code.append(exprNode.getComputation());
                code.append("ret");
                code.append(OptUtils.toOllirType(retType));
                code.append(SPACE);


                code.append(exprNode.getCode());
                break;

            default:

                exprNode = exprVisitor.visit(node);

                code.append(exprNode.getComputation());
                code.append("ret");
                code.append(OptUtils.toOllirType(retType));
                code.append(SPACE);
                code.append(exprNode.getCode());
                break;
        }




        code.append(END_STMT);

        return code.toString();
    }


    private String visitParam(JmmNode node, Void unused) {

        var typeChild = node.getJmmChild(0);
        var typeCode = OptUtils.toOllirType(typeChild);
        var id = node.get("name");
        String isVarArg = "";

        if(typeChild.get("isArray").equals("true")){
            isVarArg = ".array";
        }

        return id + isVarArg + typeCode;
    }

    private String visitVarDecl(JmmNode node, Void unused) {
        StringBuilder code = new StringBuilder();

        JmmNode parent = node.getJmmParent();
        JmmNode id = node.getJmmChild(0);

        if (parent.getKind().equals("ClassDecl")) {
            code.append(node.get("name"));
            code.append(OptUtils.toOllirType(id));
            code.append(";\n");

        }


        return code.toString();
    }


    private String visitMethodDecl(JmmNode node, Void unused) {

        StringBuilder code = new StringBuilder(".method ");

        boolean isPublic = NodeUtils.getBooleanAttribute(node, "isPublic", "false");
        boolean isMain = NodeUtils.getBooleanAttribute(node, "isMainMethod", "false");
        boolean isArray = false;


        if (isPublic) {
            code.append("public ");
        }

        if(isMain) {
            code.append("static ");
        }
        else{
            isArray = NodeUtils.getBooleanAttribute(node.getJmmChild(0), "isArray", "false");
        }

        // name
        var name = node.get("name");
        code.append(name);

        // param
        List<JmmNode> params = node.getChildren(PARAM).stream().toList();

        code.append("(");
        if (isMain){
            code.append(node.get("name_pars"));
            code.append(".array.String");
        }
        else{
            int numParams = params.size();
            for (int i = 0; i < numParams; i++) {
                var param = params.get(i);
                var paramCode = visit(param);
                code.append(paramCode);
                if (i < numParams - 1) { // Check if it's not the last parameter
                    code.append(", ");
                }
            }
        }


        code.append(")");

        // type

        String retType;
        boolean isVoid = false;

        isVoid = OptUtils.isVoid(node);

        if (isVoid) {
            retType = OptUtils.toOllirType("void");
        }
        else {
            retType = OptUtils.toOllirType(node.getJmmChild(0));
        }

        if(isArray){
            code.append(OptUtils.toOllirType("array"));
        }

        code.append(retType);
        code.append(L_BRACKET);


        // rest of its children stmts



        var afterParam = params.size() + 1;

        if(isMain){
            afterParam -= 1;
        }


        for (int i = afterParam; i < node.getNumChildren(); i++) {
            var child = node.getJmmChild(i);
            var childCode = visit(child);

            code.append(childCode);
        }

        if(isVoid){
            code.append("ret.V;\n");
        }

        code.append(R_BRACKET);
        code.append(NL);

        return code.toString();
    }


    private String visitClass(JmmNode node, Void unused) {

        StringBuilder code = new StringBuilder();

        code.append(table.getClassName());
        code.append(" extends ");

        if(table.getSuper() != ""){
            code.append(table.getSuper());
        }
        else{
            code.append("Object");
        }

        code.append(L_BRACKET);

        code.append(NL);
        var needNl = true;

        for (var child : node.getChildren()) {
            var result = visit(child);

            if (VAR_DECL.check(child)) {
                code.append(".field public ");
            }

            if (METHOD_DECL.check(child) && needNl) {
                code.append(NL);
                needNl = false;
            }

            code.append(result);
        }

        code.append(buildConstructor());
        code.append(R_BRACKET);

        return code.toString();
    }

    private String buildConstructor() {

        return ".construct " + table.getClassName() + "().V {\n" +
                "invokespecial(this, \"<init>\").V;\n" +
                "}\n";
    }


    private String visitProgram(JmmNode node, Void unused) {

        StringBuilder code = new StringBuilder();

        node.getChildren().stream()
                .map(this::visit)
                .forEach(code::append);

        return code.toString();
    }

    /**
     * Default visitor. Visits every child node and return an empty string.
     *
     * @param node
     * @param unused
     * @return
     */
    private String defaultVisit(JmmNode node, Void unused) {

        for (var child : node.getChildren()) {
            visit(child);
        }

        return "";
    }
}
