package Exceptions;

public class NoValueFoundException extends RuntimeException{

    public NoValueFoundException(){
        super("No encontro el valor en la tabla");
    }
}
