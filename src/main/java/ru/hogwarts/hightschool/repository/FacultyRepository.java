package ru.hogwarts.hightschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hightschool.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColorLike(String color);
}
