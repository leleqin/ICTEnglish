package com.thoughtWorks.util;

import com.thoughtWorks.entity.question.QType;

public class QTypeUtil {
    public static String getTypeId(QType type) {
        String typeId="";
//        switch (type) {
//            case ANAYLISIS:
//                typeId = "11";
//                break;
//            case COMPLETION:
//                typeId = "12";
//                break;
//            case DEDUCTION:
//                typeId = "13";
//                break;
//            case IDENTIFICATION:
//                typeId = "14";
//                break;
//            case NAMING:
//                typeId = "15";
//                break;
//            case SELECTION:
//                typeId = "16";
//                break;
//            case SYNTHESIS:
//                typeId = "17";
//                break;
//        }
        return typeId;
    }

    public static QType getQType( String typeId) {
        QType type = null;
//        switch (typeId) {
//        case "11":
//            type = QType.ANAYLISIS;
//            break;
//        case "12":
//            type = QType.COMPLETION;
//            break;
//        case "13":
//            type = QType.DEDUCTION;
//            break;
//        case "14":
//            type = QType.IDENTIFICATION;
//            break;
//        case "15":
//            type = QType.NAMING;
//            break;
//        case "16":
//            type = QType.SELECTION;
//            break;
//        case "17":
//            type = QType.SYNTHESIS;
//            break;
//        }

        return type;
    }

    public static String getTypeName(QType type) {
        String name="";
//        switch (type) {
//            case ANAYLISIS:
//                name = "比较分析";
//                break;
//            case COMPLETION:
//                name = "完成反应式";
//                break;
//            case DEDUCTION:
//                name = "推断结构";
//                break;
//            case IDENTIFICATION:
//                name = "鉴别";
//                break;
//            case NAMING:
//                name = "命名题";
//                break;
//            case SELECTION:
//                name = "选择题";
//                break;
//            case SYNTHESIS:
//                name = "合成";
//                break;
//        }
        return name;
    }
}
