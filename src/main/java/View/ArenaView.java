package View;

import Controller.MutexSyncronize;
import Model.Car;
import Model.Player;
import Model.Wall;
import Model.ArenaModel;
import Observer.Observer;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class ArenaView implements Observer<ArenaModel> {
    private Screen screen;
    public enum COMMAND {UP1, RIGHT1, DOWN1, LEFT1, UP2, RIGHT2, DOWN2, LEFT2, ESC, NULL}

    public ArenaView(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void changed(ArenaModel arena) {
        drawArena(arena);
    }

    private void drawArena(ArenaModel arena) {
        try {
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();
            graphics.setBackgroundColor(TextColor.Factory.fromString("#192833"));
            graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(arena.getWidth(), arena.getHeight()), ' ');

            MutexSyncronize.lock();
            for (Wall wall : arena.getWalls()) {
                drawWall(graphics, wall);
            }
            graphics.setBackgroundColor(TextColor.Factory.fromString("#192833"));

            for (Player player : arena.getPlayers()) {
                drawCar(graphics, player.getCar());
            }

            drawInfo(graphics, arena);

            MutexSyncronize.unlock();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public COMMAND getCommand() throws IOException {
            KeyStroke key = screen.pollInput();
            if (key == null) return COMMAND.NULL;
            switch (key.getKeyType()) {
                case Escape:
                    return COMMAND.ESC;
                case ArrowUp:
                    return COMMAND.UP2;
                case ArrowRight:
                    return COMMAND.RIGHT2;
                case ArrowDown:
                    return COMMAND.DOWN2;
                case ArrowLeft:
                    return COMMAND.LEFT2;
                case Character:
                    switch(key.getCharacter()) {
                        case 'w':
                            return COMMAND.UP1;
                        case 'd':
                            return COMMAND.RIGHT1;
                        case 'a':
                            return COMMAND.LEFT1;
                        case 's':
                            return COMMAND.DOWN1;
                    }
            }
            return COMMAND.NULL;
    }

    public void drawInfo(TextGraphics graphics, ArenaModel arena) {
        graphics.enableModifiers(SGR.BOLD);

        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));

        graphics.setForegroundColor(TextColor.Factory.fromString(arena.getCar(1).getColor().getStr()));
        graphics.putString(new TerminalPosition(4, arena.getHeight() + 2), "Player 1 Lives: " + arena.getCar(1).getLives());
        graphics.setForegroundColor(TextColor.Factory.fromString(arena.getCar(2).getColor().getStr()));
        graphics.putString(new TerminalPosition(4, arena.getHeight() + 4), "Player 2 Lives: " + arena.getCar(2).getLives());

        graphics.setForegroundColor(TextColor.Factory.fromString(arena.getCar(1).getColor().getStr()));
        graphics.putString(new TerminalPosition(26, arena.getHeight() + 2), "Points: " + arena.getPlayers().get(0).getPoints());
        graphics.setForegroundColor(TextColor.Factory.fromString(arena.getCar(2).getColor().getStr()));
        graphics.putString(new TerminalPosition(26, arena.getHeight() + 4), "Points: " + arena.getPlayers().get(1).getPoints());

        String strTime = arena.getTimer().getTime();

        graphics.setForegroundColor(TextColor.Factory.fromString("#EEEEEE"));
        graphics.putString(new TerminalPosition(50, arena.getHeight() + 3), strTime);

    }

    public void drawCar(TextGraphics graphics, Car car) {
        if ((car.getRecovering() > 20) || !car.isAlive()) return;
        graphics.setForegroundColor(TextColor.Factory.fromString(car.getColor().getStr()));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(car.getPosition().getX(), car.getPosition().getY()), String.valueOf(car.getOrientation().getRepresentation()));

        for (Wall wall : car.getWalls()) {
            graphics.putString(new TerminalPosition(wall.getPosition().getX(), wall.getPosition().getY()), String.valueOf(car.getWallChar()));
        }
    }

    public void drawWall(TextGraphics graphics, Wall wall) {
        graphics.setBackgroundColor(TextColor.Factory.fromString(wall.getColor().getStr()));
        graphics.enableModifiers(SGR.BOLD);
        graphics.fillRectangle(new TerminalPosition(wall.getPosition().getX(), wall.getPosition().getY()), new TerminalSize(1, 1), ' ');
    }
}
