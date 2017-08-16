package com.neocoretechs.neovolve.fit;

// Copyright (c) 2002 Cunningham & Cunningham, Inc.
// Released under the terms of the GNU General Public License version 2 or later.
/**
* @author Groff
*/
import java.lang.reflect.*;
import java.io.Serializable;

public class ColumnFixture extends Fixture implements Serializable {

    protected TypeAdapter columnBindings[];
    protected boolean hasExecuted = false;

    public ColumnFixture() { super(); }

    // Traversal ////////////////////////////////

    public void doRows(Parse rows) {
        bind(rows.parts);
        super.doRows(rows.more);
    }

    public void doRow(Parse row) {
        hasExecuted = false;
        try {
            reset();
        } catch (Exception e) {
            exception (row.leaf(), e);
        }
        super.doRow(row);
    }

    public void doCell(Parse cell, int column) {
        TypeAdapter a = columnBindings[column];
        try {
            String text = cell.text();
            if (text.equals("")) {
                check(cell, a);
            } else if (a == null) {
                ignore(cell);
            } else if (a.field != null) {
                a.set(a.parse(text));
            } else if (a.method != null) {
                check(cell, a);
            }
        } catch(Exception e) {
            exception(cell, e);
        }
    }

    public void check(Parse cell, TypeAdapter a) {
        if (!hasExecuted) {
            try {
                execute();
            } catch (Exception e) {
                exception (cell, e);
            }
            hasExecuted = true;
        }
        super.check(cell, a);
    }

    public void reset() throws Exception {
        // about to process first cell of row
    }

    public void execute() throws Exception {
        // about to process first method call of row
    }

    // Utility //////////////////////////////////

    protected void bind (Parse heads) {
        columnBindings = new TypeAdapter[heads.size()];
        for (int i=0; heads!=null; i++, heads=heads.more) {
            String name = heads.text();
            String suffix = "()";
            try {
                if (name.equals("")) {
                    columnBindings[i] = null;
                } else if (name.endsWith(suffix)) {
                    columnBindings[i] = bindMethod(name.substring(0,name.length()-suffix.length()));
                } else {
                    columnBindings[i] = bindField(name);
                }
            }
            catch (Exception e) {
                exception (heads, e);
            }
        }

    }

    protected TypeAdapter bindMethod (String name) throws Exception {
        TypeAdapter t = TypeAdapter.on(this, getTargetClass().getMethod(name, new Class[]{}));
        t.methodName = name;
        return t;
    }

    protected TypeAdapter bindField (String name) throws Exception {
        TypeAdapter t = TypeAdapter.on(this, getTargetClass().getField(name));
        t.fieldName = name;
        return t;
    }

    protected Class getTargetClass() {
        return getClass();
    }
}
