import java.util.Iterator;
import java.util.Set;
import java.util.LinkedHashSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    @Override
    public void put(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            size++;
            return;
        }

        Node current = root;
        while (true) {
            int comparison = key.compareTo(current.key);
            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node(key, value);
                    size++;
                    return;
                }
                current = current.left;
            } else if (comparison > 0) {
                if (current.right == null) {
                    current.right = new Node(key, value);
                    size++;
                    return;
                }
                current = current.right;
            } else {
                current.value = value;
                return;
            }
        }
    }

    @Override
    public V get(K key) {
        Node current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Node current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new LinkedHashSet<>();
        collectKeys(root, keys);
        return keys;
    }

    private void collectKeys(Node node, Set<K> keys) {
        if (node == null) {
            return;
        }
        collectKeys(node.left, keys);
        keys.add(node.key);
        collectKeys(node.right, keys);
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }

        //保存删除前节点的value
        V oldValue = get(key);
        root = deleteNode(root, key);
        size--;
        return oldValue;
    }

    private Node deleteNode(Node node, K key) {
        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = deleteNode(node.left, key);
            return node;
        } else if (comparison > 0) {
            node.right = deleteNode(node.right, key);
            return node;
        }
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }
        //处理右子树
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        node.key = successor.key;
        node.value = successor.value;
        node.right = deleteNode(node.right, node.key);
        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void printInOrder() {
        for (K key : this) {
            System.out.println(key + "->" + get(key));
        }
    }

    private Node root;
    private int size;
}