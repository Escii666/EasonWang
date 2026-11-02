package deque;

import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayDeque61B<T> implements Deque61B<T> {



    @Override
    public String toString() {
        // 构造并返回字符串
        StringBuilder result = new StringBuilder("[");
        boolean first = true;

        //先判断是否是第一项，若不是用逗号分隔
        for (T item : this) {
            if (!first) {
                result.append(", ");
            }

            result.append(item);
            first = false;
        }

        result.append("]");
        return result.toString();
    }

    // 按元素和顺序比较，数组队列和链表队列也可以相等
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        //如果other不是一个实现了Deque61B的对象，就返回false
        if (!(other instanceof Deque61B<?> otherDeque)) {
            return false;
        }
        //若大小不匹配，返回false
        if (size != otherDeque.size()) {
            return false;
        }

        //创造两个迭代器，进行各元素判断
        Iterator<T> thisIterator = iterator();
        Iterator<?> otherIterator = otherDeque.iterator();

        //判断各元素是否相等
        while (thisIterator.hasNext()) {
            if (!Objects.equals(thisIterator.next(), otherIterator.next())) {
                return false;
            }
        }
        return true;
    }

    // 相等的队列必须具有相同的哈希值。
    @Override
    public int hashCode() {
        int result = 1;
        for (T item : this) {
            result = 31 * result + Objects.hashCode(item);
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index = 0;

        @Override
        //判断有没有下一项
        public boolean hasNext() {
            return index < size;
        }

        @Override
        //return 下一项（若存在）
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T item = get(index);
            index++;
            return item;
        }
    }



    private T[] items;
    private int size;
    // 队首的下标
    private int front;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        //按队列顺序，从旧数组取元素，依次放进新数组的 0、1、2、3… 位置
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(front + i) % items.length];
        }
        items = newItems;
        front = 0;
    }

    @Override
    public void addFirst(T x) {
        //先判断数组是否已满，若已满则扩容
        if (size == items.length) {
            resize(items.length * 2);
        }
        //在队首添加新一项,且为了防止负数出现运用floorMod取模而不是%
        front = Math.floorMod(front - 1, items.length);
        items[front] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        //先判断数组是否已满，若已满则扩容
        if (size == items.length) {
            resize(items.length * 2);
        }
        //在队尾空余位置添加新一项
        items[(front + size) % items.length] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(items[(front + i) % items.length]);
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

        //记录队首移除的数
        T removed = items[front];
        //清空
        items[front] = null;
        //队首向后移动
        front = (front + 1) % items.length;
        size--;

        //队列缩容
        if (items.length >= 16 && size <= items.length / 4) {
            resize(items.length / 2);
        }
        return removed;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        //记录队末已移除的数
        T removed = items[(front + size - 1) % items.length];
        //清空
        items[(front + size - 1) % items.length] = null;
        size--;

        //队列缩容
        if (items.length >= 16 && size <= items.length / 4) {
            resize(items.length / 2);
        }
        return removed;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(front + index) % items.length];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException(
                "No need to implement getRecursive for proj 1b");
    }
}
