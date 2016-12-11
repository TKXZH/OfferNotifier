package top.xvzonghui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataProcessor {
	public static ContentResponse response;
	public static HttpClient client;
	public DataProcessor() throws Exception {
		
		
	}
	public static String refresh() throws Exception {
		client = new HttpClient();
		client.start();	
		String formatStr = "yyyy-MM-dd";
		String today = new SimpleDateFormat(formatStr).format(new Date());
		String tomorrow = today.substring(0,today.length()-2) + String.valueOf(Integer.parseInt(today.substring(today.length()-2,today.length()))+1);
		System.out.println(tomorrow);
		response = client.POST("http://www.xsjy.whu.edu.cn/zftal-web/zfjy!wzxx/xjhxx_cxXjhForWeb.html?doType=query")
						 .param("queryModel.currentPage", "1")
						 .param("queryModel.showCount", "80")
						 .param("queryModel.sortName", "t1.sfyx, abs(t1.jlsj) ,t1.xjhkssj")
						 .param("queryModel.sortOrder", "asc").send();
		JSONObject json = JSON.parseObject(response.getContentAsString());
		JSONArray json2 = json.getJSONArray("items");
		System.out.println(json2);
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(Object object : json2) {
			JSONObject jsonObject = (JSONObject)object;
			Map<String, String> map = new HashMap<String,String>();
			map.put("name",(String)jsonObject.get("xjhmc"));
			map.put("date",(String)jsonObject.get("xjhrq"));
			map.put("time",(String)jsonObject.get("xjhjssj"));
			map.put("address",(String)jsonObject.get("xjhcdmc"));
			map.put("url","http://www.xsjy.whu.edu.cn/zftal-web/zfjy!wzxx/zfjy!wzxx/xjhxx_ckXjhxx.html?sqbh="+(String)jsonObject.get("sqbh"));
 			if(map.get("date").equals(tomorrow)) {
				list.add(map);
			}
 			
 			client.stop();
		}
		StringBuffer sb = new StringBuffer();
		for(Map<String,String> map:list) {
			sb.append(map.get("name")+"  ");
			System.out.println(map.get("name"));
			sb.append(map.get("date")+"  ");
			sb.append(map.get("time")+"  ");
			sb.append(map.get("address")+" ");	
			sb.append("详情点击:" + map.get("url")+"\n");
		}
		//String content = new String("你好");
		//MessageService.sendMessage(content);
		//SendMessage.send(sb.toString());
		return sb.toString();
	}
	public static void main(String args[]) throws Exception {
		DataProcessor dp = new DataProcessor();
		System.out.println(DataProcessor.refresh());
	}
}
