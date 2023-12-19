package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.DemoResponse;

public class DemoHandler implements RequestHandler<Integer, DemoResponse> {
    @Override
    public DemoResponse handleRequest(Integer input, Context context) {

        return new DemoResponse().id(100).code("202").message("Hello");
    }
}
