import java.util.ArrayList;

/**
 * A card that deals damage to another player.
 */
public class AttackCard extends Card implements DealsDamage {

    private int attackDamage;

    /**
     * Creates an attack card with random point value and random damage.
     */
    public AttackCard() {
        super(Rand.randomInt(3, 5));

        int minAttackDamage = 5;
        int maxAttackDamage = 8;
        this.attackDamage = Rand.randomInt(minAttackDamage, maxAttackDamage + 1);
    }

    /**
     * Gives points to the current player and attacks one other player.
     */
    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(getPointValue());

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for the AttackCard to damage.");
            return;
        }

        Player otherPlayer = null;

        while (otherPlayer == null || otherPlayer == currentPlayer) {
            int randomPlayerIndex = Rand.randomInt(0, allPlayers.size());
            otherPlayer = allPlayers.get(randomPlayerIndex);
        }

        if (otherPlayer.consumeCancel()) {
            System.out.println(otherPlayer.getName() + " used a cancel card!");
            System.out.println("The attack was stopped.");
            return;
        }

        doDamage(currentPlayer, otherPlayer);
    }

    /**
     * Deals damage to the chosen player.
     */
    @Override
    public void doDamage(Player currentPlayer, Player playerToDamage) {
        playerToDamage.removePoints(attackDamage);

        System.out.println();
        System.out.println(currentPlayer.getName() + " did " + attackDamage + " damage to " + playerToDamage.getName() + ".");
        System.out.println(playerToDamage.getName() + " now has " + playerToDamage.getNumPoints() + " points.");
        System.out.println();
    }

    /**
     * Returns a text description of this card.
     */
    @Override
    public String toString() {
        return "Attack Card { point value: " + getPointValue() + ", damage: " + attackDamage + " }";
    }
}