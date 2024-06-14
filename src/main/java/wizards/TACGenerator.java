package wizards;

import montpy.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.InputStream;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.EnumSet;

import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

// Classe que converte código TAC para Assembly
class TACToAssemblyConverter {
    private List<String> assemblyCode;
    private Map<String, String> registerMap; // Map for variable to register
    private int registerCounter;

    public TACToAssemblyConverter() {
        assemblyCode = new ArrayList<>();
        registerMap = new HashMap<>();
        registerCounter = 0;
    }

    // Converte o código TAC para Assembly
    public List<String> convert(Map<String, List<String>> functionsTAC, List<String> globalTAC) {
        // Convert global TAC
        assemblyCode.add("_entry:");
        for (String instruction : globalTAC) {
            convertInstruction(instruction);
        }

        // Convert functions TAC
        for (Map.Entry<String, List<String>> entry : functionsTAC.entrySet()) {
            assemblyCode.add("func_" + entry.getKey() + ":");
            for (String instruction : entry.getValue()) {
                convertInstruction(instruction);
            }
        }

        return assemblyCode;
    }

    // Converte uma instrução TAC para Assembly
    private void convertInstruction(String instruction) {
        if (instruction == null || instruction.trim().isEmpty()) {
            System.err.println("Instrução vazia ou nula recebida.");
            return;
        }
        String[] parts = instruction.trim().split("\\s+");
        if (parts.length < getExpectedParts(parts[0])) {
            System.err.println("Erro: Instrução incompleta ou malformada: " + instruction);
            return;
        }

        switch (parts[0]) {
            case "goto":
                assemblyCode.add("JMP " + parts[1]);
                break;
            case "if_not":
                String condition = parts[1];
                String label = parts[3];
                assemblyCode.add("CMP " + getRegister(condition) + ", 0");
                assemblyCode.add("JE " + label);
                break;
            case "list_new":
                assemblyCode.add("CALL malloc");
                assemblyCode.add("MOV " + getRegister(parts[1]) + ", EAX");
                break;
            case "call":
                assemblyCode.add("CALL " + parts[2]);
                if (parts.length > 3) {
                    assemblyCode.add("MOV " + getRegister(parts[1]) + ", EAX");
                }
                break;
            case "return":
                if (parts.length > 1) {
                    assemblyCode.add("MOV EAX, " + getRegister(parts[1]));
                }
                assemblyCode.add("RET");
                break;
            case "list_size":
                assemblyCode.add("MOV " + getRegister(parts[0]) + ", [" + getRegister(parts[2]) + ".size]");
                break;
            case "list_add":
                assemblyCode.add("MOV [" + getRegister(parts[1]) + " + " + getRegister(parts[2]) + "], " + getRegister(parts[3]));
                break;
            case "push_param":
                assemblyCode.add("PUSH " + getRegister(parts[1]));
                break;
            case "print":
                assemblyCode.add("CALL print");
                break;
            case "begin_function":
                assemblyCode.add("BEGIN_FUNCTION " + parts[1]);
                break;
            case "end_function":
                assemblyCode.add("END_FUNCTION");
                break;
            default:
                handleDefaultCase(parts, instruction);
                break;
        }
    }

    private int getExpectedParts(String instructionType) {
        switch (instructionType) {
            case "goto":
            case "call":
            case "return":
            case "push_param":
            case "print":
                return 2;
            case "if_not":
                return 4;
            case "list_new":
                return 3;
            case "list_size":
            case "list_add":
                return 4;
            case "begin_function":
                return 2;
            case "end_function":
                return 1;
            default:
                return 2; // Valor padrão para a maioria das instruções
        }
    }

    private void handleDefaultCase(String[] parts, String instruction) {
        if (parts.length > 1 && parts[1].equals("=")) {
            if (parts.length == 3) {
                assemblyCode.add("MOV " + getRegister(parts[0]) + ", " + getRegister(parts[2]));
            } else if (parts.length == 5) {
                String op = convertOperator(parts[3]);
                assemblyCode.add(op + " " + getRegister(parts[0]) + ", " + getRegister(parts[2]) + ", " + getRegister(parts[4]));
            } else {
                System.err.println("Instrução de atribuição malformada: " + instruction);
            }
        } else if (parts.length == 1 && parts[0].endsWith(":")) {
            assemblyCode.add(parts[0]);
        } else {
            System.err.println("Instrução desconhecida ou malformada: " + instruction);
        }
    }

