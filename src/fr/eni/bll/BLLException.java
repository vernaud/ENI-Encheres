package fr.eni.bll;

public class BLLException extends Exception{
    public BLLException(String msg) {
        super(msg);
    }

    public BLLException(String message, Throwable cause) {
        super(message, cause);
    }

    public BLLException(String message, String message1) {

    }
}