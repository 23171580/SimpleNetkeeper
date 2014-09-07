package cqxinli;

import java.io.IOException;    
import java.io.InputStream;  
import java.net.HttpURLConnection;    
import java.net.MalformedURLException;    
import java.net.URL;   
import java.net.URLEncoder;


/** 
 *  
 * @author CrazyChen@CQUT
 * 
 */    
public class Router {     
    private String ip;
    private String username;
    private String password;
    private boolean isAuthed;
    private String dialer;
    private String dialingPWD;
    private CXKUsername un;
    public Router(String ip,String username,String pswd,String dialer,String dialingPWD) {    
        this.ip=ip;
        this.username=username;
        this.password=pswd;
        this.dialer=dialer;
        this.dialingPWD=dialingPWD;
        this.un=new CXKUsername(this.dialer);
        this.isAuthed=false;
        runCgi("http://"+this.ip,this.username+":"+this.password);
        
    }    
      
    /**
     * @return The connection statement of the router you configured.<br>
     * - -2 if unable to access the device with the account and password user given.<br>
     * - -1 if unable to encode the username and password to URL Encoding<br>
     * - 0 if configuration success.<br>
     * - 1 if IP address is not valid<br>
     * - 3 if the router returns that no authority to access this device.(Always caused by the ROM rejected the access even though your name and password is right)<br>
     * - 4 if InputStream processing error (IOException Occurred)<br>
     * - 9 if the application detected another login request to complete this operation, or some routers limited this functions that permision denied.<br>
     * - 10 if error Unknown
     */
    public int connect(){
    	if(isAuthed){
    		String encodeName=null;
    		String encodePassword=null;
    		try{
    			//�滻���ֵ�+Ϊ�ո񣬷����û�������
    			encodeName=URLEncoder.encode(un.Realusername(),"UTF-8").replace("+", "%2D");
    			encodePassword=URLEncoder.encode(this.dialingPWD, "UTF-8");
    		}catch(Exception ex){
    			ex.printStackTrace();
    			return -1;
    		}
    		//Ŀ���ַ����������·������½������������
    		String URL="http://"+this.ip+"/userRpm/PPPoECfgRpm.htm?wan=0&wantype=2&acc="+encodeName+"&psw="+encodePassword+"&confirm="+encodePassword
    				+"sta_ip=0.0.0.0&sta_mask=0.0.0.0&linktype=2&Connect=%C1%AC+%BD%D3";
    		
    		URL tar=null;
    		HttpURLConnection Tarhuc=null;
    		try{
    			tar=new URL(URL);
    			if(tar!=null){
    				Tarhuc=(HttpURLConnection) tar.openConnection();
    				if(Tarhuc!=null){
    					//���ù���Ա��Cookie
    					Tarhuc.setRequestProperty("Cookie", "Authorization=Basic "+Base64.encode(this.username+":"+this.password));   					
    					//��������ҳ����Ȩ�޴���
    					Tarhuc.setRequestProperty("Referer", "http://"+this.ip+"/userRpm/PPPoECfgRpm.htm");
    					Tarhuc.setRequestProperty("Host", this.ip);
    					Tarhuc.setRequestProperty("Connection", "Keep-alive");
    					Tarhuc.setRequestProperty("Content-Length", "0");
    					Tarhuc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    				}   				
    				Tarhuc.setConnectTimeout(5000);
    				DataFrame.showTips("�����¼��Ϣ��ͷ������ϣ���ʼ�������");
    				Tarhuc.connect();   				
    				InputStream is=Tarhuc.getInputStream();
    				int data=0;
    				StringBuffer sb=new StringBuffer();
    				while((data=is.read())!=-1){
    					sb.append((char)data);
    				}
    				String ResponseHTML=sb.toString();
    				System.out.println(ResponseHTML);
    				DataFrame.showTips("���ڴ���������");
    				//����ظ���Ȩ��
    				if(ResponseHTML.indexOf("You have no authority to access this device!")>=0){    					
    					return 3;
    				}else if(ResponseHTML.indexOf("noframe")>=0 || ResponseHTML.indexOf("������")>=0 || ResponseHTML.indexOf("PPPoECfgRpm.htm")>=0){    						
    					return 0;
    				}else if(ResponseHTML.indexOf("loginBox")>=0){
    					new FormTips(URL);    					
    				}
    				
    			}
    		}catch(MalformedURLException ex){
    			ex.printStackTrace();
    			return 1;
    		} catch (IOException e) {				
				e.printStackTrace();
				return 4;
			}
    		return 9;
    	}
    	else
    		return -2;
    } 
      
	/**
	 * <p>��ʼ��·���������������</p>
	 * <p>Initial whether the router is available to be operated.</p>
	 * <p>·��������֤��ʽ�������û����������Ժ󣬵��ñ���JS����BASE64���ܣ���������Ϊ Base64.Encode(�û���:����)��Ȼ������COOKIE��ˢ�±���ҳ�档<br>����ˢ��ʱ�Զ��ύCOOKIE����˿��Խ�
	 * ��֤������ڿͻ��˴���</p>
	 * <p>The authorization method for router:after user input the user name and password,the login page use local JavaScript method to encrypt these info with Base64<br>and set cookie.The Content encrypted is Base64(username:password),and then refresh local page.<br>
	 * Due to the cookie is uploaded automatically,the authorization can be simply processed by client</p>
	 * @param urlStr: The remote address to configure.
	 * @param authorizationStr: Username and password (for access network)
	 */
	private void runCgi(String urlStr, String authorizationStr) {    
		DataFrame.showTips("������֤·����������");
        URL xUrl = null;    
        HttpURLConnection xHuc = null;    
        try {    
            xUrl = new URL(urlStr);  
            if (xUrl != null) {    
                xHuc = (HttpURLConnection) xUrl.openConnection();    
                if (xHuc != null) {    
                    if (!"".equals(authorizationStr)) {    
                    	//����·������COOKIE��֤
                        xHuc.setRequestProperty("Cookie", "Authorization=Basic "+Base64.encode(authorizationStr));
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
                    DataFrame.showTips("����Ƿ����");
                    //���ÿ���  �����⵽��½�ɹ���Ŀ�ܴ���
                    //set it available if detected keyword that appear in the page which means login success.
                    if(html.indexOf("noframe")>0 || html.indexOf("frame")>=0){
                    	this.isAuthed=true;                   	
                    }
                    //DEBUG�ã������������
                    System.out.print(this.isAuthed+"\nBasic "    
                            + Base64.encode(authorizationStr)+"\n"+html);
                }    
            }    
        } catch (MalformedURLException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }  
    }    
}    
