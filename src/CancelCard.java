import java.util.ArrayList;

/*
 * A card that cancels the next incoming card effect used.
 */
public class CancelCard extends ActionCard implements AppliesCancel {

    /*
     * Creates a cancel card.
     */
    public CancelCard() {
        super(0);
    }

    /*
     * Gives points to the current player and activates the cancel effect.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers, ArrayList<Card> deck) {

        System.out.println(currentPlayer.getName() + " played " + this);

        cancelCardEffect(currentPlayer);

        System.out.println(currentPlayer.getName() + " will now cancel the incoming card effect!");
    }

    /**
     * Activates the cancel effect for the current player
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
        return "Cancel Card { point value: " + getPointValue() + " }";
    }
}