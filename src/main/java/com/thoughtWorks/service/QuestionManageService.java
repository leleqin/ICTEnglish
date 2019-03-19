package com.thoughtWorks.service;

import java.util.List;

import com.thoughtWorks.entity.paper.PaperNameInfo;
import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.entity.paper.QuestionPaperItem;
import com.thoughtWorks.entity.question.*;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author liu
 * smxy question system
 * service interface
 *
 */

public interface QuestionManageService {

    //选择题
    List<Selection> selectSelection();

    //判断题
    List<TorF> selectTorF();

    //常用词组英译汉
    List<WordEnToCN> selectWordEnToCN();

    //常用词组汉译英
    List<WordCnToEN> selectWordCnToEN();

    //缩略词解释
    List<Explanation> selectExplanation();

    //句子英译汉
    List<SentenceEnToCN> selectSentenceEnToCN();

    //句子汉译英
    List<SentenceCnToEN> selectSentenceCnToEN();

    //删除选择题
    void deleteSelection(int id);

    //删除判断题
    void deleteTorF(int id);

    //删除词组英译汉
    void deleteWordEnToCN(int id);

    //删除词组汉译英
    void deleteWordCnToEN(int id);

    //删除缩略词解释
    void deleteExplanation(int id);

    //删除句子英译汉
    void deleteSentenceEnToCN(int id);

    //删除句子汉译英
    void deleteSentenceCnToEN(int id);

    //把选择题加入到数据库
    void addSelectionToDB(Selection selection);

    //把判断题加入到数据库
    void addTorFToDB(TorF torF);

    //把词组英译汉加入到数据库
    void addWordEnToCNToDB(WordEnToCN wordEnToCN);

    //把词组汉译英加入到数据库
    void addWordCnToENToDB(WordCnToEN wordCnToEN);

    //把缩略词解释加入到数据库
    void addExplanationToDB(Explanation explanation);

    //把句子英译汉加入到数据库
    void addSentenceEnToCNToDB(SentenceEnToCN sentenceEnToCN);

    //把句子汉译英加入到数据库
    void addSentenceCnToENToDB(SentenceCnToEN sentenceCnToEN);
}
