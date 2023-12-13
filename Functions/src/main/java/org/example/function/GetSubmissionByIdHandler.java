package org.example.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.SubmissionItem;

import java.util.List;

public class GetSubmissionByIdHandler implements RequestHandler<String, SubmissionItem> {
    @Override
    public SubmissionItem handleRequest(String id, Context context) {
        return new SubmissionItem()
                .id(id)
                .score(9)
                .timestamp(1654625741)
                .selections(List.of("001_01", "001_02", "002_09", "002_10", "002_11", "004_01", "004_07"));
    }
}
