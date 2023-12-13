import org.example.dao.SubmissionDao;
import org.example.function.PostSubmissionHandler;
import org.example.model.SubmissionItem;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SubmissionDaoTests {

    @Test
    public void testGetAllSubmissions() {
        new PostSubmissionHandler().saveSubmissionToTypeDB(
                new SubmissionItem().id("12345").score(100).timestamp(12345).selections(List.of("1", "2", "3")));
    }
}
