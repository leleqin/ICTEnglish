package com.thoughtWorks.entity.paper;


/**
 * 
 * @author Liwenlu
 * ictEnglish  system
 * paper item
 * the parameter is related with db
 *
 */



public class QuestionPaperItem {
    int id;
    String qType;
    String score;	
    String qIdList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getqType() {
        return qType;
    }

    public void setqType(String qType) {
        this.qType = qType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getqIdList() {
        return qIdList;
    }

    public void setqIdList(String qIdList) {
        this.qIdList = qIdList;
    }

    @Override
    public String toString() {
        return "QuestionPaperItem [id=" + id + ", qType=" + qType + ", score=" + score + ", qIdList=" + qIdList + "]";
    }

}
