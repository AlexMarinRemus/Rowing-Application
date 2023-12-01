package nl.tudelft.sem.template.authentication.domain.user;

/**
 * Exception to indicate the NetID is already in use.
 */
public class NetIdAlreadyInUseException extends Exception {
    static final long serialVersionUID = -3387516993124229948L;

    /**
     * Constructor for the NetIdAlreadyInUseException.
     * @param netId
     */
    public NetIdAlreadyInUseException(NetId netId) {
        super(netId.toString());
    }
}
