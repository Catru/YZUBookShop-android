package com.example.xmfy.yzubookshop.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.module.selling.bean.Category2;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmfy on 2018/1/18.
 */
public class CategoryLoader {

    public static void loadCategories(Context context, List<Category1> cList1, List<List<Category2>> cList2){
        Category1 c1 = null;
        Category2 c2 = null;
        List<Category2> list2 = new ArrayList<>();
        if (cList2 == null)
            cList2 = new ArrayList<>();
        XmlResourceParser parser = context.getResources().getXml(R.xml.category_list);
        try{
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT){
                String tag = parser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if ("c1".equals(tag)){
                            list2 = new ArrayList<>();
                            c1 = new Category1();
                            c1.setId(Integer.parseInt(parser.getAttributeValue(0)));
                            c1.setName(parser.getAttributeValue(1));
                        }else if ("c2".equals(tag)){
                            c2 = new Category2(Integer.parseInt(parser.getAttributeValue(0)), parser.getAttributeValue(1));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("c1".equals(tag)){
                            cList1.add(c1);
                            cList2.add(list2);
                        }else if("c2".equals(tag)){
                            list2.add(c2);
                        }
                        break;
                }
                event = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
