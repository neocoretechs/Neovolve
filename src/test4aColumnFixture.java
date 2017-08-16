import com.neocoretechs.neovolve.*;
import com.neocoretechs.neovolve.objects.*;
import com.neocoretechs.neovolve.fit.*;
import java.io.Serializable;
/**
 * The FIT column fixture processing test4a, the zero strip evolution test
 * http://www.neocoretechs.com/results.html
 * @author jg
 *
 */
public class test4aColumnFixture extends XGPFixture implements Serializable {
        public String toStrip;
        public String fromStrippee;

        public test4aColumnFixture() {
                super();
                GENERATIONS = 100;
                POPULATION = 15000;
        }

        public String Strip(Individual ind) {
                Object[] args = new Object[]{new Strings(toStrip), new Strings(fromStrippee)};
                Strings ret = (Strings)(ind.execute_object(0, args));
                //System.out.println("Res: "+ret);
                return ret.data;
        }
}
