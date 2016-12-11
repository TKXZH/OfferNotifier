package top.xvzonghui;


import java.nio.charset.StandardCharsets;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.apache.commons.codec.binary.Base64;;

public class MessageService {
	public static HttpClient client = new HttpClient();
	public static void sendMessage(String content) throws Exception {
		client.start();
		//String baseUrl = "http://sms-api.luosimao.com/v1/send_batch.json";
		String baseUrl = "http://sms-api.luosimao.com/v1/send.json";
		String username = "api";
		String password = "key-8a387137663252092a3a7b305fadffc6";
		byte[] credentials = Base64.encodeBase64((username + ":" + password).getBytes(StandardCharsets.UTF_8));
		ContentResponse response = client.POST(baseUrl)
				 .header("Authorization","Basic " + new String(credentials, StandardCharsets.UTF_8))
				 .param("mobile", "13296677517")
				 .param("message", content)
				 .send();
		System.out.println(response.getContentAsString());
		client.stop();
		
	}
}
