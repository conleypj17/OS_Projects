//a = 4 x 3  matrix. It means a has 4 row and 3 columns. There are 12 = 3 x 4 numbers (elements)
//within the matrix A   a[4][3]

//Matrix is often used for graphics, solving equations.
//5 * x + 3 * y + 2 * z = 20
//x + y - z = 5
//2 * x - y + z = 30
//To solve this equation, you have to eliminate x, y, or z step by step  
//For programming, it is hard to perform the elimination operation. Instead,
//it uses matrix product format. 
//The coefficients:
//[5  3  2
//1  1  -1   = A   (3x3)
//2 -1  1]

//[ x
//y  = B  (3x1)
//z]

//[20
//5   = C (3x1)
//30]

//Matrix product:   A * B = C
//For one element (x, y) of C, which means the number at the x-th column and y-th row in C
//C[y][x] = The y-th row of matrix A * the x-th column of matrix B


/*This class implements the Java Runnable interface that will calculate col_idx-th column of the result matrix */
class ColumnCalculator implements Runnable{
 
 Matrix m1;
 Matrix m2;
 Matrix result;
 int col_idx; //specify which column of the result is going to be calculated

 ColumnCalculator(Matrix m_1, Matrix m_2, Matrix r, int col)
 {
  m1 = m_1;
  m2 = m_2;
  result = r;
  col_idx = col;
 }
 
 //calculating all the elements in the column of index (specified by "col_idx") of the result matrix
 @Override
 public void run(){
	//Implementation here...

 
}

}
// end of ColumnCalculator class

