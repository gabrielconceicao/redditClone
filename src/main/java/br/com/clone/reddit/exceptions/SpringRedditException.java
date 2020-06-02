package br.com.clone.reddit.exceptions;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {

    public SpringRedditException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
