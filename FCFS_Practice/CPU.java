import java.util.List;
import java.util.ArrayList;
import java.util.Comparator; // used to sort ArrayList by arrival time

public class CPU {
	int time = 0; // the parameter to pass to each process
	
	public void fcfsScheduling(List<OS_Process> processes) {
		// Sort all the processes based on their arrival time
		processes.sort(Comparator.comparingInt(OS_Process::getArrivalTime));
		
		System.out.println("Sorted by arrival time: " + processes.toString());
		
		// place the code below insde a For-Loop, which executes all the processes
		/*
		while (time < processes.get(0).arrivalTime) {
			System.out.println("Time: " + time + " ... CPU is idle");
			time = time + 1;
		}
		processes.get(0).run(time);
		time += processes.get(0).burstTime;
		System.out.println("Time " + time + ": ...CPU is idling...");
		*/
		
		for (int i = 0; i < processes.size(); ++i) {
			while (time < processes.get(i).getArrivalTime()) {
				System.out.println("Time: " + time + " ... CPU is idle");
				time++;
			}
			processes.get(i).run(time);
			time += processes.get(i).burstTime;
			System.out.println("Process P" + processes.get(i).id + " completed at time: " + time);
		} // end for loop
	}
	
	public static void main(String[] args) {
		List<OS_Process> processes = new ArrayList<OS_Process>();
		// id, arrivalTime, burst Time
		processes.add(new OS_Process(1, 2, 4));
		processes.add(new OS_Process(2, 3, 2));
		processes.add(new OS_Process(3, 3, 1));
		processes.add(new OS_Process(4, 8, 3));
		processes.add(new OS_Process(5, 15, 5));
		processes.add(new OS_Process(6, 16, 2));
		processes.add(new OS_Process(7, 16, 1));
		
		// create a CPU to execute process
		CPU cpu = new CPU();
		cpu.fcfsScheduling(processes);
		
	} // end main method
	
} // end CPU class