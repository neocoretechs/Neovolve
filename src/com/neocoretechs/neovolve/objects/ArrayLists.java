package com.neocoretechs.neovolve.objects;
import com.neocoretechs.neovolve.functions.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Title: ArrayLists
 * Description: ArrayLists data type
 * Copyright:    Copyright (c) 2003
 * Company: NeoCoreTechs
 * @author Groff 1/2003
 * @version 1.0
 */
public class ArrayLists implements Serializable, AddTo.Compatible, AddAt.Compatible,
                                              IndexOf.Compatible, Length.Compatible,
                                              Contains.Compatible, Remove.Compatible,
                                              IndexOfAt.Compatible, LastIndexOf.Compatible,
                                              Get.Compatible, Set.Compatible,
                                              Clear.Compatible, IsEmpty.Compatible,
                                              Sort.Compatible,
                                              ContainedVariable.ContainerAccess {

        public ArrayList data;
        public static Class arrayListClass = ArrayLists.class;
        public transient int currentIndex = 0; // for ContainedVariable access

        public ArrayLists() {
                this.data = new ArrayList();
        }

        public Object execute_addTo(Object obj) {
                ArrayLists al = new ArrayLists();
                for(int i = 0; i < data.size(); i++)
                        al.data.add(data.get(i));
                al.data.add(obj);
                return al;
	}

        public Object execute_addAt(Object obj, int pos) {
                ArrayLists al = new ArrayLists();
                for(int i = 0; i < data.size(); i++)
                        al.data.add(data.get(i));
                al.data.add(pos, obj);
                return al;
	}

        public int execute_indexOf(Object obj) {
                return data.indexOf(obj);
        }

        public int execute_lastIndexOf(Object obj) {
                return data.lastIndexOf(obj);
        }

        public int execute_indexOfAt(Object obj, int strt) {
                for(int j = strt; j < data.size(); j++)
                        if( data.get(j).equals(obj) )
                                return j;
                return -1;
        }

        public int execute_length() {
                return data.size();
        }
        /**
        * Equals function uses this, so we override to give meaning here
        */
        public boolean equals(Object obj) {
                return data.equals(((ArrayLists)obj).data);
        }

        public boolean execute_contains(Object obj) {
                return data.contains(obj);
        }

        public boolean execute_isEmpty() {
                return data.isEmpty();
        }

        public Object execute_clear() {
                return new ArrayLists();
        }

        public Object execute_remove(int pos) {
                ArrayLists al = new ArrayLists();
                for(int i = 0; i < data.size(); i++)
                        al.data.add(data.get(i));
                al.data.remove(pos);
                return al;
        }
        /**
        * @return Object new list
        */
        public Object execute_set(int pos, Object obj) {
                ArrayLists al = new ArrayLists();
                for(int i = 0; i < data.size(); i++)
                        al.data.add(data.get(i));
                al.data.set(pos, obj);
                return al;
        }

        public Object execute_sort() {
                Object[] a = data.toArray();
                Arrays.sort(a);
                ArrayLists al = new ArrayLists();
                for(int i = 0; i < a.length; i++)
                        al.data.add(a[i]);
                return al;
        }
        /**
        * @return Object at location
        */
        public Object execute_get(int pos) {
                currentIndex = pos;
                return data.get(pos);
        }

        /**
        * Implementation of ContainerAccess.  This is used to present
        * the container contents as a variable, dependent on the current
        * index of this container
        */
        public Object getCurrent() {
                return data.get(currentIndex);
        }

}
