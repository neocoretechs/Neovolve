package com.neocoretechs.neovolve.objects;
import com.neocoretechs.neovolve.functions.*;
import java.io.*;
/**
 * Title: Strings
 * Description: Strings data type
 * Copyright:    Copyright (c) 2003
 * Company: NeoCoreTechs
 * @author Groff 1/2003
 * @version 1.0
 */
public class Strings implements Comparable, Serializable,
                                              Add.Compatible, Substring.Compatible,
                                              IndexOf.Compatible, Length.Compatible,
                                              LessThan.Compatible, GreaterThan.Compatible,
                                              Contains.Compatible, Replace.Compatible,
                                              IndexOfAt.Compatible, LastIndexOf.Compatible,
                                              StartsWith.Compatible, EndsWith.Compatible {

        public String data;
        public static Class stringClass = Strings.class;

        public Strings(String data) {
		this.data = data;
        }

	public Object execute_add(Object obj) {
                String s = ((Strings)obj).data;
                return new Strings(data + s);
	}

        public Object execute_substring(int start, int end) {
                return new Strings(data.substring(start, end));
        }

        //public Object execute_indexOf(Object obj) {
        //        return new Integer(data.indexOf(((Strings)obj).data));
        //}

        public int execute_indexOf(Object obj) {
                return data.indexOf(((Strings)obj).data);
        }

        public int execute_lastIndexOf(Object obj) {
                return data.lastIndexOf(((Strings)obj).data);
        }

        public int execute_indexOfAt(Object obj, int strt) {
                return data.indexOf(((Strings)obj).data, strt);
        }

        public int execute_length() {
                return data.length();
        }
        /**
        * Equals function uses this, so we override to give meaning here
        */
        public boolean equals(Object obj) {
                return data.equals(((Strings)obj).data);
        }

        public int compareTo(Object obj) {
                return data.compareTo(((Strings)obj).data);
        }

        public boolean less_than(Object obj) {
                return (data.compareTo(((Strings)obj).data) < 0);
        }

        public boolean greater_than(Object obj) {
                return (data.compareTo(((Strings)obj).data) > 0);
        }

        public boolean execute_contains(Object obj) {
                return ( data.indexOf(((Strings)obj).data) > -1 );
        }

        public boolean execute_startsWith(Object obj) {
                return data.startsWith(((Strings)obj).data);
        }

        public boolean execute_endsWith(Object obj) {
                return data.endsWith(((Strings)obj).data);
        }

        public Object execute_replace(Object replThis, Object withThat) {
                return new Strings( data.replace(
                        ((Strings)replThis).data.charAt(0),
                        ((Strings)withThat).data.charAt(0)) );
        }
}
