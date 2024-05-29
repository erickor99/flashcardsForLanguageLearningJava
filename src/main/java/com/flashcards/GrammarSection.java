/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flashcards;

/**
 *
 * @author erickortiz
 */


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GrammarSection extends Application {
    private MongoCollection<Document> grammarCollection;
    
    
        public GrammarSection() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flashcardsDB");
        grammarCollection = database.getCollection("grammarExamples");
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grammar");

        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 400, 300);

        HBox inputBox = new HBox(10);

        Label grammarLabel = new Label("Grammar Usage:");
        TextField grammarField = new TextField();
        Label exampleLabel = new Label("Example:");
        TextField exampleField = new TextField();
        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
            String grammarUsage = grammarField.getText();
            String example = exampleField.getText();
            saveGrammarUsage(grammarUsage, example);
            grammarField.clear();
            exampleField.clear();
        });

        inputBox.getChildren().addAll(grammarLabel, grammarField, exampleLabel, exampleField, saveButton);
        layout.getChildren().add(inputBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void saveGrammarUsage(String grammarUsage, String example) {
        Document doc = new Document("grammarUsage", grammarUsage)
                .append("example", example);
        grammarCollection.insertOne(doc);
        System.out.println("Saved: Grammar Usage: " + grammarUsage + ", Example: " + example);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