    private String getRegister(String variable) {
        return registerMap.computeIfAbsent(variable, k -> "R" + (registerCounter++));
    }

    private String convertOperator(String op) {
        switch (op) {
            case "+":
                return "ADD";
            case "-":
                return "SUB";
            case "*":
                return "IMUL";
            case "/":
                return "IDIV";
            case "!=":
                return "CMP";
            case "<":
                return "JL";
            case ">":
                return "JG";
            case "<=":
                return "JLE";
            case ">=":
                return "JGE";
            default:
                throw new IllegalArgumentException("Operador desconhecido: " + op);
        }
    }

    public List<String> getAssemblyCode() {
        return assemblyCode;
    }
}

// Definição do ValueType e ExprType
enum ValueType {
    INT,
    FLOAT,
    LIST_INT,
    LIST_FLOAT,
    STRING;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    public String constantOf(double value) {
        if (this == FLOAT)
            return String.valueOf(value);
        return String.valueOf((int)value);
    }
}

record ExprType(String id, ValueType type) {}

// Classe que gera o código TAC a partir de um código-fonte MontPy
public class TACGenerator extends MontPyBaseVisitor<ExprType> {
    private Map<String, List<String>> functionsTAC;
    private List<String> globalTAC;
    private Deque<List<String>> context;
    private int temporaryCounter = 0;
    private Deque<Map<String, ValueType>> symbolTable;
    private Map<String, List<ValueType>> functionTable;
    private ValueType variableValueType; // Used in variable creation
    private int labelCounter = 0;

    // Labels for control flow statements
    private static final String VAR_SIZE_BYTES = "4";

    private static final Set<String> makeReservedWords(Vocabulary voc) {
        Set<String> reserved = new HashSet<>();
        Pattern pattern = Pattern.compile("\\w+");

        for (int i = 0; i < voc.getMaxTokenType(); i++) {
            String word = voc.getLiteralName(i);
            if (word == null)
                continue;
            Matcher match = pattern.matcher(word);
            if (!match.find())
                continue;
            word = match.group();
            if (!word.isEmpty())
                reserved.add(word);
        }
        return reserved;
    }

    private static final Set<String> RESERVED_WORDS = makeReservedWords(MontPyParser.VOCABULARY);
    private static final Set<ValueType> LIST_TYPES = EnumSet.of(ValueType.LIST_FLOAT, ValueType.LIST_INT);
    private static final Set<ValueType> NUMERIC_TYPES = EnumSet.of(ValueType.INT, ValueType.FLOAT);
    private static List<String> INTEGER_OPS = List.of("//", "%");

    public void generateTACFromSource(InputStream source) throws Exception {
        CharStream input = CharStreams.fromStream(source);
        MontPyLexer lexer = new MontPyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MontPyParser parser = new MontPyParser(tokens);
        ParseTree tree = parser.program();

        symbolTable = new ArrayDeque<>();
        functionTable = new HashMap<>();
        functionsTAC = new HashMap<>();
        globalTAC = new ArrayList<>();
        context = new ArrayDeque<>();
        temporaryCounter = 0;
        labelCounter = 0;

        context.addFirst(globalTAC);
        symbolTable.addFirst(new HashMap<>());

        addLabel("_entry");
        visit(tree);
    }

    public List<String> getGlobalTAC() {
        return globalTAC;
    }

    public Map<String, List<String>> getFunctionsTAC() {
        return functionsTAC;
    }

    private ExprType newTemp(ValueType type) {
        return new ExprType("t" + temporaryCounter++, type);
    }

    private String nextLabel() {
        return "_L" + (labelCounter++);
    }

    private boolean hasSymbol(String id) {
        for (var localSym : symbolTable) {
            if (localSym.containsKey(id))
                return true;
        }
        return false;
    }

