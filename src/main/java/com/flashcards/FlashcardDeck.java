/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flashcards;

/**
 *
 * @author erickortiz
 */
import com.mongodb.client.*;
import org.bson.Document;
import com.mongodb.client.model.Filters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class FlashcardDeck  
{
    private MongoCollection<Document> collection;

    public FlashcardDeck() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flashcardsDB");
        collection = database.getCollection("flashcards");
    }
    
    


    public void addFlashcard(String question, String answer) {
        Flashcard flashcard = new Flashcard(question, answer);
        Document doc = new Document("question", flashcard.getQuestion())
                        .append("answer", flashcard.getAnswer())
                        .append("level", flashcard.getLevel())
                        .append("nextReviewDate", flashcard.getNextReviewDate().toString());
        collection.insertOne(doc);
    }

    public List<Flashcard> getFlashcardsForReview() {
        List<Flashcard> flashcards = new ArrayList<>();
        LocalDate today = LocalDate.now();
        try (MongoCursor<Document> cursor = collection.find(Filters.lte("nextReviewDate", today.toString())).iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Flashcard flashcard = new Flashcard(doc.getString("question"), doc.getString("answer"));
                flashcard.level = doc.getInteger("level");
                flashcard.nextReviewDate = LocalDate.parse(doc.getString("nextReviewDate"));
                flashcards.add(flashcard);
            }
        }
        return flashcards;
    }


    public void updateFlashcard(Flashcard flashcard) {
        Document doc = new Document("question", flashcard.getQuestion())
                        .append("answer", flashcard.getAnswer())
                        .append("level", flashcard.getLevel())
                        .append("nextReviewDate", flashcard.getNextReviewDate().toString());
        collection.replaceOne(Filters.eq("question", flashcard.getQuestion()), doc);
    }
}

