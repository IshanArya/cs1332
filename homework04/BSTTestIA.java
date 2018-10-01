import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BSTTestIA {
    static final int TIMEOUT = 200;
    private BST<Integer> bst;

    @Before
    public void setUp() throws Exception {
        bst = new BST<>();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveWithOneChild() {
        bst.add(3);
        bst.add(6);

        assertEquals(new Integer(3), bst.remove(3));
        assertEquals(new Integer(6), bst.getRoot().getData());
    }

    @After
    public void tearDown() throws Exception {
    }
}
