package ui.deprecated;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Flash Cards Application
public class MnemosyneCLI {

    private String filePath = "./data/deckSave.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private Deck deck;

    // EFFECTS: runs the flash card application
    public MnemosyneCLI() {
        runFlashCards();
    }

    // MODIFIES: this
    // EFFECTS: Creates a new deck with name input from user. Outputs options and processes user input.
    private void runFlashCards() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        deck = new Deck("deck");

        jsonWriter = new JsonWriter(filePath);
        jsonReader = new JsonReader(filePath);

        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("9")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("Closing application...");
    }

    // MODIFIES: this
    // EFFECTS: takes user input and starts appropriate method
    private void processCommand(String command) {
        if (command.equals("1")) {
            practiceAllCards();
        } else if (command.equals("2")) {
            practiceUnansweredCards();
        } else if (command.equals("3")) {
            addCardToDeck();
        } else if (command.equals("4")) {
            removeCardFromDeck();
        } else if (command.equals("5")) {
            resetAllCards();
        } else if (command.equals("6")) {
            viewAllCards();
        } else if (command.equals("7")) {
            save();
        } else if (command.equals("8")) {
            load();
        }
    }

    // EFFECTS: displays menu of options to the user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> practice all cards");
        System.out.println("\t2 -> practice all unanswered cards");
        System.out.println("\t3 -> add card");
        System.out.println("\t4 -> remove card");
        System.out.println("\t5 -> reset all cards");
        System.out.println("\t6 -> view all cards");
        System.out.println("\t7 -> save");
        System.out.println("\t8 -> load");
        System.out.println("\t9 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: goes through all flash cards
    private void practiceAllCards() {
        List<Card> allCards = deck.getAllCards();
        testingCards(allCards);
    }

    // MODIFIES: this
    // EFFECTS: goes through all unanswered flash cards
    private void practiceUnansweredCards() {
        List<Card> unansweredCards = deck.getUnansweredCards();
        testingCards(unansweredCards);
    }

    // MODIFIES: this
    // EFFECTS: goes through each card in cards; shows question and asks for answer; if answer correct, increment
    //          correctly answered. Else, show correct answer.
    private void testingCards(List<Card> cards) {
        for (Card card : cards) {
            String question = card.getQuestion();
            String answer = card.getAnswer();
            System.out.println("\nQ: " + question);
            System.out.println("\nEnter your answer:");
            String userAnswer = input.next();
            if (userAnswer.equals(answer)) {
                card.answeredCorrectly();
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect. Answer was: " + answer);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: makes a new card and adds to deck
    private void addCardToDeck() {
        System.out.println("Enter a question:");
        String question = input.next();
        System.out.println("Enter the answer:");
        String answer = input.next();
        Card newCard = new Card(question, answer);
        deck.addCardToDeck(newCard);
        System.out.println("Card added!");
    }

    // MODIFIES: this
    // EFFECTS: displays all cards and removes card at user inputted index
    private void removeCardFromDeck() {
        int index = 0;
        List<Card> allCards = deck.getAllCards();
        System.out.println("Index\tQ.\tA.");
        for (Card card : allCards) {
            System.out.println(index + "\t" + card.getQuestion() + "\t" + card.getAnswer());
            index++;
        }

        System.out.println("Index of card to remove?");
        int indexToBeRemoved = Integer.valueOf(input.next());
        deck.removeCardFromDeck(indexToBeRemoved);
        System.out.println("Card removed!");
    }

    // MODIFIES: this
    // EFFECTS: resets all cards
    private void resetAllCards() {
        deck.resetCards();
        System.out.println("Cards reset!");
    }

    // EFFECTS: displays all cards in the deck
    private void viewAllCards() {
        int index = 0;
        List<Card> allCards = deck.getAllCards();
        System.out.println("Index\tQuestion\tAnswer\t# Of Times Answered");
        for (Card card : allCards) {
            System.out.println(index + "\t" + card.getQuestion() + "\t" + card.getAnswer() + "\t"
                                + card.getCorrectlyAnswered());
            index++;
        }
    }

    // EFFECTS: saves Card and Deck objects into a JSON file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.deck);
            jsonWriter.close();
            System.out.println("Saved " + deck.getName() + " to " + filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + filePath);
        }

    }

    // MODIFIES: this
    // EFFECTS: load Card and Deck objects from JSON file
    private void load() {
        try {
            deck = jsonReader.read();
            System.out.println("Loaded " + deck.getName() + " from " + filePath);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + filePath);
        }
    }

    public static void main(String[] args) {
        new MnemosyneCLI();
    }

}
