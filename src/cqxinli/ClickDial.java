package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ClickDial implements ActionListener{

	public ClickDial(FormPanel username,PasswordPanel pswd){
		this.username=username;
		this.password=pswd;
	}
	
	private FormPanel username;
	private PasswordPanel password;
	@Override
	public void actionPerformed(ActionEvent e) {
		String name=username.getValue();
		String pwd=password.getPassword();
		if(pwd.length()<6 || name.equals("")){
			DataFrame.showTips("�����û����������벻�Ϸ�");
		}
		else{
			DataFrame.showTips("���ȣ�������ܺ���û�����");
			//�����û���
			CXKUsername un=new CXKUsername(name);
			name=un.Realusername();
			//��ʼ����
			DataFrame.showTips("���ȣ���ʼ����");
			//���Ų���
			try {
				//������
				Process p=Runtime.getRuntime().exec("cmd /c rasdial NetKeeper "+name+" "+pwd);
				System.out.println("cmd /c rasdial NetKeeper "+name+" "+pwd);
				StringBuilder sb=new StringBuilder();
				BufferedReader be=new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while((line=be.readLine())!=null){
					sb.append(line);
				}
				String processData=sb.toString();
				if(processData.indexOf("������")>=0 || processData.indexOf("Connected")>=0){
					DataFrame.showTips("�ѳɹ���������");
				}
				else{
					DataFrame.showTips("��������ʧ��");
					System.out.println(processData);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