    private void SyntaxError(int linha, String msg, Object ...args) {
        throw new RuntimeException(String.format("Erro Sintaxe na linha " + String.valueOf(linha) + ":\n\t" + msg, args));
    }

    private ValueType getSymbolType(String id) {
        for (var localSym : symbolTable) {
            if (localSym.containsKey(id))
                return localSym.get(id);
        }
        return null;
    }

    private void addTAC(String format, Object... args) {
        context.peekFirst().add(String.format(Locale.ROOT, "\t" + format, args));
    }

    private void instructionOP(String result, String left, String op, String right) {
        addTAC("%s = %s %s %s", result, left, op, right);
    }

    private void instructionAssign(String lhs, String rhs) {
        addTAC("%s = %s", lhs, rhs);
    }

    private void instructionConditional(String cond, String label) {
        addTAC("if_not %s goto %s", cond, label);
    }

    private void instructionGoto(String label) {
        addTAC("goto %s", label);
    }

    private void instructionPush(String param) {
        addTAC("push_param %s", param);
    }

    private void instructionListSize(String result, String list) {
        addTAC("%s = list_size %s", result, list);
    }

    private void instructionListAdd(String list, String pos, String value) {
        addTAC("list_add %s %s %s", list, pos, value);
    }

    private void instructionListRemove(String list, String pos) {
        addTAC("list_remove %s %s", list, pos);
    }

    private void addLabel(String name) {
        context.peekFirst().add(name + ":");
    }

    private String accessArrayPos(String id, String pos) {
        ExprType offset = newTemp(ValueType.INT);
        ExprType pointer = newTemp(ValueType.INT);
        instructionOP(offset.id(), pos, "*", VAR_SIZE_BYTES);
        instructionOP(pointer.id(), id, "+", offset.id());
        return pointer.id();
    }

    private ExprType convertTo(ExprType expr, ValueType type) {
        if (!NUMERIC_TYPES.contains(expr.type()))
            return null;
        if (expr.type() == type)
            return expr;
        var result = newTemp(type);
        addTAC("%s = to_%s %s", result.id(), type, expr.id());
        return result;
    }

    private ExprType convertToFloat(ExprType expr) {
        return convertTo(expr, ValueType.FLOAT);
    }

    private ExprType convertToInt(ExprType expr) {
        return convertTo(expr, ValueType.INT);
    }

    private ExprType doBinaryExpression(ParserRuleContext ctx) {
        ExprType left = visit(ctx.getChild(0));
        if (ctx.getChildCount() == 3) {
            String op = ctx.getChild(1).getText();
            ExprType right = visit(ctx.getChild(2));
            if (!NUMERIC_TYPES.contains(left.type()))
                SyntaxError(ctx.getStart().getLine(), "Operação %s não suportada, operando %s não numérico (tipo %s).", op, left.id(), left.type());
            if (!NUMERIC_TYPES.contains(right.type()))
                SyntaxError(ctx.getStart().getLine(), "Operação %s não suportada, operando %s não numérico (tipo %s).", op, right.id(), right.type());
            if (INTEGER_OPS.contains(op)) {
                if (left.type() != ValueType.INT)
                    SyntaxError(ctx.getStart().getLine(), "Operação %s não suportada, operando %s não inteiro (tipo %s).", op, left.id(), left.type());
                if (right.type() != ValueType.INT)
                    SyntaxError(ctx.getStart().getLine(), "Operação %s não suportada, operando %s não inteiro (tipo %s).", op, right.id(), right.type());
            }
            if (left.type() != right.type() || op.equals("/")) {
                left = convertToFloat(left);
                right = convertToFloat(right);
            }
            var result = newTemp(left.type());
            instructionOP(result.id(), left.id(), op, right.id());
            return result;
        }
        return left;
    }

    // Implement visit methods for each statement type
    @Override
    public ExprType visitLogicalExpression(MontPyParser.LogicalExpressionContext ctx) {
        return doBinaryExpression(ctx);
    }

