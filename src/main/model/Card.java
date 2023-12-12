package model;

import org.json.JSONObject;

// Code influenced by the JsonSerizalizationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

/* Card class represents a flashcard with a question and answer.
 * Also contains number of times the card has been answered correctly.
 */
public class Card {
    private String question;
    private String answer;
    private int correctlyAnswered;

    /*
     * REQUIRES: question has a non-zero length, answer has a non-zero length
     * EFFECTS: this.question is set to question; this.answer is set to answer; this.correctlyAnswered
     *          is set to 0
     * */
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.correctlyAnswered = 0;
    }

    /*
     * REQUIRES: newQuestion != ""
     * MODIFIES: this
     * EFFECTS: changes this.question to the newQuestion
     *  */
    public void changeQuestion(String newQuestion) {
        String oldQuestion = this.question;
        this.question = newQuestion;
        EventLog.getInstance().logEvent(new Event("Question Changed from " + oldQuestion + " to " + this.question));
    }

    /*
     * REQUIRES: newAnswer != ""
     * MODIFIES: this
     * EFFECTS: changes this.answer to the newAnswer
     *  */
    public void changeAnswer(String newAnswer) {
        String oldAnswer = this.answer;
        this.answer = newAnswer;
        EventLog.getInstance().logEvent(new Event("Answer Changed from " + oldAnswer + " to " + this.answer));
    }

    /*
     * MODIFIES: this
     * EFFECTS: increments this.correctlyAnswered by 1
     *  */
    public void answeredCorrectly() {
        this.correctlyAnswered += 1;
        EventLog.getInstance().logEvent(new Event("correctlyAnswered for this card incremented by 1"));
    }

    /*
     * MODIFIES: this
     * EFFECTS: resets this.correctlyAnswered to 0
     *  */
    public void resetCorrectlyAnswered() {
        this.correctlyAnswered = 0;
        EventLog.getInstance().logEvent(new Event("correctlyAnswered for this card reset to 0."));
    }

    /* EFFECTS: Converts fields in this object into JSON format and returns as a JSON object
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", this.question);
        json.put("answer", this.answer);
        json.put("correctlyAnswered", this.correctlyAnswered);
        return json;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public int getCorrectlyAnswered() {
        return this.correctlyAnswered;
    }
}
