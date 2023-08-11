package com.management.studentmanagement.controller;

import com.management.studentmanagement.dto.StudentDto;
import com.management.studentmanagement.dto.UserDto;
import com.management.studentmanagement.entity.Student;
import com.management.studentmanagement.mapper.StudentMapper;
import com.management.studentmanagement.service.StudentService;
import com.management.studentmanagement.service.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }

    @GetMapping("/")
    public String index(){
        return "redirect:/login";
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
    @GetMapping("/register")
    public String returnHome(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
         return "register";
    }
    @PostMapping("/register/save")
    public String registerUser(@Valid @ModelAttribute("user") UserDto dto, BindingResult result, Model model)
    {
        String email = dto.getEmail();
        boolean emailExists = userService.ifUserEmailExists(email);

        if (emailExists) {
            result.rejectValue("email", null,
                    "An account with this email address already exists");
        }
        if (result.hasErrors()){
            model.addAttribute("user", dto);
            return "/register";
        }

        userService.saveUser(dto);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginUser(){
        return "login";
    }
}
