
/*
 * Class for representing a City node
 */
public class City implements Comparable{

	private int x;
	private int y;
	private double angle; // angle between P(o) and the given city
	private boolean inHull; // whether or not the city is in the convex hull
	
	public City(int x, int y)
	{
		this.x = x;
		this.y = y;
		inHull = false;
	}
	
	
	// the following groups of methods are just very simple accessors
	// and mutators for all of the variables of the class
	
	public boolean inHull()
	{
		return inHull;
	}
	
	public void inHull(boolean b)
	{
		inHull = b;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setAngle(double d)
	{
		angle = d;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public boolean equals(City other)
	{

			if(x == other.getX())
			{
				if(y == other.getY())
				{
					return true;
				}
			}
		return false;
	}
	
	// finds the distances between two cities
	public double getDistance(City other)
	{
		// Could have done this all in one line but it was getting way too confusing looking
		double value = 0;;
		value += Math.pow((x-other.getX()), 2);
		value += Math.pow((y-other.getY()), 2);
		value = Math.sqrt(value);
		return value;
	}
	
	public String toString()
	{
		return ("(" + x + ", " + y + ")");
	}


	// compare the cities by their angle for the quicksort method
	public int compareTo(Object arg0) 
	{
		if(!(arg0 instanceof City)) return -1;
		else
		{
			if(((City)arg0).getAngle() > this.getAngle())
			{
				return -1;
			}
			if(((City)arg0).getAngle() < this.getAngle())
			{
				return 1;
			}
		}
		return 0;
	}
	
}
