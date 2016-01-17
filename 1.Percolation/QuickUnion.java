public class QuickUnion
{
	public static int[] id;
	public static int[] sz;

	public void QuickUnionUF(int N)
	{
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++)
		{
			id[i] = i;
			sz[i] = 1;	
		}
		 
	}

	private int root(int i)
	{
		while (i != id[i])
		{
		//	id[i] = id[id[i]];
			i = id[i];
			//System.out.println("im here");
		} 
		return i;
	}
	public boolean connected(int p, int q)
	{
		return root(p) == root(q);

	}

	public void union(int p, int q)
	{
		int i = root(p);
		int j = root(q);
		if (i == j) return;
		//System.out.println("IM here");
		if (sz[i] < sz[j]) { id[i] = j; sz[j] += sz[i]; }
		else			   { id[j] = i; sz[i] += sz[j]; }
	}
	// Test with an array of length 10
	public static void main(String[] args) 
	{
		QuickUnion my_test = new QuickUnion();
		my_test.QuickUnionUF(10);
		my_test.union(0, 5);
		my_test.union(8, 5);
		//my_test.union(6, 0);





		for (int i = 0; i < my_test.id.length; i++)
		{
			System.out.print(id[i] + " ");
			//System.out.print(sz[i]);

		}
		System.out.print("\n");
		for (int i = 0; i < my_test.id.length; i++)
		{
			System.out.print(sz[i]);

		}
		System.out.print("\n");
		



	}
}