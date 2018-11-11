import org.junit.Before;
import org.junit.Test;

public class TestIA {

    AVL<Integer> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test
    public void test1() {
        avlTree.add(198);
        System.out.println(avlTree.getRoot().getHeight());
        System.out.println(avlTree.getRoot().getBalanceFactor());
    }
}