    @Override
    public ExprType visitEqualityExpression(MontPyParser.EqualityExpressionContext ctx) {
        return doBinaryExpression(ctx);
    }

    @Override
    public ExprType visitComparisonExpression(MontPyParser.ComparisonExpressionContext ctx) {
        return doBinaryExpression(ctx);
    }

    @Override
    public ExprType visitAdditiveExpression(MontPyParser.AdditiveExpressionContext ctx) {
        return doBinaryExpression(ctx);
    }

    @Override
    public ExprType visitMultiplicativeExpression(MontPyParser.MultiplicativeExpressionContext ctx) {
        return doBinaryExpression(ctx);
    }

    @Override
    public ExprType visitPowerExpression(MontPyParser.PowerExpressionContext ctx) {
        return doBinaryExpression(ctx);
    }

    @Override
    public ExprType visitUnaryExpression(MontPyParser.UnaryExpressionContext ctx) {
        var primary = visit(ctx.primary());
        if (ctx.MINUS() != null) {
            var negative = newTemp(primary.type());
            instructionOP(negative.id(), negative.type().constantOf(0.0), "-", primary.id());
            return negative;
        }
        return primary;
    }

    @Override
    public ExprType visitPrimary(MontPyParser.PrimaryContext ctx) {
        if (ctx.functionCall() != null) {
            return visit(ctx.functionCall());
        }
        if (ctx.OPEN_PAREN() != null) {
            return visit(ctx.expression());
        }
        var literal = ctx.literal();
        if (literal != null) {
            if (literal.STRING() != null) {
                return new ExprType(literal.getText(), ValueType.STRING);
            } else if (literal.INT() != null) {
                return new ExprType(Integer.valueOf(literal.getText()).toString(), ValueType.INT);
            } else {
                return new ExprType(Double.valueOf(literal.getText()).toString(), ValueType.FLOAT);
            }
        }
        var id = ctx.ID();
        if (id != null) {
            String id_str = id.getText();
            if (!hasSymbol(id_str))
                SyntaxError(ctx.getStart().getLine(), "Identificador %s não existe.", id_str);
            if (ctx.OPEN_BRACKET() != null) {
                ExprType pos = visit(ctx.expression());
                String pointer = accessArrayPos(id_str, pos.id());
                ExprType result = switch (getSymbolType(id_str)) {
                    case LIST_FLOAT -> newTemp(ValueType.FLOAT);
                    case LIST_INT -> newTemp(ValueType.INT);
                    default -> {
                        SyntaxError(ctx.getStart().getLine(), "Variável %s não é lista.", id_str);
                        yield null;
                    }
                };
                instructionAssign(result.id(), "*" + pointer);
                return result;
            }
            return new ExprType(id_str, getSymbolType(id_str));
        }
        return visit(ctx.getChild(0));
    }

    @Override
    public ExprType visitListFunctionCall(MontPyParser.ListFunctionCallContext ctx) {
        String id = ctx.ID().getText();
        if (!hasSymbol(id))
            SyntaxError(ctx.getStart().getLine(), "Identificador %s não existe.", id);
        String function_name = ctx.getChild(0).getText();
        ValueType list_type = getSymbolType(id);
        if (!LIST_TYPES.contains(list_type))
            SyntaxError(ctx.getStart().getLine(), "Função %s sobre variável %s não lista.", function_name, id);
        if (ctx.SIZE() != null) {
            ExprType result = newTemp(ValueType.INT);
            instructionListSize(result.id(), id);
            return result;
        }
        if (ctx.ADD() != null) {
            var parameters = ctx.expression();
            if (parameters.size() != 2)
                SyntaxError(ctx.getStart().getLine(), "Erro na chamada da função %s da lista %s.", function_name, id);
            ExprType pos = visit(parameters.get(0));
            ExprType element = visit(parameters.get(1));
            if (pos.type() != ValueType.INT)
                SyntaxError(ctx.getStart().getLine(), "Parâmetro pos da função %s não é inteiro.", function_name);
            if (list_type == ValueType.LIST_INT && element.type() != ValueType.INT)
                SyntaxError(ctx.getStart().getLine(), "Elemento %s não é inteiro.", element.id());
            if (list_type == ValueType.LIST_FLOAT)
                element = convertToFloat(element);
            instructionListAdd(id, pos.id(), element.id());
        } else {
            var parameters = ctx.expression();
            if (parameters.size() != 1)
                SyntaxError(ctx.getStart().getLine(), "Erro na chamada da função %s da lista %s.", function_name, id);
            var pos = visit(parameters.get(0));
            if (pos.type() != ValueType.INT)
                SyntaxError(ctx.getStart().getLine(), "Parâmetro pos da função %s não é inteiro.", function_name);
            instructionListRemove(id, pos.id());
        }
        return null;
    }

