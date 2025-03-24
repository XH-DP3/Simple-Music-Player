package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class EventTest {
    private Event event;
    private Date date;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @Before
    public void runBefore() {
        event = new Event("Sensor open at door"); // (1)
        date = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", event.getDescription());
        assertTrue(date.getTime() - event.getDate().getTime() < 0.001);
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Sensor open at door", event.toString());
    }

    @Test
    public void testHashcode() {
        event.hashCode();
    }

    @Test
    public void testEquals() {
        Event e2 = new Event("Another event");
        Event e3 = null;
        assertFalse(event.equals(e2));
        assertFalse(event.equals(e3));
        assertFalse(event.equals(new Object()));
    }
}
