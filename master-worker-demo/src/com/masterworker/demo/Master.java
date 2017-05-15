package com.masterworker.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * <p>
 * Title:Master
 * </p>
 * <p>
 * Description: 用来分发任务和汇总worker线程计算结果
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author linyb
 * @date 2017年4月13日
 */
public class Master {
	// 选用无界非堵塞队列来存放任务
	private ConcurrentLinkedQueue<Task> tasksQueue = new ConcurrentLinkedQueue<>();
	// master需要知道有多少worker线程执行任务
	private HashMap<String, Thread> workers = new HashMap<>();
	// 用来存放计算结果
	private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

	public Master(Worker worker, int workerCount) {
		// worker线程必须知道当前有多少任务量
		worker.setTaskQueue(this.tasksQueue);
		worker.setResultMap(this.resultMap);
		for (int i = 0; i < workerCount; i++) {
			workers.put(Integer.toString(i), new Thread(worker));
		}
	}

	/**
	 * 任务提交
	 * 
	 * @param task
	 */
	public void sumbit(Task task) {
		tasksQueue.offer(task);
	}

	/**
	 * 任务开始计算
	 */
	public void execute() {
		for (Map.Entry<String, Thread> mEntry : workers.entrySet()) {
			Thread worker = mEntry.getValue();
			worker.start();
		}
	}

	/**
	 * 判断worker 线程是否全部执行完毕
	 * 
	 * @return
	 */
	public boolean isComplete() {
		for (Map.Entry<String, Thread> mEntry : workers.entrySet()) {
			Thread worker = mEntry.getValue();
			if (worker.getState() != Thread.State.TERMINATED) {
				return false;
			}
		}

		return true;
	}

	public int getResult() {
		int result = 0;
		for (Map.Entry<String, Object> mEntry : resultMap.entrySet()) {
			result += (int) mEntry.getValue();
		}

		return result;
	}
}
