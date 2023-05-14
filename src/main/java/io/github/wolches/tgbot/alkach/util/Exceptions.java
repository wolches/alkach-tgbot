package io.github.wolches.tgbot.alkach.util;

public final class Exceptions {

    public static IllegalArgumentException IAE(String type, String bounds, String actual) {
        return new IllegalArgumentException(
                String.format("Invalid %s! Expected: %s, but was: %s", type, bounds, actual));
    }
}
