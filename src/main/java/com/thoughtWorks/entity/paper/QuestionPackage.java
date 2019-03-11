package com.thoughtWorks.entity.paper;

import java.util.List;


import com.thoughtWorks.entity.question.*;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * 
 * @author Liwenlu
 * ictEnglish  system
 * question type which contain  all the questions of types of one paper
 * the every parameter list is related to a type
 *
 */
class Comp<T>
{
    public void printlst(List<T> lst)
    {
        for(int i = 0; i < lst.size(); i++)
        {
            Question qu = (Question) lst.get(i);
            System.out.println(qu.getId());
        }
    }

    public boolean is_equal(T t1, T t2)
    {
        Question q1 = (Question)t1;
        Question q2 = (Question)t2;

        return q1.getId() == q2.getId();
    }

    public List<T> get_common(List<T> lst1, List<T> lst2)
    {
        List<T> lst = new ArrayList<T>();
        if(lst1!=null && lst2!=null) {
            for(int i = 0; i < lst1.size(); i++)
            {
                T qu1 = lst1.get(i);
                for(int j = 0; j < lst2.size(); j++)
                {
                    T qu2 = lst2.get(j);
                    if (is_equal(qu1, qu2))
                    {
                        lst.add(qu1);
                        break;
                    }
                }
            }
        }


        return lst;
    }
}


public class QuestionPackage {

    List<Selection> selectionList;

    List<TorF> torFList;

    List<WordEnToCN> wordEnToCNList;

    List<WordCnToEN> wordCnToENList;

    List<Explanation> explanationList;

    List<SentenceEnToCN> sentenceEnToCNList;

    List<SentenceCnToEN> sentenceCnToENList;

    public List<Selection> getSelectionList() {
        return selectionList;
    }

    public void setSelectionList(List<Selection> selectionList) {
        this.selectionList = selectionList;
    }

    public List<TorF> getTorFList() {
        return torFList;
    }

    public void setTorFList(List<TorF> torFList) {
        this.torFList = torFList;
    }

    public List<WordEnToCN> getWordEnToCNList() {
        return wordEnToCNList;
    }

    public void setWordEnToCNList(List<WordEnToCN> wordEnToCNList) {
        this.wordEnToCNList = wordEnToCNList;
    }

    public List<WordCnToEN> getWordCnToENList() {
        return wordCnToENList;
    }

    public void setWordCnToENList(List<WordCnToEN> wordCnToENList) {
        this.wordCnToENList = wordCnToENList;
    }

    public List<Explanation> getExplanationList() {
        return explanationList;
    }

    public void setExplanationList(List<Explanation> explanationList) {
        this.explanationList = explanationList;
    }

    public List<SentenceEnToCN> getSentenceEnToCNList() {
        return sentenceEnToCNList;
    }

    public void setSentenceEnToCNList(List<SentenceEnToCN> sentenceEnToCNList) {
        this.sentenceEnToCNList = sentenceEnToCNList;
    }

    public List<SentenceCnToEN> getSentenceCnToENList() {
        return sentenceCnToENList;
    }

    public void setSentenceCnToENList(List<SentenceCnToEN> sentenceCnToENList) {
        this.sentenceCnToENList = sentenceCnToENList;
    }

    public QuestionPackage comp(QuestionPackage quesPack)
    {
        QuestionPackage retPack = new QuestionPackage();

        Comp<Selection> insSel = new Comp<Selection>();
        retPack.setSelectionList(insSel.get_common(selectionList, quesPack.getSelectionList()));

        Comp<TorF> insTor = new Comp<TorF>();
        retPack.setTorFList(insTor.get_common(torFList,quesPack.getTorFList()));

        Comp<WordEnToCN> insWorCN = new Comp<WordEnToCN>();
        retPack.setWordEnToCNList(insWorCN.get_common(wordEnToCNList,quesPack.getWordEnToCNList()));

        Comp<WordCnToEN> insWorEN = new Comp<WordCnToEN>();
        retPack.setWordCnToENList(insWorEN.get_common(wordCnToENList,quesPack.getWordCnToENList()));

        Comp<Explanation> insExp = new Comp<Explanation>();
        retPack.setExplanationList(insExp.get_common(explanationList,quesPack.getExplanationList()));

        Comp<SentenceEnToCN> insSenCN = new Comp<SentenceEnToCN>();
        retPack.setSentenceEnToCNList(insSenCN.get_common(sentenceEnToCNList,quesPack.getSentenceEnToCNList()));

        Comp<SentenceCnToEN> insSenEN = new Comp<SentenceCnToEN>();
        retPack.setSentenceCnToENList(insSenEN.get_common(sentenceCnToENList,quesPack.getSentenceCnToENList()));

        return retPack;
    }



    public int getTotalNum() {
        int total = 0;
        if(selectionList != null) {
            total += selectionList.size();
        }
        if(torFList != null) {
            total += torFList.size();
        }
        if(wordEnToCNList != null) {
            total += wordEnToCNList.size();
        }
        if(wordCnToENList != null) {
            total += wordCnToENList.size();
        }
        if(explanationList != null) {
            total += explanationList.size();
        }
        if(sentenceEnToCNList != null) {
            total += sentenceEnToCNList.size();
        }
        if(sentenceCnToENList != null) {
            total += sentenceCnToENList.size();
        }
        return total;
    }

    public String getAbRepeat(QuestionPackage repeatPackage) {
        int totalNum = this.getTotalNum();
        int reapeatNum = repeatPackage.getTotalNum();
        NumberFormat  numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);
        System.out.println("totalNum:"+totalNum);
        System.out.println("reapeatNum:"+reapeatNum);
        String abReapeatRate = null;

        if(totalNum != 0) {
            abReapeatRate = numberFormat.format((float)reapeatNum/(float)totalNum*100)+"%";
        }
        return abReapeatRate;
    }

/*    public void addList(QuestionPackage qp) {
        if (qp.selectionList != null) {
            if (selectionList == null) {
                selectionList = qp.selectionList;
            } else {
                for (Selection question : qp.selectionList) {
                    if (!selectionList.contains(question)) {
                        selectionList.add(question);
                    }
                }
            }
        }

        if(qp.torFList != null ) {
            if(torFList == null) {
                torFList = qp.torFList;
            } else {
                for(TorF question:qp.torFList) {
                    if(!torFList.contains(question)) {
                        torFList.add(question);
                    }
                }
            }
        }


    }*/

    @Override
    public String toString() {
        return "QuestionPackage{" +
                "selectionList=" + selectionList +
                ", torFList=" + torFList +
                ", wordEnToCNList=" + wordEnToCNList +
                ", wordCnToENList=" + wordCnToENList +
                ", explanationList=" + explanationList +
                ", sentenceEnToCNList=" + sentenceEnToCNList +
                ", sentenceCnToENList=" + sentenceCnToENList +
                '}';
    }




}
