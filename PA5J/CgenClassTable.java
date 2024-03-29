/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/** This class is used for representing the inheritance tree during code
    generation. You will need to fill in some of its methods and
    potentially extend it in other useful ways. */
class CgenClassTable extends SymbolTable {

    /** All classes in the program, represented as CgenNode */
    private Vector nds;

    /** This is the stream to which assembly instructions are output */
    private PrintStream str;

    private int stringclasstag = 0;
    private int intclasstag = 1;
	private int boolclasstag = 2;
	
	// class -> attr
	public HashMap<AbstractSymbol, SymbolTable> classAttr = new HashMap<AbstractSymbol, SymbolTable>();


    // The following methods emit code for constants and global
    // declarations.

    /** Emits code to start the .data segment and to
     * declare the global names.
     * */
    private void codeGlobalData() {
	// The following global names must be defined first.

	str.print("\t.data\n" + CgenSupport.ALIGN);
	str.println(CgenSupport.GLOBAL + CgenSupport.CLASSNAMETAB);
	str.print(CgenSupport.GLOBAL); 
	CgenSupport.emitProtObjRef(TreeConstants.Main, str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	CgenSupport.emitProtObjRef(TreeConstants.Int, str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	CgenSupport.emitProtObjRef(TreeConstants.Str, str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	BoolConst.falsebool.codeRef(str);
	str.println("");
	str.print(CgenSupport.GLOBAL); 
	BoolConst.truebool.codeRef(str);
	str.println("");
	str.println(CgenSupport.GLOBAL + CgenSupport.INTTAG);
	str.println(CgenSupport.GLOBAL + CgenSupport.BOOLTAG);
	str.println(CgenSupport.GLOBAL + CgenSupport.STRINGTAG);

	// We also need to know the tag of the Int, String, and Bool classes
	// during code generation.

	str.println(CgenSupport.INTTAG + CgenSupport.LABEL 
		    + CgenSupport.WORD + intclasstag);
	str.println(CgenSupport.BOOLTAG + CgenSupport.LABEL 
		    + CgenSupport.WORD + boolclasstag);
	str.println(CgenSupport.STRINGTAG + CgenSupport.LABEL 
		    + CgenSupport.WORD + stringclasstag);

    }

    /** Emits code to start the .text segment and to
     * declare the global names.
     * */
    private void codeGlobalText() {
	str.println(CgenSupport.GLOBAL + CgenSupport.HEAP_START);
	str.print(CgenSupport.HEAP_START + CgenSupport.LABEL);
	str.println(CgenSupport.WORD + 0);
	str.println("\t.text");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Main, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Int, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Str, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitInitRef(TreeConstants.Bool, str);
	str.println("");
	str.print(CgenSupport.GLOBAL);
	CgenSupport.emitMethodRef(TreeConstants.Main, TreeConstants.main_meth, str);
	str.println("");
    }

    /** Emits code definitions for boolean constants. */
    private void codeBools(int classtag) {
		BoolConst.falsebool.codeDef(classtag, str);
		BoolConst.truebool.codeDef(classtag, str);
    }

    /** Emits code definitions for Int class. */
    public void codeInt(PrintStream s, int value, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable) {
        // Add -1 eye catcher
        s.println(CgenSupport.WORD + "-1");
        // label
        s.print("Int_protObj" + CgenSupport.LABEL);
        // tag
        s.println(CgenSupport.WORD + intclasstag);
		// size
		s.println(CgenSupport.WORD + (CgenSupport.DEFAULT_OBJFIELDS +
						CgenSupport.INT_SLOTS));
		// dispatch table
		s.print(CgenSupport.WORD);
        CgenSupport.emitDispTableRef(TreeConstants.Int, s);
		s.println("");
		// value
		s.println(CgenSupport.WORD + value);
		// add method
		methodTable.put(TreeConstants.Int, new Vector<AbstractSymbol>());
		Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Int);
		v.add(AbstractTable.stringtable.addString("Object.abort"));
		v.add(AbstractTable.stringtable.addString("Object.type_name"));
		v.add(AbstractTable.stringtable.addString("Object.copy"));
    }

    /** Emits code definitions for String class. */
    public void codeString(PrintStream s, String value, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable) {
		IntSymbol lensym = (IntSymbol)AbstractTable.inttable.addInt(value.length());
		// Add -1 eye catcher
		s.println(CgenSupport.WORD + "-1");
		s.print("String_protObj" + CgenSupport.LABEL);
		s.println(CgenSupport.WORD + stringclasstag); // tag
		s.println(CgenSupport.WORD + (CgenSupport.DEFAULT_OBJFIELDS +
						CgenSupport.STRING_SLOTS +
						(value.length() + 4) / 4)); // object size
		// dispatch table
		s.print(CgenSupport.WORD);
        CgenSupport.emitDispTableRef(TreeConstants.Str, s);
		s.println("");
		
		s.print(CgenSupport.WORD); lensym.codeRef(s); s.println(""); // length
		CgenSupport.emitStringConstant(value, s); // ascii string
		s.print(CgenSupport.ALIGN); // align to word
		// add method
		methodTable.put(TreeConstants.Str, new Vector<AbstractSymbol>());
		Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Str);
		v.add(AbstractTable.stringtable.addString("Object.abort"));
		v.add(AbstractTable.stringtable.addString("Object.type_name"));
		v.add(AbstractTable.stringtable.addString("Object.copy"));
		v.add(AbstractTable.stringtable.addString("String.length"));
		v.add(AbstractTable.stringtable.addString("String.concat"));
		v.add(AbstractTable.stringtable.addString("String.substr"));
	}
	

    // print class_nameTab
    public void codeClassNameTab(PrintStream s, Vector<AbstractSymbol> cls) {
		s.print("class_nameTab" + CgenSupport.LABEL);
		for (int i=0;i<cls.size();i++) {
			s.print(CgenSupport.WORD);
			s.println(CgenSupport.STRCONST_PREFIX + 
				AbstractTable.stringtable.lookup(cls.get(i).toString()).index);
		}
    }

    /** Generates GC choice constants (pointers to GC functions) */
    private void codeSelectGc() {
		str.println(CgenSupport.GLOBAL + "_MemMgr_INITIALIZER");
		str.println("_MemMgr_INITIALIZER:");
		str.println(CgenSupport.WORD 
				+ CgenSupport.gcInitNames[Flags.cgen_Memmgr]);

		str.println(CgenSupport.GLOBAL + "_MemMgr_COLLECTOR");
		str.println("_MemMgr_COLLECTOR:");
		str.println(CgenSupport.WORD 
				+ CgenSupport.gcCollectNames[Flags.cgen_Memmgr]);

		str.println(CgenSupport.GLOBAL + "_MemMgr_TEST");
		str.println("_MemMgr_TEST:");
		str.println(CgenSupport.WORD 
				+ ((Flags.cgen_Memmgr_Test == Flags.GC_TEST) ? "1" : "0"));
    }

    /** Emits code to reserve space for and initialize all of the
     * constants.  Class names should have been added to the string
     * table (in the supplied code, is is done during the construction
     * of the inheritance graph), and code for emitting string constants
     * as a side effect adds the string's length to the integer table.
     * The constants are emmitted by running through the stringtable and
     * inttable and producing code for each entry. */
    private void codeConstants() {
		// Add constants that are required by the code generator.
		AbstractTable.stringtable.addString("");
		AbstractTable.inttable.addString("0");

		AbstractTable.stringtable.codeStringTable(stringclasstag, str);
		AbstractTable.inttable.codeStringTable(intclasstag, str);
		codeBools(boolclasstag);
    }


    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// A few special class names are installed in the lookup table
	// but not the class list.  Thus, these classes exist, but are
	// not part of the inheritance hierarchy.  No_class serves as
	// the parent of Object and the other special classes.
	// SELF_TYPE is the self class; it cannot be redefined or
	// inherited.  prim_slot is a class known to the code generator.

	addId(TreeConstants.No_class,
	      new CgenNode(new class_(0,
				      TreeConstants.No_class,
				      TreeConstants.No_class,
				      new Features(0),
				      filename),
			   CgenNode.Basic, this));

	addId(TreeConstants.SELF_TYPE,
	      new CgenNode(new class_(0,
				      TreeConstants.SELF_TYPE,
				      TreeConstants.No_class,
				      new Features(0),
				      filename),
			   CgenNode.Basic, this));
	
	addId(TreeConstants.prim_slot,
	      new CgenNode(new class_(0,
				      TreeConstants.prim_slot,
				      TreeConstants.No_class,
				      new Features(0),
				      filename),
			   CgenNode.Basic, this));

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_ Object_class = 
	    new class_(0, 
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

	installClass(new CgenNode(Object_class, CgenNode.Basic, this));
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_ IO_class = 
	    new class_(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formal(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formal(0,
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

	installClass(new CgenNode(IO_class, CgenNode.Basic, this));

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_ Int_class = 
	    new class_(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	installClass(new CgenNode(Int_class, CgenNode.Basic, this));

	// Bool also has only the "val" slot.
	class_ Bool_class = 
	    new class_(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	installClass(new CgenNode(Bool_class, CgenNode.Basic, this));

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_ Str_class =
	    new class_(0,
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
						  .appendElement(new formal(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formal(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formal(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	installClass(new CgenNode(Str_class, CgenNode.Basic, this));
    }
	
    // The following creates an inheritance graph from
    // a list of classes.  The graph is implemented as
    // a tree of `CgenNode', and class names are placed
    // in the base class symbol table.
    
    private void installClass(CgenNode nd) {
	AbstractSymbol name = nd.getName();
	if (probe(name) != null) return;
	nds.addElement(nd);
	addId(name, nd);
	AbstractTable.stringtable.lookup(nd.getFilename().toString());
    }

    private void installClasses(Classes cs) {
        for (Enumeration e = cs.getElements(); e.hasMoreElements(); ) {
	    installClass(new CgenNode((Class_)e.nextElement(), 
				       CgenNode.NotBasic, this));
        }
    }

    private void buildInheritanceTree() {
	for (Enumeration e = nds.elements(); e.hasMoreElements(); ) {
	    setRelations((CgenNode)e.nextElement());
	}
    }

    private void setRelations(CgenNode nd) {
	CgenNode parent = (CgenNode)probe(nd.getParent());
	nd.setParentNd(parent);
	parent.addChild(nd);
    }

    /** Constructs a new class table and invokes the code generator */
    public CgenClassTable(Classes cls, PrintStream str) {
	nds = new Vector();

	this.str = str;

	stringclasstag = 0 /* Change to your String class tag here */;
	intclasstag =    1 /* Change to your Int class tag here */;
	boolclasstag =   2 /* Change to your Bool class tag here */;

	enterScope();
	if (Flags.cgen_debug) System.out.println("Building CgenClassTable");
	
	installBasicClasses();
	installClasses(cls);
	buildInheritanceTree();

	code();

	exitScope();
    }

    /** This method is the meat of the code generator.  It is to be
        filled in programming assignment 5 */
    public void code() {
	if (Flags.cgen_debug) System.out.println("coding global data");
	codeGlobalData();

	if (Flags.cgen_debug) System.out.println("choosing gc");
	codeSelectGc();

	if (Flags.cgen_debug) System.out.println("coding constants");
	codeConstants();

	//                 Add your code to emit
	//                   - prototype objects
	//                   - class_nameTab
	//                   - dispatch tables
	// 					 - object initializer
	if (Flags.cgen_debug) System.out.println("coding prototype objects");
	// pass inherits graph to print all prototype object
	Vector<AbstractSymbol> classNameTable = new Vector<AbstractSymbol>();
	classNameTable.add(TreeConstants.Str);
	classNameTable.add(TreeConstants.Int);
	classNameTable.add(TreeConstants.Bool);

	// method lookup map. className -> []methodName
	HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable = new HashMap<AbstractSymbol, Vector<AbstractSymbol>>();
	// add bool
	// add method
	methodTable.put(TreeConstants.Bool, new Vector<AbstractSymbol>());
	Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Bool);
	v.add(AbstractTable.stringtable.addString("Object.abort"));
	v.add(AbstractTable.stringtable.addString("Object.type_name"));
	v.add(AbstractTable.stringtable.addString("Object.copy"));

	CgenNode node = root();
	Queue<CgenNode> q = new LinkedList<CgenNode>();
	Queue<CgenNode> p = new LinkedList<CgenNode>();
	q.offer(node);
	while (!q.isEmpty()) {
		node = q.poll();
		// System.out.print(node.name + " ");
		if (node.name != TreeConstants.Str && node.name != TreeConstants.Int && node.name != TreeConstants.Bool) {
			node.setClassTag(classNameTable.size());
			classNameTable.add(node.name);
		}
		node.codeDef(str, methodTable);
		Enumeration e = node.getChildren();
		while (e.hasMoreElements()) {
			CgenNode ch = (CgenNode)e.nextElement();
			p.offer(ch);
		}
		if (q.isEmpty()) {
			// System.out.println();
			while (!p.isEmpty()) {
				q.offer(p.poll());
			}
		}
	}
	// print classNameTable
	codeClassNameTab(str, classNameTable);

	if (Flags.cgen_debug) System.out.println("coding global text");
	codeGlobalText();

		//                 Add your code to emit
		//                   - the class methods
		//                   - etc...
		node = root();
		q = new LinkedList<CgenNode>();
		p = new LinkedList<CgenNode>();
		q.offer(node);
		while (!q.isEmpty()) {
			node = q.poll();
			// print method
			if (!node.basic()) {
				node.codeMethod(str, methodTable, classNameTable);
			} else {
				node.printInit(str); // basic class init by hand
			}
			Enumeration e = node.getChildren();
			while (e.hasMoreElements()) {
				CgenNode ch = (CgenNode)e.nextElement();
				p.offer(ch);
			}
			if (q.isEmpty()) {
				while (!p.isEmpty()) {
					q.offer(p.poll());
				}
			}
		}
    }

    /** Gets the root of the inheritance tree */
    public CgenNode root() {
	return (CgenNode)probe(TreeConstants.Object_);
    }
}
			  
    
