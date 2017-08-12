package org.sam.java.framework.mockito.customizedArgMatcher;

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
public class EmpServiceTest {
	@InjectMocks
	private EmpService empService;
	@Mock
	private EmpDao empDao;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testCreateEmpWithOriginalArg(){
		Emp emp = new Emp();
		emp.setId(1);
		emp.setName("Mockito");
		when(empDao.insertEmp(emp)).thenReturn(1);
		int id = empDao.insertEmp(emp);
		assertEquals(1, id);
		verify(empDao).insertEmp(emp);
	}
	
	@Test
	public void testUpdateEmpWithCustomizedArgMatcher(){
		Emp empModified = new Emp();
		empModified.setId(1);
		empModified.setName("Mockito_M");
		
		//when(empDao.updateEmp(empModified)).thenReturn(1);
		when(empDao.updateEmp(argThat(new ArgumentMatcher<Emp>() {

			@Override
			public boolean matches(Object argument) {
				// TODO Auto-generated method stub
				return ((Emp)argument).getId() == 1;
			}
			
		}))).thenReturn(1);
		int id = empService.updateEmp(empModified);
		assertEquals(1, id);
		verify(empDao, times(1)).updateEmp(argThat(new ArgumentMatcher<Emp>() {

			@Override
			public boolean matches(Object argument) {
				// TODO Auto-generated method stub
				return ((Emp)argument).getId() == 1;
			}
			
		}));
	}
	//http://blog.karthiksankar.com/mockito-2/
}


