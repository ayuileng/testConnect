package yajima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import yajima.dao.TeacherDao;
import yajima.model.Student;
import yajima.model.Teacher;
import yajima.utils.DBCPUtil;

public class TeacherDaoImpl implements TeacherDao {
	private QueryRunner qr=new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addTeacher(Teacher t) {
		try {
			qr.update("insert into teachers (id,name,salary) values (?,?,?)", t.getId(),t.getName(),t.getSalary());
			List<Student> students = t.getStudents();
			if(students.size()>0){
				for (Student s : students) {
					Object obj = qr.query("select id from students where id =?", new ScalarHandler(1),s.getId());
					if(obj==null){
						qr.update("insert into students(id,name,grade) values (?,?,?)", s.getId(),s.getName(),s.getGrade());
					}
					qr.update("insert into teachers_students (t_id,s_id) values (?,?)", t.getId(),s.getId());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Teacher findTeacher(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
