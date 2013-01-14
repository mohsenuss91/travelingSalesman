import java.awt.Graphics;
import java.util.*;

public class RandomMap extends Map{

	private ArrayList<Vector> edges;
	Graphics g;
	
	public RandomMap(int n)
	{
		super(n);
		edges = new ArrayList<Vector>();
	}
	
	public boolean isRandom()
	{
		return true;
	}
	
	public void setEdges(ArrayList<Vector> e)
	{
		edges = e;
	}
	
	public ArrayList<Vector> randomPath()
	{
		shuffle(cities);
		City start = cities.get(0);
		City iterator = start;
		City last = cities.get(1);

		edges.add(new Vector(last, start, 0, 9));
		last.inHull(true);
		start.inHull(true);
		int next = (int)(Math.random()*total);

		while(!super.done())
		{
			next = (int) (Math.random()*total);

			while(contains(cities.get(next)))
			{
				next = (int) (Math.random()*total);
			}
			while(cities.get(next).inHull())
			{
				next = (int) (Math.random()*total);
			}
			edges.add(new Vector(iterator, cities.get(next), 0, 0));
			cities.get(next).inHull(true);
			iterator = cities.get(next);
		}
		edges.add(new Vector(iterator, cities.get(1), 0, 0));

		
		return edges;
	}
	
	// the cities arraylist is arranged in order or angle so if we do the method
	// without shuffling them, most of the time with small numbers we just end up 
	// with a convex hull by chance
	// so we shuffle them around in the arraylist to try to randomize it a little more
	private void shuffle(ArrayList<City> c)
	{
		for(int i = 0; i < 20; i++)
		{
			int num1 = (int)(Math.random() * total);
			int num2 = (int)(Math.random() * total);
			City temp = cities.get(num1);
			cities.set(num1, cities.get(num2));
			cities.set(num2, temp);
		}
	}
	
	private boolean contains(City c)
	{
		for(int i = 0; i < edges.size(); i++)
		{
			if(edges.get(i).equals(c))
			{
				return true;
			}
		}
		return false;
	}
	
}
