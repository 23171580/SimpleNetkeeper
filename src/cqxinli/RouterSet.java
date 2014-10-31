package cqxinli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @version 1.1.2
 * @author CrazyChen@CQUT
 * 
 * ����·�������õ���Ҫ�����ࡣ<br>
 * ���ƹ��̣��½���-����ʼ�����ݣ�·�������û���Ϣ��-������·��������-�����������Ƿ��ѳ�ʼ��<br>
 * ����ɵĹ����У���ȡ��ǰ·����SSID����ȡ��ǰ·��������״̬������·�������ţ�����·��������������Ϣ<br>
 * ��������״��<br>
 */
public abstract class RouterSet {
	
	protected String gRouterIP;
	protected String gRouterAccName;
	protected String gRouterAccPassword;
	protected String gAccName;
	protected String gAccPassword;
	
	protected int mAuthMethod;
	protected boolean mIsInit;
	
	public static final int AUTH_OLD = 401;
	public static final int AUTH_WEB = 402;
	public static final int AUTH_PASSWORD_ONLY=403;
	public static final int AUTH_NOT_AVALIABLE = 0;
	
	protected RouterSet(String RouterID,String RouterKey,String IP,String AccName,String AccPassword){
		this(RouterID, RouterKey, IP, AccName, AccPassword, RouterSet.AUTH_NOT_AVALIABLE);
	}
	
