package mk.ukim.finki.av2.cards;

public class MultipleDeck {

    private Deck[] decks;

    public MultipleDeck(int numberOfDecks) {
        decks = new Deck[numberOfDecks];
        for (int i = 0; i < numberOfDecks; i++)
            decks[i] = new Deck();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Deck deck : decks) {
            stringBuilder.append(deck.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        MultipleDeck multipleDecks = new MultipleDeck(3);
        System.out.println(multipleDecks);
    }
}
