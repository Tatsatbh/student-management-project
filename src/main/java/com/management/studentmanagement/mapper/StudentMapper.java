package com.management.studentmanagement.mapper;

import com.management.studentmanagement.dto.StudentDto;
import com.management.studentmanagement.entity.Student;

public class StudentMapper {
    public static StudentDto mapTosStudentDto(Student student){
        StudentDto sdto = new StudentDto(
                student.getId(),
                student.getFname(),
                student.getLname(),
                student.getEmail()
        );
        return sdto;
    }
    public static Student mapTosStudent(StudentDto sdto){
        Student student = new Student(
                sdto.getId(),
                sdto.getFname(),
                sdto.getLname(),
                sdto.getEmail()
        );
        return student;
    }
}
