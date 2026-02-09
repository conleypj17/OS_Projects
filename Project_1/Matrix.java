import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
 int rows; // Define the number of rows
 int cols; // Define the number of columns
 double values[][];
 
 Random rand = new Random();

 /*First constructor: with row and column as the input that creates a matrix with the specified size and 
  * assign each elements with randomly generated number*/
 Matrix(int r, int c) {
  rows = r;
  cols = c;
  values = new double[r][c];
  
  for(int y = 0; y < rows; y++)
  {
   for(int x = 0; x < cols; x++)
   {
    values[y][x] = rand.nextInt(10); //generating an integer number between 0 and 9
   }
  }

 }

 /*Second constructor: with row, column, and a 2D array as the input. Similar to the first constructor
  * above, but instead of randomly generating, it assigns the elements with the third argument double 2D array.  */
 Matrix(int r, int c, double v[][]) {
    
	 rows = r;
	 cols = c;

     if (r < v.length || (v.length > 0 && c < v[0].length))
     {
    	 System.out.println("Input matrix is larger than specified size. New matrix will miss some data.");
     }
     else if (r > v.length || (v.length > 0 && c > v[0].length))
     {
    	 System.out.println("Input matrix is smaller than specified size. New matrix will have 0s where data isn't provided.");
     }
	 values = new double[r][c]; // values will be filled with 0s as a default, do not need to worry about manually doing it
	 for (int y = 0; y < rows; ++y)
	 {
		 for (int x = 0; x < cols; ++x)
		 {
			 // check if the element exists in the input array, otherwise use 0 (default)
			 if (y < v.length && x < v[y].length)
			 {
				 values[y][x] = v[y][x];
			 }
		 }
	 }
  
 }
 
 
 /*Output the matrix to the console*/
 void print()
 {
    for(int y = 0; y < rows; y++)
    {
        for(int x = 0; x < cols; x++)
        {
            System.out.print(values[y][x] + ", ");
        }
        System.out.println();
    } 
 }
 
 
 /*Matrix product without thread: let the current matrix times the input argument matrix m
  * and return the result matrix
  * Below the multiplication is already provided but you need to add a few lines of code to 
  * do the dimension check. If the input matrix does not satisfy the dimension requirement for
  * matrix product, you can print out a message and return a null matrix*/
 Matrix multiplyBy(Matrix m)
 {
    
    if (this.cols != m.rows)
    {
        System.out.println("Error: Columns of current matrix do not match rows of input matrix for multiplication. Returning null matrix");
        return null;
    }
    Matrix result = new Matrix(this.rows, m.cols);
    int len = this.cols; //or m.rows, they are the same for multiplication
    for (int i = 0; i < this.rows; ++i)
    {
        for (int j = 0; j < m.cols; ++j)
        {
            double sum = 0.0;
            for (int k = 0; k < len; ++k)
            {
                sum += this.values[i][k] * m.values[k][j];
            }
            result.values[i][j] = sum;
        }
    }
    return result;
 }
 
 
 /*Implementation: instead using loops above to calculate each elements, 
  * here you will use threads to accomplish the matrix product task.
  * Similar to the "multiplyBy()" above, the input matrix m represents
  * the second matrix that you will use the current matrix to times. The
  * returned Matrix will be the product result matrix.  
  * The code below is just an example, which is not the real solution. 
  * You need to create multiple threads to do the multiplication with each thread
  * computing one column within the result matrix*/
 Matrix multiplyByThreads(Matrix m)
 {
  // checking dimensions for matrix multiplication
  if (this.cols != m.rows)
  {
      System.out.println("Error: Columns of current matrix do not match rows of input matrix for multiplication. Returning null matrix");
      return null;
  }
  
  Matrix result = new Matrix(this.rows, m.cols);
  List<Thread> threads = new ArrayList<>(); // ArrayList to keep track of threads for joining later
  
  // create threads for each column
  for (int col = 0; col < m.cols; ++col)
  {
     // creating a ColumnCalculator object for each column
      ColumnCalculator calc = new ColumnCalculator(this, m, result, col);
      Thread thread = new Thread(calc); // create a thread to run the ColumnCalculator
      threads.add(thread);
      thread.start();
      
      // if there are 10 threads or this is the last column, wait for all to complete
      if (threads.size() == 10 || col == m.cols - 1)
      {
          for (Thread t : threads)
          {
              try
              {
                  t.join(); // wait for the threads to complete
              }
              catch (InterruptedException e)
              {
                  e.printStackTrace(); // handle interruption exception
              }
          }
          threads.clear(); // emrpting the Arraylist for the next batch
      }
  }
  
  return result; 
 }
 
 
 /* The main function for evaluation purpose*/
 public static void main(String[] args)
 {
  // test 1: Simple matrices with known values to verify correctness
  System.out.println("Test 1: Simple Matrix Multiplication");
  double[][] testData1 = {{1, 2}, {3, 4}};
  double[][] testData2 = {{5, 6}, {7, 8}};
  
  Matrix m1 = new Matrix(2, 2, testData1);
  Matrix m2 = new Matrix(2, 2, testData2);
  
  System.out.println("Matrix 1:");
  m1.print();
  System.out.println("Matrix 2:");
  m2.print();
  
  // test normal matrix multiplication
  System.out.println("Result of m1.multiplyBy(m2):");
  Matrix result1 = m1.multiplyBy(m2);
  if (result1 != null) result1.print();
  
  // test multiplyByThreads, which should be faster than the normal matrix multiplication without threads
  System.out.println("Result of m1.multiplyByThreads(m2):");
  Matrix result2 = m1.multiplyByThreads(m2);
  if (result2 != null) result2.print();
  
  // verify both methods give the same result
  System.out.println("Verifying both methods produce same results:");
  boolean same = true; // flag to check if results are the same
  if (result1 != null && result2 != null)
  {
      for (int i = 0; i < result1.rows && same; ++i)
      {
          for (int j = 0; j < result1.cols && same; ++j)
          {
              if (result1.values[i][j] != result2.values[i][j])
              {
                  same = false;
              }
          }
      }
      System.out.println("Results match: " + same);
  }
  
  // Test 2 - comparing the performances with large matrices
  System.out.println("Test 2: Performance Comparison with Large Matrices (1000x2000 x 2000x1000)");
  int rows1 = 1000;
  int cols1 = 2000;
  int cols2 = 1000;
  
  System.out.println("Creating large matrices");
  Matrix largeM1 = new Matrix(rows1, cols1);
  Matrix largeM2 = new Matrix(cols1, cols2);
  
  // test normal matrix multiplication
  System.out.println("Testing multiplyBy() with large matrices...");
  long startTime = System.currentTimeMillis();
  Matrix largeResult1 = largeM1.multiplyBy(largeM2);
  long endTime = System.currentTimeMillis();
  long timeMultiplyBy = endTime - startTime;
  System.out.println("Time for multiplyBy(): " + timeMultiplyBy + " ms");
  
  // test matrix multiplication with threads, which should be faster than the normal matrix multiplication without threads
  System.out.println("Testing multiplyByThreads() with large matrices...");
  startTime = System.currentTimeMillis();
  Matrix largeResult2 = largeM1.multiplyByThreads(largeM2);
  endTime = System.currentTimeMillis();
  long timeMultiplyByThreads = endTime - startTime;
  System.out.println("Time for multiplyByThreads(): " + timeMultiplyByThreads + " ms");
  
  // printing the results
  System.out.println("Performance Comparison:");
  System.out.println("Matrix dimensions: " + rows1 + " x " + cols1 + " * " + cols1 + " x " + cols2);
  System.out.println("multiplyBy() execution time: " + timeMultiplyBy + " ms");
  System.out.println("multiplyByThreads() execution time: " + timeMultiplyByThreads + " ms");
  double speedup = (double) timeMultiplyBy / timeMultiplyByThreads;
  System.out.println("Speedup (multiplyBy time / multiplyByThreads time): " + String.format("%.2f", speedup) + "x");
  
 }
}