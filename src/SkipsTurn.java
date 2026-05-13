// Interface for cards that cause a player to skip a turn (Made my Justice)
public interface SkipsTurn {

    // Makes the selected player skip their next turn
    public void skipTurn(Player currentPlayer, Player playerToSkip);
}