package cqxinli;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class ClickGen implements ActionListener {
	
	private RealUserFrame key;
	public ClickGen(RealUserFrame key){		
		this.key=key;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			if(MainClass.getDataFrame().g_getAccName().equals("")) {
				JOptionPane.showMessageDialog(null, "��������û���Ϊ��");
			}else{
				this.key.setUsername(MainClass.getDataFrame().g_getAccName());
				this.key.setUrlInfo(MainClass.getDataFrame().g_getRouterIP(), MainClass.getDataFrame().g_getAccPassword());
				this.key.showFrame();
			}
	}
}

