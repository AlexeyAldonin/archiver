package ru.aldonin.archiver;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestProcessorTest {

    @Test
    public void checkIfRequestIsValid() {
        assertTrue(RequestProcessor.checkIfRequestIsValid("save -f -t -p -s"));
        assertFalse(RequestProcessor.checkIfRequestIsValid("sav -t -f -c -p"));
    }

    @Test
    public void checkForMatchingSave() {
        assertTrue(RequestProcessor.checkForMatchingSave("save -f -t -p -s"));
    }

    @Test
    public void checkForMatchingRetrieve() {
        assertTrue(RequestProcessor.checkForMatchingRetrieve("retrieve -f -t -p -s"));
    }

    @Test
    public void checkForMatchingGetSum() {
        assertTrue(RequestProcessor.checkForMatchingGetSum("csum -f"));
    }

    @Test
    public void checkForMatchingAddPassword() {
        assertTrue(RequestProcessor.checkForMatchingAddPassword("repas -f -p"));
    }
}