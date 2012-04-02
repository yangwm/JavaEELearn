package redis.benchmark;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class BaseTest implements Constants {

	public static void printResult(int length, int threads, int repeats,
			AtomicLong miss, AtomicLong fail, AtomicLong hit, long duration,
			long total) {
		DecimalFormat df = new DecimalFormat("######0.00");
		double hitRate = ((double) hit.get())
				/ (hit.get() + miss.get() + fail.get());
		System.out.println("time=" + new Date() + ",threads=" + threads + ",repeats=" + repeats
				+ ",valueLength=" + length + ",tps=" + total * 1000000000
				/ duration + ",miss=" + miss.get() + ",fail=" + fail.get()
				+ ",hit=" + hit.get() + ",all=" + total + ",hitRate="
				+ df.format(hitRate));
	}

	protected static int getReapts(int i) {
		// int t = (int) Math.log(THREADS[i]);
		// int repeats = BASE_REPEATS * (t <= 0 ? 1 : t);
		// return repeats;
		return BASE_REPEATS;
	}

}
