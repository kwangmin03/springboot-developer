import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUniTest {
    @Test
    public void juniTest() {
        int n1 = 1;
        int n2 = 2;

        int sum = n1 + n2;

        Assertions.assertEquals(3, sum);
    }
}
