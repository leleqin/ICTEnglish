package com.thoughtWorks.entity.question;


/**
 * 
 * @author Liwenlu
 * ictEnglish system
 *缩略词解释 Abbreviated explanation
 *
 */


public class Explanation extends Question {

    @Override
    public String toString() {
        return "Explanation [id=" + id + ", question=" + question + ", answer=" + answer + ", difficulty=" + difficulty
                + ", knowledgePoint=" + knowledgePoint + "]";
    }



}
