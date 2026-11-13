package main;

import static utils.Utils.*;

import org.slf4j.LoggerFactory;
import browser.NgordnetServer;
import ngrams.NGramMap;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    /* Do not delete or modify the static code above! */

    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        /* The following code might be useful to you.

        NGramMap ngm = new NGramMap(TOP_49887_WORDS_FILE, TOTAL_COUNTS_FILE);

        */
        NGramMap ngm = new NGramMap(
                "data/ngrams/tiny_words.tsv",
                "data/ngrams/tiny_counts.csv"
        );

        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet_2a.html");
    }
}
