import org.junit.jupiter.api.Assertions;
import ru.stepup.task.Account;
import ru.stepup.task.TestRun;

import java.lang.reflect.InvocationTargetException;

public class TestTest {
    @org.junit.jupiter.api.Test
    public void  testName() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        TestRun tstrn = new TestRun();
        tstrn.run(TestAccount.class);
    }
}
