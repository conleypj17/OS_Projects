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
    values[y][x] = rand.nextDouble() * 10.0; //generating a double number between 0 and 10
   }
  }

 }

 /*Second constructor: with row, column, and a 2D array as the input. Similar to the first constructor
  * above, but instead of randomly generating, it assigns the elements with the third argument double 2D array.  */
 Matrix(int r, int c, double v[][]) {
    
	 rows = r;
	 cols = c;

     if (r < v.length || c < v[0].length)
     {
    	 System.out.println("Input matrix is larger than specified size. New matrix will miss some data.");
     }
     else if (r > v.length || c > v[0].length)
     {
    	 System.out.println("Input matrix is smaller than specified size. New matrix will have 0s where data isn't provided.");
     }
	 values = new double[r][c]; // values will be filled with 0s as a default, do not need to worry about manually doing it
	 for (int y = 0; y < rows; ++y)
	 {
		 for (int x = 0; x < cols; ++x)
		 {
			 values[y][x] = v[y][x];
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
    int len = this.rows; //or m.cols, they are the same for multiplication
    for (int i = 0; i < this.rows; ++i)
    {
        for (int j = 0; i < m.cols; ++j)
        {
            double sum = 0.0
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
  //Implementation here...
  Matrix result = new Matrix(this.rows, m.cols);
  
  return result; 
 }
 
 
 /* The main function for evaluation purpose*/
 public static void main(String[] args)
 {
  //Implementation here...
  
  
 }
}