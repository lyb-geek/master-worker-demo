package com.masterworker.demo;

import java.util.Random;

public class Main {
	public static void main(String[] args) {
		System.out.println("当前cpu线程核数：" + Runtime.getRuntime().availableProcessors());
		Master master = new Master(new Worker(), Runtime.getRuntime().availableProcessors());

		for (int i = 0; i < 100; i++) {
			Task task = new Task();
			task.setId(Long.valueOf(i + ""));
			task.setTaskName("taskName" + i);
			task.setPrice(new Random().nextInt(1000));
			master.sumbit(task);
		}

		master.execute();

		long start = System.currentTimeMillis();
		while (true) {
			if (master.isComplete()) {
				long end = System.currentTimeMillis() - start;
				int result = master.getResult();
				System.out.println("最终执行结果：" + result + ";耗时：" + end);
				break;
			}
		}
	}

}
