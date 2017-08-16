import com.neocoretechs.neovolve.fit.*;
import com.neocoretechs.neovolve.*;
/**
 * The is the FIT column fixture for test5a, add 3 numbers
 * http://www.neocoretechs.com/results.html
 * @author jg
 *
 */
public class test5aColumnFixture extends XGPFixture {
        public int X;
        public int Y;
        public int Z;
        
        public int Add(Individual ind) {
                Object[] args = new Object[]{new Integer(X), new Integer(Y), new Integer(Z)};
                int ret = ind.execute_int(0, args);
                //System.out.println("Res: "+ret);
                return ret;
        }
}
