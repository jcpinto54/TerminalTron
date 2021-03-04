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

public class PauseView extends MenuView {
    @Override
    public void draw(Game game) throws IOException {
        TextGraphics graphics = game.getScreen().newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#192833"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(game.getWidth(), game.getHeight() + 6), ' ');
        drawOptions(graphics, game.getWidth(), game.getHeight());

        game.getScreen().refresh();
    }

    @Override
    public SELECTED getKey(Game game) throws IOException {
        KeyStroke key;
        do {
            game.getArena().getTimer().update();
            key = game.getScreen().pollInput();
            if (key == null) continue;
            switch (key.getKeyType()) {
                case ArrowDown:
                    moveDownSelected(2);
                    break;
                case ArrowUp:
                    moveUpSelected(2);
                    break;
                case Enter:
                    if (selected == 0) return SELECTED.ENTER;
                    if (selected == 1) return SELECTED.EXIT;
            }
            return SELECTED.NULL;
        } while(true);
    }

    private void drawOptions(TextGraphics graphics, int width, int height) {
        graphics.enableModifiers(SGR.BOLD);

        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.GREEN.getStr()));
        for (int i = width/2-8; i <= width/2+8; i++) {
            graphics.putString(new TerminalPosition(i, height/2 - 5), "o");
            graphics.putString(new TerminalPosition(i, height/2 + 5), "o");
        }
        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.RED.getStr()));
        for (int i = height/2 - 5; i <= height/2 + 5; i++) {
            graphics.putString(new TerminalPosition(width/2 - 8, i), "-");
            graphics.putString(new TerminalPosition(width/2 + 8, i), "-");
        }

        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
        graphics.putString(new TerminalPosition(width/2 - 3, height/2 - 3), "PAUSED");
        if (selected == 0) {
            graphics.putString(new TerminalPosition(width/2 - 4, height/2 + 2), "MAIN MENU");
            graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.RED.getStr()));
            graphics.enableModifiers(SGR.BLINK);
            graphics.putString(new TerminalPosition(width/2 - 3, height/2), "RESUME");
        }
        else {
            graphics.putString(new TerminalPosition(width/2 - 3, height/2), "RESUME");
            graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.RED.getStr()));
            graphics.enableModifiers(SGR.BLINK);
            graphics.putString(new TerminalPosition(width/2 - 4, height/2 + 2), "MAIN MENU");
        }
        graphics.disableModifiers(SGR.BLINK);
        graphics.disableModifiers(SGR.BOLD);
    }
}
