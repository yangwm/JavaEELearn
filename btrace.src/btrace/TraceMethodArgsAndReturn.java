package btrace;


import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

/**
 * 
 * @author yangwm Nov 16, 2010 9:24:14 PM
 */
@BTrace public class TraceMethodArgsAndReturn{
    @OnMethod(
       clazz="CaseObject",
       method="execute",
       location=@Location(Kind.RETURN)
    )
    public static void traceExecute(@Self CaseObject instance,int sleepTime,@Return boolean result){
      println("call CaseObject.execute");
      println(strcat("sleepTime is:",str(sleepTime)));
      println(strcat("sleepTotalTime is:",str(get(field("CaseObject","sleepTotalTime"),instance))));
      println(strcat("return value is:",str(result)));
    }
}
