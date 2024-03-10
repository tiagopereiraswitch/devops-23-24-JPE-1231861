package com.greglturnquist.payroll;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {
    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee("Dave", "Davidson", "Description", "Daveloper");
        assertNotNull(employee);
        assertEquals("Dave", employee.getFirstName());
        assertEquals("Davidson", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Daveloper", employee.getJobTitle());
    }

    @Test
    void testExceptionThrownWhenCreatingEmployeeWithNullArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee(null, "Davidson", "Description", "Daveloper");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", null, "Description", "Daveloper");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", "Davidson", null, "Daveloper");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Employee("Dave", "Davidson", "Description", null);
        });
    }

    @Test
    void testEmployeeValidation() {
        Employee employee = new Employee();
        assertFalse(employee.validateArguments(null, "Davidson", "Description", "Daveloper"));
        assertFalse(employee.validateArguments("Dave", null, "Description", "Daveloper"));
        assertFalse(employee.validateArguments("Dave", "Davidson", null, "Daveloper"));
        assertFalse(employee.validateArguments("Dave", "Davidson", "Description", null));
        assertTrue(employee.validateArguments("Dave", "Davidson", "Description", "Daveloper"));
    }

    @Test
    void testEmployeeEquality() {
        Employee employee1 = new Employee("Dave", "Davidson", "Description", "Daveloper");
        Employee employee2 = new Employee("Dave", "Davidson", "Description", "Daveloper");
        assertEquals(employee1, employee2);
    }

    @Test
    void testEmployeeToString() {
        Employee employee = new Employee("Dave", "Davidson", "Description", "Daveloper");
        String expectedString = "Employee{id=null, firstName='Dave', lastName='Davidson', description='Description', jobTitle='Daveloper'}";
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
        assertEquals(1L, employee.getId());
        assertEquals("Dave", employee.getFirstName());
        assertEquals("Davidson", employee.getLastName());
        assertEquals("Description", employee.getDescription());
        assertEquals("Daveloper", employee.getJobTitle());
    }

    @Test
    void testEmployeeHashCode() {
        Employee employee1 = new Employee("Dave", "Davidson", "Description", "Daveloper");
        Employee employee2 = new Employee("Dave", "Davidson", "Description", "Daveloper");
        assertEquals(employee1.hashCode(), employee2.hashCode());
    }
}
 

