package com.example.app;

import java.util.List;

public record FeedEntity(String name, List<FeedMessage> feedMessages) {
}
