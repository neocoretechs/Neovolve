import com.neocoretechs.powerspaces.*;
/**
 * This test invokes the 'CreateWorld' operation in the cluster
 * @author jg
 *
 */
public class volvit
{
        public static void main(String[] argv) {
                try  {
                        PowerSpace PS = new PowerSpace("localhost",8202);
                        PKRemote pkr = PS.getRemote("com.neocoretechs.neovolve.NeovolveKernel");
                        //Object o = pkr.invoke("CreateWorld",new Integer(100));
                        Object o = pkr.invoke("CreateWorld", argv[0], argv[1]);
                        System.out.println(o);
                } catch(Exception e) { System.out.println(e.getMessage()); e.printStackTrace(); }
        }
}
