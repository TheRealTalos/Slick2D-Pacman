package utils;

public class Timer {
	
	private double time;
	private double timeFraction;
	
	public Timer(){
		
	}
	
	public void init(){
		time = 0;
		timeFraction = 1;
		timeFraction = (timeFraction/60);
	}
	
	public void update(){
		time += timeFraction;
	}
	
	public double getTime(){
		return time;
	}
}
