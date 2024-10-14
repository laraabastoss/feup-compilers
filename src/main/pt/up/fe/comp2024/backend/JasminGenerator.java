package pt.up.fe.comp2024.backend;

import org.specs.comp.ollir.*;
import org.specs.comp.ollir.tree.TreeNode;
import org.stringtemplate.v4.ST;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.specs.util.classmap.FunctionClassMap;
import pt.up.fe.specs.util.exceptions.NotImplementedException;
import pt.up.fe.specs.util.utilities.StringLines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

/**
 * Generates Jasmin code from an OllirResult.
 * <p>
 * One JasminGenerator instance per OllirResult.
 */
public class JasminGenerator {

    private static final String NL = "\n";
    private static final String TAB = "   ";

    private final OllirResult ollirResult;
    private int currentStackDepth;
    private int maxStackDepth;
    private int maxLocalIndex;

    List<Report> reports;

    String code;

    Method currentMethod;

    private final FunctionClassMap<TreeNode, String> generators;

    public JasminGenerator(OllirResult ollirResult) {
        this.ollirResult = ollirResult;

        reports = new ArrayList<>();
        code = null;
        currentMethod = null;

        this.generators = new FunctionClassMap<>();
        generators.put(ClassUnit.class, this::generateClassUnit);
        generators.put(Method.class, this::generateMethod);
        generators.put(AssignInstruction.class, this::generateAssign);
        generators.put(SingleOpInstruction.class, this::generateSingleOp);
        generators.put(LiteralElement.class, this::generateLiteral);
        generators.put(Operand.class, this::generateOperand);
        generators.put(BinaryOpInstruction.class, this::generateBinaryOp);
        generators.put(ReturnInstruction.class, this::generateReturn);
        generators.put(PutFieldInstruction.class, this::generatePut);
        generators.put(CallInstruction.class, this::generateCall);
        generators.put(GetFieldInstruction.class, this::generateGetField);
        generators.put(GotoInstruction.class, this::generateGoto);
        generators.put(SingleOpCondInstruction.class, this::generateSingleOpCond);
        generators.put(UnaryOpInstruction.class, this::generateUnaryOp);
        generators.put(OpCondInstruction.class, this::generateOpCond);
    }

    public List<Report> getReports() {
        return reports;
    }

    public String build() {

        // This way, build is idempotent
        if (code == null) {
            code = generators.apply(ollirResult.getOllirClass());
        }

        return code;
    }


    private String generateClassUnit(ClassUnit classUnit) {

        var code = new StringBuilder();

        // generate class name
        var className = ollirResult.getOllirClass().getClassName();
        code.append(".class ").append(className).append(NL).append(NL);

        var superClassName = ollirResult.getOllirClass().getSuperClass();
        if (superClassName == null || superClassName.equals("Object")){
            code.append(".super java/lang/Object").append(NL);
        }
        else{
            code.append(".super ").append(superClassName).append(NL);
        }

        for (var field : ollirResult.getOllirClass().getFields()){
            var fieldType = getType(field.getFieldType());
            code.append(".field ").append(field.getFieldName()).append(" ").append(fieldType).append(NL);
        }
        code.append(NL);

        // generate a single constructor method
        code.append("""
                ;default constructor
                .method public <init>()V
                    aload_0
                    invokespecial """).append((superClassName == null || superClassName.equals("Object")) ? " java/lang/Object" : " " + superClassName)
                .append("""
               /<init>()V
                    return
                .end method
                """);

        // generate code for all other methods
        for (var method : ollirResult.getOllirClass().getMethods()) {

            // Ignore constructor, since there is always one constructor
            // that receives no arguments, and has been already added
            // previously
            if (method.isConstructMethod()) {
                continue;
            }

            code.append(generators.apply(method));
        }

        return code.toString();
    }


