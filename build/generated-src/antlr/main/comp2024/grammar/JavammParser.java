// Generated from comp2024/grammar/Javamm.g4 by ANTLR 4.5.3

    package pt.up.fe.comp2024;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavammParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EQUALS=1, SEMI=2, LCURLY=3, RCURLY=4, LPAREN=5, RPAREN=6, LSTRPAREN=7, 
		RSTRPAREN=8, MUL=9, ADD=10, SUB=11, LT=12, DIV=13, AND=14, DOT=15, DOT3=16, 
		COMMA=17, NOT=18, CLASS=19, INT=20, PUBLIC=21, BOOL=22, RETURN=23, IMPORT=24, 
		EXTENDS=25, STATIC=26, VOID=27, MAIN=28, STRING=29, IF=30, ELSE=31, WHILE=32, 
		LENGTH=33, NEW=34, TRUE=35, FALSE=36, THIS=37, INTEGER=38, ID=39, WS=40, 
		MULTI_LINE_COMMENT=41, SINGLE_LINE_COMMENT=42;
	public static final int
		RULE_program = 0, RULE_importDecl = 1, RULE_classDecl = 2, RULE_varDecl = 3, 
		RULE_type = 4, RULE_methodDecl = 5, RULE_mainMethod = 6, RULE_param = 7, 
		RULE_stmt = 8, RULE_expr = 9;
	public static final String[] ruleNames = {
		"program", "importDecl", "classDecl", "varDecl", "type", "methodDecl", 
		"mainMethod", "param", "stmt", "expr"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "';'", "'{'", "'}'", "'('", "')'", "'['", "']'", "'*'", "'+'", 
		"'-'", "'<'", "'/'", "'&&'", "'.'", "'...'", "','", "'!'", "'class'", 
		"'int'", "'public'", "'boolean'", "'return'", "'import'", "'extends'", 
		"'static'", "'void'", "'main'", "'String'", "'if'", "'else'", "'while'", 
		"'length'", "'new'", "'true'", "'false'", "'this'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "EQUALS", "SEMI", "LCURLY", "RCURLY", "LPAREN", "RPAREN", "LSTRPAREN", 
		"RSTRPAREN", "MUL", "ADD", "SUB", "LT", "DIV", "AND", "DOT", "DOT3", "COMMA", 
		"NOT", "CLASS", "INT", "PUBLIC", "BOOL", "RETURN", "IMPORT", "EXTENDS", 
		"STATIC", "VOID", "MAIN", "STRING", "IF", "ELSE", "WHILE", "LENGTH", "NEW", 
		"TRUE", "FALSE", "THIS", "INTEGER", "ID", "WS", "MULTI_LINE_COMMENT", 
		"SINGLE_LINE_COMMENT"
	};
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

	@Override
	public String getGrammarFileName() { return "Javamm.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavammParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public ClassDeclContext classDecl() {
			return getRuleContext(ClassDeclContext.class,0);
		}
		public TerminalNode EOF() { return getToken(JavammParser.EOF, 0); }
		public List<ImportDeclContext> importDecl() {
			return getRuleContexts(ImportDeclContext.class);
		}
		public ImportDeclContext importDecl(int i) {
			return getRuleContext(ImportDeclContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(20);
				importDecl();
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(26);
			classDecl();
			setState(27);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDeclContext extends ParserRuleContext {
		public Token ID;
		public List<Token> value = new ArrayList<Token>();
		public Token MAIN;
		public Token STRING;
		public Token LENGTH;
		public Token _tset377;
		public Token _tset392;
		public TerminalNode IMPORT() { return getToken(JavammParser.IMPORT, 0); }
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public List<TerminalNode> MAIN() { return getTokens(JavammParser.MAIN); }
		public TerminalNode MAIN(int i) {
			return getToken(JavammParser.MAIN, i);
		}
		public List<TerminalNode> STRING() { return getTokens(JavammParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(JavammParser.STRING, i);
		}
		public List<TerminalNode> LENGTH() { return getTokens(JavammParser.LENGTH); }
		public TerminalNode LENGTH(int i) {
			return getToken(JavammParser.LENGTH, i);
		}
		public List<TerminalNode> DOT() { return getTokens(JavammParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(JavammParser.DOT, i);
		}
		public ImportDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterImportDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitImportDecl(this);
		}
	}

	public final ImportDeclContext importDecl() throws RecognitionException {
		ImportDeclContext _localctx = new ImportDeclContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(29);
			match(IMPORT);
			setState(30);
			((ImportDeclContext)_localctx)._tset377 = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
				((ImportDeclContext)_localctx)._tset377 = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			((ImportDeclContext)_localctx).value.add(((ImportDeclContext)_localctx)._tset377);
			setState(35);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(31);
				match(DOT);
				setState(32);
				((ImportDeclContext)_localctx)._tset392 = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((ImportDeclContext)_localctx)._tset392 = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				((ImportDeclContext)_localctx).value.add(((ImportDeclContext)_localctx)._tset392);
				}
				}
				setState(37);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(38);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclContext extends ParserRuleContext {
		public Token className;
		public Token superclassName;
		public TerminalNode CLASS() { return getToken(JavammParser.CLASS, 0); }
		public TerminalNode LCURLY() { return getToken(JavammParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavammParser.RCURLY, 0); }
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public List<TerminalNode> MAIN() { return getTokens(JavammParser.MAIN); }
		public TerminalNode MAIN(int i) {
			return getToken(JavammParser.MAIN, i);
		}
		public List<TerminalNode> STRING() { return getTokens(JavammParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(JavammParser.STRING, i);
		}
		public List<TerminalNode> LENGTH() { return getTokens(JavammParser.LENGTH); }
		public TerminalNode LENGTH(int i) {
			return getToken(JavammParser.LENGTH, i);
		}
		public TerminalNode EXTENDS() { return getToken(JavammParser.EXTENDS, 0); }
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<MethodDeclContext> methodDecl() {
			return getRuleContexts(MethodDeclContext.class);
		}
		public MethodDeclContext methodDecl(int i) {
			return getRuleContext(MethodDeclContext.class,i);
		}
		public ClassDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterClassDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitClassDecl(this);
		}
	}

	public final ClassDeclContext classDecl() throws RecognitionException {
		ClassDeclContext _localctx = new ClassDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(CLASS);
			setState(41);
			((ClassDeclContext)_localctx).className = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
				((ClassDeclContext)_localctx).className = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(44);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(42);
				match(EXTENDS);
				setState(43);
				((ClassDeclContext)_localctx).superclassName = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((ClassDeclContext)_localctx).superclassName = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
			}

			setState(46);
			match(LCURLY);
			setState(50);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(47);
					varDecl();
					}
					} 
				}
				setState(52);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << PUBLIC) | (1L << BOOL) | (1L << STATIC) | (1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) {
				{
				{
				setState(53);
				methodDecl();
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(59);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclContext extends ParserRuleContext {
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public List<TerminalNode> ID() { return getTokens(JavammParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JavammParser.ID, i);
		}
		public List<TerminalNode> MAIN() { return getTokens(JavammParser.MAIN); }
		public TerminalNode MAIN(int i) {
			return getToken(JavammParser.MAIN, i);
		}
		public List<TerminalNode> STRING() { return getTokens(JavammParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(JavammParser.STRING, i);
		}
		public List<TerminalNode> LENGTH() { return getTokens(JavammParser.LENGTH); }
		public TerminalNode LENGTH(int i) {
			return getToken(JavammParser.LENGTH, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitVarDecl(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_varDecl);
		int _la;
		try {
			setState(75);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				type();
				setState(62);
				((VarDeclContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((VarDeclContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(63);
					match(COMMA);
					setState(64);
					((VarDeclContext)_localctx).name = _input.LT(1);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
						((VarDeclContext)_localctx).name = (Token)_errHandler.recoverInline(this);
					} else {
						consume();
					}
					}
					}
					setState(69);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(70);
				match(SEMI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				match(STRING);
				setState(73);
				((VarDeclContext)_localctx).name = match(ID);
				setState(74);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public boolean isArray = false;
		public boolean isVarArgs = false;
		public Token name;
		public TerminalNode INT() { return getToken(JavammParser.INT, 0); }
		public TerminalNode LSTRPAREN() { return getToken(JavammParser.LSTRPAREN, 0); }
		public TerminalNode RSTRPAREN() { return getToken(JavammParser.RSTRPAREN, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode DOT3() { return getToken(JavammParser.DOT3, 0); }
		public TerminalNode BOOL() { return getToken(JavammParser.BOOL, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		int _la;
		try {
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				((TypeContext)_localctx).name = match(INT);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				((TypeContext)_localctx).isArray = true;
				setState(79);
				((TypeContext)_localctx).name = match(INT);
				setState(80);
				match(LSTRPAREN);
				setState(81);
				match(RSTRPAREN);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				((TypeContext)_localctx).isArray = true;
				setState(83);
				((TypeContext)_localctx).name = match(STRING);
				setState(84);
				match(LSTRPAREN);
				setState(85);
				match(RSTRPAREN);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				((TypeContext)_localctx).isArray =  true; ((TypeContext)_localctx).isVarArgs = true;
				setState(87);
				((TypeContext)_localctx).name = match(INT);
				setState(88);
				match(DOT3);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(89);
				((TypeContext)_localctx).name = match(BOOL);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(90);
				((TypeContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((TypeContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(91);
				((TypeContext)_localctx).name = match(STRING);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclContext extends ParserRuleContext {
		public boolean isPublic = false;
		public boolean isMainMethod = false;
		public Token name;
		public Token name_pars;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public TerminalNode LCURLY() { return getToken(JavammParser.LCURLY, 0); }
		public TerminalNode RETURN() { return getToken(JavammParser.RETURN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public TerminalNode RCURLY() { return getToken(JavammParser.RCURLY, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public List<TerminalNode> MAIN() { return getTokens(JavammParser.MAIN); }
		public TerminalNode MAIN(int i) {
			return getToken(JavammParser.MAIN, i);
		}
		public List<TerminalNode> STRING() { return getTokens(JavammParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(JavammParser.STRING, i);
		}
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public TerminalNode PUBLIC() { return getToken(JavammParser.PUBLIC, 0); }
		public List<ParamContext> param() {
			return getRuleContexts(ParamContext.class);
		}
		public ParamContext param(int i) {
			return getRuleContext(ParamContext.class,i);
		}
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public TerminalNode STATIC() { return getToken(JavammParser.STATIC, 0); }
		public TerminalNode VOID() { return getToken(JavammParser.VOID, 0); }
		public TerminalNode LSTRPAREN() { return getToken(JavammParser.LSTRPAREN, 0); }
		public TerminalNode RSTRPAREN() { return getToken(JavammParser.RSTRPAREN, 0); }
		public MethodDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterMethodDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitMethodDecl(this);
		}
	}

	public final MethodDeclContext methodDecl() throws RecognitionException {
		MethodDeclContext _localctx = new MethodDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_methodDecl);
		int _la;
		try {
			int _alt;
			setState(158);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				_la = _input.LA(1);
				if (_la==PUBLIC) {
					{
					setState(94);
					match(PUBLIC);
					((MethodDeclContext)_localctx).isPublic = true;
					}
				}

				setState(98);
				type();
				setState(99);
				((MethodDeclContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((MethodDeclContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(100);
				match(LPAREN);
				setState(109);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INT) | (1L << BOOL) | (1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) {
					{
					setState(101);
					param();
					setState(106);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(102);
						match(COMMA);
						setState(103);
						param();
						}
						}
						setState(108);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(111);
				match(RPAREN);
				setState(112);
				match(LCURLY);
				setState(116);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(113);
						varDecl();
						}
						} 
					}
					setState(118);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCURLY) | (1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << IF) | (1L << WHILE) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(119);
					stmt();
					}
					}
					setState(124);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(125);
				match(RETURN);
				setState(126);
				expr(0);
				setState(127);
				match(SEMI);
				setState(128);
				match(RCURLY);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				_la = _input.LA(1);
				if (_la==PUBLIC) {
					{
					setState(130);
					match(PUBLIC);
					((MethodDeclContext)_localctx).isPublic = true;
					}
				}

				((MethodDeclContext)_localctx).isMainMethod = true;
				setState(135);
				match(STATIC);
				setState(136);
				match(VOID);
				setState(137);
				((MethodDeclContext)_localctx).name = match(MAIN);
				setState(138);
				match(LPAREN);
				setState(139);
				match(STRING);
				setState(140);
				match(LSTRPAREN);
				setState(141);
				match(RSTRPAREN);
				setState(142);
				((MethodDeclContext)_localctx).name_pars = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((MethodDeclContext)_localctx).name_pars = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(143);
				match(RPAREN);
				setState(144);
				match(LCURLY);
				setState(148);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(145);
						varDecl();
						}
						} 
					}
					setState(150);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				}
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCURLY) | (1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << IF) | (1L << WHILE) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(151);
					stmt();
					}
					}
					setState(156);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(157);
				match(RCURLY);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainMethodContext extends ParserRuleContext {
		public MethodDeclContext methodDecl() {
			return getRuleContext(MethodDeclContext.class,0);
		}
		public MainMethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainMethod; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterMainMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitMainMethod(this);
		}
	}

	public final MainMethodContext mainMethod() throws RecognitionException {
		MainMethodContext _localctx = new MainMethodContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_mainMethod);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			methodDecl();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamContext extends ParserRuleContext {
		public TypeContext value;
		public Token name;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitParam(this);
		}
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_param);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			((ParamContext)_localctx).value = type();
			setState(163);
			((ParamContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
				((ParamContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	 
		public StmtContext() { }
		public void copyFrom(StmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BracketsStmtContext extends StmtContext {
		public TerminalNode LCURLY() { return getToken(JavammParser.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(JavammParser.RCURLY, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BracketsStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBracketsStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBracketsStmt(this);
		}
	}
	public static class IfStmtContext extends StmtContext {
		public TerminalNode IF() { return getToken(JavammParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(JavammParser.ELSE, 0); }
		public IfStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitIfStmt(this);
		}
	}
	public static class WhileStmtContext extends StmtContext {
		public TerminalNode WHILE() { return getToken(JavammParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public StmtContext stmt() {
			return getRuleContext(StmtContext.class,0);
		}
		public WhileStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitWhileStmt(this);
		}
	}
	public static class AssignStmtContext extends StmtContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQUALS() { return getToken(JavammParser.EQUALS, 0); }
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public AssignStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitAssignStmt(this);
		}
	}
	public static class SimpleStmtContext extends StmtContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JavammParser.SEMI, 0); }
		public SimpleStmtContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterSimpleStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitSimpleStmt(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_stmt);
		int _la;
		try {
			setState(195);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new AssignStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(165);
				expr(0);
				setState(166);
				match(EQUALS);
				setState(167);
				expr(0);
				setState(168);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new BracketsStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(170);
				match(LCURLY);
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LCURLY) | (1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << IF) | (1L << WHILE) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					{
					setState(171);
					stmt();
					}
					}
					setState(176);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(177);
				match(RCURLY);
				}
				break;
			case 3:
				_localctx = new IfStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(178);
				match(IF);
				setState(179);
				match(LPAREN);
				setState(180);
				expr(0);
				setState(181);
				match(RPAREN);
				setState(182);
				stmt();
				setState(183);
				match(ELSE);
				setState(184);
				stmt();
				}
				break;
			case 4:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(186);
				match(WHILE);
				setState(187);
				match(LPAREN);
				setState(188);
				expr(0);
				setState(189);
				match(RPAREN);
				setState(190);
				stmt();
				}
				break;
			case 5:
				_localctx = new SimpleStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(192);
				expr(0);
				setState(193);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParenthExprContext extends ExprContext {
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public ParenthExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterParenthExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitParenthExpr(this);
		}
	}
	public static class BooleanLiteralContext extends ExprContext {
		public Token value;
		public TerminalNode TRUE() { return getToken(JavammParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(JavammParser.FALSE, 0); }
		public BooleanLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBooleanLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBooleanLiteral(this);
		}
	}
	public static class BinaryExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MUL() { return getToken(JavammParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(JavammParser.DIV, 0); }
		public TerminalNode SUB() { return getToken(JavammParser.SUB, 0); }
		public TerminalNode ADD() { return getToken(JavammParser.ADD, 0); }
		public BinaryExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBinaryExpr(this);
		}
	}
	public static class ObjectDeclContext extends ExprContext {
		public Token name;
		public TerminalNode NEW() { return getToken(JavammParser.NEW, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public ObjectDeclContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterObjectDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitObjectDecl(this);
		}
	}
	public static class ArraySubContext extends ExprContext {
		public ExprContext array;
		public TerminalNode LSTRPAREN() { return getToken(JavammParser.LSTRPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RSTRPAREN() { return getToken(JavammParser.RSTRPAREN, 0); }
		public ArraySubContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterArraySub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitArraySub(this);
		}
	}
	public static class MethodCallContext extends ExprContext {
		public Token name;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode DOT() { return getToken(JavammParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public MethodCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitMethodCall(this);
		}
	}
	public static class NewMethodDeclContext extends ExprContext {
		public TerminalNode NEW() { return getToken(JavammParser.NEW, 0); }
		public MethodDeclContext methodDecl() {
			return getRuleContext(MethodDeclContext.class,0);
		}
		public NewMethodDeclContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterNewMethodDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitNewMethodDecl(this);
		}
	}
	public static class NotContext extends ExprContext {
		public TerminalNode NOT() { return getToken(JavammParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitNot(this);
		}
	}
	public static class LengthContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JavammParser.DOT, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public LengthContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterLength(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitLength(this);
		}
	}
	public static class VarRefExprContext extends ExprContext {
		public Token name;
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public VarRefExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterVarRefExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitVarRefExpr(this);
		}
	}
	public static class NewArrayDeclContext extends ExprContext {
		public TerminalNode NEW() { return getToken(JavammParser.NEW, 0); }
		public TerminalNode INT() { return getToken(JavammParser.INT, 0); }
		public TerminalNode LSTRPAREN() { return getToken(JavammParser.LSTRPAREN, 0); }
		public TerminalNode RSTRPAREN() { return getToken(JavammParser.RSTRPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NewArrayDeclContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterNewArrayDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitNewArrayDecl(this);
		}
	}
	public static class ThisContext extends ExprContext {
		public Token value;
		public TerminalNode THIS() { return getToken(JavammParser.THIS, 0); }
		public ThisContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterThis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitThis(this);
		}
	}
	public static class FunctionCallContext extends ExprContext {
		public Token name;
		public TerminalNode LPAREN() { return getToken(JavammParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(JavammParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode ID() { return getToken(JavammParser.ID, 0); }
		public TerminalNode MAIN() { return getToken(JavammParser.MAIN, 0); }
		public TerminalNode STRING() { return getToken(JavammParser.STRING, 0); }
		public TerminalNode LENGTH() { return getToken(JavammParser.LENGTH, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public FunctionCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitFunctionCall(this);
		}
	}
	public static class IntegerLiteralContext extends ExprContext {
		public Token value;
		public TerminalNode INTEGER() { return getToken(JavammParser.INTEGER, 0); }
		public IntegerLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitIntegerLiteral(this);
		}
	}
	public static class BooleanExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LT() { return getToken(JavammParser.LT, 0); }
		public TerminalNode AND() { return getToken(JavammParser.AND, 0); }
		public BooleanExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterBooleanExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitBooleanExpr(this);
		}
	}
	public static class ArrayDeclContext extends ExprContext {
		public TerminalNode LSTRPAREN() { return getToken(JavammParser.LSTRPAREN, 0); }
		public TerminalNode RSTRPAREN() { return getToken(JavammParser.RSTRPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JavammParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JavammParser.COMMA, i);
		}
		public ArrayDeclContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).enterArrayDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JavammListener ) ((JavammListener)listener).exitArrayDecl(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				_localctx = new ParenthExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(198);
				match(LPAREN);
				setState(199);
				expr(0);
				setState(200);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new NewArrayDeclContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(202);
				match(NEW);
				setState(203);
				match(INT);
				setState(204);
				match(LSTRPAREN);
				setState(206);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					setState(205);
					expr(0);
					}
				}

				setState(208);
				match(RSTRPAREN);
				}
				break;
			case 3:
				{
				_localctx = new ObjectDeclContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(209);
				match(NEW);
				setState(210);
				((ObjectDeclContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((ObjectDeclContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(211);
				match(LPAREN);
				setState(220);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					setState(212);
					expr(0);
					setState(217);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(213);
						match(COMMA);
						setState(214);
						expr(0);
						}
						}
						setState(219);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(222);
				match(RPAREN);
				}
				break;
			case 4:
				{
				_localctx = new FunctionCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				{
				setState(223);
				((FunctionCallContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((FunctionCallContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				setState(224);
				match(LPAREN);
				setState(233);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					setState(225);
					expr(0);
					setState(230);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(226);
						match(COMMA);
						setState(227);
						expr(0);
						}
						}
						setState(232);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(235);
				match(RPAREN);
				}
				break;
			case 5:
				{
				_localctx = new NewMethodDeclContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				match(NEW);
				setState(237);
				methodDecl();
				}
				break;
			case 6:
				{
				_localctx = new ThisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(238);
				((ThisContext)_localctx).value = match(THIS);
				}
				break;
			case 7:
				{
				_localctx = new ObjectDeclContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(239);
				match(NEW);
				setState(240);
				((ObjectDeclContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((ObjectDeclContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(241);
				match(LPAREN);
				setState(242);
				match(RPAREN);
				}
				break;
			case 8:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(243);
				match(NOT);
				setState(244);
				expr(10);
				}
				break;
			case 9:
				{
				_localctx = new ArrayDeclContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(245);
				match(LSTRPAREN);
				setState(254);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
					{
					setState(246);
					expr(0);
					setState(251);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(247);
						match(COMMA);
						setState(248);
						expr(0);
						}
						}
						setState(253);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(256);
				match(RSTRPAREN);
				}
				break;
			case 10:
				{
				_localctx = new BooleanLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(257);
				((BooleanLiteralContext)_localctx).value = match(TRUE);
				}
				break;
			case 11:
				{
				_localctx = new BooleanLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(258);
				((BooleanLiteralContext)_localctx).value = match(FALSE);
				}
				break;
			case 12:
				{
				_localctx = new IntegerLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(259);
				((IntegerLiteralContext)_localctx).value = match(INTEGER);
				}
				break;
			case 13:
				{
				_localctx = new VarRefExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(260);
				((VarRefExprContext)_localctx).name = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
					((VarRefExprContext)_localctx).name = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(300);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(298);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(263);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(264);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MUL || _la==DIV) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(265);
						expr(9);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(266);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(267);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(268);
						expr(8);
						}
						break;
					case 3:
						{
						_localctx = new BooleanExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(269);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(270);
						((BooleanExprContext)_localctx).op = match(LT);
						setState(271);
						expr(7);
						}
						break;
					case 4:
						{
						_localctx = new BooleanExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(272);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(273);
						((BooleanExprContext)_localctx).op = match(AND);
						setState(274);
						expr(6);
						}
						break;
					case 5:
						{
						_localctx = new ArraySubContext(new ExprContext(_parentctx, _parentState));
						((ArraySubContext)_localctx).array = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(275);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(276);
						match(LSTRPAREN);
						setState(277);
						expr(0);
						setState(278);
						match(RSTRPAREN);
						}
						break;
					case 6:
						{
						_localctx = new MethodCallContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(280);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(281);
						match(DOT);
						setState(282);
						((MethodCallContext)_localctx).name = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << ID))) != 0)) ) {
							((MethodCallContext)_localctx).name = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(283);
						match(LPAREN);
						setState(292);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LSTRPAREN) | (1L << NOT) | (1L << MAIN) | (1L << STRING) | (1L << LENGTH) | (1L << NEW) | (1L << TRUE) | (1L << FALSE) | (1L << THIS) | (1L << INTEGER) | (1L << ID))) != 0)) {
							{
							setState(284);
							expr(0);
							setState(289);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==COMMA) {
								{
								{
								setState(285);
								match(COMMA);
								setState(286);
								expr(0);
								}
								}
								setState(291);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(294);
						match(RPAREN);
						}
						break;
					case 7:
						{
						_localctx = new LengthContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(295);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(296);
						match(DOT);
						setState(297);
						match(LENGTH);
						}
						break;
					}
					} 
				}
				setState(302);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 17);
		case 5:
			return precpred(_ctx, 15);
		case 6:
			return precpred(_ctx, 13);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u0132\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\7\3$\n"+
		"\3\f\3\16\3\'\13\3\3\3\3\3\3\4\3\4\3\4\3\4\5\4/\n\4\3\4\3\4\7\4\63\n\4"+
		"\f\4\16\4\66\13\4\3\4\7\49\n\4\f\4\16\4<\13\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\7\5D\n\5\f\5\16\5G\13\5\3\5\3\5\3\5\3\5\3\5\5\5N\n\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6_\n\6\3\7\3\7\5\7c\n\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\7\7k\n\7\f\7\16\7n\13\7\5\7p\n\7\3\7\3\7\3\7"+
		"\7\7u\n\7\f\7\16\7x\13\7\3\7\7\7{\n\7\f\7\16\7~\13\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\5\7\u0087\n\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\7\7\u0095\n\7\f\7\16\7\u0098\13\7\3\7\7\7\u009b\n\7\f\7\16\7\u009e"+
		"\13\7\3\7\5\7\u00a1\n\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\7\n\u00af\n\n\f\n\16\n\u00b2\13\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00c6\n\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\5\13\u00d1\n\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\7\13\u00da\n\13\f\13\16\13\u00dd\13\13\5\13\u00df\n\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\7\13\u00e7\n\13\f\13\16\13\u00ea\13\13\5\13"+
		"\u00ec\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\7\13\u00fc\n\13\f\13\16\13\u00ff\13\13\5\13\u0101\n\13\3\13"+
		"\3\13\3\13\3\13\3\13\5\13\u0108\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\7\13\u0122\n\13\f\13\16\13\u0125\13\13\5\13\u0127\n\13"+
		"\3\13\3\13\3\13\3\13\7\13\u012d\n\13\f\13\16\13\u0130\13\13\3\13\2\3\24"+
		"\f\2\4\6\b\n\f\16\20\22\24\2\5\5\2\36\37##))\4\2\13\13\17\17\3\2\f\r\u015e"+
		"\2\31\3\2\2\2\4\37\3\2\2\2\6*\3\2\2\2\bM\3\2\2\2\n^\3\2\2\2\f\u00a0\3"+
		"\2\2\2\16\u00a2\3\2\2\2\20\u00a4\3\2\2\2\22\u00c5\3\2\2\2\24\u0107\3\2"+
		"\2\2\26\30\5\4\3\2\27\26\3\2\2\2\30\33\3\2\2\2\31\27\3\2\2\2\31\32\3\2"+
		"\2\2\32\34\3\2\2\2\33\31\3\2\2\2\34\35\5\6\4\2\35\36\7\2\2\3\36\3\3\2"+
		"\2\2\37 \7\32\2\2 %\t\2\2\2!\"\7\21\2\2\"$\t\2\2\2#!\3\2\2\2$\'\3\2\2"+
		"\2%#\3\2\2\2%&\3\2\2\2&(\3\2\2\2\'%\3\2\2\2()\7\4\2\2)\5\3\2\2\2*+\7\25"+
		"\2\2+.\t\2\2\2,-\7\33\2\2-/\t\2\2\2.,\3\2\2\2./\3\2\2\2/\60\3\2\2\2\60"+
		"\64\7\5\2\2\61\63\5\b\5\2\62\61\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64"+
		"\65\3\2\2\2\65:\3\2\2\2\66\64\3\2\2\2\679\5\f\7\28\67\3\2\2\29<\3\2\2"+
		"\2:8\3\2\2\2:;\3\2\2\2;=\3\2\2\2<:\3\2\2\2=>\7\6\2\2>\7\3\2\2\2?@\5\n"+
		"\6\2@E\t\2\2\2AB\7\23\2\2BD\t\2\2\2CA\3\2\2\2DG\3\2\2\2EC\3\2\2\2EF\3"+
		"\2\2\2FH\3\2\2\2GE\3\2\2\2HI\7\4\2\2IN\3\2\2\2JK\7\37\2\2KL\7)\2\2LN\7"+
		"\4\2\2M?\3\2\2\2MJ\3\2\2\2N\t\3\2\2\2O_\7\26\2\2PQ\b\6\1\2QR\7\26\2\2"+
		"RS\7\t\2\2S_\7\n\2\2TU\b\6\1\2UV\7\37\2\2VW\7\t\2\2W_\7\n\2\2XY\b\6\1"+
		"\2YZ\7\26\2\2Z_\7\22\2\2[_\7\30\2\2\\_\t\2\2\2]_\7\37\2\2^O\3\2\2\2^P"+
		"\3\2\2\2^T\3\2\2\2^X\3\2\2\2^[\3\2\2\2^\\\3\2\2\2^]\3\2\2\2_\13\3\2\2"+
		"\2`a\7\27\2\2ac\b\7\1\2b`\3\2\2\2bc\3\2\2\2cd\3\2\2\2de\5\n\6\2ef\t\2"+
		"\2\2fo\7\7\2\2gl\5\20\t\2hi\7\23\2\2ik\5\20\t\2jh\3\2\2\2kn\3\2\2\2lj"+
		"\3\2\2\2lm\3\2\2\2mp\3\2\2\2nl\3\2\2\2og\3\2\2\2op\3\2\2\2pq\3\2\2\2q"+
		"r\7\b\2\2rv\7\5\2\2su\5\b\5\2ts\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2"+
		"w|\3\2\2\2xv\3\2\2\2y{\5\22\n\2zy\3\2\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2"+
		"\2}\177\3\2\2\2~|\3\2\2\2\177\u0080\7\31\2\2\u0080\u0081\5\24\13\2\u0081"+
		"\u0082\7\4\2\2\u0082\u0083\7\6\2\2\u0083\u00a1\3\2\2\2\u0084\u0085\7\27"+
		"\2\2\u0085\u0087\b\7\1\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087"+
		"\u0088\3\2\2\2\u0088\u0089\b\7\1\2\u0089\u008a\7\34\2\2\u008a\u008b\7"+
		"\35\2\2\u008b\u008c\7\36\2\2\u008c\u008d\7\7\2\2\u008d\u008e\7\37\2\2"+
		"\u008e\u008f\7\t\2\2\u008f\u0090\7\n\2\2\u0090\u0091\t\2\2\2\u0091\u0092"+
		"\7\b\2\2\u0092\u0096\7\5\2\2\u0093\u0095\5\b\5\2\u0094\u0093\3\2\2\2\u0095"+
		"\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u009c\3\2"+
		"\2\2\u0098\u0096\3\2\2\2\u0099\u009b\5\22\n\2\u009a\u0099\3\2\2\2\u009b"+
		"\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009f\3\2"+
		"\2\2\u009e\u009c\3\2\2\2\u009f\u00a1\7\6\2\2\u00a0b\3\2\2\2\u00a0\u0086"+
		"\3\2\2\2\u00a1\r\3\2\2\2\u00a2\u00a3\5\f\7\2\u00a3\17\3\2\2\2\u00a4\u00a5"+
		"\5\n\6\2\u00a5\u00a6\t\2\2\2\u00a6\21\3\2\2\2\u00a7\u00a8\5\24\13\2\u00a8"+
		"\u00a9\7\3\2\2\u00a9\u00aa\5\24\13\2\u00aa\u00ab\7\4\2\2\u00ab\u00c6\3"+
		"\2\2\2\u00ac\u00b0\7\5\2\2\u00ad\u00af\5\22\n\2\u00ae\u00ad\3\2\2\2\u00af"+
		"\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b3\3\2"+
		"\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00c6\7\6\2\2\u00b4\u00b5\7 \2\2\u00b5"+
		"\u00b6\7\7\2\2\u00b6\u00b7\5\24\13\2\u00b7\u00b8\7\b\2\2\u00b8\u00b9\5"+
		"\22\n\2\u00b9\u00ba\7!\2\2\u00ba\u00bb\5\22\n\2\u00bb\u00c6\3\2\2\2\u00bc"+
		"\u00bd\7\"\2\2\u00bd\u00be\7\7\2\2\u00be\u00bf\5\24\13\2\u00bf\u00c0\7"+
		"\b\2\2\u00c0\u00c1\5\22\n\2\u00c1\u00c6\3\2\2\2\u00c2\u00c3\5\24\13\2"+
		"\u00c3\u00c4\7\4\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00a7\3\2\2\2\u00c5\u00ac"+
		"\3\2\2\2\u00c5\u00b4\3\2\2\2\u00c5\u00bc\3\2\2\2\u00c5\u00c2\3\2\2\2\u00c6"+
		"\23\3\2\2\2\u00c7\u00c8\b\13\1\2\u00c8\u00c9\7\7\2\2\u00c9\u00ca\5\24"+
		"\13\2\u00ca\u00cb\7\b\2\2\u00cb\u0108\3\2\2\2\u00cc\u00cd\7$\2\2\u00cd"+
		"\u00ce\7\26\2\2\u00ce\u00d0\7\t\2\2\u00cf\u00d1\5\24\13\2\u00d0\u00cf"+
		"\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u0108\7\n\2\2\u00d3"+
		"\u00d4\7$\2\2\u00d4\u00d5\t\2\2\2\u00d5\u00de\7\7\2\2\u00d6\u00db\5\24"+
		"\13\2\u00d7\u00d8\7\23\2\2\u00d8\u00da\5\24\13\2\u00d9\u00d7\3\2\2\2\u00da"+
		"\u00dd\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00df\3\2"+
		"\2\2\u00dd\u00db\3\2\2\2\u00de\u00d6\3\2\2\2\u00de\u00df\3\2\2\2\u00df"+
		"\u00e0\3\2\2\2\u00e0\u0108\7\b\2\2\u00e1\u00e2\t\2\2\2\u00e2\u00eb\7\7"+
		"\2\2\u00e3\u00e8\5\24\13\2\u00e4\u00e5\7\23\2\2\u00e5\u00e7\5\24\13\2"+
		"\u00e6\u00e4\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8\u00e9"+
		"\3\2\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00e8\3\2\2\2\u00eb\u00e3\3\2\2\2\u00eb"+
		"\u00ec\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u0108\7\b\2\2\u00ee\u00ef\7$"+
		"\2\2\u00ef\u0108\5\f\7\2\u00f0\u0108\7\'\2\2\u00f1\u00f2\7$\2\2\u00f2"+
		"\u00f3\t\2\2\2\u00f3\u00f4\7\7\2\2\u00f4\u0108\7\b\2\2\u00f5\u00f6\7\24"+
		"\2\2\u00f6\u0108\5\24\13\f\u00f7\u0100\7\t\2\2\u00f8\u00fd\5\24\13\2\u00f9"+
		"\u00fa\7\23\2\2\u00fa\u00fc\5\24\13\2\u00fb\u00f9\3\2\2\2\u00fc\u00ff"+
		"\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff"+
		"\u00fd\3\2\2\2\u0100\u00f8\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0102\3\2"+
		"\2\2\u0102\u0108\7\n\2\2\u0103\u0108\7%\2\2\u0104\u0108\7&\2\2\u0105\u0108"+
		"\7(\2\2\u0106\u0108\t\2\2\2\u0107\u00c7\3\2\2\2\u0107\u00cc\3\2\2\2\u0107"+
		"\u00d3\3\2\2\2\u0107\u00e1\3\2\2\2\u0107\u00ee\3\2\2\2\u0107\u00f0\3\2"+
		"\2\2\u0107\u00f1\3\2\2\2\u0107\u00f5\3\2\2\2\u0107\u00f7\3\2\2\2\u0107"+
		"\u0103\3\2\2\2\u0107\u0104\3\2\2\2\u0107\u0105\3\2\2\2\u0107\u0106\3\2"+
		"\2\2\u0108\u012e\3\2\2\2\u0109\u010a\f\n\2\2\u010a\u010b\t\3\2\2\u010b"+
		"\u012d\5\24\13\13\u010c\u010d\f\t\2\2\u010d\u010e\t\4\2\2\u010e\u012d"+
		"\5\24\13\n\u010f\u0110\f\b\2\2\u0110\u0111\7\16\2\2\u0111\u012d\5\24\13"+
		"\t\u0112\u0113\f\7\2\2\u0113\u0114\7\20\2\2\u0114\u012d\5\24\13\b\u0115"+
		"\u0116\f\23\2\2\u0116\u0117\7\t\2\2\u0117\u0118\5\24\13\2\u0118\u0119"+
		"\7\n\2\2\u0119\u012d\3\2\2\2\u011a\u011b\f\21\2\2\u011b\u011c\7\21\2\2"+
		"\u011c\u011d\t\2\2\2\u011d\u0126\7\7\2\2\u011e\u0123\5\24\13\2\u011f\u0120"+
		"\7\23\2\2\u0120\u0122\5\24\13\2\u0121\u011f\3\2\2\2\u0122\u0125\3\2\2"+
		"\2\u0123\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0127\3\2\2\2\u0125\u0123"+
		"\3\2\2\2\u0126\u011e\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0128\3\2\2\2\u0128"+
		"\u012d\7\b\2\2\u0129\u012a\f\17\2\2\u012a\u012b\7\21\2\2\u012b\u012d\7"+
		"#\2\2\u012c\u0109\3\2\2\2\u012c\u010c\3\2\2\2\u012c\u010f\3\2\2\2\u012c"+
		"\u0112\3\2\2\2\u012c\u0115\3\2\2\2\u012c\u011a\3\2\2\2\u012c\u0129\3\2"+
		"\2\2\u012d\u0130\3\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f"+
		"\25\3\2\2\2\u0130\u012e\3\2\2\2!\31%.\64:EM^blov|\u0086\u0096\u009c\u00a0"+
		"\u00b0\u00c5\u00d0\u00db\u00de\u00e8\u00eb\u00fd\u0100\u0107\u0123\u0126"+
		"\u012c\u012e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}