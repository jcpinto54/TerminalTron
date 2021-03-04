import Controller.Game;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            TerminalSize terminalSize = new TerminalSize(100, 50 + 6);

            DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

            File fontFile = new File("src/main/resources/square.ttf");
            Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            // Se quiserem usar uma font do sistema
            // Font systemFont = new Font("courier", Font.PLAIN, 30);

            Font loadedFont = font.deriveFont(Font.PLAIN, 10);
            AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
            defaultTerminalFactory.setForceAWTOverSwing(true);
            defaultTerminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);

            Terminal terminal = defaultTerminalFactory.setInitialTerminalSize(terminalSize).createTerminal();
            Screen screen = new TerminalScreen(terminal);

            screen.setCursorPosition(null);   // we don't need a cursor
            screen.startScreen();             // screens must be started
            screen.doResizeIfNecessary();     // resize screen if necessary
            Game game = new Game(100, 50, screen);
            game.start();
        }
        catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}