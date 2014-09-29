package cqxinli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

public class MainClass {
	public static final String __g_ver_Build="201409281657";
	public static final int __g_ver_MainVer=1;
	public static final int __g_ver_SubVer=1;
	public static final int __g_ver_FixVer=0;
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
	public static final String[] RouterList={"MECURY/TP-LINK/FAST","DLINK","OPENWRT","TOMATO"};
	
	public static final int ROUTER_MERCURY_TP_FAST=1;
	public static final int ROUTER_DLINK=2;
	public static final int ROUTER_3RD_OPENWRT=3;
	public static final int ROUTER_3RD_TOMATO=4;
	
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
	
	public static void setRouterManufactor(int RM){
		if(RM>=1 && RM<=4){
			MainClass.__g_Router_Manufactor=RM;
			if(RM==MainClass.ROUTER_MERCURY_TP_FAST) 
				MainClass.setRouterPageEncode(ENCODE_GB2312);
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
	public static final String Config_Application_CurrentVersion="3";
	
	//��ȡ�������û�����
	public static void setUserData(FormPanel name,PasswordPanel pwd,FormPanel ip,FormPanel adminName,PasswordPanel adminPswd){
		File f=new File(System.getProperty("user.dir")+File.separator+__g_data_file_name);
		if(f.exists()){
			Properties pro=new Properties();
			try{
				pro.load(new FileInputStream(System.getProperty("user.dir")+File.separator+__g_data_file_name));
				
				name.setValue(pro.getProperty(MainClass.Config_Acc_Name));
				pwd.setPassword(Base64.decode(pro.getProperty(MainClass.Config_Acc_Password)));
				ip.setValue(pro.getProperty(MainClass.Config_Router_IP));
				adminName.setValue(Base64.decode(pro.getProperty(MainClass.Config_Router_Name)));
				adminPswd.setPassword(Base64.decode(pro.getProperty(MainClass.Config_Router_Password)));
				MainClass.setAuthMethod(Integer.parseInt(pro.getProperty(MainClass.Config_Router_AuthMethod)));
				Log.log("ͨ����ȡ�����ļ�ȡ�õĻ������ŷ�ʽΪ��"+pro.getProperty(MainClass.Config_Router_AuthMethod));
				
				if(pro.getProperty(MainClass.Config_Application_ConfVer)!=null){
					//������Ϣ			
					switch(pro.getProperty(MainClass.Config_Application_ConfVer)){
					case MainClass.Config_Application_CurrentVersion:{
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
						Log.log("�������ö�ȡ���ִ����޷�����������Ĭ��");
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
			pro.store(new FileOutputStream(f), "Netkeeper For Router Configuration File");
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
		DataFrame pDF=new DataFrame("Netkeeper For Router");
		String tConfirmData="��Ӧ��Ϊ�������ʹ���Լ���Ϊ�ܵ�Լ������ͬ����������������£����������ʹ�á�"
				+ "\n�ַ����޸ı��������ڱ����Դ���봴���µĳ���\n\n"
				+ "1.�㲻�ܽ��������/�ͱ������Դ����������ҵ��;�������������ڳ��۱���������κ���ʽ����\n\n"
				+ "2.�㲻�ܽ���������κ��޸�/�����汾������ҵ��;������������ͬ�������ԭʼ����\n\n"
				+ "3.����Խ�����������������ѣ������������ʹ�ñ������Ȼ��Ҫ��ѭ����Ҫ��\n\n"
				+ "4.���ڴ�ȷ�ϲ�ͬ�⣬�����Υ�������Ϲ��������ԭ������Ȩ��Ҫ����ֹͣΥ����Ϊ��\n\n"
				+ "�����ͬ������Լ�����������ǡ������������������˳���";
		if(!new File(System.getProperty("user.dir")+File.separator+__g_data_file_name).exists()){
			if(JOptionPane.showConfirmDialog(pDF, tConfirmData)==JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(pDF, "��ӭʹ��Netkeeper For Router�������У�汾��\nʹ�÷�ʽ��μ�ReadMe�ĵ�\n�������ʹ�ù����������κ����⣬�뽫�ļ����ڵ�NetkeeperLog.Log���͵� cx@itncre.com\n�ǳ���л��\n\n��˽������\n�������Դ�����ѹ�����GitHub,ʹ�ù����в��ᷢ���κ���˽��Ϣ���κ��ˣ��������⣡");
			}else{
				System.exit(0);
			}
		}
		
	}

	public static int getAuthMethod(){
		return MainClass.gAuthMethod;
	}
	
	public static void setAuthMethod(int aM){
		MainClass.gAuthMethod=aM;
	}
}
