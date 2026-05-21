import java.util.ArrayList;

/**
 * A CPU-controlled player.
 * Uses a simple priority-based strategy to decide which card to play each turn.
 *
 * Priority order:
 *   1. Play a Defence card if the human is winning by a wide margin.
 *   2. Play an Attack or Freeze card if any opponent is ahead.
 *   3. Play a Point card when behind or roughly even.
 *   4. Fall back to a random card from hand.
 */
public class ComputerPlayer extends Player {

    /** How many points the CPU must be trailing before it prioritises defence. */
    private static int DEFENCE_THRESHOLD = 20;

    /** How many points the CPU must be trailing before it attacks instead of farming. */
    private static int ATTACK_THRESHOLD = 5;

    /**
     * Creates a computer player with the given name.
     */
    public ComputerPlayer(String name) {
        super(name);
    }

    /**
     * Displays the CPU's status without revealing its hand.
     */
    @Override
    public void displayStatus() {
        System.out.println(" | ----- " + getName() + " (CPU) ----- ");
        System.out.println(" | Points: " + getNumPoints());

        if (isFrozen()) {
            System.out.println(" | *FROZEN*");
        }

        if (isSkipped()) {
            System.out.println(" | *SKIP NEXT TURN*");
        }

        System.out.println(" | Cards in hand: " + handSize() + " (hidden)");
        System.out.println(" | ----- ----- ----- ");
    }

    /*
     * Chooses and plays a card using a priority-based strategy.
     */
    public void playRandomCardFromHand(ArrayList<Player> players, ArrayList<Card> deck) {

        Card chosen = chooseCard(players);

        System.out.println("[CPU] " + getName() + " plays: " + chosen);
        chosen.play(this, players, deck);
    }

    /*
     * Selects the highest-priority card from the CPU's hand.
     * Falls back to a random card if no strategic pick applies.
     */
    private Card chooseCard(ArrayList<Player> players) {

        int leadingOpponentScore = highestOpponentScore(players);
        int deficit = leadingOpponentScore - getNumPoints();

        // Priority 1 — use Defence when badly behind
        if (deficit >= DEFENCE_THRESHOLD) {
            Card defence = findCardByName("Defence");
            if (defence != null) return removeCard(defence);
        }

        // Priority 2 — attack or freeze when any opponent is ahead
        if (deficit >= ATTACK_THRESHOLD) {
            Card freeze = findCardByName("Freeze");
            if (freeze != null) return removeCard(freeze);

            Card attack = findCardByName("Attack");
            if (attack != null) return removeCard(attack);
        }

        // Priority 3 — gain points when roughly even or ahead
        Card point = findCardByName("Point");
        if (point != null) return removeCard(point);

        // Priority 4 — random fallback
        return removeRandomCard();
    }

    /*
     * Returns the highest point total among all opponents.
     */
    private int highestOpponentScore(ArrayList<Player> players) {
        int best = 0;
        for (Player p : players) {
            if (p != this && p.getNumPoints() > best) {
                best = p.getNumPoints();
            }
        }
        return best;
    }

    /*
     * Finds the first card in hand whose name matches (case-insensitive).
     * Returns null if no match is found.
     */
    private Card findCardByName(String name) {
        for (int i = 0; i < handSize(); i++) {
            Card card = peekCard(i);
            if (card.toString().equalsIgnoreCase(name)) {
                return card;
            }
        }
        return null;
    }

    /*
     * Removes a specific card instance from the hand and returns it.
     */
    private Card removeCard(Card target) {
        for (int i = 0; i < handSize(); i++) {
            if (peekCard(i) == target) {
                return removeCardAt(i);
            }
        }
        return removeRandomCard();
    }
}