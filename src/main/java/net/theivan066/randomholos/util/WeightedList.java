package net.theivan066.randomholos.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedList<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public WeightedList() {
        this(new Random());
    }

    public WeightedList(Random random) {
        this.random = random;
    }

    public WeightedList<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
