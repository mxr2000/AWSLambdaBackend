import com.vaticle.typedb.driver.TypeDB;
import com.vaticle.typedb.driver.api.TypeDBDriver;
import com.vaticle.typedb.driver.api.TypeDBOptions;
import com.vaticle.typedb.driver.api.TypeDBSession;
import com.vaticle.typedb.driver.api.TypeDBTransaction;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLFetch;
import com.vaticle.typeql.lang.query.TypeQLGet;
import org.example.function.PostSubmissionHandler;
import org.example.model.SubmissionItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.vaticle.typeql.lang.TypeQL.*;

public class TypeDBDemo {
    @Test
    public void testGet() {
        try (var driver = TypeDB.coreDriver("localhost:1729");
             var session = driver.session("file", TypeDBSession.Type.DATA);
             var transaction = session.transaction(TypeDBTransaction.Type.WRITE)) {

            var query =
                    match(
                            cVar("f").isa("file")
                                    .has("path", "README.md"),
                            cVar("u").isa("user"),
                            rel(cVar("u")).rel(cVar("f")).isa("permission")
                    ).get(cVar("u"));
            transaction.query().get(query).forEach(answer -> {
                answer.explainables().attributes().forEach(a -> System.out.println(a));
            });

            transaction.commit();
        }
    }

    @Test
    public void testFetch() {
        try (var driver = TypeDB.coreDriver("localhost:1729");
             var session = driver.session("file", TypeDBSession.Type.DATA);
             var transaction = session.transaction(TypeDBTransaction.Type.WRITE)) {
            var query =
                    match(
                            cVar("f").isa("file")
                                    .has("path", "README.md"),
                            cVar("u").isa("user"),
                            rel(cVar("u")).rel(cVar("f")).isa("permission")
                    ).fetch(
                            cVar("u").map("username").map("email"),
                            cVar("f").map("path").map("size-kb")
                    );
            transaction.query().fetch(query).forEach(answer -> {
                System.out.println(answer);
            });

            transaction.commit();
        }
    }

    @Test
    public void testInsert() {
        var item = new SubmissionItem().id("123").score(100).timestamp(10000).selections(List.of("1", "2", "3"));
        new PostSubmissionHandler().saveSubmissionToTypeDB(item);
    }
}
