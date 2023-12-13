package org.example.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.SignUpRequest;
import org.example.model.SuccessResponse;

public class SignUpHandler implements RequestHandler<SignUpRequest, SuccessResponse> {
    @Override
    public SuccessResponse handleRequest(SignUpRequest input, Context context) {
        return new SuccessResponse().message("%s has successfully signed up".formatted(input.getUsername()));
    }
}
