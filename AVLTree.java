/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {

	
	private IAVLNode min;
	private IAVLNode max;
	private IAVLNode Root;
	private int size;
	private final IAVLNode virtualnode=new AVLNode(-1,null,-1);

	/**   */
	/** Constrctor  */
	public AVLTree(IAVLNode Root){
		this.Root=Root;
		if(Root!=null&&Root.isRealNode()){
			updatemin();
			updatemax();}
	}

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   *O(1)
   */
  public boolean empty() {
	  if(getRoot()==null){
		  return true;
	  }
	  return false;
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
  *
  * O(logn)
   */
  public String search(int k)
  {
	  IAVLNode x=this.Root;
	  while (x!=null){
		  if(x.getKey()==k){
			  return x.getValue();
		  }
		  else {
			  if(x.getKey()<k){
				  x=x.getRight1();
			  }
			  else {
				  x=x.getLeft1();
			  }
		  }

	  }
	  return null;
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   *
   * O(logn)
   */
   public int insert(int k, String i) {
	   IAVLNode root=this.getRoot();
	   IAVLNode x=new AVLNode(k,i);
	   IAVLNode z=Tree_position(root,k);
	   if(this.empty()){
		   this.Root=x;
		   this.size=size+1;
		   updatemin();
		   updatemax();
		   return 0;
	   }
	   if(k==z.getKey()){
		   return -1;
	   }
	   else{
		   this.size=size+1;

		   if(z.getKey()<k){

			   z.setRight(x);
			   x.setParent(z);

			   updatemin();
			   updatemax();

			   //the parent is uniary node//
			   if(z.getLeft1().isRealNode()){

				   return 0;
			   }
			   //the parent is a leaf//
			   else {
				   return rebalance_insert(x);

			   }

		   }
		   else {
			   z.setLeft(x);
			   x.setParent(z);

			   updatemin();
			   updatemax();
			   //the parent is uniary node//
			   if(z.getRight1().isRealNode()){

				   return 0;
			   }
			   //the parent is a leaf//
			   else {
				   return rebalance_insert(x);

			   }
		   }

	   }
   }

	/**
	 * public int rebalance_insert(IAVLNode x)
	 *
	 * rebalance the tree
	 * return the number of balancing operations done.
	 *
	 * O(logn)
	 */

	public int rebalance_insert(IAVLNode x){


		if(x.getParent()==null){
			return 0;
		}
		else {
			IAVLNode z=x.getParent();
			//ZERO-ONE//
			if(z.getHeight()==x.getHeight()&&z.getHeight()-z.getRight1().getHeight()==1){

				Promote(z);
				return 1+rebalance_insert(z);
			}
			//ZERO-TWO//
			if(z.getHeight()==x.getHeight()&&z.getHeight()-z.getRight1().getHeight()==2){
				//ZERO-TWO(ONE-TWO)//
				if(x.getHeight()-x.getLeft1().getHeight()==1&&x.getHeight()-x.getRight1().getHeight()==2){


					RotateRight(x);
					Demote(z);
					return 2;
				}
				//ZERO-TWO(TWO-ONE)//
				if(x.getHeight()-x.getLeft1().getHeight()==2&&x.getHeight()-x.getRight1().getHeight()==1){

					IAVLNode b=x.getRight1();
					RotateLeft(b);
					RotateRight(b);
					Promote(b);
					Demote(x);
					Demote(z);
					return 5;
				}
			}
			//ONE-ZERO//
			if(z.getHeight()==x.getHeight()&&z.getHeight()-z.getLeft1().getHeight()==1){


				Promote(z);

				return 1+rebalance_insert(z);
			}
			//TWO-ZERO//

			if(z.getHeight()-x.getHeight()==0&&z.getHeight()-z.getLeft1().getHeight()==2){
				//TWO-ZERO(ONE-TWO)//
				if(x.getHeight()-x.getLeft1().getHeight()==1&&x.getHeight()-x.getRight1().getHeight()==2){

					IAVLNode a=x.getLeft1();
					RotateRight(a);
					RotateLeft(a);
					Promote(a);
					Demote(x);
					Demote(z);
					return 5;
				}
				//TWO-ZERO(TWO-ONE)//
				if(x.getHeight()-x.getLeft1().getHeight()==2&&x.getHeight()-x.getRight1().getHeight()==1){

					this.RotateLeft(x);
					Demote(z);


					return 2;
				}
			}
		}
		return 0;
	}

	/**
	 * public void RotateRight(IAVLNode y)
	 *
	 * rotate y right
	 * change the tree and return void
	 *
	 * O(1)
	 */

	public void RotateRight(IAVLNode y) {

		IAVLNode x = y.getParent();
		IAVLNode b = y.getRight1();
		IAVLNode z = x.getParent();

		y.setRight(x);
		x.setLeft(b);

		if (b != null) {
			b.setParent(x);
		}

		y.setParent(z);
		if (z == null) {
			this.Root = y;
		} else {
			if (z.getKey() > y.getKey()) {
				z.setLeft(y);
			} else {
				z.setRight(y);
			}
		}

		x.setParent(y);

	}

/**
 * public void RotateLeft(IAVLNode x)
 *
 * rotate x left
 * change the tree and return void
 *
 * O(1)
 */


	public void RotateLeft(IAVLNode x){
		IAVLNode y=x.getParent();
		IAVLNode b=x.getLeft1();
		IAVLNode z=y.getParent();

		x.setLeft(y);
		y.setRight(b);

		if(b!=null){
			b.setParent(y);
		}

		x.setParent(z);
		if(z==null){
			this.Root=x;
		}
		else{
			if (z.getKey()>x.getKey()) {
				z.setLeft(x);
			} else {
				z.setRight(x);
			}
		}

		y.setParent(x);

	}
	/**
	 * public void Promote(IAVLNode x)
	 *
	 * promote the height of x by 1.
	 * return void
	 * O(1)
	 */
	public void Promote(IAVLNode x){
		x.setHeight(x.getHeight()+1);
	}
	/**
	 * public void Demote(IAVLNode x)
	 *
	 * demote the height of x by 1.
	 * return void
	 * O(1)
	 */
	public void Demote(IAVLNode x){
		x.setHeight(x.getHeight()-1);
	}




  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   *
   * O(logn)
   */
   public int delete(int k)
   {
	   int result=0;
	   IAVLNode y=this.Tree_position(this.Root,k);

	   if(y.getKey()!=k){
		   return -1;
	   }
	   else {
		   this.size=size-1;
		   IAVLNode z=y.getParent();

		   //Y IS ROOT//
		   if(y==Root){
			   IAVLNode successor=successor(y);
			   //Y HAS NO CHILDREN//
			   if(!y.getRight1().isRealNode()&&!y.getLeft1().isRealNode()){
				   this.Root=null;

				   return 0;
			   }
			   //Y HAS ONE CHILD//
			   if((y.getLeft1().isRealNode()&&!y.getRight1().isRealNode())||(!y.getLeft1().isRealNode()&&y.getRight1().isRealNode())){
				   //Y HAS LEFT CHILD//
				   if(y.getLeft1().isRealNode()&&!y.getRight1().isRealNode()){
					   this.Root=y.getLeft1();
					   Root.setParent(null);
					   this.min=Root;
					   this.max=Root;

					   return 0;
				   }
				   //Y HAS RIGHT CHILD//
				   if(!y.getLeft1().isRealNode()&&y.getRight1().isRealNode()){
					   this.Root=y.getRight1();
					   Root.setParent(null);
					   this.min=Root;
					   this.max=Root;

					   return 0;
				   }
			   }
			   //Y HAS 2 CHILDREN//
			   if(y.getLeft1().isRealNode()&&y.getRight1().isRealNode()){
				   IAVLNode yLeft=y.getLeft1();
				   IAVLNode yRight=y.getRight1();

				   IAVLNode sucParent=successor.getParent();
				   IAVLNode sucRight=successor.getRight1();

				   int yheight=y.getHeight();


				   this.Root=successor;
				   Root.setParent(null);
				   successor.setParent(null);
				   successor.setLeft(yLeft);
				   successor.setRight(yRight);
				   this.Root=successor;
				   Root.setParent(null);

				   yLeft.setParent(successor);
				   yRight.setParent(successor);

				   //delete//
				   if(successor==yRight){
					   successor.setRight(sucRight);
					   sucRight.setParent(successor);
				   }
				   else {

					   sucParent.setLeft(sucRight);
					   sucRight.setParent(sucParent);}


				   successor.setHeight(yheight);

				   IAVLNode noderebalance=null;
				   IAVLNode nodeheight=null;
				   if(sucParent == y){
					   noderebalance = successor;
				   }
				   else{
					   noderebalance = sucParent;
				   }

				   result=rebalance_delete(noderebalance);
				   updatemax();
				   updatemin();
				   return result;
			   }
		   }
		   //Y IS A LEAF//
		   if(!y.getLeft1().isRealNode()&&!y.getRight1().isRealNode()){

			   //Y IS LEFT SON OF Z(1-1)//
			   if(z.getHeight()==y.getHeight()&&z.getHeight()==z.getRight1().getHeight()&&z.getLeft1()==y){
				   z.setLeft(virtualnode);
				   y.setParent(null);

				   updatemin();
				   updatemax();

				   return 0;
			   }
			   //Y IS RIGHT SON OF Z(1-1)//
			   if(z.getHeight()==y.getHeight()&&z.getHeight()==z.getLeft1().getHeight()&&z.getRight1()==y){
				   z.setRight(virtualnode);
				   y.setParent(null);

				   updatemin();
				   updatemax();

				   return 0;
			   }
			   //OTHER CASES//
			   else {
				   //Y IS LEFT SON//
				   if(z.getLeft1()==y){
					   z.setLeft(virtualnode);
					   y.setParent(null);

					   result=rebalance_delete(z);
					   updatemax();
					   updatemin();
					   return result;
				   }
				   //Y IS RIGHT SON//
				   if (z.getRight1()==y){
					   z.setRight(virtualnode);
					   y.setParent(null);

					   result=rebalance_delete(z);
					   updatemin();
					   updatemax();

					   return result;
				   }
			   }
		   }
		   //Y IS UNIARY NODE//
		   if((y.getRight1().isRealNode()&&!y.getLeft1().isRealNode())||(y.getLeft1().isRealNode()&&!y.getRight1().isRealNode())){
			   //Y IS LEFT SON//
			   if(z.getLeft1()==y){
				   //Y.LEFT IS REAL NODE//
				   if(y.getLeft1().isRealNode()){
					   IAVLNode x=y.getLeft1();
					   z.setLeft(x);
					   x.setParent(z);
					   y.setLeft(null);
					   y.setParent(null);

					   result=rebalance_delete(z);
					   updatemax();
					   updatemin();

					   return result;
				   }
				   //Y.RIGHT IS REAL NODE//
				   if(y.getRight1().isRealNode()){
					   IAVLNode x=y.getRight1();
					   z.setLeft(x);
					   x.setParent(z);
					   y.setRight(null);
					   y.setParent(null);

					   result=rebalance_delete(z);
					   updatemin();
					   updatemax();

					   return result;
				   }
			   }
			   //Y IS RIGHT SON//
			   if(z.getRight1()==y){
				   //Y.LEFT IS REAL NODE//
				   if(y.getLeft1().isRealNode()){
					   IAVLNode x=y.getLeft1();
					   z.setRight(x);
					   x.setParent(z);
					   y.setLeft(null);
					   y.setParent(null);

					   result=rebalance_delete(z);
					   updatemax();
					   updatemin();

					   return result;
				   }
				   //Y.RIGHT IS REAL NODE//
				   if(y.getRight1().isRealNode()){
					   IAVLNode x=y.getRight1();
					   z.setRight(x);
					   x.setParent(z);
					   y.setRight(null);
					   y.setParent(null);

					   result=rebalance_delete(z);
					   updatemin();
					   updatemax();

					   return result;
				   }
			   }

		   }
		   //Y IS NOT LEAF OR UNIARY NODE//
		   else {
			   //SWITCHING Y WITH Y.SUCCESSOR//
			   IAVLNode successor=successor(y);

			   IAVLNode yParent=y.getParent();

			   IAVLNode yLeft=y.getLeft1();
			   IAVLNode yRight=y.getRight1();

			   IAVLNode sucParent=successor.getParent();

			   IAVLNode sucRight=successor.getRight1();

			   int yheight=y.getHeight();

			   //successor.setParent(yParent);
			   successor.setLeft(yLeft);
			   successor.setRight(yRight);

			   yLeft.setParent(successor);
			   yRight.setParent(successor);

			   //IF Y IS LEFT OR RIGHT SON//
			   if(yParent.getRight1()==y){
				   yParent.setRight(successor);
			   }
			   else{
				   yParent.setLeft(successor);
			   }
			   //delete//
			   if(successor==yRight){
				   successor.setRight(sucRight);
			   }
			   else {
				   sucParent.setLeft(sucRight);}


			   successor.setHeight(yheight);

			   IAVLNode noderebalance=null;
			   IAVLNode nodeheight=null;
			   if(sucParent == y){
				   noderebalance = successor;
			   }
			   else{
				   noderebalance = sucParent;
			   }
			   successor.setParent(yParent);

			   result=rebalance_delete(noderebalance);
			   updatemax();
			   updatemin();

			   return result;


		   }

	   }

	   return 0;
   }


	/**
	 * public int rebalance_delete(IAVLNode z)
	 *
	 * rebalance the tree
	 * return the number of rebalancing operations in the process
	 *
	 * O(logn)
	 */
	public int rebalance_delete(IAVLNode z){
		if (z==null){
			return 0;}
		else {
			IAVLNode zRight=z.getRight1();
			IAVLNode zLeft=z.getLeft1();

			//2-1//
			if(z.getHeight()-zLeft.getHeight()==2&&z.getHeight()-zRight.getHeight()==1){

				return 0;
			}
			//1-2//
			if(z.getHeight()-zLeft.getHeight()==1&&z.getHeight()-zRight.getHeight()==2){

				return 0;
			}
			//2-2//
			if(z.getHeight()-zLeft.getHeight()==2&&z.getHeight()-zRight.getHeight()==2){

				Demote(z);
				return 1+rebalance_delete(z.getParent());
			}
			//3-1//
			if(z.getHeight()-zLeft.getHeight()==3&&z.getHeight()-zRight.getHeight()==1){

				IAVLNode a=zRight.getLeft1();
				IAVLNode b=zRight.getRight1();
				//1-1//
				if(zRight.getHeight()-a.getHeight()==1&&zRight.getHeight()-b.getHeight()==1){

					RotateLeft(zRight);
					Demote(z);
					Promote(zRight);
					return 3;
				}
				//1-2//
				if(zRight.getHeight()-a.getHeight()==1&&zRight.getHeight()-b.getHeight()==2){

					RotateRight(a);
					RotateLeft(a);
					Demote(zRight);
					Promote(a);
					Demote(z);
					Demote(z);

					return 6+rebalance_delete(a.getParent());

				}
				//2-1//
				if(zRight.getHeight()-a.getHeight()==2&&zRight.getHeight()-b.getHeight()==1){

					RotateLeft(zRight);
					Demote(z);
					Demote(z);

					return 3+rebalance_delete(zRight.getParent());
				}

			}
			//1-3//
			if(z.getHeight()-zLeft.getHeight()==1&&z.getHeight()-zRight.getHeight()==3){

				IAVLNode a=zLeft.getLeft1();
				IAVLNode b=zLeft.getRight1();
				//1-1//
				if(zLeft.getHeight()-a.getHeight()==1&&zLeft.getHeight()-b.getHeight()==1){

					RotateRight(zLeft);
					Demote(z);
					Promote(zLeft);
					return 3;
				}
				//1-2//
				if(zLeft.getHeight()-a.getHeight()==1&&zLeft.getHeight()-b.getHeight()==2){


					RotateRight(zLeft);
					Demote(z);
					Demote(z);

					return 3+rebalance_delete(zLeft.getParent());

				}
				//2-1//
				if(zLeft.getHeight()-a.getHeight()==2&&zLeft.getHeight()-b.getHeight()==1){

					RotateLeft(b);
					RotateRight(b);
					Demote(zLeft);
					Promote(b);
					Demote(z);
					Demote(z);

					return 6+rebalance_delete(b.getParent());
				}

			}



		}
		return 0;

	}

	/**
	 * public IAVLNode successor(IAVLNode x)
	 *
	 * return the smallest node that is bigger than x by comparing keys.
	 *
	 * O(logn)
	 */
	public IAVLNode successor(IAVLNode x){
		IAVLNode y=x.getRight1();
		while (y.getLeft1().isRealNode()){
			y=y.getLeft1();
		}
		return y;
	}

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
	*
	* O(1)
    */
   public String min()
   {
	   if(empty()){
		   return null;
	   }
	   else
		   return min.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
	*
	* O(1)
    */
   public String max()
   {
	   if ((empty())){
		   return null;
	   }
	   else
		   return max.getValue();
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   *
   * O(n)
   */
  public int[] keysToArray()
  {
	  if(this.empty()){
		  int[] arr=new int[0];
		  return arr;
	  }
	  else {
		  int[] arr=new int[size];
		  IAVLNode root=Root;
		  int i=0;
		  keysToArrayREC(i,arr,root);
		  return arr;
	  }
  }

	/**
	 * public int keysToArrayREC(int i,int[] arr, IAVLNode r)
	 *
	 * helping recursive function for keysToArray()
	 * return the index that we have stopped in
	 *
	 * O(n)
	 */
	public int keysToArrayREC(int i,int[] arr, IAVLNode r){

		if(r!=null&& r.isRealNode()){
			i= keysToArrayREC(i,arr,r.getLeft1());
			arr[i++]= r.getKey();
			i= keysToArrayREC(i,arr,r.getRight1());
		}
		return i;
	}

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   *
   * O(n)
   */
  public String[] infoToArray()
  {
	  if(this.empty()){
		  String[] arr=new String[0];
		  return arr;
	  }
	  else {

		  String[] arr=new String[size];
		  IAVLNode root=Root;
		  int i=0;
		  infoToArrayREC(i,arr,root);
		  return arr;
	  }
  }
	/**
	 * public int infoToArrayREC(int i,String[] arr, IAVLNode r)
	 *
	 * helping recursive function for infoToArray()
	 * return the index that we have stopped in
	 *
	 * O(n)
	 */

	public int infoToArrayREC(int i,String[] arr, IAVLNode r){
		if(r!=null&& r.isRealNode()){

			i= infoToArrayREC(i,arr,r.getLeft1());
			arr[i++]= r.getValue();
			i= infoToArrayREC(i,arr,r.getRight1());
		}
		return i;

	}

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
	*
	* O(1)
    */
   public int size()
   {
	   return size;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
	*
	* O(1)
    */
   public IAVLNode getRoot()
   {
	   return this.Root;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
	*
	* O(logn)
    */   
   public AVLTree[] split(int x)
   {
	   IAVLNode y = this.Tree_position(this.Root, x);
	   AVLTree Bigger = new AVLTree(y.getRight1());
	   AVLTree Smaller = new AVLTree(y.getLeft1());
	   AVLTree[] res = new AVLTree[2];
	   res[0] = Smaller;
	   res[1] = Bigger;

	   if(this.min.getKey()==x) {
		   this.delete(x);
		   res[0]=Smaller;
		   res[1]=this;
	   }

	   else if(this.max.getKey()==x) {
		   this.delete(x);
		   res[0]=this;
		   res[1]=Bigger;
	   }

	   if (y.getParent() == null)
		   return res;
	   IAVLNode z = y.getParent();
	   while (z!=null ) {

		   if (y == z.getRight1()) {
			   AVLTree leftSub = new AVLTree(z.getLeft1());
			   leftSub.getRoot().setParent(null);
			   Smaller.join(new AVLNode(z.getKey(),z.getValue()), leftSub);
		   } else {
			   AVLTree rightSub = new AVLTree(z.getRight1());
			   rightSub.getRoot().setParent(null);
			   Bigger.join(new AVLNode(z.getKey(),z.getValue()), rightSub);
		   }
		   y=z;
		   z=z.getParent();
	   }
	   return res;
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
	*
	* O(logn)
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   int result = Math.abs(this.getRoot().getHeight() - t.getRoot().getHeight())+1;
	   this.size = this.size + t.size + 1;
	   //  System.out.println(this.size);
	   //both are empty/
	   if(this.empty()&&t.empty()){
		   return 1;
	   }
	   //this is empty//
	   if(this.empty()){
		   t.insert(x.getKey(),x.getValue());
		   this.Root = t.getRoot();
	   }
	   //t is empty//
	   if(t.empty()){
		   this.insert(x.getKey(),x.getValue());
	   }
	   //both are not empty//
	   else{

		   //this<x<t//
		   if(this.getRoot().getKey()<t.getRoot().getKey()) {
			   //t.getHeight == this.getHeight//
			   if(t.getRoot().getHeight() == this.Root.getHeight()) {
				   x.setLeft(this.getRoot());
				   this.getRoot().setParent(x);

				   x.setRight(t.getRoot());
				   t.getRoot().setParent(x);

				   x.setHeight(this.Root.getHeight()+1);

				   this.Root=x;
			   }

			   //t.getHeight > this.getHeight//
			   else if(t.getRoot().getHeight() > this.Root.getHeight()) {

				   x.setLeft(this.getRoot());
				   this.getRoot().setParent(x);

				   IAVLNode tmp=t.getRoot();

				   int k =this.Root.getHeight();

				   while(tmp.getHeight()>k) {
					   tmp=tmp.getLeft1();
				   }
				   x.setRight(tmp);



				   x.setHeight(this.Root.getHeight()+1);
				   this.Root=t.getRoot();


				   x.setParent(tmp.getParent());
				   tmp.getParent().setLeft(x);
				   tmp.setParent(x);




			   }

			   //t.getHeight < this.getHeight//
			   else {

				   x.setRight(t.getRoot());
				   t.getRoot().setParent(x);

				   IAVLNode tmp=this.getRoot();

				   int k =t.getRoot().getHeight();

				   while(tmp.getHeight()>k) {
					   tmp=tmp.getRight1();
				   }
				   x.setLeft(tmp);



				   x.setHeight(t.getRoot().getHeight()+1);
				   this.Root=this.getRoot();


				   x.setParent(tmp.getParent());
				   tmp.getParent().setRight(x);
				   tmp.setParent(x);
			   }



		   }
		   //t<x<this//
		   else {

			   if(this.getRoot().getKey()>t.getRoot().getKey()) {
				   //t.getHeight == this.getHeight//
				   if(t.getRoot().getHeight() == this.Root.getHeight()) {
					   x.setLeft(t.getRoot());
					   t.getRoot().setParent(x);

					   x.setRight(this.getRoot());
					   this.getRoot().setParent(x);

					   x.setHeight(this.Root.getHeight()+1);
					   this.Root=x;

				   }



				   //t.getHeight > this.getHeight//
				   else if(t.getRoot().getHeight() > this.Root.getHeight()) {

					   x.setRight(this.getRoot());
					   this.getRoot().setParent(x);

					   IAVLNode tmp=t.getRoot();

					   int k =this.Root.getHeight();

					   while(tmp.getHeight()>k) {

						   tmp=tmp.getRight1();
					   }
					   x.setLeft(tmp);

					   x.setHeight(this.getRoot().getHeight()+1);
					   this.Root=t.getRoot();


					   x.setParent(tmp.getParent());
					   tmp.getParent().setRight(x);
					   tmp.setParent(x);


				   }

				   //t.getHeight < this.getHeight//
				   else {

					   x.setLeft(t.getRoot());
					   t.getRoot().setParent(x);

					   IAVLNode tmp=this.getRoot();

					   int k =t.getRoot().getHeight();

					   while(tmp.getHeight()>k) {
						   tmp=tmp.getLeft1();
					   }
					   x.setRight(tmp);



					   x.setHeight(t.getRoot().getHeight()+1);
					   this.Root=this.getRoot();


					   x.setParent(tmp.getParent());
					   tmp.getParent().setLeft(x);
					   tmp.setParent(x);
				   }


			   }


		   }

		   this.updatemin();
		   this.updatemax();

		   rebalance_join(x);
		   rebalance_insert(x);
	   }

	   return result;
   }


	/**
	 * public void rebalance_join(IAVLNode x)
	 *
	 * balances the tree
	 *
	 * O(1)
	 */
	public void rebalance_join(IAVLNode x) {
		IAVLNode z = x.getParent();

		//X IS LEFT SON//
		if(z.getLeft1()==x) {
			if(z.getHeight()==x.getHeight() & z.getHeight()-z.getRight1().getHeight()==2)
				if(x.getHeight()-x.getRight1().getHeight()==1 & x.getHeight()-x.getLeft1().getHeight()==1) {
					Promote(x);

					RotateRight(x);
				}
		}

		else {
			if(z.getHeight()==x.getHeight() & z.getHeight()-z.getLeft1().getHeight()==2)
				if(x.getHeight()-x.getRight1().getHeight()==1 & x.getHeight()-x.getLeft1().getHeight()==1) {
					Promote(x);

					RotateLeft(x);
				}

		}
	}


	/**
	 * public IAVLNode Tree_position(IAVLNode x,int k)
	 *
	 * find the node if existed and return it
	 * or return its parent if it was in the tree
	 *
	 * O(logn)
	 */
	public IAVLNode Tree_position(IAVLNode x,int k){

		IAVLNode y=this.Root;
		while (x!=null&& x.isRealNode()){
			y=x;
			if(k== x.getKey()){
				return x;
			}
			else {
				if(k<x.getKey()){
					x=x.getLeft1();
				}
				else {
					x=x.getRight1();
				}
			}
		}
		return y;
	}

	/**
	 * public void updatemin()
	 *
	 * update the min field in the tree
	 *
	 * O(logn)
	 */

	public void updatemin(){
		IAVLNode x=this.getRoot();
		while (x.getLeft1().isRealNode()){
			x=x.getLeft1();
		}
		this.min=x;
	}
	/**
	 * public void updatemax()
	 *
	 * update the max field in the tree
	 *
	 * O(logn)
	 */

	public void updatemax(){
		IAVLNode x=this.getRoot();
		while (x.getRight1().isRealNode()){
			x=x.getRight1();
		}
		this.max=x;
	}

	/**
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).

		public IAVLNode getRight1();
		public IAVLNode getLeft1();
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{

	   private int key;
	   private String value;
	   private int height;
	   private IAVLNode lson;
	   private IAVLNode rson;
	   private IAVLNode parent;

	   public AVLNode(int key, String value){
		   this.key=key;
		   this.value= value;
		   this.lson=virtualnode;
		   this.rson=virtualnode;
		   this.parent=null;
		   this.height=0;
	//	   this.size=1;

	   }
	   public AVLNode(int key,String value,int height){
		   this.key=key;
		   this.value= value;
		   this.lson=virtualnode;
		   this.rson=virtualnode;
		   this.parent=null;
		   this.height=height;
	//	   this.size=0;
	   }

		public int getKey()
		{
			if(this.isRealNode()){
				return this.key;}
			else
				return -1;
		}
		public String getValue()
		{
			if(this.isRealNode()){
				return this.value;}
			else
				return null;
		}
		public void setLeft(IAVLNode node)
		{
			this.lson=node;
		}
		public IAVLNode getLeft()
		{
			if(lson.isRealNode()){
				return this.lson;
			}
			else {
				return null;
			}
		}
		public void setRight(IAVLNode node)
		{
			this.rson=node;
		}
		public IAVLNode getRight()
		{
			if(rson.isRealNode()){
				return this.rson;
			}
			else {
				return null;
			}
		}
		public void setParent(IAVLNode node)
		{
			this.parent=node;
		}
		public IAVLNode getParent()
		{
			return this.parent;
		}
		public boolean isRealNode()
		{
			if(this==virtualnode){
				return false;
			}
			if(this==null){
				return false;
			}
			return true;
		}
	    public void setHeight(int height)
	    {
			this.height=height;
	    }
	    public int getHeight()
	    {
			if(this.isRealNode()){
				return this.height;}
			else
				return -1;
	    }

	   @Override
	   public IAVLNode getRight1() {
		   return this.rson;
	   }

	   @Override
	   public IAVLNode getLeft1() {
		   return this.lson;
	   }


   }

}
  
