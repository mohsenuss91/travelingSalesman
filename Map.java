import java.util.*;

// class for representing the group of cities on the panel
public class Map {

	protected ArrayList<City> cities; // all the cities on the map
	private LinkedList<City> stack; // just the cities in the hull
	private ArrayList<String> paths;
	private double bestDistance;
	private String bestPath;
	protected int total;
	
	public Map(int n)
	{
		total = n;
		cities = new ArrayList<City>();
		bestDistance = 	Integer.MAX_VALUE;
		bestPath = "";
		findCities(); // randomly chooses x and y values
		setAngles(); // finds the angles of all the cities in relation to p(o)
	}
	
	// randomly chooses numbers between 0 and 500 
	// for each x and y value for as many cities 
	// as the user specifies
	private void findCities()
	{
		for(int i = 0; i < total; i++)
		{
			int x = (int) (Math.random()*501); // will give a number between 0 and 500
			int y = (int) (Math.random()*501); // realize that this is technically 501 numbers
			City next = new City(x, y);
			cities.add(next);
		}
	}
	
	// method for finding the cities contained in the convex hull
	public LinkedList<City> convexHull()
	{	
		City[] poPn = new City[cities.size()]; // the cities in order from p(o) to p(n-1)
		for(int i = 0; i < poPn.length; i++)
		{
			poPn[i] = cities.get(i); 
		}
		quicksort(poPn, 0, poPn.length-1); // sort the cities by angle
		
		stack = new LinkedList<City>();
		stack.addFirst(poPn[(poPn.length)-1]);
		stack.addFirst(poPn[0]);
		
		int i = 1;
		while(i < poPn.length)
		{
			City entry1 = stack.poll();
			City entry2 = stack.poll();
			Vector v1 = new Vector(entry1, entry2, entry1.getX()-entry2.getX(), entry1.getY() - entry2.getY());
			Vector v2 = new Vector(poPn[i], entry2, poPn[i].getX()-entry2.getX(), poPn[i].getY()-entry2.getY());
			
			stack.addFirst(entry2); // add them back to the stack
			stack.addFirst(entry1);
			
			// checks the cross product and changes the stack accordingly
			
			if(v1.cross(v2, v1) <= 0)
			{
				stack.addFirst(poPn[i]);
				i++;
			}
			else if(v1.cross(v2, v1) > 0)
			{
				stack.removeFirst();
			}
		}
		
		return stack;
	}

	// quicksort to use on the array
	private void quicksort(City[] city, int left, int right)
	{
		int i = left;
		int j = right;
		City[] c = city;
		City temp;
		int x = (int)c[left].getAngle();
		do
		{
			while((int)c[i].getAngle() < x)
			{
				i++;
			}
			while((int)c[j].getAngle() > x)
			{
				j--;
			}
			if(i <= j)
			{
				temp = c[i];
				c[i] = c[j];
				c[j] = temp;
				i++; 
				j--;
			}
		}
		while(i <= j);
		
		// recursively sorts the entire array
		if(left < j)
		{
			quicksort(c, left, j);
		}
		if(i < right)
		{
			quicksort(c, i, right);
		}
	}
	
	// sets all of the angles for all of the cities in the map
	private void setAngles()
	{
		City po = getPo(); // find the bottom right corner
		for(int i = 0; i < cities.size(); i++)
		{
			if(!cities.get(i).equals(po))
			{
				double d1 = cities.get(i).getDistance(po);
				double d2 = 0;
				double angle = 0;
				
				d1 = cities.get(i).getDistance(po);
				d2 = cities.get(i).getDistance(new City(cities.get(i).getX(), po.getY()));
				angle = Math.asin(d2/d1);
				angle = angle*(180/Math.PI);
				
				if(cities.get(i).getX() < po.getX()) // meaning the city is to the left of Po
				{
					// need to adjust all of the angles depending
					// on what quadrant it is in
					if(cities.get(i).getY() > po.getY()) // below it
					{
						angle = 90-angle;
						angle = angle+90;

					}
					if(cities.get(i).getY() < po.getY()) // above it
					{
						angle += 180;

					}
				}
				
				cities.get(i).setAngle(angle);
			}
			else
			{
				cities.get(i).setAngle(0); // the reference city
			}
		}		
	}
	
	// finds the city that is in the bottom right hand corner
	private City getPo()
	{
		City corner = new City(500, 0);
		City po = new City(0, 0); // dummy city
		for(int i = 0; i < cities.size(); i++)
		{
			if(cities.get(i).getDistance(corner) < po.getDistance(corner))
			{
				po = cities.get(i);
			}
		}
		return po;
	}
	
