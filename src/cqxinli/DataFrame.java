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
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class DataFrame extends JFrame{
	
	private static JLabel Tips=new JLabel("׼���������������Ҫ��Ϣ��ֱ�ӵ�������á���ʼ����·����");
	private FormPanel name=null;
	private PasswordPanel password=null;
	private FormPanel ip=null;
	private FormPanel adminName=null;
	private PasswordPanel adminPassword=null;
	
	public void setAccName(String accName){
		this.name.setValue(accName);
	}
	
	public void setAccPassword(String password){
		this.password.setPassword(password);
	}
	
	public void setRouterIpAddress(String ip){
		this.ip.setValue((ip==null || ip.trim().equals(""))?"192.168.1.1":ip);
	}
	
	public void setRouterAccName(String name){
		this.adminName.setValue((name==null || name.trim().equals(""))?"admin":name);
	}
	
	public void setRouterAccPassword(String password){
		this.adminPassword.setPassword(password);
	}
	
	public String g_getAccName(){
		return name.getValue();
	}
	
	public String g_getAccPassword(){
		return this.password.getPassword();
	}
	
	public String g_getRouterIP(){
		return this.ip.getValue().trim().equals("")?"192.168.1.1":this.ip.getValue();
	}
	
	public String g_getRouterAdmin(){
		return this.adminName.getValue().trim().equals("")?"admin":this.adminName.getValue();
	}
	
	public String g_getRouterPassword(){
		return this.adminPassword.getPassword();
	}
	
	public DataFrame(String name){
		super(name);
		NewFrame();
	}
	
	private void NewFrame(){
		int width=400,height=400;
		this.setSize(width, height);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension ss=tk.getScreenSize();
		this.setLocation(ss.width/2-width/2, ss.height/2-height/2);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		addPanel();	
	}
	
	private void addPanel(){
		JTabbedPane pTPSwitchPanel = new JTabbedPane();
		
		
		JPanel pPanelRouter = new JPanel();
		pPanelRouter.setLayout(new GridLayout(10,1));
		JLabel m_lab_accinfo=new JLabel("У԰����˺���Ϣ");
		pPanelRouter.add(m_lab_accinfo);
		name=new FormPanel("���������û���");
		name.setTooltipData("���������������У԰/��ͥ����˺�����\n���� 111111111@cqxxx");
		pPanelRouter.add(name);
		password=new PasswordPanel("��������");
		password.setTooltipData("���������������У԰/��ͥ����˺�����");
		pPanelRouter.add(password);
		JLabel m_lab_routerinfo=new JLabel("·��������Ա��Ϣ������·���������·���ǩ�ҵ�");
		pPanelRouter.add(m_lab_routerinfo);
		ip=new FormPanel("·����IP��ַ(Ĭ��192.168.1.1)");
		ip.setValue("192.168.1.1");
		ip.setTooltipData("���������·������IP��ַ��");
		pPanelRouter.add(ip);
		adminName=new FormPanel("·��������Ա�û���","admin",true);
		adminName.setTooltipData("�������·�����Ĺ���Ա���ƣ�������·������½ֻҪ�����룬�����롰admin��");
		pPanelRouter.add(adminName);
		adminPassword=new PasswordPanel("·��������Ա����");
		adminPassword.setTooltipData("���������������·��������Ա�����롣");
		pPanelRouter.add(adminPassword);
		//��ʾ
		JPanel jp2=new JPanel();
		jp2.setBackground(Color.WHITE);
		jp2.setLayout(new GridLayout(1,1));
		pPanelRouter.add(jp2);
		Tips.setForeground(Color.RED);
		jp2.add(Tips);
		//��ť
		JPanel jp3=new JPanel();		
		//JButton dial=new JButton("��������");
		//dial.addActionListener(new ClickDial(name,password));
		
		JButton pButSet=new JButton("����·����");
		pButSet.setToolTipText("����Ժ󣬳��򽫿�ʼ����ʹ�����ṩ������ִ�����Ӳ�����");
		//�û��������룬IP,·��������Ա���ƣ�����Ա����
		pButSet.addActionListener(new ClickSet(pButSet));
		JButton pButDef=new JButton("Ĭ��");
		pButDef.setToolTipText("��������Ὣ·������Ϣȫ�����á�IP����Ϊ192.168.1.1������Ա����admin������Ա����admin������������·������ʹ�á�");
		pButDef.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MainClass.getDataFrame().setRouterAccName("admin");
				MainClass.getDataFrame().setRouterIpAddress("192.168.1.1");
				MainClass.getDataFrame().setRouterAccPassword("admin");
			}
			
		});
		
		JButton pButSave = new JButton("����");
		pButSave.setToolTipText("�������Ѿ��������Ϣ");
		pButSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				MainClass.saveUserData();
			}
			
		});

		//jp3.add(dial);		
		jp3.add(pButDef);
		jp3.add(pButSet);
		jp3.add(pButSave);
		pPanelRouter.add(jp3);
		JPanel pVerPanel=new JPanel();
		pVerPanel.setLayout(new BorderLayout());
		JLabel pLabVer=new JLabel("�汾"+MainClass.getVersionNoBuild()+" by CrazyChen@CQUT");
		pVerPanel.add(pLabVer,BorderLayout.WEST);
		
		JButton pButBackMenu=new JButton("���ز˵�");
		pButBackMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MainClass.getMenuFrame().setVisible(true);
				MainClass.getDataFrame().setVisible(false);
			}
			
		});
		pVerPanel.add(pButBackMenu,BorderLayout.EAST);
		
		pPanelRouter.add(pVerPanel);		
				
		pTPSwitchPanel.add(pPanelRouter,"·��������");
		pTPSwitchPanel.add(new RealUserFrame(),"�˺ż���");
		pTPSwitchPanel.add(new AdvancePanel(),"�߼�ѡ��");
		pTPSwitchPanel.add(new HelpInfoPanel(),"�������");
		add(pTPSwitchPanel);
		
		Log.log("�Ѿ���ɽ����������");
	}
	
	public static void showTips(String info){
		Tips.setText(info);
	}
}
