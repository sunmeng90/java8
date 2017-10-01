package org.meng.java.jmx;

public class Cache implements CacheMBean {

	private boolean empty = false;

	@Override
	public synchronized void clearCache() {
		System.out.println("Starting clearing the cache");
		try {
			for (int i = 0; i < 10; i++) {
				System.out.print(".");
				Thread.sleep(500);
			}
			System.out.println("");
			this.empty = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("finally");
		}

		System.out.println("All cache cleared");

	}

	@Override
	public boolean isEmpty() {
		return empty;
	}

}
