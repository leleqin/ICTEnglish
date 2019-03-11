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
//    @RequestMapping("paperPreview")
//    public String paperPreview(){
//        return "ictEnglish/paperPreview";
//    }
    
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

}
