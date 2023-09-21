package io.github.wolches.tgbot.alkach.service.common;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RandomService {

    private AtomicLong seed;
    private Lock lock;
    private volatile Random random;

    public RandomService() {
        this.lock = new ReentrantLock();
        long unixTime = new Random(Instant.now().toEpochMilli()).nextLong();
        setSeed(unixTime);
    }

    // TODO 1 Business logic exceptions
    // TODO 2 Refactor to more clear naming
    @SneakyThrows
    public Random getRandom() {
        Random random;
        if (lock.tryLock()) {
            random = this.random;
        } else {
            Thread.sleep(100L);
            random = getRandom();
        }
        lock.unlock();
        return random;
    }

    // TODO 1 Business logic exceptions
    // TODO 2 Refactor to more clear naming
    @SneakyThrows
    public void setSeed(long newSeed) {
        if (lock.tryLock()) {
            if (seed == null) {
                seed = new AtomicLong(0L);
            }
            seed.set(newSeed);
            random = new Random(seed.get());
        } else {
            Thread.sleep(100L);
            setSeed(newSeed);
        }
        lock.unlock();
    }
}
