import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

public class DequeEqualsTest {
    static Stream<Supplier<Deque61B<String>>> dequeFactories() {
        return Stream.of(ArrayDeque61B::new, LinkedListDeque61B::new);
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void sameInstanceAndOtherTypesTest(Supplier<Deque61B<String>> factory) {
        Deque61B<String> deque = factory.get();
        deque.addLast("A");
        assertThat(deque.equals(deque)).isTrue();
        assertThat(deque.equals(null)).isFalse();
        assertThat(deque.equals("A")).isFalse();
        assertThat(deque.equals(List.of("A"))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void emptyQueuesTest(Supplier<Deque61B<String>> factory) {
        assertThat(factory.get().equals(factory.get())).isTrue();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void equalContentsTest(Supplier<Deque61B<String>> factory) {
        Deque61B<String> first = factory.get();
        Deque61B<String> second = factory.get();
        // 值相同但不是同一个字符串对象，不能用 == 比较元素。
        first.addLast(new String("A"));
        second.addLast(new String("A"));
        assertThat(first.equals(second)).isTrue();
        first.addLast("B");
        second.addLast("B");
        assertThat(first.equals(second)).isTrue();
        assertThat(second.equals(first)).isTrue();
        assertThat(first.toList()).containsExactly("A", "B").inOrder();
        assertThat(second.toList()).containsExactly("A", "B").inOrder();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void differentContentsAndOrderTest(Supplier<Deque61B<String>> factory) {
        Deque61B<String> first = factory.get();
        Deque61B<String> second = factory.get();
        first.addLast("A"); first.addLast("B");
        second.addLast("B"); second.addLast("A");
        assertThat(first.equals(second)).isFalse();
        second.removeFirst(); second.removeLast();
        second.addLast("A"); second.addLast("C");
        assertThat(first.equals(second)).isFalse();
        second.removeLast(); second.addLast("B");
        assertThat(first.equals(second)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void differentLengthsTest(Supplier<Deque61B<String>> factory) {
        Deque61B<String> first = factory.get();
        Deque61B<String> second = factory.get();
        first.addLast("A");
        second.addLast("A"); second.addLast("B");
        assertThat(first.equals(second)).isFalse();
        assertThat(second.equals(first)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void emptyAndNonemptyTest(Supplier<Deque61B<String>> factory) {
        Deque61B<String> first = factory.get();
        Deque61B<String> second = factory.get();
        second.addLast("A");
        assertThat(first.equals(second)).isFalse();
        assertThat(second.equals(first)).isFalse();
        second.removeFirst();
        assertThat(first.equals(second)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("dequeFactories")
    public void equalHashCodesTest(Supplier<Deque61B<String>> factory) {
        Deque61B<String> first = factory.get();
        Deque61B<String> second = factory.get();
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
        first.addLast("A"); first.addLast("B");
        second.addLast("A"); second.addLast("B");
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    public void crossImplementationEqualityTest() {
        Deque61B<String> array = new ArrayDeque61B<>();
        Deque61B<String> linked = new LinkedListDeque61B<>();
        Deque61B<String> another = new ArrayDeque61B<>();
        for (String item : List.of("A", "B", "C")) {
            array.addLast(item); linked.addLast(item); another.addLast(item);
        }
        assertThat(array.equals(linked)).isTrue();
        assertThat(linked.equals(array)).isTrue();
        assertThat(linked.equals(another)).isTrue();
        assertThat(array.equals(another)).isTrue();
        assertThat(array.hashCode()).isEqualTo(linked.hashCode());
        linked.removeFirst(); linked.addLast("A");
        assertThat(array.equals(linked)).isFalse();
        assertThat(linked.equals(array)).isFalse();
    }

    @Test
    public void wrappedAndResizedEqualityTest() {
        Deque61B<Integer> array = new ArrayDeque61B<>();
        Deque61B<Integer> linked = new LinkedListDeque61B<>();
        for (int value = 0; value < 64; value++) array.addLast(value);
        for (int value = 0; value < 61; value++) array.removeFirst();
        for (int value = 64; value < 96; value++) array.addLast(value);
        for (int value = 61; value < 96; value++) linked.addLast(value);
        assertThat(array.equals(linked)).isTrue();
        assertThat(linked.equals(array)).isTrue();
        assertThat(array.hashCode()).isEqualTo(linked.hashCode());
        assertThat(array.size()).isEqualTo(35);
        assertThat(linked.size()).isEqualTo(35);
    }

    @Test
    public void equalsDoesNotCallToListTest() {
        Deque61B<String> array = new ArrayDeque61B<>() {
            @Override
            public List<String> toList() {
                throw new AssertionError("equals must not call toList");
            }
        };
        Deque61B<String> linked = new LinkedListDeque61B<>() {
            @Override
            public List<String> toList() {
                throw new AssertionError("equals must not call toList");
            }
        };
        array.addLast("A"); linked.addLast("A");
        assertThat(array.equals(linked)).isTrue();
        assertThat(linked.equals(array)).isTrue();
        assertThat(array.hashCode()).isEqualTo(linked.hashCode());
    }
}
