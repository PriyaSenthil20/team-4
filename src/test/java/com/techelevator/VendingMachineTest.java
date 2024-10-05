package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.MethodName.class)

public class VendingMachineTest {

    //Is Valid Product ID Tests
    @Test
    public void testIsValidProductID_ValidID() {
        // Arrange
        VendingMachine vm = new VendingMachine();

        // Act/Assert
        assertTrue(vm.isValidProductId("A1"));
    }

    @Test
    public void testIsValidProductID_ValidIDLowercase() {
        // Arrange
        VendingMachine vm = new VendingMachine();

        // Act/Assert
        assertTrue(vm.isValidProductId("d3"));
    }

    @Test
    public void testIsValidProductID_InvalidID() {
        // Arrange
        VendingMachine vm = new VendingMachine();

        // Act/Assert
        assertFalse(vm.isValidProductId("cat"));
    }

}
