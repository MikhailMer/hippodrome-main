import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Test
    @Timeout(22)
    @Disabled("Run manually to check 22-second limit")
    void main_executesUnder22Seconds() throws Exception {
        Main.main(new String[0]);
    }
}