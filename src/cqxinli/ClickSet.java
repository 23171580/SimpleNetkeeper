package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClickSet implements ActionListener{

	private FormPanel name;
	private PasswordPanel pwd;
	private FormPanel ip;
	private FormPanel adminName;
	private PasswordPanel adminPswd;
	
	private Router mRouter;
	public ClickSet(FormPanel name,PasswordPanel pwd,FormPanel ip,FormPanel adminName,PasswordPanel adminPswd){
		this.name=name;
		this.pwd=pwd;
		this.ip=ip;
		this.adminName=adminName;
		this.adminPswd=adminPswd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//���캯����ip,·�����û��������룬�����˻�������
		if(name.getValue().equals("") || pwd.getPassword().equals("") || adminName.getValue().equals("")) return;
		else{
			mRouter=new Router(this.ip.getValue(),this.adminName.getValue(),this.adminPswd.getPassword(),this.name.getValue(),this.pwd.getPassword(),MainClass.getAuthMethod());
			Log.log("���������û����ӣ�����˺�����"+this.name.getValue()+",·�����˺ţ�"+this.adminName.getValue());
			setRes("���ڳ���Ϊ���������ӣ����Ժ󡣡���");
			switch(mRouter.connect()){
			case -2:setRes("��������·����ʱ�������⣬�޷���½��֤");break;
			case -1:setRes("���Զ��û������������ʱ��������");break;
			case 0:setRes("�ѳɹ��������Ӳ���������1���Ӻ�����������");break;
			case 1:setRes("������·������ַ���Ϸ���");break;
			case 3:setRes("���Բ���ʱ��������û���㹻��Ȩ��");break;
			case 4:setRes("IOException Occured");break;
			case 5:setRes("�������ʧ�ܣ��޷���ȡ���Ӷ���");break;
			case 6:setRes("�Ҳ������ʵ���֤��ʽ������������ִ���");break;
			case 7:setRes("�������ʧ�ܣ��ṩ�������޷�����ʼ��");break;
			case 9:setRes("��⵽����Ĳ�������ĵ�½����������Ȩ�޲���");break;
			default:setRes("Unknown Error");
			}	
			MainClass.saveUserData(this.name.getValue(), this.pwd.getPassword(), this.ip.getValue(), adminName.getValue(), adminPswd.getPassword());
		}
	}
	
	private void setRes(String r){
		DataFrame.showTips(r);
		Log.log(r);
	}
	
}
