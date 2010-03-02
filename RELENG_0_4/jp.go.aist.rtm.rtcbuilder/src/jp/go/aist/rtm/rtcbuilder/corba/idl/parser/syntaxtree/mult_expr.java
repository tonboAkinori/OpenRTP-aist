//
// Generated by JTB 1.3.2
//

package jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree;

/**
 * Grammar production:
 * <PRE>
 * unary_expr -> unary_expr()
 * nodeListOptional -> ( ( "*" | "/" | "%" ) unary_expr() )*
 * </PRE>
 */
public class mult_expr implements Node {
   private Node parent;
   public unary_expr unary_expr;
   public NodeListOptional nodeListOptional;

   public mult_expr(unary_expr n0, NodeListOptional n1) {
      unary_expr = n0;
      if ( unary_expr != null ) unary_expr.setParent(this);
      nodeListOptional = n1;
      if ( nodeListOptional != null ) nodeListOptional.setParent(this);
   }

   public void accept(jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(jp.go.aist.rtm.rtcbuilder.corba.idl.parser.visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
   public void setParent(Node n) { parent = n; }
   public Node getParent()       { return parent; }
}
