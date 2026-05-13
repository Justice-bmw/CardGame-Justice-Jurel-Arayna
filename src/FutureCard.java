import java.util.ArrayList;

public class FutureCard extends Card {

    @Override
    public void play(ArrayList<?> deck) {
        for (int i = 0; i < 3; i++) {
            System.out.println(deck.get(i));
        }
    }
}