	protected RouterSet(String RouterID,String RouterKey,String IP,String AccName,String AccPassword,int AuthMethod){
		this.gRouterAccName=RouterID;
		this.gRouterAccPassword=RouterKey;
		this.gRouterIP=IP;
		this.gAccName=AccName;
		this.gAccPassword=AccPassword;
		this.mIsInit=true;
		if((this.mAuthMethod=AuthMethod)==RouterSet.AUTH_NOT_AVALIABLE){
			testLink();
		}	
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
	 */
	protected abstract void testLink();
	
	/**
	 * ���ɰ汾�Ĺ̼�������<br>
	 * �ɰ汾ʹ��HTTP 401 Authorization Basic��֤��ʽ��Ҳ���Ǽ����ĵ��������û���������
	 * */
	protected abstract void detectOld();
	
	/**
	 * �޸ĳ�ʼ����״̬��
	 * */
	protected void changeInitState(boolean ii) {
		this.mIsInit = ii;
		Log.log("�Ѿ�����·�������ӳ�ʼ��״̬Ϊ��" + (ii ? "�ѳ�ʼ��" : "δ��ʼ��"));
		if (!ii)
			changeAuthMethod(Router.AUTH_NOT_AVALIABLE);
	}

	/**
	 * �޸���֤��ʽ
	 * */
	protected void changeAuthMethod(int Au) {
		this.mAuthMethod = Au;
		MainClass.setAuthMethod(Au);
		Log.log("�Ѹ���·������֤��ʽΪ��" + Au);
		if(Au!=Router.AUTH_NOT_AVALIABLE) 
			this.changeInitState(true);
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
	 * 		   The beginning of the returns variable is "RES_"	<br>
	 *         - -2 if unable to access the device with the account and password
	 *         user given.<br>
	 *         - -1 if unable to encode the user name and password to URL
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
	 *         - 10 if error Unknown<br>
	 */
	public abstract int connect();
	
	public static final int CONNECTION_NOT_CONNECTED=0;
	public static final int CONNECTION_SUCCESS=1;
	public static final int CONNECTION_NO_RESPONSE=4;
	public static final int CONNECTION_AUTHENTICATION_FAILED=3;
	public static final int CONNECTION_UNKNOWN=5;
	public static final int CONNECTION_NOT_CONNECTED_WAN=6;
	public static final int CONNECTION_OPERATION_NO_MODE=10;
	public static final int CONNECTION_OPERATION_EXCEPTION=11;
	public static final int CONNECTION_CONNECTING=2;
	/**
	 * ���ص�ǰ��������״̬,��ʶ����ͷ��CONNECTION_
	 * */
	public abstract int getConnectionState();
	
	
	public static final String CONNECTION_VAR_LINKSTAT="\"0.0.0.0\",";
	
	public static final String[] PPPoELinkStat={
		"δ����",
		"������",
		"��������",
		"�û�����������֤ʧ��",
		"����������Ӧ",
		"δ֪ԭ��ʧ��",
		"WAN��δ����"
	};
	
	
	/** 
	 * ����PPPoE����״̬<br>
	 * �ڳ���20��û�л�ȡ�����ӳɹ�����ʧ���Ժ󣬻���ʾ��ʱ<br>
	 * �����ж��߼������⣬����жϷ�ʽ���ܴ���һ�������ʣ�<br>
	 * */
	public abstract void trackLink();
	
	/** 
	 * ��ȡ��Զ�̼�������ص�HTML�ı����ݡ���Ҫһ��������
	 * */
	public String getHTMLContent(InputStream is){
		int data = 0;
		String ResponseHTML = null;
		try {
			BufferedReader pBufRd=new BufferedReader(new InputStreamReader(is,MainClass.getRouterPageEncode()));
			StringBuffer sb = new StringBuffer();
			while ((data = pBufRd.read()) != -1) {
				sb.append((char) data);
			}
			ResponseHTML = sb.toString();
		} catch (IOException e) {
			Log.logE(e);
		}
		return ResponseHTML;
	}
	
	
	/**
	 * ����HttpURLConnection�������������
	 * �������ã�Referer������ʱ��Ĭ��2000��
	 * */
	protected void setProperties(HttpURLConnection Tarhuc) {
		this.setProperties(Tarhuc, 200);
	}
	
	protected void setProperties(HttpURLConnection Tarhuc,int timeOut){
		// ��������ҳ����Ȩ�޴���
				Tarhuc.setRequestProperty("Referer", "http://" + this.gRouterIP + "/");
				Tarhuc.setRequestProperty("Host", this.gRouterIP);
				Tarhuc.setRequestProperty("Connection", "Keep-alive");
				Tarhuc.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				Tarhuc.setConnectTimeout(timeOut);
				Log.log("��������Ѿ��������");
	}
	
	/** 
	 * ����һ������״̬����Connect����֮��ʹ��
	 * */
	protected int getResponse(InputStream is, String URL) {
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
	
	
	public static final int SET_SUCCESS=0;
	public static final int SET_DATA_ERROR=1;
	public static final int SET_NO_DIAL_MODE=2;
	/** 
	 * ������������������Ϣ��SSID�������Լ��Ƿ����������������<br>
	 * ���� WiFiInfo.
	 * */
	public abstract void setWifiState(WiFiInfo pW);
	
	public abstract WiFiInfo getWifiState();
	
	
	/**
	 * ������ʾ����ʾ����
	 * */
	protected void setState(String r){
		DataFrame.showTips(r);
		Log.log(r);
	}
	
	/**
	 * ��ȡ��Ӧ����
	 * */
	protected boolean  getResponseData(InputStream is,String Data){
		return this.getResponseData(this.getHTMLContent(is), Data);
	}
	
	protected boolean getResponseData(String meta,String data){
		return meta.indexOf(data)>=0;
	}
	
	/** 
	 * Returns a HttpURLConnection Object,Requires a valid URL for generating.<br/>
	 * This operation will set properties at the same time<br/>
	 * ����һ��HttpURLConnection������Ҫһ���Ϸ���URL��ͬʱ������һЩ���ԣ�����������֤�ַ���
	 * */
	protected HttpURLConnection getConnection(String URL) throws MalformedURLException, IOException{
		HttpURLConnection tHuc=(HttpURLConnection)(new URL(URL).openConnection());
		if(tHuc!=null){
			this.setProperties(tHuc);
		}
		return tHuc;
	}
	

	/** 
	 * ������֤���ԣ��������ʧ�ܣ��᷵�ط�0ֵ��
	 * */
	protected abstract int setDialProperty(HttpURLConnection mRouterUrlCon);
	
	/** 
	 * ��ȡ·������֤�ַ�����������Ҫ���ش˷�����
	 * */
	protected String getBase64Acc(){
		return "Basic "+Base64.encode(this.gRouterAccName + ":"
											+ this.gRouterAccPassword);
	}
	
	/**
	 * ���ط�����URL,�� http://ip/
	 * */
	protected String getSvrURL(){
		return "http://"+this.gRouterIP+"/";
	}
	
	/**
	 * ����ַ��������еĶ���ո� trim
	 * */
	public static void trimString(String[] pStr){
		for(int i=0;i<pStr.length;i++){
			pStr[i]=pStr[i].trim();
		}
	}
	
	protected boolean writePostParam(HttpURLConnection thuc,String param){
		try {
			thuc.setRequestMethod("POST");
			thuc.setDoOutput(true);// �Ƿ��������
			thuc.getOutputStream().write(param.getBytes());
		} catch (IOException e) {
			Log.logE(e);
			return false;
		}		
		return true;
	}
}
