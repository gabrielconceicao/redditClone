package br.com.clone.reddit.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }

}
