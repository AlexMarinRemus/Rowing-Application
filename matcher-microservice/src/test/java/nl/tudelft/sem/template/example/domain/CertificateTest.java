package nl.tudelft.sem.template.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateTest {

    @Test
    void isValid() {
        Certificate certificate= new Certificate("C4");
        assertTrue(certificate.isValid());
    }

    @Test
    void NotValid() {
        Certificate certificate= new Certificate("C1");
        assertFalse(certificate.isValid());
    }

    @Test
    void isBetterCertificateSame() {
        Certificate certificate= new Certificate("C4");
        Certificate certificate1= new Certificate("C4");
        assertTrue(certificate1.isBetterCertificate(certificate));
    }

    @Test
    void isBetterCertificate() {
        Certificate certificate= new Certificate("C4");
        Certificate certificate1= new Certificate("4+");
        assertTrue(certificate1.isBetterCertificate(certificate));
    }

    @Test
    void isNotBetterCertificate() {
        Certificate certificate= new Certificate("C4");
        Certificate certificate1= new Certificate("4+");
        assertFalse(certificate.isBetterCertificate(certificate1));
    }

    @Test
    void testToString() {
        String s="C4";
        Certificate certificate= new Certificate(s);
        assertTrue(certificate.toString().equals(s));
    }
}