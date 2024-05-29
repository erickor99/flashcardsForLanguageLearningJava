/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flashcards;

/**
 *
 * @author erickortiz
 */
import javafx.application.Application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class FlashcardsSection extends Application {

    private FlashcardDeck deck = new FlashcardDeck();
    private List<Flashcard> reviewFlashcards;
    private int currentIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Flashcards");
              
        VBox layout = new VBox(10); // Create the layout VBox
        Scene scene = new Scene(layout, 400, 300);

        
        

        Label questionLabel = new Label("Question:");
        TextField questionField = new TextField();
        Label answerLabel = new Label("Answer:");
        TextField answerField = new TextField();
        Button addButton = new Button("Add Flashcard");
        Button showNextButton = new Button("Show Next Flashcard");
        Label displayQuestion = new Label();
        Label displayAnswer = new Label();
        Button showAnswerButton = new Button("Show Answer");
        Button correctButton = new Button("Correct");
        Button incorrectButton = new Button("Incorrect");
        Button studyMoreFlashcards = new Button("studyMoreFlashcards");

        addButton.setOnAction(e -> {
            String question = questionField.getText();
            String answer = answerField.getText();
            deck.addFlashcard(question, answer);
            questionField.clear();
            answerField.clear();
        });


        
        showNextButton.setOnAction(e -> {
            reviewFlashcards = deck.getFlashcardsForReview();
            if (!reviewFlashcards.isEmpty()) {
                Flashcard flashcard = reviewFlashcards.get(currentIndex);
                displayQuestion.setText("Question: " + flashcard.getQuestion());
                displayAnswer.setText("");
            } else {
                displayQuestion.setText("No flashcards to review.");
                displayAnswer.setText("");
            }
        });

        showAnswerButton.setOnAction(e -> {
            if (reviewFlashcards != null && !reviewFlashcards.isEmpty()) {
                Flashcard flashcard = reviewFlashcards.get(currentIndex);
                displayAnswer.setText("Answer: " + flashcard.getAnswer());
            }
        });

        correctButton.setOnAction(e -> {
            if (reviewFlashcards != null && !reviewFlashcards.isEmpty()) {
                Flashcard flashcard = reviewFlashcards.get(currentIndex);
                flashcard.increaseLevel();
                deck.updateFlashcard(flashcard);
                currentIndex = (currentIndex + 1) % reviewFlashcards.size();
                showNextButton.fire();
            }
        });

        incorrectButton.setOnAction(e -> {
            if (reviewFlashcards != null && !reviewFlashcards.isEmpty()) {
                Flashcard flashcard = reviewFlashcards.get(currentIndex);
                flashcard.resetLevel();
                deck.updateFlashcard(flashcard);
                currentIndex = (currentIndex + 1) % reviewFlashcards.size();
                showNextButton.fire();
            }
        });

        layout.getChildren().addAll(
                questionLabel, questionField,
                answerLabel, answerField, addButton,
                showNextButton, displayQuestion,
                showAnswerButton, displayAnswer,
                correctButton, incorrectButton
        );

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
}
