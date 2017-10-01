package org.meng.java.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Java agent with the MBeanServer in which MBeans are registed
 * @author meng_
 *
 */
public class CacheAgent {

	public static void main(String[] args) throws Exception {
		//Crate platform MBeanServer
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		//create name for MBeans
		ObjectName oname = new ObjectName("org.meng.java.jmx:type=Cache,name=CacheManagement");
		Cache mbean = new Cache();
		//Register MBeans in MBeanServer
		mbs.registerMBean(mbean, oname);
		System.out.println("waiting");
		Thread.sleep(Long.MAX_VALUE);
		
	}
}
