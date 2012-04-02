package btrace;


/**
 * 
 * 
 * @author yangwm Nov 16, 2010 9:59:01 PM
 */
public class CaseObject{
    
    private static int sleepTotalTime=0;  
  
    public boolean execute(int sleepTime) throws Exception{
        System.out.println("sleep: "+sleepTime);
        sleepTotalTime+=sleepTime;
        Thread.sleep(sleepTime);
        return true;
    }
  
}
