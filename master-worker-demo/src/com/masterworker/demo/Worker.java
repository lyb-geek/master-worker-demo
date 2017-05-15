package com.masterworker.demo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * <p>
 * Title:Worker
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author linyb
 * @date 2017年4月13日
 */
public class Worker implements Runnable {
	private ConcurrentLinkedQueue<Task> tasksQueue;

	private ConcurrentHashMap<String, Object> resultMap;

	@Override
	public void run() {
		while (true) {
			Task input = tasksQueue.poll();
			if (input == null)
				break;
			Object output = handler(input);
			resultMap.put(Long.toString(input.getId()), output);
		}

	}

	private Object handler(Task input) {
		try {
			// 模拟计算执行时间
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object output = input.getPrice();
		return output;

	}

	public void setTaskQueue(ConcurrentLinkedQueue<Task> tasksQueue) {
		this.tasksQueue = tasksQueue;

	}

	public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
		this.resultMap = resultMap;

	}

}
