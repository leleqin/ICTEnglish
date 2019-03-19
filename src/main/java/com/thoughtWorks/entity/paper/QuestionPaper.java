package com.thoughtWorks.entity.paper;

/**
 * 
 * @author Liwenlu
 * ictEnglish  system
 * paper content
 * the parameter is related with db
 *
 */

public class QuestionPaper {

    int id;
    String generateTime;
    String name;
    int type=0;
    String abRate = "0";
    String lastYearRate = "0";
    int abCounterpartId = 0;
    int lastYearCounterpartId = 0;
    String questionList="";
    int authorId=0;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(String generateTime) {
        this.generateTime = generateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAbRate() {
        return abRate;
    }

    public void setAbRate(String abRate) {
        this.abRate = abRate;
    }

    public String getLastYearRate() {
        return lastYearRate;
    }

    public void setLastYearRate(String lastYearRate) {
        this.lastYearRate = lastYearRate;
    }

    public int getAbCounterpartId() {
        return abCounterpartId;
    }

    public void setAbCounterpartId(int abCounterpartId) {
        this.abCounterpartId = abCounterpartId;
    }

    public int getLastYearCounterpartId() {
        return lastYearCounterpartId;
    }

    public void setLastYearCounterpartId(int lastYearCounterpartId) {
        this.lastYearCounterpartId = lastYearCounterpartId;
    }

    public String getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String questionList) {
        this.questionList = questionList;
    }

    @Override
    public String toString() {
        return "QuestionPaper [id=" + id + ", generateTime=" + generateTime + ", name=" + name + ", type=" + type
                + ", abRate=" + abRate + ", lastYearRate=" + lastYearRate + ", abCountpartId=" + abCounterpartId
                + ", lastYearpartId=" + lastYearCounterpartId + ", questionList=" + questionList + "]";
    }

}
