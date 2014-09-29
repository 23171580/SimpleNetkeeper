package cqxinli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.JOptionPane;

/**
 * @version 1.1
 * @author CrazyChen@CQUT
 * 
 * ����·�������õ���Ҫ�����ࡣ<br>
 * ���ƹ��̣��½���-����ʼ�����ݣ�·�������û���Ϣ��-������·��������-�����������Ƿ��ѳ�ʼ��<br>
 * ����ɵĹ����У���ȡ��ǰ·����SSID����ȡ��ǰ·��������״̬������·�������ţ�����·��������������Ϣ<br>
 * ���ù��췽����<br>
 * Router(String IP,String RouterAccName,String RouterAccPassword,String AccName,String AccPassword)<br>
 * ���������IP��ַ��·��������Ա�û��������룬����ʻ���������<br>
 * Router()<br>
 * ����޲ι�������û�����ϢΪ�գ�����ʹ������״̬Ϊδ��ʼ��
 */
public class Router {
	private String ip;
	private String username;
	private String password;
	private String dialer;
	private String dialingPWD;
	private CXKUsername un;
	private int mAuthMethod;
	private boolean mIsInit;
	public static final int AUTH_OLD = 401;
	public static final int AUTH_WEB = 402;
	public static final int AUTH_NOT_AVALIABLE = 0;

	public Router(String ip, String username, String pswd, String dialer,
			String dialingPWD) {
		this.ip = ip;
		this.username = username;
		this.password = pswd;
		this.dialer = dialer;
		this.dialingPWD = dialingPWD;
		this.un = new CXKUsername(this.dialer);
		this.mIsInit = true;
		this.mAuthMethod = Router.AUTH_NOT_AVALIABLE;
		runCgi("http://" + this.ip, (this.username + ":" + this.password));
	}

	public Router(String ip, String username, String pswd, String dialer,
			String dialingPWD,int authMethod) {
			this.ip = ip;
			this.username = username;
			this.password = pswd;
			this.dialer = dialer;
			this.dialingPWD = dialingPWD;
			this.un = new CXKUsername(this.dialer);
			this.mIsInit = true;
			this.mAuthMethod = authMethod;
			if(authMethod==Router.AUTH_NOT_AVALIABLE) 
				runCgi("http://" + this.ip, (this.username + ":" + this.password));
	}
	
	public Router() {
		this.mAuthMethod = Router.AUTH_NOT_AVALIABLE;
		this.mIsInit = false;
	}

