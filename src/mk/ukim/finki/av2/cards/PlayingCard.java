package mk.ukim.finki.av2.cards;

import java.util.Objects;

public class PlayingCard {

    private int rank;
    private PlayingCardType type;

    public PlayingCard(int rank, PlayingCardType type) {
        this.rank = rank;
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public PlayingCardType getType() {
        return type;
    }

    public void setType(PlayingCardType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("%d %s", rank, type.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayingCard card = (PlayingCard) o;
        return rank == card.rank && type == card.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, type);
    }
}
