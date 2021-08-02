package com.example.hailandbank.api;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class Result<T> {

    private Result() {
    }

    @NotNull
    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Result.Success<T> success = (Result.Success<T>) this;
            return "Success[status="+success.getStatus()+" data=" + success.getMessage() + "]";
        } else if (this instanceof Result.Error) {
            Result.Error<T> error = (Result.Error<T>) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "void(0)";
    }

    // Success sub-class
    public final static class Success<T> extends Result<T> {
        private String status;

        private String message;

        private T data;

        private List<T> dataList;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public List<T> getDataList() {
            return dataList;
        }

        public void setDataList(List<T> dataList) {
            this.dataList = dataList;
        }

    }

    // Error sub-class
    public final static class Error<T> extends Result<T> {

        private final Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }

        public String getMessage() { return this.error.getMessage(); }
    }
}

