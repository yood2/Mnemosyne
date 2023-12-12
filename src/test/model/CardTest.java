package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    private Card testCard;

    @BeforeEach
    void runBefore() {
        String question = "Test Question";
        String answer = "Test Answer";
        testCard = new Card(question, answer);
    }

    @Test
    void testConstructor() {
        boolean questionCheck = testCard.getQuestion().equals("Test Question");
        boolean answerCheck = testCard.getAnswer().equals("Test Answer");
        int correctlyAnswered = testCard.getCorrectlyAnswered();
        assertTrue(questionCheck);
        assertTrue(answerCheck);
        assertEquals(0, correctlyAnswered);
    }

    @Test
    void testChangeQuestion() {
        String newQuestion = "New Question";
        testCard.changeQuestion(newQuestion);
        boolean questionCheck = testCard.getQuestion().equals(newQuestion);
        assertTrue(questionCheck);
    }

    @Test
    void testChangeQuestionMultiple() {
        String newQuestion = "New Question";
        String newQuestion2 = "New Question 2";
        testCard.changeQuestion(newQuestion);
        testCard.changeQuestion(newQuestion2);
        boolean questionCheck = testCard.getQuestion().equals(newQuestion2);
        assertTrue(questionCheck);
    }

    @Test
    void testChangeAnswer() {
        String newAnswer = "New Answer";
        testCard.changeAnswer(newAnswer);
        boolean answerCheck = testCard.getAnswer().equals(newAnswer);
        assertTrue(answerCheck);
    }

    @Test
    void testChangeAnswerMultiple() {
        String newAnswer = "New Answer";
        String newAnswer2 = "New Answer 2";
        testCard.changeAnswer(newAnswer);
        testCard.changeAnswer(newAnswer2);
        boolean answerCheck = testCard.getAnswer().equals(newAnswer2);
        assertTrue(answerCheck);
    }

    @Test
    void testAnsweredCorrectly() {
        int res = testCard.getCorrectlyAnswered();
        assertEquals(0, res);
        testCard.answeredCorrectly();
        res = testCard.getCorrectlyAnswered();
        assertEquals(1, res);
    }

    @Test
    void testAnsweredCorrectlyMultiple() {
        int res = testCard.getCorrectlyAnswered();
        assertEquals(0, res);
        testCard.answeredCorrectly();
        testCard.answeredCorrectly();
        testCard.answeredCorrectly();
        res = testCard.getCorrectlyAnswered();
        assertEquals(3, res);
    }

    @Test
    void testResetCorrectlyAnswered() {
        testCard.answeredCorrectly();
        int res = testCard.getCorrectlyAnswered();
        assertEquals(1, res);
        testCard.resetCorrectlyAnswered();
        res = testCard.getCorrectlyAnswered();
        assertEquals(0, res);
    }

}
