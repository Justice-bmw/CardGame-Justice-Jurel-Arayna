import java.util.ArrayList;

/**
 * A card that cancels the next incoming card effect used against a player.
 */
public class CancelCard extends ActionCard implements AppliesCancel {

    /**
     * Creates a cancel card.
     * This card has a fixed point value of negative one.
     */
    public CancelCard() { // add to game
        super(-1);
    }

    /**
     * Gives points to the current player and activates the cancel effect.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(super.getPointValue());

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        cancelCardEffect(currentPlayer);

        System.out.println(currentPlayer.getName() + " is now protected from the next attack!");
    }

    /**
     * Required by the AppliesCancel interface.
     * The play method already handles the full cancel effect.
     */
    @Override
    public void cancelCardEffect(Player currentPlayer) {
        currentPlayer.activateCancel();
    }

    /**
     * Returns a text description of this card.
     */
    @Override
    public String toString() {
        return "Cancel Card { point value: " + super.getPointValue() + "}";
    }
}