package nl.tudelft.sem.template.authentication.domain.user;

import org.springframework.stereotype.Service;

/**
 * A DDD service for registering a new user.
 */
@Service
public class RegistrationService {
    private final transient UserRepository userRepository;
    private final transient PasswordHashingService passwordHashingService;

    /**
     * Instantiates a new UserService.
     *
     * @param userRepository  the user repository
     * @param passwordHashingService the password encoder
     */
    public RegistrationService(UserRepository userRepository, PasswordHashingService passwordHashingService) {
        this.userRepository = userRepository;
        this.passwordHashingService = passwordHashingService;
    }

    /**
     * Register a new user.
     *
     * @param netId    The NetID of the user
     * @param password The password of the user
     * @throws Exception if the user already exists
     */
    public AppUser registerUser(NetId netId, Password password) throws Exception {

        if (checkNetIdIsUnique(netId) && checkNetIdIsValid(netId) && checkPasswordIsValid(password)) {
            // Hash password
            HashedPassword hashedPassword = passwordHashingService.hash(password);

            // Create new account
            AppUser user = new AppUser(netId, hashedPassword);
            userRepository.save(user);

            return user;
        } else if (!checkNetIdIsUnique(netId))
            throw new NetIdAlreadyInUseException(netId);
        else if (!checkNetIdIsValid(netId))
            throw new NetIdIsInvalidException(netId);
        else if(!checkPasswordIsValid(password))
            throw new PasswordIsInvalidException();
        else
            throw new Exception("Something went wrong");
    }

    /**
     * Check if the password is valid.
     * @param password
     * @return true if the password is valid
     */
    public boolean checkPasswordIsValid(Password password) {
        return password.isValid();
    }

    /**
     * Check if the NetID is unique.
     * @param netId
     * @return true if the NetID is unique
     */
    public boolean checkNetIdIsUnique(NetId netId) {
        return !userRepository.existsByNetId(netId);
    }

    /**
     * Check if the NetID is valid.
     * @param netId
     * @return true if the NetID is valid
     */
    public boolean checkNetIdIsValid(NetId netId){
        return netId.isValid();
    }
}
