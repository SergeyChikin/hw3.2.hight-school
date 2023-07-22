package ru.hogwarts.hightschool.service;

import liquibase.pro.packaged.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.hightschool.model.Faculty;
import ru.hogwarts.hightschool.model.Student;
import ru.hogwarts.hightschool.repository.FacultyRepository;
import ru.hogwarts.hightschool.repository.StudentRepository;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository
                          ) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method addStudent with parameters");
        return studentRepository.save(student);
    }

    public Student getStudent(long id) {
        logger.info("Was invoked method getStudent with id = {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method editStudent with parameters");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method deleteStudent with id = {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method getStudentByAge");
        return studentRepository.findStudentByAgeLessThan(age);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method getAllStudents");
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method getStudentsByAgeBetween");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getStudentsByFacultyId(long id) {
        logger.info("Was invoked method getStudentsByFaculty with id = {}", id);
        return studentRepository.findAllByFaculty_Id(id);
    }


    public Faculty findFaculty(long id) {
        logger.info("Was invoked method findFaculty with id = {}", id);
        return facultyRepository.findFacultyByStudentsId(id);
    }



    public Integer getTotalNumber() {
        logger.info("Was invoked method getTotalNumber");
        return studentRepository.getAllNumbers();
    }

    public Double getAverageAge() {
        logger.info("Was invoked method getAverageAge");
        return studentRepository.getAverageAge();
    }

    public Collection<Student> getLastFive() {
        logger.info("Was invoked method getLastFive");
        return studentRepository.getLastFiveStudents();
    }

    public Collection<Student> findEveryoneWhoseNameOnA() {
        logger.info("Was invoked method findEveryoneWhoseNameOnA");
        return  studentRepository.findAll().stream()
                .filter(student -> student.getName()
                        .toUpperCase()
                        .startsWith("А"))
//                "А" - кириллица( как и в БД);
                .sorted(Comparator.comparing(Student :: getName))
                .collect(Collectors.toList());
    }


    public Double getAverageAgeStream() {
        logger.info("Was invoked method getAverageAgeStream");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0.0);
    }


    public Collection<String> getThreadNames() {
        logger.info("Was invoked method getThreadNames");
        List<String> threadNames = studentRepository.findAll()
                .stream()
                .limit(6)
                .map(Student :: getName)
                .collect(Collectors.toList());

        System.out.println(threadNames.get(0));
        System.out.println(threadNames.get(1));

        new Thread(() -> {
            try{
                System.out.println(threadNames.get(2));
                Thread.sleep(1000);
                System.out.println(threadNames.get(3));
            } catch (InterruptedException e){}
        }).start();

        new Thread(() -> {
            try{
                System.out.println(threadNames.get(2));
                Thread.sleep(1000);
                System.out.println(threadNames.get(3));
            } catch (InterruptedException e){}
        }).start();

        return threadNames;
    }

    private synchronized void printNames(String name1, String name2) {
        try {
            System.out.println(name1);
            Thread.sleep(1000);
            System.out.println(name2);
        } catch (InterruptedException e){}
    }


    public Collection<String> getThreadNamesSynchronized() {
        logger.info("Was invoked method getThreadNamesSynchronized");
        List<String> threadNames = studentRepository.findAll()
                .stream()
                .limit(6)
                .map(Student :: getName)
                .collect(Collectors.toList());

        printNames(threadNames.get(0), threadNames.get(1));

        new Thread(() -> {
            printNames(threadNames.get(2), threadNames.get(3));
        }).start();

        new Thread(() -> {
            printNames(threadNames.get(4), threadNames.get(5));
        }).start();

        return threadNames;
    }

}
