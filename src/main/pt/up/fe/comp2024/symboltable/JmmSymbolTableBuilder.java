package pt.up.fe.comp2024.symboltable;

import org.junit.Test;
import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.TypeUtils;
import pt.up.fe.specs.util.SpecsCheck;

import javax.lang.model.type.NullType;
import java.util.*;

import static jdk.jshell.Snippet.Kind.IMPORT;
import static pt.up.fe.comp2024.ast.Kind.*;

public class JmmSymbolTableBuilder {


    public static JmmSymbolTable build(JmmNode root) {

        var imports = buildImports(root);
        var classDecl = root.getChildren(CLASS_DECL).get(0);
        SpecsCheck.checkArgument(Kind.CLASS_DECL.check(classDecl), () -> "Expected a class declaration: " + classDecl);
        String className = classDecl.get("className");
        String superName = "";
        if (classDecl.hasAttribute("superclassName")) superName = classDecl.get("superclassName");

        var fields = buildFields(classDecl);
        var methods = buildMethods(classDecl);
        var returnTypes = buildReturnTypes(classDecl);
        var params = buildParams(classDecl);
        var locals = buildLocals(classDecl);


        return new JmmSymbolTable(imports, className, superName, fields, methods, returnTypes, params, locals);
    }

    private static List<String> buildImports(JmmNode root) {

        return root.getChildren(IMPORT_DECL).stream()
                .map(importDecl -> Arrays.stream(importDecl.get("value").replaceAll("[\\[\\]]", "").split(", "))
                        .map(String::trim)
                        .reduce((first, second) -> second)
                        .orElse(""))
                .toList();
    }


    private static Map<String, Type> buildReturnTypes(JmmNode classDecl) {
        // TODO: Simple implementation that needs to be expanded

        Map<String, Type> map = new HashMap<>();

        classDecl.getChildren(METHOD_DECL).stream()
                .forEach(method -> map.put(method.get("name"), TypeUtils.getType(method)));


        return map;
    }

    private static Map<String, List<Symbol>> buildParams(JmmNode classDecl) {
        // TODO: Simple implementation that needs to be expanded

        Map<String, List<Symbol>> map = new HashMap<>();

        classDecl.getChildren(METHOD_DECL).stream()
                .forEach(method -> map.put(method.get("name"),method.getChildren(PARAM).stream().map(param-> new Symbol(TypeUtils.getType(param), param.get("name"))).toList()));

        return map;
    }

    private static Map<String, List<Symbol>> buildLocals(JmmNode classDecl) {
        // TODO: Simple implementation that needs to be expanded

        Map<String, List<Symbol>> map = new HashMap<>();


        classDecl.getChildren(METHOD_DECL).stream()
                .forEach(method -> map.put(method.get("name"), getLocalsList(method)));

        return map;
    }

    private static List<String> buildMethods(JmmNode classDecl) {


        return classDecl.getChildren(METHOD_DECL).stream()
                .map(method -> method.get("name"))
                .toList();
    }

    private static List<Symbol> buildFields(JmmNode classDecl) {
        // TODO: Simple implementation that needs to be expanded


        return classDecl.getChildren(VAR_DECL).stream()
                .map(varDecl -> new Symbol(TypeUtils.getType(varDecl), varDecl.get("name")))
                .toList();
    }

    private static List<Symbol> getLocalsList(JmmNode methodDecl) {
        // TODO: Simple implementation that needs to be expanded

        return methodDecl.getChildren(VAR_DECL).stream()
                .map(varDecl -> new Symbol(TypeUtils.getType(varDecl), varDecl.get("name")))
                .toList();
    }

}
