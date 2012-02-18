package org.tartur.voti;

/**
 * This exception is thrown when the voter has already voted. A voter can vote only once.
 * User: user
 * Date: 18/02/12
 * Time: 19:12
 */
public class HasAlreadyVotedException extends Exception {
    public HasAlreadyVotedException() {
    }

    public HasAlreadyVotedException(String message) {
        super(message);
    }

    public HasAlreadyVotedException(String message, Throwable cause) {
        super(message, cause);
    }

    public HasAlreadyVotedException(Throwable cause) {
        super(cause);
    }
}