    @Override
    public ExprType visitFunctionCall(MontPyParser.FunctionCallContext ctx) {
        String function_name = ctx.ID().getText();
        if (!functionTable.containsKey(function_name))
            SyntaxError(ctx.getStart().getLine(), "Função %s não declarada.", function_name);
        var parameters = ctx.expression();
        List<ValueType> function_types = functionTable.get(function_name);
        for (int i = parameters.size(); i > 0; i--) {
            ExprType expr = visit(parameters.get(i - 1));
            if (function_types.get(i) == ValueType.FLOAT) {
                expr = convertToFloat(expr);
            } else if (expr.type() != function_types.get(i)) {
                SyntaxError(ctx.getStart().getLine(), "Parâmetro %d da função %s requer tipo %s, tipo da expressão é %s.", i - 1,
                        function_name, function_types.get(i), expr.type());
            }
            instructionPush(expr.id());
        }
        var result = newTemp(function_types.get(0));
        addTAC("%s = call func_%s", result.id(), function_name);
        return result;
    }

    @Override
    public ExprType visitVariableDeclaration(MontPyParser.VariableDeclarationContext ctx) {
        if (ctx.type() != null) {
            visit(ctx.type());
            for (var assignment : ctx.baseAssignment()) {
                ExprType assigned = visit(assignment);
                symbolTable.peekFirst().put(assigned.id(), assigned.type());
            }
        } else {
            String str_id = ctx.ID().getText();
            if (ctx.listType() != null) {
                visit(ctx.listType());
            } else if (hasSymbol(str_id)) {
                variableValueType = getSymbolType(str_id);
            } else {
                variableValueType = ValueType.LIST_FLOAT;
            }
            ExprType variable = new ExprType(str_id, variableValueType);
            if (RESERVED_WORDS.contains(variable.id()))
                SyntaxError(ctx.getStart().getLine(), "Uso de palavra reservada %s.", variable.id());
            var expr = ctx.expression();
            addTAC("%s = list_new %d", variable.id(), expr.size());
            for (int i = 0; i < expr.size(); i++) {
                ExprType result = visit(expr.get(i));
                if (variable.type() == ValueType.LIST_FLOAT) {
                    result = convertToFloat(result);
                } else if (result.type() == ValueType.FLOAT)
                    SyntaxError(ctx.getStart().getLine(), "Atribuir float a array de inteiros em %d.\n");
                String pos = accessArrayPos(variable.id(), String.valueOf(i));
                instructionAssign("*" + pos, result.id());
            }
            symbolTable.peekFirst().put(variable.id(), variable.type());
        }
        return null;
    }

    @Override
    public ExprType visitType(MontPyParser.TypeContext ctx) {
        if (ctx.FLOAT_TYPE() != null) {
            variableValueType = ValueType.FLOAT;
        } else {
            variableValueType = ValueType.INT;
        }
        return null;
    }

    @Override
    public ExprType visitListType(MontPyParser.ListTypeContext ctx) {
        visit(ctx.type());
        if (variableValueType == ValueType.FLOAT) {
            variableValueType = ValueType.LIST_FLOAT;
        } else {
            variableValueType = ValueType.LIST_INT;
        }
        return null;
    }

