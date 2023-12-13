package org.example.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.LogInRequest;
import org.example.model.SuccessResponse;

public class LogInHandler implements RequestHandler<LogInRequest, SuccessResponse> {
    @Override
    public SuccessResponse handleRequest(LogInRequest input, Context context) {
        return new SuccessResponse().message("%s has successfully logged in".formatted(input.getUsername()));
    }
}
