import java.util.ArrayList;

/**
 * Parent class for action cards.
 * This class exists so special card types can share the same card structure.
 */
public abstract class ActionCard extends Card {

    private int pointValue;

    /**
     * Creates a card with a specific point value.
     */
    public ActionCard(int pointValue) {
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