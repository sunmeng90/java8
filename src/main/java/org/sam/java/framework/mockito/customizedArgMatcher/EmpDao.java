package org.sam.java.framework.mockito.customizedArgMatcher;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class EmpDao {

	public int insertEmp(Emp emp){
		System.out.println("create emp with: "+ ToStringBuilder.reflectionToString(emp));
		return emp.getId();
	}
	public int updateEmp(Emp emp){
		System.out.println("update emp with: "+ ToStringBuilder.reflectionToString(emp));
		return emp.getId();
	}
}
