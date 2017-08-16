package com.neocoretechs.neovolve.fit;
import com.neocoretechs.neovolve.*;
import com.neocoretechs.neovolve.objects.*;
import java.lang.reflect.*;
import java.io.Serializable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Date;
/**
* @author Groff
*/
public class XGPFixture extends ColumnFixture implements Serializable {

    public XGPWorld world;
    private transient Individual currentIndividual = null;

    public XGPFixture() { super(); }

    public XGPFixture(XGPWorld tworld) {
        super();
        world = tworld;        
    }
    public void setIndividual(Individual ind) { currentIndividual = ind; }
    /**
    * Overload bind to set up Neovolve types to use, and create the world
    */
    protected void bind (Parse heads) {
        if( isBound )
                return;
        else
                isBound = true;
        super.bind(heads);
        for (int i=0; i < columnBindings.length; i++) {
            Class targetType = translateNeovolve(columnBindings[i].type);
            if( !allTypes.contains(targetType) )
                allTypes.addElement(targetType);
            // dont check for same arg type already there, we add same number args
            if( columnBindings[i].field != null )
                argTypes.addElement(targetType);
            else {
                if( columnBindings[i].method != null ) {
                        targetType = translateNeovolve(columnBindings[i].method.getReturnType());
                        if( !returnTypes.contains(targetType) )
                                returnTypes.addElement(targetType);
                }
            }
        }
        Enumeration e = allTypes.elements();
        System.out.println("All types:");
        while(e.hasMoreElements()) System.out.println(e.nextElement());
        e = returnTypes.elements();
        System.out.println("Return types:");
        while(e.hasMoreElements()) System.out.println(e.nextElement());
        e = argTypes.elements();
        System.out.println("Arg types:");
        while(e.hasMoreElements()) System.out.println(e.nextElement());

    }

    public Class translateNeovolve(Class targetType) {
            if( targetType.equals(String.class) || targetType.equals(Character.class) )
                targetType = Strings.stringClass;
            else
                if( targetType.equals(int.class) )
                        targetType = Function.integerClass;
                  else
                        if( targetType.equals(long.class) )
                                targetType = Function.longClass;
                           else
                                if( targetType.equals(float.class) )
                                        targetType = Function.floatClass;
                                  else
                                        if( targetType.equals(double.class) )
                                                targetType = Function.doubleClass;
                                          else
                                                if( targetType.equals(boolean.class) )
                                                        targetType = Function.booleanClass;
                                                  else
                                                        if( targetType.equals(void.class) )
                                                                targetType = Function.voidClass;
                                                          else
                                                                if( targetType.equals(ArrayList.class) )
                                                                        targetType = ArrayLists.arrayListClass;
        return targetType;
    }
    /**
    * This is overridden to set the fixture in the world, create and run the world
    * remember, we have do do a doTable to bind first
    */
    public static void doTables(Parse tables, XGPWorld tworld) {
        Parse heading = tables.at(0,0,0);
        if (heading != null) {
                try {
                    Fixture fixture = (Fixture)(Class.forName(heading.text()).newInstance());
                    tworld.fixture = fixture;
                    ((XGPFixture)fixture).world = tworld;
                    tworld.BREAKBEST = true; // stop when we get fit solution
                    fixture.doTable(tables); // bind types
                    tworld.create(fixture.POPULATION, fixture.returnTypes, fixture.argTypes, fixture.allTypes);
                    tworld.run(fixture.GENERATIONS);
                } catch (Exception e) {
                    tworld.exception(e);
                }
        }
    }

    /**
    * We override this to allow us to pass 1 arg to method, the evolving Individual
    */
    protected TypeAdapter bindMethod (String name) throws Exception {
        TypeAdapter t = TypeAdapter.on(this, getTargetClass().getMethod(name, new Class[]{Individual.class}));
        t.methodName = name;
        return t;
    }
    /**
    * We want a final check with cells filled properly
    */
    public void checkFinal(Parse cell, TypeAdapter a) {
        String text = cell.text();
        Object[] params = new Object[]{currentIndividual};
        if (text.equals("")) {
            try {
                cell.addToBody(gray(a.toString(a.get(params))));
            } catch (Exception e) {
                cell.addToBody(gray("error"));
            }
        } else if (a == null) {
            ignore(cell);
        } else  if (text.equals("error")) {
            try {
                Object result = a.invoke(params);
                wrong(cell, a.toString(result));
            } catch (IllegalAccessException e) {
                exception (cell, e);
            } catch (Exception e) {
                right(cell);
            }
        } else {
            try {
                Object result = a.get(params);
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
    /**
    * We want regular check to not fill cells, just tally
    * this is because each individual runs all tests, thats thousands or more
    * we overload this to accomplish this and filling our arg
    * to test mothods with Individual
    *
    */
    public void check(Parse cell, TypeAdapter a) {
        // this little hack is due to fit needing to do a complete doTable
        // before binding and world creation, after that it gets individuals
        if( currentIndividual == null )
                return;
        // and this to set final values for each generation
        if( world.DEBUG ) {
                checkFinal(cell, a);
                return;
        }
        String text = cell.text();
        Object[] params = new Object[]{currentIndividual};
        if (text.equals("")) {
            try {
                a.get(params);
            } catch (Exception e) {
                ++counts.exceptions;
            }
        } else if (a == null) {
            ++counts.ignores;
        } else  if (text.equals("error")) {
            try {
                // had to modify typeadapter
                a.invoke(params);
                ++counts.wrong;
            } catch (IllegalAccessException e) {
                ++counts.exceptions;
            } catch (Exception e) {
                ++counts.right;
            }
        } else {
            try {
                Object result = a.get(params);
                if (a.equals(a.parse(text), result)) {
                    ++counts.right;
                } else {
                    ++counts.wrong;
                }
            } catch (Exception e) {
                ++counts.exceptions;
            }
        }
    }
}
