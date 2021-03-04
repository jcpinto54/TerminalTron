package Controller;

import Model.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoresController {
    List<Score> scores;
    boolean read = false;

    public List<Score> readScores() throws IOException {
        if (read) return this.scores;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/main/resources/highscores.txt")));
        List<Score> scores = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            scores.add(new Score(line));
        }
        bufferedReader.close();
        this.read = true;
        this.scores = scores;
        return scores;
    }

    public void addScore(Score score) {
        this.scores.add(score);
    }

    public void sortScores() {
        this.scores.sort(Collections.reverseOrder());
    }

    public void writeHighscores() throws IOException {
        this.sortScores();
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/highscores.txt"));
        for (Score score : this.scores) {
            writer.write(score.str());
        }
        this.read = false;
        writer.close();
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
