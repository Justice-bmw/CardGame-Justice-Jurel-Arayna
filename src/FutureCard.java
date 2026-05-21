import java.util.ArrayList;

public class FutureCard extends Card {

    @Override
    public void play(Player currentPlayer, ArrayList<Player> players, ArrayList<Card> deck) {


        System.out.println(currentPlayer.getName() + " played " + this);

        System.out.println("\n[TOP 3] -----------------");
        for (int i = 0; i < 3; i++) {
            System.out.println(deck.get(i));
        }
        System.out.println("-------------------------\n");
    }

    @Override
    public String toString() {
        return "Future Card";
    }
}