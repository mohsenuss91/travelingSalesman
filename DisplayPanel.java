import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;

public class DisplayPanel extends JPanel {
     private Map m; // the panel needs access to the methods of the Map
     private boolean points; // whether or not to represent the points on the panel
     private boolean hull; // whether or not to draw the hull
     private boolean tour; // whether or not to draw the tsp tour
     private boolean optimal; // whether or not to draw the optimal path
     private final static int DELAY = 1000;
     private Timer timer;
     private int checker = 0;
     private ArrayList<Vector> hEdges;
     private boolean highlight = false;
     private ArrayList<Vector> edges;

    public DisplayPanel(Map m, boolean points, boolean hull, boolean tour, boolean optimal) 
    {
    	this.m = m;
    	this.points = points;
    	this.hull = hull;
    	this.tour = tour;
    	this.optimal = optimal;
    	hEdges = new ArrayList<Vector>();
      setBackground(Color.white);
      setForeground(Color.black);
      timer = new Timer(DELAY, new 
    		  ActionListener()
      		{
    	  		public void actionPerformed(ActionEvent e)
    	  		{
    	  			findCross();
    	  			repaint();
    	  		}
      		});
      
    }
    
    
    private void findCross()
    {
    	if(highlight)
    	{
    		
    		highlight = false;
    		//edges.add(hEdges.get(0));
    		//edges.add(hEdges.get(1));
    		hEdges.clear();
    	}
    	else
    	{
    		for(int i = 0; i < edges.size(); i++)
    		{
    			for(int j = i+1; j < edges.size(); j++)
    			{
    				if(interset(edges.get(i), edges.get(j))) 
    				{
    					Vector e1 = edges.get(i);
    					Vector e2 = edges.get(j);
    					edges.remove(e1);
    					edges.remove(e2);
    					hEdges.add(new Vector(e1.getCity1(), e2.getCity2(), 0, 0));
    					hEdges.add(new Vector(e1.getCity2(), e2.getCity1(), 0, 0));
    				}
    			}
    		}
    		highlight = true;
    	}
    }
    
    private boolean interset(Vector v1, Vector v2)
    {
    	int x1 = v1.getCity1().getX();
    	int y1 = v1.getCity1().getY();
    	int x2 = v1.getCity2().getX();
    	int y2 = v1.getCity2().getY();
    	int x3 = v2.getCity1().getX();
    	int y3 = v2.getCity1().getY();
    	int x4 = v2.getCity2().getX();
    	int y4 = v2.getCity2().getY();
    	
    	
    	
    	/*if(x1==x2 && x3==x4)
    	{
    		if(x1==x3 && min(y1, y2) <= max(y3, y4) && min(y3, y4) <= max(y1, y2))
    		{
    			return true;
    		}
    		return false;
    	}
    	if(x3==x4)
    	{
    		if(min(x1, x2) <= x3 && x3 <= max(x1, x2) 
    				&& min(y3, y4) <= (x3*((y2-y1)/(x2-x1)) + (y1-x1*(y2-y1)/(x2-x1)))
    				&& ((x3*((y2-y1)/(x2-x1)) + (y1-x1*(y2-y1)/(x2-x1)))) <= max(y3, y4))
    				{
    					return true;
    				}
    		return false;
    	}
    	if(x1==x2)
    	{
    		if(min(x3, x4) <= x1 && x1 <= max(x3, x4) 
    				&& min(y1, y2) <= (x1*((y4-y3)/(x4-x3)) + (y3-x3*(y4-y3)/(x4-x3)))
    				&& ((x1*((y4-y3)/(x4-x3)) + (y3-x3*(y4-y3)/(x4-x3)))) <= max(y1, y2))
    				{
    					return true;
    				}
    		return false;
    	}
    	if(((y2-y1)/(x2-x1)) == ((y4-y3)/(x4-x3)))
    	{
    		if((y1-x1*((y2-y1)/(x2-x1))) == (y3-x3*((y4-y3)/(x4-x3)))
    				&& min(y1, y2) <= max(y3, y4) && min(y3, y4) <= max(y1, y2))
    		{
    			return true;
    		}
    		return false;
    	}
    	if(max(x3, x4) < min(x1, x2) || max(x1, x2) < min(x3, x4))
    	{
    		return false;
    	}
    	*/
    	return false;
    }
    
    private int min(int n, int m)
    {
    	if(n >= m) return m;
    	return n;
    }
    
    private int max(int n, int m)
    {
    	if(n >= m) return n;
    	return m;
    }
    
    protected void paintComponent(Graphics g) {
      g.setColor(getBackground()); //colors the window
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(getForeground()); //set color and fonts
      Font MyFont = new Font("SansSerif",Font.PLAIN,10);
      g.setFont(MyFont);
      MyFont = new Font("SansSerif",Font.BOLD,10); //bigger font for tree
      g.setFont(MyFont);
      this.drawPath(g); // draw the path
      revalidate(); //update the component panel
    }

