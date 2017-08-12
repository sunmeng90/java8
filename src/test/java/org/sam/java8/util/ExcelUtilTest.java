package org.sam.java8.util;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;

public class ExcelUtilTest {

    @Test
    public void testLoad2Obj(){
        final String src = "C:\\Users\\meng_\\Documents\\GitHub\\java8\\src\\main\\resources\\data\\emp_large.xls";

        List<Emp> empList = ExcelUtil.load2Obj(src, Emp.class);
        empList.stream().forEach(emp -> System.out.println(emp.getEmp_no()));

    }



}
