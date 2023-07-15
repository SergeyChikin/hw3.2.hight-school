package ru.hogwarts.hightschool.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.hightschool.model.Faculty;
import ru.hogwarts.hightschool.model.Student;
import ru.hogwarts.hightschool.repository.FacultyRepository;
import ru.hogwarts.hightschool.repository.StudentRepository;

import java.util.Collection;


@Service
public class FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method addFaculty with parameters");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        logger.info("Was invoked method getFaculty with id = {}", id);
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method editFaculty with parameters");
        return facultyRepository.save(faculty);
    }



    public void deleteFaculty(long id) {
        logger.info("Was invoked method deleteFaculty with id = {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method getFacultiesByColor with color - {}", color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method getAllFaculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getByColorOrName(String colorOrName) {
        logger.info("Was invoked method getByColorOrName");
        return facultyRepository.findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(colorOrName, colorOrName);
    }

    public Collection<Student> getStudents(long id) {
        logger.info("Was invoked method getStudents with id faculty = {}", id);
        return studentRepository.findAllByFaculty_Id(id);
    }


}
