grammar MontPy;

tokens {INDENT, DEDENT}

// https://stackoverflow.com/questions/8642154/antlr-what-is-simpliest-way-to-realize-python-like-indent-depending-grammar
@lexer::members {
  // A queue where extra tokens are pushed on (see the NEWLINE lexer rule).
  private java.util.LinkedList<Token> tokens = new java.util.LinkedList<>();
  // The stack that keeps track of the indentation level.
  private java.util.Stack<Integer> indents = new java.util.Stack<>();
  // The amount of opened braces, brackets and parenthesis.
  private int opened = 0;
  // The most recently produced token.
  private Token lastToken = null;
  @Override
  public void emit(Token t) {
    super.setToken(t);
    tokens.offer(t);
  }

  @Override
  public Token nextToken() {
    // Check if the end-of-file is ahead and there are still some DEDENTS expected.
    if (_input.LA(1) == EOF && !this.indents.isEmpty()) {
      // Remove any trailing EOF tokens from our buffer.
      for (int i = tokens.size() - 1; i >= 0; i--) {
        if (tokens.get(i).getType() == EOF) {
          tokens.remove(i);
        }
      }

      // First emit an extra line break that serves as the end of the statement.
      this.emit(commonToken(MontPyParser.NEWLINE, "\n"));

      // Now emit as much DEDENT tokens as needed.
      while (!indents.isEmpty()) {
        this.emit(createDedent());
        indents.pop();
      }

      // Put the EOF back on the token stream.
      this.emit(commonToken(MontPyParser.EOF, "<EOF>"));
    }

    Token next = super.nextToken();

    if (next.getChannel() == Token.DEFAULT_CHANNEL) {
      // Keep track of the last token on the default channel.
      this.lastToken = next;
    }

    return tokens.isEmpty() ? next : tokens.poll();
  }

  private Token createDedent() {
    CommonToken dedent = commonToken(MontPyParser.DEDENT, "");
    dedent.setLine(this.lastToken.getLine());
    return dedent;
  }

  private CommonToken commonToken(int type, String text) {
    int stop = this.getCharIndex() - 1;
    int start = text.isEmpty() ? stop : stop - text.length() + 1;
    return new CommonToken(this._tokenFactorySourcePair, type, DEFAULT_TOKEN_CHANNEL, start, stop);
  }

  // Calculates the indentation of the provided spaces, taking the
  // following rules into account:
  //
  // "Tabs are replaced (from left to right) by one to eight spaces
  //  such that the total number of characters up to and including
  //  the replacement is a multiple of eight [...]"
  //
  //  -- https://docs.python.org/3.1/reference/lexical_analysis.html#indentation
  static int getIndentationCount(String spaces) {
    int count = 0;
    for (char ch : spaces.toCharArray()) {
      switch (ch) {
        case '\t':
          count += 8 - (count % 8);
          break;
        default:
          // A normal space char.
          count++;
      }
    }

    return count;
  }

  boolean atStartOfInput() {
    return super.getCharPositionInLine() == 0 && super.getLine() == 1;
  }
}

// Especiais para Python
fragment SPACES: [ \t]+;
NEWLINE
 : ( {atStartOfInput()}?   SPACES
   | ( '\r'? '\n' | '\r' ) SPACES?
   )
   {
     String newLine = getText().replaceAll("[^\r\n]+", "");
     String spaces = getText().replaceAll("[\r\n]+", "");
     int next = _input.LA(1);
     if (opened > 0 || next == '\r' || next == '\n' || next == '#') {
       // If we're inside a list or on a blank line, ignore all indents, 
       // dedents and line breaks.
       skip();
     }
     else {
       emit(commonToken(NEWLINE, newLine));
       int indent = getIndentationCount(spaces);
       int previous = indents.isEmpty() ? 0 : indents.peek();
       if (indent == previous) {
         // skip indents of the same size as the present indent-size
         skip();
       }
       else if (indent > previous) {
         indents.push(indent);
         emit(commonToken(MontPyParser.INDENT, spaces));
       }
       else {
         // Possibly emit more than 1 DEDENT token.
         while(!indents.isEmpty() && indents.peek() > indent) {
           this.emit(createDedent());
           indents.pop();
         }
       }
     }
   }
 ;