    private String generateMethod(Method method) {

        // set method
        currentMethod = method;

        currentStackDepth = 0;
        maxStackDepth = 0;
        maxLocalIndex = method.isStaticMethod() ? 0 : 1;

        var code = new StringBuilder();

        // calculate modifier
        var modifier = method.getMethodAccessModifier() != AccessModifier.DEFAULT ?
                method.getMethodAccessModifier().name().toLowerCase() + " " :
                "";

        var methodName = method.getMethodName();

        code.append("\n.method ").append(modifier);
        if (method.isStaticMethod()) code.append("static ");
        if (method.isFinalMethod()) code.append("final ");
        code.append(methodName).append("(");
        for (Element param : method.getParams()) {
            code.append(getType(param.getType()));
            maxLocalIndex++;
        }
        code.append(")").append(getType(method.getReturnType())).append(NL);

        // Add limits
        //code.append(TAB).append(".limit stack 99").append(NL);
        //code.append(TAB).append(".limit locals 99").append(NL);

        var instructionsCode = new StringBuilder();
        for (var inst : method.getInstructions()) {

            for (String label : method.getLabels(inst)) {
                instructionsCode.append(label).append(":").append(NL);
            }

            var instCode = StringLines.getLines(generators.apply(inst)).stream()
                    .collect(Collectors.joining(NL + TAB, TAB, NL));

            instructionsCode.append(instCode);
            if (inst.getInstType().equals(InstructionType.CALL) &&
                    ((CallInstruction)inst).getReturnType().getTypeOfElement() != ElementType.VOID) {
                instructionsCode.append(TAB).append("pop").append(NL);
                updateStackDepth(-1);
            }

        }

        if ((method.getInstructions().isEmpty()) ||
            (method.getInstructions().get(method.getInstructions().size()-1).getInstType() != InstructionType.RETURN
            && method.getReturnType().getTypeOfElement() == ElementType.VOID)) {
            instructionsCode.append("return\n");
            updateStackDepth(-1);
        }

        code.append(TAB).append(".limit stack ").append(maxStackDepth).append(NL);
        code.append(TAB).append(".limit locals ").append(maxLocalIndex + 1).append(NL);

        code.append(instructionsCode);

        code.append(".end method\n");

        // unset method
        currentMethod = null;

        return code.toString();
    }

    private String generateAssign(AssignInstruction assign) {
        var code = new StringBuilder();

        // generate code for loading what's on the right
        code.append(generators.apply(assign.getRhs()));

        // store value in the stack in destination
        var lhs = assign.getDest();

        if (!(lhs instanceof Operand)) {
            throw new NotImplementedException(lhs.getClass());
        }

        var operand = (Operand) lhs;

        // get register
        var reg = currentMethod.getVarTable().get(operand.getName()).getVirtualReg();
        ElementType type = operand.getType().getTypeOfElement();

        if (type.equals(ElementType.BOOLEAN) || type.equals(ElementType.INT32)){
            if (reg > 3) code.append("istore ").append(reg).append(NL);
            else code.append("istore_").append(reg).append(NL);
        }
        else if (type.equals(ElementType.OBJECTREF) || type.equals(ElementType.THIS) || type.equals(ElementType.STRING)){
            if (reg > 3) code.append("astore ").append(reg).append(NL);
            else code.append("astore_").append(reg).append(NL);
        }
        /*else if (type.equals(ElementType.ARRAYREF)){
            updateStackDepth(1);
            if (reg > 3) code.append("aload ").append(reg).append(NL);
            else code.append("aload_").append(reg).append(NL);
        }*/

        updateStackDepth(-1);
        updateLocalIndex(reg);

        return code.toString();
    }

    private String generateSingleOp(SingleOpInstruction singleOp) {
        return generators.apply(singleOp.getSingleOperand());
    }

    private String generateLiteral(LiteralElement literal) {
        ElementType type = literal.getType().getTypeOfElement();

        if (type.equals(ElementType.INT32) || type.equals(ElementType.BOOLEAN)){
            int literal_element = parseInt(literal.getLiteral());
            updateStackDepth(1);
            if (literal_element == -1) return "iconst_m1" + NL;
            else if (literal_element >= 0 && literal_element <= 5) return "iconst_" + literal_element + NL;
            else if (literal_element >= -128 && literal_element <= 127) return "bipush " + literal_element + NL;
            else if (literal_element >= -32768 && literal_element <= 32767) return "sipush " + literal_element + NL;
            else return "ldc " + literal.getLiteral() + NL;
        }

        return (new StringBuilder()).toString();

    }



