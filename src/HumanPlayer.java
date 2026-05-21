import java.util.ArrayList;

/**
 * A human-controlled player.
 * On each turn the player is shown their hand and prompted to pick a card by number.
 */
public class HumanPlayer extends Player {

    /**
     * Creates a human player with the given name.
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Asks the player whether to play a card or just draw, then handles their choice.
     */
    public void playRandomCardFromHand(ArrayList<Player> players, ArrayList<Card> deck) {
        displayStatus();

        // Ask whether the player wants to play a card or just draw
        int action = Input.getUserInt("What would you like to do?\n  1: Play a card\n  2: Just draw (skip playing)\n> ");

        while (action != 1 && action != 2) {
            action = Input.getUserInt("Invalid choice. Enter 1 to play or 2 to draw.\n> ");
        }

        if (action == 2) {
            System.out.println(getName() + " chose to just draw.");
            return;
        }

        // Action 1 — pick a card from hand to play
        int choice = Input.getUserInt("Choose a card to play (1-" + handSize() + "): ");

        while (choice < 1 || choice > handSize()) {
            choice = Input.getUserInt("Invalid choice. Enter a number between 1 and " + handSize() + ": ");
        }

        // Convert to zero-based index and remove from hand
        Card chosen = removeCardAt(choice - 1);

        System.out.println(getName() + " plays: " + chosen);

        chosen.play(this, players, deck);

        // if (chosen instanceof ActionCard) {
        //     System.out.println(this.toString());
        //     ActionCard temp = (ActionCard)chosen;
        //     temp.play(this, players, deck);
        // } else if (chosen instanceof Card) {
        //     chosen.play(this, players, deck);
        // }

        return;
    }
}