package ru.hogwarts.hightschool.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.hightschool.model.Faculty;
import ru.hogwarts.hightschool.model.Student;
import ru.hogwarts.hightschool.repository.FacultyRepository;
import ru.hogwarts.hightschool.repository.StudentRepository;
import java.util.Collection;
import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository
                          ) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        return studentRepository.findById(id).orElse(null);
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

    public Collection<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getStudentsByFacultyId(long id) {
        return studentRepository.findAllByFaculty_Id(id);
    }


    public Faculty findFaculty(long id) {
        return facultyRepository.findFacultyByStudentsId(id);
    }



    public Integer getTotalNumber() {
        return studentRepository.getAllNumbers();
    }

    public Double getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getLastFive() {
        return studentRepository.getLastFiveStudents();
    }
}
