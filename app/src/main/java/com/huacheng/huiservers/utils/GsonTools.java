package com.huacheng.huiservers.utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonTools {
	public static <T> T getVo(String jsonString, Class<T> cls) {  
        T t = null;  
        try {  
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);  
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return t;  
    }  
  
    public static <T> List<T> getList(JSONArray array, Class<T> cls) {  
        List<T> list = new ArrayList<T>();  
        try {  
        	if(array!=null&&array.length()>0){
        		for (int i = 0; i < array.length(); i++) {
        			T vo=getVo(array.getJSONObject(i).toString(), cls);
        			list.add(vo);
        		}
        	}
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return list;  
    }  
    public static <String, T> Map<String, T> getMap(java.lang.String mapToJson,
			Class<T> cls) {
		Gson gson = new Gson();
		Map<String, T> userMap2 = (Map<String, T>) gson.fromJson(mapToJson,
				new TypeToken<Map<String, T>>() {
				}.getType());
		return userMap2;
	}


    public static String getJson(Object object) {
        String json = "";

        try {
            Gson gson = new Gson();
            json = gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
