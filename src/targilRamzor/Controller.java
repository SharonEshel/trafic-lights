package targilRamzor;
import java.awt.Color;
import javax.swing.JPanel;

import targilRamzor.ShloshaAvot.InState;
import targilRamzor.ShloshaAvot.OutState;

public class Controller extends Thread
{

	String data;
	private Event64[] evGreen,evRed,evShabat,evHol,evMyRed;
	Event64 evPressShabat,evPress;
	enum OutState{HOL,SHABAT};
	OutState outState;
	enum InState{GROUP_A_ON,GROUP_A_OFF,GROUP_B_ON,GROUP_B_OFF,GROUP_C_ON,GROUP_C_OFF,
		PRESS_A_TO_C,PRESS_B_TO_A,WAITING_B_TO_A,WAITING_C_TO_A,WAITING_A_TO_C,
		WAITING_B_TO_C,WAITING_A_TO_B};
		InState inState;

		public Controller( Event64[] evGreen,Event64[] evRed,Event64[] evShabat,
				Event64[] evHol,Event64[] evMyRed,Event64 evPressShabat,Event64 evPress)
		{
			this.evGreen=evGreen;
			this.evRed=evRed;
			this.evShabat=evShabat;
			this.evHol=evHol;
			this.evMyRed=evMyRed;
			this.evPressShabat=evPressShabat;
			this.evPress=evPress;
			start();
		}

