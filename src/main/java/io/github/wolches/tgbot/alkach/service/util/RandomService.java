package io.github.wolches.tgbot.alkach.service.util;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RandomService {

    private AtomicLong seed;

    @Getter
    private volatile Random random;

    public RandomService() {
        long unixTime = new Random(Instant.now().toEpochMilli()).nextLong();
        setSeed(unixTime);
    }

    public void setSeed(long newSeed) {
        if (seed == null) {
            seed = new AtomicLong(0L);
        }
        seed.set(newSeed);
        random = new Random(seed.get());
    }
}
