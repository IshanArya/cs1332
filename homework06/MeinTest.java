import org.junit.Before;
import org.junit.Test;

public class MeinTest {

    private HashMap<Integer, String> map;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        map = new HashMap<>();
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");
        map.put(new Integer(3), "D");
        map.put(new Integer(4), "E");
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {

    }
}
