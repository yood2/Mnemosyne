# Flash Cards Application

### Test your knowledge!

This application will provide users a method of creating and practicing flash cards!

The **Flash Cards Application** can be used by:
- Anyone who is interested in organizing their notes in flash cards
- People who are looking for a quick way to study
- People who want to keep track of the things they have answered correctly before

I personally am interested in this project as I believe it would be a useful way to study
for tests and quizzes!

## User Stories
- As a user, I would like to be able to add new cards to my deck.
- As a user, I would like to view and practice all the cards in my deck.
- As a user, I would like to be able to view all the cards in my deck.
- As a user, I would like to be able to practice only the cards I have not been able to answer.
- As a user, I want to be able to save my deck of flashcards to file (if I so choose)
- As a user, I want to be able to load my deck of flashcards from a file (if I so choose)

- As a user, I want to be able to add multiple Cards to a Deck using the GUI.
- As a user, I want to be able to load and save the state of the application using the GUI.

## Instructions for Grader
1. Start by clicking "New Deck."
2. Click "New Card" > enter a "Question" and "Answer" > Click "Ok."
   - Add as many cards as you'd like!
   - Can also delete cards by selecting the card and clicking "Delete Card"
3. Click "Practice All" to test yourself on all your cards.
   - Alternatively, use "Practice New" to test only the cards you have not correctly answered yet.
   - You should see a visual component after each attempt at answering the card!
   - Can reset all cards and make them "new" by clicking "Reset Attempts"
4. Can save the deck by clicking "Save"
   - Later on, you can load this deck by clicking "Load Deck" at the title screen.

## Phase 4: Task 2
Tue Nov 28 13:05:28 PST 2023
Added card to deck

Tue Nov 28 13:05:32 PST 2023
Added card to deck

Tue Nov 28 13:05:37 PST 2023
Added card to deck

Tue Nov 28 13:05:44 PST 2023
correctlyAnswered for this card incremented by 1

Tue Nov 28 13:05:46 PST 2023
correctlyAnswered for this card incremented by 1

Tue Nov 28 13:05:48 PST 2023
correctlyAnswered for this card incremented by 1

Tue Nov 28 13:05:50 PST 2023
correctlyAnswered for this card reset to 0.

Tue Nov 28 13:05:50 PST 2023
correctlyAnswered for this card reset to 0.

Tue Nov 28 13:05:50 PST 2023
correctlyAnswered for this card reset to 0.

Tue Nov 28 13:05:54 PST 2023
Removed card to deck

## Phase 4: Task 3
When considering options to refactor, one potential option is 
to create a common interface or abstract class for the UI components. 
This interface can be implemented by all of the UI classes, which
would allow a more consistent structure for UI components. It would also
promote code reusability for all of my UI classes.    Additionally,
I could maybe change my implementation so that the 'PracticeUI' class
is associated with a 'Deck' object instead of with a list of cards. 
In this case, I would make it so that a new 'Deck' object with the relevant 
cards would be passed along to the 'PracticeUI' class.