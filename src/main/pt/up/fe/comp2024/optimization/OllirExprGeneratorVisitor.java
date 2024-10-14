package pt.up.fe.comp2024.optimization;

import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ast.JmmNodeImpl;
import pt.up.fe.comp.jmm.ast.PreorderJmmVisitor;
import pt.up.fe.comp2024.ast.TypeUtils;

import java.util.ArrayList;
import java.util.List;

import static pt.up.fe.comp2024.ast.Kind.*;

/**
 * Generates OLLIR code from JmmNodes that are expressions.
 */
public class OllirExprGeneratorVisitor extends PreorderJmmVisitor<Void, OllirExprResult> {

    private static final String SPACE = " ";
    private static final String ASSIGN = ":=";
    private final String END_STMT = ";\n";

    private final SymbolTable table;

    public OllirExprGeneratorVisitor(SymbolTable table) {
        this.table = table;
    }

    @Override
    protected void buildVisitor() {
        addVisit(VAR_REF_EXPR, this::visitVarRef);
        addVisit(BINARY_EXPR, this::visitBinExpr);
        addVisit(BOOLEAN_EXPR, this::visitBooleanExpr);
        addVisit(INTEGER_LITERAL, this::visitInteger);
        addVisit(BOOLEAN_LITERAL, this::visitBoolean);
        addVisit(METHOD_CALL, this::visitMethodCall);
        addVisit(PARENTH_EXPR, this::visitParenthExpr);
        addVisit(OBJECT_DECL, this::visitObjDecl);
        addVisit(NEW_ARRAY_DECL, this::visitNewArrayDecl);
        addVisit(THIS, this::visitThis);
        addVisit(NOT, this::visitNot);
        addVisit(LENGTH, this::visitLength);
        addVisit(ARRAY_SUB, this::visitArraySub);
        addVisit(ARRAY_DECL, this::visitArrayDecl);

        setDefaultVisit(this::defaultVisit);
    }

    private OllirExprResult visitThis(JmmNode node, Void unused){
        var className = table.getClassName();
        String code = node.get("value") + "." + className;
        return new OllirExprResult(code);
    }

    private OllirExprResult visitNot(JmmNode node, Void unused){
        StringBuilder computation = new StringBuilder();


        String resOllirType = OptUtils.toOllirType("boolean");
        String code = OptUtils.getTemp() + resOllirType;

        var childCode = visit(node.getJmmChild(0));

        computation.append(code).append(ASSIGN).append(resOllirType)
                .append(SPACE).append("!").append(resOllirType)
                .append(SPACE).append(childCode.getCode()).append(END_STMT);


        return new OllirExprResult(code, computation);
    }

    private OllirExprResult visitLength(JmmNode node, Void unused){
        StringBuilder computation = new StringBuilder();

        String resOllirType = OptUtils.toOllirType("int");
        String code = OptUtils.getTemp() + resOllirType;

        computation.append(code)
                .append(SPACE).append(ASSIGN).append(resOllirType).append(SPACE)
                .append("arraylength(").append(node.getJmmChild(0).get("name")).append(OptUtils.toOllirType(TypeUtils.getExprType(node.getJmmChild(0), table)))
                .append(")").append(resOllirType).append(END_STMT);



        return new OllirExprResult(code, computation);
    }

    private OllirExprResult visitParenthExpr(JmmNode node, Void unused){

        var child = node.getJmmChild(0);
        var parexpr = visit(child);
        return new OllirExprResult(parexpr.getCode(), parexpr.getComputation());

    }

    private OllirExprResult visitNewArrayDecl(JmmNode node, Void unused){

        StringBuilder computation = new StringBuilder();
        String code = "";

        Type thisType = TypeUtils.getExprType(node.getParent().getJmmChild(0), table);
        String typeString = OptUtils.toOllirType(thisType);

        var childCode = visit(node.getJmmChild(0));

        code += "new(array, ";
        code += childCode.getCode();
        code += ")";
        code += typeString;

        return new OllirExprResult(code, computation);

    }

