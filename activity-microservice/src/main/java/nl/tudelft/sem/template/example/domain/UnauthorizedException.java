package nl.tudelft.sem.template.example.domain;

public class UnauthorizedException extends Exception {
    private static final long serialVersionUID = 1;

    /**
     * Constructor for the UnauthorizedException.
     * @param s
     */
    public UnauthorizedException(String s) {
        super(s);
    }
}
