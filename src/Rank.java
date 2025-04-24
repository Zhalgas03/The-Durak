import java.util.Arrays;

enum Rank {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
    JACK, QUEEN, KING, ACE;

    public static Rank[] getRanks(int mode) {
        return mode == 52 ? Rank.values() : Arrays.copyOfRange(Rank.values(), 4, Rank.values().length);
    }
}