    private String generateOperand(Operand operand) {
        // get register
        ElementType type = operand.getType().getTypeOfElement();

        if (type.equals(ElementType.INT32) || type.equals(ElementType.BOOLEAN)){
            var reg = currentMethod.getVarTable().get(operand.getName()).getVirtualReg();
            updateStackDepth(1);
            if (reg > 3) return "iload " + reg + NL;
            else return "iload_" + reg + NL;
        }
        else if (type.equals(ElementType.ARRAYREF) || type.equals(ElementType.OBJECTREF)
                || type.equals(ElementType.THIS) || type.equals(ElementType.STRING)){
            var reg = currentMethod.getVarTable().get(operand.getName()).getVirtualReg();
            updateStackDepth(1);
            if (reg > 3) return "aload " + reg + NL;
            else return "aload_" + reg + NL;
        }

        return (new StringBuilder()).toString();

    }

    private String generateBinaryOp(BinaryOpInstruction binaryOp) {
        var code = new StringBuilder();

        // load values on the left and on the right
        code.append(generators.apply(binaryOp.getLeftOperand()));
        code.append(generators.apply(binaryOp.getRightOperand()));

        // apply operation
        var op = switch (binaryOp.getOperation().getOpType()) {
            case ADD -> "iadd";
            case MUL -> "imul";
            case SUB -> "isub";
            case DIV -> "idiv";
            case AND -> "iand";
            case OR -> "ior";
            case XOR -> "ixor";
            case NOTB -> "ifeq";
            case EQ -> "if_icmpeq";
            case NEQ -> "if_icmpne";
            case LTH -> "if_icmplt";
            case GTE -> "if_icmpte";
            default -> throw new NotImplementedException(binaryOp.getOperation().getOpType());
        };

        code.append(op).append(NL);
        updateStackDepth(-1);

        return code.toString();
    }

    private String generateReturn(ReturnInstruction returnInst) {
        var code = new StringBuilder();

        if (returnInst.getOperand() != null) {
            code.append(generators.apply(returnInst.getOperand()));
            if (returnInst.getOperand().getType().getTypeOfElement() == ElementType.INT32 ||
                    returnInst.getOperand().getType().getTypeOfElement() == ElementType.BOOLEAN) {
                code.append("i");
            } else code.append("a");
            updateStackDepth(-1);
        }
        code.append("return").append(NL);

        return code.toString();
    }

    private String generatePut(PutFieldInstruction putField){
        var code = new StringBuilder();
        var className = ((ClassType) putField.getOperands().get(0).getType()).getName();

        code.append(generators.apply(putField.getOperands().get(0)));
        code.append(generators.apply(putField.getOperands().get(2)));

        code.append("putfield ").append(getNameofClass(ollirResult.getOllirClass(), className)).append("/")
            .append(putField.getField().getName()).append(" ")
            .append(getType(putField.getField().getType())).append(NL);

        return code.toString();
    }

    private String generateCall(CallInstruction call){
        var code = new StringBuilder();
        var invokeCall = call.getInvocationType().name().toLowerCase();

        if (invokeCall.equals("new")){
            code.append("new ").append(((Operand)call.getCaller()).getName()).append(NL);
            code.append("dup").append(NL);
            updateStackDepth(2);
            return code.toString();
        }

        for (Element element : call.getOperands()){
            code.append(generators.apply(element));
            updateStackDepth(1);
        }

        code.append(invokeCall).append(" ").append((call.getCaller().getType().getTypeOfElement().equals(ElementType.OBJECTREF)
                                            || call.getCaller().getType().getTypeOfElement().equals(ElementType.THIS)) ?
                                            getNameofClass(ollirResult.getOllirClass(), ((ClassType)call.getCaller().getType()).getName())
                                            : getNameofClass(ollirResult.getOllirClass(), ((Operand)call.getCaller()).getName())).append("/");
        if (call.getMethodName().isLiteral()){
            var methodName = ((LiteralElement) call.getMethodName()).getLiteral();
            if (methodName.equals("\"\"")) code.append("<init>");
            else if (methodName.startsWith("\"")) code.append(methodName.substring(1, methodName.length()-1));
            else code.append(methodName);
        }
        else{
            code.append(call.getMethodName().toString());
        }
        code.append("(");

        for (Element element : call.getArguments()){
            code.append(getType(element.getType()));
        }
        code.append(")").append(getType(call.getReturnType()));

        if (invokeCall.equals("invokeinterface")){
            code.append(" ").append(call.getArguments().size());
        }
        code.append(NL);

        if (call.getReturnType().getTypeOfElement() != ElementType.VOID) {
            updateStackDepth(1);
        }

        return code.toString();
    }

