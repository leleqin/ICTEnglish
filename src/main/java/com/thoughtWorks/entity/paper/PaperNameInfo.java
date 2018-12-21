package com.thoughtWorks.entity.paper;
/*
 * 试卷名称信息
 * */
public class PaperNameInfo {
    public String name;
    int type;


    
    public PaperNameInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public PaperNameInfo(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public int getType() {
        return type;
    }



    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PaperNameInfo [name=" + name + ", type=" + type + "]";
    }





}
