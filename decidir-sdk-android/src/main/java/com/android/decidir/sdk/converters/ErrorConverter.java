package com.android.decidir.sdk.converters;

import com.android.decidir.sdk.dto.DecidirError;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by biandra on 04/08/16.
 */
public class ErrorConverter {

    public DecidirResponse<DecidirError> convert(Response response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DecidirResponse<DecidirError> dResponse = new DecidirResponse();
        dResponse.setStatus(response.code());
        dResponse.setResult(objectMapper.readValue(response.errorBody().string(), DecidirError.class));
        dResponse.setMessage(response.message());
        return dResponse;
    }
}