	// method for finding the tsp tour
	public LinkedList<Vector> tspTour()
	{
		for(int i = 0; i < cities.size(); i++)
		{
			cities.get(i).inHull(false);
		}
		LinkedList<City> stack2 = convexHull();
		stack2.removeLast(); // convex hull has a single vertice twice
		LinkedList<Vector> edges = new LinkedList<Vector>();
		for(int i = 0; i < stack2.size(); i++)
		{
			City current = stack2.get(i);
			City next;
			if(i+1 == stack2.size()) next = stack2.get(0);
			else next = stack2.get(i+1);
			edges.add(new Vector(current, next, 0, 0));
			current.inHull(true);
			next.inHull(true);
		}
		while(!done()) // not all of the cities are in the tour
		{	
			double lilMin = Integer.MAX_VALUE;
			City switch1 = null;
			Vector switch2 = null;
			
			for(int i = 0; i < cities.size(); i++) // for each city not in the hull
			{
				if(!cities.get(i).inHull())
				{
					for(int j = 0; j < edges.size(); j++) // check all the vectors in the hull
					{
						if(cities.get(i).getDistance(edges.get(j).getCity1()) + 
								cities.get(i).getDistance(edges.get(j).getCity2()) < lilMin)
						{
							lilMin = cities.get(i).getDistance(edges.get(j).getCity1()) + 
								cities.get(i).getDistance(edges.get(j).getCity2());
							switch1 = cities.get(i);
							switch2 = edges.get(j);
						}
					}
				}
			}
			
			stack2.add(switch1); switch1.inHull(true);
			edges.remove(switch2);
			edges.add(new Vector(switch2.getCity1(), switch1, 0, 0));
			edges.add(new Vector(switch2.getCity2(), switch1, 0, 0));
			
		}
		
		return edges;
		
	}
	
	// returns a boolean that decides whether ot not we are done finding
	// the tsp path
	// just a helper method for tspTour method
	protected boolean done()
	{
		for(int i = 0; i < cities.size(); i++)
		{
			if(!cities.get(i).inHull())
			{
				return false;
			}
		}
		return true;
	}
	
	// finds the optimal tour by calculating all of the possible paths
	// and picking the smallest one
	public String optimalTour()
	{
		paths = new ArrayList<String>();
		String s = "";
		for(int i = 0; i < cities.size(); i++)
		{
			s+= i;
		}
		permute("", s); // finds all possible combinations of cities
		
		for(int j = 0; j < paths.size(); j++)
		{
			String[] tour = paths.get(j).split("");
			double distance = 0;
			for(int k = 1; k < tour.length; k++) // tour[0] is blank
			{
				City city1 = cities.get(Integer.parseInt(tour[k]));
				City city2;
				if(k+1 == tour.length) 
					city2 = cities.get(Integer.parseInt(tour[1]));
				else	
					city2 = cities.get(Integer.parseInt(tour[k+1]));
				distance += city1.getDistance(city2);
			}
			
			if(distance <= bestDistance) // checks against the current smallest
			{
				bestDistance = distance;
				bestPath = paths.get(j);
			}
		}
		return bestPath;
	}
	
	// returns the best distance as was calcuated by the optimal tour method
	public double getBest()
	{
		return bestDistance;
	}
	
	// helper method for finding all of the possible permutations of
	// cities that could make up a path
	private void permute(String prefix, String postfix)
	{
		// need to use a String to find these permutations so that we can
		// recursively run through and modify it
		if(postfix.length() <= 1)
		{
			paths.add(prefix+postfix);
		}
		else
		{
			for(int i = 0; i < postfix.length(); i++)
			{
				String s = postfix.substring(0, i) + postfix.substring(i + 1);
				permute(prefix+postfix.charAt(i), s);
			}
		}
	}
	
	public int getN()
	{
		return total;
	}
	
	// just used as a testing method, no purpose in finished program
	public void printCities()
	{
		for(int i = 0; i < cities.size(); i++)
		{
			System.out.println(cities.get(i));
		}
	}
	
	public void setEdges(ArrayList<Vector> e)
	{
		// empty
	}
	
	// returns all of the cities in the map
	public ArrayList<City> getCities()
	{
		return cities;
	}
	
	public boolean isRandom()
	{
		return false;
	}
	
	public ArrayList<Vector> randomPath()
	{
		return new ArrayList<Vector>();
	}
	
}
