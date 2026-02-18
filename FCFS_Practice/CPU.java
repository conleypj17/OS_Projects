import java.util.List;
import java.util.ArrayList;
import java.util.Comparator; // used to sort ArrayList by arrival time

public class CPU {
	int time = 0; // the parameter to pass to each process
	
	public void fcfsScheduling(List<OS_Process> processes) {
		// Sort all the processes based on their arrival time
		processes.sort(Comparator.comparingInt(OS_Process::getArrivalTime));
		//processes.sort((p1, p2) -> p1.arrivaltime - p2.arrivalTime); lambda
		
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
	
	/**
	parameters:
		processes - list input of jobs
		mode: 0 = non-preemptive, 1 = preemptive
	*/
	public void sjfScheduling(List<OS_Process> processes, int mode) {
		
		int finishedJobs = 0;
		int recordedBurstTime = 1000;
		int size = processes.size();
		
		// mode = 0 = non-preemptive
		if (mode == 0) {
			while(finishedJobs < size) {
				recordedBurstTime = 1000;
				OS_Process current_shortest = null;
				// find the shortest job whose arrival time <= CPU current time
				for (OS_Process p: processes) {
					if (p.arrivalTime <= time && p.burstTime < recordedBurstTime) {
						// record this process id and burst time
						recordedBurstTime = p.burstTime;
						current_shortest = p;
					} // end if statement
				} // end for loop
				
				// if found shortest job, then executive it
				if (recordedBurstTime != 1000) {
					System.out.println("Job " + current_shortest.getId() + " started at time " + time + " and ended at time " + (time + recordedBurstTime));
					time += recordedBurstTime;
					finishedJobs++;
					processes.remove(current_shortest);
				}
				// otherwise the CPU idles
				else {
					System.out.println("CPU idling for time " + time + "...");
					time++;
				}
	
			}	//end while loop
		}	//end if statement
		
		
	} // end sjfScheduling function
	
	public static void main(String[] args) {
		List<OS_Process> processes = new ArrayList<OS_Process>();
		// id, arrivalTime, burst Time
		processes.add(new OS_Process(2, 3, 2));
		processes.add(new OS_Process(3, 15, 5));
		processes.add(new OS_Process(6, 8, 3));
		processes.add(new OS_Process(7, 3, 1));
		processes.add(new OS_Process(4, 16, 2));
		processes.add(new OS_Process(5, 16, 1));
		processes.add(new OS_Process(1, 2, 4));
		
		// create a CPU to execute process
		CPU cpu = new CPU();
		//cpu.fcfsScheduling(processes);
		
		cpu.time = 1;
		cpu.sjfScheduling(processes, 0); // shortest job first, non-preemptive mode
	} // end main method
	
} // end CPU class