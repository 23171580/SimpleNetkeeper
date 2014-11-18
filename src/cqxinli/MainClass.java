package cqxinli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class MainClass {
	//========================================================================
	public static final String __g_ver_Build="0033";
	public static final String BUILD_DATE="2014-11-18 12:51";
	public static final int __g_ver_MainVer=1;
	public static final int __g_ver_SubVer=1;
	public static final int __g_ver_FixVer=3;
	public static final String __g_data_file_name="NetkeeperForRouter.ini";
	
	public static final int VER_REL=0;
	public static final int VER_DEBUG=1;
	public static final int VER_BETA=2;
	public static final int VER_SPEC=3;
	//�汾��ʶ  0-Release 1-Debug 2-Beta 3-Special
	private static int __g_ver_VerSign=VER_REL;
	//========================================================================
	//���ڱ�ʶ��
	public static final short WINDOW_ROUTER=2;
	public static final short WINDOW_MENU=1;
	public static final short WINDOW_DIAL=3;
	
	private static short gWindow=WINDOW_MENU;
	
	public static void setDefaultWindow(short Window){
		gWindow=(Window>=1&& Window<=3)?Window:1;
		if(MainClass.gRemWindowState) saveUserData();
	}
	
	public static short getDefaultWindow(){
		return gWindow;
	}
	
	//�Զ����봰��
	private static boolean gRemWindowState=false;
	
	public static void setRemWindowState(boolean i){
		gRemWindowState=i;
	}
	
	public static boolean getRemWindowState(){
		return gRemWindowState;
	}
	
	//========================================================================
	//DEBUG��ǩ
	private static boolean allowDebug=false;
	
	public static boolean isDebugAllow(){
		return MainClass.allowDebug;
	}
	//========================================================================
	//·����������������
	//�㷨�汾
	public static final String []AlgVer={"0055","0087"};
	public static final int ALG_87=87;
	public static final int ALG_55=55;
	
	private static int gAlgVer=ALG_55;
	public static int getAlgVer(){
		return gAlgVer;
	}
	
	//�Ƿ���Ҫ�����˺ţ��Է�����ͨ����
	private static boolean __g_isEncrypted=true;
	public static boolean getEncrytedAcc(){
		return MainClass.__g_isEncrypted;
	}
	public static void setEncryptedAcc(boolean i){
		MainClass.__g_isEncrypted=i;
		Log.log("�����Ƿ�����˺ţ�"+(i?"��":"��"));
	}
	
	//���ŷ�ʽ 
	public static final String[] DialList={"�Զ�����","�ֶ�����"};
	public static final int DIAL_AUTO=0;
	public static final int DIAL_BY_USER=1;
	
	private static int __g_DialType=DIAL_BY_USER;
	public static int getDialType(){
		return MainClass.__g_DialType;
	}
	
	public static void setDialType(String DialType){
		for(int i=0;i<MainClass.DialList.length;i++){
			if(DialType.indexOf(MainClass.DialList[i])>=0)
				MainClass.__g_DialType=i;
		}
		Log.log("�������û���������Ϊ��"+DialList[getDialType()]);
	}
	
	//�����ֶ�����
	
	//·��������
	public static final String[] RouterList={"MECURY/TP-LINK/FAST","Tenda"};
	
	public static final int ROUTER_MERCURY_TP_FAST=1;
	public static final int ROUTER_Tenda=2;
	public static void setRouterManufactor(int RM){
		if(RM>=1 && RM<=4){
			MainClass.__g_Router_Manufactor=RM;
			if(RM==MainClass.ROUTER_MERCURY_TP_FAST) 
				MainClass.setRouterPageEncode(ENCODE_GB2312);
			Log.log("������·����Ϊ��"+MainClass.RouterList[RM-1]);
		}
	}
	
	public static final String ENCODE_GB2312="GB2312";
	public static final String ENCODE_UTF_8="UTF-8";
	
	private static String __g_Router_EncodePage=MainClass.ENCODE_GB2312;
	private static int __g_Router_Manufactor=MainClass.ROUTER_MERCURY_TP_FAST;
	public static int getRouterManufactor(){
		return MainClass.__g_Router_Manufactor;
	}
	
	public static String getRouterPageEncode(){
		return MainClass.__g_Router_EncodePage;
	}
	
	public static void setRouterPageEncode(String encode){
		if(encode.equals(MainClass.ENCODE_GB2312)){
			MainClass.__g_Router_EncodePage=MainClass.ENCODE_GB2312;
		}else{
			MainClass.__g_Router_EncodePage=MainClass.ENCODE_UTF_8;
		}
		Log.log("������ҳ����뷽ʽΪ��"+MainClass.getRouterPageEncode());
	}
	
	
	
	//�汾��֤��ʽ
	private static int gAuthMethod=Router.AUTH_NOT_AVALIABLE;
	public static int getAuthMethod(){
		return MainClass.gAuthMethod;
	}
	
	public static void setAuthMethod(int aM){
		MainClass.gAuthMethod=aM;
	}
	
	//========================================================================
	//��ȡAPP�汾
	public static String getVersion(){
		return __g_ver_MainVer+"."+__g_ver_SubVer+"."+__g_ver_FixVer+"(Build"+__g_ver_Build+")"+getVersionRelOrDebug();
	}
	
	public static String getBuild(){
		return __g_ver_Build;
	}
	
	public static String getVersionNoBuild(){
		return __g_ver_MainVer+"."+__g_ver_SubVer+"."+__g_ver_FixVer+getVersionRelOrDebug();
	}
	
	public static int getVersionSig(){
		return MainClass.__g_ver_VerSign;
	}
	
	public static String getVersionRelOrDebug(){
		String sig="";
		switch(MainClass.__g_ver_VerSign){
		case VER_REL:sig="��ʽ�汾";break;
		case VER_DEBUG:sig="���԰汾";break;
		case VER_BETA:sig="���԰汾";break;
		case VER_SPEC:sig="����汾";break;
		}
		return sig;
	}
	//========================================================================
	//�����ļ���Ŀ����
	public static final String Config_Application_DefaultMenu="AppDefaultMenu";
	//·�����������
	public static final String Config_Acc_Name="AccName";
	public static final String Config_Acc_Password="AccPassword";
	public static final String Config_Router_IP="RouterIP";
	public static final String Config_Router_Name="RouterAdmin";
	public static final String Config_Router_Password="RouterPassword";
	public static final String Config_Router_AuthMethod="AuthMethod";
	public static final String Config_Router_DialMode="DialMode";
	public static final String Config_Router_DialPlace="DialPlace";
	public static final String Config_Router_Encrypt="Encrypt";
	public static final String Config_Router_Manufactor="Manufactor";
	public static final String Config_Application_ConfVer="ConfigVersion";
	//�����������
	public static final String Config_Dial_Acc_Name="DialAccName";
	public static final String Config_Dial_Acc_Password="DialAccPassword";
	public static final String Config_Dial_is_Rem="DialAccRem";
	public static final String Config_Dial_is_HeartBeat="DialHeartBeat";
	public static final String Config_Dial_is_AutoDial="DialAuto";
	//�����ȵ�����
	public static final String Config_Wifi_SSID="WifiSSID";
	public static final String Config_Wifi_Password="WifiPassword";
	//�����ļ��汾������޸Ĵ���
	public static final String Config_Application_CurrentVersion="4";
	
	//========================================================================
	//User Interface
	private static DataFrame gDataFrame=null;
	
	public static DataFrame getDataFrame(){
		return MainClass.gDataFrame;
	}
	
	//Menu Interface
	private static MenuFrame gMenuFrame=null;
	
	public static MenuFrame getMenuFrame(){
		return MainClass.gMenuFrame;
	}
	
	//Dial Interface
	private static DialFrame gDialInterface=null;
	
	public static DialFrame getDialFrame() {
		return gDialInterface;
	}

	public static void setDialFrame(DialFrame gDialInterface) {
		MainClass.gDialInterface = gDialInterface;
	}
	//========================================================================
	
	//��ȡ�������û�����
	public static void setUserData(){
		File f=new File(System.getProperty("user.dir")+File.separator+__g_data_file_name);
		if(f.exists()){
			Properties pro=new Properties();
			try{
				pro.load(new FileInputStream(System.getProperty("user.dir")+File.separator+__g_data_file_name));
				
				MainClass.getDataFrame().setAccName(pro.getProperty(MainClass.Config_Acc_Name));
				MainClass.getDataFrame().setAccPassword(Base64.decode(pro.getProperty(MainClass.Config_Acc_Password)));
				MainClass.getDataFrame().setRouterIpAddress(pro.getProperty(MainClass.Config_Router_IP));
				MainClass.getDataFrame().setRouterAccName(Base64.decode(pro.getProperty(MainClass.Config_Router_Name)));
				MainClass.getDataFrame().setRouterAccPassword(Base64.decode(pro.getProperty(MainClass.Config_Router_Password)));
				MainClass.setAuthMethod(Integer.parseInt(pro.getProperty(MainClass.Config_Router_AuthMethod)));
				Log.log("ͨ����ȡ�����ļ�ȡ�õĻ������ŷ�ʽΪ��"+pro.getProperty(MainClass.Config_Router_AuthMethod));
				
				if(pro.getProperty(MainClass.Config_Application_ConfVer)!=null){
					Log.log("�����ļ��汾��"+pro.getProperty(MainClass.Config_Application_ConfVer));
					//������Ϣ			
					switch(pro.getProperty(MainClass.Config_Application_ConfVer)){
					case MainClass.Config_Application_CurrentVersion:{
						//���������
						String tAccName=pro.getProperty(MainClass.Config_Dial_Acc_Name);
						String tAccPassword=Base64.decode(pro.getProperty(MainClass.Config_Dial_Acc_Password));
						boolean tIsRem=pro.getProperty(MainClass.Config_Dial_is_Rem).equals("true");
						boolean tIsAutoDial=pro.getProperty(MainClass.Config_Dial_is_AutoDial).equals("true");
						boolean tIsHeartBeat=pro.getProperty(MainClass.Config_Dial_is_HeartBeat).equals("true");
						
						String tWifiSSID=pro.getProperty(MainClass.Config_Wifi_SSID);
						String tWifiPassword=Base64.decode(pro.getProperty(MainClass.Config_Wifi_Password));
						MainClass.getDialFrame().setConfigDataDial(tAccName, tAccPassword, tIsHeartBeat, tIsRem, tIsAutoDial);
						MainClass.getDialFrame().setConfigDataWifi(tWifiSSID, tWifiPassword);
						MainClass.setDefaultWindow(Short.parseShort(pro.getProperty(MainClass.Config_Application_DefaultMenu)));
						gRemWindowState=(MainClass.getDefaultWindow()!=WINDOW_MENU);
					}
					case "3":{
						MainClass.setDialType(MainClass.DialList[Integer.parseInt(pro.getProperty(MainClass.Config_Router_DialMode))]);
						MainClass.setEncryptedAcc(pro.getProperty(MainClass.Config_Router_Encrypt).equals("true"));
						MainClass.setRouterManufactor(Integer.parseInt(pro.getProperty(MainClass.Config_Router_Manufactor)));
					}break;
					case "2":{
						MainClass.setDialType(MainClass.DialList[Integer.parseInt(pro.getProperty(MainClass.Config_Router_DialMode))>=1?1:0]);
						MainClass.setEncryptedAcc(pro.getProperty(MainClass.Config_Router_Encrypt).equals("true"));
						MainClass.setRouterManufactor(Integer.parseInt(pro.getProperty(MainClass.Config_Router_Manufactor)));
					}break;
					default:{
						Log.log("�������ö�ȡ���ִ����޷�����������Ĭ�ϡ���ȡ���������ļ��汾��"+pro.getProperty(MainClass.Config_Application_ConfVer));
					}
					}
				}else{
					Log.log("�����ļ��汾���Բ����ڣ��޷�������ȡ������");
				}
				
			}catch(FileNotFoundException e){
				Log.log(e.getMessage());
			}catch(Exception e){
				Log.log(e.getMessage());
			}
		}
		
	}
	
	//�����û�����
	public static void saveUserData(){
		String name=MainClass.getDataFrame().g_getAccName();
		String pwd=MainClass.getDataFrame().g_getAccPassword();
		String ip=MainClass.getDataFrame().g_getRouterIP();
		String adminName=MainClass.getDataFrame().g_getRouterAdmin();
		String adminPswd=MainClass.getDataFrame().g_getRouterPassword();
		
		String DialAccName=MainClass.getDialFrame().getAccName();
		String DialAccPassword=MainClass.getDialFrame().getAccPassword();
		String DialIsRem=MainClass.getDialFrame().isRememberAcc()?"true":"false";
		String DialIsHeartBeat=MainClass.getDialFrame().isHeartBeat()?"true":"false";
		String DialIsAuto=MainClass.getDialFrame().isAutoDial()?"true":"false";
		
		String WifiSSID=MainClass.getDialFrame().getSSID();
		String WifiKey=MainClass.getDialFrame().getWifiKey();
		Properties pro=new Properties();
		try{
			File f=new File(System.getProperty("user.dir")+File.separator+__g_data_file_name);
			if(!f.exists()) f.createNewFile();
			pro.load(new FileInputStream(f));
			//App
			pro.setProperty(MainClass.Config_Application_ConfVer, MainClass.Config_Application_CurrentVersion);
			pro.setProperty(MainClass.Config_Application_DefaultMenu, MainClass.gWindow+"");
			
			//V3����ǰ�汾������
			pro.setProperty(MainClass.Config_Acc_Name, name);
			pro.setProperty(MainClass.Config_Acc_Password, Base64.encode(pwd));
			pro.setProperty(MainClass.Config_Router_IP, ip);
			pro.setProperty(MainClass.Config_Router_Name,Base64.encode(adminName));
			pro.setProperty(MainClass.Config_Router_Password, Base64.encode(adminPswd));
			pro.setProperty(MainClass.Config_Router_AuthMethod, ""+MainClass.getAuthMethod());
			pro.setProperty(MainClass.Config_Router_DialMode, MainClass.getDialType()+"");
			pro.setProperty(MainClass.Config_Router_Encrypt, MainClass.getEncrytedAcc()?"true":"false");
			pro.setProperty(MainClass.Config_Router_Manufactor, MainClass.getRouterManufactor()+"");
			
			//v4���ź�������Ϣ������
			pro.setProperty(MainClass.Config_Dial_Acc_Name, DialAccName);
			pro.setProperty(MainClass.Config_Dial_Acc_Password,Base64.encode(DialAccPassword));
			pro.setProperty(MainClass.Config_Dial_is_AutoDial, DialIsAuto);
			pro.setProperty(MainClass.Config_Dial_is_HeartBeat, DialIsHeartBeat);
			pro.setProperty(MainClass.Config_Dial_is_Rem, DialIsRem);
			//Wifi
			pro.setProperty(MainClass.Config_Wifi_SSID, WifiSSID);
			pro.setProperty(MainClass.Config_Wifi_Password, Base64.encode(WifiKey));
			//�洢
			pro.store(new FileOutputStream(f), "Netkeeper For Router Configuration File");
			Log.log("�ѳɹ����������ļ�");
		}catch(FileNotFoundException e){
			Log.log(e.getMessage());
		}catch(Exception e){
			Log.log(e.getMessage());
		}
	}
	//========================================================================
	//���ؿ��ļ�
	private static boolean loadedLib=false;
	public static boolean getLibLoaded(){
		return loadedLib;
	}
	
	public static void loadSystemLib(){
		/*
		try{
			System.loadLibrary("SimpNKRasDialLibx86");
			loadedLib=true;
			Log.log("���ز����ļ���:SimpNKRasDialLib (x86)");
		}catch(UnsatisfiedLinkError e){
			Log.log(e.toString());
			try{
				System.loadLibrary("SimpNKRasDialLibx64");
				loadedLib=true;
				Log.log("���ز����ļ���:SimpNKRasDialLib (x64)");				
			}catch(UnsatisfiedLinkError ex){
				loadedLib=false;
				Log.log("���ز����ļ�����ִ����ļ������ڡ�");
				Log.log(ex.toString());
			}
		}
		*/
		String pLibName="amd64".equals(System.getProperty("os.arch"))?"SimpNKRasDialLibx64":"SimpNKRasDialLibx86";
		try{
			System.loadLibrary(pLibName);
			loadedLib=true;
		}catch(UnsatisfiedLinkError e){
			Log.log(e.toString());
			loadedLib=false;
		}finally{
			Log.log("���ؿ��ļ���"+pLibName+(getLibLoaded()?"�ɹ�":"ʧ��"));
		}
	}
	//========================================================================
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Log.log(Log.nLine+"==========================�Ѿ�����===============================");
		Log.logSysInfo();
		loadSystemLib();
		
		Log.log("Ӧ�ó���汾Ϊ"+MainClass.getVersion());
		MainClass.gDataFrame=new DataFrame("Simple Netkeeper For Router");		
		MainClass.gMenuFrame=new MenuFrame("Simple Netkeeper");
		MainClass.gDialInterface=new DialFrame("Simple Netkeeper For Dialer");
		MainClass.setUserData();
		
		processArgs(args);
		
		if(gRemWindowState){
			Log.log("�ѷ���Ĭ�ϴ�������");
			switch(MainClass.getDefaultWindow()){
			case MainClass.WINDOW_DIAL:{
				if(getLibLoaded()){
					getDialFrame().setVisible(true);
				}else{
					gMenuFrame.setVisible(true);
					JOptionPane.showMessageDialog(gMenuFrame, "�ܱ�Ǹ����ʼ�����Ŵ��ڳ��ִ���������鿴��־");
				}
			}break;
			case WINDOW_MENU:gMenuFrame.setVisible(true);break;
			case WINDOW_ROUTER:gDataFrame.setVisible(true);break;
			default:
			}
		}else{
			gMenuFrame.setVisible(true);
		}
			
		//MainClass.getDataFrame().setVisible(true);
		
		String tConfirmData="��Ӧ��Ϊ�������ʹ���Լ���Ϊ�ܵ�Լ������ͬ����������������£����������ʹ�á�"
				+ "\n�ַ����޸ı��������ڱ����Դ���봴���µĳ���\n\n"
				+ "1.�㲻�ܽ��������/�ͱ������Դ����������ҵ��;�������������ڳ��۱���������κ���ʽ����\n\n"
				+ "2.�㲻�ܽ���������κ��޸�/�����汾������ҵ��;������������ͬ�������ԭʼ����\n\n"
				+ "3.����Խ�����������������ѣ������������ʹ�ñ������Ȼ��Ҫ��ѭ����Ҫ��\n\n"
				+ "4.���ڴ�ȷ�ϲ�ͬ�⣬�����Υ�������Ϲ��������ԭ������Ȩ��Ҫ����ֹͣΥ����Ϊ��\n\n"
				+ "�����ͬ������Լ�����������ǡ������������������˳���";
		if(!new File(System.getProperty("user.dir")+File.separator+__g_data_file_name).exists()){
			if(JOptionPane.showConfirmDialog(MainClass.getDataFrame(), tConfirmData)==JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(MainClass.getDataFrame(), "��ӭʹ��Simple Netkeeper�������У�汾��\nʹ�÷�ʽ��μ�ReadMe�ĵ�\n�������ʹ�ù����������κ����⣬�뽫�ļ����ڵ�NetkeeperLog.Log���͵� cx@itncre.com\n�ǳ���л��\n\n��˽������\n�������Դ�����ѹ�����GitHub,ʹ�ù����в��ᷢ���κ���˽��Ϣ���κ��ˣ��������⣡");
			}else{
				System.exit(0);
			}
		}
		
	}
	//========================================================================
	//�����д���
	private static void processArgs(String[] args){
		boolean pGetState=false;
		boolean isAutoDial=false;
		boolean isTestAcc=false;
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-debug") || args[i].equals("/debug")){
				MainClass.allowDebug=true;
				Log.log("��⵽������ָ�������"+args[i]+"���������ģʽ����");
			}else if(args[i].equals("-state")|| args[i].equals("/state")){
				pGetState=true;
			}else if(args[i].equals("-dial") || args[i].equals("/dial")){
				isAutoDial=true;
			}else if(args[i].equals("-testacc") || args[i].equals("/testacc")){
				isTestAcc=true;
			}
		}	
		if(pGetState){			
			Router pRouter=new Router(gDataFrame.g_getRouterIP(),gDataFrame.g_getRouterAdmin(),gDataFrame.g_getRouterPassword(),gDataFrame.g_getAccName(),gDataFrame.g_getAccPassword(),MainClass.getAuthMethod());
			pRouter.LoadPPPoEInf();
			String[] inf=pRouter.getPPPoEInf();
			if(inf!=null){
				for(String x:inf){
					System.out.println(x);
				}
				System.out.println("����Ϊ"+inf.length);
				System.out.println("The PPPoE Inf in index 26th is "+inf[26]);
				pRouter.trackLink();
			}
			else{
				DataFrame.showTips("���ٵ�ǰ����������ִ���");
			}
		}
		if(isAutoDial){
			if(isTestAcc){
				new ClickDial(null).Dial("chongzhi@cqdx", "111111", false);	
			}else{
				new ClickDial(null).Dial(MainClass.getDialFrame().getAccName(), MainClass.getDialFrame().getAccPassword(), true);				
			}
		}
	}
	
	//=========================================================================
}
