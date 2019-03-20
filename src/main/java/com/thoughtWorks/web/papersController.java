package com.thoughtWorks.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.thoughtWorks.dto.Result;
import com.thoughtWorks.entity.paper.PaperNameInfo;
import com.thoughtWorks.entity.paper.QuestionPackage;
import com.thoughtWorks.entity.paper.QuestionPaper;
import com.thoughtWorks.entity.paper.QuestionPaperItem;
import com.thoughtWorks.entity.question.*;
import com.thoughtWorks.service.PaperManageService;
import com.thoughtWorks.service.QuestionManageService;
import com.thoughtWorks.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 我的试卷控制层
 * @author ChenJiale
 */

@Component
@RequestMapping("/ictEnglish/paper")
@SessionAttributes({"questionPackage","oldPapers","newPaper","paperNameInfo","abReapeatPackage"})
public class papersController {


    @Autowired
    PaperManageService paperManageService;

    @Autowired
    private QuestionManageService questionManageService;


    Boolean insert = false;

    private QuestionPackage abPackage = null;
    private static String DIR="E:\\SMXY\\download\\";
    private static String PWDDB = "root";


    /**
     * 将用户输入的试题放入数据库
     * @author liu
     */
    @RequestMapping("/getDataBase")
    @ResponseBody
    public QuestionPackage getDataBase(@RequestBody String data,Model model){
        JSONObject jData = JSON.parseObject(data);
        String type = jData.getString("type");
        System.out.println("------------type:"+type);
        QuestionPackage questionPackage = new QuestionPackage();
        switch (type){
            case "Selection":   //选择题
                questionPackage.setSelectionList(questionManageService.selectSelection());
                break;
            case "TorF":        //判断题
                questionPackage.setTorFList(questionManageService.selectTorF());
                break;
            case "WordEnToCN":  //词组英译汉
                questionPackage.setWordEnToCNList(questionManageService.selectWordEnToCN());
                break;
            case "WordCnToEN":  //词组汉译英
                questionPackage.setWordCnToENList(questionManageService.selectWordCnToEN());
                break;
            case "Explanation": //缩略词解释
                questionPackage.setExplanationList(questionManageService.selectExplanation());
                break;
            case "SentenceEnToCN":  //句子英译汉
                questionPackage.setSentenceEnToCNList(questionManageService.selectSentenceEnToCN());
                break;
            case "SentenceCnToEN":  //句子汉译英
                questionPackage.setSentenceCnToENList(questionManageService.selectSentenceCnToEN());
                break;
            default:
                break;
        }
        return questionPackage;
    }

    /**
     * 从数据库删除问题
     *  @author liu
     */
    @RequestMapping("/removeQuestionInDB")
    @ResponseBody
    public void removeQuestionInDB(@RequestBody String data){
        System.out.println("removeQuestionInDB:"+data);
        JSONObject jData = JSON.parseObject(data);
        String id = jData.getString("id");
        String type = jData.getString("type");
        switch (type){
            case "Selection":       //选择题
                questionManageService.deleteSelection(Integer.parseInt(id));
                break;
            case "TorF":            //判断题
                questionManageService.deleteTorF(Integer.parseInt(id));
                break;
            case "WordEnToCN":      //短语英译汉
                questionManageService.deleteWordEnToCN(Integer.parseInt(id));
                break;
            case "WordCnToEN":      //短语汉译英
                questionManageService.deleteWordCnToEN(Integer.parseInt(id));
                break;
            case "Explanation":     //缩略词解释
                questionManageService.deleteExplanation(Integer.parseInt(id));
                break;
            case "SentenceEnToCN":  //句子英译汉
                questionManageService.deleteSentenceEnToCN(Integer.parseInt(id));
                break;
            case "SentenceCnToEN":  //句子汉译英
                questionManageService.deleteSentenceCnToEN(Integer.parseInt(id));
                break;
            default:
                break;
        }
    }

    /**
     * 更新文件
     *  @author liu
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Result uploadFile(MultipartFile file, HttpServletRequest request){
        System.out.println("uploadFile");
        String resultStr = null;
        String filePath = DIR + "upload.doc";
        File uploadFile = FileUtil.creatFile(filePath);
        String outPath = DIR + "upload.txt";
        FileUtil.creatFile(outPath);

        try {
            if (file == null){
                System.out.println("uploadFile:file is null");
            }else {
                file.transferTo(uploadFile);
                String cmd = DIR + "wordoper.exe preview "+ filePath+" "+outPath;
                CmdUtil.runCmd(cmd);
                resultStr = FileUtil.readFileByLines(outPath);
            }
            FileUtil.deleleFile(outPath);
            return Result.success(resultStr,Constant.UPLOAD_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        FileUtil.deleleFile(outPath);
        return Result.failure(null, Constant.UPDATE_FAILURE);
    }

    @RequestMapping("/afterPreview")
    public void afterPreview(Model model){
        model.addAttribute("questionPackage",null);
    }

    /**
     * 导入试题
     *  @author liu
     */
    @RequestMapping("/importPaper")
    @ResponseBody
    public Result importPaper(){
        String filePath = DIR+"upload.doc";
        String outPath = DIR+"upload.txt";
        if (!FileUtil.isExist(filePath)){
            return Result.failure(filePath,"文件不存在");
        }
        String cmd = DIR+"wordoper.exe read " + filePath + " " + outPath + " " + PWDDB;
        CmdUtil.runCmd(cmd);
        FileUtil.deleleFile(filePath);
        FileUtil.deleleFile(outPath);

        return Result.success();
    }

