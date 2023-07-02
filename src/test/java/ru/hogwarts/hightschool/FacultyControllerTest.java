package ru.hogwarts.hightschool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.hightschool.controller.StudentController;
import ru.hogwarts.hightschool.model.Faculty;
import ru.hogwarts.hightschool.model.Student;
import ru.hogwarts.hightschool.repository.FacultyRepository;
import ru.hogwarts.hightschool.repository.StudentRepository;
import ru.hogwarts.hightschool.service.FacultyService;
import ru.hogwarts.hightschool.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private StudentController studentController;

    @AfterEach
    public void clean() {
        facultyRepository.deleteAll();
    }

    @Test
    void getFacultyByIdTest() throws Exception {

        final long id = 1;
        final String name = "Хогвартс";
        final String color = "КРАСНЫЙ";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        mvc.perform(get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void getFacultyByColorTest() throws Exception {

        final String color = "ЗЕЛЁНЫЙ";

        Faculty faculty1 = new Faculty(1, "X", color);
        Faculty faculty2 = new Faculty(2, "Y", color);

        List<Faculty> faculties = List.of(faculty1, faculty2);

        when(facultyRepository.findByColor(color)).thenReturn(faculties);

        mvc.perform(get("/faculty/color/" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(faculties)));
    }

    @Test
    void createFacultyTest() throws Exception {

        final long id = 1;
        final String name = "Хогвартс";
        final String color = "КРАСНЫЙ";

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("name", name);
        userObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        mvc.perform(post("/faculty").content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(faculty)));
    }

    @Test
    void updateFacultyTest() throws Exception {

        Faculty faculty = new Faculty(1, "X", "RED");
        when(facultyRepository.save(any())).thenReturn(new Faculty(1, "S", "RED"));
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        mvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(new Faculty(1, "S", "RED")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("S"))
                .andExpect(jsonPath("$.color").value("RED"));
    }

    @Test
    void removeFacultyTest() throws Exception {

        Faculty faculty = new Faculty(1, "X", "RED");

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());
    }


    @Test
    void getByColorOrNameTest() throws Exception {

        Faculty faculty = new Faculty(1, "X", "RED");
        Faculty faculty1 = new Faculty(2, "S", "RED");

        List<Faculty> list = List.of(faculty, faculty1);

        when(facultyRepository
                .findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(anyString(), anyString()))
                .thenReturn(list);

        mvc.perform(get("/faculty/filter")
                .param("colorOrName","RED")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty1.getColor()));
    }


}
