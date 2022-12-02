package com.example.socialmediachallange.exception;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;
    private final String errorMessage;

    public GenericException(Builder builder) {
        this.httpStatus = builder.httpStatus;
        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private HttpStatus httpStatus;
        private ErrorCode errorCode;
        private String errorMessage;

        private Builder() {
        }


        public Builder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder errorCode(ErrorCode errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public GenericException build() {
            return new GenericException(this);
        }
    }
}
