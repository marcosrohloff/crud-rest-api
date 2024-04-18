package br.com.restapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.restapi.dtos.StudentDTO;
import br.com.restapi.services.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/get-all")
    public List<StudentDTO> getAllStudents() {
        System.out.println("Getting all Students");
        return studentService.getAll();
    }

    @GetMapping("/get/{id}")
    public StudentDTO getStudentById(@PathVariable("id") long studentID) {
        return studentService.getByID(studentID);
    }

    @PostMapping("/save")
    public StudentDTO saveStudent(@RequestBody StudentDTO student) {
        return studentService.save(student);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") long studentID) {
        if (studentService.deleteByID(studentID)) {
            return ResponseEntity.ok("Student deleted successfully");
        }
        return ResponseEntity.status(404).body("Student Not Found");
    }

}
