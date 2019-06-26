import java.io.PrintStream;
import java.util.*;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;

    public class_c Object_class, Int_class, IO_class, Str_class, Bool_class;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
		       TreeConstants.Object_, 
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0, 
					      TreeConstants.cool_abort, 
					      new Formals(0), 
					      TreeConstants.Object_, 
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */
	// what I should do is store these basic class information in type environment.
	this.Object_class = Object_class;
	this.IO_class = IO_class;
	this.Int_class = Int_class;
	this.Str_class = Str_class;
	this.Bool_class = Bool_class;

    }
	


    public ClassTable(Classes cls) {
	semantErrors = 0;
	errorStream = System.err;
	
	/* fill this in */
	this.installBasicClasses();
    }

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
	errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }
}

class Node {
    class_c cls = null; // if be defined, cls is not null
    AbstractSymbol className = null;
    Vector<Node> clds = null;
    Node parent = null;
    boolean isBasicClass = false;

    public Node(AbstractSymbol className, Vector<Node> clds, Node parent) {
        this.className = className;
        this.clds = new Vector<Node>();
        for(Node n : clds) {
            this.clds.add(n);
        }
        this.parent = parent;
    }
    public Node(AbstractSymbol className, Vector<Node> clds) {
        this(className, clds, null);
    }
    public Node(AbstractSymbol className) {
        this(className, new Vector<Node>(), null);
    }

    /* must be set when class be defined */
    public void setCls(class_c cls) {
	this.cls = cls;
    }

    public boolean addChild(Node node) {
        return this.clds.add(node);
    }
    public void setParent(Node node) {
        this.parent = node;
    }
    public Node getParent() {
	return this.parent;
    }

    public boolean canInherits() {
	if (className == TreeConstants.Int || className == TreeConstants.Str || className == TreeConstants.Bool) {
	    return false;
	}
	return true;
    }

    /**
     * output tree
     */
    public void out() {
        Queue<Node> q = new LinkedList<Node>();
        Queue<Node> p = new LinkedList<Node>();
        q.offer(this);
        while (!q.isEmpty()) {
            Node node = q.poll();
            System.out.print(node.className.getString() + " ");
            for (Node n : node.clds) {
                p.offer(n);
            }
            if (q.isEmpty()) {
                System.out.println();
                while (!p.isEmpty()) {
                    q.offer(p.poll());
                }
            }
        }
    }
}

class TreeBuilder {
    public Node root = null;
    HashMap<AbstractSymbol, Node> hm; // AbstractSymbol to Node
    ClassTable ct = null;
    boolean hasMain = false;

    public TreeBuilder(ClassTable ct) {
	this.ct = ct;
    }

    public void beginBuild() {
        hm = new HashMap<AbstractSymbol, Node>();
	// init basic class
	this.addBasicEdge(this.ct.Object_class);
	this.addBasicEdge(this.ct.Int_class);
	this.addBasicEdge(this.ct.Str_class);
	this.addBasicEdge(this.ct.Bool_class);
	this.addBasicEdge(this.ct.IO_class);
    }

    public boolean addEdge(AbstractSymbol parent, AbstractSymbol child, class_c childCls) {
	if (parent == TreeConstants.SELF_TYPE || child == TreeConstants.SELF_TYPE) {
	    ct.semantError(childCls).println("SELF_TYPE can't use as a class name.");
	    return false;
	}
	// System.out.println("[addEdge] parent: " + parent + " child: " + child);
        if (hm.get(parent) == null) {
            hm.put(parent, new Node(parent));
        }
	// class redefined
        if (hm.get(child) != null && hm.get(child).cls != null) {
	    this.ct.semantError(childCls).println("class: " + child + " redefined!");
	    return false;
	}
	// basic class restriction
	if (!hm.get(parent).canInherits()) {
	    this.ct.semantError(childCls).println("class: " + parent + " can't be inherits by " + child + "!");
	    return false;
	}
	if (hm.get(child) == null) {
	    hm.put(child, new Node(child));
	}
        hm.get(parent).addChild(hm.get(child));
        hm.get(child).setParent(hm.get(parent));
	hm.get(child).setCls(childCls);

	root = hm.get(child);
	if (child == TreeConstants.Main) {
	    hasMain = true;
	}
	return true;
    }

