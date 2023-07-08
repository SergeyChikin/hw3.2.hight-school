package ru.hogwarts.hightschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.hightschool.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long studentId);
}
