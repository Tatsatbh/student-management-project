package com.management.studentmanagement.service;

import com.management.studentmanagement.dto.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> getAllStudents();

    void createStudent(StudentDto sdto);

    StudentDto getValuebyId(long studentId);

    void updateStudent(StudentDto dto);

    void deleteStudent(Long studentId);

    boolean existsByEmail(String Email);
}
