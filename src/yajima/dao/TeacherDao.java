package yajima.dao;

import yajima.model.Teacher;

public interface TeacherDao {
	void addTeacher(Teacher t);
	Teacher findTeacher(int id);
}