   public void drawPath(Graphics g) 
   {
      int dx, dy, dx2, dy2;
      int SCREEN_WIDTH=500; //screen size for panel
      int SCREEN_HEIGHT=500;
      int DIAMETER = 6;
      int ys = 20; int xs = 10;
      int yss = 0; int xss = 0;
      Graphics2D g2 = (Graphics2D) g;
      
      if(points) 
      {
    	  		ArrayList<City> points = m.getCities();
    	  		for(int i = 0; i < points.size(); i++)
    	  		{
    	  			dx = points.get(i).getX();
    	  			dy = SCREEN_HEIGHT - points.get(i).getY();
    		  
    	  			Ellipse2D.Double city = new Ellipse2D.Double(dx-DIAMETER/2, dy-DIAMETER/2, DIAMETER, DIAMETER);
    	  			g2.setPaint(new Color(0, 0, 255));
    	  			g2.fill(city);
    	  		}
    	  		if(m.isRandom())
    	  		{
    	  			if(checker == 0) 
    	  			{ 
    	  				edges = m.randomPath();
    	  				checker++;
    	  			}
    	  			for(int i = 0; i < edges.size(); i++)
    	  			{
    	  				City c1 = edges.get(i).getCity1();
    	  				City c2 = edges.get(i).getCity2();
    	  				dx = c1.getX();
    	  				dy = SCREEN_HEIGHT-c1.getY();
    	  				dx2 = c2.getX();
    	  				dy2 = SCREEN_HEIGHT-c2.getY();
       		  
    	  				g2.setPaint(new Color(0, 150, 0));
    	  				g.drawLine(dx+xss, dy+yss, dx2+xss, dy2+yss);
    	  			}
    		
    	  			for(int j = 0; j < hEdges.size(); j++)
    	  			{
    	  				City c1 = hEdges.get(j).getCity1();
    	  				City c2 = hEdges.get(j).getCity2();
    	  				dx = c1.getX();
    	  				dy = SCREEN_HEIGHT-c1.getY();
    	  				dx2 = c2.getX();
    	  				dy2 = SCREEN_HEIGHT-c2.getY();
    	  				g2.setPaint(new Color(255, 255, 0));
    	  				g.drawLine(dx+xss, dy+yss, dx2+xss, dy2+yss);
    	  			}
    	  			xss+=2; yss+=2;
    		 
    	  			timer.start();
    	  		}	  			
      }
    	  
      if(hull)
      {
    	  LinkedList<City> stack = m.convexHull();
    	  City current = stack.poll();
    	  City first = current;
    	  while(!stack.isEmpty())
    	  {
    		  dx = current.getX();
    		  dy = SCREEN_HEIGHT- current.getY();
    		  City next = stack.poll();
    		  dx2 = next.getX();
    		  dy2 = SCREEN_HEIGHT - next.getY();
    		  g2.setPaint(new Color(255, 0, 0));
    		  g.drawLine(dx+xss, dy+yss, dx2+xss, dy2+yss);
    		  
    		  current = next;
    	  }
    	  // do the same thing but for the last one
    	  dx = current.getX();
    	  dy = SCREEN_HEIGHT - current.getY();
    	  dx2 = first.getX();
    	  dy2 = SCREEN_HEIGHT - first.getY();
    	  g.drawLine(dx+xss, dy+yss, dx2+xss, dy2+yss);
 		 g.drawString("Convex Hull", xs, ys);
 		 ys += 10; xss+=1; yss+=1; // change so that we don't draw the lines on top of one another

      }
      
      if(tour)
      {
    	  LinkedList<Vector> edges2 = m.tspTour();
    	  for(int i = 0; i < edges2.size(); i++)
    	  {
    		  City c1 = edges2.get(i).getCity1();
    		  City c2 = edges2.get(i).getCity2();
    		  
    		  dx = c1.getX();
    		  dy = SCREEN_HEIGHT-c1.getY();
    		  dx2 = c2.getX();
    		  dy2 = SCREEN_HEIGHT-c2.getY();
    		  
    		  g2.setPaint(new Color(0, 150, 0));
    		  g.drawLine(dx+xss, dy+yss, dx2+xss, dy2+yss);
    	  }
    	  
    	  // find the distance just so we can print it out
    	  double distance = 0;
    	  for(int j = 0; j < edges2.size(); j++)
    	  {
    		  distance += edges2.get(j).length();
    	  }
    	  g.drawString("TSP Tour: " + distance, xs, ys);
		  ys += 10; xss += 1; yss += 1;
      }
      
      if(optimal)
      {
    	  ArrayList<City> cities = m.getCities();
    	  String tour = m.optimalTour();
    	  String[] cs = tour.split("");
    	  for(int i = 1; i < cs.length; i++)
    	  {
    		  City c1 = cities.get(Integer.parseInt(cs[i]));
    		  City c2;
    		  if(i+1 == cs.length) 
					c2 = cities.get(Integer.parseInt(cs[1]));
    		  else	
					c2 = cities.get(Integer.parseInt(cs[i+1]));
    		  
    		  dx = c1.getX();
    		  dy = SCREEN_HEIGHT-c1.getY();
    		  dx2 = c2.getX();
    		  dy2 = SCREEN_HEIGHT-c2.getY();
    		  
    		  g2.setPaint(new Color(128, 0, 128));
    		  g.drawLine(dx+xss, dy+yss, dx2+xss, dy2+yss);
    		  
    		  
    	  }
		  g.drawString("Optimal Path: " + m.getBest(), xs, ys);
		  ys += 10; xss+=1; yss+=1;
  
      }

   }
}

