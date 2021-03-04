package Model;

public class Wall {
    private final Position position;
    private final ArenaModel.COLOR color;

    public Wall(Position p, ArenaModel.COLOR color) {
        this.position = p;
        this.color = color;
    }

    public ArenaModel.COLOR getColor() {
        return color;
    }

    public Position getPosition() { return position; }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }


        if (!(o instanceof Wall)) {
            return false;
        }

        Wall p = (Wall) o;
        return this.position.equals(p.position) && this.color.equals(p.color);
    }

    @Override
    public String toString() {
        String posStr = position.toString();
        return posStr + " - " + color.getStr();
    }
}
