/* Generated By:JJTree: Do not edit this line. ASTLiteral.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package edu.berkeley.cs186.database.cli.parser;

public
class ASTLiteral extends SimpleNode {
    public ASTLiteral(int id) {
        super(id);
    }

    public ASTLiteral(RookieParser p, int id) {
        super(p, id);
    }

    /**
     * Accept the visitor.
     **/
    public void jjtAccept(RookieParserVisitor visitor, Object data) {
        visitor.visit(this, data);
    }
}
/* JavaCC - OriginalChecksum=af4959770415eae2d999d949f583934e (do not edit this line) */
