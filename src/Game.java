import java.util.ArrayList;

public class Game {

    // ----------- Settings ----------- //

    // Player settings
    private int startingHandSize;

    private float playerChancesOfPlayingCard; // % chance (0-1) that a player plays a card from their hand

    // Deck settings
    private int totalNumberOfCards;
    private float pointCardChances; // % chance (from 0-1) of generating a point card
    private float attackCardChances; // % chance (from 0-1) of generating an attack card
    private float freezeCardChances; // % chance (from 0-1) of generating a freeze card
    private float thiefCardChances; // thief card chances are the leftovers of the other chance
    private float futureCardChances;
    private float jokerCardChances;
    private float defenseCardChances;
    private float cancelCardChances;
    private float skipCardChances;
    private float chaosCardChances;
    private float weightedChance;

    // -------- End of Settings ------- //


    // --------- Game Objects --------- //

    private ArrayList<Player> players;
    private ArrayList<Card> deck;

    // ------ End of Game Objects ----- //



    public Game() {
        // Set game settings
        setGameSettings();

        // Game objects
        players = new ArrayList<Player>();
        deck = new ArrayList<Card>();

        // Generate the decks
        generateDecks();
    }

    public void registerPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public int getDeckCount() {
        return deck.size();
    }

    public void run() {

        // deal cards to each player, skipping ChaosCards
        int cardsAdded = 0;
        while (cardsAdded < startingHandSize) {
            for (int i = 0; i < players.size(); i++) {
                Card randomCard = null;

                // Keep drawing until we find a non-ChaosCard
                while (randomCard == null || randomCard instanceof ChaosCard) {
                    int randomIndex = Rand.randomInt(0, deck.size());
                    randomCard = deck.remove(randomIndex);

                    // If it was a ChaosCard, put it at the bottom of the deck instead
                    if (randomCard instanceof ChaosCard) {
                        System.out.println("(ChaosCard skipped during deal — returned to deck.)");
                        deck.add(0, randomCard);
                        randomCard = null;
                    }
                }

                players.get(i).addCardToHand(randomCard);
            }
            cardsAdded += 1;
        }

        int currentPlayerIndex = -1; // will increase to 0 when the loop starts
        Player currentPlayer;

        // game loop -- loop as long as either deck has cards

        while (deck.size() > 0) {

            // switch to next player
            currentPlayerIndex += 1;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
            currentPlayer = players.get(currentPlayerIndex);

            System.out.println("# cards remaining in deck:" + deck.size() + ".\n");

            System.out.println("It's " + currentPlayer.getName() + "'s turn.\n");

            // Only show status and prompt for CPU turns; human sees their hand inside playRandomCardFromHand
            if (!(currentPlayer instanceof HumanPlayer)) {
                currentPlayer.displayStatus();
                Input.waitForUserToPressEnter("\nPress Enter to play " + currentPlayer.getName() + "'s turn.");
            } else {
                currentPlayer.displayStatus();
            }

            // Check if the player is frozen
            if (currentPlayer.isFrozen()) {

                System.out.println(currentPlayer.getName()
                        + " is frozen! Skipping turn.");

                // Remove frozen effect after skipping one turn
                currentPlayer.unfreeze();

                continue;
            }

            // Check if the player must skip their turn
            if (currentPlayer.isSkipped()) {

                System.out.println(currentPlayer.getName()
                        + " skipped their turn!");

                // Remove skip effect after one skipped turn
                currentPlayer.unskipTurn();

                continue;
            }

            // Every player draws a card at the start of their turn
            if (deck.size() > 0) {
                Card drawnCard = (Card) drawCard(deck);

                // ChaosCard triggers immediately on draw — never goes to hand
                if (drawnCard instanceof ChaosCard) {
                    System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " — it triggers instantly!");
                    ActionCard temp = (ActionCard) drawnCard;
                    temp.play(currentPlayer, players, deck);
                } else {
                    currentPlayer.addCardToHand(drawnCard);
                    if (currentPlayer instanceof HumanPlayer) {
                        System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " from the deck.");
                    }
                }
            }

