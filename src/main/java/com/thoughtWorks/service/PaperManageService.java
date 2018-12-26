package com.thoughtWorks.service;

import com.thoughtWorks.entity.paper.QuestionPaper;

import java.util.List;

/**
 * 我的试卷数据业务逻辑接口层
 */
public interface PaperManageService {

    /**
     * 试卷查询
     * @return
     */
    List<QuestionPaper> paperList();

    /**
     * 删除试卷
     * @param id
     */
    void deletePaper(int id);
}
