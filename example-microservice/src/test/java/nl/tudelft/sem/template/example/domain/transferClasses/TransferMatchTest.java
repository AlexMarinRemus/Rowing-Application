package nl.tudelft.sem.template.example.domain.transferClasses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferMatchTest {

    @Test
    void getTransferMatch() {
        TransferMatch t= new TransferMatch(1L,"cox","12-03-2027","user","owner");
        assertTrue(t.getActivityId().equals(1L));
        assertTrue(t.getOwner().equals("owner"));
        assertTrue(t.getNetId().equals("user"));
        assertTrue(t.getTimeSlot().equals("12-03-2027"));
        assertTrue(t.getPosition().equals("cox"));
    }
}