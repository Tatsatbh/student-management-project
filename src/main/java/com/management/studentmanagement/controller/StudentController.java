package com.management.studentmanagement.controller;

import com.management.studentmanagement.dto.StudentDto;
import com.management.studentmanagement.entity.Student;
import com.management.studentmanagement.mapper.StudentMapper;
import com.management.studentmanagement.service.StudentService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping({"/students"})
    public String listStudents(Model model){
        List<StudentDto> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students";
    }
    @GetMapping("/students/new")
    public String newStudent(Model model){
        // student model object to store student form data
        StudentDto studentDto = new StudentDto();
        model.addAttribute("student", studentDto);
        return "create_student";
    }
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") StudentDto sdto, BindingResult result, RedirectAttributes redirectAttributes){
        String email = sdto.getEmail();
        boolean emailExists = studentService.existsByEmail(email);
        if (emailExists) {
            redirectAttributes.addFlashAttribute("error", "Email already exists in the database");
            return "redirect:/students/new";
        }

        studentService.createStudent(sdto);
        return "redirect:/students";
    }

    @GetMapping("/students/{studentId}/edit")
    public String editStudent(@PathVariable long studentId, Model model){
        StudentDto dto = studentService.getValuebyId(studentId);
        model.addAttribute("student",dto);
        return "edit_student";
    }

    @PostMapping("/students/{studentId}")
    public String updateStudent(@PathVariable Long studentId,
                                @ModelAttribute("student") StudentDto dto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes)
    {
        dto.setId(studentId);
        StudentDto original_user = studentService.getValuebyId(studentId);
        String email  = dto.getEmail();
        boolean emailExists = studentService.existsByEmail(email);
        if (emailExists && !Objects.equals(email, original_user.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email already exists in the database");
            return "redirect:/students/{studentId}/edit";

        }
        studentService.updateStudent(dto);
        return "redirect:/students";
    }

    @GetMapping("/students/{studentId}/delete")
    public String deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
        return "redirect:/students";
    }

    @GetMapping("/students/{studentId}/view")
    public String showDetails(@PathVariable Long studentId, Model model){
        StudentDto dto = studentService.getValuebyId(studentId);
        model.addAttribute("show",dto);
        return "view";
    }



}
