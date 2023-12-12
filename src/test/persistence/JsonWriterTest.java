package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Deck deck = new Deck("New Deck");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDeck() {
        try {
            Deck deck = new Deck ("Empty Deck");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDeck.json");
            writer.open();
            writer.write(deck);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDeck.json");
            deck = reader.read();
            assertEquals("Empty Deck", deck.getName());
            assertEquals(0, deck.getAllCards().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDeck() {
        try {
            Deck deck = new Deck ("General Deck");
            Card card1 = new Card("q1","a1");
            Card card2 = new Card("q2","a2");
            card1.answeredCorrectly();
            card1.answeredCorrectly();
            deck.addCardToDeck(card1);
            deck.addCardToDeck(card2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDeck.json");
            writer.open();
            writer.write(deck);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDeck.json");
            deck = reader.read();
            assertEquals("General Deck", deck.getName());
            List<Card> cards = deck.getAllCards();
            System.out.println(cards.size());
            checkCard("q1", "a1", 2, cards.get(0));
            checkCard("q2", "a2", 0, cards.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
