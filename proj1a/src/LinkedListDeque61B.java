import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    // 哨兵不存放实际元素，用来连接链表的首尾。
    private Node sentinel;

    // 实际元素数量，不包含哨兵节点。
    private int size;


    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        // 空链表中，哨兵的前驱和后继都指向自己。
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        // 新节点插在哨兵与原来的第一个节点之间。
        Node newNode = new Node(x, sentinel, sentinel.next);
        // 让原来的第一个节点向前指向新节点。
        sentinel.next.prev = newNode;
        // 让哨兵向后指向新节点，完成插入。
        sentinel.next = newNode;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        // 新节点插在原来的尾节点与哨兵之间。
        Node newNode = new Node(x, sentinel.prev, sentinel);
        // 让原来的尾节点向后指向新节点。
        sentinel.prev.next = newNode;
        // 让哨兵向前指向新节点，完成插入。
        sentinel.prev = newNode;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            result.add(current.item);
            current = current.next;
        }

        return result;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node removed = sentinel.next;
        sentinel.next = removed.next;
        removed.next.prev = sentinel;
        size -= 1;
        return removed.item;

    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node removed = sentinel.prev;
        sentinel.prev = removed.prev;
        removed.prev.next = sentinel;
        size -= 1;
        return removed.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T getRecursive(int index) {

        if (index < 0 || index >= size) {
            return null;
        }

        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node current, int remaining) {
        if (remaining == 0) {
            return current.item;
        }
        return getRecursiveHelper(current.next, remaining - 1);
    }
}