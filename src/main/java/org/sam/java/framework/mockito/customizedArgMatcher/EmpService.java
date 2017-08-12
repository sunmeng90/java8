package org.sam.java.framework.mockito.customizedArgMatcher;

public class EmpService {
	private EmpDao empDao;

	public EmpService(EmpDao empDao) {
		super();
		this.empDao = empDao;
	}

	public int registerWithOriginalArg(Emp emp) {

		return empDao.insertEmp(emp);
	}

	public int updateEmp(Emp emp) {
		Emp empDB = new Emp();
		empDB.setId(emp.getId());//query from db with same ID
		empDB.setName(emp.getName());//update fields with modified version
		return empDao.updateEmp(empDB);
	}
}

class Emp {
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