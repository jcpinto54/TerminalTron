package Controller.State;

import Controller.Game;
import Controller.ScoresController;
import Model.Score;
import Model.Timer;
import View.State.GameOverView;
import View.State.MainMenuView;
import View.State.MenuView;

import java.io.IOException;


public class GameOverState extends GameState {
    GameOverView view;
    ScoresController scoresController;

    public GameOverState(Game game, GameOverView view, ScoresController controller) {
        super(game);
        this.view = view;
        this.scoresController = controller;
    }

    @Override
    public void draw() throws IOException {
        view.draw(game);
    }

    @Override
    public boolean processKey() throws IOException {
        MenuView.SELECTED command = view.getKey(game);
        return command != MenuView.SELECTED.NULL;
    }

    @Override
    public boolean run() {
        try {
            draw();
            if (processKey()) {
                this.scoresController.readScores();
                int winner = this.game.getArena().getWinner();
                if (game.getArena().getWinner() == -1)
                    this.scoresController.addScore(new Score(winner, 0));
                else this.scoresController.addScore(new Score(winner, this.game.getArena().getPlayers().get(winner - 1).getPoints()));
                this.scoresController.sortScores();
                this.scoresController.writeHighscores();

                game.resetGame();
                game.setGameState(new MenuState(game, new MainMenuView()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
