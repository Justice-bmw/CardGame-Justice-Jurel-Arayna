import java.util.ArrayList;

public class JokerCard extends Card {
    @Override
    public void play(Player currentPlayer, ArrayList<Player> players, ArrayList<Card> deck) {
        // ** Only added the cancel effect in

        Player otherPlayer = players.get(Rand.randomInt(0, players.size()));

        // Stop the joker card effect if the target has a cancel card active
        if (otherPlayer.consumeCancel()) {
            System.out.println(otherPlayer.getName() + " used a cancel card!");
            System.out.println("The joker effect was stopped.");
            return;
        }

        System.out.println(currentPlayer.getName() + " played " + this);

        forcedDraw(deck, otherPlayer);
    }

    public void forcedDraw(ArrayList<Card> deck, Player otherPlayer) {
        int randomCardIndex = Rand.randomInt(0, deck.size());
        Card randomCard = deck.remove(randomCardIndex);
        
        otherPlayer.addCardToHand(randomCard);
        System.out.println(otherPlayer.getName() + " got forced to draw a card!");
    }

    @Override
    public String toString() {
        return "Joker Card";
    }
}
