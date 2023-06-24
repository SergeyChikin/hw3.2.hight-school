package ru.hogwarts.hightschool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.hightschool.model.Student;
import ru.hogwarts.hightschool.service.StudentService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable long studentId) {
        Student foundStudent = studentService.getStudent(studentId);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("/age/{studentAge}")
    public ResponseEntity<Collection<Student>> getStudentByAge(@PathVariable int studentAge) {
        Collection<Student> result = studentService.getStudentsByAge(studentAge);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filterAge")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam int minAge,
                                                                       @RequestParam int maxAge) {
        Collection<Student> result = studentService.getStudentsByAgeBetween(minAge, maxAge);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Optional<Student>> getStudentFaculty(@PathVariable("id") long id) {
        Optional<Student> studentFaculty = studentService.findFaculty(id);
        if (studentFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentFaculty);
    }


    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = studentService.addStudent(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = studentService.editStudent(student);
        if (editedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Student> removeStudent(@PathVariable long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }


}
