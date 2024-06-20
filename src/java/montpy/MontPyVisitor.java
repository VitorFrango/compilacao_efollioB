// Generated from MontPy.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MontPyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MontPyVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MontPyParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MontPyParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#listType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListType(MontPyParser.ListTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MontPyParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#logicalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpression(MontPyParser.LogicalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(MontPyParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpression(MontPyParser.ComparisonExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(MontPyParser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(MontPyParser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#powerExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowerExpression(MontPyParser.PowerExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(MontPyParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MontPyParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MontPyParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#listFunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListFunctionCall(MontPyParser.ListFunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(MontPyParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#baseAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseAssignment(MontPyParser.BaseAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(MontPyParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MontPyParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MontPyParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#simpleStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleStatement(MontPyParser.SimpleStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#statementBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBlock(MontPyParser.StatementBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MontPyParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#printStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintStatement(MontPyParser.PrintStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#inputStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInputStatement(MontPyParser.InputStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MontPyParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MontPyParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MontPyParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MontPyParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(MontPyParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#typeConversion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeConversion(MontPyParser.TypeConversionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MontPyParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MontPyParser.ProgramContext ctx);
}