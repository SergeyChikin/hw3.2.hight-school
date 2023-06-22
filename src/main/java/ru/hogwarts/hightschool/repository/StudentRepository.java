package ru.hogwarts.hightschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hightschool.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAgeLessThan(int age);
}