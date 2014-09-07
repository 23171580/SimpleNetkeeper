package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

public class ClickDebug implements ActionListener{

	protected FormPanel ip;
	protected FormPanel admin;
	protected PasswordPanel pwd;
	
	public ClickDebug(FormPanel ip,FormPanel admin,PasswordPanel pwd){
		this.ip=ip;
		this.admin=admin;
		this.pwd=pwd;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(!ip.getValue().equals("") && !admin.getValue().equals("")){
			URL xUrl = null;    
	        HttpURLConnection xHuc = null;    
	        try {    
	            xUrl = new URL("http://"+ip.getValue());  
	            if (xUrl != null) {    
	                xHuc = (HttpURLConnection) xUrl.openConnection();    
	                if (xHuc != null) {    
	                    if (!"".equals(admin.getValue()+":"+pwd.getPassword())) {    
	                    	//����·������COOKIE��֤
	                        xHuc.setRequestProperty("Cookie", "Authorization=Basic "+Base64.encode(admin.getValue()+":"+pwd.getPassword()));
	                    }                         
	                    xHuc.setRequestProperty("Content-Length", "0");    
	                    xHuc.setRequestProperty("Content-Type",    
	                            "application/x-www-form-urlencoded");    
	                    xHuc.connect();     
	                    InputStream in=xHuc.getInputStream();  
	                    int chint=0;  
	                    StringBuffer sb=new StringBuffer();  
	                    while((chint=in.read())!=-1){  
	                        sb.append((char)chint);  
	                    }  
	                    String html=sb.toString(); 
	                    Boolean isAuthed=false;
	                    //���ÿ���  �����⵽��½�ɹ���Ŀ�ܴ���
	                    //set it available if detected keyword that appear in the page which means login success.
	                    if(html.indexOf("noframe")>0 || html.indexOf("frame")>=0){
	                    	isAuthed=true;                   	
	                    }
	                    //DEBUG�ã������������
	                    System.out.print(isAuthed+"\nBasic "    
	                            + Base64.encode(admin.getValue()+":"+pwd.getPassword())+"\n"+html);
	                }    
	            }    
	        } catch (MalformedURLException e1) {    
	            e1.printStackTrace();    
	        } catch (IOException e1) {    
	            e1.printStackTrace();    
	        }  
		}else{
			JOptionPane.showMessageDialog(null,"��ȷ���������IP�͹���Ա�˻�����");
		}
	}
	
}
