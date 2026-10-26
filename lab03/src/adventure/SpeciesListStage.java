package adventure;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpeciesListStage implements AdventureStage {

    private static final List<String> REFERENCE_1 = List.of(
            "leopards",
            "bison"
    );

    private static final List<String> REFERENCE_2 = List.of(
            "squirrels",
            "hummingbirds"
    );

    private static final List<String> REFERENCE_3 = new ArrayList<>();

    private final In in;
    private final Map<String, AdventureStage> responses;

    public SpeciesListStage(In in) {
        this.in = in;
        this.responses = Map.of("go", new PalindromeStage(in));
    }

    @Override
    public String nextStagePrompt() {
        return "Wow! That was pretty neat! We got to see so many neat animals! "
                + "We should study now, so let's go to the Woz.";
    }

    @Override
    public void playStage() {
        String msg = """
                Inside Professor Hug's office, you see some O'Reilly books. These books have cool animals on the
                covers. As a budding computer scientist, you should be able to identify all kinds of neat animals.
                Here's a few:
                """;

        System.out.println(msg);
        System.out.println("- These large felines with spots will teach you how to react quickly.");
        System.out.println("- This native american bovine can be found in the plains, and happens to be EXTREMELY good at Java.");
        System.out.println("- Type their names into the terminal (separated by ',')");
        this.handleResponses(REFERENCE_1);

        System.out.println("Woah! There are even more neat books here!");
        System.out.println("- These bushy-tailed friends are everywhere in and around the trees on campus, and know the best parts of Java.");
        System.out.println("- These tiny birds flap very fast, drink nectar, and know how to make simpler Java appplications.");
        System.out.println("- Type their names into the terminal (separated by ',')");
        this.handleResponses(REFERENCE_2);

        System.out.println("Well, there's nothing left here! press enter to move.");
        this.handleResponses(REFERENCE_3);
    }

    @Override
    public Map<String, AdventureStage> getResponses() {
        return this.responses;
    }

    private void handleResponses(List<String> reference) {
        while (true) {
            String input = in.readLine();
            List<String> user;

            if (input.isEmpty()) {
                user = new ArrayList<>();
            } else {
                user = Arrays.asList(input.toLowerCase().split(" *, *"));
            }

            double similarity = arraySimilarity(reference, user);

            if (similarity != 1 && reference.size() != 0) {

                // 【修改1开始】单独统计答对的动物数量
                // 替换原来的 Math.round(similarity * reference.size())
                int numCorrect = 0;

                for (String animal : reference) {
                    if (user.contains(animal)) {
                        numCorrect++;
                    }
                }


                System.out.println(
                        "Try again! You got " + numCorrect + " animals correct!"
                );
                continue;
            }

            break;
        }
    }

    /**
     * 所有参考元素都匹配时返回 1，否则返回 0。
     */
    public static int arraySimilarity(
            List<String> listOne, List<String> listTwo) {

        List<String> copy = new ArrayList<>(listOne);
        int similarObjects = 0;

        for (String o : listTwo) {
            if (copy.contains(o)) {
                similarObjects++;
                copy.remove(o);
            }
        }

        // 【修改2】不再做除法，避免空列表导致除以零
        return similarObjects == listOne.size() ? 1 : 0;
    }
}