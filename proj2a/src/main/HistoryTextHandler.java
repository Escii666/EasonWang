package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        StringBuilder response = new StringBuilder();
        for(String word :q.words())
        {
            TimeSeries weights = map.weightHistory(word,q.startYear(),q.endYear());
            response.append(word).append(": ").append(weights).append("\n");
        }
        return response.toString();
    }
}
