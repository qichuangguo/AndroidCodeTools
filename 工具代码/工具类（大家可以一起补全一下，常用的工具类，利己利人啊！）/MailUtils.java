package com.itheima.ebs.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.itheima.ebs.domain.User;

public class MailUtils {

	/**
	 * 发送邮件
	 * @author lt
	 * @param user
	 */
	public static void send(User user) {
		
		try {
			//TODO 0  基本数据，放置配置文件 --.properties
			String mail_host = "localhost";
			String mail_user = "admin";
			String mail_password = "123456";
			String mail_domain = "lt.cn";
			String mail_url = "http://localhost:8080/ebookstore/user/UserServlet?method=active";
			
			// 0.1 准备参数
			Properties props = new Properties();
			props.setProperty("mail.host", mail_host);
			props.setProperty("mail.smtp.auth", "true");
			
			// 0.2 验证
			MyAuthenticator authenticator = new MyAuthenticator(mail_user, mail_password);
			
			//1 回话
			Session session = Session.getDefaultInstance(props, authenticator);
			
			//2 消息
			MimeMessage message = new MimeMessage(session);
			// 2.1 发件人 --> admin@lt.cn
			message.setFrom(new InternetAddress(mail_user + "@" + mail_domain));
			// 2.2 收件人
			message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
			// 2.3 
			message.setSubject("xxx图书商城用户激活邮件");
			// 2.4 
			String url = mail_url + "&id="+user.getId();
			String data = user.getUsername() + ":<br/>" +
							" 您好 ,您在本商城注册了用户，请<a href='"+url+"'>点击</a>进行激活,<br/>" +
							"如果不能点击，请复制下面链接进行激活<br/>" +
							url +"<br/>" +
							"如果不是本人，请删除该邮件。";
			
			message.setContent(data, "text/html;charset=UTF-8");
			
			
			//3 发送
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}


class MyAuthenticator extends Authenticator{
	
	private String username;
	private String password;

	public MyAuthenticator(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username,password);
	}
}
