/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flashcards;

/**
 *
 * @author erickortiz
 */

import java.time.LocalDate;

public class Flashcard {
    private String question;
    private String answer;
    public int level;
    public LocalDate nextReviewDate;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.level = 1;  // Start with level 1
        this.nextReviewDate = LocalDate.now();  // Review immediately
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getLevel() {
        return level;
    }

    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }

    public void increaseLevel() {
        this.level++;
        this.nextReviewDate = LocalDate.now().plusDays(level);  // Simple SRS logic
    }

    public void resetLevel() {
        this.level = 1;
        this.nextReviewDate = LocalDate.now();
    }
}
