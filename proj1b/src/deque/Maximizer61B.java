package deque;
import java.util.Comparator;
import java.util.Iterator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();

        // 没有元素，就没有最大值
        if (!iterator.hasNext()) {
            return null;
        }

        // 先把第一个元素当作最大值
        T maxItem = iterator.next();

        // 从第二个元素开始，逐个比较
        while (iterator.hasNext()) {
            T item = iterator.next();

            if (item.compareTo(maxItem) > 0) {
                maxItem = item;
            }
        }

        return maxItem;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator to compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        T maxItem = iterator.next();
        while (iterator.hasNext()) {
            T item = iterator.next();
            // 使用调用者传入的比较规则。
            if (comp.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }
}
