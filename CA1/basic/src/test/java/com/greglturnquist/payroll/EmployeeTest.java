package com.greglturnquist.payroll;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeTest {

    @Test

    public void testEmployeeNotNull() {
        // Arrange + Act
        Employee employee = new Employee("Bilbo", "Baggins", "ring bearer", 50);
        // Assert
        assertNotNull(employee.getFirstName());
        assertNotNull(employee.getLastName());
        assertNotNull(employee.getDescription());
        assertNotNull(employee.getJobYears());
    }

    @Test
    public void testEmployeeSetNamesDescJobYears() {
        // Arrange + Act
        Employee employee = new Employee("Bilbo", "Baggins", "ring bearer", 50);
        employee.setFirstName("Frodo");
        employee.setLastName("Fernandes");
        employee.setDescription("Unemployed");
        employee.setJobYears(0);
        // Assert
        assertEquals("Frodo", employee.getFirstName());
        assertEquals("Fernandes", employee.getLastName());
        assertEquals("Unemployed", employee.getDescription());
        assertEquals(0, employee.getJobYears());
    }

    @Test
    public void testEmployeeId() {
        // Arrange + Act
        Employee employee = new Employee("Bilbo", "Baggins", "ring bearer", 50);

        // Assert
        employee.setId(4L);
        assertEquals(4L, employee.getId().longValue());
    }

}