    private OllirExprResult visitArrayDecl(JmmNode node, Void unused){

        StringBuilder computation = new StringBuilder();


        Type thisType = TypeUtils.getExprType(node, table);
        String arrayTypeString = OptUtils.toOllirType(thisType);
        String typeString = OptUtils.removeArray(arrayTypeString);

        int arrayElem = node.getNumChildren();

        String temp = OptUtils.getTemp() + arrayTypeString;

        computation.append(temp).append(SPACE).append(ASSIGN).append(arrayTypeString)
                .append(SPACE).append("new(array, ").append(arrayElem).append(OptUtils.toOllirType("int"))
                .append(")").append(arrayTypeString).append(END_STMT);

        String code = OptUtils.getVarArg() + arrayTypeString;

        computation.append(code).append(SPACE).append(ASSIGN).append(arrayTypeString)
                .append(SPACE).append(temp).append(END_STMT);

        for (int i = 0; i < arrayElem; i++) {

            var child = node.getJmmChild(i);
            var childCode = visit(child);

            computation.append(code).append("[").append(i).append(OptUtils.toOllirType("int"))
                    .append("]").append(typeString)
                    .append(SPACE).append(ASSIGN).append(typeString)
                    .append(SPACE).append(childCode.getCode()).append(END_STMT);

        }



        return new OllirExprResult(code, computation);

    }

    private OllirExprResult visitArraySub(JmmNode node, Void unused){

        StringBuilder computation = new StringBuilder();
        String code = "";


        Type thisType = TypeUtils.getExprType(node.getJmmChild(0), table);
        String typeString = OptUtils.toOllirType(thisType);
        typeString = OptUtils.removeArray(typeString);

        var childName = node.getJmmChild(0).get("name");
        var childCode = visit(node.getJmmChild(1));

        computation.append(childCode.getComputation());

        if(node.getParent().getKind().equals("AssignStmt")){
            code += childName;
            code += "[";
            code += childCode.getCode();
            code += "]";
            code += typeString;
        }
        else{

            String tempVar = OptUtils.getTemp();

            String resOllirType = OptUtils.toOllirType("int");
            code = OptUtils.getTemp() + resOllirType;

            computation.append(code).append(SPACE).append(ASSIGN).append(resOllirType).append(SPACE)
                    .append(childName).append(".array").append(typeString).append("[").append(childCode.getCode()).append("]").append(typeString)
                    .append(END_STMT);

        }

        return new OllirExprResult(code, computation);

    }

    private OllirExprResult visitObjDecl(JmmNode node, Void unused) {
        StringBuilder computation = new StringBuilder();

        String resOllirType = "." + node.get("name");
        String tempVar = OptUtils.getTemp();

        computation.append(tempVar).append(resOllirType)
                .append(SPACE).append(ASSIGN).append(resOllirType).append(SPACE)
                .append("new(").append(node.get("name")).append(")").append(resOllirType).append(END_STMT);

        computation.append("invokespecial(").append(tempVar).append(resOllirType)
                .append(", \"\").V").append(END_STMT);

        String code = tempVar + resOllirType;

        return new OllirExprResult(code, computation);
    }

