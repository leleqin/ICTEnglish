package com.thoughtWorks.util;

import com.thoughtWorks.entity.question.QType;

public class QTypeUtil {
    public static String getTypeId(QType type) {
        String typeId="";
        switch (type) {
            case Selection:
                typeId = "11";
                break;
            case TorF:
                typeId = "12";
                break;
            case WordEnToCN:
                typeId = "13";
                break;
            case WordCnToEN:
                typeId = "14";
                break;
            case Explanation:
                typeId = "15";
                break;
            case SentenceEnToCN:
                typeId = "16";
                break;
            case SentenceCnToEN:
                typeId = "17";
                break;
        }
        return typeId;
    }

    public static QType getQType( String typeId) {
        QType type = null;
        switch (typeId) {
        case "11":
            type = QType.Selection;
            break;
        case "12":
            type = QType.TorF;
            break;
        case "13":
            type = QType.WordEnToCN;
            break;
        case "14":
            type = QType.WordCnToEN;
            break;
        case "15":
            type = QType.Explanation;
            break;
        case "16":
            type = QType.SentenceEnToCN;
            break;
        case "17":
            type = QType.SentenceCnToEN;
            break;
        }

        return type;
    }

    public static String getTypeName(QType type) {
        String name="";
        switch (type) {
            case Selection:
                name = "选择题";
                break;
            case TorF:
                name = "判断题";
                break;
            case WordEnToCN:
                name = "常用词组英译汉";
                break;
            case WordCnToEN:
                name = "常用词组汉译英";
                break;
            case Explanation:
                name = "缩略词解释";
                break;
            case SentenceEnToCN:
                name = "句子英译汉";
                break;
            case SentenceCnToEN:
                name = "句子汉译英";
                break;
        }
        return name;
    }
}