    /**
     * 导入失败
     *  @author liu
     */
    @RequestMapping("/notImport")
    @ResponseBody
    public void notImport(){
        System.out.println("notImport");
        String filePath = DIR+"upload.doc";
        String outPath = DIR+"upload.txt";
        FileUtil.deleleFile(filePath);
        FileUtil.deleleFile(outPath);
    }

    /**
     * 将选择题加入到数据库中
     *  @author liu
     */
    @RequestMapping("/addSelectionToDB")
    @ResponseBody
    public Result addSelectionToDB(@RequestBody String data){
        System.out.println("before addSelection");
        JSONObject jData = JSON.parseObject(data);
        String question = jData.getString("question");
        String answerA = jData.getString("answerA");
        String answerB = jData.getString("answerB");
        String answerC = jData.getString("answerC");
        String answerD = jData.getString("answerD");
        String answer = jData.getString("answer");
        String knowledge = jData.getString("knowledge");
        String difficulty = jData.getString("difficulty");
        question = Base64Util.deal_img_html(question);
        answerA = Base64Util.deal_img_html(answerA);
        answerB = Base64Util.deal_img_html(answerB);
        answerC = Base64Util.deal_img_html(answerC);
        answerD = Base64Util.deal_img_html(answerD);
        answer = Base64Util.deal_img_html(answer);
        Selection selection = new Selection();
        selection.setQuestion(question);
        selection.setAnswerA(answerA);
        selection.setAnswerB(answerB);
        selection.setAnswerC(answerC);
        selection.setAnswerD(answerD);
        selection.setAnswer(answer);
        selection.setDifficulty(difficulty);
        selection.setKnowledgePoint(knowledge);
        System.out.println("-----------"+selection.getKnowledgePoint());
        selection.setDifficulty(difficulty);
        questionManageService.addSelectionToDB(selection);
        System.out.println("after addSelection");
        return Result.success();
    }

    /**
     * 将判断题加入到数据库
     *  @author liu
     */
    @RequestMapping("/addTorFToDB")
    @ResponseBody
    public Result addTorFToDB(@RequestBody String data){
        JSONObject jData = JSON.parseObject(data);
        String question = jData.getString("question");
        /*String answerA = jData.getString("answerA");
        String answerB = jData.getString("answerB");*/
        String answer = jData.getString("answer");
        String knowledge = jData.getString("knowledge");
        String difficulty = jData.getString("difficulty");
        question = Base64Util.deal_img_html(question);
      /*  answerA = Base64Util.deal_img_html(answerA);
        answerB = Base64Util.deal_img_html(answerB);*/
        answer = Base64Util.deal_img_html(answer);
        TorF torF = new TorF();
        torF.setQuestion(question);
      /*  torF.setAnswerA(answerA);
        torF.setAnswerB(answerB);*/
        torF.setAnswer(answer);
        torF.setKnowledgePoint(knowledge);
        // System.out.println("-----------"+selection.getKnowledgePoint());
        torF.setDifficulty(difficulty);
        questionManageService.addTorFToDB(torF);
        //System.out.println("after addSelection");
        return Result.success();
    }

    /**
     * 添加除选择题和判断题以外的题目到数据库中
     *  @author liu
     */
    @RequestMapping("/addQuestionToDB")
    @ResponseBody
    public Result addQuestionToDB(@RequestBody String data){
        JSONObject jData = JSON.parseObject(data);
        String type = jData.getString("type");
        String question = jData.getString("question");
        String answer = jData.getString("answer");
        String knowledge = jData.getString("knowledge");
        String difficulty = jData.getString("difficulty");
        question = Base64Util.deal_img_html(question);
        answer = Base64Util.deal_img_html(answer);
        switch (type){
            case "WordEnToCN":  //词组英译汉
                WordEnToCN wordEnToCN = new WordEnToCN();
                wordEnToCN.setQuestion(question);
                wordEnToCN.setAnswer(answer);
                wordEnToCN.setKnowledgePoint(knowledge);
                wordEnToCN.setDifficulty(difficulty);
                questionManageService.addWordEnToCNToDB(wordEnToCN);
                break;
            case "WordCnToEN":  //词组汉译英
                WordCnToEN wordCnToEN = new WordCnToEN();
                wordCnToEN.setQuestion(question);
                wordCnToEN.setAnswer(answer);
                wordCnToEN.setKnowledgePoint(knowledge);
                wordCnToEN.setDifficulty(difficulty);
                questionManageService.addWordCnToENToDB(wordCnToEN);
                break;
            case "Explanation": //缩略词解释
                Explanation explanation = new Explanation();
                explanation.setQuestion(question);
                explanation.setAnswer(answer);
                explanation.setKnowledgePoint(knowledge);
                explanation.setDifficulty(difficulty);
                questionManageService.addExplanationToDB(explanation);
                break;
            case "SentenceEnToCN":  //句子英译汉
                SentenceEnToCN sentenceEnToCN = new SentenceEnToCN();
                sentenceEnToCN.setQuestion(question);
                sentenceEnToCN.setAnswer(answer);
                sentenceEnToCN.setKnowledgePoint(knowledge);
                sentenceEnToCN.setDifficulty(difficulty);
                questionManageService.addSentenceEnToCNToDB(sentenceEnToCN);
                break;
            case "SentenceCnToEN":  //句子汉译英
                SentenceCnToEN sentenceCnToEN = new SentenceCnToEN();
                sentenceCnToEN.setQuestion(question);
                sentenceCnToEN.setAnswer(answer);
                sentenceCnToEN.setKnowledgePoint(knowledge);
                sentenceCnToEN.setDifficulty(difficulty);
                questionManageService.addSentenceCnToENToDB(sentenceCnToEN);
                break;
            default:
                break;
        }
        return Result.success();
    }