    private OllirExprResult visitMethodCall(JmmNode node, Void unused) {
        StringBuilder computation = new StringBuilder();
        StringBuilder invoke = new StringBuilder();
        String code = "";

        JmmNode firstChild = node.getJmmChild(0);

        JmmNode parent = node.getParent();
        int numChildren = node.getNumChildren();
        int numChildrenTemp = numChildren;

        //String methodName = node.getAncestor(METHOD_DECL).map(method -> method.get("name")).orElseThrow();
        Type resType = table.getReturnType(node.get("name"));



        if (parent.getKind().equals("AssignStmt")){
            resType = TypeUtils.getExprType(node.getParent().getChild(0), table);
        }

        if (resType == null) {
            resType = new Type("void", false);
        }



        String retString = "";

        var className = table.getClassName();

        List<String> paramCodes = new ArrayList<>();
        var paramList = table.getParameters(node.get("name"));
        var parNum = paramList.size();
        boolean varArg = false;
        boolean hasArray = false;

        for(int i = 0; i < parNum; i++){
            if(paramList.get(i).getType().isArray()){
                hasArray = true;
            }
        }

        // possible ERRORS

        var optionalClassDecl = node.getAncestor(CLASS_DECL);
        var methodClassDecl = optionalClassDecl.get();
        String methodCallName = node.get("name");

        for(int i = 0; i < methodClassDecl.getNumChildren(); i++){
            var child = methodClassDecl.getChild(i);
            var childName = child.get("name");
            if (childName.equals(methodCallName)){
                var params = child.getChildren(PARAM);
                for(int j = 0; j < params.size(); j++){
                    var paramChild = params.get(j);
                    var paramType = paramChild.getChild(0);
                    var isVarArgType = paramType.get("isVarArgs");
                    if (isVarArgType.equals("true")){
                        numChildrenTemp = parNum;
                        varArg = true;
                    }
                }

            }
        }



        for(int i = 1; i < numChildrenTemp; i++){
            var param = visit(node.getJmmChild(i));
            computation.append(param.getComputation());
            paramCodes.add(param.getCode());
        }

        if (varArg) {
            // Create a new ArrayDecl node and add the remaining integer literals
            JmmNode arrayDeclNode = new JmmNodeImpl("ArrayDecl");
            for (int i = numChildrenTemp; i < node.getNumChildren(); i++) {
                arrayDeclNode.add(node.getJmmChild(i));
            }

            // Visit the new ArrayDecl node
            var arrayDeclResult = visit(arrayDeclNode);
            computation.append(arrayDeclResult.getComputation());
            paramCodes.add(arrayDeclResult.getCode());
        }



        List<String> imports = table.getImports();


        boolean isStatic = false;

        if(!firstChild.getKind().equals("This") && !firstChild.getKind().equals("ParenthExpr")){
            if(imports.contains(firstChild.get("name"))){
                isStatic = true;
            }
        }


        if(isStatic) {
            invoke.append("invokestatic(");
            if (firstChild.getKind().equals("VarRefExpr")) {
                invoke.append(firstChild.get("name"));
            }
            else{
                var firstChildExpr = visit(firstChild);
                invoke.append(firstChildExpr.getCode());
                computation.append(firstChildExpr.getComputation());
            }

            retString = OptUtils.toOllirType(resType);

        }
        else {


            retString = OptUtils.toOllirType(resType);

            invoke.append("invokevirtual(");
            var firstChildExpr = visit(firstChild);

            invoke.append(firstChildExpr.getCode());
            computation.append(firstChildExpr.getComputation());

        }

        if(!parent.getKind().equals("SimpleStmt")){
            String resOllirType = OptUtils.toOllirType(resType);
            code = OptUtils.getTemp() + resOllirType;

            computation.append(code).append(SPACE)
                    .append(ASSIGN).append(resOllirType).append(SPACE);
        }

        computation.append(invoke);
        computation.append(", \"");
        computation.append(node.get("name"));
        computation.append("\"");
        if(numChildren > 1) computation.append(", ");

        for(int i = 0; i < paramCodes.size(); i++){
            computation.append(paramCodes.get(i));
            if (i < paramCodes.size() - 1) { // Check if it's not the last parameter
                computation.append(", ");
            }

        }
        computation.append(")");
        computation.append(retString);
        computation.append(END_STMT);



        return new OllirExprResult(code, computation);
    }


    private OllirExprResult visitInteger(JmmNode node, Void unused) {
        var intType = new Type(TypeUtils.getIntTypeName(), false);
        String ollirIntType = OptUtils.toOllirType(intType);
        String code = node.get("value") + ollirIntType;
        return new OllirExprResult(code);
    }

    private OllirExprResult visitBoolean(JmmNode node, Void unused) {
        var boolType = new Type(TypeUtils.getBooleanTypeName(), false);
        String ollirBoolType = OptUtils.toOllirType(boolType);
        String intBool;
        if (node.get("value").equals("true")) {
            intBool = "1";
        }
        else intBool = "0";
        String code = intBool + ollirBoolType;
        return new OllirExprResult(code);
    }

    private OllirExprResult visitBooleanExpr(JmmNode node, Void unused) {

        StringBuilder computation = new StringBuilder();
        String varTrue = OptUtils.getVarTrue();


        var lhs = visit(node.getJmmChild(0));
        String lhsComp = lhs.getComputation();
        String lhsCode = lhs.getCode();
        computation.append(lhsComp);




        var rhs = visit(node.getJmmChild(1));
        String rhsComp = rhs.getComputation();
        String rhsCode = rhs.getCode();
        //computation.append(rhsComp);


        // code to compute self

        String resOllirType = OptUtils.toOllirType("boolean");
        String code = OptUtils.getTemp() + resOllirType;

        if (node.get("op").equals("&&")) {

            computation.append("if(").append(lhsCode).append(") goto true_").append(varTrue).append(END_STMT);

            computation.append(code).append(SPACE).append(ASSIGN).append(resOllirType).append(SPACE)
                    .append("0.bool").append(END_STMT);

            computation.append("goto end_").append(varTrue).append(END_STMT);

            computation.append("true_").append(varTrue).append(":\n").append(rhsComp).append(code).append(SPACE).append(ASSIGN).append(resOllirType)
                    .append(SPACE).append(rhsCode).append(END_STMT);

            computation.append("end_").append(varTrue).append(":\n");

            return new OllirExprResult(code, computation);
        }


        computation.append("if(").append(lhsCode).append(SPACE)
                        .append(node.get("op")).append(resOllirType).append(SPACE)
                        .append(rhsCode).append(") goto true_").append(varTrue).append(END_STMT);

        computation.append(code).append(SPACE).append(ASSIGN).append(resOllirType).append(SPACE)
                .append("0.bool").append(END_STMT);

        computation.append("goto end_").append(varTrue).append(END_STMT);

        computation.append("true_").append(varTrue).append(":\n").append(code).append(SPACE).append(ASSIGN).append(resOllirType)
                .append(SPACE).append("1.bool").append(END_STMT);

        computation.append("end_").append(varTrue).append(":\n");



//        computation.append("if(1.bool) goto true_0").append(END_STMT);
//        computation.append("goto end_0").append(END_STMT);
//
//        computation.append(code).append(SPACE)
//                .append(ASSIGN).append(resOllirType).append(SPACE)
//                .append(lhsCode).append(SPACE);
//
//
//        computation.append(node.get("op")).append(resOllirType).append(SPACE)
//                .append(rhsCode).append(END_STMT);

        return new OllirExprResult(code, computation);
    }


