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
import java.util.Stack;

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
            table.codeInt(s, 0, methodTable);
            printDispTab(s, methodTable);
	        return;
        } else if (this.name == TreeConstants.Str) {
            table.codeString(s, "", methodTable);
            printDispTab(s, methodTable);
            return;
        } else if (this.name == TreeConstants.Bool) {
            printDispTab(s, methodTable);
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
                    AbstractSymbol newName = AbstractTable.stringtable.addString(this.name.toString() + "." + p.name.toString());
                    int index = -1;
                    for (int i=0;i<v.size();i++) {
			// System.out.println("v " + (v.get(i).toString().split("\\."))[1]);
			// System.out.println("p " + p.name.toString());
                        if ((v.get(i).toString().split("\\."))[1].equals(p.name.toString())) {
                            index = i;
                            break;
                        }   
                    }
                    if (index == -1) {
                        v.add(newName);
                    } else {
                        v.set(index, newName);
                    }
                }
            }
        }
    }

    // print dispatch table
    public void printDispTab(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable) {
	// print _init method
        s.print(CgenSupport.WORD);
        s.print(this.name + "_init");
        s.println("");
        // at begin of methodTable, add parent protObj address
        s.print(CgenSupport.WORD);
        if (this.name != TreeConstants.Object_) {
            CgenSupport.emitProtObjRef(this.parent.name, s);
        } else {
            s.print("0");
        }
        s.println("");
        // print method
        Vector<AbstractSymbol> v = methodTable.get(this.name);
	CgenSupport.emitDispTableRef(this.name, s); s.print(CgenSupport.LABEL);
        for (int i=0;i<v.size();i++) {
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


    // object init
    public void printInitBegin(PrintStream s) {
        s.print(this.name + "_init" + CgenSupport.LABEL); // label
        s.println("\taddiu	$sp $sp -12");
        s.println("\tsw	$fp 12($sp)");
        s.println("\tsw	$s0 8($sp)");
        s.println("\tsw	$ra 4($sp)");
        s.println("\taddiu	$fp $sp 4");
        s.println("\tmove	$s0 $a0");
    }

    public void printInitEnd(PrintStream s) {
        s.println("\tmove	$a0 $s0");
        s.println("\tlw	$fp 12($sp)");
        s.println("\tlw	$s0 8($sp)");
	    s.println("\tlw	$ra 4($sp)");
        s.println("\taddiu	$sp $sp 12");
        s.println("\tjr	$ra	");
    }

    // **********************************print method*************************

    // codeMethod
    public void codeMethod(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable, Vector<AbstractSymbol> classNameTable) {
        SymbolTable varTab = new SymbolTable(); // Addr
        varTab.enterScope();
        printInitBegin(s);
        // 1. add all attr and set default value
        // two type of environment E: attr and stack variable
        Enumeration fs = this.features.getElements();
        int cnt = 0;
        int cntParent = 0;
        CgenNode node = this;
        Stack<CgenNode> st = new Stack<CgenNode>();
        while (node.name != TreeConstants.Object_) {
            st.push(node);
            node = node.parent;
        }
        while (!st.empty()) {
            node = st.pop();
            fs = node.features.getElements();
            while (fs.hasMoreElements()) {
                Object e = fs.nextElement();
                if (!(e instanceof attr)) {
                    continue;
                }
                attr p = (attr)e;
                varTab.addId(p.name, new Addr(Addr.TypeAttr, cnt+CgenSupport.DEFAULT_OBJFIELDS));
                // if (node.name != this.name) {
                //     cnt++;
                //     cntParent++;
                //     continue;
                // }
                // need init again, because not only copy from protObj
                if (p.type_decl == TreeConstants.Int) {
                    CgenSupport.emitPartialLoadAddress(CgenSupport.T1, s);
                    ((IntSymbol)AbstractTable.inttable.addInt(0)).codeRef(s);
                    s.println();
                    CgenSupport.emitStore(CgenSupport.T1, cnt+CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.SELF, s);
                } else if (p.type_decl == TreeConstants.Str) {
                    CgenSupport.emitPartialLoadAddress(CgenSupport.T1, s);
                    ((StringSymbol)AbstractTable.stringtable.addString("")).codeRef(s);
                    s.println();
                    CgenSupport.emitStore(CgenSupport.T1, cnt+CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.SELF, s);
                } else if (p.type_decl == TreeConstants.Bool) {
                    CgenSupport.emitPartialLoadAddress(CgenSupport.T1, s);
                    BoolConst.falsebool.codeRef(s);
                    s.println();
                    CgenSupport.emitStore(CgenSupport.T1, cnt+CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.SELF, s);
                } else {
                    CgenSupport.emitMove(CgenSupport.T1, CgenSupport.ZERO, s);
                    CgenSupport.emitStore(CgenSupport.T1, cnt+CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.SELF, s);
                }
                cnt++;
                if (node.name != this.name) {
                    cntParent++;
                }
            }
        }

        // 2. call parent init
        s.println("\tmove	$a0 $s0");
        if (this.name != TreeConstants.Object_) {
            s.println("\tjal	" + this.parent.name + "_init");
        }

	    // 3. attr init expr
        // assumption: object in $a0
        fs = this.features.getElements();
        cnt = cntParent;
        while (fs.hasMoreElements()) {
            Object e = fs.nextElement();
            if (e instanceof attr) {
                attr p = (attr)e;
                if (!(p.init instanceof no_expr)) {
                    // init
                    int offsetCnt = -1; // -1(fp)
                    codeExpression(s, methodTable, varTab, p.init, offsetCnt, classNameTable);
                    // rewrite protObj
                    CgenSupport.emitStore(CgenSupport.ACC, cnt+CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.SELF, s);
                }
                cnt++;
            }
        }
        printInitEnd(s);

        // ********************* begin method
        fs = this.features.getElements();
        cnt = 0;
        while (fs.hasMoreElements()) {
            Object e = fs.nextElement();
            if (e instanceof method) {
                // methodTable
                method p = (method)e;
                Vector<AbstractSymbol> v = methodTable.get(this.name);
                codeSingleMethod(s, methodTable, varTab, p, classNameTable);
            }
        }
        varTab.exitScope();
    }

    // codeSingleMethod
    public void codeSingleMethod(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable, SymbolTable varTab, method m, Vector<AbstractSymbol> classNameTable) {
        varTab.enterScope();
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
        int argCnt = 0; // distance of fp. word
        Enumeration es = m.formals.getElements();
        while (es.hasMoreElements()) {
            formal sub = (formal)es.nextElement();
            varTab.addId(sub.name, new Addr(Addr.TypeStack, argCnt+3));
            argCnt++;
        }

        // method implementation
        int offsetCnt = -1; // -4(fp)
        codeExpression(s, methodTable, varTab, m.expr, offsetCnt, classNameTable);

        // return
        // CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
        CgenSupport.emitLoad(CgenSupport.FP, 3, CgenSupport.SP, s);
        CgenSupport.emitLoad(CgenSupport.SELF, 2, CgenSupport.SP, s);
        CgenSupport.emitLoad(CgenSupport.RA, 1, CgenSupport.SP, s);
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 12+(4*argCnt), s);
        CgenSupport.emitReturn(s);
        varTab.exitScope();
    }

    // codeExpression
    public void codeExpression(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable, SymbolTable varTab, Expression e, int offsetCnt, Vector<AbstractSymbol> classNameTable) {
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
                codeExpression(s, methodTable, varTab, sub, offsetCnt, classNameTable);
                // return value in $a0
            }
        } else if (e instanceof dispatch) {
            // dispatch
            dispatch p = (dispatch)e;
            // 1. resolve actual args
            Enumeration es = p.actual.getElements();
            int argCnt = 0;
            while (es.hasMoreElements()) {
                Expression sub = (Expression)es.nextElement();
                argCnt++;
            }
            // newloc stack
            CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, -4*argCnt, s);
	        offsetCnt -= argCnt;
            // calc and store
            int index = 1;
            es = p.actual.getElements();
            while (es.hasMoreElements()) {
                Expression sub = (Expression)es.nextElement();
                codeExpression(s, methodTable, varTab, sub, offsetCnt, classNameTable);
                // save args
                CgenSupport.emitStore(CgenSupport.ACC, index, CgenSupport.SP, s);
                index++;
            }
	        // 2. evolve e0
            codeExpression(s, methodTable, varTab, p.expr, offsetCnt, classNameTable);
            // if e0 is zero, abort
            int labelZero = CgenSupport.labelCnt++;
            CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.ZERO, labelZero, s);
            // get classtag -> classname, classtag -> dispatchtable
            // use p.expr'type, it's static type, but index is the same
	        AbstractSymbol className = p.expr.get_type();
            if (className == TreeConstants.SELF_TYPE) {
                className = this.name;
            }
	        if (className == TreeConstants.Str && p.name == TreeConstants.substr) {
                // revert two arg
                CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.SP, s);
                CgenSupport.emitLoad(CgenSupport.T2, 1, CgenSupport.SP, s);
                CgenSupport.emitStore(CgenSupport.T2, 2, CgenSupport.SP, s);
                CgenSupport.emitStore(CgenSupport.T1, 1, CgenSupport.SP, s);
            }
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(className);
	        CgenNode tmp = (CgenNode)(table.lookup(className));
            while (true) {
	        	for (int i=0;i<v.size();++i){ 
                    if(v.get(i) == AbstractTable.stringtable.addString(className + "." + p.name)) {
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
            // recover offsetCnt
            offsetCnt += argCnt;
            // end
            int labelEnd = CgenSupport.labelCnt++;
            CgenSupport.emitBranch(labelEnd, s);
            // abort
            CgenSupport.emitLabelDef(labelZero, s);
            // line number in $t1
            CgenSupport.emitLoadImm(CgenSupport.T1, p.getLineNumber(), s);
            // filename in $a0
            CgenSupport.emitLoadAddress(CgenSupport.ACC,
                CgenSupport.STRCONST_PREFIX + AbstractTable.stringtable.lookup(this.getFilename().toString()).index, s);
            // call case_abort
            CgenSupport.emitJal("_dispatch_abort", s);
            // end
            CgenSupport.emitLabelDef(labelEnd, s);
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
            StringSymbol token = (StringSymbol)AbstractTable.stringtable.addString(p.token.toString());
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX + token.index, s);
        } else if (e instanceof assign) {
            assign p = (assign)e;
            // cal expr
            codeExpression(s, methodTable, varTab, p.expr, offsetCnt, classNameTable);
            // assign
            Addr add = (Addr)varTab.lookup(p.name);
            // no self
            if (add == null ) {
                System.out.println("[assign] should be never occur! p.name: " + p.name);
            }
            if (add.type == Addr.TypeAttr) {
                CgenSupport.emitStore(CgenSupport.ACC, add.offset, CgenSupport.SELF, s);
            } else {
                CgenSupport.emitStore(CgenSupport.ACC, add.offset, CgenSupport.FP, s);
            }
            // return value in $a0
        } else if (e instanceof bool_const) {
            bool_const p = (bool_const)e;
            if (p.val) {
                CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.BOOLCONST_PREFIX + "1", s);
            } else {
                CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.BOOLCONST_PREFIX + "0", s);
            }
        } else if (e instanceof int_const) {
            int_const p = (int_const)e;
            IntSymbol token = (IntSymbol)AbstractTable.inttable.addString(p.token.toString());
            CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + token.index, s);
        } else if (e instanceof cond) {
            cond p = (cond)e;
            // pred
            codeExpression(s, methodTable, varTab, p.pred, offsetCnt, classNameTable);
            CgenSupport.emitLoadBool(CgenSupport.T1, BoolConst.truebool, s);
            int trueLable = CgenSupport.labelCnt++;
            CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.T1, trueLable, s);
            // print else and jump to end of if
            codeExpression(s, methodTable, varTab, p.else_exp, offsetCnt, classNameTable);
            int endIfLable = CgenSupport.labelCnt++;
            CgenSupport.emitBranch(endIfLable, s);
            // print true
            CgenSupport.emitLabelDef(trueLable, s);
            codeExpression(s, methodTable, varTab, p.then_exp, offsetCnt, classNameTable);
            // end if
            CgenSupport.emitLabelDef(endIfLable, s);
        } else if (e instanceof isvoid) {
            isvoid p = (isvoid)e;
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            // determine
            int trueLable = CgenSupport.labelCnt;
            CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.ZERO, trueLable, s);
            CgenSupport.labelCnt++;
            // not equal / not void
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
            int endIfLable = CgenSupport.labelCnt;
            CgenSupport.emitBranch(endIfLable, s);
            CgenSupport.labelCnt++;
            // euqal / void
            CgenSupport.emitLabelDef(trueLable, s);
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
            // end if
            CgenSupport.emitLabelDef(endIfLable, s);
        } else if (e instanceof eq) {
            eq p = (eq)e;
            // cal first
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // cal second
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // compare euqal
            int trueLable = CgenSupport.labelCnt++;
            CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.ACC, trueLable, s);
            // else, address is not euqal
            // call equality_test
            CgenSupport.emitMove(CgenSupport.T2, CgenSupport.ACC, s);
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
            CgenSupport.emitLoadBool(CgenSupport.A1, BoolConst.falsebool, s);
            CgenSupport.emitJal("equality_test", s);
            CgenSupport.emitBne(CgenSupport.ACC, CgenSupport.A1, trueLable, s);
            // false
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
            int endIfLable = CgenSupport.labelCnt++;
            CgenSupport.emitBranch(endIfLable, s);
            // trueLable
            CgenSupport.emitLabelDef(trueLable, s);
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
            // end if
            CgenSupport.emitLabelDef(endIfLable, s);
        } else if (e instanceof let) {
            let p = (let)e;
            // evaluate init expr
            if (p.init instanceof no_expr) {
                // Int, Bool, and Str use default value
                if (p.type_decl == TreeConstants.Int) {
                    IntSymbol is = (IntSymbol)AbstractTable.inttable.addInt(0);
                    CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.INTCONST_PREFIX + is.index, s);
                } else if (p.type_decl == TreeConstants.Bool) {
                    CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.BOOLCONST_PREFIX + "0", s);
                } else if (p.type_decl == TreeConstants.Str) {
                    StringSymbol ss = (StringSymbol)AbstractTable.stringtable.addString("");
                    CgenSupport.emitLoadAddress(CgenSupport.ACC, CgenSupport.STRCONST_PREFIX + ss.index, s);
                } else {
                    // use zero, not protObj
                    CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s);
                }
            } else {
                codeExpression(s, methodTable, varTab, p.init, offsetCnt, classNameTable);
	        }
            // store and add new variable
            varTab.enterScope(); // enter
            CgenSupport.emitPush(CgenSupport.ACC, s);
            varTab.addId(p.identifier, new Addr(Addr.TypeStack, offsetCnt));
            offsetCnt--;
            // calc body
            codeExpression(s, methodTable, varTab, p.body, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            varTab.exitScope(); // exit
        } else if (e instanceof static_dispatch) {
            // static_dispatch
            static_dispatch p = (static_dispatch)e;
            // 1. resolve actual args
            Enumeration es = p.actual.getElements();
            int argCnt = 0;
            while (es.hasMoreElements()) {
                Expression sub = (Expression)es.nextElement();
                argCnt++;
            }
            // newloc stack
            CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, -4*argCnt, s);
	        offsetCnt -= argCnt;
            // calc and store
            int index = 1;
            es = p.actual.getElements();
            while (es.hasMoreElements()) {
                Expression sub = (Expression)es.nextElement();
                codeExpression(s, methodTable, varTab, sub, offsetCnt, classNameTable);
                // save args
                CgenSupport.emitStore(CgenSupport.ACC, index, CgenSupport.SP, s);
                index++;
            }
	        // 2. evolve e0
            codeExpression(s, methodTable, varTab, p.expr, offsetCnt, classNameTable);
            // if e0 is zero, abort
            int labelZero = CgenSupport.labelCnt++;
            CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.ZERO, labelZero, s);
            // get classtag -> classname, classtag -> dispatchtable
            // use p.expr'type, it's static type, but index is the same
            AbstractSymbol className = p.type_name;
            // can't dispatch SELF_TYPE
            // if (className == TreeConstants.SELF_TYPE) {
            //     className = this.name;
            // }
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(className);
	    CgenNode tmp = (CgenNode)(table.lookup(className));
            while (true) {
	        	for (int i=0;i<v.size();++i){ 
                    if(v.get(i) == AbstractTable.stringtable.addString(className + "." + p.name)) {
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
	    CgenSupport.emitLoadAddress(CgenSupport.T1, className + CgenSupport.DISPTAB_SUFFIX, s);
            // method addr
	        CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s);
            // jump to method
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // recover offsetCnt
            offsetCnt += argCnt;
            // end
            int labelEnd = CgenSupport.labelCnt++;
            CgenSupport.emitBranch(labelEnd, s);
            // abort
            CgenSupport.emitLabelDef(labelZero, s);
            // line number in $t1
            CgenSupport.emitLoadImm(CgenSupport.T1, p.getLineNumber(), s);
            // filename in $a0
            CgenSupport.emitLoadAddress(CgenSupport.ACC,
                CgenSupport.STRCONST_PREFIX + AbstractTable.stringtable.lookup(this.getFilename().toString()).index, s);
            // call case_abort
            CgenSupport.emitJal("_dispatch_abort", s);
            // end
            CgenSupport.emitLabelDef(labelEnd, s);
            // return value in $a0
        } else if (e instanceof new_) {
            // new_
            new_ p = (new_)e;
            // type_name
            AbstractSymbol as = p.type_name;
	    if (as == TreeConstants.SELF_TYPE) {
                CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
            } else {
                // call copy
                if (as == TreeConstants.Bool) {
                    CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
                } else {
                    CgenSupport.emitLoadAddress(CgenSupport.ACC, as + CgenSupport.PROTOBJ_SUFFIX, s);
                }
            }
            // get dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s); // dispatch table
            // get offset
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Object_);
            for (int i=0;i<v.size();++i){ 
                if(v.get(i) == AbstractTable.stringtable.addString(TreeConstants.Object_ + "." + TreeConstants.copy)) {
                    offset = i;
                    break;
                } 
            }
            if (offset == -1) {
                System.out.println("never occur!");
            }
            CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s); // copy method address
            CgenSupport.emitJalr(CgenSupport.T1, s); // call copy, with args in a0
	    // call init
	    CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s); // dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, -2, CgenSupport.T1, s);
            CgenSupport.emitJalr(CgenSupport.T1, s); // call init
            // return address in a0
        } else if (e instanceof comp) {
            // comp
            comp p = (comp)e;
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, s);
            CgenSupport.emitLoadImm(CgenSupport.T2, 1, s);
            // compare
            int falseLable = CgenSupport.labelCnt;
            CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, falseLable, s);
            CgenSupport.labelCnt++;
            // result is true
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
            int endIfLable = CgenSupport.labelCnt;
            CgenSupport.emitBranch(endIfLable, s);
            CgenSupport.labelCnt++;
            // result is false
            CgenSupport.emitLabelDef(falseLable, s);
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
            // return address in a0
            CgenSupport.emitLabelDef(endIfLable, s);
        } else if (e instanceof lt) {
            // lt
            lt p = (lt)e;
            // e1
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // e2 
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // compare
	        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, s);
            CgenSupport.emitLoad(CgenSupport.ACC, 3, CgenSupport.ACC, s);
            CgenSupport.emitSlt(CgenSupport.T2, CgenSupport.T1, CgenSupport.ACC, s);
            // jump
            CgenSupport.emitLoadImm(CgenSupport.T1, 1, s);
            int trueLable = CgenSupport.labelCnt;
            CgenSupport.emitBeq(CgenSupport.T2, CgenSupport.T1, trueLable, s);
            CgenSupport.labelCnt++;
            // false
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
            int endIfLable = CgenSupport.labelCnt;
            CgenSupport.emitBranch(endIfLable, s);
            CgenSupport.labelCnt++;
            // true
            CgenSupport.emitLabelDef(trueLable, s);
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
            // endif
            CgenSupport.emitLabelDef(endIfLable, s);
        } else if (e instanceof leq) {
            // leq
            leq p = (leq)e;
            // e1
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // e2 
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // compare
	        CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.T1, s);
            CgenSupport.emitLoad(CgenSupport.ACC, 3, CgenSupport.ACC, s);
            CgenSupport.emitSle(CgenSupport.T2, CgenSupport.T1, CgenSupport.ACC, s);
            // jump
            CgenSupport.emitLoadImm(CgenSupport.T1, 1, s);
            int trueLable = CgenSupport.labelCnt;
            CgenSupport.emitBeq(CgenSupport.T2, CgenSupport.T1, trueLable, s);
            CgenSupport.labelCnt++;
            // false
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
            int endIfLable = CgenSupport.labelCnt;
            CgenSupport.emitBranch(endIfLable, s);
            CgenSupport.labelCnt++;
            // true
            CgenSupport.emitLabelDef(trueLable, s);
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.truebool, s);
            // endif
            CgenSupport.emitLabelDef(endIfLable, s);
        } else if (e instanceof neg) {
            // neg
            neg p = (neg)e;
            // e1
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
	    CgenSupport.emitLoad(CgenSupport.T1, 3, CgenSupport.ACC, s);
            CgenSupport.emitNeg(CgenSupport.T1, CgenSupport.T1, s);

	    // store result
            CgenSupport.emitPush(CgenSupport.T1, s);
            offsetCnt--;
            // acc need copy
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Object_);
            for (int i=0;i<v.size();++i){ 
                if(v.get(i) == AbstractTable.stringtable.addString(TreeConstants.Object_ + "." + TreeConstants.copy)) {
                    offset = i;
                    break;
                } 
            }
            if (offset == -1) {
                System.out.println("never occur!");
            }
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s); // dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s); // copy method
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // restore t1
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;

            CgenSupport.emitStore(CgenSupport.T1, 3, CgenSupport.ACC, s);
        } else if (e instanceof plus) {
            // plus
            plus p = (plus)e;
            // e1
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // e2
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // add
            CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); // acc's int value
            CgenSupport.emitLoad(CgenSupport.T3, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T1, s); // t1's int value
            CgenSupport.emitAdd(CgenSupport.T1, CgenSupport.T2, CgenSupport.T3, s);
            // store result
            CgenSupport.emitPush(CgenSupport.T1, s);
            offsetCnt--;
            // acc need copy
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Object_);
            for (int i=0;i<v.size();++i){ 
                if(v.get(i) == AbstractTable.stringtable.addString(TreeConstants.Object_ + "." + TreeConstants.copy)) {
                    offset = i;
                    break;
                } 
            }
            if (offset == -1) {
                System.out.println("never occur!");
            }
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s); // dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s); // copy method
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // restore t1
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // result rewrite acc
            CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); 
        } else if (e instanceof sub) {
            // sub
            sub p = (sub)e;
            // e1
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // e2
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // sub
            CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); // acc's int value
            CgenSupport.emitLoad(CgenSupport.T3, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T1, s); // t1's int value
            CgenSupport.emitSub(CgenSupport.T1, CgenSupport.T3, CgenSupport.T2, s);
            // store result
            CgenSupport.emitPush(CgenSupport.T1, s);
            offsetCnt--;
            // acc need copy
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Object_);
            for (int i=0;i<v.size();++i){ 
                if(v.get(i) == AbstractTable.stringtable.addString(TreeConstants.Object_ + "." + TreeConstants.copy)) {
                    offset = i;
                    break;
                } 
            }
            if (offset == -1) {
                System.out.println("never occur!");
            }
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s); // dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s); // copy method
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // restore t1
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // result rewrite acc
            CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); 
        } else if (e instanceof mul) {
            // mul
            mul p = (mul)e;
            // e1
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // e2
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // mul
            CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); // acc's int value
            CgenSupport.emitLoad(CgenSupport.T3, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T1, s); // t1's int value
            CgenSupport.emitMul(CgenSupport.T1, CgenSupport.T2, CgenSupport.T3, s);
            // store result
            CgenSupport.emitPush(CgenSupport.T1, s);
            offsetCnt--;
            // acc need copy
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Object_);
            for (int i=0;i<v.size();++i){ 
                if(v.get(i) == AbstractTable.stringtable.addString(TreeConstants.Object_ + "." + TreeConstants.copy)) {
                    offset = i;
                    break;
                } 
            }
            if (offset == -1) {
                System.out.println("never occur!");
            }
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s); // dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s); // copy method
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // restore t1
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // result rewrite acc
            CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); 
        } else if (e instanceof divide) {
            // divide
            divide p = (divide)e;
            // e1
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt, classNameTable);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // e2
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt, classNameTable);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // divide
            CgenSupport.emitLoad(CgenSupport.T2, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); // acc's int value
            CgenSupport.emitLoad(CgenSupport.T3, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.T1, s); // t1's int value
            CgenSupport.emitDiv(CgenSupport.T1, CgenSupport.T3, CgenSupport.T2, s);
            // store result
            CgenSupport.emitPush(CgenSupport.T1, s);
            offsetCnt--;
            // acc need copy
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(TreeConstants.Object_);
            for (int i=0;i<v.size();++i){ 
                if(v.get(i) == AbstractTable.stringtable.addString(TreeConstants.Object_ + "." + TreeConstants.copy)) {
                    offset = i;
                    break;
                } 
            }
            if (offset == -1) {
                System.out.println("never occur!");
            }
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s); // dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, offset, CgenSupport.T1, s); // copy method
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // restore t1
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // result rewrite acc
            CgenSupport.emitStore(CgenSupport.T1, CgenSupport.DEFAULT_OBJFIELDS, CgenSupport.ACC, s); 
        } else if (e instanceof typcase) {
            typcase p = (typcase)e;
            int labelBeginCase = CgenSupport.labelCnt;
            CgenSupport.labelCnt++;
            codeExpression(s, methodTable, varTab, p.expr, offsetCnt, classNameTable);
            // check acc is zero
            int labelZeroAbort = CgenSupport.labelCnt;
            CgenSupport.labelCnt++;
            CgenSupport.emitBeq(CgenSupport.ZERO, CgenSupport.ACC, labelZeroAbort, s);
            // save expr's classtag
            int tagAddress = offsetCnt;
            CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.ACC, s);
            // CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.T1, s);
            CgenSupport.emitPush(CgenSupport.T1, s);
            offsetCnt--;
            // save expr's value
            int vAddress = offsetCnt;
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // *********************get each branch's classtag and save in stack
            // classtag and expr address
            int cnt = 0;
            Enumeration<branch> bs = p.cases.getElements();
            Vector<Integer> vIndex = new Vector<Integer>();
            while (bs.hasMoreElements()) {
                branch b = (branch)bs.nextElement();
                // get type_name classtag
                int classTag = -1;
                for (int i=0;i<classNameTable.size();i++) {
                    if (classNameTable.get(i) == b.type_decl) {
                        classTag = i;
                        break;
                    }
                }
		        // save classTag in the stack
                CgenSupport.emitLoadImm(CgenSupport.T1, classTag, s);
                CgenSupport.emitPush(CgenSupport.T1, s);
                offsetCnt--;
                // save expr address in stack
                int labelTmp = CgenSupport.labelCnt++;
                vIndex.add(labelTmp);
                CgenSupport.emitLoadAddress(CgenSupport.T1, "label" + labelTmp, s);
                CgenSupport.emitPush(CgenSupport.T1, s);
                offsetCnt--;
                cnt++;
            }
            // jump to dynamic selected
            int labelLoop = CgenSupport.labelCnt++;
            CgenSupport.emitJal("label" + labelLoop, s);
            // method gen
            bs = p.cases.getElements();
            int index = 0;
            while (bs.hasMoreElements()) {
                branch b = (branch)bs.nextElement();
                varTab.enterScope();
                varTab.addId(b.name, new Addr(Addr.TypeStack, vAddress));
                CgenSupport.emitLabelDef(vIndex.get(index), s); // label
                codeExpression(s, methodTable, varTab, b.expr, offsetCnt, classNameTable);
                varTab.exitScope();
                CgenSupport.emitJal("label" + labelBeginCase, s);
                index++;
            }

            // *********************begin dynamic selected
            CgenSupport.emitLabelDef(labelLoop, s); // loop:
            int labeEndSelect = CgenSupport.labelCnt;
            CgenSupport.labelCnt++;
            for (int i=1;i<=cnt;i++) {
                CgenSupport.emitLoadImm(CgenSupport.T3, (2*i-1)*4, s); // result offset
                CgenSupport.emitLoad(CgenSupport.T1, 2*i, CgenSupport.SP, s);
                CgenSupport.emitLoad(CgenSupport.T2, tagAddress, CgenSupport.FP, s);
                // if t1 == t2, break
                CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, labeEndSelect, s);
            }
            // if is object, abort
            int labelAbortNoMatch = CgenSupport.labelCnt;
            CgenSupport.labelCnt++;
            CgenSupport.emitLoadImm(CgenSupport.T1, 3, s); // object class tag
            CgenSupport.emitLoad(CgenSupport.T2, tagAddress, CgenSupport.FP, s);
            CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.T2, labelAbortNoMatch, s); // jump to abort

            // not match this classtag, classtag <- classtag.parent
            CgenSupport.emitLoad(CgenSupport.T1, vAddress, CgenSupport.FP, s);
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.T1, s); // dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, -1, CgenSupport.T1, s); // parent protObj address
            CgenSupport.emitStore(CgenSupport.T1, vAddress, CgenSupport.FP, s); // rewrite vAddress
            // tag
            CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.T1, s); // parent tag
            CgenSupport.emitStore(CgenSupport.T1, tagAddress, CgenSupport.FP, s); // rewrite tagAddress
            CgenSupport.emitJal("label" + labelLoop, s); // jump to loop begin

            // !!!abort no branch matched
            CgenSupport.emitLabelDef(labelAbortNoMatch, s);
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
            CgenSupport.emitJal("_case_abort", s); // call case_abort

            // !!!abor on zero expr
            CgenSupport.emitLabelDef(labelZeroAbort, s);
            // line number in $t1
            CgenSupport.emitLoadImm(CgenSupport.T1, p.getLineNumber(), s);
            // filename in $a0
            CgenSupport.emitLoadAddress(CgenSupport.ACC,
                CgenSupport.STRCONST_PREFIX + AbstractTable.stringtable.lookup(this.getFilename().toString()).index, s);
            // call case_abort
            CgenSupport.emitJal("_case_abort2", s);

            // *********************end dynamic selected
            CgenSupport.emitLabelDef(labeEndSelect, s);
            CgenSupport.emitAdd(CgenSupport.T1, CgenSupport.T3, CgenSupport.SP, s);
            CgenSupport.emitLoad(CgenSupport.T1, 0, CgenSupport.T1, s); // expr jump address
            CgenSupport.emitJalr(CgenSupport.T1, s);
            // result address
            CgenSupport.emitLabelDef(labelBeginCase, s);
            // pop to recover stack
            CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, cnt*8 + 8, s);
            offsetCnt+=2*cnt+2;
            // return value in $a0
        } else if (e instanceof loop) {
            loop p = (loop)e;
            int labelLoop = CgenSupport.labelCnt++;
            int labelBreak = CgenSupport.labelCnt++;
            CgenSupport.emitLabelDef(labelLoop, s);
            // pred
            codeExpression(s, methodTable, varTab, p.pred, offsetCnt, classNameTable);
            CgenSupport.emitLoadBool(CgenSupport.T1, BoolConst.falsebool, s);
            CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.T1, labelBreak, s);
            // body
            codeExpression(s, methodTable, varTab, p.body, offsetCnt, classNameTable);
            CgenSupport.emitJal("label" + labelLoop, s);
            // end
            CgenSupport.emitLabelDef(labelBreak, s);
            CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.ZERO, s); // where statement return value is zero
       }
    }
}
    

    
