package targilRamzor;
import java.awt.Color;

import javax.swing.JPanel;


class ShneyLuchot extends Thread
{
	private Event64 evGreen,evRed,evShabat,evHol,evMyRed;
	enum OutState{HOL,SHABAT};
	OutState outState;
	enum InState{RED,GREEN};
	InState inState;
	Ramzor ramzor;
	JPanel panel;
	String state;
	public ShneyLuchot(Ramzor ramzor,JPanel panel,
			Event64 evGreen,Event64 evRed,Event64 evShabat,Event64 evHol,Event64 evMyRed)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		this.evGreen=evGreen;
		this.evRed=evRed;
		this.evShabat=evShabat;
		this.evHol=evHol;
		this.evMyRed=evMyRed;
		start();
	}

	public void run()
	{
		boolean finish=false;
		boolean out=false;
		outState=OutState.HOL;
		inState=InState.RED;
		init();
		while (!finish)
		{
			switch(outState)
			{
			case HOL:
				while (!out)
				{
					switch(inState)
					{
					case RED:
						while(true)
						{
							if(evGreen.arrivedEvent())
							{
								evGreen.waitEvent();
								setLight(1,Color.GRAY);
								setLight(2,Color.GREEN);
								inState=InState.GREEN;
								break;
							}
							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(2,Color.GRAY);
								out=true;
								outState=OutState.SHABAT;
								break;	
							}
							else
								yield();
						}
						break;

					case GREEN:
						while(true)
						{
							if(evRed.arrivedEvent())
							{
							/*	//we want to sleep because we want to see the green light 
								try {
									sleep(5000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}*/
								evRed.waitEvent();
								setLight(2,Color.GRAY);
								setLight(1,Color.RED);
								inState= InState.RED;
								evMyRed.sendEvent();//////send event to the controller
								break;
							}
							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(2,Color.GRAY);
								out=true;
								outState=OutState.SHABAT;
								break;	
							}
							else
								yield();
						}
						break;
					}
				}
				break;

			case SHABAT:
				evHol.waitEvent();
				out=false;
				init();
				outState=OutState.HOL;
				inState=InState.RED;
				break;	
			}

		}
	} 

	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}

	private void init()
	{
		setLight(1,Color.RED);
		/*try
		{
			sleep(56000);
		}
		catch (InterruptedException e) {}*/
	}


}
