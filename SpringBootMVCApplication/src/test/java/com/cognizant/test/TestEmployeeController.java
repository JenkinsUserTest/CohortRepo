package com.cognizant.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.cognizant.controller.EmployeeController;
import com.cognizant.dto.EmployeeResponse;
import com.cognizant.dto.EmployeesModel;
import com.cognizant.main.SpringBootMvcApplication;
import com.cognizant.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@AutoConfigureMockMvc
@WebMvcTest
@ContextConfiguration(classes = SpringBootMvcApplication.class)
class TestEmployeeController {

	private MockMvc mockMvc;
	
	@Mock
	private EmployeeService employeeService;
	@InjectMocks
	private EmployeeController employeeController;
	@Mock
	private RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
	
	private RestTemplate template;
	
	private ObjectMapper mapper=new ObjectMapper();
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(employeeController).build();
		template=new RestTemplate();
		mockServer=MockRestServiceServer.createServer(template);
		
	}
	@Test
	void testhandleGetAllEmployees_positive() {	
    	try {
		List<EmployeesModel> employeesModelList=new ArrayList<EmployeesModel>();
		EmployeesModel employeesModel=new EmployeesModel();
		employeesModel.setEmpId(1001);
		employeesModel.setEmpName("Sabbir");
		employeesModel.setEmpSalary(34000);
		employeesModel.setEmpDesignation("Trainer");
		
		employeesModelList.add(employeesModel);
		
		when(employeeService.getAllEmployees()).thenReturn(employeesModelList);
		
		ResponseEntity<List<EmployeeResponse>> responseEntity=employeeController.handleGetAllEmployees();
		List<EmployeeResponse> employeeResponseList=responseEntity.getBody();
		assertTrue(employeeResponseList.size()>0);
		}catch(Exception e) {
			assertTrue(false);
		}
		
	}
	@Test
	public void testGetEmployeePositive() {
		when(employeeService.getEmployeeById(anyInt())).thenAnswer(
				new Answer<EmployeesModel>() {
					
					public EmployeesModel answer(InvocationOnMock invocation) throws Throwable{
						EmployeesModel employeesModel=new EmployeesModel();
						employeesModel.setEmpId(1001);
						employeesModel.setEmpName("Sabbir");
						employeesModel.setEmpSalary(34000);
						employeesModel.setEmpDesignation("Trainer");
						return employeesModel;
					}
				});
		ResponseEntity<EmployeeResponse> responseEntity=employeeController.getEmployee(1001);
		EmployeeResponse employeeResponse=responseEntity.getBody();
		assertEquals(1001,employeeResponse.getEmpId());
	}
	@Test
	void testGetEmployeesURIPositive() {
		EmployeesModel mockEmployeesModel=new EmployeesModel();
		mockEmployeesModel.setEmpId(1001);
		when(employeeService.getEmployeeById(anyInt())).thenReturn(mockEmployeesModel);
		
		try {
			MvcResult result=mockMvc.perform(get("http://localhost:8082/api/employees/{empId}",1001))
			.andExpect(status().isFound()).andReturn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}
		
	}
	@Test
	void testGetEmployeesURINegative() {
		EmployeesModel mockEmployeesModel=new EmployeesModel();
		mockEmployeesModel.setEmpId(1001);
		when(employeeService.getEmployeeById(anyInt())).thenReturn(mockEmployeesModel);
		
		try {
			MvcResult result=mockMvc.perform(get("http://localhost:8082/api/employees/{empId}",222))
			.andExpect(status().isNotFound()).andReturn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}
		
	}
	@Test
	void testUpdateEmployeeSalaryPositive() {
		when(employeeService.UpdateEmployeeSalary(anyInt(), anyDouble())).thenReturn(true);
		try {
			MvcResult result=mockMvc.perform(patch("http://localhost:8082/api/employees/{empId}/{empSalary}",1001,32000))
			.andExpect(status().isAccepted()).andReturn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}
	}

	@Test
	void testRestTemplatePositive() {
		EmployeeResponse employeeResponse=new EmployeeResponse();
		employeeResponse.setEmpId(1001);
		employeeResponse.setEmpName("Sabbir");
		employeeResponse.setEmpSalary(32000);
		employeeResponse.setEmpDesignation("Trainer");
		when(restTemplate.getForEntity("http://localhost:8082/api/employees/1001", EmployeeResponse.class))
		.thenReturn(new ResponseEntity<EmployeeResponse>(employeeResponse,HttpStatus.OK));
		
		ResponseEntity<EmployeeResponse> responseEntity=
				restTemplate.getForEntity("http://localhost:8082/api/employees/1001", EmployeeResponse.class);
		
		EmployeeResponse response=responseEntity.getBody();
		assertEquals(1001,response.getEmpId());
		
	}
	@Test
	void testMockServerPositive() {
		
		EmployeeResponse employeeResponse=new EmployeeResponse();
		employeeResponse.setEmpId(1001);
		employeeResponse.setEmpName("Sabbir");
		employeeResponse.setEmpSalary(32000);
		employeeResponse.setEmpDesignation("Trainer");
		
		try {
			mockServer.expect(ExpectedCount.once(),
					requestTo(new URI("http://localhost:8082/api/employees/1001")))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(mapper.writeValueAsString(employeeResponse)));
			
			ResponseEntity<EmployeeResponse> responseEntity=
					template.getForEntity("http://localhost:8082/api/employees/1001", EmployeeResponse.class);
			EmployeeResponse response=responseEntity.getBody();
			assertEquals(1001,response.getEmpId());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
