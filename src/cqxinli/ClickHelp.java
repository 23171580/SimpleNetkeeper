package cqxinli;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ClickHelp implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		new HelpFrame();		
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
			this.setVisible(true);
			this.setSize(500, 550);
			Toolkit tk=Toolkit.getDefaultToolkit();
			Dimension dm=tk.getScreenSize();
			this.setResizable(false);
			this.setLocation(dm.width/2-250,dm.height/2-275);
			JTextArea jta=new JTextArea();
			this.setLayout(new BorderLayout());
			add(jta,BorderLayout.NORTH);
			jta.setEditable(false);
			jta.setLineWrap(true);
			String data="��ӭʹ��Netkeeper Dialer For Router �汾1.0(Build 012.20140820)\n\n"
					+ "���СJAVA������Է����һ������·�������š��������������У԰����û���\n\n"
					+ "����TP-LINK���¹̼����Լ�ˮ��(Mercury)·��������ͨ��������·���������в��ԡ����ۿ��á�\n\n"
					+ "ʹ�÷�����\n�û���������������Ŀ���˺ź�����\n"
					+ "·����IPһ�����������·������IP��ַ��Ĭ��Ϊ192.168.0.1����192.168.1.1��\n"
					+ "·�����û���һ��Ĭ��Ϊadmin������Ĭ��Ϊadmin�����������ݿ�����·���������·��ı�ǩ���ҵ���\n"
					+ "���ڻָ���·�������ָ��������ã����û���ֱ�ӵ����Ĭ�ϡ���ȷ����Ϣ����󼴿�\n"
					+ "���������·������������Ὺʼ����Ϊ������·����������ݡ������ʾ�ɹ������������ĵȺ�1�������ң���Ϊ·������Ҫ���Ų��������һ���Ӻ�����û����ͨ���������ζ��������������ڸò�Ʒ��\n\n"
					+ "�����ֻ��Ҫ���ܺ���û��������������ɡ�\n\n"
					+ "��л�������ʵ��ѧ��ѧ���������ǵĸ�������õ���Netkeeper�ļ����㷨��\n\n"
					+ "Դ��������� http://i.itncre.com/redirect?tar=github �ҵ���������δ��ͨ������Ȼ��Ҳ���Է���������\n\n"
					+ "CopyLeft 2014 CrazyChen@��������ѧ   �����ʼ���cx@itncre.com\n\n"
					+ "���棺����������˺ż��ܵ�ȫ�����ݾ����Ի�����������ѧϰ����֮�ã����˲��������κη��򹤳̡��ɴ�����������κκ����ʹ���߱��˳е��������߸Ų�����"
					+ "�������Ϊ���������������۴�����Լ���������޸Ļ�/�������汾������׷�����Ρ�";
			jta.setText(data);
			
		}
	}

}