    @Override
    public ExprType visitBaseAssignment(MontPyParser.BaseAssignmentContext ctx) {
        ExprType variable = new ExprType(ctx.ID().getText(), variableValueType);
        if (hasSymbol(variable.id())) {
            SyntaxError(ctx.getStart().getLine(), "Redeclaração de variável %s.", variable.id());
        }
        var expression = ctx.expression();
        if (expression != null) {
            var expr_result = visit(expression);
            if (variable.type() == ValueType.FLOAT) {
                expr_result = convertToFloat(expr_result);
            } else if (variable.type() != expr_result.type()) {
                SyntaxError(ctx.getStart().getLine(), "Tipo de atribuição de variável esperado %s, Tipo da operação é %s.", variable.type(), expr_result.type());
            }
            instructionAssign(variable.id(), expr_result.id());
        } else {
            instructionAssign(variable.id(), variableValueType.constantOf(0.0));
        }
        return variable;
    }

    @Override
    public ExprType visitAssignment(MontPyParser.AssignmentContext ctx) {
        // Assignment or declaration of implicit variable
        String id = ctx.ID().getText();
        ExprType variable;
        if (ctx.OPEN_BRACKET() != null) {
            if (!hasSymbol(id)) {
                SyntaxError(ctx.getStart().getLine(), "Variável %s não existe.\n", id);
            }
            variable = new ExprType(id, getSymbolType(id));
            if (!LIST_TYPES.contains(variable.type()))
                SyntaxError(ctx.getStart().getLine(), "Variável %s não é lista.", id);
            ExprType pos = visit(ctx.expression(0));
            ExprType value = visit(ctx.expression(1));
            if (pos.type() != ValueType.INT)
                SyntaxError(ctx.getStart().getLine(), "Índice %s não é inteiro, é do tipo %s.", pos.id(), pos.type());
            if (variable.type() == ValueType.LIST_FLOAT) {
                value = convertToFloat(value);
            } else if (value.type() == ValueType.FLOAT) {
                SyntaxError(ctx.getStart().getLine(), "Atribuir float a elemento de array de inteiros %s.", id);
            }
            String arr_pos = accessArrayPos(id, pos.id());
            instructionAssign("*" + arr_pos, value.id());
        } else {
            if (hasSymbol(id)) {
                variable = new ExprType(id, getSymbolType(id));
            } else {
                variable = new ExprType(id, ValueType.FLOAT);
                symbolTable.peekFirst().put(id, ValueType.FLOAT);
            }
            ExprType value = visit(ctx.expression(0));
            if (variable.type() == ValueType.FLOAT) {
                value = convertToFloat(value);
            } else if (value.type() == ValueType.FLOAT) {
                SyntaxError(ctx.getStart().getLine(), "Atribuir float a elemento inteiro %s.\n", variable.id());
            }
            instructionAssign(id, value.id());
        }
        return null;
    }

    @Override
    public ExprType visitFunctionDeclaration(MontPyParser.FunctionDeclarationContext ctx) {
        String function_name = ctx.ID().getText();
        if (RESERVED_WORDS.contains(function_name)) {
            SyntaxError(ctx.getStart().getLine(), "Função reservada %s declarada.", function_name);
        }
        var parameters = ctx.param();
        // Create function context
        context.addFirst(new ArrayList<>());
        symbolTable.addFirst(new HashMap<>());
        List<ValueType> signature = new ArrayList<>(parameters.size() + 1);
        if (ctx.type() != null) {
            visit(ctx.type());
            signature.add(variableValueType);
        } else {
            signature.add(ValueType.FLOAT);
        }
        addLabel("func_" + function_name);

        addTAC("begin_function %d", parameters.size());
        for (var param : parameters) {
            ExprType parameter = visit(param);
            symbolTable.peekFirst().put(parameter.id(), parameter.type());
            signature.add(parameter.type());
            addTAC("%s = pop_param", parameter.id());
        }
        functionTable.put(function_name, signature);
        visit(ctx.statementBlock());
        addTAC("end_function");

        functionsTAC.put(function_name, context.removeFirst());
        symbolTable.removeFirst();
        return null;
    }

