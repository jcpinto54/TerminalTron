package View.State;

import Controller.Game;
import Controller.ScoresController;
import Model.ArenaModel;
import Model.Score;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.*;
import java.util.List;

import static java.lang.Integer.min;

public class HighScoresView extends MenuView {
    private ScoresController scoresController = new ScoresController();

    @Override
    public void draw(Game game) throws IOException {
        TextGraphics graphics = game.getScreen().newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#192833"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(game.getWidth(), game.getHeight() + 6), ' ');
        drawName(graphics, game.getWidth(), game.getHeight());

        graphics.putString(new TerminalPosition(game.getWidth()/2 - 14, game.getHeight() + 4), "Press ENTER or ESC to return");

        scoresController.readScores();
        scoresController.sortScores();
        List<Score> scores = scoresController.readScores();
        graphics.putString(new TerminalPosition(game.getWidth()/2 - 8, game.getHeight()/2 + 4), "Winner");
        graphics.putString(new TerminalPosition(game.getWidth()/2 + 4, game.getHeight()/2 + 4), "Score");
        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
        graphics.enableModifiers(SGR.BOLD);
        for (int i = 0, j = 6; i < min(3, scores.size()); i++, j+=2) {
            graphics.putString(new TerminalPosition(game.getWidth()/2 - 9, game.getHeight()/2 + j), "Player " + scores.get(i).getWinner());
            graphics.putString(new TerminalPosition(game.getWidth()/2 + 5, game.getHeight()/2 + j), String.valueOf(scores.get(i).getScore()));
        }
        graphics.disableModifiers(SGR.BOLD);


        game.getScreen().refresh();
    }

    @Override
    public SELECTED getKey(Game game) throws IOException {
        KeyStroke key = game.getScreen().readInput();
        switch (key.getKeyType()) {
            case Escape:
            case Enter:
                return SELECTED.EXIT;
        }
        return SELECTED.NULL;
    }

    private void drawName(TextGraphics graphics, int width, int height) {
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 8), "TTTTT RRRRR   OOOOO  NN     N    RRRRR  E E E  MM     MM      A      K  K  E E E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 7), "  T   R    R O     O N N    N    R    R E      M M   M M     A A     K K   E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 6), "  T   R    R O     O N  N   N    R    R E E E  M  M M  M    A   A    KK    E E E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 5), "  T   R R R  O     O N   N  N    R R R  E      M   M   M   AAAAAAA   K K   E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 4), "  T   R R    O     O N    N N    R R    E      M       M  A       A  K  K  E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 3), "  T   R   R   OOOOO  N     NN    R   R  E E E  M       M A         A K   K E E E");

        graphics.putString(new TerminalPosition(width/2 - 5, height/2 - 10), "HIGH SCORES");

        graphics.disableModifiers(SGR.BOLD);
    }

}
