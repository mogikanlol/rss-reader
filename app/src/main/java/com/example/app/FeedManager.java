package com.example.app;

import javafx.util.Pair;

import java.util.List;

public class FeedManager {

    public Pair<String, List<FeedMessage>> createNew(String name, String url) throws Exception {
        RssParser parser1 = new RssParser(url, "netflix");
        parser1.readRss();

        return new Pair<>(name, parser1.readRss());
    }
}