	public void setRouterData(String ip, String username, String pswd,
			String dialer, String dialingPWD) {
		this.ip = ip;
		this.username = username;
		this.password = pswd;
		this.dialer = dialer;
		this.dialingPWD = dialingPWD;
		this.mIsInit = true;
		runCgi("http://" + this.ip, this.username + ":" + this.password);
	}

	
	public static final int RES_UNABLE_ACCESS=-2;
	public static final int RES_UNABLE_ENCODE=-1;
	public static final int RES_SUCCESS=0;
	public static final int RES_IP_INVALID=1;
	public static final int RES_NO_DIAL_MODE=2;
	public static final int RES_NO_AUTHORITY=3;
	public static final int RES_IO_EXCEPTION=4;
	public static final int RES_NO_CONNECTION_OBJ=5;
	public static final int RES_AUTHENTICATION_NO_METHED=6;
	public static final int RES_META_DATA_NOT_INIT=7;
	public static final int RES_ALGRITHOM_NOT_ALLOWED=8;
	public static final int RES_REQUIRE_LOGIN=9;
	public static final int RES_ERROR_UNKNOWN=10;
	/**
	 * @return The connection statement of the router you configured.<br>
	 *         - -2 if unable to access the device with the account and password
	 *         user given.<br>
	 *         - -1 if unable to encode the username and password to URL
	 *         Encoding<br>
	 *         - 0 if configuration success.<br>
	 *         - 2 if dialing mode is not pointed<br>
	 *         - 1 if IP address is not valid<br>
	 *         - 3 if the router returns that no authority to access this
	 *         device.(Always caused by the ROM rejected the access even though
	 *         your name and password is right)<br>
	 *         - 4 if InputStream processing error (IOException Occurred)<br>
	 *         - 5 if application can not get Connection Object<br>
	 *         - 6 if no authentication method available<br>
	 *         - 7 if Meta Data not initialized Correctly;<br>
	 *         - 8 if the Algrithom for calculating truly account is not allowed<br>
	 *         - 9 if the application detected another login request to complete
	 *         this operation, or some routers limited this functions that
	 *         permission denied.<br>
	 *         - 10 if error Unknown
	 */
	public int connect() {
		if (this.mAuthMethod != Router.AUTH_NOT_AVALIABLE && this.mIsInit) {
			String encodeName = null;
			String encodePassword = null;
			if(MainClass.getEncrytedAcc()){
				try {
					Log.log("��ʼ��������û�������");
					// �滻���ֵ�+Ϊ�ո񣬷����û�������
					encodeName = URLEncoder.encode(un.Realusername(), "UTF-8")
							.replace("+", "%2D");
					encodePassword = URLEncoder.encode(this.dialingPWD, "UTF-8");
				} catch (Exception ex) {
					Log.logE(ex);
					return Router.RES_UNABLE_ENCODE;
				}
			}else{
				Log.log("��⵽����ģʽΪ������ģʽ��");
				try {
					encodeName=URLEncoder.encode(username,"UTF-8").replace("+", "%2D");
					encodePassword=URLEncoder.encode(this.dialingPWD,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					Log.logE(e);
				}
				
			}
			
			// Ŀ���ַ����������·������½������������
			
			String URL = null;
			switch(MainClass.getDialType()){
			case MainClass.DIAL_AUTO:
				URL="http://"
						+ this.ip
						+ "/userRpm/PPPoECfgRpm.htm?wan=0&wantype=2&acc="
						+ encodeName
						+ "&psw="
						+ encodePassword
						+ "&confirm="
						+ encodePassword
						+ "sta_ip=0.0.0.0&sta_mask=0.0.0.0&linktype=2&Connect=%C1%AC+%BD%D3";
				;break;
			case MainClass.DIAL_BY_USER:
				URL="http://"
						+ this.ip
						+ "/userRpm/PPPoECfgRpm.htm?wan=0&wantype=2&acc="
						+ encodeName
						+ "&psw="
						+ encodePassword
						+ "&confirm="
						+ encodePassword
						+ "sta_ip=0.0.0.0&sta_mask=0.0.0.0&linktype=4&waittime2=0&Connect=%C1%AC+%BD%D3";
				;break;
			default:return Router.RES_NO_DIAL_MODE;
			}
			//linktype=2 : �Զ�����
			
			Log.log("��鵽����������Ϊ��"+MainClass.getDialType());
			try {
				HttpURLConnection mRouterUrlCon =this.getConnection(URL);
				if (mRouterUrlCon != null) {
					if(this.setDialProperty(mRouterUrlCon)!=0) return Router.RES_NO_DIAL_MODE;
					Log.log("���ڳ�����������");
					mRouterUrlCon.connect();
					return this.getResponse(mRouterUrlCon.getInputStream(),
							URL);
				}
				return Router.RES_NO_CONNECTION_OBJ;
			} catch (MalformedURLException e) {
				Log.logE(e);
				return Router.RES_IP_INVALID;
			} catch (IOException e) {
				Log.logE(e);
				return Router.RES_IO_EXCEPTION;
			}
		}
		Log.log(this.mIsInit ? "�޷�ͨ�����з�ʽ����·��������������Ϊ�˻����󣬻��߲�֧�֡�"
				: "��⵽�û�����δ����ʼ���������Ѿ�ֹͣ");
		return this.mIsInit ? Router.RES_UNABLE_ACCESS : Router.RES_META_DATA_NOT_INIT;
	}

	private void setProperties(HttpURLConnection Tarhuc) {
		// ��������ҳ����Ȩ�޴���
		Tarhuc.setRequestProperty("Referer", "http://" + this.ip + "/");
		Tarhuc.setRequestProperty("Host", this.ip);
		Tarhuc.setRequestProperty("Connection", "Keep-alive");
		Tarhuc.setRequestProperty("Content-Length", "0");
		Tarhuc.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		Tarhuc.setConnectTimeout(2000);
		Log.log("��������Ѿ��������");
	}

	
	/** 
	 * ����һ������״̬����Connect����֮��ʹ��
	 * */
	private int getResponse(InputStream is, String URL) {
		String ResponseHTML=this.getHTMLContent(is);
		if (ResponseHTML
				.indexOf("You have no authority to access this device!") >= 0) {
			Log.log("��⵽�ؼ��֣�����Ȩ����");
			return Router.RES_NO_AUTHORITY;
		} else if (ResponseHTML.indexOf("noframe") >= 0
				|| ResponseHTML.indexOf("������") >= 0
				|| ResponseHTML.indexOf("PPPoECfgRpm.htm") >= 0) {
			return Router.RES_SUCCESS;
		} else if (ResponseHTML.indexOf("loginBox") >= 0) {
			Log.log("��⵽�����½����");
			new FormTips(URL);
		}
		return Router.RES_REQUIRE_LOGIN;
	}
	
	
	/** 
	 * ��ȡ��Զ�̼�������ص�HTML�ı����ݡ���Ҫһ��������
	 * */
	private String getHTMLContent(InputStream is){
		int data = 0;
		String ResponseHTML = null;
		try {
			BufferedReader pBufRd=new BufferedReader(new InputStreamReader(is,MainClass.getRouterPageEncode()));
			StringBuffer sb = new StringBuffer();
			while ((data = pBufRd.read()) != -1) {
				sb.append((char) data);
			}
			ResponseHTML = sb.toString();
			Log.log(ResponseHTML);
		} catch (IOException e) {
			Log.logE(e);
		}
		return ResponseHTML;
	}

	/**
	 * <p>
	 * ��ʼ��·���������������
	 * </p>
	 * <p>
	 * Initial whether the router is available to be operated.
	 * </p>
	 * <p>
	 * ·��������֤��ʽ�������û����������Ժ󣬵��ñ���JS����BASE64���ܣ���������Ϊ
	 * Base64.Encode(�û���:����)��Ȼ������COOKIE��ˢ�±���ҳ�档<br>
	 * ����ˢ��ʱ�Զ��ύCOOKIE����˿��Խ� ��֤������ڿͻ��˴���
	 * </p>
	 * <p>
	 * The authorization method for router:after user input the user name and
	 * password,the login page use local JavaScript method to encrypt these info
	 * with Base64<br>
	 * and set cookie.The Content encrypted is Base64(username:password),and
	 * then refresh local page.<br>
	 * Due to the cookie is uploaded automatically,the authorization can be
	 * simply processed by client
	 * </p>
	 * 
	 * @param urlStr
	 *            : The remote address to configure.
	 * @param authorizationStr
	 *            : Username and password (for access network)
	 */
	private void runCgi(String urlStr, String authorizationStr) {
		if (this.mIsInit) {
			Log.log("���ڳ������°汾�̼��ķ�ʽ�����������");
			HttpURLConnection xHuc = null;
			try {
					xHuc = this.getConnection(urlStr);
					if (xHuc != null) {
						if (!"".equals(authorizationStr)) {
							// ����·������COOKIE��֤
							xHuc.setRequestProperty(
									"Cookie",
									"Authorization=Basic "
											+ Base64.encode(authorizationStr));
						}
						Log.log("��ʼ���Ե�һ������");
						xHuc.connect();
						InputStream in = xHuc.getInputStream();
						int chint = 0;
						StringBuffer sb = new StringBuffer();
						while ((chint = in.read()) != -1) {
							sb.append((char) chint);
						}
						String html = sb.toString();
						Log.log("����Ƿ����");
						// ���ÿ��� �����⵽��½�ɹ���Ŀ�ܴ���
						// set it available if detected keyword that appear in
						// the
						// page which means login success.
						if (html.indexOf("noframe") > 0
								|| html.indexOf("frame") >= 0) {
							this.changeAuthMethod(AUTH_WEB);
						} else {
							// ���Ծɰ汾
							this.changeInitState(false);
						}
						// DEBUG�ã������������
						Log.log(this.mAuthMethod + Log.nLine + "Basic "
								+ Base64.encode(authorizationStr) + Log.nLine
								+ html);
					}
			} catch (MalformedURLException e) {
				this.changeInitState(false);
				Log.logE(e);
			} catch (IOException e) {
				this.changeInitState(false);
				Log.logE(e);
				this.detectOld(urlStr, authorizationStr);
			}
		} else {
			this.changeAuthMethod(Router.AUTH_NOT_AVALIABLE);
		}
	}

	private void changeInitState(boolean ii) {
		this.mIsInit = ii;
		Log.log("�Ѿ�����·�������ӳ�ʼ��״̬Ϊ��" + (ii ? "�ѳ�ʼ��" : "δ��ʼ��"));
		if (!ii)
			changeAuthMethod(Router.AUTH_NOT_AVALIABLE);
	}

	private void changeAuthMethod(int Au) {
		this.mAuthMethod = Au;
		MainClass.setAuthMethod(Au);
		Log.log("�Ѹ���·������֤��ʽΪ��" + Au);
		if(Au!=Router.AUTH_NOT_AVALIABLE) 
			this.changeInitState(true);
	}

	private void detectOld(String URL, String auth) {
		try {
			Log.log("�����Ծɰ汾�ķ�ʽ��������");
			HttpURLConnection pHuc = this.getConnection(URL);

			pHuc.setRequestProperty("Authorization",
					"Basic " + Base64.encode(auth));
			pHuc.connect();
			InputStream in = pHuc.getInputStream();
			StringBuffer sb = new StringBuffer();
			int chint;
			while ((chint = in.read()) != -1) {
				sb.append((char) chint);
			}
			String html = sb.toString();
			if (html.indexOf("noframe") > 0 || html.indexOf("frame") >= 0){
				this.changeAuthMethod(Router.AUTH_OLD);
			}
			else {
				this.changeInitState(false);
				Log.log(html);
			}
		} catch (IOException e) {
			Log.logE(e);
		}

	}
	
	/** 
	 * ����PPPoE����״̬
	 * */
	public void trackLink(){
		boolean getData=true;
		while(getData){
			try {
				HttpURLConnection mHuc=getConnection("http://"+ip+"/userRpm/PPPoECfgRpm.htm");
				if(setDialProperty(mHuc)!=0){
					DataFrame.showTips("û�к��ʵ����ӷ�ʽ����������ʧ��");
				}else{
					mHuc.connect();
					String mContent=getHTMLContent(mHuc.getInputStream());
					if(getResponseData(mContent, "��������")){
						DataFrame.showTips("���ڽ���������");
					}else if(getResponseData(mContent,"������")){
						DataFrame.showTips("�Ѿ�����");
						getData=false;
					}else if(getResponseData(mContent,"������") || getResponseData(mContent,"��Ӧ")){
						DataFrame.showTips("��⵽������û����Ӧ������Ҫ�����������ӡ�");
						getData=false;
					}else if(getResponseData(mContent,"�û���") || getResponseData(mContent,"����") || getResponseData(mContent,"����")){
						DataFrame.showTips("�û��������������");
						getData=false;
					}
					/*
					 * else if(getResponseData(mContent,"")){
						DataFrame.showTips("");
						getData=false;
					}
					 * */
				}
			} catch (MalformedURLException ex) {
				Log.logE(ex);
				getData=false;
			} catch (IOException ex) {
				Log.logE(ex);
				getData=false;
			}
		}
	}
	
	/** 
	 * ��ȡ��ǰWIFI��Ϣ������WIFI�ȵ����ƣ��ȵ�����
	 * */
	public WiFiInfo getWifiState(){
		WiFiInfo tWi=null;
		String SSID=null;
		String HTML=null;
		try {
			//�Ȼ�ȡWIFI����
			HttpURLConnection tHuc=this.getConnection("http://"+this.ip+"/userRpm/WlanNetworkRpm.htm");
			if(this.setDialProperty(tHuc)!=0) return null;
			tHuc.connect();
			HTML=this.getHTMLContent(tHuc.getInputStream());
			String keyWord="var wlanPara=new Array(";
			int tIndex=HTML.indexOf(keyWord);
			if(tIndex>0){
				//��ȡ��������������λ��
				tIndex+=keyWord.length();
				for(int i=0;i<3;i++){
					tIndex=HTML.indexOf(",",tIndex);
				}
				//���ĸ�
				int fourthIndex=tIndex+HTML.indexOf(",",tIndex);
				SSID=HTML.substring(tIndex, fourthIndex).replace("\"", "");
				String[] tmp=SSID.split(",");
				SSID=tmp[3].trim();
				
				tWi=new WiFiInfo(SSID,"",false);
			}
		} catch (IOException e) {
			Log.logE(e);
			Log.log(HTML);
		}
		return tWi;
	}
	
	private static final String PAGE_CONFIG_WLAN_SEC="http://%IP%/userRpm/WlanSecurityRpm.htm?secType=3&pskSecOpt=2&pskCipher=3&pskSecret=%KEY%&interval=1800&Save=%B1%A3+%B4%E6";
	private static final String PAGE_CONFIG_WLAN_NETWORK="http://%IP%/userRpm/WlanNetworkRpm.htm?ssid1=%SSID%&wlMode=2&channel=0&mode=5&chanWidth=2&ap=1&brlssid=&brlbssid=&detctwds=1&keytype=1&wepindex=1&keytext=%HIDESSID%&Save=%B1%A3+%B4%E6";
	private static final String PAGE_VAR_IP="%IP%";
	private static final String PAGE_VAR_SSID="%SSID%";
	private static final String PAGE_VAR_KEY="%KEY%";
	private static final String PAGE_VAR_HIDESSID="%HIDESSID";
	private static final String PAGE_VAR_HIDESSID_DATA="&broadcast=2";
	
	public static final int SET_SUCCESS=0;
	public static final int SET_DATA_ERROR=1;
	public static final int SET_NO_DIAL_MODE=2;
	/** 
	 * ������������������Ϣ��SSID�������Լ��Ƿ����������������<br>
	 * ���� WiFiInfo
	 * */
	public void setWifiState(WiFiInfo pW){
		//�������õ�ַ��http://172.16.17.1/userRpm/WlanSecurityRpm.htm?
		//secType=3&pskSecOpt=2&pskCipher=3&pskSecret=CYXnetkeeperA617&interval=1800
		//&Save=%B1%A3+%B4%E6
		String tConfigPassword=Router.PAGE_CONFIG_WLAN_SEC.replace(PAGE_VAR_IP, this.ip).replace(PAGE_VAR_KEY, pW.getWifiPassword());
		try {
			HttpURLConnection tConfigPwdHuc=this.getConnection(tConfigPassword);
			if(this.setDialProperty(tConfigPwdHuc)!=0) return;
			else{
				tConfigPwdHuc.connect();
				boolean tRes=this.getResponseData(tConfigPwdHuc.getInputStream(), pW.getWifiName());
				DataFrame.showTips(tRes?"�޸��ȵ�ɹ�":"�޸��ȵ�ʧ��");
			}
		} catch ( IOException e) {
			Log.logE(e);
		}		
		
		try {
			JOptionPane.showMessageDialog(null,"�ѳ����޸��������·�������룬���ڰ�ȫԭ�����ڿ��ܻᱻ�Ͽ�����\n\n�����������������·������45���Ӻ�ϵͳ���Զ������޸�SSID����");
			this.wait(45000);
		} catch (InterruptedException e1) {
			Log.logE(e1);
		}
		//SSID��http://172.16.17.1/userRpm/WlanNetworkRpm.htm?
		//ssid1=ChenYX&wlMode=2&channel=0&mode=5&chanWidth=2&ap=1&brlssid=
		//&brlbssid=&detctwds=1&keytype=1&we
		//pindex=1&keytext=&broadcast=2&Save=%B1%A3+%B4%E6
		String tConfigSSID=Router.PAGE_CONFIG_WLAN_NETWORK.replace(PAGE_VAR_IP, this.ip).replace(PAGE_VAR_SSID, pW.getWifiName()).replace(PAGE_VAR_HIDESSID, pW.isWifiBroadCast()?"":PAGE_VAR_HIDESSID_DATA);
		try {
			HttpURLConnection tConfigSSIDHuc=this.getConnection(tConfigSSID);
			if(this.setDialProperty(tConfigSSIDHuc)!=0) return;
			else{
				tConfigSSIDHuc.connect();
				boolean tRes=this.getResponseData(tConfigSSIDHuc.getInputStream(), pW.getWifiName());
				DataFrame.showTips(tRes?"�ɹ��޸��ȵ���":"�޸��ȵ�����ʧ�ܣ�Ҳ�п����Ǽ����������м�����ӣ���");
				Log.log("�޸��ȵ����Ƶ����ղο����Ϊ��"+tRes);
			}
		} catch (MalformedURLException e) {
			Log.logE(e);
		} catch (IOException e) {
			Log.logE(e);
		}
	}
	
	private boolean  getResponseData(InputStream is,String Data){
		return this.getResponseData(this.getHTMLContent(is), Data);
	}
	
	private boolean getResponseData(String meta,String data){
		return meta.indexOf(data)>=0;
	}
	
	/** 
	 * Returns a HttpURLConnection Object,Requires a valid URL for generating.<br/>
	 * This operation will set properties at the same time<br/>
	 * ����һ��HttpURLConnection������Ҫһ���Ϸ���URL��ͬʱ������һЩ���ԣ�����������֤�ַ���
	 * */
	private HttpURLConnection getConnection(String URL) throws MalformedURLException, IOException{
		HttpURLConnection tHuc=(HttpURLConnection)(new URL(URL).openConnection());
		if(tHuc!=null){
			this.setProperties(tHuc);
		}
		return tHuc;
	}
	
	
	/** 
	 * ������֤���ԣ��������ʧ�ܣ��᷵�ط�0ֵ��
	 * */
	private int setDialProperty(HttpURLConnection mRouterUrlCon){
		switch(this.mAuthMethod){
		case Router.AUTH_OLD:mRouterUrlCon.setRequestProperty(
				"Authorization",this.getBase64Acc());return 0;
		case Router.AUTH_WEB:mRouterUrlCon.setRequestProperty(
				"Cookie",
				"Authorization="+ this.getBase64Acc());return 0;
		default:return Router.RES_NO_DIAL_MODE;
		}
		
	}
	
	/** 
	 * ��ȡ·������֤�ַ���
	 * */
	private String getBase64Acc(){
		return "Basic "+Base64.encode(this.username + ":"
											+ this.password);
	}
}
