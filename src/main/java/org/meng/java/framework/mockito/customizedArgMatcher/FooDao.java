package org.meng.java.framework.mockito.customizedArgMatcher;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FooDao {

	public int insertFoo(Foo foo){
		System.out.println("create foo with: "+ ToStringBuilder.reflectionToString(foo));
		return foo.getId();
	}
	public int updateFoo(Foo foo){
		System.out.println("update foo with: "+ ToStringBuilder.reflectionToString(foo));
		return foo.getId();
	}
}
