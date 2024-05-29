/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flashcards;

/**
 *
 * @author erickortiz
 */



import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    private MongoCollection<Document> flashcardCollection;
    private MongoCollection<Document> grammarCollection;

    public PDFGenerator() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flashcardsDB");
        flashcardCollection = database.getCollection("flashcards");
        grammarCollection = database.getCollection("grammarExamples");
    }

    public void generatePDF(String pdfPath) throws IOException {
        PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
        PdfDocument pdfDoc = new PdfDocument(writer);
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);

        addFlashcardsSection(document);
        addGrammarSection(document);

        document.close();
    }

    private void addFlashcardsSection(com.itextpdf.layout.Document document) {
        document.add(new Paragraph("Flashcards"));

        List<Document> flashcards = flashcardCollection.find().into(new java.util.ArrayList<>());

        int numColumns = 2; // Number of flashcards per row
        Table table = new Table(numColumns);

        for (Document flashcard : flashcards) {
            table.addCell(new Paragraph("Q: " + flashcard.getString("question")));
            table.addCell(new Paragraph("A: " + flashcard.getString("answer")));
        }

        document.add(table);
    }

    private void addGrammarSection(com.itextpdf.layout.Document document) {
        document.add(new Paragraph("Grammar Examples"));

        List<Document> grammarExamples = grammarCollection.find().into(new java.util.ArrayList<>());

        for (Document example : grammarExamples) {
            document.add(new Paragraph("Grammar Usage: " + example.getString("grammarUsage")));
            document.add(new Paragraph("Example: " + example.getString("example")));
            document.add(new Paragraph("")); // Add some spacing
        }
    }

public static void main(String[] args) {
    PDFGenerator generator = new PDFGenerator();
    try {
        generator.generatePDF("/Users/erickortiz/Downloads/flashcards_and_grammar.pdf"); // Change this to your desired path
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
