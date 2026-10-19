import java.util.List;
import java.util.ArrayList;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int sum = 0;
        for(int i = 0; i < L.size(); i++) {
            sum = sum+L.get(i);
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < L.size(); i++) {
            if(L.get(i)%2==0) {
                result.add(L.get(i));
            }
        }
        return result;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < L1.size(); i++) {
            for(int j = 0; j < L2.size(); j++) {
                if(L1.get(i) == L2.get(j)) {
                    result.add(L1.get(i));
                }
            }
        }
        return result;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int ans = 0;
        for (String s : words) {
            for(int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == c) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
