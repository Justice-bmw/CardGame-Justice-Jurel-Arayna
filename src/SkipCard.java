import java.util.ArrayList;

/**
 * Card that allows a player to skip their own next turn.
 */
public class SkipCard extends ActionCard implements SkipsTurn {

    /**
     * Creates a SkipCard with a random point value.
     */
    public SkipCard() {
        // Choose the point value that this card gives
        super(Rand.randomInt(5, 7 + 1));
    }

    /**
     * Gives points to the current player and makes them skip their next turn.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        // Give the player the points from this card
        currentPlayer.addPoints(getPointValue());

        // Display the card and updated point total
        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        // Stop the skip effect if the player has cancel active
        if (currentPlayer.consumeCancel()) {
            System.out.println(currentPlayer.getName() + " used a cancel card!");
            System.out.println("The skip effect was stopped.");
            return;
        }

        // Make the player skip their next turn
        skipTurn(currentPlayer, currentPlayer);

        System.out.println();
    }

    /**
     * Marks the selected player so they skip their next turn.
     */
    @Override
    public void skipTurn(Player currentPlayer, Player playerToSkip) {
        // Mark the player as skipped
        playerToSkip.skipTurn();

        // Show the result
        System.out.println(playerToSkip.getName() + " will skip their next turn!");
    }

    /**
     * Returns a text description of this card.
     */
    @Override
    public String toString() {
        return "Skip Card { point value: " + getPointValue() + " }";
    }
}