    private OllirExprResult visitBinExpr(JmmNode node, Void unused) {



        StringBuilder computation = new StringBuilder();


        var lhs = visit(node.getJmmChild(0));
        String lhsComp = lhs.getComputation();
        String lhsCode = lhs.getCode();
        computation.append(lhsComp);




        var rhs = visit(node.getJmmChild(1));
        String rhsComp = rhs.getComputation();
        String rhsCode = rhs.getCode();
        computation.append(rhsComp);


        // code to compute self
        Type resType = TypeUtils.getExprType(node, table);
        String resOllirType = OptUtils.toOllirType(resType);
        String code = OptUtils.getTemp() + resOllirType;


        computation.append(code).append(SPACE)
                .append(ASSIGN).append(resOllirType).append(SPACE)
                .append(lhsCode).append(SPACE);

        Type type = TypeUtils.getExprType(node, table);
        computation.append(node.get("op")).append(OptUtils.toOllirType(type)).append(SPACE)
                .append(rhsCode).append(END_STMT);

        return new OllirExprResult(code, computation);
    }


    private OllirExprResult visitVarRef(JmmNode node, Void unused) {

        var id = node.get("name");
        JmmNode parent = node.getParent();



        StringBuilder computation = new StringBuilder();

        List<String> fieldsList = new ArrayList<>();
        var fields = table.getFields();

        for (var field : fields) {
            fieldsList.add(field.getName());
        }

        List<String> localsList = new ArrayList<>();
        String methodName = node.getAncestor(METHOD_DECL).map(method -> method.get("name")).orElseThrow();



        var locals = table.getLocalVariables(methodName);

        for (var local : locals) {
            localsList.add(local.getName());

        }



        List<String> paramsList = new ArrayList<>();
        var params = table.getParameters(node.getAncestor(METHOD_DECL).map(method -> method.get("name")).orElseThrow());

        for (var param : params) {
            localsList.add(param.getName());
        }

        
        if(fieldsList.contains(id) && !localsList.contains(id) && !paramsList.contains(id)){
            if(parent.getJmmChild(0).equals(node) && parent.getKind().equals("AssignStmt")){

                StringBuilder code = new StringBuilder();

                Type resType = TypeUtils.getExprType(node, table);
                String resOllirType = OptUtils.toOllirType(resType);

                var rhs = visit(parent.getJmmChild(1));

                code.append(rhs.getComputation());

                code.append("putfield(this, ").append(id).append(resOllirType).append(", ")
                        .append(rhs.getCode())
                        .append(").V").append(END_STMT);


                return new OllirExprResult(code.toString(), "skip");

            }
            else {
                String tempVar = OptUtils.getTemp();

                StringBuilder code = new StringBuilder();

                Type resType = TypeUtils.getExprType(node, table);
                String resOllirType = OptUtils.toOllirType(resType);

                computation.append(tempVar).append(resOllirType)
                        .append(SPACE).append(ASSIGN).append(resOllirType).append(SPACE)
                        .append("getfield(this, ").append(id).append(resOllirType).append(")").append(resOllirType).append(END_STMT);

                code.append(tempVar).append(resOllirType);

                return new OllirExprResult(code.toString(), computation);
            }
        }




        Type type = TypeUtils.getExprType(node, table);
        String ollirType = OptUtils.toOllirType(type);


        String code = id + ollirType;

        return new OllirExprResult(code);
    }

    /**
     * Default visitor. Visits every child node and return an empty result.
     *
     * @param node
     * @param unused
     * @return
     */
    private OllirExprResult defaultVisit(JmmNode node, Void unused) {

        for (var child : node.getChildren()) {
            visit(child);
        }

        return OllirExprResult.EMPTY;
    }

}
