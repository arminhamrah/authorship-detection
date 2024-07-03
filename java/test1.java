
import acm.program.*;

public class test1 extends ConsoleProgram
{

    public void run()
    {
        String s = "a\".length() + \"b";
        for (int i = 0; i < s.length(); i++){
            System.out.println(s.substring(i));
        }
    }


}
