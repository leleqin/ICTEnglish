package com.thoughtWorks.service.impl;

import com.thoughtWorks.dao.PaperDao;
import com.thoughtWorks.entity.paper.PaperNameInfo;
import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.entity.paper.QuestionPaperItem;
import com.thoughtWorks.entity.question.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtWorks.service.PaperManageService;

import java.util.List;

/**
 * 我的试卷业务逻辑实现层
 * @author ChenJiale
 */
@Service
public class PaperManageServiceImpl implements PaperManageService {
    @Autowired
    PaperDao paperDao;


    /**
     * 删除试卷
     * @author ChenJiale
     * @param id
     */
    @Override
    public void deletePaper(int id) {
        paperDao.deletePaper(id);
    }
    @Override
    public void deleteQuestions(int id) {
        paperDao.deleteQuestions(id);
    }
    /**
     * 试卷列表查询
     * @author ChenJiale
     * @return
     */
    @Override
    public List<QuestionPaper> selectPaperList() {
        return paperDao.selectPaperList();
    }

    @Override
    public Selection selectSelectionById(int id) {
        return paperDao.selectSelectionById(id);
    }

    @Override
    public TorF selectTorFById(int id) {
        return paperDao.selectTorFById(id);
    }

    @Override
    public WordEnToCN selectWordEnToCNById(int id) {
        return paperDao.selectWordEnToCNById(id);
    }

    @Override
    public WordCnToEN selectWordCnToENById(int id) {
        return paperDao.selectWordCnToENById(id);
    }

    @Override
    public Explanation selectExplanationById(int id) {
        return paperDao.selectExplanationById(id);
    }

    @Override
    public SentenceEnToCN selectSentenceEnToCNById(int id) {
        return paperDao.selectSentenceEnToCNById(id);
    }

    @Override
    public SentenceCnToEN selectSentenceCnToENById(int id) {
        return paperDao.selectSentenceCnToENById(id);
    }


    @Override
    public QuestionPaper selectPaperQListById(int id) {
        return paperDao.selectPaperQListById(id);
    }

    @Override
    public QuestionPaperItem selectPaperItemById(int id) {
        return paperDao.selectPaperItemById(id);
    }


    @Override
    public int selectABRatePaper(String name, int type) {
        return paperDao.selectABRatePaper(name,type);
    }

    @Override
    public PaperNameInfo selectPaperNameInfo(int id) {
        return paperDao.selectPaperNameInfo(id);
    }

}
