package br.com.clone.reddit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SubredditNotFoundException extends RuntimeException {

    public SubredditNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
