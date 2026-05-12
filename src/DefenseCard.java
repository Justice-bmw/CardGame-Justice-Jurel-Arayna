import java.util.ArrayList;

/**
 * A card that gives a player extra points through a defense effect.
 */
public class DefenseCard extends ActionCard implements AppliesDefense {

    private int defensePoints;

    /**
     * Creates a defense card with random point value and random defense strength.
     */
    public DefenseCard() {
        super(Rand.randomInt(1, 3 + 1));
        defensePoints = Rand.randomInt(2, 4 + 1);
    }

    /**
     * Gives points to the current player and then applies the defense effect.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(super.getPointValue());

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        defendPlayer(currentPlayer);
    }

    /**
     * Gives the chosen player the defense points from this card.
     */
    @Override
    public void defendPlayer(Player playerToDefend) {
        playerToDefend.addPoints(defensePoints);
        System.out.println(playerToDefend.getName() + " defended and gained " + defensePoints + " points.");
        System.out.println(playerToDefend.getName() + " now has " + playerToDefend.getNumPoints() + " points.\n");
    }

    /**
     * Returns a text description of this card.
     */
    @Override
    public String toString() {
        return "Defense Card { point value: " + super.getPointValue() + ", defense: " + defensePoints + "}";
    }
}