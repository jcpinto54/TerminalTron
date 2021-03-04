package View.State;

import Controller.Game;
import Model.*;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OptionsView extends MenuView {
    private List<ArenaModel.COLOR> colors =  new ArrayList<>();
    int colorIndex = 0;
    private ArenaModel.COLOR player1Color = ArenaModel.COLOR.GREEN;
    private ArenaModel.COLOR player2Color = ArenaModel.COLOR.RED;
    private char player1Char = 'O';
    private char player2Char = '|';

    public OptionsView() {
        this.addColors(colors);
    }

    public void addColors(List<ArenaModel.COLOR> colors) {
        for (ArenaModel.COLOR color : ArenaModel.COLOR.values()) {
            colors.add(color);
        }
    }

    @Override
    public void draw(Game game) throws IOException {
        TextGraphics graphics = game.getScreen().newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#192833"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(game.getWidth(), game.getHeight() + 6), ' ');
        drawName(graphics, game.getWidth(), game.getHeight());
        drawInterface(graphics, game);

        graphics.putString(new TerminalPosition(game.getWidth()/2 - 12, game.getHeight() + 2), "Press ENTER to save and return");
        graphics.putString(new TerminalPosition(game.getWidth()/2 - 14, game.getHeight() + 4), "Press ESC to return without saving");

        game.getScreen().refresh();
    }

    @Override
    public SELECTED getKey(Game game) throws IOException {
        KeyStroke key = game.getScreen().readInput();
        switch (key.getKeyType()) {
            case ArrowLeft:
                moveUpSelected(4);
                colorIndex = 0;
                break;
            case ArrowRight:
                moveDownSelected(4);
                colorIndex = 0;
                break;
            case ArrowUp:
                if (selected == 0) ColorUp(colors.size(), 1);
                else if (selected == 2) ColorUp(colors.size(), 2);
                else if (selected == 1 || selected == 3) {
                    char p1 = getPlayer1Char();
                    this.player1Char = this.player2Char;
                    this.player2Char = p1;
                }
                break;
            case ArrowDown:
                if (selected == 0) ColorDown(colors.size(), 1);
                else if (selected == 2) ColorDown(colors.size(), 2);
                else if (selected == 1 || selected == 3) {
                    char p1 = getPlayer1Char();
                    this.player1Char = this.player2Char;
                    this.player2Char = p1;
                }
                break;
            case Enter:
                return SELECTED.ENTER;
            case Escape:
                return SELECTED.EXIT;
        }
        return SELECTED.NULL;
    }

    private void drawInterface(TextGraphics graphics, Game game) {
        graphics.enableModifiers(SGR.BOLD);

        graphics.enableModifiers(SGR.BLINK);
        if (selected == 0)
            graphics.putString(new TerminalPosition(game.getWidth()/2 - 44, game.getHeight()/2 - 2),"-->");
        else if (selected == 1)
            graphics.putString(new TerminalPosition(game.getWidth()/2 + 17, game.getHeight()/2 - 2),"-->");
        else if (selected == 2)
            graphics.putString(new TerminalPosition(game.getWidth()/2 - 44, game.getHeight()/2 + 12),"-->");
        else
            graphics.putString(new TerminalPosition(game.getWidth()/2 + 17, game.getHeight()/2 + 12),"-->");
        graphics.disableModifiers(SGR.BLINK);


        graphics.putString(new TerminalPosition(game.getWidth()/2 - 40, game.getHeight()/2 - 2), "Player 1 Car Color");
        graphics.putString(new TerminalPosition(game.getWidth()/2 + 21, game.getHeight()/2 - 2), "Player 1 Type of Trail:");
        graphics.putString(new TerminalPosition(game.getWidth()/2 - 40, game.getHeight()/2 + 12), "Player 2 Car Color");
        graphics.putString(new TerminalPosition(game.getWidth()/2 + 21, game.getHeight()/2 + 12), "Player 2 Type of Trail:");

        graphics.setForegroundColor(TextColor.Factory.fromString(player1Color.getStr()));
        graphics.putString(new TerminalPosition(game.getWidth() / 2 - 40, game.getHeight() / 2 + 1), player1Color.getName());

        graphics.setForegroundColor(TextColor.Factory.fromString(player2Color.getStr()));
        graphics.putString(new TerminalPosition(game.getWidth() / 2 - 40, game.getHeight() / 2 + 15), player2Color.getName());


        graphics.setForegroundColor(TextColor.Factory.fromString(ArenaModel.COLOR.WHITE.getStr()));
        for (int i = 22; i < 44; i++) graphics.putString(new TerminalPosition(game.getWidth()/2 + i, game.getHeight()/2 + 1), String.valueOf(player1Char));

        for (int i = 22; i < 44; i++) graphics.putString(new TerminalPosition(game.getWidth()/2 + i, game.getHeight()/2 + 15), String.valueOf(player2Char));

        if (getPlayer1Char() == 'O') {
            graphics.putString(new TerminalPosition(game.getWidth() / 2 + 30, game.getHeight() / 2 + 3), "(Circle Trail)");
            graphics.putString(new TerminalPosition(game.getWidth() / 2 + 32, game.getHeight() / 2 + 17), "(Line Trail)");
        }
        else {
            graphics.putString(new TerminalPosition(game.getWidth() / 2 + 32, game.getHeight() / 2 + 3), "(Line Trail)");
            graphics.putString(new TerminalPosition(game.getWidth() / 2 + 30, game.getHeight() / 2 + 17), "(Circle Trail)");
        }

        graphics.disableModifiers(SGR.BOLD);
    }

    private void drawName(TextGraphics graphics, int width, int height) {
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 8), "TTTTT RRRRR   OOOOO  NN     N    RRRRR  E E E  MM     MM      A      K  K  E E E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 7), "  T   R    R O     O N N    N    R    R E      M M   M M     A A     K K   E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 6), "  T   R    R O     O N  N   N    R    R E E E  M  M M  M    A   A    KK    E E E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 5), "  T   R R R  O     O N   N  N    R R R  E      M   M   M   AAAAAAA   K K   E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 4), "  T   R R    O     O N    N N    R R    E      M       M  A       A  K  K  E");
        graphics.putString(new TerminalPosition(width/2 - 40, height/4 - 3), "  T   R   R   OOOOO  N     NN    R   R  E E E  M       M A         A K   K E E E");

        graphics.putString(new TerminalPosition(width/2 - 3, height/2 - 10), "OPTIONS");

        graphics.disableModifiers(SGR.BOLD);
    }

    public void ColorUp(int maxColors, int idx) {
        colorIndex--;
        colorIndex %= maxColors;
        if (colorIndex < 0) colorIndex = maxColors + colorIndex;
        if (colors.get(colorIndex) == player1Color || colors.get(colorIndex) == player2Color) {
            ColorUp(maxColors, idx);
            return;
        }
        switch(idx) {
            case 1:
                player1Color = colors.get(colorIndex);
                break;
            case 2:
                player2Color = colors.get(colorIndex);
                break;
        }
    }

    public void ColorDown(int maxColors, int idx) {
        colorIndex++;
        colorIndex %= maxColors;
        if (colors.get(colorIndex) == player2Color || colors.get(colorIndex) == player1Color) {
            ColorDown(maxColors, idx);
            return;
        }
        switch(idx) {
            case 1:
                player1Color = colors.get(colorIndex);
                break;
            case 2:
                player2Color = colors.get(colorIndex);
                break;
        }
    }

    public char getPlayer1Char() {
        return player1Char;
    }

    public char getPlayer2Char() {
        return player2Char;
    }

    public ArenaModel.COLOR getPlayer1Color() {
        return player1Color;
    }

    public ArenaModel.COLOR getPlayer2Color() {
        return player2Color;
    }

    public void setPlayer1Char(char player1Char) {
        this.player1Char = player1Char;
    }

    public void setPlayer2Char(char player2Char) {
        this.player2Char = player2Char;
    }
}
