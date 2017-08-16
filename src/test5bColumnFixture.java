import com.neocoretechs.neovolve.fit.*;
import com.neocoretechs.neovolve.*;
/**
 * The is the FIT column fixture for test5b, add 2 floating numbers
 * http://www.neocoretechs.com/results.html
 * @author jg
 *
 */
public class test5bColumnFixture extends XGPFixture {
        public float X;
        public float Y;
        
        public float Add(Individual ind) {
                Object[] args = new Object[]{new Float(X), new Float(Y)};
                float ret = ind.execute_float(0, args);
                //System.out.println("Res: "+ret);
                return ret;
        }
}
