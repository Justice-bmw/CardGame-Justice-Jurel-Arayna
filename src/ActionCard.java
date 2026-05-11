import java.util.ArrayList;

public abstract class ActionCard extends Card {

    private int pointValue;

    public ActionCard(int pointValue) {
        this.pointValue = pointValue;
    }

    public int getPointValue() {
        return pointValue;
    }

    public abstract void play(Player currentPlayer, ArrayList<Player> allPlayers);
}
