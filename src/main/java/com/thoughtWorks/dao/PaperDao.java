package com.thoughtWorks.dao;

import com.thoughtWorks.entity.paper.PaperNameInfo;
import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.entity.paper.QuestionPaperItem;
import com.thoughtWorks.entity.question.*;

import java.util.List;

/**
 * 
 * 我的试卷数据访问层
 * ICTEnglish Dao about Paper
 * 试卷相关的DAO
 *
 */
public interface PaperDao {
    /**
     * 试卷列表查询
     * @return
     */
    List<QuestionPaper> paperList();

    /**
     * 删除试卷
     * @param id
     */
    void deletePaper(int id);


    Selection selectSelectionById(int id);

    TorF selectTorFById(int id);

    WordEnToCN selectWordEnToCNById(int id);

    WordCnToEN selectWordCnToENById(int id);

    Explanation selectExplanationById(int id);

    SentenceEnToCN selectSentenceEnToCNById(int id);

    SentenceCnToEN selectSentenceCnToENById(int id);

    QuestionPaper selectPaperQListById(int id);

    QuestionPaperItem selectPaperItemById(int id);

    PaperNameInfo selectPaperNameInfo(int id);

    int selectABRatePaper(String name, int type);
}
