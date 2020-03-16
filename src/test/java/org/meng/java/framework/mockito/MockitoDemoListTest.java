package org.meng.java.framework.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MockitoDemoListTest {

    @Test
    public void testMockList() {
        //mock creation
        List mockedList = mock(List.class);
        //using mock obj
        mockedList.add("one");
        mockedList.clear();
        //verfication
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void testMockListWithStub() {
        //mock creation
        List mockedList = mock(List.class);
        //stubbing
        when(mockedList.get(0)).thenReturn("one");
        String first = (String) mockedList.get(0);
        assertEquals("one", first, "first element should be one");
        // the following prints "null" because get(999) was not stubbed
        System.out.println(first);

        System.out.println(mockedList.get(999));//null
        //verification
        verify(mockedList).get(0);
    }
}
