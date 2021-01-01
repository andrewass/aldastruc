package tree;

import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree<K extends Comparable<K>, E> {

    private BinaryTreeNode root;

    public void insert(K key, E element) {
        var node = new BinaryTreeNode(key, element);
        var currentNode = root;
        BinaryTreeNode leafParent = null;

        while (currentNode != null) {
            leafParent = currentNode;
            currentNode = key.compareTo(currentNode.key) <= 0 ? currentNode.leftChild : currentNode.rightChild;
        }
        node.parent = leafParent;

        if (leafParent == null) {
            root = node;
        } else if (key.compareTo(leafParent.key) <= 0) {
            leafParent.leftChild = node;
        } else {
            leafParent.rightChild = node;
        }
    }

    public void delete(K key) {
        var deleteNode = get(key, root);
        if (deleteNode != null) {
            deleteNode(deleteNode);
        }
    }

    public E get(K key) {
        var result = get(key, root);

        return result != null ? result.element : null;
    }

    public E minimum() {
        var result = findMinimumNode(root);

        return result != null ? result.element : null;
    }

    public E maximum() {
        var result = findMaximum(root);

        return result != null ? result.element : null;
    }

    public List<E> inOrderList() {
        LinkedList<E> inOrderList = new LinkedList<>();
        fillInOrderList(root, inOrderList);
        return inOrderList;
    }

    private void deleteNode(BinaryTreeNode deleteNode) {
        if (deleteNode.leftChild == null) {
            transplant(deleteNode, deleteNode.rightChild);
        } else if (deleteNode.rightChild == null) {
            transplant(deleteNode, deleteNode.leftChild);
        } else {
            var minRightNode = findMinimumNode(deleteNode.rightChild);
            if (minRightNode.parent != deleteNode) {
                transplant(minRightNode, minRightNode.rightChild);
                minRightNode.rightChild = deleteNode.rightChild;
                minRightNode.rightChild.parent = minRightNode;
            }
            transplant(deleteNode, minRightNode);
            minRightNode.leftChild = deleteNode.leftChild;
            minRightNode.leftChild.parent = minRightNode;
        }
    }

    private BinaryTreeNode findMinimumNode(BinaryTreeNode root) {
        var currentNode = root;
        while (currentNode != null && currentNode.leftChild != null) {
            currentNode = currentNode.leftChild;
        }

        return currentNode;
    }

    private BinaryTreeNode findMaximum(BinaryTreeNode root) {
        var currentNode = root;
        while (currentNode != null && currentNode.rightChild != null) {
            currentNode = currentNode.rightChild;
        }

        return currentNode;
    }

    private void transplant(BinaryTreeNode node, BinaryTreeNode subNode) {
        if (node.parent == null) {
            root = subNode;
        } else if (node == node.parent.leftChild) {
            node.parent.leftChild = subNode;
        } else {
            node.parent.rightChild = subNode;
        }

        if (subNode != null) {
            subNode.parent = node.parent;
        }
    }

    private BinaryTreeNode get(K key, BinaryTreeNode current) {
        if (current == null) {
            return null;
        }
        if (current.key.equals(key)) {
            return current;
        }

        return key.compareTo(current.key) <= 0 ? get(key, current.leftChild) : get(key, current.rightChild);
    }

    private void fillInOrderList(BinaryTreeNode node, LinkedList<E> inOrderList) {
        if (node != null) {
            fillInOrderList(node.leftChild, inOrderList);
            inOrderList.addLast(node.element);
            fillInOrderList(node.rightChild, inOrderList);
        }
    }


    private class BinaryTreeNode {
        K key;
        E element;
        BinaryTreeNode parent;
        BinaryTreeNode leftChild;
        BinaryTreeNode rightChild;

        BinaryTreeNode(K key, E element) {
            this.key = key;
            this.element = element;
        }
    }
}
