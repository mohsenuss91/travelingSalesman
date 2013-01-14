
// a class for representing a vector, or the edge between two cities
public class Vector {

	private City city1; // the city at one end of the edge
	private City city2;  // the city at the other end of the edge
	private int dx; // these two values derive the slope of the vector
	private int dy;
	
	// need to get the two cities on the ends of the edge
	public Vector(City city1, City city2, int dx, int dy)
	{
		this.city1 = city1;
		this.city2 = city2;
		this.dx = dx;
		this.dy = dy;
	}
	
	public City getCity1()
	{
		return city1;
	}
	
	public City getCity2()
	{
		return city2;
	}
	
	public int getDX()
	{
		return dx;
	}
	
	public int getDY()
	{
		return dy;
	}
	
	public double length()
	{
		return city1.getDistance(city2);
	}
	
	// finds the cross product of the two given vectors
	public int cross(Vector v1, Vector v2)
	{
		int dzz = (v1.getDX()*v2.getDY()-v1.getDY()*v2.getDX()); // only part that matters
		return dzz;
	}
	
	// the string that represents the vector is the toStrings of the citys at its end
	public String toString()
	{
		String s = "";
		s += city1 + " & " + city2;
		return s;
	}
	
	public boolean equals(Vector other)
	{
		if(this.city1.equals(other.city1) && this.city2.equals(other.city2))
			return true;
		return false;
	}
}
