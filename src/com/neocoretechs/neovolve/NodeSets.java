package com.neocoretechs.neovolve;
import com.neocoretechs.neovolve.functions.*;
import com.neocoretechs.neovolve.objects.*;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;

public class NodeSets {
        static Hashtable mappedTypes = new Hashtable();

        public NodeSets() {
                mappedTypes.clear();
        }

        public static Function[][] getNodeSets(Vector v) {
                // always set up boolean, theres no logic without it jim
                putClassIfNotExisting(Function.booleanClass, getBoolean());
                Enumeration e = v.elements();
                while(e.hasMoreElements() ) {
                        Class targetClass = (Class)(e.nextElement());
                        if( mappedTypes.get(targetClass) == null ) {
                                if( targetClass.equals(Strings.stringClass) ) {
                                        putClassIfNotExisting(Function.integerClass, getNumber(Function.integerClass));
                                        putClassIfNotExisting(targetClass, getString());
                                } else {
                                        if( targetClass.equals(Function.integerClass) ||
                                            targetClass.equals(Function.longClass) ||
                                            targetClass.equals(Function.floatClass) ||
                                            targetClass.equals(Function.doubleClass) ) {
                                                putClassIfNotExisting(targetClass, getNumber(targetClass));
                                        } else {
                                                if( targetClass.equals(ArrayLists.arrayListClass) ) {
                                                        putClassIfNotExisting(targetClass, getArrayList());
                                                }
                                        }
                                }
                        }
                }
                Enumeration ens = mappedTypes.elements();
                int ilen = 0;
                while(ens.hasMoreElements())
                        ilen += ((Function[])(ens.nextElement())).length;
                Function[][] retFuncs = new Function[1][ilen];
                ens = mappedTypes.elements();
                ilen = 0;
                while(ens.hasMoreElements()) {
                        Function[] funcElem = (Function[])(ens.nextElement());
                        System.arraycopy(funcElem, 0, retFuncs[0], ilen, funcElem.length);
                        ilen += funcElem.length;
                }
                return retFuncs;                        
        }

        public static void putClassIfNotExisting(Class mapClass, Function[] nodeSets) {
                if( mappedTypes.get(mapClass) == null )
                        mappedTypes.put(mapClass, nodeSets);
        }
        /**
        * Class booleanClass = Boolean.class;
        */
        public static Function[] getBoolean() {
                return new Function[]{
                        new And(),
                        new Or(),
                        new Not(),
                        new Nop(),
                        new Loop(),
                        new True(),
                        new False(),
                        new TwoThings() };
        }
        /**
        * Class integerClass = Integer.class;
        * Class longClass = Long.class;
        * Class floatClass = Float.class;
        * Class doubleClass = Double.class;
        */
        public static Function[] getNumber(Class ofType) {
                return new Function[]{
                        new If(ofType),
                        new Add(ofType),
                        new Subtract(ofType),
                        new Multiply(ofType),
                        new Divide(ofType),
                        new One(ofType),
                        new GreaterThan(ofType),
                        new LessThan(ofType),
                        new Equals(ofType) };
       }

       public static Function[] getString() {
                return new Function[]{
                        new If(Strings.stringClass),
                        new Add(Strings.stringClass),
                        new GreaterThan(Strings.stringClass),
                        new LessThan(Strings.stringClass),
                        new Equals(Strings.stringClass),
                        new Substring(),
                        new IndexOf(Strings.stringClass, Strings.stringClass),
                        new IndexOfAt(Strings.stringClass, Strings.stringClass),
                        new LastIndexOf(Strings.stringClass, Strings.stringClass),
                        new Length(Strings.stringClass),
                        new Contains(Strings.stringClass, Strings.stringClass),
                        //new ValueOf(Function.integerClass),
                        new Replace(Strings.stringClass, Strings.stringClass, Strings.stringClass),
                        new StartsWith(Strings.stringClass, Strings.stringClass),
                        new EndsWith(Strings.stringClass, Strings.stringClass) };
        }
        public static Function[] getArrayList() {
                return new Function[]{
                        // ArrayList
                        new Length(ArrayLists.arrayListClass),
                        new Clear(ArrayLists.arrayListClass),
                        new IsEmpty(ArrayLists.arrayListClass),
                        new If(ArrayLists.arrayListClass),
                        new Remove(ArrayLists.arrayListClass),
                        new Sort(ArrayLists.arrayListClass)  };
       }
        public static Function[] getArrayList(Class ofType) {
                return new Function[]{
                        // ArrayList
                        new IndexOf(ArrayLists.arrayListClass, ofType),
                        new IndexOfAt(ArrayLists.arrayListClass, ofType),
                        new LastIndexOf(ArrayLists.arrayListClass, ofType),
                        new Contains(ArrayLists.arrayListClass, ofType),
                        new AddTo(ArrayLists.arrayListClass, ofType),
                        new AddAt(ArrayLists.arrayListClass, ofType),
                        new Get(ArrayLists.arrayListClass, ofType),
                        new Set(ArrayLists.arrayListClass, ofType) };
       }
}
