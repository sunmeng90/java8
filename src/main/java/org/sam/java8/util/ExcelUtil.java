package org.sam.java8.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelUtil {


    public static List<Map<String, String>> load2Map(final String src){
        List<Map<String, String>> rowMapList = new ArrayList();
        try {
            FileInputStream fis =  new FileInputStream(src);
            HSSFWorkbook book = new HSSFWorkbook(fis);
            HSSFSheet sheet = book.getSheet("emp_large");
            Row headerRow = sheet.getRow(0);
            List<String> headers = getHeader(headerRow);
            int totalRowNum = sheet.getLastRowNum();
            for (int i = 1; i < totalRowNum; i++) {
                Row valueRow = sheet.getRow(i);
                Map<String, String> rowMap = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    valueRow.getCell(j).setCellType(CellType.STRING);
                    rowMap.put(headers.get(j), valueRow.getCell(j).getStringCellValue());
                }
                rowMapList.add(rowMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowMapList;

    }

    private static List<String> getHeader(Row headerRow){
        List<String> headers = new ArrayList<>();
        for(Cell headerCol : headerRow){
            headers.add(headerCol.getStringCellValue());
        }
        return headers;
    }



    private static <T> T map2Obj(Map<String, String> map, Class<T> beanClass) {
        if (map == null)
            return null;
        T obj = null;
        try {
            obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> List<T> load2Obj(final String src, Class<T> clz){

        List<Map<String,String>> mapList = load2Map(src);
        List<T> objList = mapList.parallelStream().map((map) -> map2Obj(map, clz)).collect(Collectors.toList());
        return objList;
    }

    public static void main(String[] args) {
    }

    /**
     * public static <T> T convertInstanceOfObject(Object o, Class<T> clazz) {
     try {
     return clazz.cast(o);
     } catch(ClassCastException e) {
     return null;
     }
     }
     */

//    public static <T> T swap(T[] a, int m, int n) {
//        T temp = a[n];
//        a[n] = a[m];
//        a[m] = temp;
//        return temp;
//    }

    /**
     *
     * Once again, we have some code that works for type String, and we can imagine writing almost identical code to work with other types of objects. By writing a generic method, we get to write a single method definition that will work for objects of any type. We need to replace the specific type String in the definition of the method with the name of a type parameter, such as T. However, if that's the only change we make, the compiler will think that "T" is the name of an actual type, and it will mark it as an undeclared identifier. We need some way of telling the compiler that "T" is a type parameter. That's what the "<T>" does in the definition of the generic class "class Queue<T> { ...". For a generic method, the "<T>" goes just before the name of the return type of the method:

     public static <T> int countOccurrences(T[] list, T itemToCount) {
     int count = 0;
     if (itemToCount == null) {
     for ( T listItem : list )
     if (listItem == null)
     count++;
     }
     else {
     for ( T listItem : list )
     if (itemToCount.equals(listItem))
     count++;
     }
     return count;
     }
     */
}
