package com.neocoretechs.neovolve.fit;
import com.neocoretechs.neovolve.*;
// Copyright (c) 2002 Cunningham & Cunningham, Inc.
// Released under the terms of the GNU General Public License version 2 or later.
/**
* @author Groff
*/
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.text.DateFormat;

public class Fixture implements Serializable {

    public Map summary = new HashMap();
    public Counts counts = new Counts();
    protected transient Vector allTypes = new Vector();
    protected transient Vector returnTypes = new Vector();
    protected transient Vector argTypes = new Vector();
    // should change for efficient binding on remote nodes
    protected transient boolean isBound = false;
    public int GENERATIONS = 10;
    public int POPULATION = 1000;

    public Fixture() {}

    public class Counts implements Serializable {
        public int right = 0;
        public int wrong = 0;
        public int ignores = 0;
        public int exceptions = 0;
        public Counts() {}
        public String toString() {
            return
                right + " right, " +
                wrong + " wrong, " +
                ignores + " ignored, " +
                exceptions + " exceptions";
        }

        public void tally(Counts source) {
            right += source.right;
            wrong += source.wrong;
            ignores += source.ignores;
            exceptions += source.exceptions;
        }
    }

    public class RunTime implements Serializable {
        long start = System.currentTimeMillis();
        long elapsed = 0;
        public RunTime() {}
        public String toString() {
            elapsed = (System.currentTimeMillis()-start);
            if (elapsed > 600000) {
                return d(3600000)+":"+d(600000)+d(60000)+":"+d(10000)+d(1000);
            } else {
                return d(60000)+":"+d(10000)+d(1000)+"."+d(100)+d(10);
            }
        }

        String d(long scale) {
            long report = elapsed / scale;
            elapsed -= report * scale;
            return Long.toString(report);
        }
    }



    // Traversal //////////////////////////

    public void doTables(Parse tables) {
        while (tables != null) {
            Parse heading = tables.at(0,0,0);
            if (heading != null) {
                Fixture fixture = null;
                try {
                    fixture = (Fixture)(Class.forName(heading.text()).newInstance());
                    fixture.doTable(tables);
                } catch (Exception e) {
                    exception (heading, e);
                }
            }
            tables = tables.more;
        }
    }

    public static void doTables(Parse tables, XGPWorld tworld) {
    }

    public void doTable(Parse table) {
        doRows(table.parts.more);
    }

    public void doRows(Parse rows) {
        while (rows != null) {
            Parse more = rows.more;
            doRow(rows);
            rows = more;
        }
    }

    public void doRow(Parse row) {
        doCells(row.parts);
    }

    public void doCells(Parse cells) {
        for (int i=0; cells != null; i++) {
            try {
                doCell(cells, i);
            } catch (Exception e) {
                exception(cells, e);
            }
            cells=cells.more;
        }
    }

    public void doCell(Parse cell, int columnNumber) {
        ignore(cell);
    }


    // Annotation ///////////////////////////////

    public void right (Parse cell) {
        cell.addToTag(" bgcolor=\"#cfffcf\"");
        counts.right++;
    }

    public void wrong (Parse cell) {
        cell.addToTag(" bgcolor=\"#ffcfcf\"");
        counts.wrong++;
    }

    public void wrong (Parse cell, String actual) {
        wrong(cell);
        cell.addToBody(label("expected") + "<hr>" + escape(actual) + label("actual"));
    }

    public void ignore (Parse cell) {
        cell.addToTag(" bgcolor=\"#efefef\"");
        counts.ignores++;
    }

    public void exception (Parse cell, Throwable exception) {
        while(exception.getClass().equals(InvocationTargetException.class)) {
            exception = ((InvocationTargetException)exception).getTargetException();
        }
        final StringWriter buf = new StringWriter();
        exception.printStackTrace(new PrintWriter(buf));
        cell.addToBody("<hr><font size=-2><pre>" + (buf.toString()) + "</pre></font>");
        cell.addToTag(" bgcolor=\"#ffffcf\"");
        counts.exceptions++;
    }

    // Utility //////////////////////////////////

    public String counts() {
        return counts.toString();
    }

    public static String label (String string) {
        return " <font size=-1 color=#c08080><i>" + string + "</i></font>";
    }

    public static String gray (String string) {
        return " <font color=#808080>" + string + "</font>";
    }

    public static String escape (String string) {
        return escape(escape(string, '&', "&amp;"), '<', "&lt;");
    }

    public static String escape (String string, char from, String to) {
        int i=-1;
        while ((i = string.indexOf(from, i+1)) >= 0) {
            if (i == 0) {
                string = to + string.substring(1);
            } else if (i == string.length()) {
                string = string.substring(0, i) + to;
            } else {
                string = string.substring(0, i) + to + string.substring(i+1);
            }
        }
        return string;
    }

    public static String camel (String name) {
        StringBuffer b = new StringBuffer(name.length());
        StringTokenizer t = new StringTokenizer(name);
        b.append(t.nextToken());
        while (t.hasMoreTokens()) {
            String token = t.nextToken();
            b.append(token.substring(0, 1).toUpperCase());      // replace spaces with camelCase
            b.append(token.substring(1));
        }
        return b.toString();
    }

    public Object parse (String s, Class type) throws Exception {
        if (type.equals(String.class))              {return s;}
        if (type.equals(Date.class))                {return DateFormat.getDateInstance().parse(s);}
        if (type.equals(ScientificDouble.class))    {return ScientificDouble.valueOf(s);}
        throw new Exception("can't yet parse "+type);
    }

    public void check(Parse cell, TypeAdapter a) {
        String text = cell.text();
        if (text.equals("")) {
            try {
                cell.addToBody(gray(a.toString(a.get())));
            } catch (Exception e) {
                cell.addToBody(gray("error"));
            }
        } else if (a == null) {
            ignore(cell);
        } else  if (text.equals("error")) {
            try {
                Object result = a.invoke();
                wrong(cell, a.toString(result));
            } catch (IllegalAccessException e) {
                exception (cell, e);
            } catch (Exception e) {
                right(cell);
            }
        } else {
            try {
                Object result = a.get();
                if (a.equals(a.parse(text), result)) {
                    right(cell);
                } else {
                    wrong(cell, a.toString(result));
                }
            } catch (Exception e) {
                exception(cell, e);
            }
        }
    }

}
