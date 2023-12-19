package org.example.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.model.SubmissionItem;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GetSubmissionByIdHandler implements RequestHandler<String, SubmissionItem> {
    @Override
    public SubmissionItem handleRequest(String id, Context context) {
        String redisEndpoint = "3.83.120.140";
        SubmissionItem result = null;
        try (Jedis jedis = new Jedis(redisEndpoint, 6379)) {
            var s = jedis.hget("submissions", id);
            result = SubmissionItem.fromJson(s);
        } catch (Exception e) {
        }
//        return new SubmissionItem()
//                .id(id)
//                .score(9)
//                .timestamp(1654625741)
//                .selections(List.of("001_01", "001_02", "002_09", "002_10", "002_11", "004_01", "004_07"));
        return result;
    }
}
