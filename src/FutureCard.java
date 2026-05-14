import java.util.ArrayList;

/**
 * A card that lets a player look at the next few cards in the deck.
 */
public class FutureCard extends ActionCard {

    private ArrayList<ActionCard> deck;

    /**
     * Creates a FutureCard with a random point value and a reference to the deck.
     */
    public FutureCard(ArrayList<ActionCard> deck) {
        super(Rand.randomInt(2, 4 + 1));
        this.deck = deck;
    }

    /**
     * Gives points to the current player and shows the next three cards in the deck.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        // Give the current player the point value from this card
        currentPlayer.addPoints(getPointValue());

        // Show which card was played and the new total
        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        // Stop the future effect if the cancel shield is active
        if (currentPlayer.consumeCancel()) {
            System.out.println(currentPlayer.getName() + " used a cancel card!");
            System.out.println("The future effect was stopped.");
            return;
        }

        // Show up to the next three cards from the deck
        System.out.println("Next cards in the deck:");

        for (int i = 0; i < 3; i++)
        {
            System.out.println(deck.get(i));
        }
    }

    /**
     * Returns a text description of this card.
     */
    @Override
    public String toString() {
        return "Future Card { point value: " + getPointValue() + " }";
    }
}