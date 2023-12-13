package org.example.dao;

import com.vaticle.typedb.driver.TypeDB;
import com.vaticle.typedb.driver.api.TypeDBSession;
import com.vaticle.typedb.driver.api.TypeDBTransaction;
import com.vaticle.typedb.driver.api.answer.JSON;
import org.example.model.SubmissionItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vaticle.typeql.lang.TypeQL.*;

public class SubmissionDao {
    private final String address = "3.83.120.140:1729";

    public List<String> getSelectionsBySubmission(String submissionId) {
        var result = new ArrayList<String>();
        try (var driver = TypeDB.coreDriver(address);
             var session = driver.session("submission", TypeDBSession.Type.DATA);
             var transaction = session.transaction(TypeDBTransaction.Type.READ)) {

            var query =
                    match(
                            cVar("s").has("id", submissionId).isa("submission"),
                            cVar("sl").isa("selection"),
                            cVar("h")
                                    .rel("submission", cVar("s"))
                                    .rel("selection", cVar("sl"))
                                    .isa("has-selection")
                    ).fetch(
                            cVar("sl").map("selection-id")
                    );
            transaction.query().fetch(query).forEach(answer -> {
                var selectionId = answer.asObject().get("sl").asObject().get("selection-id").asArray().get(0).asObject().get("value").asString();
                result.add(selectionId);
            });
        }
        return result;
    }

    public List<SubmissionItem> getAllSubmissions() {
        var result = new ArrayList<SubmissionItem>();
        try (var driver = TypeDB.coreDriver(address);
             var session = driver.session("submission", TypeDBSession.Type.DATA);
             var transaction = session.transaction(TypeDBTransaction.Type.READ)) {

            var query =
                    match(
                            cVar("s").isa("submission")
                    ).fetch(
                            cVar("s").map("id").map("score").map("timestamp")
                    );
            transaction.query().fetch(query).forEach(answer -> {
                var obj = answer.asObject().get("s").asObject();
                var id = obj.get("id").asArray().get(0).asObject().get("value").asString();
                var selections = getSelectionsBySubmission(id);
                var score = (int) (obj.get("score").asArray().get(0).asObject().get("value").asNumber());
                var timestamp = (int) (obj.get("timestamp").asArray().get(0).asObject().get("value").asNumber());
                result.add(new SubmissionItem().id(id).score(score).timestamp(timestamp).selections(selections));
            });
        }
        return result;
    }

}
