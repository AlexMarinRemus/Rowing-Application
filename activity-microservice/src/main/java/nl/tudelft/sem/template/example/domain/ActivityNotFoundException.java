package nl.tudelft.sem.template.example.domain;

public class ActivityNotFoundException extends Exception {
    private static final long serialVersionUID = 1;

    /**
     * Constructor for the ActivityNotFoundException.
     * @param id
     */
    public ActivityNotFoundException(long id) {
        super("Activity with id " + id + " not found");
    }

    /**
     * Constructor for the ActivityNotFoundException.
     * @param s
     */
    public ActivityNotFoundException(String s) {
        super(s);
    }
}

