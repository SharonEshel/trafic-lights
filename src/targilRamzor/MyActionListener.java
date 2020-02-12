package targilRamzor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

/*
 * Created on Tevet 5770 
 */

/**
 * @author לויאן
 */

public class MyActionListener implements ActionListener
{
	Event64 evPress;
	Event64 evPressShabat;
	
	public MyActionListener( Event64 evPress,Event64 evShabats)
	{
		this.evPress=evPress;
		this.evPressShabat=evShabats;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton butt=(JRadioButton)e.getSource();
		System.out.println(butt.getName());
		
		if(butt.getName().equals("16"))
		{
			evPressShabat.sendEvent();	
		}
		else
		{
			evPress.sendEvent(butt.getName());	
		}
		
		
		//		butt.setEnabled(false);
		//		butt.setSelected(false);
	}

}