    @Override
    public ExprType visitReturnStatement(MontPyParser.ReturnStatementContext ctx) {
        if (context.size() == 1) {
            SyntaxError(ctx.getStart().getLine(), "Return fora de função.");
        }
        var expression = ctx.expression();
        if (expression == null) {
            addTAC("return");
        } else {
            ExprType value = visit(expression);
            addTAC("return %s", value.id());
        }
        return null;
    }

    @Override
    public ExprType visitParam(MontPyParser.ParamContext ctx) {
        if (ctx.listType() != null) {
            visit(ctx.listType());
            return new ExprType(ctx.ID().getText(), variableValueType);
        }
        if (ctx.type() != null) {
            visit(ctx.type());
            return new ExprType(ctx.ID().getText(), variableValueType);
        }
        return new ExprType(ctx.ID().getText(), ValueType.FLOAT);
    }

    @Override
    public ExprType visitPrintStatement(MontPyParser.PrintStatementContext ctx) {
        var expressions = ctx.expression();
        for (int i = expressions.size() - 1; i >= 0; i--) {
            ExprType value = visit(expressions.get(i));
            instructionPush(value.id());
        }
        addTAC("print");
        return null;
    }

    @Override
    public ExprType visitInputStatement(MontPyParser.InputStatementContext ctx) {
        ExprType value = visit(ctx.expression());
        String id = ctx.ID().getText();
        if (ctx.type() != null) {
            visit(ctx.type());
            if (hasSymbol(id)) {
                SyntaxError(ctx.getStart().getLine(), "Redeclaração de variável %s.", id);
            }
            symbolTable.peekFirst().put(id, variableValueType);
        } else if (!hasSymbol(id)) {
            // Criar variável implícita caso não exista
            variableValueType = ValueType.FLOAT;  // Tipo padrão
            symbolTable.peekFirst().put(id, variableValueType);
        }
        ExprType result = new ExprType(id, getSymbolType(id));
        instructionPush(value.id());
        addTAC("%s = input", id);
        return result;
    }

    @Override
    public ExprType visitIfStatement(MontPyParser.IfStatementContext ctx) {
        var conditions = ctx.expression();
        var blocks = ctx.statementBlock();
        boolean last_block;
        ExprType condition = visit(conditions.get(0));
        String end_label = nextLabel();
        last_block = (blocks.size() == 1);
        String next_label = last_block ? end_label : nextLabel();
        instructionConditional(condition.id(), next_label);
        visit(blocks.get(0));
        if (!last_block)
            instructionGoto(end_label);
        // elif...
        for (int i = 1; i < conditions.size(); i++) {
            addLabel(next_label);
            last_block = i + 1 == conditions.size() && ctx.ELSE() == null;
            next_label = last_block ? end_label : nextLabel();
            condition = visit(conditions.get(i));
            instructionConditional(condition.id(), next_label);
            visit(blocks.get(i));
            if (!last_block)
                instructionGoto(end_label);
        }
        // else
        if (ctx.ELSE() != null) {
            addLabel(next_label);
            visit(blocks.get(blocks.size() - 1));
        }
        addLabel(end_label);
        return null;
    }

