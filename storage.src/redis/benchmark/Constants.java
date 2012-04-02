package redis.benchmark;

public interface Constants {

	final int[] THREADS = {
			1, 
			//10, 
			20,
			//50, 
			100,
			200,
			300 
			};
	final int[] BYTES = {
	        16,
			64, 
			//128, 512, 1024, 4096
			//,16 * 1024, 
			};
	public static final double WRITE_RATE = 0.05;
	final int BASE_REPEATS = 100 * 1000;
	final long OP_TIMEOUT = 5000;

}
