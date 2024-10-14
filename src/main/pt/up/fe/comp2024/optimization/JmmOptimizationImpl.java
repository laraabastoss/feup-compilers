package pt.up.fe.comp2024.optimization;

import pt.up.fe.comp.jmm.analysis.JmmSemanticsResult;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.ollir.JmmOptimization;
import pt.up.fe.comp.jmm.ollir.OllirResult;
import pt.up.fe.comp2024.analysis.passes.*;


import org.specs.comp.ollir.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JmmOptimizationImpl implements JmmOptimization {

    @Override
    public OllirResult toOllir(JmmSemanticsResult semanticsResult) {

        JmmSemanticsResult newSemanticsResult = optimize(semanticsResult);

        var visitor = new OllirGeneratorVisitor(semanticsResult.getSymbolTable());
        var ollirCode = visitor.visit(semanticsResult.getRootNode());

        OllirResult newOllir = optimize(new OllirResult(newSemanticsResult, ollirCode, Collections.emptyList()));

        return newOllir;


    }

    @Override
    public OllirResult optimize(OllirResult ollirResult) {
        if (ollirResult.getConfig().get("registerAllocation")==null || ollirResult.getConfig().get("registerAllocation").equals("-1")  ) return ollirResult;

        System.out.println("OIOIOI");


        RegisterAllocationVisitor registerAllocationVisitor = new RegisterAllocationVisitor(ollirResult, ollirResult.getConfig().get("registerAllocation"));
        return ollirResult;
    }
    @Override
    public JmmSemanticsResult optimize(JmmSemanticsResult semanticsResult) {

        if (semanticsResult.getConfig().get("optimize")==null) return semanticsResult;

        boolean constPropagationOpt = true;
        boolean constFoldingOpt = true;


        while (constPropagationOpt ||constFoldingOpt ){

            ConstantPropagationVisitor constantPropagationVisitor = new ConstantPropagationVisitor();
            constantPropagationVisitor.visit(semanticsResult.getRootNode());
            ConstantFoldingVisitor.ConstantFoldingVisitor(semanticsResult);

            constPropagationOpt = ConstantPropagationVisitor.wasOptimized();

            constFoldingOpt = ConstantFoldingVisitor.wasOptimized();
         }

        return semanticsResult;

    }

}
