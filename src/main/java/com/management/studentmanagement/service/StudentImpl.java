package com.management.studentmanagement.service;

import com.management.studentmanagement.dto.StudentDto;
import com.management.studentmanagement.entity.Student;
import com.management.studentmanagement.mapper.StudentMapper;
import com.management.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentImpl implements StudentService{
    private StudentRepository studentRepository;

    public StudentImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDto> sdto = students.stream()
                .map((student)->StudentMapper.mapTosStudentDto(student))
                .collect(Collectors.toList());
        return sdto;
    }

    @Override
    public void createStudent(StudentDto sdto) {
        Student student = StudentMapper.mapTosStudent(sdto);
        studentRepository.save(student);
    }

    @Override
    public StudentDto getValuebyId(long studentId) {
        Student student = studentRepository.findById(studentId).get();
        StudentDto dto = StudentMapper.mapTosStudentDto(student);
        return dto;
    }

    @Override
    public void updateStudent(StudentDto dto) {
        Student student = StudentMapper.mapTosStudent(dto);
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public boolean existsByEmail(String Email) {
        return studentRepository.existsByEmail(Email);
    }
}
