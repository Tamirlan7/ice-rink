package by.tami.ticketservice.exception;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String s) {
        super(s);
    }
}
