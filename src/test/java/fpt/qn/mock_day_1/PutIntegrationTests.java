package fpt.qn.mock_day_1;

import fpt.qn.mock_day_1.controller.EmployeeController;
import fpt.qn.mock_day_1.controller.ProjectController;
import fpt.qn.mock_day_1.entity.Employee;
import fpt.qn.mock_day_1.entity.Project;
import fpt.qn.mock_day_1.entity.ProjectStatus;
import fpt.qn.mock_day_1.exceptionHandler.GlobalExceptionHandler;
import fpt.qn.mock_day_1.repository.AllocationRepository;
import fpt.qn.mock_day_1.repository.EmployeeRepository;
import fpt.qn.mock_day_1.repository.ProjectRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
public class PutIntegrationTests {

    private MockMvc mockMvc;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private ProjectController projectController;

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EntityManager entityManager;

    private Employee employee1;
    private Employee employee2;
    private Project project1;
    private Project project2;

    @BeforeEach
    public void setUp() {
        // Setup MockMvc standalone with GlobalExceptionHandler
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController, projectController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        // Clear references in correct order of dependency
        allocationRepository.deleteAll();
        employeeRepository.deleteAll();
        projectRepository.deleteAll();
        
        // Force Hibernate to run the deletes immediately
        entityManager.flush();

        // Create sample employees
        employee1 = new Employee();
        employee1.setEmployeeCode("EMP001");
        employee1.setFullName("Tuan Ho Anh");
        employee1.setEmail("tuanha@company.com");
        employee1.setRole("Senior Developer");
        employee1.setDepartment("FSOFT-Q1");
        employee1 = employeeRepository.save(employee1);

        employee2 = new Employee();
        employee2.setEmployeeCode("EMP002");
        employee2.setFullName("Nguyen Van B");
        employee2.setEmail("nguyenvanb@company.com");
        employee2.setRole("Junior Developer");
        employee2.setDepartment("FSOFT-Q2");
        employee2 = employeeRepository.save(employee2);

        // Create sample projects
        project1 = new Project();
        project1.setProjectCode("PROJ001");
        project1.setProjectName("NCG Project");
        project1.setCustomer("FPT Software");
        project1.setStatus(ProjectStatus.ACTIVE);
        project1 = projectRepository.save(project1);

        project2 = new Project();
        project2.setProjectCode("PROJ002");
        project2.setProjectName("GRID Project");
        project2.setCustomer("GRID Inc");
        project2.setStatus(ProjectStatus.PLANNING);
        project2 = projectRepository.save(project2);
        
        entityManager.flush();
    }

    @Test
    public void testUpdateEmployeeSuccess() throws Exception {
        String json = "{"
                + "\"employeeCode\":\"EMP001\","
                + "\"fullName\":\"Tuan Ho Anh Updated\","
                + "\"email\":\"tuanha_new@company.com\","
                + "\"role\":\"Tech Lead\","
                + "\"department\":\"FSOFT-Q1-Updated\""
                + "}";

        mockMvc.perform(put("/employees/EMP001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeCode", is("EMP001")))
                .andExpect(jsonPath("$.fullName", is("Tuan Ho Anh Updated")))
                .andExpect(jsonPath("$.email", is("tuanha_new@company.com")))
                .andExpect(jsonPath("$.role", is("Tech Lead")))
                .andExpect(jsonPath("$.department", is("FSOFT-Q1-Updated")));
    }

    @Test
    public void testUpdateEmployeeCodeChangeSuccess() throws Exception {
        String json = "{"
                + "\"employeeCode\":\"EMP001-NEW\","
                + "\"fullName\":\"Tuan Ho Anh\","
                + "\"email\":\"tuanha@company.com\","
                + "\"role\":\"Senior Developer\","
                + "\"department\":\"FSOFT-Q1\""
                + "}";

        mockMvc.perform(put("/employees/EMP001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeCode", is("EMP001-NEW")));
    }

    @Test
    public void testUpdateEmployeeDuplicateCode() throws Exception {
        // Try to update EMP001 to use EMP002's code
        String json = "{"
                + "\"employeeCode\":\"EMP002\","
                + "\"fullName\":\"Tuan Ho Anh\","
                + "\"email\":\"tuanha@company.com\","
                + "\"role\":\"Senior Developer\","
                + "\"department\":\"FSOFT-Q1\""
                + "}";

        mockMvc.perform(put("/employees/EMP001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEmployeeNotFound() throws Exception {
        String json = "{"
                + "\"employeeCode\":\"EMP999\","
                + "\"fullName\":\"No Name\","
                + "\"email\":\"noname@company.com\","
                + "\"role\":\"Developer\","
                + "\"department\":\"FSOFT-Q1\""
                + "}";

        mockMvc.perform(put("/employees/EMP999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateProjectSuccess() throws Exception {
        String json = "{"
                + "\"projectCode\":\"PROJ001\","
                + "\"projectName\":\"NCG Project Updated\","
                + "\"customer\":\"FPT Software Global\","
                + "\"status\":\"COMPLETED\""
                + "}";

        mockMvc.perform(put("/projects/PROJ001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectCode", is("PROJ001")))
                .andExpect(jsonPath("$.projectName", is("NCG Project Updated")))
                .andExpect(jsonPath("$.customer", is("FPT Software Global")))
                .andExpect(jsonPath("$.status", is("COMPLETED")));
    }

    @Test
    public void testUpdateProjectCodeChangeSuccess() throws Exception {
        String json = "{"
                + "\"projectCode\":\"PROJ001-NEW\","
                + "\"projectName\":\"NCG Project\","
                + "\"customer\":\"FPT Software\","
                + "\"status\":\"ACTIVE\""
                + "}";

        mockMvc.perform(put("/projects/PROJ001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectCode", is("PROJ001-NEW")));
    }

    @Test
    public void testUpdateProjectDuplicateCode() throws Exception {
        // Try to update PROJ001 to use PROJ002's code
        String json = "{"
                + "\"projectCode\":\"PROJ002\","
                + "\"projectName\":\"NCG Project\","
                + "\"customer\":\"FPT Software\","
                + "\"status\":\"ACTIVE\""
                + "}";

        mockMvc.perform(put("/projects/PROJ001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateProjectNotFound() throws Exception {
        String json = "{"
                + "\"projectCode\":\"PROJ999\","
                + "\"projectName\":\"No Project\","
                + "\"customer\":\"Unknown\","
                + "\"status\":\"PLANNING\""
                + "}";

        mockMvc.perform(put("/projects/PROJ999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
