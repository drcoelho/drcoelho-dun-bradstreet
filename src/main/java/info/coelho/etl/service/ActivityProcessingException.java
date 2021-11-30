package info.coelho.etl.service;

public class ActivityProcessingException extends RuntimeException {

    public ActivityProcessingException(Throwable e) {
        super(e);
    }

    public ActivityProcessingException(String message) {
        super(message);
    }

}
