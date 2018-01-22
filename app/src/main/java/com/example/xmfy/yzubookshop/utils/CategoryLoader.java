package com.example.xmfy.yzubookshop.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.xmfy.yzubookshop.R;
import com.example.xmfy.yzubookshop.module.selling.bean.Category1;
import com.example.xmfy.yzubookshop.module.selling.bean.Category2;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xmfy on 2018/1/18.
 */
public class CategoryLoader {

    public static List<Category1> cList1 = new ArrayList<>();
    public static List<List<Category2>> cList2 = new ArrayList<>();

    public static void loadCategories(Context context){
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

    public static HashMap<String, Integer> getCategoryIdByName(String cname1, String cname2){
        HashMap<String, Integer> result = new HashMap<>();
        int position = 0;
        for(int i = 0; i<cList1.size(); i++){
            if (cList1.get(i).getName().equals(cname1)){
                result.put("cid1", cList1.get(i).getId());
                position = i;
                break;
            }
        }
        List<Category2> list = cList2.get(position);
        for (Category2 c2 : list){
            if (c2.getName().equals(cname2)){
                result.put("cid2", c2.getId());
                break;
            }
        }
        return result;
    }

    public static HashMap<Integer, String> getCategoryNameById(int cid1, int cid2){
        HashMap<Integer, String> result = new HashMap<>();
        int position = 0;
        for (int i = 0; i<cList1.size(); i++){
            if (cList1.get(i).getId() == cid1){
                result.put(1, cList1.get(i).getName());
                position = i;
                break;
            }
        }
        List<Category2> list = cList2.get(position);
        for(Category2 c2 : list){
            if (c2.getId() == cid2){
                result.put(2, c2.getName());
                break;
            }
        }
        return result;
    }

}
