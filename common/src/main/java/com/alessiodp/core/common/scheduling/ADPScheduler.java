package com.alessiodp.core.common.scheduling;

import com.alessiodp.core.common.ADPPlugin;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ADPScheduler {
	protected final ADPPlugin plugin;
	protected final String poolName;
	
	private final ExecutorService pool;
	
	// Scheduler that calls executor
	private final ScheduledExecutorService scheduler;
	
	
	protected ADPScheduler(@NonNull ADPPlugin plugin) {
		this.plugin = plugin;
		poolName = plugin.getPluginFallbackName() + "-pool-";
		
		pool = Executors.newCachedThreadPool(
				new ThreadFactoryBuilder()
						.setNameFormat(poolName + "%d")
						.setDaemon(true)
						.setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
						.build()
		);
		scheduler = Executors.newSingleThreadScheduledExecutor(
				new ThreadFactoryBuilder()
						.setNameFormat(plugin.getPluginFallbackName() + "-scheduler")
						.setDaemon(true)
						.setUncaughtExceptionHandler((t, e) -> e.printStackTrace())
						.build()
		);
	}
	
	/**
	 * Get asynchronous executor
	 *
	 * @return the asynchronous executor
	 */
	public Executor getAsyncExecutor() {
		return pool;
	}
	
	/**
	 * Get synchronous executor
	 *
	 * @return the synchronous server executor
	 */
	public abstract Executor getSyncExecutor();
	
	/**
	 * Execute runnable asynchronously
	 *
	 * @param runnable the runnable to execute
	 * @return void
	 */
	public CompletableFuture<Void> runAsync(@NonNull Runnable runnable) {
		if (!Thread.currentThread().getName().startsWith(poolName)) {
			return CompletableFuture.runAsync(runnable, getAsyncExecutor())
					.exceptionally(ADPScheduler.exceptionally());
		}
		runnable.run();
		return CompletableFuture.completedFuture(null);
	}
	
	/**
	 * Execute supplier asynchronously and wait for response
	 * @param supplier the supplier to execute
	 * @param <T> the response type
	 * @return the response of the supplier
	 */
	public <T> CompletableFuture<T> runSupplyAsync(@NonNull Supplier<T> supplier) {
		return CompletableFuture.supplyAsync(supplier, getAsyncExecutor());
	}
	
	/**
	 * Schedule a runnable to be performed later
	 * @param runnable the runnable to execute
	 * @param delay the later delay
	 * @param unit the unit of delay
	 * @return a cancellable task
	 */
	public CancellableTask scheduleAsyncLater(@NonNull Runnable runnable, long delay, TimeUnit unit) {
		ScheduledFuture<?> future = this.scheduler.schedule(() -> runAsync(runnable), delay, unit);
		return () -> future.cancel(false);
	}
	
	/**
	 * Schedule a runnable to be performed repeatedly
	 * @param runnable the runnable to execute
	 * @param delay the later delay
	 * @param period the period between each execution
	 * @param unit the unit of delay
	 * @return a cancellable task
	 */
	public CancellableTask scheduleAsyncRepeating(@NonNull Runnable runnable, long delay, long period, TimeUnit unit) {
		ScheduledFuture<?> future = this.scheduler.scheduleAtFixedRate(() -> runAsync(runnable), delay, period, unit);
		return () -> future.cancel(false);
	}
	
	/**
	 * Shutdown the scheduler
	 */
	public void shutdown() {
		scheduler.shutdown();
		pool.shutdown();
		
		try {
			scheduler.awaitTermination(10, TimeUnit.SECONDS);
			pool.awaitTermination(10, TimeUnit.SECONDS);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			scheduler.shutdownNow();
			pool.shutdownNow();
		}
	}
	
	public static Function<Throwable, ? extends Void> exceptionally() {
		return ex -> {
			ex.printStackTrace();
			return null;
		};
	}
}