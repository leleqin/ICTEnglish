package com.thoughtWorks.dao;

import java.util.List;

import com.thoughtWorks.entity.paper.PaperNameInfo;
import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.entity.paper.QuestionPaperItem;
import com.thoughtWorks.entity.question.*;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author liu
 * ICTEnglish Dao about Question
 * 试题相关的DAO
 */
public interface QuestionDao {

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

    //用id查找选择题
    Selection selectSelectionById(int id);

    //用id查找判断题
    TorF selectTorFById(int id);

    //用id查找英译汉
    WordEnToCN selectWordEnToCNById(int id);

    //用id查找汉译英
    WordCnToEN selectWordCnToENById(int id);

    //用id查找缩略词解释
    Explanation selectExplanationById(int id);

    //用id查找句子英译汉
    SentenceEnToCN selectSentenceEnToCNById(int id);

    //用id查找句子汉译英
    SentenceCnToEN selectSentenceCnToENById(int id);

    //删除试题
    void deleteQuestions(int id);

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
    void addSelectionToDB(@Param("selection") Selection selection);

    //把判断题加入到数据库
    void addTorFToDB(@Param("torF") TorF torF);

    //把词组英译汉加入到数据库
    void addWordEnToCNToDB(@Param("wordEnToCN") WordEnToCN wordEnToCN);

    //把词组汉译英加入到数据库
    void addWordCnToENToDB(@Param("wordCnToEN") WordCnToEN wordCnToEN);

    //把缩略词解释加入到数据库
    void addExplanationToDB(@Param("explanation") Explanation explanation);

    //把句子英译汉加入到数据库
    void addSentenceEnToCNToDB(@Param("sentenceEnToCN") SentenceEnToCN sentenceEnToCN);

    //把句子汉译英加入到数据库
    void addSentenceCnToENToDB(@Param("sentenceCnToEN") SentenceCnToEN sentenceCnToEN);

    void addQuestionToPaper(@Param("questionpaperitem") QuestionPaperItem questionpaperitem );

    void addPaper(@Param("questionpaper") QuestionPaper questionpaper);

    List<QuestionPaper> selectPaperList();

    QuestionPaper selectPaperQListById(int id);

    QuestionPaperItem selectPaperItemById(int id);

    List<Selection> selectSelectionByChapter(String condition);

    List<TorF> selectTorFByChapter(String condition);

    List<WordEnToCN> selectWordEnToCNByChapter(String condition);

    List<WordCnToEN> selectWordCnToENByChapter(String condition);

    List<Explanation> selectExplanationByChapter(String condition);

    List<SentenceEnToCN> selectSentenceEnToCNByChapter(String condition);

    List<SentenceCnToEN> selectSentenceCnToENByChapter(String condition);

    int selectABRatePaper(String name, int type);
}