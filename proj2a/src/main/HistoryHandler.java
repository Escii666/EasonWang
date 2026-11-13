package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap map;
    public HistoryHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> labels = new ArrayList<>();
        List<TimeSeries> series = new ArrayList<>();

        for (String word : q.words()) {
            TimeSeries weight = map.weightHistory(word, q.startYear(), q.endYear());
            if (!weight.isEmpty()) {
                labels.add(word);
                series.add(weight);
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, series);
        if (series.isEmpty()) {
            chart.setTitle("No data for the selected words and years");
        }
        return Plotter.encodeChartAsString(chart);
    }
}
