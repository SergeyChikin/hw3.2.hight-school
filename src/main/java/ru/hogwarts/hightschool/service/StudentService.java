package ru.hogwarts.hightschool.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.hightschool.model.Student;
import ru.hogwarts.hightschool.repository.StudentRepository;
import java.util.Collection;



@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findStudentByAgeLessThan(age);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }


}
