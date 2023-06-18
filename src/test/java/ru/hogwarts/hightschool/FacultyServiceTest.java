package ru.hogwarts.hightschool;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.hightschool.model.Faculty;
import ru.hogwarts.hightschool.service.FacultyService;

import java.util.Collections;
import java.util.List;

public class FacultyServiceTest {
    FacultyService out;

    @BeforeEach
    public void beforeEach() {
        out = new FacultyService();
        out.addFaculty(new Faculty(1, "Gryffindor", "RED"));
        out.addFaculty(new Faculty(2, "Ravenclaw", "BLUE"));
        out.addFaculty(new Faculty(3, "Hufflepuff", "YELLOW"));
        out.addFaculty(new Faculty(4, "Slytherin", "GREEN"));
    }

    @Test
    void addFacultyTest() {
        int size = out.getAllFaculties().size();
        Faculty a = new Faculty(5, "Darkpuff", "DARK");
        Assertions.assertEquals(a, out.addFaculty(a));
        Assertions.assertEquals(size + 1, out.getAllFaculties().size());
    }

    @Test
    void getFacultyPositiveTest() {
        Assertions.assertEquals(new Faculty(4, "Slytherin", "GREEN"), out.getFaculty(4));
    }

    @Test
    void getFacultyNegativeTest() {
        Assertions.assertNull(out.getFaculty(5));
    }

    @Test
    void editFacultyPositiveTest() {
        Faculty a = (new Faculty(4, "Slytherin", "WHITE"));
        int size = out.getAllFaculties().size();
        Assertions.assertEquals(a, out.editFaculty(a));
        Assertions.assertEquals(size, out.getAllFaculties().size());
    }

    @Test
    void editFacultyNegativeTest() {
        Faculty a = new Faculty(5, "Slytherin", "WHITE");
        int size = out.getAllFaculties().size();
        Assertions.assertNull(out.editFaculty(a));
        Assertions.assertEquals(size, out.getAllFaculties().size());
    }

    @Test
    void removeFacultyPositiveTest() {
        Faculty a = new Faculty(4, "Slytherin", "GREEN");
        int size = out.getAllFaculties().size();
        Faculty b = out.deleteFaculty(4);
        Assertions.assertEquals(null, out.deleteFaculty(4 ));
        Assertions.assertEquals(size - 1, out.getAllFaculties().size());
    }

    @Test
    void removeFacultyNegativeTest() {
        int size = out.getAllFaculties().size();
        Assertions.assertNull(out.deleteFaculty(5));
        Assertions.assertEquals(size, out.getAllFaculties().size());
    }

    @Test
    void getFacultyByColorPositiveTest() {
        Faculty a = new Faculty(5, "Darkpuff", "GREEN");
        out.addFaculty(a);
        List<Faculty> b = List.of(new Faculty(4, "Slytherin", "GREEN"), a);
        Assertions.assertIterableEquals(b, out.getFacultiesByColor("GREEN"));
    }

    @Test
    void getFacultyByColorNegativeTest() {
        List<Faculty> b = Collections.emptyList();
        Assertions.assertIterableEquals(b, out.getFacultiesByColor("WHITE"));
    }
}
