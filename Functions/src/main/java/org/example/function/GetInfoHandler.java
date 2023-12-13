package org.example.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.GetUserInfoResponse;

public class GetInfoHandler implements RequestHandler<String, GetUserInfoResponse> {
    @Override
    public GetUserInfoResponse handleRequest(String input, Context context) {
        context.getLogger().log("Get user %s's info".formatted(input));
        if ("a".equals(input)) {
            throw new IllegalArgumentException("User not found");
        }
        return new GetUserInfoResponse().email("example@demo.com").username(input);
    }
}
