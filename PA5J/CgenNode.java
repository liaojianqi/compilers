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
                // methodTable
                method p = (method)e;
                Vector<AbstractSymbol> v = methodTable.get(this.name);
                codeSingleMethod(s, methodTable, varTab, p);
            }
        }
        varTab.exitScope();
    }

    // codeSingleMethod
    public void codeSingleMethod(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable, SymbolTable varTab, method m) {
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
        int offsetCnt = -1; // -1(fp)
        codeExpression(s, methodTable, varTab, m.expr, offsetCnt);

        // return
        CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, s);
        CgenSupport.emitLoad(CgenSupport.FP, 3, CgenSupport.SP, s);
        CgenSupport.emitLoad(CgenSupport.SELF, 2, CgenSupport.SP, s);
        CgenSupport.emitLoad(CgenSupport.RA, 1, CgenSupport.SP, s);
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 12+(4*argCnt), s);
        CgenSupport.emitReturn(s);
        varTab.exitScope();
    }

    // codeExpression
    public void codeExpression(PrintStream s, HashMap<AbstractSymbol, Vector<AbstractSymbol>> methodTable, SymbolTable varTab, Expression e, int offsetCnt) {
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
                codeExpression(s, methodTable, varTab, sub, offsetCnt);
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
                codeExpression(s, methodTable, varTab, sub, offsetCnt);
                // save args
                CgenSupport.emitStore(CgenSupport.ACC, index, CgenSupport.SP, s);
                index++;
            }
	        // 2. evolve e0
            codeExpression(s, methodTable, varTab, p.expr, offsetCnt);
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
            codeExpression(s, methodTable, varTab, p.expr, offsetCnt);
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
            codeExpression(s, methodTable, varTab, p.pred, offsetCnt);
            CgenSupport.emitLoadBool(CgenSupport.T1, BoolConst.truebool, s);
            int trueLable = CgenSupport.labelCnt;
            CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.T1, trueLable, s);
            CgenSupport.labelCnt++;
            // print else and jump to end of if
            codeExpression(s, methodTable, varTab, p.else_exp, offsetCnt);
            int endIfLable = CgenSupport.labelCnt;
            CgenSupport.emitBranch(endIfLable, s);
            CgenSupport.labelCnt++;
            // print true
            CgenSupport.emitLabelDef(trueLable, s);
            codeExpression(s, methodTable, varTab, p.then_exp, offsetCnt);
            // end if
            CgenSupport.emitLabelDef(endIfLable, s);
        } else if (e instanceof isvoid) {
            isvoid p = (isvoid)e;
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt);
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
            codeExpression(s, methodTable, varTab, p.e1, offsetCnt);
            CgenSupport.emitPush(CgenSupport.ACC, s);
            offsetCnt--;
            // cal second
            codeExpression(s, methodTable, varTab, p.e2, offsetCnt);
            CgenSupport.emitPop(CgenSupport.T1, s);
            offsetCnt++;
            // compare euqal
            int trueLable = CgenSupport.labelCnt;
            CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.ACC, trueLable, s);
            CgenSupport.labelCnt++;
            // else, address is not euqal
            // isvoid
            int endElseLable = CgenSupport.labelCnt;
            CgenSupport.emitBeq(CgenSupport.T1, CgenSupport.ZERO, endElseLable, s);
            CgenSupport.emitBeq(CgenSupport.ACC, CgenSupport.ZERO, endElseLable, s);
            CgenSupport.labelCnt++;
            if (p.e1.get_type() == p.e2.get_type() && (p.e1.get_type() == TreeConstants.Int)) {
                CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, s);
                CgenSupport.emitLoad(CgenSupport.T3, 3, CgenSupport.T1, s);
                CgenSupport.emitBeq(CgenSupport.T2, CgenSupport.T3, trueLable, s);
                CgenSupport.emitBranch(endElseLable, s);    
            } else if (p.e1.get_type() == p.e2.get_type() && (p.e1.get_type() == TreeConstants.Str)) {
                CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, s);
                CgenSupport.emitLoad(CgenSupport.T3, 3, CgenSupport.T1, s);
                CgenSupport.emitBne(CgenSupport.T2, CgenSupport.T3, endElseLable, s);
                // ascii
                CgenSupport.emitLoadImm(CgenSupport.T2, 4, s);
                CgenSupport.emitLoad(CgenSupport.T3, 3, CgenSupport.ACC, s); // length
                CgenSupport.emitLoad(CgenSupport.T3, 3, CgenSupport.T3, s);
                CgenSupport.emitAddiu(CgenSupport.T3, CgenSupport.T3, 4, s); // +4
                CgenSupport.emitDiv(CgenSupport.T3, CgenSupport.T3, CgenSupport.T2, s); // /4
                CgenSupport.emitLoadImm(CgenSupport.T2, 1, s); // incr
		
                int loop = CgenSupport.labelCnt;
                CgenSupport.emitLabelDef(loop, s);
                CgenSupport.labelCnt++;
                // loop: 
                CgenSupport.emitPush(CgenSupport.T2, s); // push
                CgenSupport.emitPush(CgenSupport.T3, s); // push
                CgenSupport.emitAddiu(CgenSupport.ACC, CgenSupport.ACC, 4, s);
                CgenSupport.emitAddiu(CgenSupport.T1, CgenSupport.T1, 4, s);
                CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, s);
                CgenSupport.emitLoad(CgenSupport.T3, 3, CgenSupport.T1, s);
                CgenSupport.emitBne(CgenSupport.T2, CgenSupport.T3, endElseLable, s);
                // pop
                CgenSupport.emitPop(CgenSupport.T3, s);
                CgenSupport.emitPop(CgenSupport.T2, s);
                CgenSupport.emitBeq(CgenSupport.T2, CgenSupport.T3, trueLable, s);
                CgenSupport.emitAddiu(CgenSupport.T2, CgenSupport.T2, 1, s);
                // to loop
                CgenSupport.emitBranch(loop, s); 
            } else if (p.e1.get_type() == p.e2.get_type() && (p.e1.get_type() == TreeConstants.Bool)) {
                CgenSupport.emitLoad(CgenSupport.T2, 3, CgenSupport.ACC, s);
                CgenSupport.emitLoad(CgenSupport.T3, 3, CgenSupport.T1, s);
                CgenSupport.emitBeq(CgenSupport.T2, CgenSupport.T3, trueLable, s);
                CgenSupport.emitBranch(endElseLable, s);
            }
            // endElse label
            CgenSupport.emitLabelDef(endElseLable, s);	
            CgenSupport.emitLoadBool(CgenSupport.ACC, BoolConst.falsebool, s);
            int endIfLable = CgenSupport.labelCnt;
            CgenSupport.emitBranch(endIfLable, s);
            CgenSupport.labelCnt++;
            // trueLable, address is equal
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
                codeExpression(s, methodTable, varTab, p.init, offsetCnt);
            }
            // store and add new variable
            varTab.enterScope(); // enter
            CgenSupport.emitPush(CgenSupport.ACC, s);
            varTab.addId(p.identifier, new Addr(Addr.TypeStack, offsetCnt));
            offsetCnt--;
            // calc body
            codeExpression(s, methodTable, varTab, p.body, offsetCnt);
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
                codeExpression(s, methodTable, varTab, sub, offsetCnt);
                // save args
                CgenSupport.emitStore(CgenSupport.ACC, index, CgenSupport.SP, s);
                index++;
            }
	        // 2. evolve e0
            codeExpression(s, methodTable, varTab, p.expr, offsetCnt);
            // get classtag -> classname, classtag -> dispatchtable
            // use p.expr'type, it's static type, but index is the same
            AbstractSymbol className = p.type_name;
            // can't dispatch SELF_TYPE
            // if (className == TreeConstants.SELF_TYPE) {
            //     className = this.name;
            // }
            int offset = -1;
            Vector<AbstractSymbol> v = methodTable.get(className);
            CgenNode tmp = this;
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
            // return value in $a0
        } else if (e instanceof new_) {
            // new_
            new_ p = (new_)e;
            // type_name
            AbstractSymbol as = p.type_name;
            if (as == TreeConstants.SELF_TYPE) {
                as = this.name;
            }
            // call copy
            CgenSupport.emitLoadAddress(CgenSupport.ACC, as + CgenSupport.PROTOBJ_SUFFIX, s);
            // get dispatch table
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.ACC, s);
            CgenSupport.emitLoad(CgenSupport.T1, 2, CgenSupport.T1, s); // copy method address
            CgenSupport.emitJalr(CgenSupport.T1, s); // call copy, with args in a0
            // return address in a0
       }
    }
}
    

    
