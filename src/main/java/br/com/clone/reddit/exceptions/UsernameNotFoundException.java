package br.com.clone.reddit.exceptions;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
