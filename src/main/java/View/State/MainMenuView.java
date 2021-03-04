package View.State;

import Controller.Game;
import Model.ArenaModel;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

public class MainMenuView extends MenuView {
    @Override
    public void draw(Game game) throws IOException {
        TextGraphics graphics = game.getScreen().newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#192833"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(game.getWidth(), game.getHeight() + 6), ' ');
        drawName(graphics, game.getWidth(), game.getHeight());
        drawOptions(graphics, game.getWidth(), game.getHeight());

        game.getScreen().refresh();
    }

    @Override
    public SELECTED getKey(Game game) throws IOException {
        KeyStroke key = game.getScreen().readInput();
        switch(key.getKeyType()) {
            case ArrowDown:
                moveDownSelected(4);
                break;
            case ArrowUp:
                moveUpSelected(4);
                break;
            case Enter:
                if (selected == 0) {
                    return SELECTED.ENTER;
                }
                else if (selected == 1) {
                    return SELECTED.OPTIONS;
                }
                else if (selected == 2) {
                    return SELECTED.HIGHSCORES;
                }
                else if (selected == 3) {
                    return SELECTED.EXIT;
                }
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

        graphics.putString(new TerminalPosition(width/2 - 13, height/2 - 10), "A Terminal-like Tron Game!");
        graphics.disableModifiers(SGR.BOLD);
    }

    private void drawOptions(TextGraphics graphics, int width, int height) {
        graphics.enableModifiers(SGR.BOLD);

        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.GREEN.getStr()));
        for (int i = width/2-8; i <= width/2+8; i++) {
            graphics.putString(new TerminalPosition(i, height - 15), "o");
            graphics.putString(new TerminalPosition(i, height - 5), "o");
        }
        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.RED.getStr()));
        for (int i = height - 15; i <= height - 5; i++) {
            graphics.putString(new TerminalPosition(width/2 - 8, i), "-");
            graphics.putString(new TerminalPosition(width/2 + 8, i), "-");
        }

        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.RED.getStr()));
        graphics.enableModifiers(SGR.BLINK);
        if (selected == 0) {
            graphics.putString(new TerminalPosition(width/2 - 5, height - 13), "PLAY");
            graphics.disableModifiers(SGR.BLINK);
            graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
            graphics.putString(new TerminalPosition(width/2 - 5, height - 11), "OPTIONS");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 9), "HIGHSCORES");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 7), "EXIT GAME");
        }
        else if (selected == 1) {
            graphics.putString(new TerminalPosition(width/2 - 5, height - 11), "OPTIONS");
            graphics.disableModifiers(SGR.BLINK);
            graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
            graphics.putString(new TerminalPosition(width/2 - 5, height - 13), "PLAY");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 9), "HIGHSCORES");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 7), "EXIT GAME");
        }
        else if (selected == 2) {
            graphics.putString(new TerminalPosition(width/2 - 5, height - 9), "HIGHSCORES");
            graphics.disableModifiers(SGR.BLINK);
            graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
            graphics.putString(new TerminalPosition(width/2 - 5, height - 13), "PLAY");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 11), "OPTIONS");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 7), "EXIT GAME");
        }
        else {
            graphics.putString(new TerminalPosition(width/2 - 5, height - 7), "EXIT GAME");
            graphics.disableModifiers(SGR.BLINK);
            graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
            graphics.putString(new TerminalPosition(width/2 - 5, height - 13), "PLAY");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 11), "OPTIONS");
            graphics.putString(new TerminalPosition(width/2 - 5, height - 9), "HIGHSCORES");
        }
        graphics.disableModifiers(SGR.BOLD);
    }
}
