package supporting;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class GreedyAlgorithm implements SearchPath{
	private static Point goal;
	private Point start;
	private PriorityQueue<Point> frontier;
	private HashMap<Point, Point> comeFrom;
	private ArrayDeque<Point> result;
	private int steps;
	
	public GreedyAlgorithm(Graph gr,Point start,Point goal) {
		this.goal = goal;
		this.start = start;
		frontier = new PriorityQueue<Point>(PointComparator);
		frontier.add(start);
		comeFrom = new HashMap<Point, Point>(gr.V());  
		result = new ArrayDeque<Point>(gr.V());
		comeFrom.put(start,null);
		greed(gr);
		HashtoQueue();
	}
	
	private void greed(Graph gr) {
		Point current;
		while (!frontier.isEmpty()) {
			   current = frontier.poll();
			   if (current.x== goal.x && current.y == goal.y)break;
			   for(Point next : gr.adj(current)) {
				   if(!comeFrom.containsKey(next)) {
					   frontier.add(next);
					   comeFrom.put(next,current);
					   steps++;
				   }
			   }
		}
	}
	public static Comparator<Point> PointComparator = new Comparator<Point>(){
        
		@Override
		public int compare(Point a, Point b) {
			return heuristic(goal,a)-heuristic(goal,b);
		}
    };
	private void HashtoQueue() {
		Point cur= goal;
		result.addFirst(goal);
		while((cur.x != start.x || cur.y!=start.y) && comeFrom.containsKey(cur)) {
			result.addFirst(comeFrom.get(cur));
			cur=comeFrom.get(cur);
		}
	}
	
	private static int heuristic(Point a,Point b) {
		return Math.abs(a.x-b.x)+Math.abs(a.y-b.y);
	}

	@Override
	public Point getNextVisualPoint() {
		return result.poll();
	}

	@Override
	public int getCountStepsToFind() {
		return result.size();
	}

	@Override
	public int getCountStepsFromStartToFinish() {
		return steps;
	}
}
