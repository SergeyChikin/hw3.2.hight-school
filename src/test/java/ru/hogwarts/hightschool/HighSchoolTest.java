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

public class HighSchoolTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

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
        studentRepository.deleteAll();
    }


    @Test
    void getStudentPositiveTest() throws Exception{

        final long id = 1;
        final String name = "Гермиона Грейнджер";
        final int age = 12;

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        mvc.perform(get("/student/" + id).accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age)) ;
    }

    @Test
    void getStudentNegativeTest() throws Exception{
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get("/student/1").accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound());
    }

    @Test
    void addStudentPositiveTest() throws Exception {

        final long id = 1;
        final String name = "Гермиона Грейнджер";
        final int age = 12;

        Student student = new Student();
        student.setName(name);
        student.setId(id);
        student.setAge(age);

        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("name", name);
        userObject.put("age", age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        mvc.perform(post("/student").content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age)) ;
    }

    @Test
    void editStudentNegativeTest() throws Exception{
        when(studentRepository.save(any(Student.class))).thenReturn(null);
        mvc.perform(put("/student").accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
    }

    @Test
    void getStudentByAgeTest() throws Exception {
        Faculty f1 = new Faculty();
        Faculty f2 = new Faculty();
        Student s1 = new Student(1, "A", 10, f1);
        Student s2 = new Student(2, "B", 10, f2);

        List<Student> list = List.of(s1, s2);

        when(studentRepository.findStudentByAgeLessThan(anyInt())).thenReturn(list);

        mvc.perform(get("/student/age/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.[0].name").value(s1.getName()))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(s2.getId()))
                .andExpect(jsonPath("$.[1].name").value(s2.getName()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));
    }

    @Test
    void getStudentByAgeBetweenTest() throws Exception {
        Faculty f1 = new Faculty();
        Faculty f2 = new Faculty();
        Student s1 = new Student(1, "A", 10, f1);
        Student s2 = new Student(2, "B", 10, f2 );

        List<Student> list = List.of(s1, s2);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(list);

        mvc.perform(get("/student/filterAge")
                        .param("minAge", "10")
                        .param("maxAge", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.[0].name").value(s1.getName()))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(s2.getId()))
                .andExpect(jsonPath("$.[1].name").value(s2.getName()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));
    }

    @Test
    void getStudentFacultyTest() throws Exception {
        final long id = 17;
        final String name = "Гермиона Грейнджер";
        final int age = 12;
        final long idFaculty = 1;
        final String nameFaculty = "Гриффиндор";
        final String color = "красный";

        Student student = new Student();
        Faculty faculty = new Faculty();

        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);
        faculty.setId(idFaculty);
        faculty.setName(nameFaculty);
        faculty.setColor(color);

        when(facultyRepository.findFacultyByStudentsId(anyLong())).thenReturn(student.getFaculty());

        mvc.perform(get("/student/17/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idFaculty))
                .andExpect(jsonPath("$.name").value(nameFaculty))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void removeStudentTest() throws Exception {
        Faculty f2 = new Faculty();
        Student s1 = new Student(1, "A", 10, f2);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(s1));

        mvc.perform(delete("/student/{id}", s1.getId()))
                .andExpect(status().isOk());
    }

}
