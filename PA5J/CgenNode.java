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
    public void codeDef(PrintStream s) {
    	// for basic class(IO, String)
        if (this.name == TreeConstants.Int) {
            table.codeInt(s, 0);
	        return;
        } else if (this.name == TreeConstants.Str) {
            table.codeString(s, "");
            return;
        } else if (this.name == TreeConstants.Bool) {
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
        s.println("0");
        // attributs
        printAttr(s);
    }

    // print attributes
    public void printAttr(PrintStream s) {
        // object
        if (this.name == TreeConstants.Object_) {
            return ;
        }
        // parent
        if (this.parent != null) {
            this.parent.printAttr(s);
        }
        // current
        Enumeration fs = this.features.getElements();
        while (fs.hasMoreElements()) {
            Object e = fs.nextElement();
            if (e instanceof attr) {
                attr p = (attr)e;
                s.print(CgenSupport.WORD);
                CgenSupport.emitProtObjRef(p.type_decl, s);
                s.println("");
            }
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
}
    

    
