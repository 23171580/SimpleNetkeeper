package cqxinli;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AdvancePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public AdvancePanel(){
		initWindow();
	}
	
	private void initWindow(){
		this.setLayout(new GridLayout(11,1));

		ComboBoxPanel<String> pCBPanelIsSchool=new ComboBoxPanel<String>("ʹ��ģʽ",new String[]{"����ģʽ�������ܣ�","ѧУģʽ�������˺ţ�"},new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED){
					String pSelected=e.getItem().toString();
					if(pSelected.equals("����ģʽ�������ܣ�")){
						MainClass.setEncryptedAcc(false);
					}else if(pSelected.equals("ѧУģʽ�������˺ţ�")){
						MainClass.setEncryptedAcc(true);
					}
				}
			}
			
		});
		pCBPanelIsSchool.setSelectedIndex(MainClass.getEncrytedAcc()?1:0);
		this.add(pCBPanelIsSchool);
		
		ComboBoxPanel<String> pCBPanelRouter=new ComboBoxPanel<String>("·����Ʒ��",MainClass.RouterList,new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					String pSelected=e.getItem().toString();
					for(int i=0;i<MainClass.RouterList.length;i++){
						if(pSelected.equals(MainClass.RouterList[i])) {
							MainClass.setRouterManufactor(i+1);
							break;
						}							
					}
				}
			}
		}
		);
		pCBPanelRouter.setSelectedIndex(MainClass.getRouterManufactor()-1);
		this.add(pCBPanelRouter);
		
		
		ComboBoxPanel<String> pCBPanelDialType=new ComboBoxPanel<String>("��������",MainClass.DialList,new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					MainClass.setDialType(e.getItem().toString());
				}
			}
			
		});
		pCBPanelDialType.setSelectedItem(MainClass.DialList[MainClass.getDialType()]);
		this.add(pCBPanelDialType);
			
		ComboBoxPanel<String> pCBPanelAlgVer=new ComboBoxPanel<String>("�����㷨�汾",MainClass.AlgVer,new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					String pSelected=e.getItem().toString();
					for(int i=0;i<MainClass.AlgVer.length;i++){
						if(pSelected.equals(MainClass.AlgVer[i])) {
							//MainClass.setAlgVer(i+1);
							break;
						}							
					}
				}
			}
			
		});
		pCBPanelAlgVer.setAllowSelect(false);
		this.add(pCBPanelAlgVer);
		
		ComboBoxPanel<String> pCBPanelRealm=new ComboBoxPanel<String>("��������λ��",new String[]{"����","�㽭"},new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					String pSelected=e.getItem().toString();
					if(pSelected.equals("����")){
						
					}else if(pSelected.equals("�㽭")){
						
					}
				}
			}
			
		});
		pCBPanelRealm.setAllowSelect(false);
		this.add(pCBPanelRealm);
		
		JPanel pPanelRouterWirelessSet = new JPanel();
		pPanelRouterWirelessSet.add(new JLabel("�·���·��������������������"));
		this.add(pPanelRouterWirelessSet);
		
		final FormPanel pFPWirelessSSID=new FormPanel("·����SSID����WiFi���ƣ�");
		pFPWirelessSSID.setValue("���ȵ������ȡ��Ϣ����ȡ�õ�ǰ��������");
		this.add(pFPWirelessSSID);
		final PasswordPanel pPPWirelessPwd=new PasswordPanel("������������");
		this.add(pPPWirelessPwd);
		
		JPanel pPanelRouterWirelessSetButton = new JPanel();
		
		final JCheckBox pHideSSID=new JCheckBox("����SSID");
		
		
		final JButton pButtonSetSSID=new JButton("��������(S)");
		pButtonSetSSID.setMnemonic(KeyEvent.VK_S);
		pButtonSetSSID.setEnabled(false);
		pButtonSetSSID.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Router tRouter=new Router(MainClass.getDataFrame().g_getRouterIP(),MainClass.getDataFrame().g_getRouterAdmin(),MainClass.getDataFrame().g_getRouterPassword(),MainClass.getDataFrame().g_getAccName(),MainClass.getDataFrame().g_getAccPassword());
				tRouter.setWifiState(new WiFiInfo(pFPWirelessSSID.getValue(),pPPWirelessPwd.getPassword(),pHideSSID.isSelected()));
			}
			
		});
				
		JButton pButtonGetData=new JButton("��ȡ��Ϣ(G)");
		pButtonGetData.setMnemonic(KeyEvent.VK_G);
		pButtonGetData.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				allowWirelessConfig(pButtonSetSSID,pHideSSID,pFPWirelessSSID,pPPWirelessPwd);
			}			
		});
		pPanelRouterWirelessSetButton.add(pHideSSID);
		pPanelRouterWirelessSetButton.add(pButtonSetSSID);
		pPanelRouterWirelessSetButton.add(pButtonGetData);
		
		this.add(pPanelRouterWirelessSetButton);
		
		JPanel pPanelApplicationConfig=new JPanel();
		pPanelApplicationConfig.add(new JLabel("Ӧ�ó�������"));
		this.add(pPanelApplicationConfig);
		
		JPanel pPanelApplicationConfigButton=new JPanel();
		
		JButton pButInfo=new JButton("·������ǰ��Ϣ");
		pButInfo.setEnabled(false);
		
		JButton pButInternal = new JButton("����ģʽ������ˮ��/TP��");
		pButInternal.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(AdvancePanel.this, "��ע�⣺\n�������ֻ��TP/ˮ��·������Ч����ִ�в�����Ϻ�·������WAN�ڽ��Ͽ���������ͬʱ�л�����̬IPģʽ��\n�ڼ�������ǰ������Ҫ��·�������Ž�����д��ȷ��·����������Ϣ��\nȷ��������","��������",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
					DataFrame tDf=MainClass.getDataFrame();
					Router tRouter=new Router(tDf.g_getRouterIP(),tDf.g_getRouterAdmin(),tDf.g_getRouterPassword(),tDf.g_getAccName(),tDf.g_getAccPassword(),MainClass.getAuthMethod());
					tRouter.setInternalNet();
				}
				
			}
			
		});
		
		pPanelApplicationConfigButton.add(pButInternal);
		pPanelApplicationConfigButton.add(pButInfo);
		this.add(pPanelApplicationConfigButton);
		
	}
	
	
	private void allowWirelessConfig(final JButton d,final JCheckBox p,final FormPanel a,final PasswordPanel b){
		new Thread(new Runnable(){
			@Override
			public void run() {
				Router tRt=new Router(MainClass.getDataFrame().g_getRouterIP(),MainClass.getDataFrame().g_getRouterAdmin(),MainClass.getDataFrame().g_getRouterPassword(),MainClass.getDataFrame().g_getAccName(),MainClass.getDataFrame().g_getAccPassword());
				final WiFiInfo pWifi=tRt.getWifiState();
				final Timer tTi=new Timer(2500,null);	
				
				tTi.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						if(pWifi!=null){
							if(pWifi.getWifiName()!=null){
								a.setValue(pWifi.getWifiName());
								d.setEnabled(true);
								tTi.stop();
							}	
						}
					}
					
				});
				tTi.start();
				
			}
			
		}).start();
	}
	
	
}
