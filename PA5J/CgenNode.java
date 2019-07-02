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

/**
 * an address of a variable
 * only two type: attr and stack
 * offset is of fp or self
 */
class Addr {
    static int TypeAttr = 0;
    static int TypeStack = 1;

    int type;
    int offset; // word

    public Addr(int t, int o) {
        this.type = t;
        this.offset = o;
    }
}

class CgenNode extends class_ {
    /** The parent of this node in the inheritance tree */
    private CgenNode parent;

    /** The children of this node in the inheritance tree */
    private Vector children;

    /** Indicates a basic class */
    final static int Basic = 0;

    /** Indicates a class that came from a Cool program */
    final static int NotBasic = 1;
    
    /** Does this node correspond to a basic class? */
    private int basic_status;

    // classtag
    private int classtag;

    // classtable
    private CgenClassTable table;

    /** Constructs a new CgenNode to represent class "c".
     * @param c the class
     * @param basic_status is this class basic or not
     * @param table the class table
     * */
    CgenNode(Class_ c, int basic_status, CgenClassTable table) {
	super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
	this.parent = null;
	this.children = new Vector();
	this.basic_status = basic_status;
	this.table = table;
	AbstractTable.stringtable.addString(name.getString());
    }

    void setClassTag(int tag) {
	this.classtag = tag;
    }

    void addChild(CgenNode child) {
	children.addElement(child);
    }

    /** Gets the children of this class
     * @return the children
     * */
    Enumeration getChildren() {
	return children.elements(); 
    }

    /** Sets the parent of this class.
     * @param parent the parent
     * */
    void setParentNd(CgenNode parent) {
	if (this.parent != null) {
	    Utilities.fatalError("parent already set in CgenNode.setParent()");
	}
	if (parent == null) {
	    Utilities.fatalError("null parent in CgenNode.setParent()");
	}
	this.parent = parent;
    }    
	

    /** Gets the parent of this class
     * @return the parent
     * */
    CgenNode getParentNd() {
	return parent; 
    }

    /** Returns true is this is a basic class.
     * @return true or false
     * */
    boolean basic() { 
	return basic_status == Basic; 
    }
    /** Emits code definitions for the class. */
    public void codeDef(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable) {
    	// for basic class(IO, String)
        if (this.name == TreeConstants.Int) {
	        return;
        } else if (this.name == TreeConstants.Str) {
            table.codeString(s, "", methodTable);
            printDispTab(s, methodTable);
            return;
        } else if (this.name == TreeConstants.Bool) {
            table.codeInt(s, 0);
            return;
        }
        // Add -1 eye catcher
        s.println(CgenSupport.WORD + "-1");
        // label
        s.print(this.name + "_protObj" + CgenSupport.LABEL);
        // tag
        s.println(CgenSupport.WORD + this.classtag);
        // size
        s.println(CgenSupport.WORD + (CgenSupport.DEFAULT_OBJFIELDS +
            getSize())); // the number of attr
        // dispatch table
        s.print(CgenSupport.WORD);
        CgenSupport.emitDispTableRef(this.name, s);
        s.println("");
        // attributs
        printAttr(s, methodTable);
        // print dispatch table
        printDispTab(s, methodTable);
    }

