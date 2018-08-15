package com.glodon.tot.exception;

import org.springframework.stereotype.Service;

@Service
public class ServerBusyException extends Exception {
    public ServerBusyException() {
    }

    public ServerBusyException(String message) {
        super(message);
    }

    public ServerBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerBusyException(Throwable cause) {
        super(cause);
    }

    public ServerBusyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