            // Human players always get to choose what to play
            if (currentPlayer instanceof HumanPlayer) {
                if (currentPlayer.hasCardsInHand()) {
                    currentPlayer.playRandomCardFromHand(players, deck);
                }
            }

            // CPU players randomly decide whether to play a card
            else {
                float randomValue = Rand.random();

                if (randomValue < playerChancesOfPlayingCard && currentPlayer.hasCardsInHand()) {
                    currentPlayer.playRandomCardFromHand(players, deck);
                }
            }


        }

        // End game: determine which Player had the most points
        declareWinner();
    }

    // Removes the randomly selected reference from the specified ArrayList.
    // Returns the selected reference as an Object (because we don't know what type the ArrayList stores).
    public Object drawCard(ArrayList<?> arrayList) {
        Card drawnCard = deck.get(0);
        deck.remove(drawnCard);
        return drawnCard;
    }

    // Initializes the settings fields.
    private void setGameSettings() {
        // Player settings
        startingHandSize = 3;
        playerChancesOfPlayingCard = 0.5f; // 50% play card, 25% draw card from mixed, 25% draw card from damage deck and play immediately

        // Deck settings
        totalNumberOfCards = 40;

        weightedChance = 2.37f;

        pointCardChances = 0.4f;
        chaosCardChances = 0.3f;
        defenseCardChances = 0.2f;
        cancelCardChances = 0.2f;
        futureCardChances = 0.4f;
        attackCardChances = 0.15f;
        freezeCardChances = 0.12f;
        thiefCardChances = 0.4f;
        jokerCardChances = 0.4f;
        skipCardChances = 0.4f;
    }

    private void generateDecks() {
        for (int i = 0; i < totalNumberOfCards; i++) {

            

            float randomValue = Rand.randomFloat(0, weightedChance);

            // Going up and down chance, if multiple cards have the same chance, they have a secondary chance, around the same between all.

            // % chance of creating a point card
            if (randomValue <= pointCardChances) {
                deck.add(new PointCard());
            }

            // % chance of creating an attack card
            else if (randomValue <= pointCardChances + chaosCardChances) {
                ChaosCard newChaosCard = new ChaosCard();

                deck.add(newChaosCard);
            }

            // % chance of creating a freeze card
            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances) {
                DefenseCard newDefenseCard = new DefenseCard();

                deck.add(newDefenseCard);
            }

            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances + cancelCardChances) {
                CancelCard newCancelCard = new CancelCard();

                deck.add(newCancelCard);
            }

            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances + cancelCardChances + futureCardChances) {
                FutureCard newFutureCard = new FutureCard();

                deck.add(newFutureCard);
            }

            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances + cancelCardChances + futureCardChances + attackCardChances) {
                AttackCard newAttackCard = new AttackCard();

                deck.add(newAttackCard);
            }

            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances + cancelCardChances + futureCardChances + attackCardChances + freezeCardChances) {
                FreezeCard newFreezeCard = new FreezeCard();

                deck.add(newFreezeCard);
            }

            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances + cancelCardChances + futureCardChances + attackCardChances + freezeCardChances + thiefCardChances) {
                ThiefCard newThiefCard = new ThiefCard();

                deck.add(newThiefCard);
            }

            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances + cancelCardChances + futureCardChances + attackCardChances + freezeCardChances + thiefCardChances + jokerCardChances) {
                JokerCard newJokerCard = new JokerCard();

                deck.add(newJokerCard);
            }

            else if (randomValue <= pointCardChances + chaosCardChances + defenseCardChances + cancelCardChances + futureCardChances + attackCardChances + freezeCardChances + thiefCardChances + jokerCardChances + skipCardChances) {
                SkipCard newSkipCard = new SkipCard();

                deck.add(newSkipCard);
            }
        }
    }

    private void declareWinner() {
        int highestScore = 0;
        Player playerWithHighestScore = null;

        System.out.println("\nFinal Scoreboard:");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println(p.getName() + ": " + p.getNumPoints());

            // update highest score tracker
            if (p.getNumPoints() >= highestScore) {
                highestScore = p.getNumPoints();
                playerWithHighestScore = p;
            }
        }

        System.out.println("Player '" + playerWithHighestScore.getName() + "' wins!");
    }
}