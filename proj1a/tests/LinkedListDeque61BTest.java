import java.util.List;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/**
 * Performs some basic linked list tests.
 */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
           to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
           but not ["front", "middle", "back"].
         */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        /* I've decided to add in comments the state after each call for the convenience of the
           person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    public void isEmptyTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // 新建的链表应当为空。
        assertThat(lld1.isEmpty()).isTrue();

        // 从两端添加元素后，应当非空。
        lld1.addFirst(0);
        assertThat(lld1.isEmpty()).isFalse();
        lld1.addLast(1);
        assertThat(lld1.isEmpty()).isFalse();

        // 删除一个元素后仍然非空，删除最后一个元素后恢复为空。
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isFalse();
        lld1.removeLast();
        assertThat(lld1.isEmpty()).isTrue();

        // 删空后重新添加和删除，判空仍应正确。
        lld1.addLast(3);
        assertThat(lld1.isEmpty()).isFalse();
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void sizeTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.size()).isEqualTo(0);

        //添加后测试
        lld1.addFirst(0);
        assertThat(lld1.size()).isEqualTo(1);
        lld1.addLast(2);
        assertThat(lld1.size()).isEqualTo(2);

        //删除后测试
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(1);
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);

        //空链表删除后测试

        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void getTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // 空链表检测
        assertThat(lld1.get(0)).isNull();
        assertThat(lld1.get(-1)).isNull();

        // 单元素检测
        lld1.addLast(10);
        assertThat(lld1.get(0)).isEqualTo(10);

        // 三项检测
        lld1.addFirst(5);
        lld1.addLast(20);
        assertThat(lld1.get(0)).isEqualTo(5);
        assertThat(lld1.get(1)).isEqualTo(10);

        assertThat(lld1.get(2)).isEqualTo(20);

        // 负数、恰好等于 size 和远超范围的下标检测
        assertThat(lld1.get(-1)).isNull();
        assertThat(lld1.get(3)).isNull();
        assertThat(lld1.get(28723)).isNull();

        // 查询不应该改变链表的内容和数量
        assertThat(lld1.toList()).containsExactly(5, 10, 20).inOrder();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public void getRecursiveTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        // 空链表检测
        assertThat(lld1.getRecursive(0)).isNull();
        assertThat(lld1.getRecursive(-1)).isNull();

        // 单元素检测
        lld1.addLast(10);
        assertThat(lld1.getRecursive(0)).isEqualTo(10);

        // 三项检测
        lld1.addFirst(5);
        lld1.addLast(20);
        assertThat(lld1.getRecursive(0)).isEqualTo(5);
        assertThat(lld1.getRecursive(1)).isEqualTo(10);

        assertThat(lld1.getRecursive(2)).isEqualTo(20);

        // 负数、恰好等于 size 和远超范围的下标检测
        assertThat(lld1.getRecursive(-1)).isNull();
        assertThat(lld1.getRecursive(3)).isNull();
        assertThat(lld1.getRecursive(28723)).isNull();

        // 查询不应该改变链表的内容和数量
        assertThat(lld1.toList()).containsExactly(5, 10, 20).inOrder();
        assertThat(lld1.size()).isEqualTo(3);
    }

    @Test
    public void removeFirstTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // 空链表删除应返回 null
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addLast(10);
        lld1.addLast(20); // [10, 20]

        // 两个元素删到一个
        assertThat(lld1.removeFirst()).isEqualTo(10);
        assertThat(lld1.toList()).containsExactly(20).inOrder();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.isEmpty()).isFalse();

        // 删除唯一元素后，恢复为空
        assertThat(lld1.removeFirst()).isEqualTo(20);
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        // 删空后再次删除
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        // 删空后从头部添加，再从尾部删除
        lld1.addFirst(30);
        assertThat(lld1.toList()).containsExactly(30).inOrder();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.removeLast()).isEqualTo(30);
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);

        // 再次删空后，从尾部添加
        lld1.addLast(40);
        assertThat(lld1.toList()).containsExactly(40).inOrder();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.removeFirst()).isEqualTo(40);
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void removeLastTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // 空链表删除应返回 null
        assertThat(lld1.removeLast()).isNull();
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        lld1.addLast(10);
        lld1.addLast(20); // [10, 20]

        // 两个元素删到一个
        assertThat(lld1.removeLast()).isEqualTo(20);
        assertThat(lld1.toList()).containsExactly(10).inOrder();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.isEmpty()).isFalse();

        // 删除唯一元素后，恢复为空
        assertThat(lld1.removeLast()).isEqualTo(10);
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        // 删空后再次删除
        assertThat(lld1.removeLast()).isNull();
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();

        // 删空后从尾部添加，再从头部删除
        lld1.addLast(30);
        assertThat(lld1.toList()).containsExactly(30).inOrder();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.removeFirst()).isEqualTo(30);
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);

        // 再次删空后，从头部添加也应该正常。
        lld1.addFirst(40);
        assertThat(lld1.toList()).containsExactly(40).inOrder();
        assertThat(lld1.size()).isEqualTo(1);
        assertThat(lld1.removeLast()).isEqualTo(40);
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.isEmpty()).isTrue();
    }


    @Test
    public void removeFirstMultipleElementsTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        for (int value = 10; value <= 40; value += 10) {
            lld1.addLast(value);
        }

        assertThat(lld1.removeFirst()).isEqualTo(10);
        assertThat(lld1.toList()).containsExactly(20, 30, 40).inOrder();
        assertThat(lld1.size()).isEqualTo(3);
        assertThat(lld1.isEmpty()).isFalse();

        assertThat(lld1.removeLast()).isEqualTo(40);
        assertThat(lld1.toList()).containsExactly(20, 30).inOrder();
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    public void removeLastMultipleElementsTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        for (int value = 10; value <= 40; value += 10) {
            lld1.addLast(value);
        }

        assertThat(lld1.removeLast()).isEqualTo(40);
        assertThat(lld1.toList()).containsExactly(10, 20, 30).inOrder();
        assertThat(lld1.size()).isEqualTo(3);
        assertThat(lld1.isEmpty()).isFalse();

        assertThat(lld1.removeFirst()).isEqualTo(10);
        assertThat(lld1.toList()).containsExactly(20, 30).inOrder();
        assertThat(lld1.size()).isEqualTo(2);
    }

    @Test
    public void toListIndependentCopyTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(10);
        lld1.addLast(20);

        List<Integer> firstCopy = lld1.toList();
        List<Integer> secondCopy = lld1.toList();
        firstCopy.clear();

        assertThat(lld1.toList()).containsExactly(10, 20).inOrder();
        assertThat(lld1.size()).isEqualTo(2);
        assertThat(secondCopy).containsExactly(10, 20).inOrder();

        lld1.addLast(30);
        assertThat(lld1.toList()).containsExactly(10, 20, 30).inOrder();
        assertThat(lld1.size()).isEqualTo(3);
        assertThat(secondCopy).containsExactly(10, 20).inOrder();
        assertThat(firstCopy).isEmpty();
    }

}