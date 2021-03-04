package Controller;

import Model.Score;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScoresControllerTest {
    private ScoresController controller;

    @Before
    public void setUp() {
        controller = new ScoresController();
    }

    @Test
    public void sortScores() throws IOException {
        controller.readScores();
        controller.addScore(new Score(1, 4));
        controller.addScore(new Score(1, 50));
        controller.addScore(new Score(2, 23));
        controller.addScore(new Score(1, 32));
        controller.sortScores();
        List<Score> sortedScores = controller.readScores();
        int lastScore = sortedScores.get(0).getScore();

        for (Score score : sortedScores) {
            if (score == sortedScores.get(0)) continue;
            if (score.getScore() > lastScore) fail();
            lastScore = score.getScore();
        }
    }

    @Test
    public void readWriteScores() throws IOException {
        List<Score> scores = new ArrayList<>();
        scores.add(new Score(1, 4));
        scores.add(new Score(2, 13));
        scores.add(new Score(1, 10));
        scores.add(new Score(-1, 1));
        scores.add(new Score(2, 3));
        scores.add(new Score(1, 20));

        List<Score> oldScores = controller.readScores();

        controller.setScores(scores);
        controller.writeHighscores();
        controller.setRead(false);
        List<Score> toTestScores = controller.readScores();

        controller.setScores(oldScores);
        controller.writeHighscores();

        assertEquals(scores, toTestScores);
    }
}
