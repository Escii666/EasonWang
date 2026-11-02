import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import deque.Maximizer61B;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

public class Maximizer61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return Integer.compare(a.length(), b.length());
        }
    }

    static Stream<Supplier<Deque61B<Integer>>> integerQueues() {
        return Stream.of(ArrayDeque61B::new, LinkedListDeque61B::new);
    }

    static Stream<Supplier<Deque61B<String>>> stringQueues() {
        return Stream.of(ArrayDeque61B::new, LinkedListDeque61B::new);
    }

    @ParameterizedTest
    @MethodSource("integerQueues")
    public void emptyInputTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        assertThat(Maximizer61B.max(deque)).isNull();
        assertThat(Maximizer61B.max(deque, Comparator.naturalOrder())).isNull();
    }

    @ParameterizedTest
    @MethodSource("integerQueues")
    public void singleElementTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        deque.addLast(-7);
        assertThat(Maximizer61B.max(deque)).isEqualTo(-7);
        assertThat(Maximizer61B.max(deque, Comparator.reverseOrder())).isEqualTo(-7);
    }

    @ParameterizedTest
    @MethodSource("integerQueues")
    public void maximumAtDifferentPositionsTest(Supplier<Deque61B<Integer>> factory) {
        for (List<Integer> values : List.of(List.of(9, 1, 2), List.of(1, 9, 2), List.of(1, 2, 9))) {
            Deque61B<Integer> deque = factory.get();
            for (int value : values) {
                deque.addLast(value);
            }
            assertThat(Maximizer61B.max(deque)).isEqualTo(9);
            assertThat(Maximizer61B.max(deque, Comparator.naturalOrder())).isEqualTo(9);
        }
    }

    @ParameterizedTest
    @MethodSource("integerQueues")
    public void negativesAndDuplicatesTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        for (int value : List.of(-8, -2, -5, -2)) {
            deque.addLast(value);
        }
        assertThat(Maximizer61B.max(deque)).isEqualTo(-2);
        assertThat(Maximizer61B.max(deque, Comparator.naturalOrder())).isEqualTo(-2);
    }

    @ParameterizedTest
    @MethodSource("integerQueues")
    public void comparatorControlsOrderTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        for (int value : List.of(3, 8, 2, 5)) {
            deque.addLast(value);
        }
        // 按反向规则，“最大”的元素实际是数值最小的 2。
        assertThat(Maximizer61B.max(deque, Comparator.reverseOrder())).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("integerQueues")
    public void doesNotModifyInputTest(Supplier<Deque61B<Integer>> factory) {
        Deque61B<Integer> deque = factory.get();
        for (int value = 0; value < 64; value++) {
            deque.addLast(value);
        }
        for (int value = 0; value < 61; value++) {
            deque.removeFirst();
        }
        deque.addLast(100);
        List<Integer> before = deque.toList();
        assertThat(Maximizer61B.max(deque)).isEqualTo(100);
        assertThat(Maximizer61B.max(deque, Comparator.reverseOrder())).isEqualTo(61);
        assertThat(deque.toList()).containsExactlyElementsIn(before).inOrder();
        assertThat(deque.size()).isEqualTo(4);
    }

    @ParameterizedTest
    @MethodSource("stringQueues")
    //按长度比较string
    public void stringsUseDifferentRulesTest(Supplier<Deque61B<String>> factory) {
        Deque61B<String> deque = factory.get();
        for (String value : List.of("zoo", "elephant", "cat")) {
            deque.addLast(value);
        }
        assertThat(Maximizer61B.max(deque)).isEqualTo("zoo");
        assertThat(Maximizer61B.max(deque, new StringLengthComparator())).isEqualTo("elephant");
    }

    @Test
    public void comparatorWorksWithoutComparableTest() {
        // 普通自定义类型不必实现 Comparable，也能使用 Comparator 版本。
        class Student {
            final int score;
            Student(int score) {
                this.score = score;
            }
        }
        Student first = new Student(70);
        Student best = new Student(95);
        Student third = new Student(80);
        Student result = Maximizer61B.max(List.of(first, best, third),
                Comparator.comparingInt(student -> student.score));
        assertThat(result).isSameInstanceAs(best);
    }

    @Test
    public void otherIterableAndTiesTest() {
        assertThat(Maximizer61B.max(List.of(5, 5, 5))).isEqualTo(5);
        assertThat(Maximizer61B.max(List.of(1, 9, 4))).isEqualTo(9);
        assertThat(Maximizer61B.max(List.of("aa", "bb"), new StringLengthComparator()).length())
                .isEqualTo(2);
    }
}
