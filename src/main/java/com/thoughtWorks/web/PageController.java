package com.thoughtWorks.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * Creat by liwenlu
 * ICT English Paper System
 * this file use to set the index
 * */

@Controller
@RequestMapping("ictEnglish")
public class PageController {
    /*登录后跳转至首页*/
    @RequestMapping("index")
    public String index(){
        return "ictEnglish/index";
    }
    
    /*跳转至我的试卷*/
    @RequestMapping("paperList")
    public String paperList(){
        return "ictEnglish/paperList";
    }

    /*跳转至预览页面*/
    @RequestMapping("paperPreview")
    public String paperPreview(){
        return "ictEnglish/paperPreview";
    }
    
    /*跳转至手动出题*/
    @RequestMapping("manualPaper")
    public String manualPaper(){
        return "ictEnglish/manualPaper";
    }
    
    /*跳转至题库管理*/
    @RequestMapping("dbPage")
    public String dbPage(){
        return "ictEnglish/dbPage";
    }

    /*跳转到章节练习*/
    @RequestMapping("auto")
    public String auto(){
        return "ictEnglish/auto";
    }

    /*跳转到导入试卷*/
    @RequestMapping("import")
    public String importPaper(){
        return "ictEnglish/import";
    }

    /*跳转到试题预览*/
    @RequestMapping("questionpreview")
    public String questionpreview(){
        return "ictEnglish/questionpreview";
    }

    /*跳转到选择试题页面*/
    @RequestMapping("addSelectionPage")
    public String addSelectionPage(){
        return "ictEnglish/addSelectionPage";
    }

    /*跳转到判断题页面*/
    @RequestMapping("addTorFPage")
    public String addTorFPage(){ return "ictEnglish/addTorFPage";}

    /*跳转到添加试题页面*/
    @RequestMapping("addQuestionPage")
    public String addQuestionPage(){
        return "ictEnglish/addQuestionPage";
    }

    @RequestMapping("editor")
    public String editor(){
        return "kindeditor/jsp/demo";
    }

    @RequestMapping("testEdit")
    public String uploadJson(){
        return "kindediter/jsp/testEdit";
    }
}
