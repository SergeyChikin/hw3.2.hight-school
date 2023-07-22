package ru.hogwarts.hightschool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.hightschool.model.Faculty;
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
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable int studentAge) {
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
    public ResponseEntity<Optional<Faculty>> getStudentFaculty(@PathVariable("id") long id) {
        Optional<Faculty> studentFaculty = Optional.ofNullable(studentService.findFaculty(id));
        if (!studentFaculty.isPresent()) {
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



    // Возможность получить количество всех студентов в школе.
    @GetMapping("/total-number")
    public Integer getTotalNumberOfStudents() {
        return studentService.getTotalNumber();
    }

    // Возможность получить средний возраст студентов.
    @GetMapping("/average-age")
    public Double getAverageAgeOfStudents() {
        return studentService.getAverageAge();
    }

    //Возможность получать только пять последних студентов.
    @GetMapping("/last-five")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFive();
    }

    //Эндпоинт, кот. возвр. всех студентов в алф. порядке, в верхнем регистре,
    // чьё имя на "А".
    @GetMapping("/find-students-on-a")
    public ResponseEntity<Collection<Student>> findEveryoneWhoseNameOnA() {
        Collection<Student> result = studentService.findEveryoneWhoseNameOnA();
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

   //    Создать эндпоинт, который будет возвращать средний возраст всех студентов.
    @GetMapping("/average-age-stream")
    public Double getAverageAgeStream() {
        return studentService.getAverageAgeStream();
    }


    //    Эндпоинт, который запускает два параллельных потока для вывода имен студентов в консоль.
    @GetMapping("/thread-names")
    public Collection<String> getThreadNames() {
        return studentService.getThreadNames();
    }

   //эндпоинт, который запускает два синхронизированных параллельных потока для вывода имен студентов в консоль.
    @GetMapping("/thread-names-synchronized")
    public Collection<String> getThreadNamesSynchronized(){
        return studentService.getThreadNamesSynchronized();
    }
}

