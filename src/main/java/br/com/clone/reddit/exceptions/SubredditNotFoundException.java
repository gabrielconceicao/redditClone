package br.com.clone.reddit.exceptions;

public class SubredditNotFoundException extends RuntimeException {

    public SubredditNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
