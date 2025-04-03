package com.java.model;

import java.util.Objects;

public class ResponseCode {
    private Long id;
    private String errorCode;
    private String errorDescription;
    private String errorLevel;

    public ResponseCode() {
    }

    public ResponseCode(Long id, String errorCode, String errorDescription, String errorLevel) {
        this.id = id;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorLevel = errorLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        if (errorCode == null) {
            throw new IllegalArgumentException("ErrorCode не может быть нулем");
        }
        if (errorCode.length() != 2) {
            throw new IllegalArgumentException("ErrorCode должен содержать ровно 2 символа.");
        }
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        if (errorDescription == null) {
            throw new IllegalArgumentException("errorDescription не может быть нулем");
        }
        if (errorDescription.length() > 255) {
            throw new IllegalArgumentException("errorDescription не может содержать больше  255 символов.");
        }
        this.errorDescription = errorDescription;
    }

    public String getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(String errorLevel) {
        if (errorLevel == null) {
            throw new IllegalArgumentException("errorLevel не может быть нулем");
        }
        if (errorLevel.length() > 255) {
            throw new IllegalArgumentException("errorLevel не может содержать больше  255 символов.");
        }
        this.errorLevel = errorLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResponseCode that = (ResponseCode) o;
        return Objects.equals(errorCode, that.errorCode) && Objects.equals(id, that.id) && Objects.equals(errorDescription, that.errorDescription) && Objects.equals(errorLevel, that.errorLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, errorCode, errorDescription, errorLevel);
    }


    @Override
    public String toString() {
        return "ResponseCode{" +
                "id=" + id +
                ", errorCode=" + errorCode +
                ", errorDescription='" + errorDescription + '\'' +
                ", errorLevel='" + errorLevel + '\'' +
                '}';
    }
}
