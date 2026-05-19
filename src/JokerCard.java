public class JokerCard extends Card
{
    public void play()
    {
        // ** Only added the cancel effect in

        // Stop the joker card effect if the target has a cancel card active
        if (otherPlayer.consumeCancel()) {
            System.out.println(otherPlayer.getName() + " used a cancel card!");
            System.out.println("The joker effect was stopped.");
            return;
        }
    }
}
