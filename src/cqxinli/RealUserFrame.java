package cqxinli;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
class RealUserFrame extends JPanel{
	private FormPanel URLName=null;
	private FormPanel EncodedName=null;
	private String Username=null;
	private JTextField gUrlArea=null;
	private String gAccPassword=null;
	private JButton gButCopy=null;
	private FormPanel gUserName=null;
	private FormPanel gUserPassword=null;
	private FormPanel gRouterIP=null;
	public RealUserFrame(String username){
		initFrame();
		if(username!=null)
			setUsername(username);
	}
	
	public RealUserFrame(){
		this(null);
	}
	
	public void setUrlInfo(String ip,String password){
		this.gAccPassword=password;
		if(this.NotEmpty(this.gRouterIP.getValue()) && this.NotEmpty(this.gAccPassword) && this.NotEmpty(this.URLName.getValue())){
			this.gUrlArea.setText("http://"+gRouterIP.getValue()+"/userRpm/PPPoECfgRpm.htm?wan=0&wantype=2&acc="+URLName.getValue()+"&psw="+gAccPassword+"&confirm="+gAccPassword
	    				+"sta_ip=0.0.0.0&sta_mask=0.0.0.0&linktype=2&Connect=%C1%AC+%BD%D3");
			this.gButCopy.setEnabled(true);
		}		
		else{
			this.gUrlArea.setText("����·����IP��ַ�Լ�����˺���Ϣ��ſ������ɡ�");
			this.gButCopy.setEnabled(false);
		}
			
	}
	
	private boolean NotEmpty(String x){
		if (x==null) return false;
		return !x.equals("");
	}

	
	private void initFrame(){
		JLabel tips=new JLabel("���ɵ��û���");
		this.gUserName=new FormPanel("����������û���","",true);
		this.gUserPassword=new FormPanel("�˻����룿","ֻ��������Ҫ��ȡ��������ʱ��������",true);
		this.gRouterIP=new FormPanel("·����IP","ֻ��������Ҫ��ȡ��������ʱ����",true);
		this.URLName=new FormPanel("URL�������û���","",false);
		this.EncodedName=new FormPanel("���ܺ���û���","",false);
		this.add(tips);
		this.add(this.gUserName);
		this.add(this.gUserPassword);
		this.add(this.gRouterIP);
		this.add(this.URLName);
		this.add(EncodedName);
		JLabel mTipsURL=new JLabel("���ɵ��趨��ַ");
		add(mTipsURL);
		gUrlArea=new JTextField();
		gUrlArea.setEditable(false);
		add(gUrlArea);
		
		this.add(new JLabel("��ע�⣺·������������ֻ��ˮ��/TP·������Ч��"));
		
		JPanel pOperationPanel = new JPanel();
		gButCopy=new JButton("���Ƶ�ַ�����а�");
		gButCopy.setEnabled(false);
		gButCopy.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					Toolkit mToolkit=Toolkit.getDefaultToolkit();
					StringSelection mUrlData=new StringSelection(gUrlArea.getText());
					mToolkit.getSystemClipboard().setContents(mUrlData, mUrlData);
					JOptionPane.showMessageDialog(null, "�ѽ���ַ���Ƶ����а�");
			}
			
		});
		pOperationPanel.add(gButCopy);
		
		JButton pButCalculate=new JButton("�����˺�");
		pButCalculate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setUsername(gUserName.getValue());			
			}
			
		});
		
		
		pOperationPanel.add(pButCalculate);
		this.add(pOperationPanel);
		this.setLayout(new GridLayout(10,1));
	}

	public void setUsername(String username){
		this.Username=username;
		if(!this.Username.equals("") && this.Username!=null){
			CXKUsername un=new CXKUsername(this.Username);
			try {
				this.URLName.setValue(URLEncoder.encode(un.Realusername(), "UTF-8").replace("+", "%2D"));
				this.EncodedName.setValue(un.Realusername());
				this.setUrlInfo(this.gRouterIP.getValue(), this.gUserPassword.getValue());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "����д����˺�����!\n�����Ҫ��ȡ�������ӣ���ͬʱ�����������\n�������ӻ�ȡ����ֻ�Բ���ˮ��/TP·������Ч");
		}
	}
	
}