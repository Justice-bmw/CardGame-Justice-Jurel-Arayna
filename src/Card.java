import java.util.ArrayList;

/**
 * Base class for all cards in the game.
 * Stores the point value earned when the card is played.
 */
public abstract class Card {

    private int pointValue;

    /**
     * Creates a card with a specific point value.
     */
    public Card(int pointValue) {
        this.pointValue = pointValue;
    }

    /**
     * Returns the number of points this card gives.
     */
    public int getPointValue() {
        return pointValue;
    }

    /**
     * Runs the effect of the card for the current player.
     */
    public abstract void play(Player currentPlayer, ArrayList<Player> allPlayers);
}