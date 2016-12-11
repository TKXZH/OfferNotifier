package top.xvzonghui;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailTask extends TimerTask{
	private String text = "默认邮件正文";
	private String header = "默认主题";
	public MailTask(String text, String header) {
		this.header = header;
		this.text = text;
	}
	@Override
	public void run() {
        try {
        	String formatStr = "yyyy-MM-dd";
        	String today = new SimpleDateFormat(formatStr).format(new Date());
    		String tomorrow = today.substring(0,today.length()-2) + String.valueOf(Integer.parseInt(today.substring(today.length()-2,today.length()))+1);
        	this.header = tomorrow + "武大宣讲信息";
			this.text = DataProcessor.refresh();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //String to = "954079120@qq.com";

        // 发件人电子邮箱
        String from = "xvzh@mails.ccnu.edu.cn";
        
        // 收件人电子邮箱
        String to[] = {"954079120@qq.com","1071980069@qq.com","962792501@qq.com"};
        ArrayList<InternetAddress> tos = new ArrayList<InternetAddress>();
        for(int i=0; i<to.length; i++) {
        	try {
				tos.add(new InternetAddress(to[i]));
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        // 指定发送邮件的主机为 host
        String host = "smtp.exmail.qq.com";  //QQ企业 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
        public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("xvzh@mails.ccnu.edu.cn", "马赛克"); //发件人邮件用户名、密码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            Iterator<InternetAddress> iterator = tos.iterator();
            while(iterator.hasNext()) {
            	message.addRecipient(Message.RecipientType.TO,
            			iterator.next());
            }
            // Set Subject: 头部头字段
            message.setSubject(header,"utf8");

            // 设置消息体
            message.setText(text,"utf8");

            // 发送消息
            Transport.send(message);
            System.out.println("SUCCESS");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
	
}
