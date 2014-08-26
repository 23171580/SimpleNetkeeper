package cqxinli;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DataFrame extends JFrame{
	
	private static JLabel Tips=new JLabel("׼���������������Ҫ��Ϣ��ֱ�ӵ�������á���ʼ����·����");
	
	public DataFrame(String name){
		super(name);
		NewFrame();
	}
	
	private void NewFrame(){
		int width=400,height=360;
		this.setSize(width, height);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension ss=tk.getScreenSize();
		this.setLocation(ss.width/2-width/2, ss.height/2-height/2);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		addPanel();
		this.setVisible(true);
	}
	
	private void addPanel(){
		this.setLayout(new GridLayout(10,1));
		final FormPanel name=new FormPanel("���������û���");
		add(name);
		FormPanel realname=new FormPanel("���ܺ���û���","",false);
		add(realname);
		FormPanel encodedRealname=new FormPanel("URL�������û���","",false);
		add(encodedRealname);
		final PasswordPanel password=new PasswordPanel("��������");
		add(password);
		final FormPanel ip=new FormPanel("·����IP��ַ(Ĭ��192.168.1.1)");
		add(ip);
		final FormPanel adminName=new FormPanel("·��������Ա�û���","admin",true);
		add(adminName);
		final PasswordPanel adminPassword=new PasswordPanel("·��������Ա����");
		add(adminPassword);
		//��ʾ
		JPanel jp2=new JPanel();
		jp2.setBackground(Color.WHITE);
		add(jp2);
		Tips.setForeground(Color.RED);
		jp2.add(Tips);
		//��ť
		JPanel jp3=new JPanel();		
		//JButton dial=new JButton("��������");
		//dial.addActionListener(new ClickDial(name,password));
		JButton gen=new JButton("����");
		gen.addActionListener(new ClickGen(name,realname,encodedRealname));
		JButton set=new JButton("����·����");
		//�û��������룬IP,·��������Ա���ƣ�����Ա����
		set.addActionListener(new ClickSet(name,password,ip,adminName,adminPassword));
		JButton def=new JButton("Ĭ��");
		def.addActionListener(new ClickDefault(ip,adminName,adminPassword));
		JButton help=new JButton("����");
		help.addActionListener(new ClickHelp());
		JButton save = new JButton("����");
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MainClass.saveUserData(name, password, ip, adminName, adminPassword);
			}
			
		});
		//jp3.add(dial);
		jp3.add(gen);		
		jp3.add(def);
		jp3.add(set);
		jp3.add(save);
		jp3.add(help);
		this.add(jp3);
		JLabel ver=new JLabel("�汾"+MainClass.getVersionNoBuild()+" by CrazyChen@CQUT");
		add(ver);
		MainClass.setUserData(name, password, ip, adminName, adminPassword);
	}
	
	public static void showTips(String info){
		Tips.setText(info);
	}
}
