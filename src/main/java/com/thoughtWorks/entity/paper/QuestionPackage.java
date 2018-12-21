package com.thoughtWorks.entity.paper;

import java.util.List;


import com.thoughtWorks.entity.question.Question;

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

    





    


}
