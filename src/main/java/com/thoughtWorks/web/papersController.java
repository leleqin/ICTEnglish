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
import com.thoughtWorks.util.CmdUtil;
import com.thoughtWorks.util.FileUtil;
import com.thoughtWorks.util.QTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

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
@RequestMapping("/paper")
@SessionAttributes({"questionPackage","oldPapers","newPaper","paperNameInfo","abReapeatPackage"})
public class papersController {


    @Autowired
    PaperManageService paperManageService;

    Boolean insert = false;

    private QuestionPackage abPackage = null;
    private static String DIR="E:\\SMXY\\download\\";
    private static String PWDDB = "root";

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



