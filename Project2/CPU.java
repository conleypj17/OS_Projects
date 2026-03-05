
// uncomment the following line if using scheduling package
//package scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class CPU {
	int time = 0; //The parameter to pass to 
				  // each process
	public void fcfsScheduling(List<Process> processes)
	{
		//Sort all the processes based on their arrival time
		processes.sort((p1, p2)-> p1.arrivalTime - p2.arrivalTime);  //"()->..." lambda expression
	
		
		//Place the code below inside a For-loop, which execute all the processes
		for(Process p : processes)
		{
			while(time < p.arrivalTime)
			{
				System.out.println("Time " + time + ": ...CPU is idling...");
				time++;
			}
			p.run(time);
			time += p.burstTime;
		}
		
		
		System.out.println("Time " + time + ": ...CPU is idling...");
		
	}
	
	//processes - input list of jobs
	//mode - 0:non-preemptive; 1:preemptive
	public void sjfScheduling(List<Process> processes, int mode)
	{
		for (Process p : processes) {
			p.finished = false; //reset all processes to unfinished for accurate tracking in SJF
			p.remainingTime = p.burstTime; //reset remaining time for accurate tracking in SJF
		}


		if(mode == 0)
		{
			int completed = 0; //How many jobs have finished
			int total = processes.size(); //Total number of processes or jobs
		
			//Initially make all the processes unfinished
			for(Process p : processes)
				p.finished = false;
			
			//Keep processing new jobs until finishing all of them
			while(completed < total)
			{

				Process shortestJobFound = null;
				
				//find the shortest job among those who arrives
				for(Process p : processes)
				{
					//only deal with arrived jobs but not finished yet
					if(p.arrivalTime <= time && !p.finished) 
					{
						//To find so far the shortest arrived & unfinished job recorded
						if(shortestJobFound == null || p.burstTime < shortestJobFound.burstTime)
							shortestJobFound = p;
					}
				}
				
				//CPU idles
				if(shortestJobFound == null)
				{
					System.out.println("Time " + time + ": ...CPU is idling...");
					time++;
				}
				else //Find a job to execute
				{
					shortestJobFound.run(time);
					time += shortestJobFound.burstTime;
					
					//avoid infinite loop
					completed++;
					shortestJobFound.finished = true;
				}
			}
			
		}
		else {  //mode = 1: preemptive mode
			//Implementation Part 1:
		    // At each time unit:
		    // 1. Among all arrived and unfinished processes,
		    //    select the one with the smallest remaining time.
		    // 2. Run it for ONE time unit only.
		    // 3. Decrease its remaining time and increase global time.
		    // 4. If it finishes, mark it completed.
		    // 5. If no process is ready, CPU idles and time increases.
		    // 6. Continue until all processes finish.
			int completed = 0; //How many jobs have finished
			int total = processes.size(); //Total number of processes or jobs
			//Initially make all the processes unfinished
			for(Process p : processes)
				p.finished = false;
			while(completed < total)
			{

				Process shortestJobFound = null;
				//find the shortest job among those who arrives
				for(Process p : processes)
				{
					//only deal with arrived jobs but not finished yet
					if(p.arrivalTime <= time && !p.finished) 
					{
						//To find so far the shortest arrived & unfinished job recorded
						if(shortestJobFound == null || p.remainingTime < shortestJobFound.remainingTime)
							shortestJobFound = p;
					}
				}
				//CPU idles
				if(shortestJobFound == null)
				{
					System.out.println("Time " + time + ": ...CPU is idling...");
					time++;
				}
				else //Find a job to execute
				{
					shortestJobFound.run(time);
					shortestJobFound.remainingTime -= 1; //run for one time unit only
					time += 1; //increase global time by one unit
					
					if(shortestJobFound.remainingTime == 0) //if it finishes, mark it completed.
					{
						completed++;
						shortestJobFound.finished = true;
					}
					
				}
			}
			
		}
	}
	
	
	// Implementation Part 2: Multi-Level Feedback Queue (MLFQ)
	//
	// System configuration:
	//   Q0 (highest priority): Round Robin, quantum = 8
	//   Q1: Round Robin, quantum = 16
	//   Q2 (lowest priority): FCFS (no quantum)
	//
	// Requirements:
	// 1. All newly arrived processes enter Q0.
	// 2. The CPU always selects from the highest non-empty queue.
	// 3. If a process in Q0 or Q1 uses up its full quantum and is not finished,
	//	  it is demoted to the next lower queue.
	// 4. Q2 follows FCFS and runs until completion unless preempted
	//	by a higher-priority arrival.
	// 5. If a higher-priority process arrives, preempt the currently running
	//	lower-priority process.
	// 6. Advance time step-by-step until all processes finish.	public void multiFeedbackScheduling(List<Process> processes)
	public void multiFeedbackScheduling(List<Process> processes)
	{
		//Implementation Part 2:
		// reset all processes to unfinished and reset their remaining time and time in queues for accurate tracking in MLFQ
		for (Process p : processes) {
			p.finished = false;
			p.remainingTime = p.burstTime;
			p.timeInQ0 = 0;
			p.timeInQ1 = 0;
		}

		// initializing three queues - using linked list underlying data structure
		Queue<Process> Q0 = new LinkedList<>(); //highest priority, q = 8
		Queue<Process> Q1 = new LinkedList<>(); //second priority, q = 16
		Queue<Process> Q2 = new LinkedList<>(); //lowest priority, FCFS

		int completed = 0; //How many jobs have finished
		int total = processes.size(); //Total number of processes
		//Initially make all the processes unfinished
		for(Process p : processes)
			p.finished = false;
		while (completed < total) {
			// check for newly arrived processes and add them to Q0
			for (Process p : processes) {
				if (p.arrivalTime == time) 
				{
					Q0.add(p);
				}
			}

			Process currentProcess = null; // holds the current process

			// select the next process to run from the highest non-empty queue
			if (!Q0.isEmpty()) 
			{
				currentProcess = Q0.peek();  // use peek, not remove because time needs to increment by 1 so other jobs can be added to queue at their arrival time
				int quantum = 8;
				currentProcess.run(time);
				currentProcess.remainingTime--;
				time++;
				currentProcess.timeInQ0++;  // tracking how many quantums it has used in Q0 for demotion purposes

				// process finished
				if (currentProcess.remainingTime == 0)
				{
					Q0.remove();
					completed++;
					currentProcess.finished = true;
					System.out.println("Time " + time + ": Process P" + currentProcess.id + " has finished in Q0.");
				}
				// if quantum is used up but process not finished, demote to Q1
				else if (currentProcess.timeInQ0 >= quantum)
				{
					currentProcess = Q0.remove();
					currentProcess.timeInQ0 = 0; // reset time in Q0 for future tracking if it gets promoted back up
					Q1.add(currentProcess);
					System.out.println("Time " + time + ": Process P" + currentProcess.id + " is demoted to Q1 after 8 quantums.");
				}
			}
			else if (!Q1.isEmpty()) 
			{
				// only peek from Q1 since it may be preempted by new arrivals in Q0
				currentProcess = Q1.peek();
				int jobTime = currentProcess.remainingTime;
				int quantum = 16;
				currentProcess.run(time);
				// only deincrementing one at a time since new processes may preempt it
				currentProcess.remainingTime--;
				time++;
				// making sure to count how many time units it has run in Q1 for demotion purposes
				currentProcess.timeInQ1++;
				if (currentProcess.timeInQ1 >= quantum && currentProcess.remainingTime > 0) 
				{
					Q1.remove(); // remove from Q1
					currentProcess.timeInQ1 = 0; // reset time in Q1 for future tracking if it gets promoted back up
					Q2.add(currentProcess); // demote to Q2
					System.out.println("Time " + time + ": Process P" + currentProcess.id + " is demoted to Q2 after 16 quantums");
				}
				else if (currentProcess.remainingTime == 0)
				{
					currentProcess = Q1.remove(); // remove from Q1 if finished
					completed++;
					currentProcess.finished = true;
					System.out.println("Time " + time + ": Process P" + currentProcess.id + " has finished in Q1.");
				}
			} 
			else if (!Q2.isEmpty()) 
			{
				// also have to use peek here in case a new process arrives in Q0 or Q1 and preempts the current Q2 process
				currentProcess = Q2.peek();
				currentProcess.run(time);
				// only deincrementing one at a time since new processes may preempt it
				currentProcess.remainingTime--;
				time++;
				if (currentProcess.remainingTime == 0) 
				{
					currentProcess = Q2.remove(); // remove from Q2 if finished
					completed++;
					currentProcess.finished = true;
					System.out.println("Time " + time + ": Process P" + currentProcess.id + " has finished in Q2.");
				}
			} 
			else 
			{
				System.out.println("Time " + time + ": ...CPU is idling...");
				time++;
			}
		}
		
	}
	
	
	public static void main(String[] args)
	{
		List<Process> processes = new ArrayList<>();
		
		processes.add(new Process(5, 15, 5));
		processes.add(new Process(6, 16, 2));
		processes.add(new Process(4, 8, 12));
		processes.add(new Process(7, 3, 1));
		processes.add(new Process(2, 3, 2)); //P2
		processes.add(new Process(1, 2, 25)); //P1
		processes.add(new Process(3, 3, 1)); //P3
		
		
		
		// Create a CPU to execute process
		System.out.println("*************************************************************");
		System.out.println("Now testing FCFS:");
		CPU cpu = new CPU();
		// cpu.fcfsScheduling(processes); //FCFS
		
		cpu.time = 0;
		// cpu.sjfScheduling(processes, 0); //Shortest Job First - non preemptive mode

		System.out.println("*************************************************************");
		System.out.println("Now testing Shortest Job First - preemptive mode:");
		cpu.time = 0;
		cpu.sjfScheduling(processes, 1); //Shortest Job First - preemptive mode

		System.out.println("*************************************************************");
		System.out.println("Now testing Multi-Level Feedback Queue:");
		cpu.time = 0;
		cpu.multiFeedbackScheduling(processes); //Multi-Level Feedback Queue
		
		
	}
	
}



