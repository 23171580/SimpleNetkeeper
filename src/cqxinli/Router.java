package cqxinli;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import javax.swing.JOptionPane;

public class Router extends RouterSet{
	private CXKUsername un;
	
	public Router(String ip, String username, String pswd, String dialer,
			String dialingPWD) {
		this(ip,username,pswd,dialer,dialingPWD,RouterSet.AUTH_NOT_AVALIABLE);
	}

	public Router(String ip, String username, String pswd, String dialer,
			String dialingPWD,int authMethod) {
			super(username,pswd,ip,dialer,dialingPWD,authMethod);
	}
	
	
	public int connect() {
		if(this.getConnectionState()==CONNECTION_SUCCESS){
			return RES_SUCCESS;
		}
		if (this.mAuthMethod != Router.AUTH_NOT_AVALIABLE && this.mIsInit) {
			if(this.un==null) this.un=new CXKUsername(this.gAccName);
			String encodeName = null;
			String encodePassword = null;
			if(MainClass.getEncrytedAcc()){
				try {
					Log.log("��ʼ��������û�������");
					// �滻���ֵ�+Ϊ�ո񣬷����û�������
					encodeName = URLEncoder.encode(un.Realusername(), "UTF-8")
							.replace("+", "%2D");
					encodePassword = URLEncoder.encode(this.gAccPassword, "UTF-8");
				} catch (Exception ex) {
					Log.log(un==null?"true":"false");
					Log.logE(ex);
					return Router.RES_UNABLE_ENCODE;
				}
			}else{
				Log.log("��⵽����ģʽΪ������ģʽ��");
				try {
					encodeName=URLEncoder.encode(this.gAccName,"UTF-8").replace("+", "%2D");
					encodePassword=URLEncoder.encode(this.gAccPassword,"UTF-8");
				} catch (UnsupportedEncodingException e) {
					Log.logE(e);
				}
				
			}
			
			// Ŀ���ַ����������·������½������������
			
			String URL = null;
			switch(MainClass.getDialType()){
			case MainClass.DIAL_AUTO:
				URL="http://"
						+ this.gRouterIP
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
						+ this.gRouterIP
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


	private String[] mPPPoEInf;
	
	public void LoadPPPoEInf(){
		try {
			HttpURLConnection pCon=this.getConnection("http://"+this.gRouterIP+"/userRpm/PPPoECfgRpm.htm");
			if(this.setDialProperty(pCon)!=0) return;
			pCon.connect();
			String HTML=this.getHTMLContent(pCon.getInputStream());
			String KeyWord2="var pppoeInf=new Array(";
			int pIndex=HTML.indexOf(KeyWord2);
			if(pIndex>=0){
				int eIndex=HTML.indexOf(");", pIndex);
				if(eIndex>pIndex){
					this.mPPPoEInf=HTML.substring(pIndex+KeyWord2.length(),eIndex).split(",\n");
					this.TrimPPPoEInf();
					Log.log("�Ѿ���⵽�������ݡ�����Ϊ"+this.mPPPoEInf.length);
				}
					
			}else{
				String KeyWord3="var pppoeInf = new Array(";
				pIndex=HTML.indexOf(KeyWord3);
				if(pIndex>=0){
					int eIndex=HTML.indexOf(");", pIndex);
					if(eIndex>pIndex){
						this.mPPPoEInf=HTML.substring(pIndex+KeyWord3.length(),eIndex).split(",\n");
						this.TrimPPPoEInf();
						Log.log("�Ѿ���⵽�������ݡ�����Ϊ"+this.mPPPoEInf.length);
					}
				}
			}
		} catch (IOException e) {
			Log.logE(e);
		}
	}
	
	public void TrimPPPoEInf(){
		for(int i=0;i<this.mPPPoEInf.length;i++){
			this.mPPPoEInf[i]=this.mPPPoEInf[i].trim();
		}
	}
	
	public String[] getPPPoEInf(){
		return this.mPPPoEInf;
	}
	
	/**
	 * ���ص�ǰ��������״̬,��ʶ����ͷ��CONNECTION_
	 * */
	public int getConnectionState(){
		this.LoadPPPoEInf();
		if(this.getPPPoEInf()==null) return CONNECTION_OPERATION_EXCEPTION;
		return Integer.parseInt(this.mPPPoEInf[26]);
	}
	
	
	protected void testLink() {
		if (this.mIsInit) {
			Log.log("���ڳ������°汾�̼��ķ�ʽ�����������");
			HttpURLConnection xHuc = null;
			try {
					xHuc = this.getConnection("http://"+this.gRouterIP);
					if (xHuc != null) {
						xHuc.setRequestProperty(
								"Cookie",
								"Authorization="+ this.getBase64Acc());
						Log.log("��ʼ���Ե�һ������");
						xHuc.connect();
						String HTML=this.getHTMLContent(xHuc.getInputStream());
						Log.log("����Ƿ����");
						if (HTML.indexOf("noframe") > 0
								|| HTML.indexOf("frame") >= 0) {
							this.changeAuthMethod(AUTH_WEB);
						} else {
							// ���Ծɰ汾
							this.changeInitState(false);
						}
					}
			} catch (MalformedURLException e) {
				this.changeInitState(false);
				Log.logE(e);
			} catch (IOException e) {
				this.changeInitState(false);
				Log.logE(e);
				this.detectOld();
			}
		} else {
			this.changeAuthMethod(Router.AUTH_NOT_AVALIABLE);
		}
	}

	

	protected void detectOld() {
		try {
			Log.log("�����Ծɰ汾�ķ�ʽ��������");
			HttpURLConnection pHuc = this.getConnection("http://"+this.gRouterIP);
			pHuc.setRequestProperty(
					"Authorization",
					this.getBase64Acc());		
			pHuc.connect();
			String html=this.getHTMLContent(pHuc.getInputStream());
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
	
	public void trackLink(){
		boolean getData=true;
		int count = 0;
		while(getData){
			if(++count==20) {
				getData=false;
				this.setState("�ѳ�����·�����������ݣ����ڳ���ʱ�޺�û���յ��������Ϣ�������Ѿ���ֹ");
				break;
			};
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				Log.logE(e);
			}
			switch(this.getConnectionState()){
			case CONNECTION_AUTHENTICATION_FAILED:this.setState("·����״̬����֤ʧ�ܣ��û������������");getData=false;break;
			case CONNECTION_CONNECTING:this.setState("·����״̬����������...(���Ը��ٴ�����"+count+"/20)");break;
			case CONNECTION_NO_RESPONSE:this.setState("·����״̬��������û����Ӧ(�볢�Լ�����·�������ߣ�������·����)");getData=false;break;
			case CONNECTION_NOT_CONNECTED:this.setState("·����״̬��δ����");getData=false;break;
			case CONNECTION_NOT_CONNECTED_WAN:this.setState("·����״̬��WAN��δ����");getData=false;break;
			case CONNECTION_OPERATION_EXCEPTION:this.setState("·����״̬�����������쳣������޷��жϣ������в鿴�����Ƿ����ӣ���");getData=false;break;
			case CONNECTION_OPERATION_NO_MODE:this.setState("·����״̬��û�п��÷�ʽ���ӵ�·����");getData=false;break;
			case CONNECTION_SUCCESS:this.setState("·����״̬��·���������ӵ�����");getData=false;break;
			case CONNECTION_UNKNOWN:this.setState("·����״̬������δ֪����");getData=false;break;
			default:break;
			}
		}
	}
	
	
	/**
	 * WLAN������Ϣ������<br>
	 * 0-����״̬���������1-���ã�<br>
	 * 1-WIFI��SSID<br>
	 * 2-�����ŵ�<br>
	 * 3-����ģʽ<br>
	 * 4-���ߵ�MAC<br>
	 * 5-��ǰ����IP<br>
	 * 6-����Ƶ�δ���<br>
	 * 7-<br>
	 * 8-<br>
	 * 9-<br>
	 * 10-WDDS״̬
	 * */
	private String[] gLinkInfoIndexWlan;
	
	/**
	 * WAN����Ϣ������
	 * 1-WAN��MAC��ַ
	 * 2-WAN��IP��ַ
	 * 3-
	 * 4-��������
	 * 5-
	 * 6-
	 * 7-����IP
	 * 11-DNS
	 * 12-������ʱ��
	 * 13-����״̬��0-δ���� 1-������ 2-�������� 3-�û���/������� 4-����Ӧ 5-δ֪ԭ�� 6-WANδ���� ��
	 * */
	private String[] gLinkInfoIndexWan;
	
	/**
	 * ���ص�ǰ״̬��Ϣ������·����״̬ҳ��
	 * */
	public void LoadLinkInfo(){
		try{
			HttpURLConnection tHuc=this.getConnection("http://"+this.gRouterIP+"/userRpm/StatusRpm.htm");
			if(this.setDialProperty(tHuc)!=0) Log.log("���� ��ҳȫ����Ϣ ���ִ����޷���������");
			else{
				tHuc.connect();
				String HTML=this.getHTMLContent(tHuc.getInputStream());
				String Keyword="var wlanPara=new Array(";
				int tIndex=HTML.indexOf(Keyword);
				if(tIndex>0){
					int tEnd=HTML.indexOf(");", tIndex);
					if(tEnd>tIndex){
						String tArray=HTML.substring(tIndex+Keyword.length(),tEnd);
						this.gLinkInfoIndexWlan=tArray.split(",\n");
						Router.trimString(gLinkInfoIndexWlan);
					}
				}
				String KeywordWAN="var wanPara=new Array(";
				tIndex=HTML.indexOf(KeywordWAN);
				if(tIndex>0){
					int tEnd=HTML.indexOf(":)",tIndex);
					if(tEnd>tIndex){
						String tArray=HTML.substring(tIndex, tEnd);
						this.gLinkInfoIndexWan=tArray.split(",\n");
						Router.trimString(gLinkInfoIndexWan);
					}
				}
			}
		}catch(IOException e){
			Log.logE(e);
		}
	}
	
	
	/**
	 * WLAN������Ϣ������<br>
	 * 0-����״̬���������1-���ã�<br>
	 * 1-WIFI��SSID<br>
	 * 2-�����ŵ�<br>
	 * 3-����ģʽ<br>
	 * 4-���ߵ�MAC<br>
	 * 5-��ǰ����IP<br>
	 * 6-����Ƶ�δ���<br>
	 * 7-<br>
	 * 8-<br>
	 * 9-<br>
	 * 10-WDDS״̬
	 * */
	public String[] getLinkInfoWlan(){
		return this.gLinkInfoIndexWlan;
	}
	
	/**
	 * WAN����Ϣ������
	 * 1-WAN��MAC��ַ
	 * 2-WAN��IP��ַ
	 * 3-
	 * 4-��������
	 * 5-
	 * 6-
	 * 7-����IP
	 * 11-DNS
	 * 12-������ʱ��
	 * 13-����״̬��0-δ���� 1-������ 2-�������� 3-�û���/������� 4-����Ӧ 5-δ֪ԭ�� 6-WANδ���� ��
	 * */
	public String[] getLinkInfoWan(){
		return this.gLinkInfoIndexWan;
	}
	
	private String[] gWlanInfoKey;
	
	
	/**
	 * ��������·������Ϣ���������߰�ȫҳ��
	 * */
	public void LoadWlanInfoKey(){
		try {
			HttpURLConnection tHuc=this.getConnection("http://"+this.gRouterIP+"/userRpm/WlanSecurityRpm.htm");
			if(this.setDialProperty(tHuc)!=0) Log.log("���� WLAN ��Ϣ���ִ����޷�������֤����");
			else{
				tHuc.connect();
				String HTML=this.getHTMLContent(tHuc.getInputStream());
				String Keyword="var wlanPara=new Array(";
				int tIndex=HTML.indexOf(Keyword);
				if(tIndex>0){
					int tEnd=HTML.indexOf(");", tIndex);
					if(tEnd>tIndex){
						String tArray=HTML.substring(tIndex+Keyword.length(),tEnd);
						this.gWlanInfoKey=tArray.split(",\n");
						Router.trimString(gWlanInfoKey);
					}
				}
			}
		} catch (IOException e) {
			Log.logE(e);
		}
	}
	
	public String[] getWlanInfoKey(){
		return this.gWlanInfoKey;
	}
	
	public String getWifiPassword(){
		if(this.gWlanInfoKey!=null && this.gWlanInfoKey.length>12){
			return this.gWlanInfoKey[9];
		}else{
			this.LoadWlanInfoKey();
			return (this.gWlanInfoKey!=null && this.gWlanInfoKey.length>12)?this.gWlanInfoKey[9]:null;
		}
	}
	
	
	/** 
	 * ��ȡ��ǰWIFI��Ϣ������WIFI�ȵ����ƣ��ȵ�����
	 * */
	public WiFiInfo getWifiState(){
		WiFiInfo tWi=null;
		this.LoadLinkInfo();
		this.LoadWlanInfoKey();
		
		String SSID=null;
		String Password=null;
		
		SSID=this.gLinkInfoIndexWlan[1];
		Password=this.getWifiPassword();
		tWi=new WiFiInfo(SSID,Password);
		
		return tWi;
	}
	
	private static final String PAGE_CONFIG_WLAN_SEC="http://%IP%/userRpm/WlanSecurityRpm.htm?secType=3&pskSecOpt=2&pskCipher=3&pskSecret=%KEY%&interval=1800&Save=%B1%A3+%B4%E6";
	private static final String PAGE_CONFIG_WLAN_NETWORK="http://%IP%/userRpm/WlanNetworkRpm.htm?ssid1=%SSID%&wlMode=2&channel=0&mode=5&chanWidth=2&ap=1&brlssid=&brlbssid=&detctwds=1&keytype=1&wepindex=1&keytext=%HIDESSID%&Save=%B1%A3+%B4%E6";
	private static final String PAGE_VAR_IP="%IP%";
	private static final String PAGE_VAR_SSID="%SSID%";
	private static final String PAGE_VAR_KEY="%KEY%";
	private static final String PAGE_VAR_HIDESSID="%HIDESSID%";
	private static final String PAGE_VAR_HIDESSID_DATA="&broadcast=2";
	
	
	public void setWifiState(WiFiInfo pW){
		//�������õ�ַ��http://172.16.17.1/userRpm/WlanSecurityRpm.htm?
		//secType=3&pskSecOpt=2&pskCipher=3&pskSecret=<password>&interval=1800
		//&Save=%B1%A3+%B4%E6
		String tConfigPassword=Router.PAGE_CONFIG_WLAN_SEC.replace(PAGE_VAR_IP, this.gRouterIP).replace(PAGE_VAR_KEY, pW.getWifiPassword());
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
		//ssid1=<ssid>&wlMode=2&channel=0&mode=5&chanWidth=2&ap=1&brlssid=
		//&brlbssid=&detctwds=1&keytype=1&we
		//pindex=1&keytext=&broadcast=2&Save=%B1%A3+%B4%E6
		String tConfigSSID=Router.PAGE_CONFIG_WLAN_NETWORK.replace(PAGE_VAR_IP, this.gRouterIP).replace(PAGE_VAR_SSID, pW.getWifiName()).replace(PAGE_VAR_HIDESSID, pW.isWifiBroadCast()?"":PAGE_VAR_HIDESSID_DATA);
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

	@Override
	protected int setDialProperty(HttpURLConnection mRouterUrlCon) {
		switch(this.mAuthMethod){
		case Router.AUTH_OLD:mRouterUrlCon.setRequestProperty(
				"Authorization",this.getBase64Acc());
				Log.log("��֤��ʽ��401");
		return 0;
		case Router.AUTH_WEB:mRouterUrlCon.setRequestProperty(
				"Cookie",
				"Authorization="+ this.getBase64Acc());
		Log.log("��֤��ʽ��402");
		return 0;
		default:return Router.RES_NO_DIAL_MODE;
		}
		
	}
	
	public void setInternalNet(){
		try {
			HttpURLConnection pCon=this.getConnection("http://"+this.gRouterIP+"/userRpm/WanDynamicIpCfgRpm.htm?wantype=0&mtu=1500&downBandwidth=0&upBandwidth=0&Save=%B1%A3+%B4%E6");
			this.setDialProperty(pCon);
			pCon.connect();
			String tHTML=this.getHTMLContent(pCon.getInputStream());
			Log.log(tHTML);
			this.setState(tHTML.indexOf("dhcp")>=0?"����·������������ģʽ�������ݡ�":"����ʧ��");
		} catch (IOException e) {
			this.setState("�������ʱ���ִ���");
			Log.logE(e);
		}
		
	}

}
