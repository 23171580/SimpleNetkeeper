package cqxinli;

import java.awt.BorderLayout;
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
	private FormPanel name=null;
	private PasswordPanel password=null;
	private FormPanel ip=null;
	private FormPanel adminName=null;
	private PasswordPanel adminPassword=null;
	
	public String g_getAccName(){
		return name.getValue();
	}
	
	public String g_getAccPassword(){
		return this.password.getPassword();
	}
	
	public String g_getRouterIP(){
		return this.ip.getValue();
	}
	
	public String g_getRouterAdmin(){
		return this.adminName.getValue();
	}
	
	public String g_getRouterPassword(){
		return this.adminPassword.getPassword();
	}
	
	public DataFrame(String name){
		super(name);
		NewFrame();
	}
	
	private void NewFrame(){
		int width=400,height=380;
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
		JLabel m_lab_accinfo=new JLabel("У԰����˺���Ϣ");
		add(m_lab_accinfo);
		name=new FormPanel("���������û���");
		add(name);
		password=new PasswordPanel("��������");
		add(password);
		JLabel m_lab_routerinfo=new JLabel("·��������Ա��Ϣ������·���������·���ǩ�ҵ�");
		add(m_lab_routerinfo);
		ip=new FormPanel("·����IP��ַ(Ĭ��192.168.1.1)");
		add(ip);
		adminName=new FormPanel("·��������Ա�û���","admin",true);
		add(adminName);
		adminPassword=new PasswordPanel("·��������Ա����");
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
		JButton pButGen=new JButton("����");
		RealUserFrame pRuf=new RealUserFrame();
		pButGen.addActionListener(new ClickGen(name,pRuf,password,ip));
		JButton pButSet=new JButton("����·����");
		//�û��������룬IP,·��������Ա���ƣ�����Ա����
		pButSet.addActionListener(new ClickSet(pButSet,this));
		JButton pButDef=new JButton("Ĭ��");
		pButDef.addActionListener(new ClickDefault(ip,adminName,adminPassword));
		JButton pButHelp=new JButton("����");
		pButHelp.addActionListener(new ClickHelp(ip,adminName,adminPassword));
		JButton pButSave = new JButton("����");
		pButSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MainClass.saveUserData(name.getValue(), password.getPassword(), ip.getValue(), adminName.getValue(), adminPassword.getPassword());
			}
			
		});
		
		MainClass.setUserData(name, password, ip, adminName, adminPassword);
		
		JButton pButAdvance=new JButton("�߼�");
		final AdvanceFrame pAdvFrame=new AdvanceFrame(this);
		pButAdvance.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				pAdvFrame.setVisible(true);
			}
			
		});
		//jp3.add(dial);
		jp3.add(pButGen);		
		jp3.add(pButDef);
		jp3.add(pButSet);
		jp3.add(pButSave);
		jp3.add(pButAdvance);
		this.add(jp3);
		JPanel pVerPanel=new JPanel();
		pVerPanel.setLayout(new BorderLayout());
		JLabel pLabVer=new JLabel("�汾"+MainClass.getVersionNoBuild()+" by CrazyChen@CQUT");
		pVerPanel.add(pLabVer,BorderLayout.WEST);
		pVerPanel.add(pButHelp,BorderLayout.EAST);
		add(pVerPanel);		
		Log.log("�Ѿ���ɽ����������");
	}
	
	public static void showTips(String info){
		Tips.setText(info);
	}
}
