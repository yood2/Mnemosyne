package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    private Deck testDeck;
    private Card card1;
    private Card card2;
    private Card card3;

    @BeforeEach
    void runBefore() {
        testDeck = new Deck("Test Deck");
        card1 = new Card("Question1", "Answer1");
        card2 = new Card("Question2", "Answer2");
        card3 = new Card("Question3", "Answer3");
    }

    @Test
    void testConstructor() {
        String name = testDeck.getName();
        int listLength = testDeck.getAllCards().size();
        assertEquals("Test Deck", name);
        assertEquals(0, listLength);
    }

    @Test
    void testAddCardToDeck() {
        testDeck.addCardToDeck(card1);
        int listLength = testDeck.getAllCards().size();
        assertEquals(1, listLength);
        String res = testDeck.getCardAtIndex(0).getQuestion();
        assertEquals("Question1", res);
    }

    @Test
    void testAddCardToDeckMultiple() {
        testDeck.addCardToDeck(card1);
        testDeck.addCardToDeck(card2);
        testDeck.addCardToDeck(card3);
        int listLength = testDeck.getAllCards().size();
        assertEquals(3, listLength);
        String res = testDeck.getCardAtIndex(1).getQuestion();
        assertEquals("Question2", res);
    }

    @Test
    void testRemoveCardFromDeck() {
        testDeck.addCardToDeck(card1);
        testDeck.addCardToDeck(card2);
        testDeck.removeCardFromDeck(0);
        int listLength = testDeck.getAllCards().size();
        assertEquals(1, listLength);
        String res = testDeck.getCardAtIndex(0).getQuestion();
        assertEquals("Question2", res);
    }

    @Test
    void testRemoveCardFromDeckMultiple() {
        testDeck.addCardToDeck(card1);
        testDeck.addCardToDeck(card2);
        testDeck.addCardToDeck(card3);
        testDeck.removeCardFromDeck(2);
        testDeck.removeCardFromDeck(0);
        int listLength = testDeck.getAllCards().size();
        assertEquals(1, listLength);
        String res = testDeck.getCardAtIndex(0).getQuestion();
        assertEquals("Question2", res);
    }

    @Test
    void testResetCards() {
        testDeck.addCardToDeck(card1);
        card1.answeredCorrectly();
        int card1 = testDeck.getCardAtIndex(0).getCorrectlyAnswered();
        assertEquals(1, card1);
        testDeck.resetCards();
        card1 = testDeck.getCardAtIndex(0).getCorrectlyAnswered();
        assertEquals(0, card1);
    }

    @Test
    void testResetCardsMultiple() {
        testDeck.addCardToDeck(card1);
        testDeck.addCardToDeck(card2);
        testDeck.addCardToDeck(card3);
        card1.answeredCorrectly();
        card2.answeredCorrectly();
        card3.answeredCorrectly();
        int card1 = testDeck.getCardAtIndex(0).getCorrectlyAnswered();
        int card2 = testDeck.getCardAtIndex(1).getCorrectlyAnswered();
        int card3 = testDeck.getCardAtIndex(2).getCorrectlyAnswered();
        assertEquals(1, card1);
        assertEquals(1, card2);
        assertEquals(1, card3);
        testDeck.resetCards();
        card1 = testDeck.getCardAtIndex(0).getCorrectlyAnswered();
        card2 = testDeck.getCardAtIndex(1).getCorrectlyAnswered();
        card3 = testDeck.getCardAtIndex(2).getCorrectlyAnswered();
        assertEquals(0, card1);
        assertEquals(0, card2);
        assertEquals(0, card3);
    }

    @Test
    void testGetUnansweredCards() {
        card1.answeredCorrectly();
        card3.answeredCorrectly();
        testDeck.addCardToDeck(card1);
        testDeck.addCardToDeck(card2);
        testDeck.addCardToDeck(card3);
        String card = testDeck.getUnansweredCards().get(0).getQuestion();
        assertEquals("Question2", card);
    }

}
