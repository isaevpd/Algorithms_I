public class QuickFind
{
	public static int[] id;

	public void QuickFindUF(int N)
	{
		id = new int[N];
		for (int i = 0; i < N; i++)
		{
			id[i] = i;
		}
	}
	public boolean connected(int p, int q)
	{ return id[p] == id[q]; }
	public void union(int p, int q)
	{
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; i < id.length; i++)
		{
			if (id[i] == pid) id[i] = qid;
		}	
		
	}
	// Test with an array of length
	public static void main(String[] args) 
	{
		QuickFind my_test = new QuickFind();
		my_test.QuickFindUF(10);
		my_test.union(5, 0);
		my_test.union(1, 9);
		my_test.union(5, 3);
	    my_test.union(8, 3);
		my_test.union(5, 2);
		my_test.union(7, 5);
		// my_test.union(5, 0);
		// my_test.union(7, 2);
		// my_test.union(6, 1);
		for (int i = 0; i < my_test.id.length; i++)
		{
			System.out.print(id[i] + " ");

		}
		System.out.print("\n");



	}
}