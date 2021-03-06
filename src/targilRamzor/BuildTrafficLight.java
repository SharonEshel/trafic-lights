package targilRamzor;
import javax.swing.JRadioButton;

/*
 * Created on Mimuna 5767  upDate on Addar 5772 
 */

/**
 * @author �����
 */
public class BuildTrafficLight
{

	public static void main(String[] args) 
	{
		final int numOfLights=4+12+1;
		Ramzor ramzorim[]=new Ramzor[numOfLights];
		ramzorim[0]=new Ramzor(3,40,430,110,472,110,514,110);
		ramzorim[1]=new Ramzor(3,40,450,310,450,352,450,394);
		ramzorim[2]=new Ramzor(3,40,310,630,280,605,250,580);
		ramzorim[3]=new Ramzor(3,40,350,350,308,350,266,350);

		ramzorim[4]=new Ramzor(2,20,600,18,600,40);
		ramzorim[5]=new Ramzor(2,20,600,227,600,205);
		ramzorim[6]=new Ramzor(2,20,600,255,600,277);
		ramzorim[7]=new Ramzor(2,20,600,455,600,433);
		ramzorim[8]=new Ramzor(2,20,575,475,553,475);
		ramzorim[9]=new Ramzor(2,20,140,608,150,590);
		ramzorim[10]=new Ramzor(2,20,205,475,193,490);
		ramzorim[11]=new Ramzor(2,20,230,475,250,475);
		ramzorim[12]=new Ramzor(2,20,200,453,200,433);
		ramzorim[13]=new Ramzor(2,20,200,255,200,277);
		ramzorim[14]=new Ramzor(2,20,200,227,200,205);
		ramzorim[15]=new Ramzor(2,20,200,18,200,40);

		ramzorim[16]=new Ramzor(1,30,555,645);

		TrafficLightFrame tlf=new TrafficLightFrame(" ���''� installation of traffic lights",ramzorim);

		
		Event64[] evGreen= new Event64[16];
		Event64[] evRed= new Event64[16];
		Event64[] evShabat= new Event64[16];
		Event64[] evHol= new Event64[16];
		Event64[] evMyRed= new Event64[16];
		for(int i=0; i<16; i++)
		{
			evRed[i]=new Event64();
			evGreen[i]=new Event64();
			evShabat[i]=new Event64();
			evHol[i]=new Event64();
			evMyRed[i]=new Event64();
		}
		
		new ShloshaAvot(ramzorim[0],tlf.myPanel,1,evGreen[0],evRed[0],evShabat[0],evHol[0],evMyRed[0]);
		new ShloshaAvot(ramzorim[1],tlf.myPanel,2,evGreen[1],evRed[1],evShabat[1],evHol[1],evMyRed[1]);
		new ShloshaAvot(ramzorim[2],tlf.myPanel,3,evGreen[2],evRed[2],evShabat[2],evHol[2],evMyRed[2]);
		new ShloshaAvot(ramzorim[3],tlf.myPanel,4,evGreen[3],evRed[3],evShabat[3],evHol[3],evMyRed[3]);

		
		for(int i=4; i<16;i++)
		{
			new ShneyLuchot(ramzorim[i],tlf.myPanel,evGreen[i],evRed[i],evShabat[i],evHol[i],evMyRed[i]);
		}
		new Echad(ramzorim[16],tlf.myPanel);

		
		
		Event64 evPressShabat= new Event64();
		Event64 evPress= new Event64();
		
		MyActionListener myListener=new MyActionListener(evPress,evPressShabat);

		JRadioButton butt[]=new JRadioButton[13]; 

		for (int i=0;i<butt.length-1;i++) 
		{
			butt[i]  =new JRadioButton();
			butt[i].setName(Integer.toString(i+4));
			butt[i].setOpaque(false);
			butt[i].addActionListener(myListener);
			tlf.myPanel.add(butt[i]);
		}
		butt[0].setBounds(620, 30, 18, 18);
		butt[1].setBounds(620, 218, 18, 18);
		butt[2].setBounds(620, 267, 18, 18);
		butt[3].setBounds(620, 447, 18, 18);
		butt[4].setBounds(566, 495, 18, 18);
		butt[5].setBounds(162,608, 18, 18);
		butt[6].setBounds(213,495, 18, 18);
		butt[7].setBounds(240,457, 18, 18);
		butt[8].setBounds(220,443, 18, 18);
		butt[9].setBounds(220,267, 18, 18);
		butt[10].setBounds(220,218, 18, 18);
		butt[11].setBounds(220,30, 18, 18);

		butt[12]  =new JRadioButton();
		butt[12].setName(Integer.toString(16));
		butt[12].setBounds(50,30, 55, 20);
		butt[12].setText("���");
		butt[12].setOpaque(false);
		butt[12].addActionListener(myListener);
		tlf.myPanel.add(butt[12]);
		
		
		Controller controller= new Controller(evGreen,evRed, evShabat, evHol, evMyRed,evPressShabat,evPress);
	}
}
