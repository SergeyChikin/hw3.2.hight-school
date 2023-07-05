package ru.hogwarts.hightschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.hightschool.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentByAgeLessThan(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    Collection<Student> findAllByFaculty_Id(long facultyId);


    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getAllNumbers();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double getAverageAge();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
