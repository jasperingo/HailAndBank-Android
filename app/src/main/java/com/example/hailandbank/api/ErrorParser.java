package com.example.hailandbank.api;

import com.example.hailandbank.utils.InputData;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


public class ErrorParser {

    public static Result.Success<Map<String, InputData>> parseInputData(Response<?> response) {
        Converter<ResponseBody, Result.Success<Map<String, InputData>>> converter =
                Api.getConnection()
                        .responseBodyConverter(Result.Success.class, new Annotation[0]);

        try {
            if (response.errorBody() == null) throw new IOException();
            return converter.convert(response.errorBody());
        } catch (IOException e) {
            return new Result.Success<>();
        }
    }

    public static Result.Success<Void> parseVoid(Response<?> response) {
        Converter<ResponseBody, Result.Success<Void>> converter =
                Api.getConnection()
                        .responseBodyConverter(Result.Success.class, new Annotation[0]);

        try {
            if (response.errorBody() == null) throw new IOException();
            return converter.convert(response.errorBody());
        } catch (IOException e) {
            Result.Success<Void> err = new Result.Success<>();
            err.setMessage(e.getMessage());
            return err;
        }
    }

    public static Result.Error<Void> parseError(Response<?> response) {
        Result.Success<Void> r = parseVoid(response);
        return new Result.Error<>(new Exception(r.getMessage()));
    }

}

