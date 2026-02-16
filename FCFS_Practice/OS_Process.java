public class OS_Process {
	int id; // each process unique number
	int arrivalTime;	// time arrived
	int burstTime;	// time needed for CPU service
	int remainingTime; // needed for pre-emptive mode
	
	int waitingTime;
	
	public OS_Process(int id, int arrivalTime, int burstTime) {
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.remainingTime = burstTime;
	}	//end constructor
	
	// currentTime - the global time of the CPU when executing this process
	public void run(int currentTime) {
		System.out.println("Time: " + currentTime + " -- Process P" + id + " is running.");
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public String toString() {
		return "Process ID: " + this.id + " Arrival Time: " + this.arrivalTime;
	}
}	// end Process class