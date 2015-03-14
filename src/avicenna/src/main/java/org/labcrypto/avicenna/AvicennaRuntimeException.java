package org.labcrypto.avicenna;

public class AvicennaRuntimeException extends RuntimeException {
    public AvicennaRuntimeException() {
    }

    public AvicennaRuntimeException(String message) {
        super(message);
    }

    public AvicennaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AvicennaRuntimeException(Throwable cause) {
        super(cause);
    }

    public AvicennaRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
