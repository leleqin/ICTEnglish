package com.thoughtWorks.entity.question;


/**
 * 
 * @author Liwenlu
 * ictEnglish system
 *缩略词解释 Abbreviated explanation
 *
 */


public class Selection extends Question {


    String answerA;
    String answerB;
    String answerC;
    String answerD; 

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    @Override
    public String toString() {
        return "Selection id = "+ id + "question = "+question+"[answerA=" + answerA + ", answerB=" + answerB + ", answerC=" + answerC + ", answerD="
                + answerD + "]"+"anwser = "+answer;
    }




}
