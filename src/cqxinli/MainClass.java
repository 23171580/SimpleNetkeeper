package cqxinli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

public class MainClass {
	public static final String __g_ver_Build="201409201131";
	public static final int __g_ver_MainVer=1;
	public static final int __g_ver_SubVer=0;
	public static final int __g_ver_FixVer=22;
	public static final String __g_data_file_name="NetkeeperForRouter.ini";
	
	public static final int VER_REL=0;
	public static final int VER_DEBUG=1;
	public static final int VER_BETA=2;
	public static final int VER_SPEC=3;
	//�汾��ʶ  0-Release 1-Debug 2-Beta 3-Special
	private static int __g_ver_VerSign=VER_DEBUG;
	
	
	//DEBUG��ǩ
	private static boolean allowDebug=false;
	
	public static boolean isDebugAllow(){
		return MainClass.allowDebug;
	}
	
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
	public static final String[] DialList={"��������","�Զ�����","��ʱ����","�ֶ�����"};
	public static final int DIAL_ON_NEED=1;
	public static final int DIAL_AUTO=2;
	public static final int DIAL_ON_TIME=3;
	public static final int DIAL_BY_USER=4;
	
	private static int __g_DialType=DIAL_BY_USER;
	public static int getDialType(){
		return MainClass.__g_DialType;
	}
	
	public static void setDialType(int DialType){
		if(DialType>=1 && DialType<=4){
			MainClass.__g_DialType=DialType;
			Log.log("�û����ֶ��޸Ĳ��ŷ�ʽΪ��"+DialList[DialType-1]);
		} 
	}
	
	//���ڶ�ʱ����
	
	//�����ֶ�����
	
	//·��������
	public static final String[] RouterList={"MECURY/TP-LINK/FAST","DLINK","OPENWRT","TOMATO"};
	
	public static final int ROUTER_MERCURY_TP_FAST=1;
	public static final int ROUTER_DLINK=2;
	public static final int ROUTER_3RD_OPENWRT=3;
	public static final int ROUTER_3RD_TOMATO=4;
	
	private static int __g_Router_Manufactor=MainClass.ROUTER_MERCURY_TP_FAST;
	public static int getRouterManufactor(){
		return MainClass.__g_Router_Manufactor;
	}
	
	public static void setRouterManufactor(int RM){
		if(RM>=1 && RM<=4){
			MainClass.__g_Router_Manufactor=RM;
			Log.log("������·����Ϊ��"+MainClass.RouterList[RM-1]);
		}
	}
	
	//�汾��֤��ʽ
	private static int gAuthMethod=Router.AUTH_NOT_AVALIABLE;
	
	
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
	public static final String Config_Application_CurrentVersion="2";
	
	//��ȡ�������û�����
	public static void setUserData(FormPanel name,PasswordPanel pwd,FormPanel ip,FormPanel adminName,PasswordPanel adminPswd){
		File f=new File(System.getProperty("user.dir")+File.separator+__g_data_file_name);
		if(f.exists()){
			Properties pro=new Properties();
			try{
				pro.load(new FileInputStream(System.getProperty("user.dir")+File.separator+__g_data_file_name));
				if(pro.getProperty(MainClass.Config_Application_ConfVer)!=null){
					if(pro.getProperty(MainClass.Config_Application_ConfVer).equals(MainClass.Config_Application_CurrentVersion)){
						//������Ϣ
						name.setValue(pro.getProperty(MainClass.Config_Acc_Name));
						pwd.setPassword(Base64.decode(pro.getProperty(MainClass.Config_Acc_Password)));
						ip.setValue(pro.getProperty(MainClass.Config_Router_IP));
						adminName.setValue(Base64.decode(pro.getProperty(MainClass.Config_Router_Name)));
						adminPswd.setPassword(Base64.decode(pro.getProperty(MainClass.Config_Router_Password)));
						MainClass.setAuthMethod(Integer.parseInt(pro.getProperty(MainClass.Config_Router_AuthMethod)));
						Log.log("ͨ����ȡ�����ļ�ȡ�õĻ������ŷ�ʽΪ��"+pro.getProperty(MainClass.Config_Router_AuthMethod));
						//�߼�ѡ��
						
						MainClass.setDialType(Integer.parseInt(pro.getProperty(MainClass.Config_Router_DialMode)));
						MainClass.setEncryptedAcc(pro.getProperty(MainClass.Config_Router_Encrypt).equals("true"));
						MainClass.setRouterManufactor(Integer.parseInt(pro.getProperty(MainClass.Config_Router_Manufactor)));
						
					}else{
						Log.log("�����ļ��汾���������汾��ƥ�䣬�����ж�ȡ����");
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
	public static void saveUserData(String name,String pwd,String ip,String adminName,String adminPswd){
		Properties pro=new Properties();
		try{
			File f=new File(System.getProperty("user.dir")+File.separator+__g_data_file_name);
			if(!f.exists()) f.createNewFile();
			pro.load(new FileInputStream(f));
			pro.setProperty(MainClass.Config_Acc_Name, name);
			pro.setProperty(MainClass.Config_Acc_Password, Base64.encode(pwd));
			pro.setProperty(MainClass.Config_Router_IP, ip);
			pro.setProperty(MainClass.Config_Router_Name,Base64.encode(adminName));
			pro.setProperty(MainClass.Config_Router_Password, Base64.encode(adminPswd));
			pro.setProperty(MainClass.Config_Router_AuthMethod, ""+MainClass.getAuthMethod());
			pro.setProperty(MainClass.Config_Application_ConfVer, MainClass.Config_Application_CurrentVersion);
			pro.setProperty(MainClass.Config_Router_DialMode, MainClass.getDialType()+"");
			pro.setProperty(MainClass.Config_Router_Encrypt, MainClass.getEncrytedAcc()?"true":"false");
			pro.setProperty(MainClass.Config_Router_Manufactor, MainClass.getRouterManufactor()+"");
			pro.store(new FileOutputStream(f), "Netkeeper For Router Configuration File version 1.1");
			Log.log("�ѳɹ����������ļ�");
		}catch(FileNotFoundException e){
			Log.log(e.getMessage());
		}catch(Exception e){
			Log.log(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Log.log(Log.nLine+"==========================�Ѿ�����===============================");
		for(int i=0;i<args.length;i++){
			if(args[i].equals("debug") || args[i].equals("/debug")){
				MainClass.allowDebug=true;
				Log.log("��⵽������ָ�������"+args[i]+"���������ģʽ����");
			}
		}		
		Log.log("Ӧ�ó���汾Ϊ"+MainClass.getVersion());
		new DataFrame("Netkeeper For Router");		
	}

	public static int getAuthMethod(){
		return MainClass.gAuthMethod;
	}
	
	public static void setAuthMethod(int aM){
		MainClass.gAuthMethod=aM;
	}
}
