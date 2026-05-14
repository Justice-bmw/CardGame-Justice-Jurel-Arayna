import java.util.ArrayList;

/**
 * A card that freezes another player.
 * Frozen players lose their next turn.
 */
public class FreezeCard extends ActionCard implements AppliesFreeze {

    /**
     * Creates a freeze card with a random point value.
     */
    public FreezeCard() {
        super(Rand.randomInt(2, 4 + 1));
    }

    /**
     * Gives points to the current player and freezes another player.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {

        // Give the current player the card's point value
        currentPlayer.addPoints(getPointValue());

        // Show the played card and updated points
        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        // Stop if there are not enough players
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players to freeze.");
            return;
        }

        Player otherPlayer = null;

        // Keep choosing until a different player is selected
        while (otherPlayer == null || otherPlayer == currentPlayer) {
            int randomPlayerIndex = Rand.randomInt(0, allPlayers.size());
            otherPlayer = allPlayers.get(randomPlayerIndex);
        }

        // Stop the freeze effect if the target has a cancel card active
        if (otherPlayer.consumeCancel()) {
            System.out.println(otherPlayer.getName() + " used a cancel card!");
            System.out.println("The freeze effect was stopped.");
            return;
        }

        // Apply the freeze effect
        freeze(currentPlayer, otherPlayer);
    }

    /**
     * Freezes the chosen player.
     */
    @Override
    public void freeze(Player currentPlayer, Player playerToFreeze) {

        // Freeze the target player
        playerToFreeze.freeze();

        // Show the freeze result
        System.out.println(playerToFreeze.getName() + " is now frozen!");
    }

    /**
     * Returns a text description of this card.
     */
    @Override
    public String toString() {
        return "Freeze Card { point value: " + getPointValue() + " }";
    }
}