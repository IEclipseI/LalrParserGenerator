grammar GrammarParser;

@header {
}

s:file;
file: terminals rules;
terminals: terminal_def*;
terminal_def:TERMINAL_NAME ':' REGEX ';';
TERMINAL_NAME:[A-Z]([A-Z0-9]*);
REGEX: '"' (~[\\"] | '\\' [\\"])* '"';
WS: [ \t\r\n]+ -> skip;

rules: start rul*;
start: 'start' '->' RULE_NAME ';';
rul:RULE_NAME '->' (TERMINAL_NAME | RULE_NAME)* ';';
RULE_NAME: [a-z]([a-z0-9]*);