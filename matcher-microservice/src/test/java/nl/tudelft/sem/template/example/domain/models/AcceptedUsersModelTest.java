package nl.tudelft.sem.template.example.domain.models;

import nl.tudelft.sem.template.example.domain.transferObject.TransferMatch;
import org.checkerframework.checker.units.qual.A;
import org.h2.value.Transfer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AcceptedUsersModelTest {

    @Test
    void getTransferMatches() {
        AcceptedUsersModel acceptedUsersModel = new AcceptedUsersModel();
        TransferMatch transferMatch = Mockito.mock(TransferMatch.class);
        acceptedUsersModel.setTransferMatches(List.of(transferMatch));
        assertEquals(acceptedUsersModel.getTransferMatches(),List.of(transferMatch));
    }

    @Test
    void setTransferMatches() {
    }

    @Test
    void testEquals() {
        AcceptedUsersModel acceptedUsersModel1 = new AcceptedUsersModel();
        AcceptedUsersModel acceptedUsersModel2 = new AcceptedUsersModel();
        TransferMatch transferMatch = Mockito.mock(TransferMatch.class);
        acceptedUsersModel1.setTransferMatches(List.of(transferMatch));
        acceptedUsersModel2.setTransferMatches(List.of(transferMatch));
        assertEquals(acceptedUsersModel1, acceptedUsersModel2);

    }

    @Test
    void canEqual() {
        AcceptedUsersModel acceptedUsersModel1 = new AcceptedUsersModel();
        AcceptedUsersModel acceptedUsersModel2 = new AcceptedUsersModel();
        assertTrue(acceptedUsersModel1.canEqual(acceptedUsersModel2));

    }

}