public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        String name = Input.getUserString("What is your name?\n> ");

        game.registerPlayer(new HumanPlayer(name));
        game.registerPlayer(new ComputerPlayer("Justice"));
        game.registerPlayer(new ComputerPlayer("Jurel"));

        game.run();
    }
}