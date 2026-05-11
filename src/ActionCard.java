import java.util.ArrayList;

/**
 * Parent class for action cards.
 * This class exists so special card types can share the same card structure.
 */
public abstract class ActionCard extends Card {

    /**
     * Creates an action card with a specific point value.
     */
    public ActionCard(int pointValue) {
        super(pointValue);
    }

    /**
     * Runs the action card effect for the current player.
     */
    public abstract void play(Player currentPlayer, ArrayList<Player> allPlayers);
}