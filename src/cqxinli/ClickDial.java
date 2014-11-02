package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

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
		Dial(name,pwd,true);
	}
	
	public synchronized void Dial(String name,final String pwd,boolean enc){
		if(pwd.length()<6 || name.equals("")){
			this.setInfo("�û��������벻����Ҫ��");
			if(this.gBut!=null)
				this.gBut.setEnabled(true);
		}
		else{
			if(MainClass.getDialFrame().isRememberAcc()){
				MainClass.saveUserData();
			}
			//�����û�
			final String Realname=enc?new CXKUsername(name).Realusername():name;
			//��ʼ����
			MainClass.getDialFrame().setConnectionState("��ʼ���Ų���");
			//���Ų���
			new Thread(new Runnable(){

				@Override
				public void run() {
					setRes((int)dialRasWindows(Realname, pwd));	
					if(gBut!=null)
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
		case 0:this.setInfo("���ӳɹ���");break;
		case 623:
		case 624:
		case 625:this.setInfo("���ӷ����ڲ�����");break;
		case 629:this.setInfo("���ӱ�Զ�̼�����ر�");break;
		case 651:this.setInfo("���ƽ�����������豸�޷�����");break;
		case 678:this.setInfo("Զ�̼����û����Ӧ");break;
		case 691:this.setInfo("�û�ƾ���޷���������");break;
		case 711:this.setInfo("���鲦����ط����Ƿ��Ѿ�������");break;
		case 720:this.setInfo("���ܽ������ӡ��������Ҫ��������");break;
		default:this.setInfo("�����õķ���״̬�����룺"+this.getRes());
		}
		System.out.println(this.getRes());
		try {
			Log.log(this.getRes()+":"+new String(this.dialRasWindowsErrorStr(getRes()).getBytes(),"GB2312"));
		} catch (UnsupportedEncodingException e) {
			Log.logE(e);
		}
	}
	
	private native long dialRasWindows(String username,String password);

	private native String dialRasWindowsErrorStr(long error);
}
