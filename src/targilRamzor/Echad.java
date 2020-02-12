package targilRamzor;
import java.awt.Color;

import javax.swing.JPanel;

/*
 * Created on Mimuna 5767  upDate on Tevet 5770 
 */


class Echad extends Thread
{
	String state;
	Ramzor ramzor;
	JPanel panel;
	public Echad( Ramzor ramzor,JPanel panel)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		start();
	}

	public void run()
	{
		try 
		{
			state="YELLOW";
			setLight(1,Color.YELLOW);
			while (true)
			{
				switch (state)
				{
				case "YELLOW":
					sleep(500);
					setLight(1,Color.GRAY);
					state="GRAY";
					break;
				case "GRAY":
					sleep(500);
					setLight(1,Color.YELLOW);
					state="YELLOW";
					break;
				}
				
			}
		} catch (InterruptedException e) {}

	}
	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}
}
