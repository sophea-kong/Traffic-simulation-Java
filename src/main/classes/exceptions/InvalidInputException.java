package exceptions;

public class InvalidInputException extends IllegalArgumentException{
    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Object value) {
        super(message+" Invalid value: " + value);
    }

    
    @Override
    public String getMessage() {
        return super.getMessage() + " Please enter a valid input.";
    }
}
