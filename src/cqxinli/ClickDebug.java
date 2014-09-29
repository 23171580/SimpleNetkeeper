package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	                    BufferedReader pBufRdr=new BufferedReader(new InputStreamReader(xHuc.getInputStream(),MainClass.getRouterPageEncode()));
	                    int chint=0;  
	                    StringBuffer sb=new StringBuffer();  
	                    while((chint=pBufRdr.read())!=-1){  
	                        sb.append((char)chint);  
	                    }  
	                    String html=sb.toString(); 
	                    System.out.print(html);
	                    Log.log("�ڡ��°汾�̼�����ʽ��ȡ�õ�HTML��������"+Log.nLine+html);
	                    Boolean isAuthed=false;
	                    //���ÿ���  �����⵽��½�ɹ���Ŀ�ܴ���
	                    //set it available if detected keyword that appear in the page which means login success.
	                    if(html.indexOf("noframe")>0 || html.indexOf("frame")>=0){
	                    	isAuthed=true;                   	
	                    }
	                    Log.log("DEBUG:�����¹̼���ʽ�����մ�����Ϊ��"+(isAuthed?"����":"������"));
	                }    
	            }    
	        } catch (MalformedURLException e1) {    
	            Log.logE(e1); 
	        } catch (IOException e1) {    
	            Log.logE(e1);  
	            this.detectOld(ip.getValue(), admin.getValue()+":"+pwd.getPassword());
	            
	        }  
		}else{
			JOptionPane.showMessageDialog(null,"��ȷ���������IP�͹���Ա�˻�����");
		}
	}
	
	private void detectOld(String URL, String auth) {
		try {
			Log.log("�����Ծɰ汾�ķ�ʽ��������");
			URL pUrl = new URL("http://"+URL);
			HttpURLConnection pHuc = (HttpURLConnection) pUrl.openConnection();

			pHuc.setRequestProperty("Authorization",
					"Basic " + Base64.encode(auth));
			pHuc.setRequestProperty("Content-Length", "0");
			pHuc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			pHuc.connect();
			InputStream in = pHuc.getInputStream();
			StringBuffer sb = new StringBuffer();
			int chint;
			while ((chint = in.read()) != -1) {
				sb.append((char) chint);
			}
			String html = sb.toString();
			if (html.indexOf("noframe") > 0 || html.indexOf("frame") >= 0){
				Log.log("DEBUG:�ԡ��ɰ汾�̼�����ʽ���Ľ��Ϊ����");
			}
			else {
				Log.log("DEBUG:�ԡ��ɰ汾�̼�����ʽ���Ľ��Ϊ������"+Log.nLine+html);
			}
		} catch (IOException e) {
			Log.logE(e);
		}

	}
	
}
