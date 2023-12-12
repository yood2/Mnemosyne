package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCard(String question, String answer, int numAnswered, Card card) {
        assertEquals(question, card.getQuestion());
        assertEquals(answer, card.getAnswer());
        assertEquals(numAnswered, card.getCorrectlyAnswered());
    }
}
