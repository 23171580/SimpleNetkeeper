package cqxinli;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
			case MainClass.DIAL_ON_NEED:
			case MainClass.DIAL_ON_TIME:
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

			switch (this.mAuthMethod) {
			case Router.AUTH_WEB: {
				Log.log("��������ģ�����¹̼�������ʽ");
				URL tar = null;
				HttpURLConnection Tarhuc = null;
				try {
					tar = new URL(URL);
					if (tar != null) {
						Tarhuc = (HttpURLConnection) tar.openConnection();
						if (Tarhuc != null) {
							// ���ù���Ա��Cookie
							Tarhuc.setRequestProperty(
									"Cookie",
									"Authorization=Basic "
											+ Base64.encode(this.username + ":"
													+ this.password));
							this.setProperties(Tarhuc);
							Log.log("���ڳ�����������");
							Tarhuc.connect();
							InputStream is = Tarhuc.getInputStream();
							return this.getResponse(is, URL);
						}
						return Router.RES_NO_CONNECTION_OBJ;
					}
				} catch (MalformedURLException ex) {
					Log.logE(ex);
					return Router.RES_IP_INVALID;
				} catch (IOException e) {
					Log.logE(e);
					return Router.RES_IO_EXCEPTION;
				}
			}
			case Router.AUTH_OLD: {
				Log.log("���ڳ��Ծɰ汾��½����");
				URL mRouterUrl = null;
				HttpURLConnection mRouterUrlCon = null;
				try {
					mRouterUrl = new URL(URL);
					mRouterUrlCon = (HttpURLConnection) mRouterUrl
							.openConnection();
					if (mRouterUrlCon != null) {
						mRouterUrlCon.setRequestProperty(
								"Authorization",
								"Basic "
										+ Base64.encode(this.username + ":"
												+ this.password));
						this.setProperties(mRouterUrlCon);
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
			default:
				return Router.RES_AUTHENTICATION_NO_METHED;
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
		Tarhuc.setConnectTimeout(5000);
		Log.log("��������Ѿ��������");
	}

	private int getResponse(InputStream is, String URL) {
		int data = 0;
		StringBuffer sb = new StringBuffer();
		try {
			while ((data = is.read()) != -1) {
				sb.append((char) data);
			}
		} catch (IOException e) {
			Log.logE(e);
		}
		String ResponseHTML = sb.toString();
		Log.log("���ڴ���������");
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
			URL xUrl = null;
			HttpURLConnection xHuc = null;
			try {
				xUrl = new URL(urlStr);
				if (xUrl != null) {
					xHuc = (HttpURLConnection) xUrl.openConnection();
					if (xHuc != null) {
						if (!"".equals(authorizationStr)) {
							// ����·������COOKIE��֤
							xHuc.setRequestProperty(
									"Cookie",
									"Authorization=Basic "
											+ Base64.encode(authorizationStr));
							xHuc.setRequestProperty("Content-Length", "0");
							xHuc.setRequestProperty("Content-Type",
									"application/x-www-form-urlencoded");

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
			URL pUrl = new URL(URL);
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
}
