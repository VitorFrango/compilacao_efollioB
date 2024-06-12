// Generated from MontPy.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MontPyLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

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
		"\u0004\u0000.\u0142\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u0001\u0000"+
		"\u0004\u0000c\b\u0000\u000b\u0000\f\u0000d\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0003\u0001j\b\u0001\u0001\u0001\u0001\u0001\u0003\u0001n\b\u0001"+
		"\u0001\u0001\u0003\u0001q\b\u0001\u0003\u0001s\b\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0003\u0003z\b\u0003\u0001"+
		"\u0003\u0005\u0003}\b\u0003\n\u0003\f\u0003\u0080\t\u0003\u0001\u0003"+
		"\u0001\u0003\u0004\u0003\u0084\b\u0003\u000b\u0003\f\u0003\u0085\u0001"+
		"\u0003\u0001\u0003\u0003\u0003\u008a\b\u0003\u0001\u0003\u0004\u0003\u008d"+
		"\b\u0003\u000b\u0003\f\u0003\u008e\u0003\u0003\u0091\b\u0003\u0001\u0004"+
		"\u0003\u0004\u0094\b\u0004\u0001\u0004\u0004\u0004\u0097\b\u0004\u000b"+
		"\u0004\f\u0004\u0098\u0001\u0005\u0001\u0005\u0005\u0005\u009d\b\u0005"+
		"\n\u0005\f\u0005\u00a0\t\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0004"+
		"\u0006\u00a5\b\u0006\u000b\u0006\f\u0006\u00a6\u0001\u0006\u0001\u0006"+
		"\u0001\u0007\u0001\u0007\u0005\u0007\u00ad\b\u0007\n\u0007\f\u0007\u00b0"+
		"\t\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a"+
		"\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001"+
		"#\u0001$\u0001$\u0001%\u0001%\u0001&\u0001&\u0001&\u0001\'\u0001\'\u0001"+
		"\'\u0001(\u0001(\u0001)\u0001)\u0001*\u0001*\u0001*\u0001+\u0001+\u0001"+
		"+\u0001,\u0001,\u0001-\u0001-\u0001.\u0001.\u0001/\u0001/\u0005/\u013e"+
		"\b/\n/\f/\u0141\t/\u0000\u00000\u0001\u0000\u0003\u0001\u0005\u0000\u0007"+
		"\u0002\t\u0003\u000b\u0004\r\u0005\u000f\u0006\u0011\u0007\u0013\b\u0015"+
		"\t\u0017\n\u0019\u000b\u001b\f\u001d\r\u001f\u000e!\u000f#\u0010%\u0011"+
		"\'\u0012)\u0013+\u0014-\u0015/\u00161\u00173\u00185\u00197\u001a9\u001b"+
		";\u001c=\u001d?\u001eA\u001fC E!G\"I#K$M%O&Q\'S(U)W*Y+[,]-_.\u0001\u0000"+
		"\b\u0002\u0000\t\t  \u0001\u000009\u0002\u0000++--\u0002\u0000EEee\u0003"+
		"\u0000\n\n\r\r\"\"\u0002\u0000\n\n\r\r\u0003\u0000AZ__az\u0004\u00000"+
		"9AZ__az\u0150\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000"+
		"\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000"+
		"\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000"+
		"\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000"+
		"\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%"+
		"\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001"+
		"\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000"+
		"\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u0000"+
		"3\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001"+
		"\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000"+
		"\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000"+
		"A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001"+
		"\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001\u0000\u0000"+
		"\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000\u0000\u0000"+
		"O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000\u0000S\u0001"+
		"\u0000\u0000\u0000\u0000U\u0001\u0000\u0000\u0000\u0000W\u0001\u0000\u0000"+
		"\u0000\u0000Y\u0001\u0000\u0000\u0000\u0000[\u0001\u0000\u0000\u0000\u0000"+
		"]\u0001\u0000\u0000\u0000\u0000_\u0001\u0000\u0000\u0000\u0001b\u0001"+
		"\u0000\u0000\u0000\u0003r\u0001\u0000\u0000\u0000\u0005v\u0001\u0000\u0000"+
		"\u0000\u0007y\u0001\u0000\u0000\u0000\t\u0093\u0001\u0000\u0000\u0000"+
		"\u000b\u009a\u0001\u0000\u0000\u0000\r\u00a4\u0001\u0000\u0000\u0000\u000f"+
		"\u00aa\u0001\u0000\u0000\u0000\u0011\u00b3\u0001\u0000\u0000\u0000\u0013"+
		"\u00b7\u0001\u0000\u0000\u0000\u0015\u00bd\u0001\u0000\u0000\u0000\u0017"+
		"\u00c1\u0001\u0000\u0000\u0000\u0019\u00c5\u0001\u0000\u0000\u0000\u001b"+
		"\u00c8\u0001\u0000\u0000\u0000\u001d\u00ce\u0001\u0000\u0000\u0000\u001f"+
		"\u00d4\u0001\u0000\u0000\u0000!\u00d7\u0001\u0000\u0000\u0000#\u00dc\u0001"+
		"\u0000\u0000\u0000%\u00e1\u0001\u0000\u0000\u0000\'\u00e6\u0001\u0000"+
		"\u0000\u0000)\u00ea\u0001\u0000\u0000\u0000+\u00f1\u0001\u0000\u0000\u0000"+
		"-\u00f5\u0001\u0000\u0000\u0000/\u00f8\u0001\u0000\u0000\u00001\u00ff"+
		"\u0001\u0000\u0000\u00003\u0105\u0001\u0000\u0000\u00005\u010b\u0001\u0000"+
		"\u0000\u00007\u010d\u0001\u0000\u0000\u00009\u010f\u0001\u0000\u0000\u0000"+
		";\u0111\u0001\u0000\u0000\u0000=\u0113\u0001\u0000\u0000\u0000?\u0116"+
		"\u0001\u0000\u0000\u0000A\u0119\u0001\u0000\u0000\u0000C\u011b\u0001\u0000"+
		"\u0000\u0000E\u011d\u0001\u0000\u0000\u0000G\u011f\u0001\u0000\u0000\u0000"+
		"I\u0121\u0001\u0000\u0000\u0000K\u0123\u0001\u0000\u0000\u0000M\u0125"+
		"\u0001\u0000\u0000\u0000O\u0128\u0001\u0000\u0000\u0000Q\u012b\u0001\u0000"+
		"\u0000\u0000S\u012d\u0001\u0000\u0000\u0000U\u012f\u0001\u0000\u0000\u0000"+
		"W\u0132\u0001\u0000\u0000\u0000Y\u0135\u0001\u0000\u0000\u0000[\u0137"+
		"\u0001\u0000\u0000\u0000]\u0139\u0001\u0000\u0000\u0000_\u013b\u0001\u0000"+
		"\u0000\u0000ac\u0007\u0000\u0000\u0000ba\u0001\u0000\u0000\u0000cd\u0001"+
		"\u0000\u0000\u0000db\u0001\u0000\u0000\u0000de\u0001\u0000\u0000\u0000"+
		"e\u0002\u0001\u0000\u0000\u0000fg\u0004\u0001\u0000\u0000gs\u0003\u0001"+
		"\u0000\u0000hj\u0005\r\u0000\u0000ih\u0001\u0000\u0000\u0000ij\u0001\u0000"+
		"\u0000\u0000jk\u0001\u0000\u0000\u0000kn\u0005\n\u0000\u0000ln\u0005\r"+
		"\u0000\u0000mi\u0001\u0000\u0000\u0000ml\u0001\u0000\u0000\u0000np\u0001"+
		"\u0000\u0000\u0000oq\u0003\u0001\u0000\u0000po\u0001\u0000\u0000\u0000"+
		"pq\u0001\u0000\u0000\u0000qs\u0001\u0000\u0000\u0000rf\u0001\u0000\u0000"+
		"\u0000rm\u0001\u0000\u0000\u0000st\u0001\u0000\u0000\u0000tu\u0006\u0001"+
		"\u0000\u0000u\u0004\u0001\u0000\u0000\u0000vw\u0007\u0001\u0000\u0000"+
		"w\u0006\u0001\u0000\u0000\u0000xz\u0007\u0002\u0000\u0000yx\u0001\u0000"+
		"\u0000\u0000yz\u0001\u0000\u0000\u0000z~\u0001\u0000\u0000\u0000{}\u0003"+
		"\u0005\u0002\u0000|{\u0001\u0000\u0000\u0000}\u0080\u0001\u0000\u0000"+
		"\u0000~|\u0001\u0000\u0000\u0000~\u007f\u0001\u0000\u0000\u0000\u007f"+
		"\u0081\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000\u0081\u0083"+
		"\u0005.\u0000\u0000\u0082\u0084\u0003\u0005\u0002\u0000\u0083\u0082\u0001"+
		"\u0000\u0000\u0000\u0084\u0085\u0001\u0000\u0000\u0000\u0085\u0083\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\u0001\u0000\u0000\u0000\u0086\u0090\u0001"+
		"\u0000\u0000\u0000\u0087\u0089\u0007\u0003\u0000\u0000\u0088\u008a\u0007"+
		"\u0002\u0000\u0000\u0089\u0088\u0001\u0000\u0000\u0000\u0089\u008a\u0001"+
		"\u0000\u0000\u0000\u008a\u008c\u0001\u0000\u0000\u0000\u008b\u008d\u0003"+
		"\u0005\u0002\u0000\u008c\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001"+
		"\u0000\u0000\u0000\u008e\u008c\u0001\u0000\u0000\u0000\u008e\u008f\u0001"+
		"\u0000\u0000\u0000\u008f\u0091\u0001\u0000\u0000\u0000\u0090\u0087\u0001"+
		"\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091\b\u0001\u0000"+
		"\u0000\u0000\u0092\u0094\u0007\u0002\u0000\u0000\u0093\u0092\u0001\u0000"+
		"\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0096\u0001\u0000"+
		"\u0000\u0000\u0095\u0097\u0003\u0005\u0002\u0000\u0096\u0095\u0001\u0000"+
		"\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098\u0096\u0001\u0000"+
		"\u0000\u0000\u0098\u0099\u0001\u0000\u0000\u0000\u0099\n\u0001\u0000\u0000"+
		"\u0000\u009a\u009e\u0005\"\u0000\u0000\u009b\u009d\b\u0004\u0000\u0000"+
		"\u009c\u009b\u0001\u0000\u0000\u0000\u009d\u00a0\u0001\u0000\u0000\u0000"+
		"\u009e\u009c\u0001\u0000\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000"+
		"\u009f\u00a1\u0001\u0000\u0000\u0000\u00a0\u009e\u0001\u0000\u0000\u0000"+
		"\u00a1\u00a2\u0005\"\u0000\u0000\u00a2\f\u0001\u0000\u0000\u0000\u00a3"+
		"\u00a5\u0007\u0000\u0000\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a5"+
		"\u00a6\u0001\u0000\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000\u00a6"+
		"\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a8\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a9\u0006\u0006\u0001\u0000\u00a9\u000e\u0001\u0000\u0000\u0000\u00aa"+
		"\u00ae\u0005#\u0000\u0000\u00ab\u00ad\b\u0005\u0000\u0000\u00ac\u00ab"+
		"\u0001\u0000\u0000\u0000\u00ad\u00b0\u0001\u0000\u0000\u0000\u00ae\u00ac"+
		"\u0001\u0000\u0000\u0000\u00ae\u00af\u0001\u0000\u0000\u0000\u00af\u00b1"+
		"\u0001\u0000\u0000\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b1\u00b2"+
		"\u0006\u0007\u0001\u0000\u00b2\u0010\u0001\u0000\u0000\u0000\u00b3\u00b4"+
		"\u0005d\u0000\u0000\u00b4\u00b5\u0005e\u0000\u0000\u00b5\u00b6\u0005f"+
		"\u0000\u0000\u00b6\u0012\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005f\u0000"+
		"\u0000\u00b8\u00b9\u0005l\u0000\u0000\u00b9\u00ba\u0005o\u0000\u0000\u00ba"+
		"\u00bb\u0005a\u0000\u0000\u00bb\u00bc\u0005t\u0000\u0000\u00bc\u0014\u0001"+
		"\u0000\u0000\u0000\u00bd\u00be\u0005i\u0000\u0000\u00be\u00bf\u0005n\u0000"+
		"\u0000\u00bf\u00c0\u0005t\u0000\u0000\u00c0\u0016\u0001\u0000\u0000\u0000"+
		"\u00c1\u00c2\u0005f\u0000\u0000\u00c2\u00c3\u0005o\u0000\u0000\u00c3\u00c4"+
		"\u0005r\u0000\u0000\u00c4\u0018\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005"+
		"i\u0000\u0000\u00c6\u00c7\u0005n\u0000\u0000\u00c7\u001a\u0001\u0000\u0000"+
		"\u0000\u00c8\u00c9\u0005r\u0000\u0000\u00c9\u00ca\u0005a\u0000\u0000\u00ca"+
		"\u00cb\u0005n\u0000\u0000\u00cb\u00cc\u0005g\u0000\u0000\u00cc\u00cd\u0005"+
		"e\u0000\u0000\u00cd\u001c\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005w\u0000"+
		"\u0000\u00cf\u00d0\u0005h\u0000\u0000\u00d0\u00d1\u0005i\u0000\u0000\u00d1"+
		"\u00d2\u0005l\u0000\u0000\u00d2\u00d3\u0005e\u0000\u0000\u00d3\u001e\u0001"+
		"\u0000\u0000\u0000\u00d4\u00d5\u0005i\u0000\u0000\u00d5\u00d6\u0005f\u0000"+
		"\u0000\u00d6 \u0001\u0000\u0000\u0000\u00d7\u00d8\u0005e\u0000\u0000\u00d8"+
		"\u00d9\u0005l\u0000\u0000\u00d9\u00da\u0005i\u0000\u0000\u00da\u00db\u0005"+
		"f\u0000\u0000\u00db\"\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005e\u0000"+
		"\u0000\u00dd\u00de\u0005l\u0000\u0000\u00de\u00df\u0005s\u0000\u0000\u00df"+
		"\u00e0\u0005e\u0000\u0000\u00e0$\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005"+
		"s\u0000\u0000\u00e2\u00e3\u0005i\u0000\u0000\u00e3\u00e4\u0005z\u0000"+
		"\u0000\u00e4\u00e5\u0005e\u0000\u0000\u00e5&\u0001\u0000\u0000\u0000\u00e6"+
		"\u00e7\u0005a\u0000\u0000\u00e7\u00e8\u0005d\u0000\u0000\u00e8\u00e9\u0005"+
		"d\u0000\u0000\u00e9(\u0001\u0000\u0000\u0000\u00ea\u00eb\u0005r\u0000"+
		"\u0000\u00eb\u00ec\u0005e\u0000\u0000\u00ec\u00ed\u0005m\u0000\u0000\u00ed"+
		"\u00ee\u0005o\u0000\u0000\u00ee\u00ef\u0005v\u0000\u0000\u00ef\u00f0\u0005"+
		"e\u0000\u0000\u00f0*\u0001\u0000\u0000\u0000\u00f1\u00f2\u0005a\u0000"+
		"\u0000\u00f2\u00f3\u0005n\u0000\u0000\u00f3\u00f4\u0005d\u0000\u0000\u00f4"+
		",\u0001\u0000\u0000\u0000\u00f5\u00f6\u0005o\u0000\u0000\u00f6\u00f7\u0005"+
		"r\u0000\u0000\u00f7.\u0001\u0000\u0000\u0000\u00f8\u00f9\u0005r\u0000"+
		"\u0000\u00f9\u00fa\u0005e\u0000\u0000\u00fa\u00fb\u0005t\u0000\u0000\u00fb"+
		"\u00fc\u0005u\u0000\u0000\u00fc\u00fd\u0005r\u0000\u0000\u00fd\u00fe\u0005"+
		"n\u0000\u0000\u00fe0\u0001\u0000\u0000\u0000\u00ff\u0100\u0005p\u0000"+
		"\u0000\u0100\u0101\u0005r\u0000\u0000\u0101\u0102\u0005i\u0000\u0000\u0102"+
		"\u0103\u0005n\u0000\u0000\u0103\u0104\u0005t\u0000\u0000\u01042\u0001"+
		"\u0000\u0000\u0000\u0105\u0106\u0005i\u0000\u0000\u0106\u0107\u0005n\u0000"+
		"\u0000\u0107\u0108\u0005p\u0000\u0000\u0108\u0109\u0005u\u0000\u0000\u0109"+
		"\u010a\u0005t\u0000\u0000\u010a4\u0001\u0000\u0000\u0000\u010b\u010c\u0005"+
		"+\u0000\u0000\u010c6\u0001\u0000\u0000\u0000\u010d\u010e\u0005-\u0000"+
		"\u0000\u010e8\u0001\u0000\u0000\u0000\u010f\u0110\u0005*\u0000\u0000\u0110"+
		":\u0001\u0000\u0000\u0000\u0111\u0112\u0005/\u0000\u0000\u0112<\u0001"+
		"\u0000\u0000\u0000\u0113\u0114\u0005*\u0000\u0000\u0114\u0115\u0005*\u0000"+
		"\u0000\u0115>\u0001\u0000\u0000\u0000\u0116\u0117\u0005/\u0000\u0000\u0117"+
		"\u0118\u0005/\u0000\u0000\u0118@\u0001\u0000\u0000\u0000\u0119\u011a\u0005"+
		"%\u0000\u0000\u011aB\u0001\u0000\u0000\u0000\u011b\u011c\u0005[\u0000"+
		"\u0000\u011cD\u0001\u0000\u0000\u0000\u011d\u011e\u0005]\u0000\u0000\u011e"+
		"F\u0001\u0000\u0000\u0000\u011f\u0120\u0005(\u0000\u0000\u0120H\u0001"+
		"\u0000\u0000\u0000\u0121\u0122\u0005)\u0000\u0000\u0122J\u0001\u0000\u0000"+
		"\u0000\u0123\u0124\u0005=\u0000\u0000\u0124L\u0001\u0000\u0000\u0000\u0125"+
		"\u0126\u0005=\u0000\u0000\u0126\u0127\u0005=\u0000\u0000\u0127N\u0001"+
		"\u0000\u0000\u0000\u0128\u0129\u0005!\u0000\u0000\u0129\u012a\u0005=\u0000"+
		"\u0000\u012aP\u0001\u0000\u0000\u0000\u012b\u012c\u0005>\u0000\u0000\u012c"+
		"R\u0001\u0000\u0000\u0000\u012d\u012e\u0005<\u0000\u0000\u012eT\u0001"+
		"\u0000\u0000\u0000\u012f\u0130\u0005>\u0000\u0000\u0130\u0131\u0005=\u0000"+
		"\u0000\u0131V\u0001\u0000\u0000\u0000\u0132\u0133\u0005<\u0000\u0000\u0133"+
		"\u0134\u0005=\u0000\u0000\u0134X\u0001\u0000\u0000\u0000\u0135\u0136\u0005"+
		",\u0000\u0000\u0136Z\u0001\u0000\u0000\u0000\u0137\u0138\u0005:\u0000"+
		"\u0000\u0138\\\u0001\u0000\u0000\u0000\u0139\u013a\u0005.\u0000\u0000"+
		"\u013a^\u0001\u0000\u0000\u0000\u013b\u013f\u0007\u0006\u0000\u0000\u013c"+
		"\u013e\u0007\u0007\u0000\u0000\u013d\u013c\u0001\u0000\u0000\u0000\u013e"+
		"\u0141\u0001\u0000\u0000\u0000\u013f\u013d\u0001\u0000\u0000\u0000\u013f"+
		"\u0140\u0001\u0000\u0000\u0000\u0140`\u0001\u0000\u0000\u0000\u0141\u013f"+
		"\u0001\u0000\u0000\u0000\u0012\u0000dimpry~\u0085\u0089\u008e\u0090\u0093"+
		"\u0098\u009e\u00a6\u00ae\u013f\u0002\u0001\u0001\u0000\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}