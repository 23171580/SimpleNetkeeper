package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class ClickSet implements ActionListener{

	private DataFrame mDF;
	private JButton mButton;
	
	private RouterSet mRouter;
	public ClickSet(JButton but){
		this.mButton=but;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.mDF=MainClass.getDataFrame();
		if(this.mDF==null){
			this.setRes("���������ִ���");
			return;
		}
		//���캯����ip,·�����û��������룬�����˻�������
		if(mDF.g_getAccName().equals("") || mDF.g_getAccPassword().equals("") || mDF.g_getRouterAdmin().equals("")) return;
		else{
			MainClass.saveUserData();
			
			setRes("���ڳ���Ϊ���������ӣ����Ժ󡣡���");
			new Thread(new Runnable(){
				@Override
				public void run() {
					mButton.setEnabled(false);
					mRouter=new Router(mDF.g_getRouterIP(),mDF.g_getRouterAdmin(),mDF.g_getRouterPassword(),mDF.g_getAccName(),mDF.g_getAccPassword(),MainClass.getAuthMethod());
					Log.log("���������û����ӣ�����˺�����"+mDF.g_getAccName()+",·�����˺ţ�"+mDF.g_getRouterAdmin());
					
					switch(mRouter.connect()){
					case -2:setRes("��������·����ʱ�������⣬�޷���½��֤");break;
					case -1:setRes("���Զ��û������������ʱ��������");break;
					case 0:setRes("�ѳɹ��������Ӳ���������1���Ӻ�����������");mRouter.trackLink();break;
					case 1:setRes("������·������ַ���Ϸ���");break;
					case 2:setRes("û��ָ������ģʽ����������Ϊ��·������֧��");break;
					case 3:setRes("���Բ���ʱ��������û���㹻��Ȩ��");break;
					case 4:setRes("IOException Occured");break;
					case 5:setRes("�������ʧ�ܣ��޷���ȡ���Ӷ���");break;
					case 6:setRes("�Ҳ������ʵ���֤��ʽ������������ִ���");break;
					case 7:setRes("�������ʧ�ܣ��ṩ�������޷�����ʼ��");break;
					case 8:setRes("���Լ����˺ų��ִ���");break;
					case 9:setRes("��⵽����Ĳ�������ĵ�½����������Ȩ�޲���");break;
					default:setRes("Unknown Error");
					}	
					mButton.setEnabled(true);
				}
				
			}).start();
		}
	}
	
	private void setRes(String r){
		DataFrame.showTips(r);
		Log.log(r);
	}
	
}