    /**
     * 下载模板
     *  @author liu
     */
    @RequestMapping("/toDownLoadModule")
    public ResponseEntity<byte[]> toDownLoadModule(HttpServletRequest request,HttpServletResponse response){
        String paperName = "import_module.doc";
        String wordName = DIR + paperName;
        File wordFile = new File(wordName);

        byte[] body = FileUtil.readByte(wordFile);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(wordFile.getName(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body,headers, HttpStatus.OK);
        return entity;
    }

    /**
     *  @author  changduo
     *  getByTypeAndChapter方法是来查询搜索数据的
     */
    @RequestMapping("/getByTypeAndChapter")
    @ResponseBody
    public QuestionPackage getByTypeAndChapter(@RequestBody String data) {
        JSONObject jData = JSON.parseObject(data);
        System.out.println(jData);
        String types = jData.getString("type");
        List<String> type_list = JSON.parseArray(types, String.class);
        Boolean isAll = jData.getBoolean("isAll");
        System.out.println("getByTypeAndChapter");
        System.out.println("type_list:" + type_list);
        System.out.println("isAll:" + isAll);

        List<Selection> selection = null;
        List<TorF> torf = null;
        List<WordEnToCN> wordentocn = null;
        List<WordCnToEN> wordcntoen = null;
        List<Explanation> explanation = null;
        List<SentenceEnToCN> sentenceentocn = null;
        List<SentenceCnToEN> sentencecntoen = null;
        for(int i = 0; i < type_list.size();i++) {
            switch(type_list.get(i)) {
                case "Selection":
                    if(selection == null) {
                        selection = new ArrayList<Selection>();
                    }
                    if(isAll) {
                        selection = questionManageService.selectSelection();
                    } else {
                        for(int j = 0; j < type_list.size(); j++) {
                            String condition = "%"+type_list.get(j)+"%";
                            List<Selection> newList = questionManageService.selectSelectionByChapter(condition);
                            for(Selection question:newList) {
                                if(!selection.contains(question)) {
                                    selection.add(question);
                                }
                            }
                        }
                    }
                    break;
                case "TorF": //命名题
                    if(torf == null) {
                        torf = new ArrayList<TorF>();
                    }

                    if(isAll) {
                        torf = questionManageService.selectTorF();
                    } else {
                        for(int j = 0; j < type_list.size(); j++) {
                            String condition = "%"+type_list.get(j)+"%";
                            List<TorF> newList = questionManageService.selectTorFByChapter(condition);
                            for(TorF question:newList) {
                                if(!torf.contains(question)) {
                                    torf.add(question);
                                }
                            }
                        }
                    }

                    break;
                case "WordEnToCN": //完成反应式题
                    if(wordentocn == null) {
                        wordentocn = new ArrayList<WordEnToCN>();
                    }
                    if(isAll) {
                        wordentocn = questionManageService.selectWordEnToCN();
                    } else {
                        for(int j = 0; j < type_list.size(); j++) {
                            String condition = "%"+type_list.get(j)+"%";
                            List<WordEnToCN> newList = questionManageService.selectWordEnToCNByChapter(condition);
                            for(WordEnToCN question:newList) {
                                if(!wordentocn.contains(question)) {
                                    wordentocn.add(question);
                                }
                            }
                        }
                    }
                    break;
                case "WordCnToEN": //推断结构题
                    if(wordcntoen == null) {
                        wordcntoen = new ArrayList<WordCnToEN>();
                    }
                    if(isAll) {
                        wordcntoen = questionManageService.selectWordCnToEN();
                    } else {
                        for(int j = 0; j < type_list.size(); j++) {
                            String condition = "%"+type_list.get(j)+"%";
                            List<WordCnToEN> newList = questionManageService.selectWordCnToENByChapter(condition);
                            for(WordCnToEN question:newList) {
                                if(!wordcntoen.contains(question)) {
                                    wordcntoen.add(question);
                                }
                            }
                        }
                    }

                    break;
                case "Explanation": //比较分析题
                    if(explanation == null) {
                        explanation = new ArrayList<Explanation>();
                    }
                    if(isAll) {
                        explanation = questionManageService.selectExplanation();
                    } else {
                        for(int j = 0; j < type_list.size(); j++) {
                            String condition = "%"+type_list.get(j)+"%";
                            List<Explanation> newList = questionManageService.selectExplanationByChapter(condition);
                            for(Explanation question:newList) {
                                if(!explanation.contains(question)) {
                                    explanation.add(question);
                                }
                            }
                        }
                    }
                    break;
                case "SentenceEnToCN": //鉴别题
                    if(sentenceentocn == null) {
                        sentenceentocn = new ArrayList<SentenceEnToCN>();
                    }
                    if(isAll) {
                        sentenceentocn = questionManageService.selectSentenceEnToCN();
                    } else {
                        for(int j = 0; j < type_list.size(); j++) {
                            String condition = "%"+type_list.get(j)+"%";
                            List<SentenceEnToCN> newList = questionManageService.selectSentenceEnToCNByChapter(condition);
                            for(SentenceEnToCN question:newList) {
                                if(!sentenceentocn.contains(question)) {
                                    sentenceentocn.add(question);
                                }
                            }
                        }
                    }
                    break;
                case "SentenceCnToEN": //合成题
                    if(sentencecntoen == null) {
                        sentencecntoen = new ArrayList<SentenceCnToEN>();
                    }
                    if(isAll) {
                        sentencecntoen = questionManageService.selectSentenceCnToEN();
                    } else {
                        for(int j = 0; j < type_list.size(); j++) {
                            String condition = "%"+type_list.get(j)+"%";
                            List<SentenceCnToEN> newList = questionManageService.selectSentenceCnToENByChapter(condition);
                            for(SentenceCnToEN question:newList) {
                                if(!sentencecntoen.contains(question)) {
                                    sentencecntoen.add(question);
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        QuestionPackage questionPackage = new QuestionPackage();
        questionPackage.setSelectionList(selection);
        questionPackage.setTorFList(torf);
        questionPackage.setWordEnToCNList(wordentocn);
        questionPackage.setWordCnToENList(wordcntoen);
        questionPackage.setExplanationList(explanation);
        questionPackage.setSentenceEnToCNList(sentenceentocn);
        questionPackage.setSentenceCnToENList(sentencecntoen);
        return questionPackage;
    }


    /**
     *@author  changduo
     * setDataBeforeSelect是用来检查是否添加了试卷名称
     */

    @RequestMapping("/setDataBeforeSelect")
    @ResponseBody
    public void setDataBeforeSelect(@RequestBody String data,Model model) {
        JSONObject jData = JSON.parseObject(data);
        insert = jData.getBoolean("fromPreview");
        String paperName = jData.getString("paperName");
        int paperType = Integer.parseInt(jData.getString("paperType"));
        System.out.println("setDataBeforeSelect");
        System.out.println("insert:"+insert);
        System.out.println("paperName:"+paperName);
        System.out.println("paperType:"+paperType);
        PaperNameInfo paperNameInfo = new PaperNameInfo(paperName, paperType);
        model.addAttribute("paperNameInfo", paperNameInfo);
        int id = getRepeatPaper(paperNameInfo);
        if (id!=-1) {
            abPackage = generatePaper(Integer.toString(id));
            //System.out.println("repaetABPackage:" +abPackage);
        }
    }

    /**
     * @author  changduo
     * addQuestionToPaper完成选题功能
     */
    @RequestMapping("/addQuestionToPaper")
    @ResponseBody
    public Result addQuestionToPaper(@RequestBody QuestionPackage questionPackage, Model model, HttpSession httpSession){
        //System.out.println("getQuestion: "+questionPackage.toString());
        System.out.println("addQuestionToPaper insert: "+insert);
        System.out.println(questionPackage);
        if(!insert) {
            model.addAttribute("questionPackage", questionPackage);
            System.out.println(questionPackage);
            addRepeatABPackage(questionPackage,model);
        } else {
            QuestionPackage sessionQuestion = (QuestionPackage) httpSession.getAttribute("questionPackage");
//            System.out.println("session: "+sessionQuestion);
            sessionQuestion.addList(questionPackage);
            model.addAttribute("questionPackage", sessionQuestion);
            addRepeatABPackage(sessionQuestion,model);
        }
//        System.out.println("sdddd");
        return Result.success();

    }

    /**
     * @author  changduo
     *removeQestionPreview删除预览页面的选题
     */
    @RequestMapping("/removeQestionPreview")
    @ResponseBody
    public Result removeQestionPreview(@RequestBody String data,Model model, HttpSession httpSession) {
        System.out.println("removeQestionPreview");
        JSONObject jData = JSON.parseObject(data);
        String id = jData.getString("id");
        String type = jData.getString("type");
        System.out.println("id:"+id+" type: "+type);
        QuestionPackage sQuestion = (QuestionPackage) httpSession.getAttribute("questionPackage");
        System.out.println("removeQestionPreview session:"+ sQuestion);
        switch (type) {
            case "Selection"://选择题
                System.out.println("SELECTION");
                removeQuestion(sQuestion.getSelectionList(),id);
                break;
            case "TorF": //判断题
                removeQuestion(sQuestion.getTorFList(),id);
                break;
            case "WordEnToCN": //常用词组英译汉
                removeQuestion(sQuestion.getWordEnToCNList(),id);
                break;
            case "WordCnToEN": //常用词组汉译英
                removeQuestion(sQuestion.getWordCnToENList(),id);
                break;
            case "Explanation": //缩略词解释
                removeQuestion(sQuestion.getExplanationList(),id);
                break;
            case "SentenceEnToCN": //句子英译汉
                removeQuestion(sQuestion.getSentenceEnToCNList(),id);
                break;
            case "SentenceCnToEN": //句子汉译英
                removeQuestion(sQuestion.getSentenceCnToENList(),id);
                break;
            default:
                break;
        }
        model.addAttribute("questionPackage", sQuestion);
        addRepeatABPackage(sQuestion,model);
        return Result.success();
    }

    /**
     * @author  changduo
     *store生成试卷
     */
    @RequestMapping("/store")
    @ResponseBody
    public Result store( Model model, HttpSession httpSession) {
        System.out.println("store");
        getPaperList(model);
        QuestionPackage sQuestion = (QuestionPackage) httpSession.getAttribute("questionPackage");
        PaperNameInfo paperNameInfo = (PaperNameInfo) httpSession.getAttribute("paperNameInfo");
        //System.out.println("PaperNameInfo:"+paperNameInfo);
        //System.out.println("用户选择试题内容有："+sQuestion.toString());
        ArrayList<String> paperIdList= new ArrayList<>();

        if(sQuestion.getSelectionList()!=null) {//插入选择题到数据库
            paperIdList.add(generateQIdList(QType.Selection, sQuestion.getSelectionList()));
        }

        if(sQuestion.getTorFList()!=null) {//插入判断题到数据库
            paperIdList.add(generateQIdList(QType.TorF, sQuestion.getTorFList()));
        }

        if(sQuestion.getWordEnToCNList()!=null) {//插入常用词组英译汉到数据库
            paperIdList.add(generateQIdList(QType.WordEnToCN, sQuestion.getWordEnToCNList()));
        }

        if(sQuestion.getWordCnToENList()!=null) {//插入常用词组汉译英到数据库
            paperIdList.add(generateQIdList(QType.WordCnToEN, sQuestion.getWordCnToENList()));
        }

        if(sQuestion.getExplanationList()!=null) {//插入缩略词解释到数据库
            paperIdList.add(generateQIdList(QType.Explanation, sQuestion.getExplanationList()));
        }

        if(sQuestion.getSentenceEnToCNList()!=null) {//插入句子英译汉到数据库
            paperIdList.add(generateQIdList(QType.SentenceEnToCN, sQuestion.getSentenceEnToCNList()));
        }

        if(sQuestion.getSentenceCnToENList()!=null) {//插入句子汉译英到数据库
            paperIdList.add(generateQIdList(QType.SentenceCnToEN, sQuestion.getSentenceCnToENList()));
        }

        if(paperIdList!=null && paperIdList.size()>0) {
            QuestionPaper questionPaper = new QuestionPaper();
            Date currentTime=new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            questionPaper.setGenerateTime(df.format(currentTime));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(paperNameInfo != null) {
                questionPaper.setName(paperNameInfo.getName());
                questionPaper.setType(paperNameInfo.getType());
            } else {
                questionPaper.setName("练习题"+sdf.format(currentTime));
                questionPaper.setType(3);
            }

            questionPaper.setAuthorId(1);
            JSONObject  questionList = new JSONObject();
            questionList.put("试卷题目表", paperIdList);
            questionPaper.setQuestionList(questionList.toString());
            //System.out.println("试卷试题列表："+questionList.toString());
            questionManageService.addPaper(questionPaper);
            model.addAttribute("newPaper", questionPaper);
        }

        return Result.success();
    }

    /**
     * @author  changduo
     *  removeQuestion对应上面removeQestionPreview方法删除预览试卷的题目
     */
    private void removeQuestion(List<? extends Question> questionList, String id) {
        int rdex = 0;
        int i = 0;
        for(i = 0 ; i < questionList.size();i++) {
            if(questionList.get(i).getId() == Integer.parseInt(id)) {
                rdex = i;
                break;
            }
        }
        if(i<questionList.size()) {
            questionList.remove(rdex);
        }
    }
    /**
     * @author  changduo
     *  generateQIdList生成试卷的题目编号和重复试卷编号插入到eurasia_ictenglish_paper_question_lists表中
     */
    private String generateQIdList(QType type, List<? extends Question> questionList ) {
        ArrayList<JSONObject>  idListItem = new ArrayList<JSONObject> ();
        for (int i = 0; i<questionList.size(); i++) {
            JSONObject  idItem = new JSONObject();
            idItem.put("题目编号", questionList.get(i).getId());
            idItem.put("重复试卷编号","0");
            idListItem.add(idItem);
        }
        JSONObject idList = new JSONObject();
        idList.put("题目编号列表", idListItem);

        QuestionPaperItem questionPaperItem = new QuestionPaperItem();
        questionPaperItem.setqType(QTypeUtil.getTypeId(type));
        questionPaperItem.setScore("2");
        questionPaperItem.setqIdList(idList.toString());

        questionManageService.addQuestionToPaper(questionPaperItem);
        return String.valueOf(questionPaperItem.getId());
    }



    /**
     * 试卷列表查询
     * @author ChenJiale
     * @param model
     */
    @RequestMapping("/getPaperList")
    @ResponseBody
    public void getPaperList( Model model) {
        QuestionPaper newPaper = new QuestionPaper();
        newPaper.setId(-1);
        model.addAttribute("newPaper",newPaper);
        System.out.println("np:"+newPaper);
        List<QuestionPaper> oldPaperList = paperManageService.selectPaperList();
        model.addAttribute("oldPapers", oldPaperList);
        System.out.println("getPaperList OLDPapers:"+oldPaperList);
    }

    /**
     * 删除试卷
     * @author ChenJiale
     * @param id
     */
    @RequestMapping("/removePaper")
    @ResponseBody
    public void removePaper(@RequestBody String id,Model model) {
        int deleteId = Integer.parseInt(id);
        System.out.println("removePaper paper id:"+id);
        List<Integer> qTLists = getQuestionList(id);
        for(int i: qTLists) {
            paperManageService.deleteQuestions(i);
        }
        paperManageService.deletePaper(deleteId);

        QuestionPaper newPaper = new QuestionPaper();
        newPaper.setId(-1);
        model.addAttribute("newPaper",newPaper);
        model.addAttribute("oldPapers",paperManageService.selectPaperList());
    }

    /**
     * 清除session
     * @author ChenJiale
     */
    @RequestMapping("clearSession")
    public void clearSession(SessionStatus sessionStatus, HttpSession session){
        sessionStatus.setComplete();
        System.out.println("clear session ok.");
    }

    /**
     * 浏览试卷
     * @author ChenJiale
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toPreviewPaper")
    @ResponseBody
    public Result toPreviewPaper(@RequestBody String id, Model model) {
        System.out.println("selectPaper id = "+id);
        QuestionPackage questionPackage = generatePaper(id);
        PaperNameInfo paperNameInfo = paperManageService.selectPaperNameInfo(Integer.parseInt(id));
        model.addAttribute("questionPackage", questionPackage);
        setABPaper(paperNameInfo);
        addRepeatABPackage(questionPackage,model);
        model.addAttribute("paperNameInfo", paperNameInfo);

        return Result.success();
    }

    /**
     * AB试卷
     * @author ChenJiale
     * @param paperNameInfo
     */
    private void setABPaper(PaperNameInfo paperNameInfo) {
        int id = getRepeatPaper(paperNameInfo);
        if (id!=-1) {
            abPackage = generatePaper(Integer.toString(id));
//            System.out.println("repaetABPackage:" +abPackage);
        } else {
            abPackage = null;
        }
    }

    /**
     * 获取重复率
     * @author ChenJiale
     * @param pNameInfo
     * @return
     */
    private int getRepeatPaper(PaperNameInfo pNameInfo) {
        int type = -1;
        int id = -1;
        if(pNameInfo.getType() == 1) {
            type = 2;
        } else if(pNameInfo.getType() == 2) {
            type = 1;
        }
        if(type == 1 || type == 2) {
            id = paperManageService.selectABRatePaper(pNameInfo.getName(),type);
        }
//        System.out.println("id:"+id);
        return id;
    }

    /**
     * 添加重复率
     * @author ChenJiale
     * @param qp
     * @param model
     */
    private void addRepeatABPackage(QuestionPackage qp, Model model) {

        QuestionPackage reapeatABPackage = new QuestionPackage();
        if(qp!=null && abPackage!=null) {
            reapeatABPackage = qp.comp(abPackage);
        }
        model.addAttribute("abReapeatPackage", reapeatABPackage);
    }

    /**
     * 下载试卷
     * @author ChenJiale
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toDownLoadPaper")
    public ResponseEntity<byte[]> toDownLoadPaper (@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("downLoad paper id:"+id);
        Boolean isPaper = true;
        QuestionPackage questionPackage = generatePaper(id);
        String paperString = writePaperOrAnwserStr(questionPackage,isPaper);
        String paperName = getPaperName(id);
        FileUtil.creatDir(DIR);
        String txtName = DIR + paperName + ".txt";
        File txtFile = FileUtil.creatFile(txtName);

        FileUtil.writeFile(txtFile, paperString);

        String wordName = DIR + paperName + ".doc";
        File wordFile = FileUtil.creatFile(wordName);

        String cmd =DIR+"wordoper.exe"+" write "+txtName+" "+DIR+"module.docx "+wordName+" 陕西国际商贸学院考试试卷"+" 有机化学";
        CmdUtil.runCmd(cmd);

        byte[] body = FileUtil.readByte(wordFile);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(wordFile.getName(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);

        txtFile.delete();
        wordFile.delete();

        return entity;
    }

    /**
     * 下载答案
     * @author ChenJiale
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toDownloadAnswer")
    public ResponseEntity<byte[]>  toDownloadAnswer (@RequestParam("id") String id, HttpServletRequest request,HttpServletResponse response) {
        System.out.println("downLoad paper id:"+id);
        Boolean isPaper = false;
        QuestionPackage questionPackage = generatePaper(id);
        String paperString = writePaperOrAnwserStr(questionPackage,isPaper);
        String paperName = getPaperName(id);
        FileUtil.creatDir(DIR);
        String txtName = DIR + paperName + "答案.txt";
        File txtFile = FileUtil.creatFile(txtName);

        FileUtil.writeFile(txtFile, paperString);

        String wordName = DIR + paperName + "答案.doc";
        File wordFile = FileUtil.creatFile(wordName);

        String cmd =DIR+"wordoper.exe"+" answer "+txtName+" "+wordName;
        CmdUtil.runCmd(cmd);

        byte[] body = FileUtil.readByte(wordFile);
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Disposition", "attchement;filename=" + URLEncoder.encode(wordFile.getName(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);

        txtFile.delete();
        wordFile.delete();
        return entity;
    }


    /**
     * 获取试题列表
     * @author ChenJiale
     * @param id
     * @return
     */
    private List<Integer> getQuestionList( String id ) {
        QuestionPaper questionPaper = paperManageService.selectPaperQListById(Integer.parseInt(id));
        System.out.println("selectPaper questionPaper = "+questionPaper);
        JSONObject jsonQList = JSON.parseObject(questionPaper.getQuestionList());
        String qTList = jsonQList.getString("试卷题目表");
        qTList = qTList.replaceAll("\\[", "");
        qTList = qTList.replaceAll("\\]", "");
        //System.out.println("questionList = "+qTList);
        List<String> qTLists = java.util.Arrays.asList(qTList.split(","));
        List<Integer> qList = new ArrayList<Integer>();

        for(String item:qTLists) {
            int index = Integer.parseInt(item.replace("\"", ""));
            qList.add(index);
        }
        return qList;
    }

    /**
     * 生成试卷id
     * @author ChenJiale
     * @param id
     * @return
     */
    private QuestionPackage generatePaper(String id) {
        List<Integer> qTLists = getQuestionList(id);
        QuestionPackage questionPackage = new QuestionPackage();
        QuestionPaperItem qPaperItem = new QuestionPaperItem();

        for(int item:qTLists) {
            qPaperItem = paperManageService.selectPaperItemById(item);
            generateQuestionPackage(qPaperItem,questionPackage);
        }
        System.out.println("generatePaper"+questionPackage);
        return questionPackage;
    }

    /**
     * 试题包
     * @author ChenJiale
     * @param qPaperItem
     * @param qPackage
     */
    private void generateQuestionPackage(QuestionPaperItem qPaperItem, QuestionPackage qPackage) {
        QType qType = QTypeUtil.getQType(qPaperItem.getqType());
        JSONObject jsonData = JSON.parseObject(qPaperItem.getqIdList());
        JSONArray jsonList = jsonData.getJSONArray("题目编号列表");
        System.out.println("题目编号列表："+jsonList);
        List<Integer> idList = new ArrayList<Integer>();
        for(Object listItem: jsonList) {
            JSONObject idJson = JSON.parseObject(listItem.toString());
            int id = idJson.getIntValue("题目编号");
            idList.add(id);
        }

        switch(qType) {
            case Selection:
                List<Selection> selectionList = new ArrayList<Selection>();
                for( int id: idList) {
                    selectionList.add(paperManageService.selectSelectionById(id));
                }
                qPackage.setSelectionList(selectionList);
                break;
            case TorF:
                List<TorF> torFList = new ArrayList<TorF>();
                for( int id: idList) {
                    torFList.add(paperManageService.selectTorFById(id));
                }
                qPackage.setTorFList(torFList);
                break;
            case WordEnToCN:
                List<WordEnToCN> wordEnToCNList = new ArrayList<WordEnToCN>();
                for( int id: idList) {
                    wordEnToCNList.add(paperManageService.selectWordEnToCNById(id));
                }
                qPackage.setWordEnToCNList(wordEnToCNList);
                break;
            case WordCnToEN:
                List<WordCnToEN> wordCnToENList = new ArrayList<WordCnToEN>();
                for( int id: idList) {
                    wordCnToENList.add(paperManageService.selectWordCnToENById(id));
                }
                qPackage.setWordCnToENList(wordCnToENList);
                break;
            case Explanation:
                List<Explanation> explanationList = new ArrayList<Explanation>();
                for( int id: idList) {
                    explanationList.add(paperManageService.selectExplanationById(id));
                }
                qPackage.setExplanationList(explanationList);
                break;
            case SentenceEnToCN:
                List<SentenceEnToCN> sentenceEnToCNList = new ArrayList<SentenceEnToCN>();
                for( int id: idList) {
                    sentenceEnToCNList.add(paperManageService.selectSentenceEnToCNById(id));
                }
                qPackage.setSentenceEnToCNList(sentenceEnToCNList);
                break;
            case SentenceCnToEN:
                List<SentenceCnToEN> sentenceCnToENList = new ArrayList<SentenceCnToEN>();
                for( int id: idList) {
                    sentenceCnToENList.add(paperManageService.selectSentenceCnToENById(id));
                }
                qPackage.setSentenceCnToENList(sentenceCnToENList);
                break;
        }
        System.out.println("generateQuestionPackage: "+qPackage);
    }

    /**
     * 试卷答案
     * @author ChenJiale
     * @param questionPackage
     * @param isPaper
     * @return
     */
    private String writePaperOrAnwserStr(QuestionPackage questionPackage,Boolean isPaper) {
        String writeStr="";
        List<Selection> selection = questionPackage.getSelectionList();
        List<TorF> torf = questionPackage.getTorFList();
        List<WordEnToCN> wordentocn = questionPackage.getWordEnToCNList();
        List<WordCnToEN> wordcntoen = questionPackage.getWordCnToENList();
        List<Explanation> explanation = questionPackage.getExplanationList();
        List<SentenceEnToCN> sentenceentocn = questionPackage.getSentenceEnToCNList();
        List<SentenceCnToEN> sentencecntoen = questionPackage.getSentenceCnToENList();

        if(selection!=null && selection.size() != 0 ) {
            if(isPaper) {
                writeStr = writeStr + writeQuestionStr(QType.Selection,selection);
            } else {
                writeStr = writeStr + writeAnswerStr(QType.Selection,selection);
            }
        }

        if(torf!=null && torf.size() != 0) {
            if(isPaper) {
                writeStr = writeStr + writeQuestionStr(QType.TorF,torf);
            } else {
                writeStr = writeStr + writeAnswerStr(QType.TorF,torf);
            }
        }

        if(wordentocn != null && wordentocn.size() != 0) {
            if(isPaper) {
                writeStr = writeStr + writeQuestionStr(QType.WordEnToCN,wordentocn);
            } else {
                writeStr = writeStr + writeAnswerStr(QType.WordEnToCN,wordentocn);
            }

        }

        if(wordcntoen != null && wordcntoen.size() != 0) {
            if(isPaper) {
                writeStr = writeStr + writeQuestionStr(QType.WordCnToEN,wordcntoen);
            } else {
                writeStr = writeStr + writeAnswerStr(QType.WordCnToEN,wordcntoen);
            }
        }

        if(explanation != null && explanation.size() != 0) {
            if(isPaper) {
                writeStr = writeStr + writeQuestionStr(QType.Explanation,explanation);
            } else {
                writeStr = writeStr + writeAnswerStr(QType.Explanation,explanation);
            }
        }

        if(sentenceentocn != null && sentenceentocn.size() != 0) {
            if(isPaper) {
                writeStr = writeStr + writeQuestionStr(QType.SentenceEnToCN,sentenceentocn);
            } else {
                writeStr = writeStr + writeAnswerStr(QType.SentenceEnToCN,sentenceentocn);
            }
        }

        if(sentencecntoen !=null && sentencecntoen.size() != 0) {
            if(isPaper) {
                writeStr = writeStr + writeQuestionStr(QType.SentenceCnToEN,sentencecntoen);
            } else {
                writeStr = writeStr + writeAnswerStr(QType.SentenceCnToEN,sentencecntoen);
            }
        }
        return writeStr;
    }

    /**
     * 试题答案
     * @author ChenJiale
     * @param type
     * @param questionList
     * @return
     */
    private String writeAnswerStr(QType type, List<?extends Question> questionList) {
        String writeStr="";
        String title = QTypeUtil.getTypeName(type);
        writeStr = writeStr+title+"\n";
        int i = 1;
        for(Question question:questionList) {
            if(question != null) {
                writeStr = writeStr + addIndex(question.getAnswer(),String.valueOf(i));
            }
            i++;
        }
        return writeStr;
    }

    /**
     * 试题
     * @author ChenJiale
     * @param type
     * @param questionList
     * @return
     */
    private String writeQuestionStr(QType type, List<?extends Question> questionList) {
        String writeStr = "";
        String title = QTypeUtil.getTypeName(type);
        writeStr = writeStr+title+"\n";
        int i = 1;
        for(Question question:questionList) {
            if(question != null) {
                if(type != QType.Selection ) {
                    writeStr = writeStr + addIndex(question.getQuestion(),String.valueOf(i));
                } else {
                    Selection se =(Selection) question;
                    writeStr = writeStr + addIndex(se.getQuestion(),String.valueOf(i));
                    writeStr = writeStr + addIndex(se.getAnswerA(),"A");
                    writeStr = writeStr + addIndex(se.getAnswerB(),"B");
                    writeStr = writeStr + addIndex(se.getAnswerC(),"C");
                    writeStr = writeStr + addIndex(se.getAnswerD(),"D");
                }
            }
            i++;
        }
        return writeStr;
    }

    /**
     * 添加Index
     * @author ChenJiale
     * @param question
     * @param index
     * @return
     */
    private String addIndex(String question, String index) {
        String result=null;
        String left ="<p>";
        String right ="</p>";
        result = question;
        if(result!=null) {
            result = result.replace(left, "");
            result = result.replace(right, "");
            result = index + ". " + result;
            result = left+result+right+"\n";
        }
        return result;
    }

    /**
     * 获取试卷名字
     * @author ChenJiale
     * @param id
     * @return
     */
    private String getPaperName(String id) {
        QuestionPaper questionPaper = paperManageService.selectPaperQListById(Integer.parseInt(id));
        String paperName = questionPaper.getName();
        int type = questionPaper.getType();
        System.out.println("type:"+type);
        if(type == 1) {
            paperName = paperName + "——A卷";
        } else if(type == 2 ){
            paperName = paperName + "——B卷";
        }
        System.out.println("paperName:"+paperName);
        return paperName;
    }



}



