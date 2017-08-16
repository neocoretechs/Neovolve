/**
 * This test exercises some S-code such as that produced by the Neovolve processes
 * @author jg
 *
 */
public class test2 {
        public static void main(String argv[]) {
                try {
                String S0 = argv[0];
                int LENS0 = argv[0].length();
                System.out.println(
                   (S0+S0).substring(
                        ((S0+S0).substring(
                                (1+(S0.indexOf((S0+S0+S0).substring(1, ((S0+S0).substring(1,LENS0)).length() )))), LENS0) ).length(),
                        LENS0 ) );
                } catch(Exception e) { System.out.println(e); e.printStackTrace(); }
                System.exit(0);
       }
}
