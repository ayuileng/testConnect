package yajima.test;

import static org.junit.Assert.*;

import org.junit.Test;

import yajima.dao.TeacherDao;
import yajima.dao.impl.TeacherDaoImpl;
import yajima.model.Student;
import yajima.model.Teacher;

public class TeacherDaoTest {
	private TeacherDao dao = new TeacherDaoImpl();
	@Test
	public void testAddTeacher() {
		Teacher t1=new Teacher();
		t1.setId(1);
		t1.setName("yajima");
		t1.setSalary(1000);
		Teacher t2=new Teacher();
		t2.setId(2);
		t2.setName("suzuki");
		t2.setSalary(2000);
		
		Student s1=new Student();
		s1.setId(1);
		s1.setName("hehe1");
		s1.setGrade("81");
		Student s2=new Student();
		s2.setId(2);
		s2.setName("hehe2");
		s2.setGrade("82");
		
		
		t1.getStudents().add(s1);
		t1.getStudents().add(s2);
		t2.getStudents().add(s1);
		t2.getStudents().add(s2);
		dao.addTeacher(t1);
		dao.addTeacher(t2);
	}

	@Test
	public void testFindTeacher() {
	}

}
