package org.example.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.dao.SubmissionDao;
import org.example.model.SubmissionItem;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class GetAllSubmissionsHandler implements RequestHandler<Void, List<SubmissionItem>> {
    @Override
    public List<SubmissionItem> handleRequest(Void input, Context context) {
        /*String redisEndpoint = "3.83.120.140";
        try (Jedis jedis = new Jedis(redisEndpoint, 6379)) {
            return jedis.hgetAll("submissions").values().stream().map(v -> {
                try {
                    return SubmissionItem.fromJson(v);
                } catch (IOException e) {
                    return null;
                }
            }).filter(Objects::nonNull).toList();
        } catch (Exception e) {
            throw e;
        }*/
        return new SubmissionDao().getAllSubmissions();
    }
}