    @Override
    public ExprType visitForStatement(MontPyParser.ForStatementContext ctx) {
        String iterator_id = ctx.ID(0).getText();
        ExprType iterator = null;
        ExprType idx_iterator = null, end_value, shift_value;
        ExprType cmp = newTemp(ValueType.INT);
        String start_label = nextLabel();
        String end_label = nextLabel();
        String next_it_label = nextLabel();
        boolean is_list = ctx.RANGE() == null;
        String list_id = "";
        if (is_list) {
            list_id = ctx.ID(1).getText();
            iterator = switch (getSymbolType(list_id)) {
                case LIST_INT -> new ExprType(iterator_id, ValueType.INT);
                case LIST_FLOAT -> new ExprType(iterator_id, ValueType.FLOAT);
                default -> {
                    SyntaxError(ctx.getStart().getLine(), "For espera variável %s do tipo lista, tipo informado é %s.", list_id, getSymbolType(list_id));
                    yield null;
                }
            };
            idx_iterator = newTemp(ValueType.INT);
            instructionAssign(idx_iterator.id(), idx_iterator.type().constantOf(0.0));
            end_value = newTemp(ValueType.INT);
            instructionListSize(end_value.id(), list_id);
            shift_value = newTemp(ValueType.INT);
            instructionAssign(shift_value.id(), shift_value.type().constantOf(1.0));
        } else {
            var expressions = ctx.expression();
            if (expressions.size() == 1) {
                end_value = visit(expressions.get(0));
                iterator = new ExprType(iterator_id, end_value.type());
                instructionAssign(iterator.id(), iterator.type().constantOf(0.0));
                shift_value = newTemp(end_value.type());
                instructionAssign(shift_value.id(), shift_value.type().constantOf(1.0));
            } else {
                end_value = visit(expressions.get(1));
                iterator = new ExprType(iterator_id, end_value.type());
                ExprType tmp = visit(expressions.get(0));
                instructionAssign(iterator.id(), tmp.id());
                if (expressions.size() == 3) {
                    shift_value = visit(expressions.get(2));
                } else {
                    shift_value = newTemp(end_value.type());
                    instructionAssign(shift_value.id(), shift_value.type().constantOf(1.0));
                }
            }
        }
        symbolTable.peekFirst().put(iterator.id(), iterator.type());
        addLabel(start_label);
        instructionOP(cmp.id(), (is_list) ? idx_iterator.id() : iterator.id(), "<", end_value.id());
        instructionConditional(cmp.id(), end_label);
        if (is_list) {
            String pointer = accessArrayPos(list_id, idx_iterator.id());
            instructionAssign(iterator.id(), "*" + pointer);
        }
        visit(ctx.statementBlock());
        addLabel(next_it_label);
        ExprType next = newTemp(end_value.type());
        instructionOP(next.id(), (is_list) ? idx_iterator.id() : iterator.id(), "+", shift_value.id());
        instructionAssign((is_list) ? idx_iterator.id() : iterator.id(), next.id());
        instructionGoto(start_label);
        addLabel(end_label);
        symbolTable.peekFirst().remove(iterator.id());
        return null;
    }

    @Override
    public ExprType visitWhileStatement(MontPyParser.WhileStatementContext ctx) {
        String start_label = nextLabel();
        String end_label = nextLabel();
        addLabel(start_label);
        ExprType condition = visit(ctx.expression());
        instructionConditional(condition.id(), end_label);
        visit(ctx.statementBlock());
        instructionGoto(start_label);
        addLabel(end_label);
        return null;
    }

    @Override
    public ExprType visitTypeConversion(MontPyParser.TypeConversionContext ctx) {
        ExprType value = visit(ctx.expression());
        visit(ctx.type());
        return switch(variableValueType) {
            case INT -> convertToInt(value);
            case FLOAT -> convertToFloat(value);
            default -> null;
        };
    }

    public static void main(String[] args) {

        TACGenerator tacGenerator = new TACGenerator();

        // Simulando a leitura do código-fonte MontPy a partir de um InputStream
        InputStream sourceCode = System.in; // Exemplo: ler a partir da entrada padrão

        try {
            tacGenerator.generateTACFromSource(sourceCode);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Exibir o TAC gerado
        List<String> globalTAC = tacGenerator.getGlobalTAC();
        Map<String, List<String>> functionsTAC = tacGenerator.getFunctionsTAC();

        System.out.println("TAC Global:");
        for (String line : globalTAC) {
            System.out.println(line);
        }

        System.out.println("\nTAC de Funções:");
        for (Map.Entry<String, List<String>> entry : functionsTAC.entrySet()) {
            System.out.println("Função " + entry.getKey() + ":");
            for (String line : entry.getValue()) {
                System.out.println(line);
            }
        }

        // Converter TAC para Assembly
        TACToAssemblyConverter converter = new TACToAssemblyConverter();
        List<String> assemblyCode = converter.convert(functionsTAC, globalTAC);

        // Exibir o código Assembly gerado
        System.out.println("\nCódigo Assembly:");
        for (String line : assemblyCode) {
            System.out.println(line);
        }
    }
}
