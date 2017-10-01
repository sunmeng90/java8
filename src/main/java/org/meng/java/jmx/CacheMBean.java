package org.meng.java.jmx;

public interface CacheMBean {
	/**
	 * clear all cache in memory
	 */
	public void clearCache();

	/**
	 * check the cache status
	 * 
	 * @return
	 */
	public boolean isEmpty();

}
