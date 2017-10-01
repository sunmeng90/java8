package org.meng.java.framework.mockito.customizedArgMatcher;

public class FooTestService {
	private FooDao fooDao;

	public FooTestService(FooDao fooDao) {
		super();
		this.fooDao = fooDao;
	}

	public int registerWithOriginalArg(Foo foo) {

		return fooDao.insertFoo(foo);
	}

	public int updateFoo(Foo foo) {
		Foo fooDB = new Foo();
		fooDB.setId(foo.getId());//query from db with same ID
		fooDB.setName(foo.getName());//update fields with modified version
		return fooDao.updateFoo(fooDB);
	}
}

class Foo {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}