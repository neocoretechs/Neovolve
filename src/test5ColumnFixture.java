import com.neocoretechs.neovolve.fit.*;
import com.neocoretechs.neovolve.*;
/**
 * The is the test5 FIT column fixture, add 2 integers
 * http://www.neocoretechs.com/results.html
 * @author jg
 *
 */
public class test5ColumnFixture extends XGPFixture {
        public int X;
        public int Y;
        
        public int Add(Individual ind) {
                Object[] args = new Object[]{new Integer(X), new Integer(Y)};
                int ret = ind.execute_int(0, args);
                //System.out.println("Res: "+ret);
                return ret;
        }
}
