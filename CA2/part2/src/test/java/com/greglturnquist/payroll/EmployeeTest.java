package com.greglturnquist.payroll;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee("Dave", "Davidson", "Description", "Daveloper", 1, "myprecious@gmail.com");
        assertNotNull(employee);
        assertEquals("Dave", employee.getFirstName());
        assertEquals("Davidson", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Daveloper", employee.getJobTitle());
        assertEquals(1, employee.getJobYears());
        assertEquals("myprecious@gmail.com", employee.getEmail());
    }

    @Test
    void testExceptionThrownWhenCreatingEmployeeWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(null, "Davidson", "Description", "Daveloper", 1, "myprecious@gmail.com");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", null, "Description", "Daveloper", 1, "myprecious@gmail.com");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", "Davidson", null, "Daveloper", 1, "myprecious@gmail.com");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", "Davidson", "Description", null, 1,"myprecious@gmail.com");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", "Davidson", "Description", "Daveloper", -1,"myprecious@gmail.com");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", "Davidson", "Description", "Daveloper", 1, null);
        });
    }

    @Test
    void testEmployeeValidation() {
        Employee employee = new Employee();
        assertFalse(employee.validateArguments(null, "Davidson", "Description", "Daveloper",1,"myprecious@gmail.com"));
        assertFalse(employee.validateArguments("Dave", null, "Description", "Daveloper",1,"myprecious@gmail.com"));
        assertFalse(employee.validateArguments("Dave", "Davidson", null, "Daveloper",1,"myprecious@gmail.com"));
        assertFalse(employee.validateArguments("Dave", "Davidson", "Description", null,1,"myprecious@gmail.com"));
        assertFalse(employee.validateArguments("Dave", "Davidson", "Description", "Daveloper", -1,"myprecious@gmail.com"));
        assertFalse(employee.validateArguments("Dave", "Davidson", "Description", "Daveloper",1, null));

    }

    @Test
    void testEmployeeEquality() {
        Employee employee1 = new Employee("Dave", "Davidson", "Description", "Daveloper",1,"myprecious@gmail.com");
        Employee employee2 = new Employee("Dave", "Davidson", "Description", "Daveloper",1,"myprecious@gmail.com");
        assertEquals(employee1, employee2);
    }

    @Test
    void testEmployeeToString() {
        Employee employee = new Employee("Dave", "Davidson", "Description", "Daveloper",1,"myprecious@gmail.com");
        String expectedString = "Employee{id=null, firstName='Dave', lastName='Davidson', description='Description', jobTitle='Daveloper', jobYears='1', email='myprecious@gmail.com'}";
        assertEquals(expectedString, employee.toString());
    }

    @Test
    void testEmployeeSettersAndGetters() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Dave");
        employee.setLastName("Davidson");
        employee.setDescription("Description");
        employee.setJobTitle("Daveloper");
        employee.setJobYears(1);
        employee.setEmail("myprecious@gmail.com");
        assertEquals(1L, employee.getId());
        assertEquals("Dave", employee.getFirstName());
        assertEquals("Davidson", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Daveloper", employee.getJobTitle());
        assertEquals(1, employee.getJobYears());
        assertEquals("myprecious@gmail.com", employee.getEmail());
    }

    @Test
    void testEmployeeHashCode() {
        Employee employee1 = new Employee("Dave", "Davidson", "Description", "Daveloper",1,"myprecious@gmail.com");
        Employee employee2 = new Employee("Dave", "Davidson", "Description", "Daveloper", 1, "myprecious@gmail.com");
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }

    @Test
    void testInvalidEmail_shouldReturnFalse() {
        Employee employee = new Employee();
        assertFalse(employee.validateArguments("John", "Doe", "Description", "Developer", 5, "john"));
    }
}
 

