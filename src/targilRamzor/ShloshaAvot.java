package targilRamzor;

import java.awt.Color;

import javax.swing.JPanel;

public class ShloshaAvot extends Thread
{
	private Event64 evGreen,evRed,evShabat,evHol,evMyRed;
	Ramzor ramzor;
	JPanel panel;
	int count;
	enum OutState{HOL,SHABAT};
	OutState outState;
	enum InState{RED,RED_ORANGE,GREEN,FLICKER_GRAY,FLICKER_GREEN,ORANGE,GRAY};
	InState inState;
	int bareket=0;

	public ShloshaAvot( Ramzor ramzor,JPanel panel,int key,
			Event64 evGreen,Event64 evRed,Event64 evShabat,Event64 evHol,Event64 evMyRed)
	{
		this.ramzor=ramzor;
		this.panel=panel;
		new CarsMaker(panel,this,key);
		this.evGreen=evGreen;
		this.evRed=evRed;
		this.evShabat=evShabat;
		this.evHol=evHol;
		this.evMyRed=evMyRed;
		start();
	}

	public void run()
	{
		Event64 evTime;
		MyTimer72 trigger;
		boolean finish=false;
		boolean out=false;
		outState=OutState.HOL;
		inState=InState.RED;
		initHol();

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
								setLight(2,Color.ORANGE);
								inState= InState.RED_ORANGE;
								bareket++;
								System.out.println(bareket);
								break;
							}
							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(3,Color.GRAY);
								out=true;
								initShabat();
								break;	
							}
							else
								yield();
						}
						break;

					case RED_ORANGE:
						evTime=new Event64();
						trigger=new MyTimer72(1000,evTime);
						while(true)
						{
							if(evTime.arrivedEvent())
							{
								evTime.waitEvent();
								setLight(1,Color.GRAY);
								setLight(2,Color.GRAY);
								setLight(3,Color.GREEN);
								inState=InState.GREEN;
								break;
							}
							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(3,Color.GRAY);
								out=true;
								initShabat();
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
								evRed.waitEvent();
								setLight(3,Color.GRAY);
								inState=InState.FLICKER_GRAY;
								break;
							}
							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(3,Color.GRAY);
								out=true;
								initShabat();
								break;	
							}
							else
								yield();
						}
						break;

					case FLICKER_GRAY:
						evTime=new Event64();
						trigger=new MyTimer72(1000,evTime);
						while(true)
						{
							if(evTime.arrivedEvent())
							{
								evTime.waitEvent();
								count++;
								setLight(3,Color.GREEN);
								inState=InState.FLICKER_GREEN;
								break;
							}
							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(3,Color.GRAY);
								out=true;
								initShabat();
								break;	
							}
							else
								yield();
						}
						break;

					case FLICKER_GREEN: 
						evTime=new Event64();
						trigger=new MyTimer72(1000,evTime);
						while(true)
						{
							if(evTime.arrivedEvent() && count<=3)
							{
								evTime.waitEvent();
								setLight(3,Color.GRAY);
								inState=InState.FLICKER_GRAY;
								break;
							}

							else if (count>3)
							{
								count=0;
								setLight(3,Color.GRAY);
								setLight(2,Color.ORANGE);
								inState=InState.ORANGE;
								break;
							}

							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(3,Color.GRAY);
								out=true;
								initShabat();
								break;	
							}
							else
								yield();
						}
						break;

					case ORANGE:
						evTime=new Event64();
						trigger=new MyTimer72(5000,evTime);
						while(true)
						{
							if(evTime.arrivedEvent())
							{
								evTime.waitEvent();
								setLight(2,Color.GRAY);
								setLight(1,Color.RED);
								evMyRed.sendEvent();//////send event to the controller
								inState=InState.RED;
								break;
							}
							else if (evShabat.arrivedEvent())
							{
								evShabat.waitEvent();
								setLight(1,Color.GRAY);
								setLight(3,Color.GRAY);
								out=true;
								initShabat();
								break;	
							}
							else
								yield();
						}
						break;

					}
				}

			case SHABAT:
				switch(inState)
				{
				case ORANGE:	
					evTime=new Event64();
					trigger=new MyTimer72(1000,evTime);
					while(true)
					{
						if(evTime.arrivedEvent())
						{
							evTime.waitEvent();
							setLight(2,Color.GRAY);
							inState=InState.GRAY;
							break;
						}
						else if (evHol.arrivedEvent())
						{
							evHol.waitEvent();
							setLight(2,Color.GRAY);
							out=false;
							initHol();
							break;	
						}
						else
							yield();
					}
					break;

				case GRAY:
					evTime=new Event64();
					trigger=new MyTimer72(1000,evTime);
					while(true)
					{
						if(evTime.arrivedEvent())
						{
							evTime.waitEvent();
							setLight(2,Color.ORANGE);
							inState=InState.ORANGE;
							break;
						}
						else if (evHol.arrivedEvent())
						{
							evHol.waitEvent();
							setLight(2,Color.GRAY);
							out=false;
							initHol();
							break;	
						}
						else
							yield();
					}
					break;


				}



			}

		}

	}

	public void setLight(int place, Color color)
	{
		ramzor.colorLight[place-1]=color;
		panel.repaint();
	}

	private void initHol()
	{
		count=0;
		setLight(1,Color.RED);
		inState=InState.RED;
		outState=OutState.HOL;
	}

	private void initShabat()
	{
		setLight(2,Color.ORANGE);
		inState=InState.ORANGE;
		outState=OutState.SHABAT;
	}
	
	public boolean isStop()
   	{
    		return true;
    }
	
}