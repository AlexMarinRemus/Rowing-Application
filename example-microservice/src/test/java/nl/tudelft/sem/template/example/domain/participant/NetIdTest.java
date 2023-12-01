package nl.tudelft.sem.template.example.domain.participant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetIdTest {

    @Test
    void testToString() {
        String s="netId";
        NetId netId= new NetId(s);
        assertTrue(netId.toString().equals(s));
    }
}