		public void run()
		{
			Event64 evTime;
			MyTimer72 trigger;
			boolean finish=false;
			boolean out=false;
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
						case GROUP_A_ON:
							evTime=new Event64();
							trigger=new MyTimer72(5000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evRed[0].sendEvent();
									evRed[9].sendEvent();
									evRed[10].sendEvent();
									inState=InState.GROUP_A_OFF;
									break;
								}

								else if (evPress.arrivedEvent())
								{
									data= (String) evPress.waitEvent();
									if(data.equals("8") || data.equals("11") || data.equals("14") || data.equals("15"))
									{
										
										evRed[0].sendEvent();
										evRed[6].sendEvent();
										evRed[7].sendEvent();
										evRed[9].sendEvent();
										evRed[10].sendEvent();
										evRed[12].sendEvent();
										evRed[13].sendEvent();
										inState=InState.PRESS_A_TO_C;
										break;
									}
									
								}

								else
									yield();
							}
							break;
							
							
						case GROUP_A_OFF:
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evMyRed[0].arrivedEvent() && evMyRed[9].arrivedEvent() 
										&& evMyRed[10].arrivedEvent())
								{
									evMyRed[0].waitEvent();
									evMyRed[9].waitEvent();
									evMyRed[10].waitEvent();
									inState=InState.WAITING_A_TO_B;
									break;
								}

								else
									yield();
							}
							break;

						   
						case WAITING_A_TO_B:
							evTime=new Event64();
							trigger=new MyTimer72(1000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evGreen[1].sendEvent();
									evGreen[4].sendEvent();
									evGreen[5].sendEvent();
									evGreen[2].sendEvent();
									inState=InState.GROUP_B_ON;
									break;
								}

								else
									yield();
							}
							break;
							
						
						case GROUP_B_ON:
							evTime=new Event64();
							trigger=new MyTimer72(5000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evRed[1].sendEvent();
									evRed[6].sendEvent();
									evRed[7].sendEvent();
									evRed[12].sendEvent();
									evRed[13].sendEvent();
									inState=InState.GROUP_B_OFF;
									break;
								}

								else if (evPress.arrivedEvent()) 
										
								{
									data=(String) evPress.waitEvent();
									if(data.equals("9") || data.equals("10"))
									{
										
										evRed[1].sendEvent();
										evRed[2].sendEvent();
										evRed[4].sendEvent();
										evRed[5].sendEvent();
										inState=InState.PRESS_B_TO_A;
										break;
									}
									
								}

								else
									yield();
							}
							break;
						
							
						case GROUP_B_OFF:
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evMyRed[1].arrivedEvent() && evMyRed[7].arrivedEvent() 
										&& evMyRed[6].arrivedEvent() && evMyRed[13].arrivedEvent() 
										&& evMyRed[12].arrivedEvent())
								{
									evMyRed[1].waitEvent();
									evMyRed[7].waitEvent();
									evMyRed[6].waitEvent();
									evMyRed[13].waitEvent();
									evMyRed[12].waitEvent();
									inState=InState.WAITING_B_TO_C;
									break;
								}

								else
									yield();
							}
							break;

						
						
					
						case WAITING_B_TO_C:
							evTime=new Event64();
							trigger=new MyTimer72(1000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evGreen[3].sendEvent();
									evGreen[15].sendEvent();
									evGreen[14].sendEvent();
									evGreen[8].sendEvent();
									evGreen[11].sendEvent();
									inState=InState.GROUP_C_ON;
									break;
								}

								else
									yield();
							}
							break;
						
						
						case GROUP_C_ON:
							evTime=new Event64();
							trigger=new MyTimer72(5000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evRed[2].sendEvent();
									evRed[3].sendEvent();
									evRed[8].sendEvent();
									evRed[5].sendEvent();
									evRed[4].sendEvent();
									evRed[11].sendEvent();
									evRed[15].sendEvent();
									evRed[14].sendEvent();
									inState=InState.GROUP_C_OFF;
									break;
								}

								else
									yield();
							}
							break;
							
							
						case GROUP_C_OFF:
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evMyRed[2].arrivedEvent() && evMyRed[3].arrivedEvent() 
										&& evMyRed[5].arrivedEvent() && evMyRed[4].arrivedEvent() 
										&& evMyRed[15].arrivedEvent() && evMyRed[14].arrivedEvent() 
										&& evMyRed[8].arrivedEvent() && evMyRed[11].arrivedEvent())
								{
									evMyRed[2].waitEvent();
									evMyRed[3].waitEvent();
									evMyRed[5].waitEvent();
									evMyRed[4].waitEvent();
									evMyRed[15].waitEvent();
									evMyRed[14].waitEvent();
									evMyRed[8].waitEvent();
									evMyRed[11].waitEvent();
									inState=InState.WAITING_C_TO_A;
									break;
								}

								else
									yield();
							}
							break;

						
						case WAITING_C_TO_A:
							evTime=new Event64();
							trigger=new MyTimer72(1000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evGreen[0].sendEvent();
									evGreen[6].sendEvent();
									evGreen[13].sendEvent();
									evGreen[7].sendEvent();
									evGreen[12].sendEvent();
									evGreen[10].sendEvent();
									evGreen[9].sendEvent();
									inState=InState.GROUP_A_ON;
									break;
								}

								else
									yield();
							}
							break;
							
						case PRESS_B_TO_A:
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evMyRed[2].arrivedEvent() && evMyRed[1].arrivedEvent() 
										&& evMyRed[5].arrivedEvent() && evMyRed[4].arrivedEvent())
								{
									evMyRed[2].waitEvent();
									evMyRed[5].waitEvent();
									evMyRed[4].waitEvent();
									evMyRed[1].waitEvent();
									inState=InState.WAITING_B_TO_A;
									break;
								}

								else
									yield();
							}
							break;
							
							
						case WAITING_B_TO_A:
							evTime=new Event64();
							trigger=new MyTimer72(1000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evGreen[0].sendEvent();
									evGreen[9].sendEvent();
									evGreen[10].sendEvent();
									inState=InState.GROUP_A_ON;
									break;
								}

								else
									yield();
							}
							break;

						
						
						case PRESS_A_TO_C:
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evMyRed[0].arrivedEvent() && evMyRed[6].arrivedEvent() 
										&& evMyRed[7].arrivedEvent() && evMyRed[9].arrivedEvent()
										&& evMyRed[10].arrivedEvent() && evMyRed[12].arrivedEvent()
										&& evMyRed[13].arrivedEvent())
								{
									evMyRed[0].waitEvent();
									evMyRed[6].waitEvent();
									evMyRed[7].waitEvent();
									evMyRed[9].waitEvent();
									evMyRed[10].waitEvent();
									evMyRed[12].waitEvent();
									evMyRed[13].waitEvent();
									inState=InState.WAITING_A_TO_C;
									break;
								}

								else
									yield();
							}
							break;
						
						
						case WAITING_A_TO_C:
							evTime=new Event64();
							trigger=new MyTimer72(1000,evTime);
							while(true)
							{
								if (evPressShabat.arrivedEvent())
								{
									evPressShabat.waitEvent();
									sendAllShabat();
									out=true;
									outState=OutState.SHABAT;
									break;	
								}

								else if (evTime.arrivedEvent())
								{
									evTime.waitEvent();
									evGreen[2].sendEvent();
									evGreen[3].sendEvent();
									evGreen[4].sendEvent();
									evGreen[5].sendEvent();
									evGreen[8].sendEvent();
									evGreen[11].sendEvent();
									evGreen[14].sendEvent();
									evGreen[15].sendEvent();
									inState=InState.GROUP_C_ON;
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
					evPressShabat.waitEvent();
					sendAllHol();
					out=false;
					initHol();
					break;	
				}
			}
		}



		private void initHol()
		{
			evGreen[9].sendEvent();
			evGreen[0].sendEvent();
			evGreen[7].sendEvent();
			evGreen[6].sendEvent();
			evGreen[12].sendEvent();
			evGreen[13].sendEvent();
			evGreen[10].sendEvent();
			inState=InState.GROUP_A_ON;
			outState=OutState.HOL;
			/*try {
				sleep(2000);
			} catch (InterruptedException e) {}*/
		}

		private void sendAllShabat()
		{
			for(int i=0;i<evShabat.length;i++)
			{
				evShabat[i].sendEvent();
			}
		}
		
		private void sendAllHol()
		{
			for(int i=0;i<evHol.length;i++)
			{
				evHol[i].sendEvent();
			}
		}
}

