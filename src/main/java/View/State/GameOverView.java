package View.State;

import Controller.Game;
import Model.ArenaModel;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class GameOverView extends MenuView {
    @Override
    public void draw(Game game) throws IOException {
        TextGraphics graphics = game.getScreen().newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#192833"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(game.getWidth(), game.getHeight() + 6), ' ');

        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(game.getWidth()/2 - 11, game.getHeight() - game.getHeight() / 4, "Press ENTER to continue");
        graphics.enableModifiers(SGR.BLINK);
        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
        graphics.putString(game.getWidth()/2 - 4, game.getHeight()/2, "GAME OVER");
        graphics.disableModifiers(SGR.BLINK);

        int winner = game.getArena().getWinner();
        if (game.getArena().getWinner() != -1) {
            graphics.setForegroundColor(TextColor.Factory.fromString(game.getArena().getPlayers().get(winner - 1).getCar().getColor().getStr()));
            graphics.putString(game.getWidth() / 2 - 13, game.getHeight() - 19, "Player " + winner + " WON with " + game.getArena().getPlayers().get(winner - 1).getPoints() + " points!");
        }
        else {
            graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
            graphics.putString(game.getWidth() / 2 - 9, game.getHeight() - 19, "Game ended in a tie!");
        }
        graphics.disableModifiers(SGR.BOLD);

        game.getScreen().refresh();
    }

    @Override
    public SELECTED getKey(Game game) throws IOException {
        if (game.getScreen().readInput().getKeyType() == KeyType.Enter)
            return SELECTED.EXIT;
        return SELECTED.NULL;
    }
}
