package com.thoughtWorks.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Encoder;
import java.util.regex.*;

public class Base64Util {
    public static String getImageStr(String path){
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    static String getImgPath(String imghtml)
    {
        String str2 = "src\\s*=\\s*\"(.*?)\"";
        Pattern pt = Pattern.compile(str2);
        Matcher match = pt.matcher(imghtml);
        while (match.find()) {
            return match.group(1);
        }
        return "";
    }

    static String getBase64ByPath(String path)
    {   
        String base64Str = "<img src=\\\"data:image/png; base64,"+getImageStr(path)+"/>";
        //return "<img src=\"data:image/png; base64,iVBORw0KGgoAAAANSUhEUgAAg==\" width=\"48\" height=\"36\"/>";
        return base64Str;
    }


    public static String deal_img_html(String html)
    {
        String str2 = "<\\s*img.*?>";

        Pattern pt = Pattern.compile(str2);
        Matcher match = pt.matcher(html);
        while (match.find()) 
        {
            String img_html = match.group(); //get all <img xxxxxx> 
            if (img_html == "")
                continue;

            String img_path = getImgPath(img_html);
            String replaceImg = img_html.replace(">", "/>");
            html = html.replace(img_html, replaceImg);
            //html = html.replace(img_html, getBase64ByPath(img_path));
        }
        return html;
    }
}
