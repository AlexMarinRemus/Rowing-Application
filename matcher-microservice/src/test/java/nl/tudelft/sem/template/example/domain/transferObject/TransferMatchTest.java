package nl.tudelft.sem.template.example.domain.transferObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferMatchTest {

    @Test
    void getTransferMatch() {
        TransferMatch t= new TransferMatch(1L,"cox","12-03-2027","user","owner");
        assertEquals(1L, (long) t.getActivityId());
        assertEquals("owner", t.getOwner());
        assertEquals("user", t.getNetId());
        assertEquals("12-03-2027", t.getTimeSlot());
        assertEquals("cox", t.getPosition());
    }
    @Test
    void constructorsTest() {
        TransferMatch t= new TransferMatch(1L,"cox","12-03-2027","user","owner");
        assertNotNull(new TransferMatch());
        assertNotNull(t);
    }
    @Test
    void settersTest() {
        TransferMatch t= new TransferMatch();
        t.setActivityId(1L);
        t.setOwner("owner");
        t.setNetId("user");
        t.setPosition("cox");
        t.setTimeSlot("12-03-2027");
        assertEquals(1L, (long) t.getActivityId());
        assertEquals("owner", t.getOwner());
        assertEquals("user", t.getNetId());
        assertEquals("12-03-2027", t.getTimeSlot());
        assertEquals("cox", t.getPosition());

    }
}