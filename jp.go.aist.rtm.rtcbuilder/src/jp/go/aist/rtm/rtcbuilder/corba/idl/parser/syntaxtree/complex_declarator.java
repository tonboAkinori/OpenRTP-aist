//
// Generated by JTB 1.3.2
//

package jp.go.aist.rtm.rtcbuilder.corba.idl.parser.syntaxtree;

/**
 * Grammar production:
 * array_declarator -> array_declarator()
 */
public class complex_declarator implements Node {
   private Node parent;
   public array_declarator array_declarator;

   public complex_declarator(array_declarator n0) {
      array_declarator = n0;
      if ( array_declarator != null ) array_declarator.setParent(this);
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

