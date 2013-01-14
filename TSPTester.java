
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

// main method that creates the GUI and displays everything to the user
public class TSPTester
{
	private final static int FRAME_WIDTH = 725;
	private final static int FRAME_HEIGHT = 575;
	private static Map map;
	
   public static void main(String[] args)
   {   
      final JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
      // all of the buttons are set to disabled until we 
      // until we get points onto the screen
      final JTextField textField = new JTextField("Enter a number");  
      final JButton pButton = new JButton("Draw Points");
      final JButton cButton = new JButton("Draw Convex Hull");
      cButton.setEnabled(false);
      final JButton tButton = new JButton("Draw TSP Tour");
      tButton.setEnabled(false);
      final JButton oButton = new JButton("Draw Optimal Tour");
      oButton.setEnabled(false);
      final JButton rButton = new JButton("Draw Path & 2-Opt");
      final JButton qButton = new JButton("Quit");
      
      // panel of buttons
      JPanel buttons = new JPanel(new GridLayout(7, 1));
      buttons.add(qButton, 6, 0);
      buttons.add(rButton, 5, 0);
      buttons.add(oButton, 4, 0);
      buttons.add(tButton, 3, 0);
      buttons.add(cButton, 2, 0);
      buttons.add(pButton, 1, 0);
      buttons.add(textField, 0, 0);
      
      // inner panel used just so that we can determine where the items go within
      // the frame
      final JPanel area = new JPanel(new BorderLayout());
      area.add(buttons, BorderLayout.WEST);
      
      pButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {        
            	String text = textField.getText();
            	if(isNumber(text))
            	{
            		map = new Map(Integer.parseInt(textField.getText()));
            		DisplayPanel panel = new DisplayPanel(map, true, false, false, false);
            		area.add(panel);
            		frame.setVisible(true);
            		
            		cButton.setEnabled(true);
            	    tButton.setEnabled(true);
            	    oButton.setEnabled(true);
            	    if(map.getN() > 9) oButton.setEnabled(false);
            	}
            }
         });
      
      cButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
            	DisplayPanel panel = new DisplayPanel(map, true, true, false, false);          
            	area.add(panel);
            	frame.setVisible(true);
            	tButton.setEnabled(true);            	
            }
         });

      
      tButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
            	DisplayPanel panel= new DisplayPanel(map, true, true, true, false);
            	area.add(panel);
            	frame.setVisible(true);
            
            }
         });

     
      
      oButton.addActionListener(new
    		  ActionListener()
      		  {
    	  		public void actionPerformed(ActionEvent event)
    	  		{
    	  			DisplayPanel panel = new DisplayPanel(map, true, true, true, true);
                	
                	area.add(panel);
                	frame.setVisible(true);
    	  			
    	  		}
      });
      
      // r button is the button for generating the animation
      rButton.addActionListener(new
    		  ActionListener()
      		  {
    	  		public void actionPerformed(ActionEvent event)
    	  		{
    	  			JFrame popup = new JFrame();
    	  			popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  			String text = textField.getText();
                	if(isNumber(text))
                	{
                		map = new RandomMap(Integer.parseInt(textField.getText()));
                		DisplayPanel panel = new DisplayPanel(map, true, false, false, false);
                		area.add(panel);
                		frame.setVisible(true);
                	}
    	  			
    	  		}
      });
      
      qButton.addActionListener(new
    		  ActionListener()
      		  {
    	  		public void actionPerformed(ActionEvent event)
    	  		{
    	  			frame.dispose();
    	  		}
      });
      
      
      frame.add(area);
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setVisible(true);
   }
   
   private static boolean isNumber(String text)
   {
	   try
	   {
		  int n = Integer.parseInt(text); 
	   }
	   catch(NumberFormatException nfe)
	   {
		   return false;
	   }
	   return true;
   }
   
}


