import java.util.ArrayList;

/**
 * A card that steals one random card from another player.
 */
public class ThiefCard extends ActionCard {

    /**
     * Creates a thief card with a random point value.
     */
    public ThiefCard() {
        super(Rand.randomInt(2, 4 + 1));
    }

    /**
     * Gives points to the current player and steals a card from another player.
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
            System.out.println("Error: No other players to steal from.");
            return;
        }

        Player otherPlayer = null;

        // Keep selecting until another player is chosen
        while (otherPlayer == null || otherPlayer == currentPlayer) {
            int randomPlayerIndex = Rand.randomInt(0, allPlayers.size());
            otherPlayer = allPlayers.get(randomPlayerIndex);
        }

        // Stop the steal effect if the target has cancel active
        if (otherPlayer.consumeCancel()) {
            System.out.println(otherPlayer.getName() + " used a cancel card!");
            System.out.println("The steal effect was stopped.");
            return;
        }

        // Stop if the target has no cards
        if (!otherPlayer.hasCardsInHand()) {
            System.out.println(otherPlayer.getName() + " has no cards to steal.");
            return;
        }

        // Remove a random card from the target player
        ActionCard stolenCard = otherPlayer.removeRandomCard();

        // Add the stolen card to the current player's hand
        currentPlayer.addCardToHand(stolenCard);

        // Show the steal result
        System.out.println(currentPlayer.getName() + " stole a card from " + otherPlayer.getName() + "!");
    }

    /**
     * Returns a text description of this card.
     */
    @Override
    public String toString() {
        return "Thief Card { point value: " + getPointValue() + " }";
    }
}