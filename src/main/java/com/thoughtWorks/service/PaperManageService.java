package com.thoughtWorks.service;

import com.thoughtWorks.entity.paper.PaperNameInfo;
import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.entity.paper.QuestionPaperItem;
import com.thoughtWorks.entity.question.*;

import java.util.List;

/**
 * 我的试卷数据业务逻辑接口层
 * @author ChenJiale
 */
public interface PaperManageService {


    /**
     * 删除试卷
     * @author ChenJiale
     * @param id
     */
    void deleteQuestions(int id);

    void deletePaper(int id);

    /**
     * 试卷查询
     * @return
     */
    List<QuestionPaper> selectPaperList();

    Selection selectSelectionById(int id);

    TorF selectTorFById(int id);

    WordEnToCN selectWordEnToCNById(int id);

    WordCnToEN selectWordCnToENById(int id);

    Explanation selectExplanationById(int id);

    SentenceEnToCN selectSentenceEnToCNById(int id);

    SentenceCnToEN selectSentenceCnToENById(int id);

    QuestionPaper selectPaperQListById(int id);

    QuestionPaperItem selectPaperItemById(int id);

    int selectABRatePaper(String name,int type);

    PaperNameInfo selectPaperNameInfo(int id);
}
