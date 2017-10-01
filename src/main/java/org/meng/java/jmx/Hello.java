package org.meng.java.jmx;

public class Hello implements HelloMBean {

	private String message;

	@Override
	public void sayHello() {
		System.out.println("hello, world");

	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public synchronized void setMessage(String message) {
		this.message = message;
	}

}
