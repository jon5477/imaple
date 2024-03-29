/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.util;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Sactual
 */
public class TimerManager implements TimerManagerMBean {
	private static TimerManager instance = new TimerManager();
	private ScheduledThreadPoolExecutor ses;

	private TimerManager() {
	}

	public static TimerManager getInstance() {
		return instance;
	}

	public void start() {
		if (ses != null && !ses.isShutdown() && !ses.isTerminated()) {
			return; //starting the same timermanager twice is no - op
		}
		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(4, new ThreadFactory() {
			private final AtomicInteger threadNumber = new AtomicInteger(1);
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("Timermanager-Worker-" + threadNumber.getAndIncrement());
				return t;
			}
		});
		stpe.setMaximumPoolSize(4);
		stpe.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
		ses = stpe;
	}

	public void stop() {
		ses.shutdown();
	}

	public ScheduledFuture<?> register(Runnable r, long repeatTime, long delay) {
		return ses.scheduleAtFixedRate(new LoggingSaveRunnable(r), delay, repeatTime, TimeUnit.MILLISECONDS);
	}

	public ScheduledFuture<?> register(Runnable r, long repeatTime) {
		return ses.scheduleAtFixedRate(new LoggingSaveRunnable(r), 0, repeatTime, TimeUnit.MILLISECONDS);
	}

	public ScheduledFuture<?> schedule(Runnable r, long delay) {
		return ses.schedule(new LoggingSaveRunnable(r), delay, TimeUnit.MILLISECONDS);
	}

	public ScheduledFuture<?> scheduleAtTimestamp(Runnable r, long timestamp) {
		return schedule(r, timestamp - System.currentTimeMillis());
	}

	@Override
	public long getActiveCount() {
		return ses.getActiveCount();
	}

	@Override
	public long getCompletedTaskCount() {
		return ses.getCompletedTaskCount();
	}

	@Override
	public int getQueuedTasks() {
		return ses.getQueue().toArray().length;
	}

	@Override
	public long getTaskCount() {
		return ses.getTaskCount();
	}

	@Override
	public boolean isShutdown() {
		return ses.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return ses.isTerminated();
	}

	private static class LoggingSaveRunnable implements Runnable {
		Runnable r;

		public LoggingSaveRunnable(Runnable r) {
			this.r = r;
		}

		@Override
		public void run() {
			try {
				r.run();
			} catch (Throwable t) {
			}
		}
	}
}