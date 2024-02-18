package io.github.wolches.tgbot.alkach.pipeline.common.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractContext implements Context {

    private final Map<String, Object> data;

    public AbstractContext() {
        data = new ConcurrentHashMap<>();
    }

    public void add(String key, Object value) {
        data.put(key, value);
    }

    public <T> T get(String key, Class<T> type) {
        return ((T) data.get(key));
    }
}
