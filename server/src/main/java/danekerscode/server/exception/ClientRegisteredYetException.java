package danekerscode.server.exception;

public class ClientRegisteredYetException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     */
    public ClientRegisteredYetException() {
        super("client registered yet");
    }
}
