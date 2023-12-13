package org.example.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.vaticle.typedb.driver.TypeDB;
import com.vaticle.typedb.driver.api.TypeDBSession;
import com.vaticle.typedb.driver.api.TypeDBTransaction;
import com.vaticle.typeql.lang.pattern.statement.ThingStatement;
import org.example.model.SubmissionItem;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.*;
import static com.vaticle.typeql.lang.TypeQL.cVar;

public class PostSubmissionHandler implements RequestHandler<SubmissionItem, String> {
    @Override
    public String handleRequest(SubmissionItem input, Context context) {
        String redisEndpoint = "3.83.120.140";
        try (Jedis jedis = new Jedis(redisEndpoint, 6379)) {
            jedis.hset("submissions", input.getId(), input.toJson());
        } catch (Exception e) {
            throw e;
        }

        return "oh yeah";
    }

    public void saveSubmissionToTypeDB(SubmissionItem item) {
        try (var driver = TypeDB.coreDriver("localhost:1729");
             var session = driver.session("submission", TypeDBSession.Type.DATA);
             var transaction = session.transaction(TypeDBTransaction.Type.WRITE)) {

            var selections = IntStream.range(1, 1 + item.getSelections().size()).mapToObj(index ->
                    List.of(
                            cVar("sl%d".formatted(index))
                                    .isa("selection")
                                    .has("selection-id", "%d".formatted(index)),
                            cVar("r%d".formatted(index))
                                    .rel("submission", cVar("s"))
                                    .rel("selection", cVar("sl%d".formatted(index)))
                                    .isa("has-selection")
                    )).flatMap(List::stream).toList();

            List<ThingStatement<?>> list = new ArrayList<>(selections);
            list.add(cVar("s").isa("submission")
                    .has("id", item.getId())
                    .has("score", item.getScore())
                    .has("timestamp", item.getTimestamp()));

            var query = insert(list);
            transaction.query().insert(query);
            transaction.commit();
        }
    }
}
