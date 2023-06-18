package ru.hogwarts.hightschool.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.hightschool.model.Faculty;
import ru.hogwarts.hightschool.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long facultyId) {
        Faculty foundFaculty = facultyService.getFaculty(facultyId);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @GetMapping("/color/{facultyColor}")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@PathVariable String facultyColor) {
        Collection<Faculty> result = facultyService.getFacultiesByColor(facultyColor);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty addedFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(addedFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editedFaculty = facultyService.editFaculty(faculty);
        if (editedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editedFaculty);
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long facultyId) {
        Faculty removedFaculty = facultyService.deleteFaculty(facultyId);
        if (removedFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(removedFaculty);
    }
}
