package app.exception;

public class InvalidTLSHandshake extends RuntimeException
{
    public InvalidTLSHandshake(String message)
    {
        super(message);
    }

    public InvalidTLSHandshake(String message, Throwable cause)
    {
        super(message, cause);
    }
}