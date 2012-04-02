package race;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Stat util
 * @author tim
 *
 */
public class TestUtil {
	private static Log log = LogFactory.getLog(TestUtil.class);
	
	public static String getElapseTimeStat(List<Integer> elapseTime) {
		int n = elapseTime.size();
		if (n == 0) return "";
		int t1 = 0, t2 = 0, t3 = 0, t4 = 0, t5 = 0, t6 = 0, t7 = 0, t8 = 0, t9 = 0, t10 = 0;
		for (int j = 0 ; j < n; j++) {
			int t = elapseTime.get(j);
			if (t == 0) t1++;
			else if (t < 3) t2++;
			else if (t < 5) t3++;
			else if (t < 10) t4++;
			else if (t < 50) t5++;
			else if (t < 100) t6++;
			else if (t < 500) t7++;
			else if (t < 1000) t8++;
			else if (t < 3000) t9++;
			else t10++;
		}
		double v1 = t1 * 1000l / n / 10.0;
		double v2 = t2 * 1000l / n / 10.0;
		double v3 = t3 * 1000l / n / 10.0;
		double v4 = t4 * 1000l / n / 10.0;
		double v5 = t5 * 1000l / n / 10.0;
		double v6 = t6 * 1000l / n / 10.0;
		double v7 = t7 * 1000l / n / 10.0;
		double v8 = t8 * 1000l / n / 10.0;
		double v9 = t9 * 1000l / n / 10.0;
		double v10 = t10 * 1000l / n / 10.0;

		int j = t1;
		double u1 = j * 1000l / n / 10.0; j += t2;
		double u2 = j * 1000l / n / 10.0; j += t3;
		double u3 = j * 1000l / n / 10.0; j += t4;
		double u4 = j * 1000l / n / 10.0; j += t5;
		double u5 = j * 1000l / n / 10.0; j += t6;
		double u6 = j * 1000l / n / 10.0; j += t7;
		double u7 = j * 1000l / n / 10.0; j += t8;
		double u8 = j * 1000l / n / 10.0; j += t9;
		double u9 = j * 1000l / n / 10.0; j += t10;
		double u10 = j * 1000l / n / 10.0;		

		StringBuilder sb = new StringBuilder();
		sb.append("<    1ms ").append(u1).append("% ").append(v1).append("% (").append(t1).append(")\n");
		if (u1 < 99.99) sb.append("<    3ms ").append(u2).append("% ").append(v2).append("% (").append(t2).append(")\n");
		if (u2 < 99.99) sb.append("<    5ms ").append(u3).append("% ").append(v3).append("% (").append(t3).append(")\n");
		if (u3 < 99.99) sb.append("<   10ms ").append(u4).append("% ").append(v4).append("% (").append(t4).append(")\n");
		if (u4 < 99.99) sb.append("<   50ms ").append(u5).append("% ").append(v5).append("% (").append(t5).append(")\n");
		if (u5 < 99.99) sb.append("<  100ms ").append(u6).append("% ").append(v6).append("% (").append(t6).append(")\n");
		if (u6 < 99.99) sb.append("<  500ms ").append(u7).append("% ").append(v7).append("% (").append(t7).append(")\n");
		if (u7 < 99.99) sb.append("< 1000ms ").append(u8).append("% ").append(v8).append("% (").append(t8).append(")\n");
		if (u8 < 99.99) sb.append("< 3000ms ").append(u9).append("% ").append(v9).append("% (").append(t9).append(")\n");
		if (t10 > 0) sb.append("> 3000ms ").append(u10).append("% ").append(v10).append("% (").append(t10).append(")\n");
		return sb.toString();
	}

	static DecimalFormat secFormat = new DecimalFormat("###,###,###,###");
	public static void printStat(AtomicInteger count, AtomicInteger errorCount, List<Integer> elapseTime) {
		long time1 = System.currentTimeMillis();
		int lastCount = 0;
		long lastTime = 0;
		long max = 0;
		int loopCount = 0;
		while (true) {
			long time2 = System.currentTimeMillis();
			if (time2 == 0)
				break;
			if (time2 == time1)
				continue;
			int cnt = count.get();
			int errCount = errorCount.get();
			StringBuilder sb = new StringBuilder();
			
			long uptime = (time2 - time1) / 1000;
			sb.append("\n");
			sb.append("OK /ERR: ").append(cnt).append("/").append(errCount);
			
			sb.append(" (Uptime: ").append(secFormat.format(uptime)).append(", ")
				.append(secFormat.format(uptime / 60)).append(")\n");

			long avg = cnt * 1000l / (time2 - time1);
			long cur = (cnt - lastCount) * 1000l / (time2 - lastTime);
			if (cur > max)  max = cur;
			
			sb.append("AVG/CUR/MAX RPS: ");
			sb.append(avg);
			sb.append("/");
			sb.append(cur);
			sb.append("/");
			sb.append(max).append("\n");
			
			lastTime = time2;
			lastCount = cnt;
			
			if (elapseTime != null && loopCount++ % 10 == 0) {
				sb.append(TestUtil.getElapseTimeStat(elapseTime));
				sb.append("HEAP: ");
				sb.append(memoryReport());
				sb.append("\n");
			}
			
			log.info(sb.toString());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String memoryReport() {
		Runtime runtime = Runtime.getRuntime();

		double freeMemory = (double) runtime.freeMemory() / (1024 * 1024);
		double maxMemory = (double) runtime.maxMemory() / (1024 * 1024);
		double totalMemory = (double) runtime.totalMemory() / (1024 * 1024);
		double usedMemory = totalMemory - freeMemory;
		double percentFree = ((maxMemory - usedMemory) / maxMemory) * 100.0;
		double percentUsed = 100 - percentFree;
		// int percent = 100 - (int) Math.round(percentFree);

		DecimalFormat mbFormat = new DecimalFormat("#0.00");
        DecimalFormat percentFormat = new DecimalFormat("#0.0");
		
        StringBuilder sb = new StringBuilder(" ");
		sb.append(mbFormat.format(usedMemory)).append("MB of ").append(mbFormat.format(maxMemory))
		.append(" MB (").append(percentFormat.format(percentUsed)).append("%) used");
		return sb.toString();
	}

	// private static String value100 = "asdfasdfxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsaaaaaaaaaaaaaaadfsasdfasdfxxxxxxxxxxxxxxxxxxxxxxxxxxxxxasdwdd";
	private static String value32 = "asdfasdfxxxxxxxxxxxxxxxxxxxxxxxx";
	
	private static String value128 = "db-4.7.25.tar.gzlibevent-1.4.11-stable.tar.gzmemcached-1.2.8.tar.gzmemcachedb-1.2.1-beta.tar.gzPython-2.6.2.tgzredis-0.900_2.tar";
	
	/**
	 * return random str in size
	 * @param len, if len == 0, return 100 size str
	 * @return
	 */
	public static String getStr(int len) {
		if (len == 0)
			return value32;
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= len / 128; i++) {
			sb.append(value128);
		}
		return sb.substring(0, len);
	}
	
	public static void main(String[] args) {
		DecimalFormat mbFormat = new DecimalFormat("###,###,###");
		System.out.println(mbFormat.format(1234567));
		System.out.println(mbFormat.format(12));
	}
}
