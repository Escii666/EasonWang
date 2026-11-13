package ngrams;


import java.util.Collection;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    //构造表，以单词为keys
    private final Map<String, TimeSeries> wordHistories = new HashMap<>();

    //当年语料库总词数
    private final TimeSeries totalCounts = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(wordsFilename))) {
            String line = reader.readLine();
            while (line != null) {
                String[] columns = line.split("\t");

                //word取单词
                String word = columns[0];

                //year取年份
                int year = Integer.parseInt(columns[1]);

                //count取出现次数
                double count = Double.parseDouble(columns[2]);

                TimeSeries history = wordHistories.get(word);
                if (history == null) {
                    history = new TimeSeries();
                    wordHistories.put(word, history);
                }
                history.put(year, count);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read: " + wordsFilename, e);
        }

        try (BufferedReader reader = Files.newBufferedReader(Path.of(countsFilename))) {
            String line = reader.readLine();
            while (line != null) {
                String[] columns = line.split(",");
                int year = Integer.parseInt(columns[0]);
                double count = Double.parseDouble(columns[1]);
                totalCounts.put(year, count);

                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read: " + countsFilename, e);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries history = wordHistories.get(word);
        if (history == null) {
            //若找不到，返回空TimeSeries
            return new TimeSeries();
        }

        //返回出现次数
        return new TimeSeries(history, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {

        TimeSeries copy = new TimeSeries();
        TimeSeries history = wordHistories.get(word);

        if (history != null) {
            copy.putAll(history);
        }

        return copy;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries copy = new TimeSeries();
        copy.putAll(totalCounts);
        return copy;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries counts = countHistory(word, startYear, endYear);
        return counts.dividedBy(totalCounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries counts = countHistory(word);
        return counts.dividedBy(totalCounts);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();

        for (String word : words) {
            TimeSeries weight = weightHistory(word, startYear, endYear);
            sum = sum.plus(weight);
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sum = new TimeSeries();

        for (String word : words) {
            TimeSeries weight = weightHistory(word);
            sum = sum.plus(weight);
        }
        return sum;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