    // print attributes
    public void printAttr(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable) {
        boolean shouldAddMethod = true;
        if (methodTable.get(this.name) != null) {
            shouldAddMethod = false;
        } else {
            methodTable.put(this.name, new Vector<AbstractSymbol>());
        }
        // parent
        if (this.name != TreeConstants.Object_) {
            this.parent.printAttr(s, methodTable);
            // add all parent method to self
            if (shouldAddMethod) {
                Vector<AbstractSymbol> p = methodTable.get(this.parent.name);
                Vector<AbstractSymbol> v = methodTable.get(this.name);
                v.addAll(p);
            }
        }
        // current
        Enumeration fs = this.features.getElements();
        while (fs.hasMoreElements()) {
            Object e = fs.nextElement();
            if (e instanceof attr) {
                attr p = (attr)e;
                s.print(CgenSupport.WORD);
                if (p.type_decl == TreeConstants.Int) {
                    ((IntSymbol)AbstractTable.inttable.addInt(0)).codeRef(s);
                } else if (p.type_decl == TreeConstants.Str) {
                    ((StringSymbol)AbstractTable.stringtable.addString("")).codeRef(s);
                } else if (p.type_decl == TreeConstants.Bool) {
                    BoolConst.falsebool.codeRef(s);
                } else {
                    s.print("0");
                }
		        s.println("");
            } else {
                if (shouldAddMethod) {
                    // methodTable
                    method p = (method)e;
                    Vector<AbstractSymbol> v = methodTable.get(this.name);
                    v.add(AbstractTable.stringtable.addString(this.name.toString() + "." + p.name.toString()));
                }
            }
        }
    }

    // print dispatch table
    public void printDispTab(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable) {
        CgenSupport.emitDispTableRef(this.name, s); s.print(CgenSupport.LABEL);
        Vector<AbstractSymbol> v = methodTable.get(this.name);
        for (int i=0;i<v.size();++i) {
            s.print(CgenSupport.WORD);
            s.println(v.get(i));
        }
    }

    // only attributes
    public int getSize() {
        int cnt = 0;
        // object
        if (this.name == TreeConstants.Object_) {
            return 0;
        }
        // parent
        if (this.parent != null) {
            cnt += this.parent.getSize();
        }
        // current
        Enumeration fs = this.features.getElements();
        while (fs.hasMoreElements()) {
            Object e = fs.nextElement();
            if (e instanceof attr) {
                cnt++; 
            }
        }
        return cnt;
    }

    // object init
    public void printInit(PrintStream s) {
        s.print(this.name + "_init" + CgenSupport.LABEL); // label
        s.println("\taddiu	$sp $sp -12");
        s.println("\tsw	$fp 12($sp)");
        s.println("\tsw	$s0 8($sp)");
        s.println("\tsw	$ra 4($sp)");
        s.println("\taddiu	$fp $sp 4");
        s.println("\tmove	$s0 $a0");
        if (this.name != TreeConstants.Object_) {
            s.println("\tjal	" + this.parent.name + "_init");
        }
        s.println("\tmove	$a0 $s0");
        s.println("\tlw	$fp 12($sp)");
        s.println("\tlw	$s0 8($sp)");
	s.println("\tlw	$ra 4($sp)");
        s.println("\taddiu	$sp $sp 12");
        s.println("\tjr	$ra	");
    }

    // **********************************print method*************************

    // codeMethod
    public void codeMethod(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable) {
        SymbolTable varTab = new SymbolTable(); // Addr
        varTab.enterScope();
        // add all attr
        // two type of environment E: attr and stack variable
        Enumeration fs = this.features.getElements();
        int cnt = 0;
        while (fs.hasMoreElements()) {
            Object e = fs.nextElement();
            if (e instanceof attr) {
                attr p = (attr)e;
                varTab.addId(p.name, new Addr(Addr.TypeAttr, cnt+CgenSupport.DEFAULT_OBJFIELDS));
                cnt++;
            }
        }
        // method
        fs = this.features.getElements();
        while (fs.hasMoreElements()) {
            Object e = fs.nextElement();
            if (e instanceof method) {
                varTab.enterScope();
                // methodTable
                method p = (method)e;
                Vector<AbstractSymbol> v = methodTable.get(this.name);
                codeSingleMethod(s, methodTable, varTab, p);
                varTab.exitScope();
            }
        }
    }

