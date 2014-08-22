package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ClickSet implements ActionListener{

	private FormPanel name;
	private PasswordPanel pwd;
	private FormPanel ip;
	private FormPanel adminName;
	private PasswordPanel adminPswd;
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
		Router rt=new Router(this.ip.getValue(),this.adminName.getValue(),this.adminPswd.getPassword(),this.name.getValue(),this.pwd.getPassword());
		switch(rt.connect()){
		case -2:DataFrame.showTips("��������·����ʱ�������⣬�޷���½��֤");break;
		case -1:DataFrame.showTips("���Զ��û������������ʱ��������");break;
		case 0:DataFrame.showTips("�ѳɹ��������Ӳ���������30�������������");break;
		case 1:DataFrame.showTips("������·������ַ���Ϸ���");break;
		case 3:DataFrame.showTips("���Բ���ʱ��������û���㹻��Ȩ��");break;
		case 4:DataFrame.showTips("IOException Occured");break;
		case 9:DataFrame.showTips("��⵽����Ĳ�������ĵ�½����������Ȩ�޲���");break;
		default:DataFrame.showTips("Unknown Error");
		}
	}
	
}
