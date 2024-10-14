// Generated from comp2024/grammar/Javamm.g4 by ANTLR 4.5.3

    package pt.up.fe.comp2024;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JavammParser}.
 */
public interface JavammListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JavammParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(JavammParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(JavammParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void enterImportDecl(JavammParser.ImportDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#importDecl}.
	 * @param ctx the parse tree
	 */
	void exitImportDecl(JavammParser.ImportDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void enterClassDecl(JavammParser.ClassDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#classDecl}.
	 * @param ctx the parse tree
	 */
	void exitClassDecl(JavammParser.ClassDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void enterVarDecl(JavammParser.VarDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#varDecl}.
	 * @param ctx the parse tree
	 */
	void exitVarDecl(JavammParser.VarDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(JavammParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(JavammParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void enterMethodDecl(JavammParser.MethodDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#methodDecl}.
	 * @param ctx the parse tree
	 */
	void exitMethodDecl(JavammParser.MethodDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#mainMethod}.
	 * @param ctx the parse tree
	 */
	void enterMainMethod(JavammParser.MainMethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#mainMethod}.
	 * @param ctx the parse tree
	 */
	void exitMainMethod(JavammParser.MainMethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavammParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(JavammParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavammParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(JavammParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(JavammParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(JavammParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BracketsStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterBracketsStmt(JavammParser.BracketsStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BracketsStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitBracketsStmt(JavammParser.BracketsStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(JavammParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(JavammParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(JavammParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(JavammParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SimpleStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStmt(JavammParser.SimpleStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SimpleStmt}
	 * labeled alternative in {@link JavammParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStmt(JavammParser.SimpleStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenthExpr(JavammParser.ParenthExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenthExpr(JavammParser.ParenthExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(JavammParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(JavammParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(JavammParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(JavammParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ObjectDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterObjectDecl(JavammParser.ObjectDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ObjectDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitObjectDecl(JavammParser.ObjectDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArraySub}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArraySub(JavammParser.ArraySubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArraySub}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArraySub(JavammParser.ArraySubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MethodCall}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(JavammParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MethodCall}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(JavammParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewMethodDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewMethodDecl(JavammParser.NewMethodDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewMethodDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewMethodDecl(JavammParser.NewMethodDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Not}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNot(JavammParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Not}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNot(JavammParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Length}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLength(JavammParser.LengthContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Length}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLength(JavammParser.LengthContext ctx);
	/**
	 * Enter a parse tree produced by the {@code VarRefExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterVarRefExpr(JavammParser.VarRefExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code VarRefExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitVarRefExpr(JavammParser.VarRefExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NewArrayDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayDecl(JavammParser.NewArrayDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NewArrayDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayDecl(JavammParser.NewArrayDeclContext ctx);
	/**
	 * Enter a parse tree produced by the {@code This}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterThis(JavammParser.ThisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code This}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitThis(JavammParser.ThisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(JavammParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(JavammParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntegerLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(JavammParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerLiteral}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(JavammParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpr(JavammParser.BooleanExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanExpr}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpr(JavammParser.BooleanExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterArrayDecl(JavammParser.ArrayDeclContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayDecl}
	 * labeled alternative in {@link JavammParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitArrayDecl(JavammParser.ArrayDeclContext ctx);
}