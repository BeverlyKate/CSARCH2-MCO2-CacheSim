package mvc;

public class Set {
    private int blocks;
    private int counter;
    private int [][] setArr; 
    private int mostReccentlyUsed; //is a block index
    //setArr= data= [blockIndex][0] age= [blockIndex][2]
    static final int dataCol= 0;
    static final int ageCol= 1;

    public Set(int setSize, int age){
        this.blocks= setSize;
        this.setArr= new int[setSize][2];
        intlBlockAge(this.setArr);
        this.counter= 0;
    }

    public Set(){
        this.blocks= 4;
        this.setArr= new int[4][2];
        intlBlockAge(this.setArr);
        this.counter= 0;
    }

    private void intlBlockAge(int[][] setArr) {
        for(int i=0; i<this.blocks; i++){
            setArr[i][ageCol]= 0;
        }
    }

    public int findLargestAge (){
        int blockIndex; 
        int largestAge;

        for(int i=0; i<this.blocks; i++){
            if(setArr)
        }

        return blockIndex;
    }

   public int setBlockData(int data){
        int blockIndex;

        //find vacant block 
        for(int i=0; i<this.blocks; i++){

        }
        //no vacancy, replace mostReccentlyUsed
        findLargestAge();

        return blockIndex;
   }

   public int incBlockAge(int foundBlockIndex){
        return this.setArr[foundBlockIndex][ageCol]++;
   }
    
    public int getBlocks() {
        return blocks;
    }

    public int[][] getSetArr() {
        return setArr;
    }

}