    public void addEdge(class_c c) {
	this.addEdge(c.getParent(), c.getName(), c);
    }

    public void addBasicEdge(class_c c) {
	this.addEdge(c.getParent(), c.getName(), c);
	hm.get(c.getName()).isBasicClass = true;
    }  

    public boolean endBuild() {
	if (!hasMain) {
	    ct.semantError().println("class Main is not defined.");
	    return false;
	}
	return true;
    }

    /* return true if not error, else return false */
    public boolean validate() {
	// get root and simple validate
	Set<AbstractSymbol> visited = new HashSet<AbstractSymbol>();
	while (root.className != TreeConstants.Object_) {
	    if (visited.contains(root.className)) {
		// has circle, a Node must be defined when it in a circle
	 	ct.semantError(root.cls).println("has circle(1)");
		return false;
	    }
	    visited.add(root.className);
	    if (root.getParent().cls == null) {
		// parent class not defined
	 	ct.semantError(root.cls).println("parent class not defined. parent: " + root.getParent().className);
		return false;
	    }
	    root = root.getParent();
	}
	
	// top-down validate
	Queue<Node> q = new LinkedList<Node>();
        visited = new HashSet<AbstractSymbol>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node node = q.poll();
	    if (node.cls == null) {
		// parent class not defined
	 	ct.semantError(node.cls).println("class as parent not defined. class: " + node.className);
		return false;
	    }
            if (visited.contains(node.className)) {
		// has circle
	 	ct.semantError(node.cls).println("has circle(2)");
                return false;
            }
            visited.add(node.className);
            for (Node n : node.clds) {
                q.offer(n);
            }
        }
	return true;
    }

    public Node getTreeRoot() {
        return root;
    }

    public AbstractSymbol inferSelfType(class_c c, AbstractSymbol name) {
	if (name == TreeConstants.SELF_TYPE) {
	    return c.name;
	}
	return name;
    }

    /* init class method name scope */
    public boolean initMethod(SymbolTable m, class_c c, HashMap<AbstractSymbol, SymbolTable> classMethod) {
        m.enterScope();
	if (c.parent != TreeConstants.No_class) {
	    m.appendMethodPeek(classMethod.get(c.parent), c.name); // for static_dispatch, just dig into stack...
	}
        Features fs = c.getFeatures();
        Enumeration<Feature> it = fs.getElements();
	while (it.hasMoreElements()) {
	    Feature f = it.nextElement();
	    if (f instanceof method) {
		method me = (method)f;
		AbstractSymbol name = AbstractTable.idtable.addString(c.name.toString() + "." + me.name.getString());
		Object info = m.lookup(name);
		if (info != null) {
		    // check: override must conform to parent method
		    method pm = (method)info;
		    if (inferSelfType(c, pm.return_type) != inferSelfType(c, me.return_type)) {
			ct.semantError(c).println("override must has same return type.");
			return false;
		    }
		    Enumeration it1 = pm.formals.getElements();
		    Enumeration it2 = me.formals.getElements();
		    while (it1.hasMoreElements() && it2.hasMoreElements()) {
			formalc fp = (formalc)it1.nextElement();
			formalc fc = (formalc)it2.nextElement();
			if (fp.type_decl != fc.type_decl) {
			    ct.semantError(c).println("override must has same args type.");
			    return false;
			}
		    }
		    if (it1.hasMoreElements() || it2.hasMoreElements()) {
			ct.semantError(c).println("override must has same args num.");
			return false;
		    }
		}
                m.addId(name, me);
	    }
	}
	classMethod.put(c.name, m.copyPeek());
	return true;
    }

    /**
     * 
     * @param o object symboltable
     * @param m method symboltable
     * @param c current class
     */
    public boolean annotateClass(SymbolTable o, SymbolTable m, class_c c, HashMap<AbstractSymbol, SymbolTable> classAttr) {
        o.enterScope();
        m.enterScope();
	
	// copy attr from parent.
	if (c.parent != TreeConstants.No_class) {
	    o.appendPeek(classAttr.get(c.parent));
	}
	
	// bound self
	o.addId(TreeConstants.self, TreeConstants.SELF_TYPE);

        Features fs = c.getFeatures();
        Enumeration<Feature> it = fs.getElements();
	// add attr and method to name scope
	while (it.hasMoreElements()) {
	    Feature f = it.nextElement();
            if (f instanceof attr) {
		attr at  = (attr)f;
		// check: self can't be the name of attribute
		if (at.name == TreeConstants.self) {
		    ct.semantError(c).println("self can't be the name of attribute.");
		    return false;
		}
		// check: attribute anme can't be duplicate
		Object info = o.lookup(at.name);
		if (info != null) {
		    ct.semantError(c).println("attribute name can't be duplicat. name: " + at.name + ".");
		    return false;	
		}
                o.addId(at.name, at.type_decl);
	    }
	}

	it = fs.getElements();
	while (it.hasMoreElements()) {
	    Feature f = it.nextElement();
            if (f instanceof attr) {
		attr at  = (attr)f;
                // attr
                // annotate init expression
                if (!annotateExpression(o, m, c, at.init)) {
		    return false;
		}
		// check: conform
		AbstractSymbol tp = at.type_decl, tc = at.init.get_type();
		if (tp == TreeConstants.SELF_TYPE) { tp = c.name; }
		if (tc == TreeConstants.SELF_TYPE) { tc = c.name; }
		if (tc != TreeConstants.No_type && !isChild(tp, tc)) {
		    ct.semantError(c).println(tc + " is not conform to " + tp);
		    return false;
		}
            } else {
                // method
                method me = (method)f;
                
                o.enterScope();
                // add formals to o
                Enumeration<formalc> fmls =  me.formals.getElements();
		// check: formal args can't be duplicate
		HashSet<AbstractSymbol> hs = new HashSet<AbstractSymbol>();
                while (fmls.hasMoreElements()) {
                    formalc fc = fmls.nextElement();
		    // check: self can't be use formals name
		    if (fc.name == TreeConstants.self) {
			ct.semantError(c).println("self can't use as formals name.");
			return false;
		    }
		    if (fc.type_decl == TreeConstants.SELF_TYPE) {
			ct.semantError(c).println("SELF_TYPE can't use as formals type.");
			return false;
		    }
		    if (hs.contains(fc.name)) {
			ct.semantError(c).println("formal args duplicate: " + fc.name);
			return false;
		    }
		    hs.add(fc.name);
                    o.addId(fc.name, fc.type_decl);
                }
		// check: return_type must be defined
		if (!hm.containsKey(inferSelfType(c, me.return_type))) {
		    ct.semantError(c).println("return type not defined: " + me.return_type);
		    return false;
		}
                // annotate init expression
                if (!annotateExpression(o, m, c, me.expr)) {
		    return false;
		}
		// check: me.expr.get_type() conform to me.return_type
		AbstractSymbol tp = me.return_type, tc = me.expr.get_type();
		if (tp == TreeConstants.SELF_TYPE && tc == TreeConstants.SELF_TYPE) { tp = c.name;}
		if (tc == TreeConstants.SELF_TYPE) { tc = c.name; }
		if (tc != TreeConstants.No_type && !isChild(tp, tc)) {
		    ct.semantError(c).println(tc + " is not conform to " + tp);
		    return false;
		}

                o.exitScope();
            }
        }
	
	// store class identifier and method
	classAttr.put(c.name, o.copyPeek());

        o.exitScope();
        // m.exitScope(); not exit, use TypeName.MethodName to distinguish
	return true;
    }

    public boolean annotateExpression(SymbolTable o, SymbolTable m, class_c c, Expression e) {
	// o.enterScope();

        if (e instanceof no_expr) {
            e.set_type(TreeConstants.No_type);
	} else if (e instanceof int_const) {
            e.set_type(TreeConstants.Int);
        } else if (e instanceof string_const) {
            e.set_type(TreeConstants.Str);
        } else if (e instanceof bool_const) {
            e.set_type(TreeConstants.Bool);
        } else if (e instanceof neg) {
	    neg ne = (neg)e;
            annotateExpression(o, m, c, ne.e1);
            e.set_type(ne.e1.get_type());
        } else if (e instanceof comp) {
            comp cm = (comp)e;
            annotateExpression(o, m, c, cm.e1);
	    if (cm.e1.get_type() != TreeConstants.Bool) {
		ct.semantError(c).println("comp e1 must be bool.");
		return false;
	    }
            e.set_type(cm.e1.get_type());
        } else if (e instanceof plus) {
            plus p = (plus)e;
            annotateExpression(o, m, c, p.e1);
            annotateExpression(o, m, c, p.e2);
	    if (p.e1.get_type() != TreeConstants.Int || p.e2.get_type() != TreeConstants.Int) {	ct.semantError(c).println("+ operand must be all int."); return false; }
            e.set_type(p.e1.get_type());
        } else if (e instanceof sub) {
            sub p = (sub)e;
            annotateExpression(o, m, c, p.e1);
            annotateExpression(o, m, c, p.e2);
	    if (p.e1.get_type() != TreeConstants.Int || p.e2.get_type() != TreeConstants.Int) {	ct.semantError(c).println("- operand must be all int."); return false; }
            e.set_type(p.e1.get_type());
        } else if (e instanceof mul) {
            mul p = (mul)e;
            annotateExpression(o, m, c, p.e1);
            annotateExpression(o, m, c, p.e2);
	    if (p.e1.get_type() != TreeConstants.Int || p.e2.get_type() != TreeConstants.Int) {	ct.semantError(c).println("* operand must be all int."); return false; }
            e.set_type(p.e1.get_type());
        } else if (e instanceof divide) {
            divide p = (divide)e;
            annotateExpression(o, m, c, p.e1);
            annotateExpression(o, m, c, p.e2);
	    if (p.e1.get_type() != TreeConstants.Int || p.e2.get_type() != TreeConstants.Int) {	ct.semantError(c).println("/ operand must be all int."); return false; }
            e.set_type(p.e1.get_type());
        } else if (e instanceof eq) {
            eq p = (eq)e;
            annotateExpression(o, m, c, p.e1);
            annotateExpression(o, m, c, p.e2);
	    if (p.e1.get_type() == TreeConstants.Int || p.e1.get_type() == TreeConstants.Bool || p.e1.get_type() == TreeConstants.Str) {
		if (p.e1.get_type() != p.e2.get_type()) {
		    ct.semantError(c).println("int, bool, string can only compare to same type.");
		    return false;
		}
	    }
	    if (p.e2.get_type() == TreeConstants.Int || p.e2.get_type() == TreeConstants.Bool || p.e2.get_type() == TreeConstants.Str) {
		if (p.e1.get_type() != p.e2.get_type()) {
		    ct.semantError(c).println("int, bool, string can only compare to same type.");
		    return false;
		}
	    }
            e.set_type(TreeConstants.Bool);
        } else if (e instanceof lt) {
            lt p = (lt)e;
            annotateExpression(o, m, c, p.e1);
            annotateExpression(o, m, c, p.e2);
	    if (p.e1.get_type() != TreeConstants.Int || p.e2.get_type() != TreeConstants.Int) {	ct.semantError(c).println("< operand must be all int."); return false; }
            e.set_type(TreeConstants.Bool);
        } else if (e instanceof leq) {
            leq p = (leq)e;
            annotateExpression(o, m, c, p.e1);
            annotateExpression(o, m, c, p.e2);
	    if (p.e1.get_type() != TreeConstants.Int || p.e2.get_type() != TreeConstants.Int) {
		ct.semantError(c).println("<= operand must be all int.");
		return false;
	    }
            e.set_type(TreeConstants.Bool);
        } else if (e instanceof isvoid) {
            isvoid p = (isvoid)e;
            annotateExpression(o, m, c, p.e1);
            e.set_type(TreeConstants.Bool);
        } else if (e instanceof new_) {
            new_ p = (new_)e;
	    // check: type_name must defined
	    if (!hm.containsKey(inferSelfType(c, p.type_name))) {
	    	ct.semantError(c).println("new oprand must be defined. " + p.type_name);
   	    	return false;
	    }
            e.set_type(p.type_name);
        } else if (e instanceof typcase) {
            typcase p = (typcase)e;
            annotateExpression(o, m, c, p.expr);
	    // check: each branch of case must have different type
	    HashSet<AbstractSymbol> visited = new HashSet<AbstractSymbol>();
	    // for branch
	    Enumeration<branch> it = p.cases.getElements();
	    Stack<AbstractSymbol> as = new Stack<AbstractSymbol>();
	    while (it.hasMoreElements()) {
		branch b = it.nextElement();
		if (visited.contains(b.type_decl)) {
		    ct.semantError(c).println("each branch of case must have distinct type. duplicate: " + b.type_decl);
		    return false;
		}
		visited.add(b.type_decl);
		o.enterScope();
		o.addId(b.name, b.type_decl);
            	annotateExpression(o, m, c, b.expr);
		o.exitScope();
		as.push(b.expr.get_type());
	    }
	    // get latestAncestor of as
	    while (as.size() > 1) {
		as.push(latestAncestor(inferSelfType(c, as.pop()), inferSelfType(c, as.pop())));
	    }
	    e.set_type(as.peek());	    
        }
	else if (e instanceof let) {
	    let p = (let)e;
            annotateExpression(o, m, c, p.init);
	    // check: var name can't be self
	    if (p.identifier == TreeConstants.self) {
		ct.semantError(c).println("let identifier name can't be self");
		return false;
	    }
	    // check: p.init must conform to p.type_decl
	    AbstractSymbol tp = p.type_decl, tc = p.init.get_type();
	    if (tp == TreeConstants.SELF_TYPE) {
		tp = c.name;
	    }
	    if (tc == TreeConstants.SELF_TYPE) {
		tc = c.name;
	    }
	    if (p.init.get_type() != TreeConstants.No_type && !isChild(tp, tc)) {
		ct.semantError(c).println(tc + " is not conform to " + tp);
		return false;
	    }
	    o.enterScope();
	    o.addId(p.identifier, p.type_decl);
            annotateExpression(o, m, c, p.body);
	    o.exitScope();
	    e.set_type(p.body.get_type());	
	}
        else if (e instanceof block) {
	    // not need o.EnterScope, because can't declare new variable
            // block
            block p = (block)e;
            Enumeration<Expression> es = p.body.getElements();
            Expression last = null;
            while (es.hasMoreElements()) {
                Expression e1 = es.nextElement();
                annotateExpression(o, m, c, e1);
                last = e1;
            }
            e.set_type(last.get_type());
        }
        // method call
        else if (e instanceof dispatch) {
	    dispatch p = (dispatch)e;
            annotateExpression(o, m, c, p.expr);
		
            Object info = m.lookup(AbstractTable.idtable.addString(p.expr.get_type().toString() + "." + p.name.toString()));
	    if (p.expr.get_type() == TreeConstants.SELF_TYPE) {
		info = m.lookup(AbstractTable.idtable.addString(c.name.toString() + "." + p.name.toString()));
	    }
            if (info == null) {
                // error, not defined
                ct.semantError(c).println("method " + p.name + " not defined");
		e.set_type(TreeConstants.Object_);
		return false;
            }
            if (!(info instanceof method)) {
                // error, not method
                ct.semantError(c).println(p.name + " not method");
		e.set_type(TreeConstants.Object_);
		return false;
            }
            method me = (method)info;  
	
	    
	    // set actual and check args match
	    Enumeration it = p.actual.getElements();
	    Enumeration it2 = me.formals.getElements();
	    int expected = 0, actual = 0;
	    while (it.hasMoreElements() && it2.hasMoreElements()) {
		expected++;
		actual++;
		Expression innerE = (Expression)it.nextElement();
		annotateExpression(o, m, c, innerE);
		// check: conform to formal
		formalc fl = (formalc)it2.nextElement();
		AbstractSymbol tp = fl.type_decl, tc = innerE.get_type();
		if (tp == TreeConstants.SELF_TYPE) { tp = c.name; }
		if (tc == TreeConstants.SELF_TYPE) { tc = c.name; }
	 	if (!isChild(tp, tc)) {
		    ct.semantError(c).println("method " + me.name + 
			" has unmatch args type. expected: " + tp + " actual: " + tc);
		    return false;
		}
	    }
	    // check: args and actual must have same num of args
	    while (it.hasMoreElements()) {
		actual++;
		it.nextElement();
	    }
	    while (it2.hasMoreElements()) {
		expected++;
		it2.nextElement();
	    }
	    if (expected != actual){
	        ct.semantError(c).println("method " + me.name + 
		    " has unmatch arg nums. expected: " + expected + " actual: " + actual);
	    	return false;
	    }
	    
	    if (me.return_type == TreeConstants.SELF_TYPE) {
		e.set_type(p.expr.get_type());
	    } else {
                e.set_type(me.return_type);
	    }
        }
        // static_dispatch method call
        else if (e instanceof static_dispatch) {
            static_dispatch p = (static_dispatch)e;
	    annotateExpression(o, m, c, p.expr);
	    // check: p.type_name can't be SELF_TYPE
            if (p.type_name == TreeConstants.SELF_TYPE) {
		ct.semantError(c).println("Static dispatch to SELF_TYPE.");
		return false;
	    }
	    // check: p.expr must be conform to p.type_name
	    AbstractSymbol tp1 = p.type_name, tc1 = p.expr.get_type();
	    if (tp1 == TreeConstants.SELF_TYPE) { tp1 = c.name; }
	    if (tc1 == TreeConstants.SELF_TYPE) { tc1 = c.name; }
	    if (!isChild(tp1, tc1)) {
		ct.semantError(c).println("static_distpatch name: " + tc1 + " is not conform to " + tp1);
		return false;
	    }
            Object info = m.lookup(AbstractTable.idtable.addString(p.type_name .toString() + "." + p.name.toString()));
            if (info == null) {
                // error, not defined
                ct.semantError(c).println("method " + p.name + " not defined");
                e.set_type(TreeConstants.Object_);
                return false;
            }
            if (!(info instanceof method)) {
                // error, not method
                ct.semantError(c).println(p.name + " not method");
                e.set_type(TreeConstants.Object_);
                return false;
            }

            method me = (method)info;
	    // set actual and check args match
	    Enumeration it = p.actual.getElements();
	    Enumeration it2 = me.formals.getElements();
	    int expected = 0, actual = 0;
	    while (it.hasMoreElements() && it2.hasMoreElements()) {
		expected++;
		actual++;
		Expression innerE = (Expression)it.nextElement();
		annotateExpression(o, m, c, innerE);
		// check: conform to formal
		formalc fl = (formalc)it2.nextElement();
		AbstractSymbol tp = fl.type_decl, tc = innerE.get_type();
		if (tp == TreeConstants.SELF_TYPE) { tp = c.name; }
		if (tc == TreeConstants.SELF_TYPE) { tc = c.name; }
		if (!isChild(fl.type_decl, innerE.get_type())) {
		    ct.semantError(c).println("method " + me.name +
			" has unmatch args type. expected: " + tp + " actual: " + tc);
		    return false;
		}
	    }
	    // check: args and actual must have same num of args
            while (it.hasMoreElements()) {
                actual++;
                it.nextElement();
            }
            while (it2.hasMoreElements()) {
                expected++;
                it2.nextElement();
            }
            if (expected != actual){
                ct.semantError(c).println("method " + me.name +
                    " has unmatch arg nums. expected: " + expected + " actual: " + actual);
                return false;
            }

            if (me.return_type == TreeConstants.SELF_TYPE) {
                e.set_type(p.type_name);
            } else {
                e.set_type(me.return_type);
            }
        }
	// single object id
	else if (e instanceof object) {
	    object p = (object)e;
	    // System.out.println("==============" + p.name);
	    
	    Object info = o.lookup(p.name);
	    if (info == null) {
		ct.semantError(c).println("never occur!! objectID not defined: " + p.name);	
	    }
	    // System.out.println("set " + p.name + " as " + ((AbstractSymbol)info).getString());
	    e.set_type((AbstractSymbol)info);
	} else if (e instanceof assign) {
	    assign p = (assign)e;
	    // check: self can't be assign
	    if (p.name == TreeConstants.self) {
		ct.semantError(c).println("self can't be assigned.");	
		return false;
	    }
            annotateExpression(o, m, c, p.expr);
	    Object info = o.lookup(p.name);
	    if (info == null) {
		ct.semantError(c).println("never occur!! objectID not defined: " + p.name);	
	    }
	    if (isChild((AbstractSymbol)info, p.expr.get_type())) {
	        e.set_type(p.expr.get_type());
	    } else {
		ct.semantError(c).println("[assign] expr type not child of name.");
		e.set_type(TreeConstants.Object_);
	    }
	} else if (e instanceof cond) {
	    cond p = (cond)e;
	    // check: pred must be bool
            annotateExpression(o, m, c, p.pred);
	    if (p.pred.get_type() != TreeConstants.Bool) {
		ct.semantError(c).println("if pred muse be bool");
		return false;
	    }
            annotateExpression(o, m, c, p.then_exp);
            annotateExpression(o, m, c, p.else_exp);
	    e.set_type(latestAncestor(inferSelfType(c, p.then_exp.get_type()), inferSelfType(c, p.else_exp.get_type())));
	} else if (e instanceof loop) {
	    loop p = (loop)e;
            annotateExpression(o, m, c, p.pred);
	    if (p.pred.get_type() != TreeConstants.Bool) {
		ct.semantError(c).println("loop pred muse be bool");
		return false;
	    }
            annotateExpression(o, m, c, p.body);
	    e.set_type(TreeConstants.Object_);
	}
	// o.exitScope();
	return true;
    }

    public AbstractSymbol latestAncestor(AbstractSymbol a, AbstractSymbol b) {
	Node na = hm.get(a);
	Node nb = hm.get(b);	
	Stack<Node> sa = new Stack<Node>();
	Stack<Node> sb = new Stack<Node>();
	// System.out.println("=====a: " + a);
	// System.out.println("=====b: " + b);
	this.pathToRoot(na, sa);
	this.pathToRoot(nb, sb);
	if (sb.size() < sa.size()) {
	    Stack<Node> tmp = sa;
	    sa = sb;
	    sb = tmp;
	}
	while (sa.size() < sb.size()) sb.pop();
	while (sa.peek().className != sb.peek().className) {
	    sa.pop();
	    sb.pop();
	}
	return sa.peek().className;
    }

    public void pathToRoot(Node a, Stack<Node> s) {
	if (a.className != TreeConstants.Object_) {
	    pathToRoot(a.parent, s);
	}
	s.push(a);
    }

    public boolean isChild(AbstractSymbol p, AbstractSymbol c) {
	// System.out.println(p + " " + c);
	if (p == c) return true;
	if (p == TreeConstants.SELF_TYPE || c == TreeConstants.SELF_TYPE) return false;
	return isChild(hm.get(p), hm.get(c));
    }

    public boolean isChild(Node p, Node c) {
	// System.out.println("========");
	if (p.className == TreeConstants.Object_ || p == c) return true;
	if (c.parent == p) return true;
	if (c.className == TreeConstants.Object_) return false;
	return isChild(p, c.parent);
    }

}
