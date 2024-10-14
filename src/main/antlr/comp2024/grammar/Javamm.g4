grammar Javamm;

@header {
    package pt.up.fe.comp2024;
}

EQUALS : '=';
SEMI : ';' ;
LCURLY : '{' ;
RCURLY : '}' ;
LPAREN : '(' ;
RPAREN : ')' ;
LSTRPAREN : '[' ;
RSTRPAREN : ']' ;
MUL : '*' ;
ADD : '+' ;
SUB : '-';
LT : '<';
DIV : '/';
AND : '&&';
DOT : '.';
DOT3 : '...';
COMMA : ',';
NOT : '!';

CLASS : 'class' ;
INT : 'int' ;
PUBLIC : 'public' ;
BOOL : 'boolean' ;
RETURN : 'return' ;
IMPORT : 'import';
EXTENDS : 'extends';
STATIC : 'static';
VOID : 'void';
MAIN : 'main';
STRING : 'String';
IF : 'if';
ELSE : 'else';
WHILE : 'while';
LENGTH : 'length';
NEW : 'new';
TRUE : 'true';
FALSE : 'false';
THIS : 'this';



INTEGER : ([1-9][0-9]*) | [0];
ID : [a-zA-Z_$][a-zA-Z_0-9$]*;

WS : [ \t\n\r\f]+ -> skip ;
MULTI_LINE_COMMENT: '/*' .*? '*/' -> skip;
SINGLE_LINE_COMMENT : '//' .*? '\n' -> skip ;


program
    : (importDecl)* classDecl EOF
    ;

importDecl
    : IMPORT value+=(ID|MAIN|STRING|LENGTH)
    (DOT value+=(ID|MAIN|STRING|LENGTH))*
    SEMI
    ;

classDecl
    : CLASS className=(ID|MAIN|STRING|LENGTH)
        (EXTENDS superclassName=(ID|MAIN|STRING|LENGTH))?
        LCURLY
        varDecl* methodDecl*
        RCURLY
    ;

varDecl
    : type name=(ID|MAIN|STRING|LENGTH) (COMMA name=(ID|MAIN|STRING|LENGTH))* SEMI
    | STRING name=ID SEMI
    ;

type locals[boolean isArray=false,boolean isVarArgs=false]
    :  name = INT
    | {$isArray=true;} name = INT LSTRPAREN RSTRPAREN
    | {$isArray=true;} name = STRING LSTRPAREN RSTRPAREN
    | {$isArray = true; $isVarArgs=true;} name = INT DOT3
    | name = BOOL
    | name = (ID|MAIN|STRING|LENGTH)
    | name = STRING
    ;

methodDecl locals[boolean isPublic=false,boolean isMainMethod=false]
    : (PUBLIC {$isPublic=true;})?
        type name=(ID|MAIN|STRING|LENGTH)
        LPAREN (param (COMMA param)*)? RPAREN
        LCURLY varDecl* stmt* RETURN expr SEMI RCURLY
    | (PUBLIC {$isPublic=true;})? {$isMainMethod=true;} STATIC VOID name=MAIN
       LPAREN STRING LSTRPAREN RSTRPAREN name_pars=(ID|MAIN|STRING|LENGTH) RPAREN
       LCURLY varDecl* stmt* RCURLY
     ;

mainMethod
    :methodDecl
    ;

param
    : value = type name=(ID|MAIN|STRING|LENGTH)
    ;

stmt
    : expr EQUALS expr SEMI #AssignStmt
    | LCURLY stmt* RCURLY #BracketsStmt
    | IF LPAREN expr RPAREN stmt  ELSE stmt #IfStmt
    | WHILE LPAREN expr RPAREN stmt #WhileStmt
    | expr SEMI #SimpleStmt
    ;

expr
    :  LPAREN expr RPAREN #ParenthExpr
    | NEW INT LSTRPAREN (expr)? RSTRPAREN #NewArrayDecl
    | NEW name=(ID|MAIN|STRING|LENGTH) LPAREN (expr (COMMA expr)*)? RPAREN #ObjectDecl
    | array=expr LSTRPAREN expr RSTRPAREN #ArraySub
   | (name=(ID|MAIN|STRING|LENGTH)) LPAREN
          ( expr ( COMMA expr ) * ) ?
          RPAREN #FunctionCall
    | expr DOT name=(ID|MAIN|STRING|LENGTH) LPAREN (expr ( COMMA expr)*)? RPAREN #MethodCall
    | NEW methodDecl #NewMethodDecl
    | expr DOT LENGTH #Length
    | value = THIS #This
    | NEW name=(ID|MAIN|STRING|LENGTH) LPAREN RPAREN #ObjectDecl
    | NOT expr #Not
    | LSTRPAREN
     ( expr ( COMMA expr ) * ) ?
     RSTRPAREN #ArrayDecl

    |  expr op = (MUL| DIV) expr #BinaryExpr
    | expr op = (SUB | ADD)  expr #BinaryExpr
    | expr op =  LT  expr #BooleanExpr
    | expr op =   AND  expr #BooleanExpr
    | value = TRUE #BooleanLiteral
    | value = FALSE #BooleanLiteral
    | value = INTEGER #IntegerLiteral
    | name=(ID|MAIN|STRING|LENGTH) #VarRefExpr
    ;





