package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

/* Deck class represents a deck (list) of flashcards (Card objects).
 * Contains methods that allow you to add, remove, reset, and index card objects.
 */

public class Deck {
    private String name;
    private List<Card> allCards;

    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: this.name is set to name; allCards is instantiated as a empty ArrayList of Card objects
     * */
    public Deck(String name) {
        this.name = name;
        this.allCards = new ArrayList<Card>();
    }

    /*
     * REQUIRES: newCard
     * MODIFIES: allCards
     * EFFECTS: adds newCard to this.allCards
     *  */
    public void addCardToDeck(Card newCard) {
        this.allCards.add(newCard);
        EventLog.getInstance().logEvent(new Event("Added card to deck"));
    }

    /*
     * REQUIRES: index
     * MODIFIES: allCards
     * EFFECTS: removes card at index from this.allCards
     *  */
    public void removeCardFromDeck(int index) {
        this.allCards.remove(index);
        EventLog.getInstance().logEvent(new Event("Removed card to deck"));
    }

    /*
     * MODIFIES: Card.correctlyAnswered
     * EFFECTS: resets correctlyAnswered field in Card object to 0
     *  */
    public void resetCards() {
        for (Card card : this.allCards) {
            card.resetCorrectlyAnswered();
        }
        EventLog.getInstance().logEvent(new Event("All cards in deck reset"));
    }

    /*
     * EFFECTS: Goes through allCards and puts all cards that have been answered into a new list. Returns that list.
     *  */
    public List<Card> getUnansweredCards() {
        List<Card> unansweredCards = new ArrayList<Card>();
        for (Card card : this.allCards) {
            if (card.getCorrectlyAnswered() == 0) {
                unansweredCards.add(card);
            }
        }
        EventLog.getInstance().logEvent(new Event("Getting all unanswered cards"));
        return unansweredCards;
    }

    /* EFFECTS: Creates and returns a new JSONObject with the name and cards fields.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("cards", cardsToJson());
        return json;
    }

    /* EFFECTS: Iterates through list of allCards and converts each card into JSON format in a JSONArray object.
                Returns array of JSON objects.
     */
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card card : this.allCards) {
            jsonArray.put(card.toJson());
        }
        return jsonArray;
    }

    public List<Card> getAllCards() {
        return this.allCards;
    }

    public Card getCardAtIndex(int index) {
        return this.allCards.get(index);
    }

    public String getName() {
        return this.name;
    }
}
