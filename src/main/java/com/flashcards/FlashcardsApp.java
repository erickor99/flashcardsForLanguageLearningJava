/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.flashcards;

/**
 *
 * @author erickortiz
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class FlashcardsApp extends Application {

    private FlashcardDeck deck = new FlashcardDeck();
    private List<Flashcard> reviewFlashcards;
    private int currentIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Flashcards");

        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 400, 300);
        
        Button flashcardsButton = new Button("Flashcards");
        Button grammarButton = new Button("Grammar");
        
        flashcardsButton.setOnAction(e -> {
            // Open the flashcards section
            openFlashcardsSection(primaryStage);
        });

        grammarButton.setOnAction(e -> {
            // Open the grammar section
            openGrammarSection(primaryStage);
        });
        
       layout.getChildren().addAll(flashcardsButton, grammarButton);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

     private void openFlashcardsSection(Stage primaryStage) {
        // Instantiate and launch the Flashcards section
        FlashcardsSection flashcardsSection = new FlashcardsSection();
        flashcardsSection.start(primaryStage);
  
    }
    private void openGrammarSection(Stage primaryStage) {
        // Instantiate and launch the Grammar section
        GrammarSection grammarSection = new GrammarSection();
        grammarSection.start(primaryStage);
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
