package exceptions;

public class TickSpeedException extends IllegalArgumentException{
    public TickSpeedException() {
        super();
    }
    
    @Override
    public String getMessage() {
        return "Invalid tick speed";
    }
}
