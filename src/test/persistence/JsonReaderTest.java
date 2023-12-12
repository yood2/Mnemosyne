package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Deck deck = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDeck() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDeck.json");
        try {
            Deck deck = reader.read();
            assertEquals("Empty Deck", deck.getName());
            assertEquals(0, deck.getAllCards().size());
        } catch (IOException e) {
            fail("Couldn't read from file.");
        }
    }

    @Test
    void testReaderGeneralDeck() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDeck.json");
        try {
            Deck deck = reader.read();
            assertEquals("General Deck", deck.getName());
            List<Card> cards = deck.getAllCards();
            assertEquals(2, cards.size());
            checkCard("q1", "a1", 2, cards.get(0));
            checkCard("q2", "a2", 0, cards.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file.");
        }
    }
}
