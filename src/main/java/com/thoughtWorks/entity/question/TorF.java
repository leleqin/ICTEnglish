package com.thoughtWorks.entity.question;


/**
 * 
 * @author Liwenlu
 * ictEnglish system
 * 判断题
 *
 */


public class TorF extends Question{
    String answerA;
    String answerB;
    
    
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


    @Override
    public String toString() {
        return "TorF id = "+ id + "question = "+question+"[answerA=" + answerA + ", answerB=" + answerB + "]"+"anwser = "+answer;
    }
    
}
