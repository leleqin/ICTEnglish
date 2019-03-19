package com.thoughtWorks.service.impl;

import java.util.List;

import com.thoughtWorks.dao.QuestionDao;
import com.thoughtWorks.entity.paper.PaperNameInfo;
import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.entity.paper.QuestionPaperItem;
import com.thoughtWorks.entity.question.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtWorks.service.QuestionManageService;


/**
 *
 * @author liu
 * smxy question system
 * service level
 *
 */

@Service
public class QuestionManageServiceImpl implements QuestionManageService {


    @Autowired
    QuestionDao questionDao;

    @Override
    public List<Selection> selectSelection() {
        List<Selection> list = questionDao.selectSelection();
        return list;
    }

    @Override
    public List<TorF> selectTorF() {
        List<TorF> list = questionDao.selectTorF();
        return list;
    }

    @Override
    public List<WordEnToCN> selectWordEnToCN() {
        List<WordEnToCN> list = questionDao.selectWordEnToCN();
        return list;
    }

    @Override
    public List<WordCnToEN> selectWordCnToEN() {
        List<WordCnToEN> list = questionDao.selectWordCnToEN();
        return list;
    }

    @Override
    public List<Explanation> selectExplanation() {
        List<Explanation> list = questionDao.selectExplanation();
        return list;
    }

    @Override
    public List<SentenceEnToCN> selectSentenceEnToCN() {
        List<SentenceEnToCN> list = questionDao.selectSentenceEnToCN();
        return list;
    }

    @Override
    public List<SentenceCnToEN> selectSentenceCnToEN() {
        List<SentenceCnToEN> list = questionDao.selectSentenceCnToEN();
        return list;
    }


    @Override
    public void deleteSelection(int id) {
        questionDao.deleteSelection(id);
    }

    @Override
    public void deleteTorF(int id) {
        questionDao.deleteTorF(id);
    }

    @Override
    public void deleteWordEnToCN(int id) {
        questionDao.deleteWordEnToCN(id);
    }

    @Override
    public void deleteWordCnToEN(int id) {
        questionDao.deleteWordCnToEN(id);
    }

    @Override
    public void deleteExplanation(int id) {
        questionDao.deleteExplanation(id);
    }

    @Override
    public void deleteSentenceEnToCN(int id) {
        questionDao.deleteSentenceEnToCN(id);
    }

    @Override
    public void deleteSentenceCnToEN(int id) {
        questionDao.deleteSentenceCnToEN(id);
    }

    @Override
    public void addSelectionToDB(Selection selection) {
        questionDao.addSelectionToDB(selection);
    }

    @Override
    public void addTorFToDB(TorF torF) {
        questionDao.addTorFToDB(torF);
    }

    @Override
    public void addWordEnToCNToDB(WordEnToCN wordEnToCN) {
        questionDao.addWordEnToCNToDB(wordEnToCN);
    }

    @Override
    public void addWordCnToENToDB(WordCnToEN wordCnToEN) {
        questionDao.addWordCnToENToDB(wordCnToEN);
    }

    @Override
    public void addExplanationToDB(Explanation explanation) {
        questionDao.addExplanationToDB(explanation);
    }

    @Override
    public void addSentenceEnToCNToDB(SentenceEnToCN sentenceEnToCN) {
        questionDao.addSentenceEnToCNToDB(sentenceEnToCN);
    }

    @Override
    public void addSentenceCnToENToDB(SentenceCnToEN sentenceCnToEN) {
        questionDao.addSentenceCnToENToDB(sentenceCnToEN);
    }
}
