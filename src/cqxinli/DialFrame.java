package cqxinli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class DialFrame extends JFrame{
	
	private FormPanel gFPUser;
	private PasswordPanel gPPPassword;
	
	private JCheckBox pChkRem;
	private JCheckBox pChkAutoDial;
	private JCheckBox pChkHeartBeat;
	//��ȡ�˺ź�����
	public String getAccName(){
		return this.gFPUser.getValue();
	}
	
	public String getAccPassword(){
		return this.gPPPassword.getPassword();
	}
	
	public boolean isRememberAcc(){
		return pChkRem.isSelected();
	}
	
	public boolean isAutoDial(){
		return this.pChkAutoDial.isSelected();
	}
	
	public boolean isHeartBeat(){
		return this.pChkHeartBeat.isSelected();
	}
	
	//���������Ŀ
	
	private FormPanel gSSID;
	private PasswordPanel gPPWifiPassword;
	//��ȡWIFI��Ϣ
	public String getSSID(){
		return this.gSSID.getValue();
	}
	
	public String getWifiKey(){
		return this.gPPWifiPassword.getPassword();
	}
		
	private JLabel gConState;
	private JLabel gWifiState;
		
	public void setConnectionState(CharSequence c){
		Log.log("����״̬��"+c);
		this.gConState.setText(c.toString());
	}
	
	//������,����ʼ��
	public void setConfigDataDial(String name,String password,boolean isHeartBeat,boolean isRem,boolean isAutoDial){
		this.gFPUser.setValue(name);
		this.gPPPassword.setPassword(password);
		this.pChkAutoDial.setSelected(isAutoDial);
		this.pChkHeartBeat.setSelected(isHeartBeat);
		this.pChkRem.setSelected(isRem);
	}
	
	public void setConfigDataWifi(String SSID,String password){
		this.gSSID.setValue(SSID);
		this.gPPWifiPassword.setPassword(password);
	}
	
	
	
	
	private boolean isDialed;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2828127360103736288L;

	public DialFrame(String m) {
		super(m);
		int width=400,height=200;
		this.setSize(width, height);
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension ss=tk.getScreenSize();
		this.setLocation(ss.width/2-width/2, ss.height/2-height/2);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		add();
	}
	
	private void add(){
		this.setLayout(new BorderLayout());
		
		//ѡ�ҳ��
		JTabbedPane pTPDialPanel=new JTabbedPane();
		
		JPanel pTPDial=new JPanel();
		pTPDial.setLayout(new GridLayout(5,1));
		
		this.gFPUser=new FormPanel("У԰����˺�","���������У԰����˺�",true);
		this.gPPPassword=new PasswordPanel("У԰�������");
		this.gSSID=new FormPanel("�����ȵ�����","������������ȵ�����",false);
		this.gPPWifiPassword=new PasswordPanel("������������ȵ������",false);
		pTPDial.add(this.gFPUser);
		pTPDial.add(this.gPPPassword);
		JPanel pPanCheck1=new JPanel();
		pChkRem=new JCheckBox("��ס�ҵ��˻�");
		
		pChkAutoDial=new JCheckBox("����ʱ�Զ���¼");
		pChkAutoDial.setEnabled(false);
		pPanCheck1.add(pChkRem);
		pPanCheck1.add(pChkAutoDial);
		
		pChkHeartBeat=new JCheckBox("������ģ��");
		pChkHeartBeat.setEnabled(false);
		pPanCheck1.add(pChkHeartBeat);
		
		pTPDial.add(pPanCheck1);
		
		this.gConState=new JLabel("׼������");
		this.gConState.setForeground(Color.RED);
		pTPDial.add(this.gConState);
		
		final JButton pButTestDial=new JButton("�����˺�");
		pButTestDial.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new ClickDial(pButTestDial).Dial("chongzhi@cqdx", "111111", false);			
			}
		});
				
		JPanel pPanButton = new JPanel();
		JButton pButDial=new JButton("��������");
		pButDial.setForeground(Color.MAGENTA);
		pButDial.addActionListener(new ClickDial(pButDial));	
		
		JButton pButBackMenu=new JButton("���ز˵�");
		pButBackMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MainClass.getMenuFrame().setVisible(true);
				MainClass.getDialFrame().setVisible(false);
			}
			
		});
		
		pPanButton.add(pButTestDial);
		pPanButton.add(pButDial);				
		pPanButton.add(pButBackMenu);
		
		pTPDial.add(pPanButton);
		pTPDialPanel.add(pTPDial,"���Ų���");
		
		
		JPanel pTPWifi=new JPanel(new GridLayout(4,1));
		pTPWifi.add(this.gSSID);
		pTPWifi.add(this.gPPWifiPassword);
		
		JPanel pPanConfig=new JPanel();		
		JButton pButWifi=new JButton("���������ȵ�");
		pButWifi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isDialed){
					
				}
				
			}
			
		});
		
		pPanConfig.add(pButWifi);
		this.gWifiState=new JLabel("����״̬��Unknown");		
		pTPWifi.add(this.gWifiState);		
		pTPWifi.add(pPanConfig);
		
		pTPDialPanel.add(pTPWifi,"���߹���");
		
		add(pTPDialPanel);
	}

}
