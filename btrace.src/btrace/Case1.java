package btrace;


import java.util.Random;

/**
 * 
 * 
 * @author yangwm Nov 16, 2010 9:59:28 PM
 */
public class Case1{
    
    public static void main(String[] args) throws Exception{
       Random random=new Random();
       CaseObject object=new CaseObject();
       boolean result=true;
       while(result){
          result=object.execute(random.nextInt(1000));
          Thread.sleep(1000);
       }
    }
  
}

