package com.thoughtWorks.service.impl;

import com.thoughtWorks.dao.PaperDao;
import com.thoughtWorks.entity.paper.QuestionPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtWorks.service.PaperManageService;

import java.util.List;

/**
 * 我的试卷业务逻辑实现层
 */
@Service
public class PaperManageServiceImpl implements PaperManageService {
    @Autowired
    PaperDao paperDao;

    /**
     * 试卷列表查询
     * @return
     */
    @Override
    public List<QuestionPaper> paperList() {
        return paperDao.paperList();
    }
}
