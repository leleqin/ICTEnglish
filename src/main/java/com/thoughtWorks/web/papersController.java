package com.thoughtWorks.web;

import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.service.PaperManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 我的试卷控制层
 */

@Component
@RequestMapping("/paper")
public class papersController {

    @Autowired
    PaperManageService paperManageService;

    /**
     * 试卷列表查询
     * @param questionPaper
     * @return
     */
    @RequestMapping("/paperList")
    @ResponseBody
    public List<QuestionPaper> paperList(QuestionPaper questionPaper){
        List<QuestionPaper> questionPaperList = paperManageService.paperList();
        return questionPaperList;
    }
}