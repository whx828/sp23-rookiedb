/* Generated By:JJTree: Do not edit this line. ASTCommonTableExpression.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.berkeley.cs186.database.cli.parser;

public
class ASTCommonTableExpression extends SimpleNode {
    public ASTCommonTableExpression(int id) {
        super(id);
    }

    public ASTCommonTableExpression(RookieParser p, int id) {
        super(p, id);
    }

    /**
     * Accept the visitor.
     **/
    public void jjtAccept(RookieParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=554990525865f39ee3a97ccd6dd4f3ed (do not edit this line) */
