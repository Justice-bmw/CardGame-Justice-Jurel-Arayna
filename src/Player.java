import java.util.ArrayList;

/**
 * Represents one player in the game.
 * Stores the player's name, hand, points, and status effects.
 */
public class Player {

    private String name;
    private ArrayList<Card> hand;
    private int numPoints;
    private boolean isFrozen;
    private boolean hasCancelActive = false;
    private boolean skipped;

    /**
     * Creates a player with the given name.
     * Each player starts with five points and an empty hand.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.numPoints = 5;
        this.isFrozen = false;
        this.skipped = false;
    }

    /**
     * Removes a random action card from the hand and plays it.
     */
    public void playRandomCardFromHand(ArrayList<Player> players, ArrayList<Card> deck) {

        // Select a random card from the hand
        int randomCardIndex = Rand.randomInt(0, hand.size());

        // Remove the selected card from the hand
        ActionCard randomCard = (ActionCard)hand.remove(randomCardIndex);

        // Play the card
        randomCard.play(this, players, deck);
    }

    /**
     * Checks whether the player currently has at least one card in hand.
     */
    public boolean hasCardsInHand() {
        return hand.size() > 0;
    }

    /**
     * Adds one action card to the player's hand.
     */
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    /**
     * Returns whether the player is frozen.
     */
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Marks the player as frozen.
     */
    public void freeze() {
        isFrozen = true;
    }

    /**
     * Removes the frozen status from the player.
     */
    public void unfreeze() {
        isFrozen = false;
    }
// No extra action is needed here.
    /**
     * Removes and returns one random card from the hand.
     * Returns null when the hand is empty.
     */
    public Card removeRandomCard() {
        if (hand.size() == 0) {
            return null;
        }

        int randomCardIndex = Rand.randomInt(0, hand.size());
        return hand.remove(randomCardIndex);
    }

    /**
     * Returns the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds the given number of points to the player.
     * If the total becomes negative, the total is reset to zero.
     */
    public void addPoints(int pointsToAdd) {
        numPoints += pointsToAdd;

        if (numPoints < 0) {
            numPoints = 0;
        }
    }

    /**
     * Removes the given number of points from the player.
     */
    public void removePoints(int pointsToRemove) {
        addPoints(-pointsToRemove);
    }

    /**
     * Returns the player's current point total.
     */
    public int getNumPoints() {
        return numPoints;
    }

    /**
     * Prints the player's current status, including points and cards in hand.
     */
    public void displayStatus() {
        System.out.println(" | ----- " + name + " ----- ");
        System.out.println(" | Points: " + numPoints);

        if (isFrozen) {
            System.out.println(" | *FROZEN*");
        }

        if (skipped) {
            System.out.println(" | *SKIP NEXT TURN*");
        }

        System.out.println(" | Cards in hand:");

        for (int i = 0; i < hand.size(); i++) {
            System.out.print(" | " + (i + 1) + ": ");
            System.out.println(hand.get(i));
        }

        System.out.println(" | ----- ----- ----- ");
    }

    /**
     * Activates the cancel shield so the next incoming attack can be blocked.
     */
    public void activateCancel() {
        hasCancelActive = true;
    }

    /**
     * Uses the cancel shield if it is active.
     * Returns true when the shield was consumed.
     */
    public boolean consumeCancel() {
        if (hasCancelActive) {
            hasCancelActive = false;
            return true;
        }
        return false;
    }

    public void skipTurn() {
        skipped = true;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void unskipTurn() {
        skipped = false;
    }

    // Returns how many cards are in hand
    public int handSize() {
    return hand.size();
    }

    // Peeks at a card by index without removing it
    public Card peekCard(int index) {
    return (Card) hand.get(index);
    }

    // Removes and returns a card at a specific index
    public Card removeCardAt(int index) {
    return (Card) hand.remove(index);
    }
}