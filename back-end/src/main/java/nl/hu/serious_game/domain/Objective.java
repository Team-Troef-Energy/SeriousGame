package nl.hu.serious_game.domain;

public class Objective {
    private int maxCo2;
    private int maxCoins;

    public Objective(int maxCo2, int maxCoins) {
        if (maxCo2 < 0 || maxCoins < 0) {
            throw new IllegalArgumentException("maxCo2 and maxCoins must be positive");
        }
        this.maxCo2 = maxCo2;
        this.maxCoins = maxCoins;
    }

    public int getMaxCo2() {
        return maxCo2;
    }

    public int getMaxCoins() {
        return maxCoins;
    }
}
