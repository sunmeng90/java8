package org.meng.java.framework.mockito.customizedArgMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class FooTestServiceTest {
	@InjectMocks
	private FooTestService fooTestService;
	@Mock
	private FooDao fooDao;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testCreateEmpWithOriginalArg(){
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("Mockito");
		when(fooDao.insertEmp(foo)).thenReturn(1);
		int id = fooDao.insertEmp(foo);
		assertEquals(1, id);
		verify(fooDao).insertEmp(foo);
	}
	
	@Test
	public void testUpdateEmpWithCustomizedArgMatcher(){
		Foo fooModified = new Foo();
		fooModified.setId(1);
		fooModified.setName("Mockito_M");
		
		//when(fooDao.updateEmp(fooModified)).thenReturn(1);
		when(fooDao.updateEmp(argThat(new ArgumentMatcher<Foo>() {

			@Override
			public boolean matches(Object argument) {
				// TODO Auto-generated method stub
				return ((Foo)argument).getId() == 1;
			}
			
		}))).thenReturn(1);
		int id = fooTestService.updateEmp(fooModified);
		assertEquals(1, id);
		verify(fooDao, times(1)).updateEmp(argThat(new ArgumentMatcher<Foo>() {

			@Override
			public boolean matches(Object argument) {
				// TODO Auto-generated method stub
				return ((Foo)argument).getId() == 1;
			}
			
		}));
	}
	//http://blog.karthiksankar.com/mockito-2/
}