// Definindo tokens
fragment DIGIT : [0-9];
FLOAT : ('+'|'-')? DIGIT* '.' DIGIT+ (('e' | 'E') ('+'|'-')? DIGIT+)?;
INT : ('+'|'-')? DIGIT+;
STRING : '"' ~["\r\n]* '"';
WS : [ \t]+ -> skip;
COMMENT : '#' ~[\r\n]* -> skip;

// Palavras reservadas
DEF: 'def';
FLOAT_TYPE: 'float';
INT_TYPE: 'int';
FOR: 'for';
IN: 'in';
RANGE: 'range';
WHILE: 'while';
IF: 'if';
ELIF: 'elif';
ELSE: 'else';
SIZE: 'size';
ADD: 'add';
REMOVE: 'remove';
AND: 'and';
OR: 'or';
RETURN: 'return';
PRINT: 'print';
INPUT: 'input';
//TAB: '\\t';

// Operadores e símbolos
PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DIVIDE: '/';
EXPONENT: '**';  // Exponenciação
FLOOR_DIVIDE: '//';
MODULO: '%';
OPEN_BRACKET: '[';
CLOSE_BRACKET: ']';
OPEN_PAREN: '(';
CLOSE_PAREN: ')';
EQUALS: '=';
EQUALITY: '==';
INEQUALITY: '!=';
GREATER_THAN: '>';
LESS_THAN: '<';
GREATER_THAN_OR_EQUAL: '>=';
LESS_THAN_OR_EQUAL: '<=';
COMMA: ',';
COLON: ':';
DOT: '.';

// Identificar nomes de variáveis e funções
ID: [a-zA-Z_] [a-zA-Z_0-9]*;

// 
// PARSER RULES
//

type : INT_TYPE | FLOAT_TYPE;

listType : type OPEN_BRACKET CLOSE_BRACKET;

// Regras de precedência adicionadas para operações matemáticas e lógicas
expression : logicalExpression;

logicalExpression : equalityExpression ((AND | OR) logicalExpression)?;

equalityExpression : comparisonExpression ((EQUALITY | INEQUALITY) equalityExpression)?;

comparisonExpression : additiveExpression ((GREATER_THAN | LESS_THAN | GREATER_THAN_OR_EQUAL | LESS_THAN_OR_EQUAL) comparisonExpression)?;

additiveExpression : multiplicativeExpression ((PLUS | MINUS) additiveExpression)?;

multiplicativeExpression : powerExpression ((MULTIPLY | DIVIDE | FLOOR_DIVIDE | MODULO) multiplicativeExpression)?;

powerExpression : unaryExpression (EXPONENT powerExpression)?; // Associatividade à direita para exponenciação

unaryExpression : (PLUS | MINUS)? primary;

primary : literal
  | ID (OPEN_BRACKET expression CLOSE_BRACKET)?
  | OPEN_PAREN expression CLOSE_PAREN
  | listFunctionCall
  | functionCall
  | typeConversion
  ;

literal : INT | FLOAT | STRING;

listFunctionCall : (SIZE | ADD | REMOVE) OPEN_PAREN ID (COMMA expression)* CLOSE_PAREN;

variableDeclaration :
    type baseAssignment (COMMA baseAssignment)*
  | listType ID (EQUALS OPEN_BRACKET (expression (COMMA expression)*)? CLOSE_BRACKET)?
  | ID EQUALS OPEN_BRACKET expression (COMMA expression)* CLOSE_BRACKET
  ;

baseAssignment : ID (EQUALS expression)? ;

functionDeclaration : DEF type? ID OPEN_PAREN (param (COMMA param)*)? CLOSE_PAREN COLON statementBlock;

param : (type | listType)? ID;

statement :
    ( simpleStatement NEWLINE )
  | ifStatement
  | forStatement
  | whileStatement;

simpleStatement : 
    assignment
  | variableDeclaration
  | expression
  | returnStatement
  | printStatement
  | inputStatement
  ;

statementBlock : NEWLINE INDENT statement+ DEDENT;

returnStatement : RETURN expression?;
printStatement : PRINT OPEN_PAREN expression (COMMA expression)* CLOSE_PAREN;
inputStatement : type? ID EQUALS INPUT OPEN_PAREN expression CLOSE_PAREN;

assignment :
    ID EQUALS expression
  | ID OPEN_BRACKET expression CLOSE_BRACKET EQUALS expression;

ifStatement : IF expression COLON statementBlock (ELIF expression COLON statementBlock)* (ELSE COLON statementBlock)?;

forStatement : FOR ID IN (ID | RANGE OPEN_PAREN expression (COMMA expression (COMMA expression)?)? CLOSE_PAREN) COLON statementBlock;

whileStatement : WHILE expression COLON statementBlock;

functionCall : ID OPEN_PAREN (expression (COMMA expression)*)? CLOSE_PAREN;

typeConversion : type OPEN_PAREN expression CLOSE_PAREN;

program : (NEWLINE | statement | functionDeclaration)+ EOF;