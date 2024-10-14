package pt.up.fe.comp2024.ast;

import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.Kind;

import java.util.List;

import static pt.up.fe.comp2024.ast.Kind.METHOD_DECL;

public class TypeUtils {

    private static final String INT_TYPE_NAME = "int";
    private static final String BOOLEAN_TYPE_NAME = "boolean";
    private static final String STRING_TYPE_NAME = "String";
    private static final String VOID_TYPE_NAME = "void";
    private static final String ELLIPSIS_TYPE_NAME = "int";

    public static String getIntTypeName() {
        return INT_TYPE_NAME;
    }

    public static String getBooleanTypeName() {
        return BOOLEAN_TYPE_NAME;
    }
    public static String getStringTypeName() {
        return STRING_TYPE_NAME;
    }
    public static String getVoidTypeName() {
        return VOID_TYPE_NAME;
    }
    public static String getEllipsisTypeName() {
        return ELLIPSIS_TYPE_NAME;
    }



    public static Type getType(JmmNode curr) {



       if (curr.hasAttribute("isMainMethod") && curr.get("isMainMethod").equals("true")){
           return new Type(TypeUtils.getVoidTypeName(), false);
        }
        String type = curr.getChildren(Kind.TYPE).get(0).get("name");

        if (type.equals("int") && curr.getChildren(Kind.TYPE).get(0).get("isArray").equals("true") && curr.getChildren(Kind.TYPE).get(0).get("isVarArgs").equals("false")) {
            return new Type(TypeUtils.getIntTypeName(), true);
        } else if (curr.getChildren(Kind.TYPE).get(0).get("isVarArgs").equals( "true")) {
            return new Type(TypeUtils.getEllipsisTypeName(), true);
        } else if (type.equals("int") ) {
            return new Type(TypeUtils.getIntTypeName(), false);
        } else if (type.equals("boolean") ) {
            return new Type(TypeUtils.getBooleanTypeName(), false);
        } else if (type.equals("String") ) {
            return new Type(TypeUtils.getStringTypeName(), false);
        } else if (type.equals("main") || type.equals("length") ) {
            return new Type(curr.getChildren(Kind.TYPE).get(0).get("name"), false);
        } else if (type.equals("void")) {
            return new Type(TypeUtils.getVoidTypeName(), false);
        }
        else {
            return new Type(curr.getChildren(Kind.TYPE).get(0).get("name"), false);
        }

    }




    /**
     * Gets the {@link Type} of an arbitrary expression.
     *
     * @param expr
     * @param table
     * @return
     */
    public static Type getExprType(JmmNode expr, SymbolTable table) {
        // TODO: Simple implementation that needs to be expanded

        var kind = Kind.fromString(expr.getKind());

        Type type = switch (kind) {
            case BINARY_EXPR -> getBinExprType(expr);
            case VAR_REF_EXPR -> getVarExprType(expr, table);
            case INTEGER_LITERAL -> new Type(INT_TYPE_NAME, false);
            case ARRAY_SUB -> new Type(INT_TYPE_NAME, true);
            case ARRAY_DECL -> new Type(INT_TYPE_NAME, true);
            default -> throw new UnsupportedOperationException("Can't compute type for expression kind '" + kind + "'");
        };

        return type;
    }

    private static Type getBinExprType(JmmNode binaryExpr) {
        // TODO: Simple implementation that needs to be expanded

        String operator = binaryExpr.get("op");

        return switch (operator) {
            case "+", "*", "/", "-", "&&", "||" -> new Type(INT_TYPE_NAME, false);
            default ->
                    throw new RuntimeException("Unknown operator '" + operator + "' of expression '" + binaryExpr + "'");
        };
    }


    private static Type getVarExprType(JmmNode varRefExpr, SymbolTable table) {
        var currentMethodOptional = varRefExpr.getAncestor(Kind.METHOD_DECL);

        JmmNode currentMethod;
        if (currentMethodOptional.isPresent()) {
            currentMethod = currentMethodOptional.get();
        } else {
            throw new RuntimeException("Variable reference outside of a method");
        }

        var varName = varRefExpr.get("name");
        String currentMethodName = currentMethod.get("name");

        // Search for the variable in the local variables
        var locals = table.getLocalVariables(currentMethodName);
        var type = locals.stream()
                .filter(symbol -> symbol.getName().equals(varName))
                .map(Symbol::getType)
                .findFirst();

        if (type.isPresent()) {
            return type.get();
        }

        // Search for the variable in the parameters
        var params = table.getParameters(currentMethodName);
        var paramType = params.stream()
                .filter(symbol -> symbol.getName().equals(varName))
                .map(Symbol::getType)
                .findFirst();

        if (paramType.isPresent()) {
            return paramType.get();
        }

        // Search for the variable in the fields
        var fields = table.getFields();
        var fieldType = fields.stream()
                .filter(symbol -> symbol.getName().equals(varName))
                .map(Symbol::getType)
                .findFirst();

        if (fieldType.isPresent()) {
            return fieldType.get();
        }

        // Search in imports
        var imports = table.getImports();
        var importType = imports.stream()
                .filter(name -> {
                    var parts = name.replaceAll("\\[|]", "").split("\\.");
                    return parts[parts.length - 1].equals(varName);
                })
                .map(name -> new Type("", false))
                .findFirst();

        if (importType.isPresent()) {
            return importType.get();
        }

        throw new RuntimeException("Variable '" + varName + "' not found in the local variables, parameters or fields");
}


    /**
     * @param sourceType
     * @param destinationType
     * @return true if sourceType can be assigned to destinationType
     */
    public static boolean areTypesAssignable(Type sourceType, Type destinationType) {
        // TODO: Simple implementation that needs to be expanded
        return sourceType.getName().equals(destinationType.getName());
    }
}
