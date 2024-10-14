package pt.up.fe.comp2024.analysis.passes;

import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp.jmm.analysis.table.Symbol;
import pt.up.fe.comp.jmm.analysis.table.SymbolTable;
import pt.up.fe.comp.jmm.analysis.table.Type;
import pt.up.fe.comp.jmm.ast.JmmNode;
import pt.up.fe.comp.jmm.report.Report;
import pt.up.fe.comp.jmm.report.Stage;
import pt.up.fe.comp2024.analysis.AnalysisVisitor;
import pt.up.fe.comp2024.ast.Kind;
import pt.up.fe.comp2024.ast.NodeUtils;
import pt.up.fe.comp2024.ast.TypeUtils;
import pt.up.fe.specs.util.SpecsCheck;

public class InvalidUseOfThis  extends AnalysisVisitor {

    boolean inMainMethod = false;
    private String currentMethod;

    @Override
    public void buildVisitor() {
        addVisit(Kind.METHOD_DECL, this::visitMethodDecl);
        addVisit(Kind.THIS, this::visitThisUsage);

    }

    private Void visitMethodDecl(JmmNode method, SymbolTable table) {

        if (method.get("isMainMethod").equals("true")) inMainMethod = true;
        else  inMainMethod = false;

        currentMethod = method.get("name");

        return null;

    }

    private Void visitThisUsage(JmmNode statement, SymbolTable table) {

        if (inMainMethod==true){

            var message = String.format("this cannot be used in a static method");
            addReport(Report.newError(
                    Stage.SEMANTIC,
                    NodeUtils.getLine(statement),
                    NodeUtils.getColumn(statement),
                    message,
                    null)
            );

        }



        return null;

    }

}
