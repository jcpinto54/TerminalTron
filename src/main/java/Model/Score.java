package Model;

public class Score implements Comparable {
    private int winner;
    private int score;

    public Score(String str) {
        String[] splitedStr = str.split(" ");
        this.winner = Integer.parseInt(splitedStr[0]);
        this.score = Integer.parseInt(splitedStr[1]);
    }

    public Score(int playerNr, int score) {
        this.winner = playerNr;
        this.score = score;
    }

    public String str() {
        return winner + " " + score + "\n";
    }

    public int getWinner() {
        return winner;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Object o) {
        Score s = (Score) o;
        return Integer.compare(this.score, s.score);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Score)) {
            return false;
        }

        Score score = (Score) o;

        return this.score == score.score && this.winner == score.winner;
    }
}
