package org.labcrypto.avicenna;

public class AvicennaException extends Exception {
    public AvicennaException() {
    }

    public AvicennaException(String message) {
        super(message);
    }

    public AvicennaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvicennaException(Throwable cause) {
        super(cause);
    }

    public AvicennaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
