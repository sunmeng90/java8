package org.meng.java.framework.mockito.customizedArgMatcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FooTestServiceTest {
    @InjectMocks
    private FooTestService fooTestService;
    @Mock
    private FooDao fooDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateEmpWithOriginalArg() {
        Foo foo = new Foo();
        foo.setId(1);
        foo.setName("Mockito");
        when(fooDao.insertFoo(foo)).thenReturn(1);
        int id = fooDao.insertFoo(foo);
        assertEquals(1, id);
        verify(fooDao).insertFoo(foo);
    }

    @Test
    public void testUpdateEmpWithCustomizedArgMatcher() {
        Foo fooModified = new Foo();
        fooModified.setId(1);
        fooModified.setName("Mockito_M");

        //when(fooDao.updateEmp(fooModified)).thenReturn(1);
        when(fooDao.updateFoo(argThat(new ArgumentMatcher<Foo>() {

            @Override
            public boolean matches(Foo argument) {
                // TODO Auto-generated method stub
                return argument.getId() == 1;
            }

        }))).thenReturn(1);
        int id = fooTestService.updateFoo(fooModified);
        assertEquals(1, id);
        verify(fooDao, times(1)).updateFoo(argThat(new ArgumentMatcher<Foo>() {

            @Override
            public boolean matches(Foo argument) {
                // TODO Auto-generated method stub
                return argument.getId() == 1;
            }

        }));
    }
    //http://blog.karthiksankar.com/mockito-2/
}


