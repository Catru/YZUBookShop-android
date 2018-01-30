package test;

import com.example.xmfy.yzubookshop.global.AppConstants;
import com.example.xmfy.yzubookshop.model.Book;
import com.example.xmfy.yzubookshop.model.FormedData;
import com.example.xmfy.yzubookshop.utils.OKHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xmfy on 2018/1/27.
 */
public class CommonTest{

    @Test
    public void test(){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("type", "title");
        map.put("value", "2");
        String s = OKHttpUtils.doGetWithParams(AppConstants.QUERY_BY_TYPE, map);
        Gson gson = new Gson();
        FormedData data = gson.fromJson(s, new TypeToken<FormedData<List<Book>>>(){}.getType());
        System.out.println(data.toString());
    }

}
