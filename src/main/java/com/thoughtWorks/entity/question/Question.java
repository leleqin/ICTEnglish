package com.thoughtWorks.entity.question;

/**
 * 
 * @author Liwenlu
 * ictEnglish system
 * base question type
 *
 */
public class Question {

    int id;
    String question;
    String answer;
    String difficulty = "";
    String knowledgePoint = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    
    
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getKnowledgePoint() {
        return knowledgePoint;
    }

    public void setKnowledgePoint(String knowledgePoint) {
        this.knowledgePoint = knowledgePoint;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(this == obj) return true;
        if(obj == null) return false;
        if(this.getClass() != obj.getClass()) return false; //如果不死同一个类，则必然false  
        Question that = (Question)obj; 
        
        if(that.id == this.id) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        return "id=" +id + "question=" + question + ", answer=" + answer + "";
    }
}
