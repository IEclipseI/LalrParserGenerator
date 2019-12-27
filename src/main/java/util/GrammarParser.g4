grammar GrammarParser;

@header {
}

s:file;
file: terminals rules;
terminals: terminal_def*;
terminal_def:TERMINAL_NAME ':' REGEX ';';
TERMINAL_NAME:[A-Z]([A-Z0-9]*);
REGEX: '"' (~[\\"] | '\\' [\\"])* '"';

rules: start rul*;
start: 'start' TEXT '->' RULE_NAME action ';';
rul:RULE_NAME TEXT '->' (TERMINAL_NAME | RULE_NAME)* action';';
action: TEXT;
RULE_NAME: [a-z]([a-z0-9]*);
TEXT: '{'.*?'}';
WS: [ \t\r\n]+ -> skip;
