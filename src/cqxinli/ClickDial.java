package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ClickDial implements ActionListener{

	JButton gBut;
	private int dialRes;
	
	public synchronized void setRes(int R){this.dialRes=R;this.dialRes();}
	public int getRes(){return this.dialRes;}
	
	public ClickDial(JButton p){
		this.gBut=p;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.gBut.setEnabled(false);
		String name=MainClass.getDialFrame().getAccName();
		final String pwd=MainClass.getDialFrame().getAccPassword();
		if(pwd.length()<6 || name.equals("")){
			this.setInfo("�û��������벻����Ҫ��");
		}
		else{
			
			//�����û���
			CXKUsername un=new CXKUsername(name);
			final String Realname=un.Realusername();
			//��ʼ����
			MainClass.getDialFrame().setConnectionState("��ʼ���Ų���");
			//���Ų���
			new Thread(new Runnable(){

				@Override
				public void run() {
					setRes((int)dialRasWindows(Realname, pwd));	
					gBut.setEnabled(true);
					
				}
				
			}).start();
		}
		
	}
	
	private synchronized void setInfo(CharSequence c){
		MainClass.getDialFrame().setConnectionState(c);
	}
	
	private synchronized void dialRes(){
		switch(this.getRes()){
		case 623:
		case 624:
		case 625:this.setInfo("���ӷ����ڲ�����");break;
		case 629:this.setInfo("���ӱ�Զ�̼�����ر�");break;
		case 678:this.setInfo("Զ�̼����û����Ӧ");break;
		case 691:this.setInfo("�û�ƾ���޷���������");break;
		case 720:this.setInfo("���ܽ������ӡ��������Ҫ��������");break;
		default:this.setInfo("�����õķ���״̬");
		}
		Log.log(this.getRes()+":"+this.dialRasWindowsErrorStr(getRes()));
	}
	
	private native long dialRasWindows(String username,String password);

	private native String dialRasWindowsErrorStr(long error);
}
