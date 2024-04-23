package com.example.app;

import javafx.util.Pair;

import java.util.List;

public class FeedManager {

    private static final String UNIQLO_URL = "https://www.uniqlo.com/jp/ja/contents/corp/press-release/index.xml";
    private static final String COMRAKOFF_URL = "https://www.youtube.com/feeds/videos.xml?channel_id=UChr1x_GdwoNkduX5956VyGA";

    private final List<FeedMessage> uniqloFeed;
    private final List<FeedMessage> comrakoffFeed;

    public FeedManager() {
        RssParser parser1 = new RssParser(UNIQLO_URL, "netflix");
        RssParser parser2 = new RssParser(COMRAKOFF_URL, "netflix");

        try {
            uniqloFeed = parser1.readRss();
            comrakoffFeed = parser2.readRss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FeedEntity createNew(String name, String url) throws Exception {
        RssParser parser1 = new RssParser(url, "netflix");

        return new FeedEntity(name, parser1.readRss());
    }


    public List<FeedEntity> getExistedFeed() {
        return List.of(
            new FeedEntity("comrakoff", comrakoffFeed),
            new FeedEntity("uniqlo", uniqloFeed)
        );
    }
}
