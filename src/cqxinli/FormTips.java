package cqxinli;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class FormTips extends JFrame{
	private String url;
	public FormTips(String URL){
		super();
		this.url=URL;
		this.setVisible(true);
		this.setLocation(200, 200);
		this.setSize(600,400);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		JTextArea jtf=new JTextArea();
		jtf.setEditable(false);
		jtf.setText("�����ڳ�����������˺�ʱ��⵽��һ����½����\n����ζ�ţ�\n�п������ṩ��·��������Ա�˺Ŵ��󣬻���\n·�������������㳢�����·�������·��������\n\n���ڵڶ�������������������ڵ�ַ����������������\n\n"+this.url+"\n\n������·������Ҫ���������룬���������Ĺ���Ա�˺ź������������");
		jtf.setLineWrap(true);
		add(jtf,BorderLayout.NORTH);
		
		JButton jbt=new JButton("���Ƶ����а�");
		jbt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StringSelection stsel = new StringSelection(url);
		        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
		        JOptionPane.showMessageDialog(null, "��ַ�Ѹ��Ƶ����а�");
			}
			
		});
		add(jbt,BorderLayout.SOUTH);
	}
	
	
}
