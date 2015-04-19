package cqxinli;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpInfoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public HelpInfoPanel(){
		this.setSize(400,320);
		
		this.setLayout(new BorderLayout());
		
		JTextArea jta=new JTextArea();
		jta.setEditable(false);
		jta.setLineWrap(true);
		//����String ��ƴ�Ӳ����ǳ���������˸���ΪStringBuilder������Ϣ
		StringBuilder sb=new StringBuilder();
		sb.append("��ӭʹ��Netkeeper Dialer For Router �汾"+MainClass.getVersion()+MainClass.BUILD_DATE+"\n");
		sb.append("���СJAVA������Է����һ������·�������š��������������У԰����û���\n\n");
		sb.append("��������ҵĸ��˲���  http://www.sunflyer.cn/?p=8 �ҵ�����ĸ��°汾\n\n");
		sb.append("��л�������ʵ��ѧ��ѧ����Netkeeper�ļ����㷨��(��Ȼ�����Լ�����Ҳ�ɹ��ˡ�)\n");
		sb.append("Դ��������� https://github.com/sunflyer/NetkeeperForRouter �ҵ�\n\n");
		sb.append("CopyLeft 2014 CrazyChen@CQUT   �����ʼ���cx@itncre.com\n\n");
		sb.append("���棺����������˺ż��ܵ�ȫ�����ݾ����Ի�����������ѧϰ����֮�ã����˲��������κη��򹤳̡��ɴ�����������κκ����ʹ���߱��˳е��������߸Ų�����");
		sb.append("�������Ϊ���������������۴�����Լ���������޸Ļ�/�������汾������׷�����Ρ�");
		jta.setText(sb.toString());
		
		jta.setSize(400, 300);
		
		JScrollPane scroll = new JScrollPane(jta); 
				scroll.setVerticalScrollBarPolicy( 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

		scroll.setSize(400, 300);
		this.add(scroll,BorderLayout.NORTH);
	}	
}
