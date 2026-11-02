import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DequeIteratorTest {
    // 同一组测试分别运行在数组队列和链表队列上。
    static Stream<Supplier<Deque61B<Integer>>> dequeFactories() {
        return Stream.of(ArrayDeque61B::new, LinkedListDeque61B::new);
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void emptyIteratorTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        Iterator<Integer> iterator = deque.iterator();
        assertThat(iterator.hasNext()).isFalse();
        assertThat(iterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThat(deque.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void iteratorOrderAndExhaustionTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        deque.addLast(20);
        deque.addFirst(10);
        deque.addLast(30);
        Iterator<Integer> iterator = deque.iterator();
        for (int expected : List.of(10, 20, 30)) {
            // 重复调用 hasNext 不应该推进遍历位置。
            assertThat(iterator.hasNext()).isTrue();
            assertThat(iterator.hasNext()).isTrue();
            assertThat(iterator.next()).isEqualTo(expected);
        }
        assertThat(iterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.toList()).containsExactly(10, 20, 30).inOrder();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void forEachDoesNotModifyDequeTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        // 可以多次完整遍历，同一个队列的内容不变。
        for (int pass = 0; pass < 2; pass++) {
            List<Integer> visited = new ArrayList<>();
            for (int item : deque) {
                visited.add(item);
            }
            assertThat(visited).containsExactly(1, 2, 3).inOrder();
            assertThat(deque.toList()).containsExactly(1, 2, 3).inOrder();
            assertThat(deque.size()).isEqualTo(3);
        }
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void independentIteratorsTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        deque.addLast(10);
        deque.addLast(20);
        Iterator<Integer> first = deque.iterator();
        Iterator<Integer> second = deque.iterator();
        assertThat(first.next()).isEqualTo(10);
        assertThat(first.next()).isEqualTo(20);
        assertThat(first.hasNext()).isFalse();
        assertThat(second.hasNext()).isTrue();
        assertThat(second.next()).isEqualTo(10);
        assertThat(second.next()).isEqualTo(20);
        assertThat(second.hasNext()).isFalse();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void singleElementIteratorTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        deque.addFirst(42);
        Iterator<Integer> iterator = deque.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(42);
        assertThat(iterator.hasNext()).isFalse();
        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertThat(deque.removeLast()).isEqualTo(42);
        assertThat(deque.iterator().hasNext()).isFalse();
    }

    @Test
    public void arrayIteratorAfterWrapAndResizeTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int value = 0; value < 32; value++) {
            deque.addLast(value);
        }
        for (int value = 0; value < 29; value++) {
            assertThat(deque.removeFirst()).isEqualTo(value);
        }
        for (int value = 32; value < 48; value++) {
            deque.addLast(value);
        }
        List<Integer> expected = new ArrayList<>();
        for (int value = 29; value < 48; value++) {
            expected.add(value);
        }
        List<Integer> visited = new ArrayList<>();
        for (int item : deque) {
            visited.add(item);
        }
        assertThat(visited).containsExactlyElementsIn(expected).inOrder();
        assertThat(deque.toList()).containsExactlyElementsIn(expected).inOrder();
        assertThat(deque.size()).isEqualTo(19);
    }
}
