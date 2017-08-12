package org.sam.java.framework.mockito;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;

public class MockitoDemoListTest {

	@Test
	public void testMockList() {
		//mock creation
		List mockedList	 = mock(List.class);
		//using mock obj
		mockedList.add("one");
		assertEquals("mockedList size should be 1", mockedList.size(), 1);
		assertEquals("first element should be one", mockedList.get(0), "one");
		mockedList.clear();
		assertEquals("mockedList size should be 0 after clear is invoked", mockedList.size(), 1);
		//verfication
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}
	
	@Test
	public void testMockListWithStub() {
		//mock creation
		List mockedList	 = mock(List.class);
		//stubbing
		when(mockedList.get(0)).thenReturn("one");
		String first = (String) mockedList.get(0);
		assertEquals("first element should be one", first, "one");
		// the following prints "null" because get(999) was not stubbed
		System.out.println(first);

		System.out.println(mockedList.get(999));//null
		//verfication
		verify(mockedList).get(0);
	}
}