    // codeSingleMethod
    public void codeSingleMethod(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable, SymbolTable varTab, method m) {
        // label
        CgenSupport.emitMethodRef(this.name, m.name, s);
        s.print(CgenSupport.LABEL);

        // enter
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, -12, s);
        CgenSupport.emitStore(CgenSupport.FP, 3, CgenSupport.SP, s);
        CgenSupport.emitStore(CgenSupport.SELF, 2, CgenSupport.SP, s);
        CgenSupport.emitStore(CgenSupport.RA, 1, CgenSupport.SP, s);
        CgenSupport.emitAddiu(CgenSupport.FP, CgenSupport.SP, 4, s);
        CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, s);

        // add formals to environment
        int cnt = 0;
        Enumeration es = m.formals.getElements();
        while (es.hasMoreElements()) {
            formal sub = (formal)es.nextElement();
            varTab.addId(sub.name, new Addr(Addr.TypeStack, cnt+3));
            cnt++;
        }

        // method implementation
        codeExpression(s, methodTable, varTab, m.expr);

        // return
        CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
        CgenSupport.emitLoad(CgenSupport.FP, 3, CgenSupport.SP, s);
        CgenSupport.emitLoad(CgenSupport.SELF, 2, CgenSupport.SP, s);
        CgenSupport.emitLoad(CgenSupport.RA, 1, CgenSupport.SP, s);
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 12, s);
        CgenSupport.emitReturn(s);
    }

    // codeExpression
    public void codeExpression(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable, SymbolTable varTab, Expression e) {
        // The implementation of environment:E just a offset of a0?
        // two type of environment E: attr and stack variable

        // store result in $a0
        // don't change stack

        if (e instanceof block) {
            // block
            block p = (block)e;
            Enumeration es = p.body.getElements();
            while (es.hasMoreElements()) {
                Expression sub = (Expression)es.nextElement();
                codeExpression(s, methodTable, varTab, sub);
                // return value in $a0
            }
        } else if (e instanceof dispatch) {
            // dispatch
            dispatch p = (dispatch)e;
            // 1. resolve actual args
            Enumeration es = p.actual.getElements();
            while (es.hasMoreElements()) {
                Expression sub = (Expression)es.nextElement();
                codeExpression(s, methodTable, varTab, sub);
                // save args
                CgenSupport.emitPush(CgenSupport.ACC, s);
            }
            // 2. evolve e0
            codeExpression(s, methodTable, varTab, p.expr);
            // get classtag -> classname, classtag -> dispatchtable
            // use p.expr'type, it's static type, but index is the same
	        AbstractSymbol className = p.expr.get_type();
            if (className == TreeConstants.SELF_TYPE) {
                className = this.name;
            }
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(className);
            CgenNode tmp = this;
            while (true) {
                //System.out.println(className);
                //System.out.println("==========" +  (className + "." + p.name) + ", offset = " + v.indexOf(className + "." + p.name));
                    //offset = v.indexOf(AbstractTable.stringtable.addString(className + "." + p.name));
                for (int i=0;i<v.size();++i){
                    //		System.out.println("expected: " + AbstractTable.stringtable.addString(className + "." + p.name) + ", actual: " + v.get(i));
                    if(v.get(i) == AbstractTable.stringtable.addString(className + "." + p.name)) {
                        // System.out.println("get it");
                        offset = i;
                        break;
                    } 
                }
                if (offset != -1) break;
                if (className == TreeConstants.Object_) break;
                tmp = tmp.parent;
                className = tmp.name;
                // v don't change
            }
            // load dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s);
            // method addr
	        CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s);
            // jump to method
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // return value in $a0
        } else if (e instanceof object) {
            object p = (object)e;
            Addr add = (Addr)varTab.lookup(p.name);
            if (p.name == TreeConstants.self) {
                CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
                return;
            }
            if (add == null ) {
                System.out.println("should be never occur! p.name: " + p.name);
            }
            if (add.type == Addr.TypeAttr) {
                CgenSupport.emitLoad(CgenSupport.ACC, add.offset, CgenSupport.SELF, s);
            } else {
                CgenSupport.emitLoad(CgenSupport.ACC, add.offset, CgenSupport.FP, s);
            }
            // return value in $a0
        } else if (e instanceof string_const) {
            string_const p = (string_const)e;
            StringSymbol token = (StringSymbol)AbstractTable.stringtable.addString(p.token);
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX + p.index, s);
        }
    }
}
    

    
