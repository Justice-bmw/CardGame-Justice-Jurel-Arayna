import java.util.ArrayList;

// A very dangerous card that immediately damages the player who draws or plays it. (Made my Justice)
// The amount of damage is determined by rolling a six-sided die.

public class ChaosCard extends ActionCard implements DealsDamage {

    // Amount of damage dealt by the card
    private int damage;

    /**
     * Creates a ChaosCard.
     * Damage is randomly generated from 1 to 6.
     */
    public ChaosCard() {

        // Simulate rolling a 6-sided die
        int minDamage = 1;
        int maxDamage = 6;

        damage = Rand.randomInt(minDamage, maxDamage + 1);

        // This card gives no points
        int pointValue = 0;

        // Call ActionCard constructor
        super(pointValue);
    }

    /**
     * Plays the ChaosCard.
     * The current player immediately takes damage.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {

        System.out.println(currentPlayer.getName()
                + " played " + this);

        // Damage the current player
        doDamage(currentPlayer, currentPlayer);

        System.out.println();
    }

    /**
     * Removes points from the selected player.
     */
    @Override
    public void doDamage(Player currentPlayer, Player playerToDamage) {

        playerToDamage.removePoints(damage);

        System.out.println(playerToDamage.getName()
                + " lost "
                + damage
                + " points!");

        System.out.println(playerToDamage.getName()
                + " now has "
                + playerToDamage.getNumPoints()
                + " points.");
    }

    /**
     * Returns a String representation of the card.
     */
    @Override
    public String toString() {

        return "Chaos Card { damage: "
                + damage
                + " }";
    }
}
