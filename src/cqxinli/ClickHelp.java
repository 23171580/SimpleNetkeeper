package cqxinli;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ClickHelp implements ActionListener{

	protected FormPanel ip;
	protected FormPanel admin;
	protected PasswordPanel pwd;
	
	public ClickHelp(FormPanel ip,FormPanel admin,PasswordPanel pwd){
		this.ip=ip;
		this.admin=admin;
		this.pwd=pwd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new HelpFrame("����");		
	}
	
	@SuppressWarnings("serial")
	class HelpFrame extends JFrame{
		public HelpFrame(String name){
			super(name);
			HelpPre();
		}
		
		public HelpFrame(){
			super();
			HelpPre();
		}
		
		private void HelpPre(){
			Toolkit tk=Toolkit.getDefaultToolkit();
			Dimension dm=tk.getScreenSize();
			this.setResizable(false);
			this.setLocation(dm.width/2-250,dm.height/2-275);
			JTextArea jta=new JTextArea();
			this.setLayout(new BorderLayout());		
			jta.setEditable(false);
			jta.setLineWrap(true);
			//����String ��ƴ�Ӳ����ǳ���������˸���ΪStringBuilder������Ϣ
			StringBuilder sb=new StringBuilder();
			sb.append("��ӭʹ��Netkeeper Dialer For Router �汾"+MainClass.getVersion()+"\n");
			sb.append("���СJAVA������Է����һ������·�������š��������������У԰����û���\n\n");
			sb.append("����TP-LINK���¹̼����Լ�ˮ��(Mercury)·��������ͨ��������·���������в��ԡ����ۿ��á�(D-LINKò����һ�����������в��ԣ�����������ϵ�ҷ�����������)\n\n");
			sb.append("��Ҫ��ʾ�����·�����ϵ磬�볢����������·������Ȼ��·����ͨ��3���Ӻ��ٽ������ӡ����ڶϵ�������ԭ��·����ͨ�����״β���ʱ���Ƚϳ��������ĵȴ�\n\n");
			sb.append("ʹ�÷�����\n�û���������������Ŀ���˺ź�����\n");
			sb.append("·����IPһ�����������·������IP��ַ��Ĭ��Ϊ192.168.0.1����192.168.1.1��\n");
			sb.append("·�����û���һ��Ĭ��Ϊadmin������Ĭ��Ϊadmin�����������ݿ�����·���������·��ı�ǩ���ҵ���\n");
			sb.append("���ڻָ���·�������ָ��������ã����û���ֱ�ӵ����Ĭ�ϡ���ȷ����Ϣ����󼴿�\n");
			sb.append("���������·������������Ὺʼ����Ϊ������·����������ݡ������ʾ�ɹ������������ĵȺ�1�������ң���Ϊ·������Ҫ���Ų��������һ���Ӻ�����û����ͨ���������ζ��������������ڸò�Ʒ��\n\n");
			sb.append("�����ֻ��Ҫ���ܺ���û��������������ɡ�\n\n");
			sb.append("��л�������ʵ��ѧ��ѧ���������ǵĸ�������õ���Netkeeper�ļ����㷨��\n");
			sb.append("Դ��������� https://github.com/sunflyer/NetkeeperForRouter �ҵ�\n\n");
			sb.append("CopyLeft 2014 CrazyChen@CQUT   �����ʼ���cx@itncre.com\n\n");
			sb.append("���棺����������˺ż��ܵ�ȫ�����ݾ����Ի�����������ѧϰ����֮�ã����˲��������κη��򹤳̡��ɴ�����������κκ����ʹ���߱��˳е��������߸Ų�����");
			sb.append("�������Ϊ���������������۴�����Լ���������޸Ļ�/�������汾������׷�����Ρ�");
			jta.setText(sb.toString());
			add(jta,BorderLayout.NORTH);
			JButton debug=new JButton("Debug");
			debug.addActionListener(new ClickDebug(ip,admin,pwd));
			if(MainClass.getVersionSig()==MainClass.VER_DEBUG || MainClass.getVersionSig()==MainClass.VER_BETA || MainClass.isDebugAllow()) {
				debug.setEnabled(true);
				debug.setText("Debug(����󽫳��Ի�ȡ���ӷ�ʽ�������Ϣ��LOG)");
			}else{
				debug.setEnabled(false);
				debug.setText("Debug(���ڵ���/���԰汾��߼�ģʽ�¿���)");
			}				
			add(debug,BorderLayout.SOUTH);
			this.setSize(650, 545);
			this.setVisible(true);
		}
	}

}