    private String generateGetField(GetFieldInstruction getField){
        var code = new StringBuilder();
        var className = ((ClassType) getField.getOperands().get(0).getType()).getName();

        code.append(generators.apply(getField.getOperands().get(0)));
        code.append("getfield ").append(getNameofClass(ollirResult.getOllirClass(), className)).append("/")
                .append(getField.getField().getName()).append(" ")
                .append(getType(getField.getField().getType())).append(NL);

        return code.toString();
    }
    private String generateSingleOpCond(SingleOpCondInstruction singleOpCond){
        var code = new StringBuilder();
        code.append(generators.apply(singleOpCond.getCondition()));
        var label = singleOpCond.getLabel();
        code.append("ifne ").append(label).append(NL);
        updateStackDepth(-1);
        return code.toString();
    }

    private String generateGoto(GotoInstruction gotoInstruction){
        StringBuilder code = new StringBuilder();
        code.append("goto ").append(gotoInstruction.getLabel()).append(NL);
        return code.toString();
    }

    private String generateUnaryOp(UnaryOpInstruction unaryOpInstruction){
        StringBuilder code = new StringBuilder();
        updateStackDepth(1);
        code.append("iconst_1").append(NL);
        code.append(generators.apply(unaryOpInstruction.getOperand()));
        code.append("ixor").append(NL);
        updateStackDepth(-1);
        return code.toString();
    }

    private String generateOpCond(OpCondInstruction opCondInstruction){
        StringBuilder code = new StringBuilder();

        code.append(generators.apply(opCondInstruction.getOperands().get(0)));
        code.append(generators.apply(opCondInstruction.getOperands().get(1)));

        code.append("isub").append(NL);
        updateStackDepth(-1);

        OperationType operation = opCondInstruction.getCondition().getOperation().getOpType();

        switch (operation){
            case LTH:
                code.append("iflt ");
                break;
            case GTH:
                code.append("ifgt ");
                break;
            case LTE:
                code.append("ifle ");
                break;
            case GTE:
                code.append("ifge ");
                break;

        }
        code.append(opCondInstruction.getLabel()).append(NL);
        return code.toString();
    }

    private String getType(Type type) {
        var t = type.getTypeOfElement();
        if (t == ElementType.ARRAYREF) return getArrayType(type);
        else if (t == ElementType.OBJECTREF) return getObjectType(type);
        if (t.equals(ElementType.INT32)) return "I";
        if (t.equals(ElementType.BOOLEAN)) return "Z";
        if (t.equals(ElementType.VOID)) return "V";
        if (t.equals(ElementType.STRING)) return "Ljava/lang/String;";
        return null;
    }

    private String getArrayType(Type type){
        return "[" + getType(((ArrayType) type).getElementType());
    }

    private String getObjectType(Type type){

        String className = ((ClassType) type).getName();
        if (className.equals("this")){
            return "L" + ollirResult.getOllirClass().getClassName() + ";";
        }

        for (String importedClass : ollirResult.getOllirClass().getImports()){
            if (importedClass.endsWith(className)){
                return "L" + importedClass + ";";
            }
        }

        return "L" + className + ";";

    }

    public static String getNameofClass(ClassUnit classUnit, String nameofClass) {
        for (String importElement: classUnit.getImports()) {
            String[] split = importElement.split("\\.");
            String lastImport = (split.length == 0) ? importElement : split[split.length - 1];

            if (lastImport.equals(nameofClass)) {
                return importElement.replace('.', '/');
            }
        }
        if(nameofClass.contains(".")) return classUnit.getClassName().replace(".", "/");
        return classUnit.getClassName();
    }

    private void updateStackDepth(int value){
        currentStackDepth += value;
        if (currentStackDepth > maxStackDepth){
            maxStackDepth = currentStackDepth;
        }
    }

    private void updateLocalIndex(int index){
        if (index > maxLocalIndex){
            maxLocalIndex = index;
        }
    }

}
