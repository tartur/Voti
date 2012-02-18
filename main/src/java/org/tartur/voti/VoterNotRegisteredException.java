package org.tartur.voti;

/**
 * An exception that should be thrown when trying to find a voter that is not registered.
 * User: user
 * Date: 19/02/12
 * Time: 00:01
 */
public class VoterNotRegisteredException extends Exception {
    public VoterNotRegisteredException() {
    }

    public VoterNotRegisteredException(String message) {
        super(message);
    }

    public VoterNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoterNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
