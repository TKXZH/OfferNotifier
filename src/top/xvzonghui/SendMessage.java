package top.xvzonghui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SendMessage {
	public static void send(String content) {
        SendMessage message = new SendMessage();
        String httpResponse =  message.testSend(content);
         try {
            JSONObject jsonObj = new JSONObject( httpResponse );
            int error_code = jsonObj.getInt("error");
            String error_msg = jsonObj.getString("msg");
            if(error_code==0){
                System.out.println("Send message success.");
            }else{
                System.out.println("Send message failed,code is "+error_code+",msg is "+error_msg);
            }
        } catch (JSONException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }

        httpResponse =  message.testStatus();
        try {
            JSONObject jsonObj = new JSONObject( httpResponse );
            int error_code = jsonObj.getInt("error");
            if( error_code == 0 ){
                int deposit = jsonObj.getInt("deposit");
                System.out.println("Fetch deposit success :"+deposit);
            }else{
                String error_msg = jsonObj.getString("msg");
                System.out.println("Fetch deposit failed,code is "+error_code+",msg is "+error_msg);
            }
        } catch (JSONException ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private String testSend(String content){
        // just replace key here
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
            "api","key-42e46a2137198da66833d8d4a8e0f293"));
        WebResource webResource = client.resource(
            "http://sms-api.luosimao.com/v1/send_batch.json");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile_list", "13296677517");
        formData.add("message", content);
        ClientResponse response =  webResource.type( MediaType.APPLICATION_FORM_URLENCODED ).
        post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        //System.out.print(textEntity);
        //System.out.print(status);
        return textEntity;
    }

    private String testStatus(){
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
            "api","key-d609b769db914a4d959bae3414ed1f7X"));
        WebResource webResource = client.resource( "http://sms-api.luosimao.com/v1/status.json" );
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        ClientResponse response =  webResource.get( ClientResponse.class );
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        //System.out.print(status);
        //System.out.print(textEntity);
        return textEntity;
    }
}
