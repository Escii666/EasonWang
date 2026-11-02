import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void addLastTest() {
        Deque61B<String> deque = new ArrayDeque61B<>();

        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.toList())
                .containsExactly("A", "B", "C")
                .inOrder();
    }

    @Test
    public void addFirstTest() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addFirst("A");
        deque.addFirst("B");
        deque.addFirst("C");

        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.toList()).containsExactly("C", "B", "A").inOrder();
    }

    @Test
    public void addFirstLastTest() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addFirst("A");
        deque.addFirst("B");
        deque.addFirst("C");
        deque.addLast("D");
        deque.addLast("E");
        deque.addLast("F");
        assertThat(deque.size()).isEqualTo(6);
        assertThat(deque.toList()).containsExactly("C", "B", "A", "D", "E", "F").inOrder();
    }

    @Test
    public void isEmptyTestBasic() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 新建的链表应当为空。
        assertThat(deque.isEmpty()).isTrue();

        // 从两端添加元素后，应当非空。
        deque.addFirst(0);
        assertThat(deque.isEmpty()).isFalse();
        deque.addLast(1);
        assertThat(deque.isEmpty()).isFalse();

        // 删除一个元素后仍然非空，删除最后一个元素后恢复为空。
        deque.removeFirst();
        assertThat(deque.isEmpty()).isFalse();
        deque.removeLast();
        assertThat(deque.isEmpty()).isTrue();

        // 删空后重新添加和删除，判空仍应正确。
        deque.addLast(3);
        assertThat(deque.isEmpty()).isFalse();
        deque.removeFirst();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void sizeTestBasic() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        assertThat(deque.size()).isEqualTo(0);

        //添加后测试
        deque.addFirst(0);
        assertThat(deque.size()).isEqualTo(1);
        deque.addLast(2);
        assertThat(deque.size()).isEqualTo(2);

        //删除后测试
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(1);
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(0);

        //空链表删除后测试

        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(0);
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void getTestBasic() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 空链表检测
        assertThat(deque.get(0)).isNull();
        assertThat(deque.get(-1)).isNull();

        // 单元素检测
        deque.addLast(10);
        assertThat(deque.get(0)).isEqualTo(10);

        // 三项检测
        deque.addFirst(5);
        deque.addLast(20);
        assertThat(deque.get(0)).isEqualTo(5);
        assertThat(deque.get(1)).isEqualTo(10);

        assertThat(deque.get(2)).isEqualTo(20);

        // 负数、恰好等于 size 和远超范围的下标检测
        assertThat(deque.get(-1)).isNull();
        assertThat(deque.get(3)).isNull();
        assertThat(deque.get(28723)).isNull();

        // 查询不应该改变链表的内容和数量
        assertThat(deque.toList()).containsExactly(5, 10, 20).inOrder();
        assertThat(deque.size()).isEqualTo(3);
    }

    @Test
    public void removeFirstTestBasic() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 空链表删除应返回 null
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();

        deque.addLast(10);
        deque.addLast(20); // [10, 20]

        // 两个元素删到一个
        assertThat(deque.removeFirst()).isEqualTo(10);
        assertThat(deque.toList()).containsExactly(20).inOrder();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.isEmpty()).isFalse();

        // 删除唯一元素后，恢复为空
        assertThat(deque.removeFirst()).isEqualTo(20);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();

        // 删空后再次删除
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();

        // 删空后从头部添加，再从尾部删除
        deque.addFirst(30);
        assertThat(deque.toList()).containsExactly(30).inOrder();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.removeLast()).isEqualTo(30);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);

        // 再次删空后，从尾部添加
        deque.addLast(40);
        assertThat(deque.toList()).containsExactly(40).inOrder();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.removeFirst()).isEqualTo(40);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void removeLastTestBasic() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 空链表删除应返回 null
        assertThat(deque.removeLast()).isNull();
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();

        deque.addLast(10);
        deque.addLast(20); // [10, 20]

        // 两个元素删到一个
        assertThat(deque.removeLast()).isEqualTo(20);
        assertThat(deque.toList()).containsExactly(10).inOrder();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.isEmpty()).isFalse();

        // 删除唯一元素后，恢复为空
        assertThat(deque.removeLast()).isEqualTo(10);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();

        // 删空后再次删除
        assertThat(deque.removeLast()).isNull();
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();

        // 删空后从尾部添加，再从头部删除
        deque.addLast(30);
        assertThat(deque.toList()).containsExactly(30).inOrder();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.removeFirst()).isEqualTo(30);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);

        // 再次删空后，从头部添加也应该正常。
        deque.addFirst(40);
        assertThat(deque.toList()).containsExactly(40).inOrder();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.removeLast()).isEqualTo(40);
        assertThat(deque.toList()).isEmpty();
        assertThat(deque.size()).isEqualTo(0);
        assertThat(deque.isEmpty()).isTrue();
    }


    @Test
    public void removeFirstMultipleElementsTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int value = 10; value <= 40; value += 10) {
            deque.addLast(value);
        }

        assertThat(deque.removeFirst()).isEqualTo(10);
        assertThat(deque.toList()).containsExactly(20, 30, 40).inOrder();
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.isEmpty()).isFalse();

        assertThat(deque.removeLast()).isEqualTo(40);
        assertThat(deque.toList()).containsExactly(20, 30).inOrder();
        assertThat(deque.size()).isEqualTo(2);
    }

    @Test
    public void removeLastMultipleElementsTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int value = 10; value <= 40; value += 10) {
            deque.addLast(value);
        }

        assertThat(deque.removeLast()).isEqualTo(40);
        assertThat(deque.toList()).containsExactly(10, 20, 30).inOrder();
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.isEmpty()).isFalse();

        assertThat(deque.removeFirst()).isEqualTo(10);
        assertThat(deque.toList()).containsExactly(20, 30).inOrder();
        assertThat(deque.size()).isEqualTo(2);
    }

    @Test
    public void toListIndependentCopyTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(10);
        deque.addLast(20);

        List<Integer> firstCopy = deque.toList();
        List<Integer> secondCopy = deque.toList();
        firstCopy.clear();

        assertThat(deque.toList()).containsExactly(10, 20).inOrder();
        assertThat(deque.size()).isEqualTo(2);
        assertThat(secondCopy).containsExactly(10, 20).inOrder();

        deque.addLast(30);
        assertThat(deque.toList()).containsExactly(10, 20, 30).inOrder();
        assertThat(deque.size()).isEqualTo(3);
        assertThat(secondCopy).containsExactly(10, 20).inOrder();
        assertThat(firstCopy).isEmpty();
    }

    @Test
    public void addLastResizingTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        List<Integer> expected = new ArrayList<>();
        // 超过初始容量，并继续添加，验证多次扩容。
        for (int value = 0; value < 40; value++) {
            deque.addLast(value);
            expected.add(value);
            assertContents(deque, expected);
        }
    }

    @Test
    public void addFirstResizingTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        List<Integer> expected = new ArrayList<>();
        for (int value = 0; value < 40; value++) {
            deque.addFirst(value);
            expected.add(0, value);
            assertContents(deque, expected);
        }
    }

    @Test
    public void addLastResizingWithWrappedElementsTest() {
        Deque61B<Integer> deque = wrappedDeque(); // [5, 6, ..., 11]
        deque.addLast(12);
        assertContents(deque, range(5, 13));
        // 此时已满，下一次添加需要按逻辑顺序搬运跨界的元素。
        deque.addLast(13);
        assertContents(deque, range(5, 14));
        deque.addFirst(4);
        assertContents(deque, range(4, 14));
    }

    @Test
    public void addFirstResizingWithWrappedElementsTest() {
        Deque61B<Integer> deque = wrappedDeque();
        deque.addLast(12);
        assertContents(deque, range(5, 13));
        deque.addFirst(4);
        assertContents(deque, range(4, 13));
        deque.addLast(13);
        assertContents(deque, range(4, 14));
    }

    @Test
    public void removeFirstWrapAroundTest() {
        Deque61B<Integer> deque = wrappedDeque();
        for (int value = 5; value <= 11; value++) {
            assertThat(deque.removeFirst()).isEqualTo(value);
            assertContents(deque, range(value + 1, 12));
        }
        assertThat(deque.removeFirst()).isNull();
        deque.addLast(100);
        assertContents(deque, List.of(100));
        assertThat(deque.removeFirst()).isEqualTo(100);
        assertContents(deque, List.of());
    }

    @Test
    public void removeLastWrapAroundTest() {
        Deque61B<Integer> deque = wrappedDeque();
        for (int value = 11; value >= 5; value--) {
            assertThat(deque.removeLast()).isEqualTo(value);
            assertContents(deque, range(5, value));
        }
        assertThat(deque.removeLast()).isNull();
        deque.addFirst(100);
        assertContents(deque, List.of(100));
        assertThat(deque.removeLast()).isEqualTo(100);
        assertContents(deque, List.of());
    }

    @Test
    public void removeFirstShrinkAndRegrowTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int value = 0; value < 128; value++) {
            deque.addLast(value);
        }
        // 从队首大量删除，跨过多个缩容阈值，逐次核对剩余元素。
        for (int value = 0; value < 125; value++) {
            assertThat(deque.removeFirst()).isEqualTo(value);
            assertContents(deque, range(value + 1, 128));
        }
        deque.addFirst(124);
        assertContents(deque, range(124, 128));
        for (int value = 128; value < 192; value++) {
            deque.addLast(value);
            assertContents(deque, range(124, value + 1));
        }
        for (int value = 191; value >= 124; value--) {
            assertThat(deque.removeLast()).isEqualTo(value);
            assertContents(deque, range(124, value));
        }
        assertThat(deque.removeLast()).isNull();
    }

    @Test
    public void removeLastShrinkAndRegrowTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int value = 0; value < 128; value++) {
            deque.addLast(value);
        }
        for (int value = 127; value >= 3; value--) {
            assertThat(deque.removeLast()).isEqualTo(value);
            assertContents(deque, range(0, value));
        }
        deque.addFirst(-1);
        assertContents(deque, range(-1, 3));
        for (int value = 3; value < 64; value++) {
            deque.addLast(value);
            assertContents(deque, range(-1, value + 1));
        }
        for (int value = -1; value < 64; value++) {
            assertThat(deque.removeFirst()).isEqualTo(value);
            assertContents(deque, range(value + 1, 64));
        }
        assertThat(deque.removeFirst()).isNull();
    }

    // 构造跨界状态
    private Deque61B<Integer> wrappedDeque() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int value = 0; value < 8; value++) {
            deque.addLast(value);
        }
        assertContents(deque, range(0, 8));
        for (int value = 0; value < 5; value++) {
            assertThat(deque.removeFirst()).isEqualTo(value);
        }
        for (int value = 8; value < 12; value++) {
            deque.addLast(value);
        }
        assertContents(deque, range(5, 12));
        return deque;
    }

    // 生成 [start, end) 的预期结果，独立于被测队列的实现
    private List<Integer> range(int start, int end) {
        List<Integer> expected = new ArrayList<>();
        for (int value = start; value < end; value++) {
            expected.add(value);
        }
        return expected;
    }

    private void assertContents(Deque61B<Integer> deque, List<Integer> expected) {
        assertThat(deque.size()).isEqualTo(expected.size());
        assertThat(deque.isEmpty()).isEqualTo(expected.isEmpty());
        assertThat(deque.toList()).containsExactlyElementsIn(expected).inOrder();
        for (int index = 0; index < expected.size(); index++) {
            assertThat(deque.get(index)).isEqualTo(expected.get(index));
        }
        assertThat(deque.get(-1)).isNull();
        assertThat(deque.get(expected.size())).isNull();
    }
}
