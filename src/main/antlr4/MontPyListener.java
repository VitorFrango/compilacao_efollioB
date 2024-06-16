// Generated from MontPy.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MontPyParser}.
 */
public interface MontPyListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MontPyParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MontPyParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MontPyParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#listType}.
	 * @param ctx the parse tree
	 */
	void enterListType(MontPyParser.ListTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#listType}.
	 * @param ctx the parse tree
	 */
	void exitListType(MontPyParser.ListTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MontPyParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MontPyParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#logicalExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpression(MontPyParser.LogicalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#logicalExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpression(MontPyParser.LogicalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(MontPyParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#equalityExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(MontPyParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpression(MontPyParser.ComparisonExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpression(MontPyParser.ComparisonExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpression(MontPyParser.AdditiveExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#additiveExpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpression(MontPyParser.AdditiveExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpression(MontPyParser.MultiplicativeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpression(MontPyParser.MultiplicativeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#powerExpression}.
	 * @param ctx the parse tree
	 */
	void enterPowerExpression(MontPyParser.PowerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#powerExpression}.
	 * @param ctx the parse tree
	 */
	void exitPowerExpression(MontPyParser.PowerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpression(MontPyParser.UnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#unaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpression(MontPyParser.UnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(MontPyParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(MontPyParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MontPyParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MontPyParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#listFunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterListFunctionCall(MontPyParser.ListFunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#listFunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitListFunctionCall(MontPyParser.ListFunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(MontPyParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(MontPyParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#baseAssignment}.
	 * @param ctx the parse tree
	 */
	void enterBaseAssignment(MontPyParser.BaseAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#baseAssignment}.
	 * @param ctx the parse tree
	 */
	void exitBaseAssignment(MontPyParser.BaseAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(MontPyParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(MontPyParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(MontPyParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(MontPyParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MontPyParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MontPyParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void enterSimpleStatement(MontPyParser.SimpleStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#simpleStatement}.
	 * @param ctx the parse tree
	 */
	void exitSimpleStatement(MontPyParser.SimpleStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void enterStatementBlock(MontPyParser.StatementBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void exitStatementBlock(MontPyParser.StatementBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MontPyParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MontPyParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void enterPrintStatement(MontPyParser.PrintStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#printStatement}.
	 * @param ctx the parse tree
	 */
	void exitPrintStatement(MontPyParser.PrintStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void enterInputStatement(MontPyParser.InputStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#inputStatement}.
	 * @param ctx the parse tree
	 */
	void exitInputStatement(MontPyParser.InputStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(MontPyParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(MontPyParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(MontPyParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(MontPyParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MontPyParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MontPyParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MontPyParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MontPyParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(MontPyParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(MontPyParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#typeConversion}.
	 * @param ctx the parse tree
	 */
	void enterTypeConversion(MontPyParser.TypeConversionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#typeConversion}.
	 * @param ctx the parse tree
	 */
	void exitTypeConversion(MontPyParser.TypeConversionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MontPyParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MontPyParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MontPyParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MontPyParser.ProgramContext ctx);
}