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
        // mixedDeck = new ArrayList<ActionCard>();
        // damageDeck = new ArrayList<DealsDamage>();

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

        // deal cards to each player
        int cardsAdded = 0;
        while (cardsAdded < startingHandSize) {
            for (int i = 0; i < players.size(); i++) {
                Card randomCard = deck.get(i);
                deck.remove(i);
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
            currentPlayer.displayStatus();
            Input.waitForUserToPressEnter("\nPress Enter to play " + currentPlayer.getName() + "'s turn.");

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

            // generate a random value to choose a random action
            float randomValue = Rand.random();

            // 1. play a card from player's hand
            if (randomValue < playerChancesOfPlayingCard && currentPlayer.hasCardsInHand()) {
                currentPlayer.playRandomCardFromHand(players);
            }

            // 2. OR draw a card from mixed deck (but don't play it yet)
            else if (deck.size() > 0 && randomValue < playerChancesOfPlayingCard) {
                Object drawnObject = drawRandomCard(deck);
                ActionCard drawnCard = (ActionCard)drawnObject;
                currentPlayer.addCardToHand(drawnCard);

                System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " from the Mixed deck.");
            }

            // 3. OR draw a card from damage deck and use its damage effect immediately, without getting points
            else {
                Object drawnObject = drawRandomCard(deck);

                if (drawnObject instanceof DealsDamage) {
                    DealsDamage damageCard = (DealsDamage)drawnObject;

                    boolean selectedAnotherPlayer = false;
                    Player otherPlayer = null;

                    while (!selectedAnotherPlayer) {
                        int randomPlayerIndex = Rand.randomInt(0, players.size());
                        otherPlayer = players.get(randomPlayerIndex);
                        if (otherPlayer != currentPlayer) {
                            selectedAnotherPlayer = true;
                        }
                    }
                    if (drawnObject instanceof ChaosCard) {
                        ((ActionCard)drawnObject).play(currentPlayer, players);
                    }


                    damageCard.doDamage(currentPlayer, otherPlayer);
                    if (damageCard instanceof AppliesFreeze) {
                        AppliesFreeze freezeCard = (AppliesFreeze)damageCard;
                        freezeCard.freeze(currentPlayer, otherPlayer);
                    }
                }
            }

            Input.waitForUserToPressEnter("\nPress Enter to end " + currentPlayer.getName() + "'s turn.\n");
        }

        // End game: determine which Player had the most points
        declareWinner();
    }

    // Randomly selects a reference (Card or DealsDamage) from an ArrayList (mixedDeck or damageDeck).
    // Removes the randomly selected reference from the specified ArrayList.
    // Returns the selected reference as an Object (because we don't know what type the ArrayList stores).
    private Object drawRandomCard(ArrayList<?> arrayList) {
        int randomCardIndex = Rand.randomInt(0, arrayList.size());
        Object randomCard = arrayList.remove(randomCardIndex);
        return randomCard;
    }

    // Initializes the settings fields.
    private void setGameSettings() {
        // Player settings
        startingHandSize = 3;
        playerChancesOfPlayingCard = 0.5f; // 50% play card, 25% draw card from mixed, 25% draw card from damage deck and play immediately

        // Deck settings
        totalNumberOfCards = 40;

        weightedChance = 1.47f;

        pointCardChances = 0.4f;
        chaosCardChances = 0.3f;
        defenseCardChances = 0.2f;
        cancelCardChances = 0.2f;
        futureCardChances = 0.2f;
        attackCardChances = 0.15f;
        freezeCardChances = 0.12f;
        thiefCardChances = 0.1f;
        jokerCardChances = 0.1f;
        skipCardChances = 0.1f;
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
                CancelCard newCancelCard = new CancelCard();

                deck.add(newCancelCard);
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
