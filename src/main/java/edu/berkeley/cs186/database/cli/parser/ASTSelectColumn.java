/* Generated By:JJTree: Do not edit this line. ASTSelectColumn.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.berkeley.cs186.database.cli.parser;

public
class ASTSelectColumn extends SimpleNode {
    public ASTSelectColumn(int id) {
        super(id);
    }

    public ASTSelectColumn(RookieParser p, int id) {
        super(p, id);
    }

    /**
     * Accept the visitor.
     **/
    public void jjtAccept(RookieParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=a12e1fc7235b25a70b3742f2199cad3b (do not edit this line) */
