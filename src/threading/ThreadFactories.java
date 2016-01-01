package threading;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadFactories {
	
	public static final ThreadFactory daemonThreadFactory = new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread thread = Executors.defaultThreadFactory().newThread(r);
			thread.setDaemon(true);
			return thread;
		}
	};
	
}
