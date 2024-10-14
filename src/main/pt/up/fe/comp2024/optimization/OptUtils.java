package pt.up.fe.comp2024.optimization;

import org.specs.comp.ollir.Instruction;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.specs.util.exceptions.NotImplementedException;

import java.util.List;
import java.util.Optional;

import static pt.up.fe.comp2024.ast.Kind.TYPE;

public class OptUtils {
    private static int tempNumber = -1;
    private static int varArgNumber = -1;
    private static int varTrue = -1;
    private static int varIf = -1;
    private static int varWhile = -1;

    public static String getTemp() {

        return getTemp("tmp");
    }

    public static String getVarArg() {

        return getVarArg("__varargs_array_");
    }

    public static String getVarTrue() {

        return "" + getNextVarTrue();
    }

    public static String getVarIf() {

        return "" + getNextVarIf();
    }

    public static String getVarWhile() {

        return "" + getNextVarWhile();
    }

    public static String getTemp(String prefix) {

        return prefix + getNextTempNum();
    }

    public static String getVarArg(String prefix) {

        return prefix + getNextVarArg();
    }

    public static int getNextTempNum() {

        tempNumber += 1;
        return tempNumber;
    }

    public static int getNextVarArg() {

        varArgNumber += 1;
        return varArgNumber;
    }

    public static int getNextVarTrue() {

        varTrue += 1;
        return varTrue;
    }

    public static int getNextVarIf() {

        varIf += 1;
        return varIf;
    }

    public static int getNextVarWhile() {

        varWhile += 1;
        return varWhile;
    }

    public static void reduceTemp(int a){
        tempNumber -= a;
    }

    public static String toOllirType(JmmNode typeNode) {

        TYPE.checkOrThrow(typeNode);

        String typeName = typeNode.get("name");

        return toOllirType(typeName);
    }

    public static String toOllirType(Type type) {
        return toOllirType(type.getName(), type.isArray());
    }

    public static String toOllirType(String typeName) {

        String type = "." + switch (typeName) {
            case "int" -> "i32";
            case "boolean" -> "bool";
            case "void" -> "V";
            default -> typeName;
        };

        if(type.equals(".")) return "";

        return type;
    }

    public static String toOllirType(String typeName, boolean array) {

        String type = "";

        if (array) type += ".array";

        type += "." + switch (typeName) {
            case "int" -> "i32";
            case "boolean" -> "bool";
            case "void" -> "V";
            default -> typeName;
        };

        if(type.equals(".")) return "";

        return type;
    }

    public static Boolean isVoid(JmmNode node){

        Boolean isVoid = false;

        if (node.getNumChildren() == 0) {
            isVoid = true;
        }
        else{
            var retTypeNode = node.getJmmChild(0);
            isVoid = !retTypeNode.getKind().equals("Type");
        }

        return isVoid;
    }

    public static String getFirst(String computation){
        int index = computation.indexOf(":=");
        String firstPart = computation.substring(0, index).trim();
        return firstPart;
    }

    public static String removeType(String computation){
        int index = computation.indexOf(".");
        String firstPart = computation.substring(0, index).trim();
        return firstPart;
    }

    public static String removeArray(String computation){
        return computation.replace(".array", "").trim();
    }


    public static String getClassName(String importStatement){

        if (importStatement.contains(".")) {
            return importStatement.substring(importStatement.lastIndexOf(".") + 1);
        } else {
            return importStatement;
        }
    }


}
