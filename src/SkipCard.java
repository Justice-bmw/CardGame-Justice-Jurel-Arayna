import java.util.ArrayList;

// Card that allows a player to skip their own next turn. (Made by Justice)
public class SkipCard extends ActionCard implements SkipsTurn {

    /**
     * Creates a SkipCard with a random point value.
     */
    public SkipCard() {

        // Points gained from playing this card
        int minPoints = 5;
        int maxPoints = 7;

        int pointValue = Rand.randomInt(minPoints, maxPoints + 1);

        // Call ActionCard constructor
        super(pointValue);
    }

    /**
     * Plays the SkipCard.
     * The current player gains points
     * and skips their next turn.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {

        // Give points to the player
        currentPlayer.addPoints(super.getPointValue());

        // Display card information
        System.out.println(currentPlayer.getName()
                + " played " + this);

        System.out.println(currentPlayer.getName()
                + " now has "
                + currentPlayer.getNumPoints()
                + " points.");

        // Skip the player's next turn
        skipTurn(currentPlayer, currentPlayer);

        System.out.println();
    }

    /**
     * Causes the selected player
     * to skip their next turn.
     */
    @Override
    public void skipTurn(Player currentPlayer, Player playerToSkip) {

        // Mark the player as skipped
        playerToSkip.skipTurn();

        // Display status message
        System.out.println(playerToSkip.getName()
                + " will skip their next turn!");
    }

    // Returns a String representation of the card.
    @Override
    public String toString() {

        return "Skip Card { point value: "
                + super.getPointValue()
                + " }";
    }
}
