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
			if(this.gBut!=null) this.gBut.setEnabled(false);
			if(MainClass.getDialFrame().isRememberAcc()){
				MainClass.saveUserData();
			}
			//�����û�
			final String Realname=enc?new CXKUsername(name).Realusername():name;
			//��ʼ����
			MainClass.getDialFrame().setConnectionState("��ʼ���Ų��������Ժ�");
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
		case 625:this.setInfo("���ӷ����ڲ�����625��");break;
		case 629:this.setInfo("���ӱ�Զ�̼�����رգ�629��");break;
		case 651:this.setInfo("���ƽ�����������豸�޷����ӣ�651��");break;
		case 678:this.setInfo("Զ�̼����û����Ӧ��678��");break;
		case 691:this.setInfo("�û�ƾ���޷��������磨691��");break;
		case 711:this.setInfo("���鲦����ط����Ƿ��Ѿ���������711��");break;
		case 720:this.setInfo("���ܽ������ӡ��������Ҫ�������ã�720��");break;
		case 813:this.setInfo("���Ѿ���һ����������ˣ�(813)");break;
		case 815:this.setInfo("Զ�̼����û����Ӧ(815)");
		default:this.setInfo("�����õķ���״̬�����룺"+this.getRes());
		}
		System.out.println(this.getRes());
		MainClass.getDialFrame().allowWifi(this.getRes()==0);
		try {
			Log.log(this.getRes()+":"+new String(this.dialRasWindowsErrorStr(getRes()).getBytes(),"GB2312"));
		} catch (UnsupportedEncodingException e) {
			Log.logE(e);
		}
	}
	
	private native long dialRasWindows(String username,String password);

	private native String dialRasWindowsErrorStr(long error);
}
