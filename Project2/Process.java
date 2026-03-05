 // uncomment the following line if using scheduling package
 // package scheduling;

public class Process {
	int id; //each process unique number
	int arrivalTime; //time arrived
	int burstTime; //time needed for CPU service
	int remainingTime; //Needed for preemptive mode
	int timeInQ0; //Needed for Multilevel Feedback Queue scheduling
	int timeInQ1; //Needed for Multilevel Feedback Queue scheduling
	
	int waitingTime;
	boolean finished = false; //mark it as not done
	
	public Process(int id, int arrivalTime, int burstTime)
	{
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.remainingTime = burstTime;
		this.timeInQ1 = 0;
		this.timeInQ0 = 0;
	}
	
	//currentTime - the global time of the CPU when executing this process 
	public void run(int currentTime) {
		
		System.out.println("Time " + currentTime + ": Process P"
				+ id + " is running...");
		
	}
	
	
	
	
	
	
	
	
	

}
