// Generated from MontPy.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MontPyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NEWLINE=1, FLOAT=2, INT=3, STRING=4, WS=5, COMMENT=6, DEF=7, FLOAT_TYPE=8, 
		INT_TYPE=9, FOR=10, IN=11, RANGE=12, WHILE=13, IF=14, ELIF=15, ELSE=16, 
		SIZE=17, ADD=18, REMOVE=19, AND=20, OR=21, RETURN=22, PRINT=23, INPUT=24, 
		PLUS=25, MINUS=26, MULTIPLY=27, DIVIDE=28, EXPONENT=29, FLOOR_DIVIDE=30, 
		MODULO=31, OPEN_BRACKET=32, CLOSE_BRACKET=33, OPEN_PAREN=34, CLOSE_PAREN=35, 
		EQUALS=36, EQUALITY=37, INEQUALITY=38, GREATER_THAN=39, LESS_THAN=40, 
		GREATER_THAN_OR_EQUAL=41, LESS_THAN_OR_EQUAL=42, COMMA=43, COLON=44, DOT=45, 
		ID=46;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SPACES", "NEWLINE", "DIGIT", "FLOAT", "INT", "STRING", "WS", "COMMENT", 
			"DEF", "FLOAT_TYPE", "INT_TYPE", "FOR", "IN", "RANGE", "WHILE", "IF", 
			"ELIF", "ELSE", "SIZE", "ADD", "REMOVE", "AND", "OR", "RETURN", "PRINT", 
			"INPUT", "PLUS", "MINUS", "MULTIPLY", "DIVIDE", "EXPONENT", "FLOOR_DIVIDE", 
			"MODULO", "OPEN_BRACKET", "CLOSE_BRACKET", "OPEN_PAREN", "CLOSE_PAREN", 
			"EQUALS", "EQUALITY", "INEQUALITY", "GREATER_THAN", "LESS_THAN", "GREATER_THAN_OR_EQUAL", 
			"LESS_THAN_OR_EQUAL", "COMMA", "COLON", "DOT", "ID"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, "'def'", "'float'", "'int'", 
			"'for'", "'in'", "'range'", "'while'", "'if'", "'elif'", "'else'", "'size'", 
			"'add'", "'remove'", "'and'", "'or'", "'return'", "'print'", "'input'", 
			"'+'", "'-'", "'*'", "'/'", "'**'", "'//'", "'%'", "'['", "']'", "'('", 
			"')'", "'='", "'=='", "'!='", "'>'", "'<'", "'>='", "'<='", "','", "':'", 
			"'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "NEWLINE", "FLOAT", "INT", "STRING", "WS", "COMMENT", "DEF", "FLOAT_TYPE", 
			"INT_TYPE", "FOR", "IN", "RANGE", "WHILE", "IF", "ELIF", "ELSE", "SIZE", 
			"ADD", "REMOVE", "AND", "OR", "RETURN", "PRINT", "INPUT", "PLUS", "MINUS", 
			"MULTIPLY", "DIVIDE", "EXPONENT", "FLOOR_DIVIDE", "MODULO", "OPEN_BRACKET", 
			"CLOSE_BRACKET", "OPEN_PAREN", "CLOSE_PAREN", "EQUALS", "EQUALITY", "INEQUALITY", 
			"GREATER_THAN", "LESS_THAN", "GREATER_THAN_OR_EQUAL", "LESS_THAN_OR_EQUAL", 
			"COMMA", "COLON", "DOT", "ID"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


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


	public MontPyLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MontPy.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 1:
			NEWLINE_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void NEWLINE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:

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
			   
			break;
		}
	}
	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return NEWLINE_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean NEWLINE_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return atStartOfInput();
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\60\u0144\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\6\2e\n\2\r\2\16\2f\3"+
		"\3\3\3\3\3\5\3l\n\3\3\3\3\3\5\3p\n\3\3\3\5\3s\n\3\5\3u\n\3\3\3\3\3\3\4"+
		"\3\4\3\5\5\5|\n\5\3\5\7\5\177\n\5\f\5\16\5\u0082\13\5\3\5\3\5\6\5\u0086"+
		"\n\5\r\5\16\5\u0087\3\5\3\5\5\5\u008c\n\5\3\5\6\5\u008f\n\5\r\5\16\5\u0090"+
		"\5\5\u0093\n\5\3\6\5\6\u0096\n\6\3\6\6\6\u0099\n\6\r\6\16\6\u009a\3\7"+
		"\3\7\7\7\u009f\n\7\f\7\16\7\u00a2\13\7\3\7\3\7\3\b\6\b\u00a7\n\b\r\b\16"+
		"\b\u00a8\3\b\3\b\3\t\3\t\7\t\u00af\n\t\f\t\16\t\u00b2\13\t\3\t\3\t\3\n"+
		"\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3"+
		"\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3+\3+\3,\3"+
		",\3,\3-\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\7\61\u0140\n\61\f\61\16"+
		"\61\u0143\13\61\2\2\62\3\2\5\3\7\2\t\4\13\5\r\6\17\7\21\b\23\t\25\n\27"+
		"\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27\61\30\63\31"+
		"\65\32\67\339\34;\35=\36?\37A C!E\"G#I$K%M&O\'Q(S)U*W+Y,[-]._/a\60\3\2"+
		"\n\4\2\13\13\"\"\3\2\62;\4\2--//\4\2GGgg\5\2\f\f\17\17$$\4\2\f\f\17\17"+
		"\5\2C\\aac|\6\2\62;C\\aac|\2\u0152\2\5\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S"+
		"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2"+
		"\2\2\2a\3\2\2\2\3d\3\2\2\2\5t\3\2\2\2\7x\3\2\2\2\t{\3\2\2\2\13\u0095\3"+
		"\2\2\2\r\u009c\3\2\2\2\17\u00a6\3\2\2\2\21\u00ac\3\2\2\2\23\u00b5\3\2"+
		"\2\2\25\u00b9\3\2\2\2\27\u00bf\3\2\2\2\31\u00c3\3\2\2\2\33\u00c7\3\2\2"+
		"\2\35\u00ca\3\2\2\2\37\u00d0\3\2\2\2!\u00d6\3\2\2\2#\u00d9\3\2\2\2%\u00de"+
		"\3\2\2\2\'\u00e3\3\2\2\2)\u00e8\3\2\2\2+\u00ec\3\2\2\2-\u00f3\3\2\2\2"+
		"/\u00f7\3\2\2\2\61\u00fa\3\2\2\2\63\u0101\3\2\2\2\65\u0107\3\2\2\2\67"+
		"\u010d\3\2\2\29\u010f\3\2\2\2;\u0111\3\2\2\2=\u0113\3\2\2\2?\u0115\3\2"+
		"\2\2A\u0118\3\2\2\2C\u011b\3\2\2\2E\u011d\3\2\2\2G\u011f\3\2\2\2I\u0121"+
		"\3\2\2\2K\u0123\3\2\2\2M\u0125\3\2\2\2O\u0127\3\2\2\2Q\u012a\3\2\2\2S"+
		"\u012d\3\2\2\2U\u012f\3\2\2\2W\u0131\3\2\2\2Y\u0134\3\2\2\2[\u0137\3\2"+
		"\2\2]\u0139\3\2\2\2_\u013b\3\2\2\2a\u013d\3\2\2\2ce\t\2\2\2dc\3\2\2\2"+
		"ef\3\2\2\2fd\3\2\2\2fg\3\2\2\2g\4\3\2\2\2hi\6\3\2\2iu\5\3\2\2jl\7\17\2"+
		"\2kj\3\2\2\2kl\3\2\2\2lm\3\2\2\2mp\7\f\2\2np\7\17\2\2ok\3\2\2\2on\3\2"+
		"\2\2pr\3\2\2\2qs\5\3\2\2rq\3\2\2\2rs\3\2\2\2su\3\2\2\2th\3\2\2\2to\3\2"+
		"\2\2uv\3\2\2\2vw\b\3\2\2w\6\3\2\2\2xy\t\3\2\2y\b\3\2\2\2z|\t\4\2\2{z\3"+
		"\2\2\2{|\3\2\2\2|\u0080\3\2\2\2}\177\5\7\4\2~}\3\2\2\2\177\u0082\3\2\2"+
		"\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\3\2\2\2\u0082\u0080"+
		"\3\2\2\2\u0083\u0085\7\60\2\2\u0084\u0086\5\7\4\2\u0085\u0084\3\2\2\2"+
		"\u0086\u0087\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0092"+
		"\3\2\2\2\u0089\u008b\t\5\2\2\u008a\u008c\t\4\2\2\u008b\u008a\3\2\2\2\u008b"+
		"\u008c\3\2\2\2\u008c\u008e\3\2\2\2\u008d\u008f\5\7\4\2\u008e\u008d\3\2"+
		"\2\2\u008f\u0090\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091"+
		"\u0093\3\2\2\2\u0092\u0089\3\2\2\2\u0092\u0093\3\2\2\2\u0093\n\3\2\2\2"+
		"\u0094\u0096\t\4\2\2\u0095\u0094\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0098"+
		"\3\2\2\2\u0097\u0099\5\7\4\2\u0098\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a"+
		"\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\f\3\2\2\2\u009c\u00a0\7$\2\2"+
		"\u009d\u009f\n\6\2\2\u009e\u009d\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e"+
		"\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3"+
		"\u00a4\7$\2\2\u00a4\16\3\2\2\2\u00a5\u00a7\t\2\2\2\u00a6\u00a5\3\2\2\2"+
		"\u00a7\u00a8\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa"+
		"\3\2\2\2\u00aa\u00ab\b\b\3\2\u00ab\20\3\2\2\2\u00ac\u00b0\7%\2\2\u00ad"+
		"\u00af\n\7\2\2\u00ae\u00ad\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2"+
		"\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3"+
		"\u00b4\b\t\3\2\u00b4\22\3\2\2\2\u00b5\u00b6\7f\2\2\u00b6\u00b7\7g\2\2"+
		"\u00b7\u00b8\7h\2\2\u00b8\24\3\2\2\2\u00b9\u00ba\7h\2\2\u00ba\u00bb\7"+
		"n\2\2\u00bb\u00bc\7q\2\2\u00bc\u00bd\7c\2\2\u00bd\u00be\7v\2\2\u00be\26"+
		"\3\2\2\2\u00bf\u00c0\7k\2\2\u00c0\u00c1\7p\2\2\u00c1\u00c2\7v\2\2\u00c2"+
		"\30\3\2\2\2\u00c3\u00c4\7h\2\2\u00c4\u00c5\7q\2\2\u00c5\u00c6\7t\2\2\u00c6"+
		"\32\3\2\2\2\u00c7\u00c8\7k\2\2\u00c8\u00c9\7p\2\2\u00c9\34\3\2\2\2\u00ca"+
		"\u00cb\7t\2\2\u00cb\u00cc\7c\2\2\u00cc\u00cd\7p\2\2\u00cd\u00ce\7i\2\2"+
		"\u00ce\u00cf\7g\2\2\u00cf\36\3\2\2\2\u00d0\u00d1\7y\2\2\u00d1\u00d2\7"+
		"j\2\2\u00d2\u00d3\7k\2\2\u00d3\u00d4\7n\2\2\u00d4\u00d5\7g\2\2\u00d5 "+
		"\3\2\2\2\u00d6\u00d7\7k\2\2\u00d7\u00d8\7h\2\2\u00d8\"\3\2\2\2\u00d9\u00da"+
		"\7g\2\2\u00da\u00db\7n\2\2\u00db\u00dc\7k\2\2\u00dc\u00dd\7h\2\2\u00dd"+
		"$\3\2\2\2\u00de\u00df\7g\2\2\u00df\u00e0\7n\2\2\u00e0\u00e1\7u\2\2\u00e1"+
		"\u00e2\7g\2\2\u00e2&\3\2\2\2\u00e3\u00e4\7u\2\2\u00e4\u00e5\7k\2\2\u00e5"+
		"\u00e6\7|\2\2\u00e6\u00e7\7g\2\2\u00e7(\3\2\2\2\u00e8\u00e9\7c\2\2\u00e9"+
		"\u00ea\7f\2\2\u00ea\u00eb\7f\2\2\u00eb*\3\2\2\2\u00ec\u00ed\7t\2\2\u00ed"+
		"\u00ee\7g\2\2\u00ee\u00ef\7o\2\2\u00ef\u00f0\7q\2\2\u00f0\u00f1\7x\2\2"+
		"\u00f1\u00f2\7g\2\2\u00f2,\3\2\2\2\u00f3\u00f4\7c\2\2\u00f4\u00f5\7p\2"+
		"\2\u00f5\u00f6\7f\2\2\u00f6.\3\2\2\2\u00f7\u00f8\7q\2\2\u00f8\u00f9\7"+
		"t\2\2\u00f9\60\3\2\2\2\u00fa\u00fb\7t\2\2\u00fb\u00fc\7g\2\2\u00fc\u00fd"+
		"\7v\2\2\u00fd\u00fe\7w\2\2\u00fe\u00ff\7t\2\2\u00ff\u0100\7p\2\2\u0100"+
		"\62\3\2\2\2\u0101\u0102\7r\2\2\u0102\u0103\7t\2\2\u0103\u0104\7k\2\2\u0104"+
		"\u0105\7p\2\2\u0105\u0106\7v\2\2\u0106\64\3\2\2\2\u0107\u0108\7k\2\2\u0108"+
		"\u0109\7p\2\2\u0109\u010a\7r\2\2\u010a\u010b\7w\2\2\u010b\u010c\7v\2\2"+
		"\u010c\66\3\2\2\2\u010d\u010e\7-\2\2\u010e8\3\2\2\2\u010f\u0110\7/\2\2"+
		"\u0110:\3\2\2\2\u0111\u0112\7,\2\2\u0112<\3\2\2\2\u0113\u0114\7\61\2\2"+
		"\u0114>\3\2\2\2\u0115\u0116\7,\2\2\u0116\u0117\7,\2\2\u0117@\3\2\2\2\u0118"+
		"\u0119\7\61\2\2\u0119\u011a\7\61\2\2\u011aB\3\2\2\2\u011b\u011c\7\'\2"+
		"\2\u011cD\3\2\2\2\u011d\u011e\7]\2\2\u011eF\3\2\2\2\u011f\u0120\7_\2\2"+
		"\u0120H\3\2\2\2\u0121\u0122\7*\2\2\u0122J\3\2\2\2\u0123\u0124\7+\2\2\u0124"+
		"L\3\2\2\2\u0125\u0126\7?\2\2\u0126N\3\2\2\2\u0127\u0128\7?\2\2\u0128\u0129"+
		"\7?\2\2\u0129P\3\2\2\2\u012a\u012b\7#\2\2\u012b\u012c\7?\2\2\u012cR\3"+
		"\2\2\2\u012d\u012e\7@\2\2\u012eT\3\2\2\2\u012f\u0130\7>\2\2\u0130V\3\2"+
		"\2\2\u0131\u0132\7@\2\2\u0132\u0133\7?\2\2\u0133X\3\2\2\2\u0134\u0135"+
		"\7>\2\2\u0135\u0136\7?\2\2\u0136Z\3\2\2\2\u0137\u0138\7.\2\2\u0138\\\3"+
		"\2\2\2\u0139\u013a\7<\2\2\u013a^\3\2\2\2\u013b\u013c\7\60\2\2\u013c`\3"+
		"\2\2\2\u013d\u0141\t\b\2\2\u013e\u0140\t\t\2\2\u013f\u013e\3\2\2\2\u0140"+
		"\u0143\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142b\3\2\2\2"+
		"\u0143\u0141\3\2\2\2\24\2fkort{\u0080\u0087\u008b\u0090\u0092\u0095\u009a"+
		"\u00a0\u00a8\u00b0\u0141\4\3\3\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}