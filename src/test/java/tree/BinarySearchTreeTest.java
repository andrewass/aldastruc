package tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    private BinarySearchTree<Integer, WrapperClass> tree;
    private List<WrapperClass> wrapperList;

    @BeforeEach
    public void setup() {
        tree = createBinarySearchTree();
    }

    @Test
    public void treeShouldBeABinarySearchTree() {
        var inOrderList = tree.inOrderList();
        assertTrue(isInOrder(inOrderList));
    }

    @Test
    public void shouldBeAbleToLookUpNodesInTree() {
        assertEquals(5, tree.get(5).getPrime());
        assertEquals(13, tree.get(13).getPrime());
        assertEquals(41, tree.get(41).getPrime());
        assertNull(tree.get(44));
    }

    @Test
    public void treeShouldBeInCorrectOrderAfterDeletes() {
        tree.delete(wrapperList.get(1).getPrime());
        tree.delete(wrapperList.get(5).getPrime());
        tree.delete(wrapperList.get(11).getPrime());

        var inOrderList = tree.inOrderList();

        assertTrue(isInOrder(inOrderList));
        assertEquals(inOrderList.size(), 12);
        assertNull(tree.get(wrapperList.get(1).getPrime()));
        assertNull(tree.get(wrapperList.get(5).getPrime()));
        assertNull(tree.get(wrapperList.get(11).getPrime()));
    }

    @Test
    public void shouldFindCorrectMinimumValue() {
        assertEquals(tree.minimum().getPrime(), 2);
    }

    @Test
    public void shouldFindCorrectMaximumValue() {
        assertEquals(tree.maximum().getPrime(), 47);
    }

    private BinarySearchTree<Integer, WrapperClass> createBinarySearchTree() {
        var binarySearchTree = new BinarySearchTree<Integer, WrapperClass>();
        var primeList = find15thFirstPrimeNumbers();
        Collections.shuffle(primeList);
        wrapperList = new ArrayList<>();
        fillTree(binarySearchTree, primeList);

        return binarySearchTree;
    }

    private List<Integer> find15thFirstPrimeNumbers() {
        return Stream.iterate(new BigInteger("2"), BigInteger::nextProbablePrime)
                .limit(15)
                .map(BigInteger::intValue)
                .collect(Collectors.toList());
    }

    private boolean isInOrder(List<WrapperClass> list) {
        int prevValue = 0;
        for (WrapperClass item : list) {
            if (item.getPrime() < prevValue) {
                return false;
            } else {
                prevValue = item.getPrime();
            }
        }
        return true;
    }

    private void fillTree(BinarySearchTree<Integer, WrapperClass> binarySearchTree, List<Integer> primeList) {
        primeList.forEach(prime -> {
            var wrapper = new WrapperClass(prime);
            binarySearchTree.insert(prime, wrapper);
            wrapperList.add(wrapper);
        });
    }

    private static class WrapperClass {

        private final int prime;

        WrapperClass(int prime) {
            this.prime = prime;
        }

        int getPrime() {
            return prime;
        }
    }
}