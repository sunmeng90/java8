package org.meng.java8.util;

import org.junit.jupiter.api.Test;

import java.util.List;

public class ExcelUtilTest {

    @Test
    public void testLoad2Obj(){
        final String src = ".\\src\\main\\resources\\data\\emp_large.xls";

        List<Emp> empList = ExcelUtil.load2Obj(src, Emp.class);
        empList.stream().forEach(emp -> System.out.println(emp.getEmp_no()));

    }



}
