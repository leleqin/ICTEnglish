package com.thoughtWorks.dao;

import com.thoughtWorks.entity.paper.QuestionPaper;

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

}
