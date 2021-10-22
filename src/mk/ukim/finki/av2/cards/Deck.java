package mk.ukim.finki.av2.cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {

    private PlayingCard[] cards;
    private boolean[] isDealt;
    private int dealtTotal;

    public Deck() {
        cards = new PlayingCard[52];
        isDealt = new boolean[52];
        dealtTotal = 0;

        for (int i = 0; i < PlayingCardType.values().length; i++) {
            for (int j = 0; j < 13; j++) {
                cards[i * 13 + j] = new PlayingCard(j + 2, PlayingCardType.values()[i]);
            }
        }
    }

    public PlayingCard[] getCards() {
        return cards;
    }

    public void setCards(PlayingCard[] cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Arrays.equals(cards, deck.cards);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cards);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (PlayingCard card : cards) {
            stringBuilder.append(card.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public boolean hasCardsLeft() {
        return (cards.length - dealtTotal) > 0;
    }

    public PlayingCard[] shuffle() {
        List<PlayingCard> playingCardList = Arrays.asList(cards);
        Collections.shuffle(playingCardList);
        return cards;
    }

    public PlayingCard dealCard() {
        if (!hasCardsLeft()) return null;

        int card = (int) (Math.random() * 52);

        if (!isDealt[card]) {
            isDealt[card] = true;
            dealtTotal++;
            return cards[card];
        }
        return dealCard();
    }

    public void dealCardsSecondWay() {
        shuffle();

        for (PlayingCard card : cards)
            System.out.println(card);
    }

    public static void main(String[] args) {
        Deck deck1 = new Deck();
        System.out.println(deck1);

        deck1.shuffle();
        System.out.println(deck1);

        PlayingCard card;
        while ((card = deck1.dealCard()) != null) {
            System.out.println(card);
        }

        System.out.println();
        Deck deck2 = new Deck();
        System.out.println(deck2);

        deck2.dealCardsSecondWay();
    }
}
