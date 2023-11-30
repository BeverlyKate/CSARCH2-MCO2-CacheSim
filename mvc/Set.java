package mvc;

public class Set {
    private int blocks;
    private int [][] setArr; 
    private int mostReccentlyUsed; //is a block index
    //setArr= data= [blockIndex][0] age= [blockIndex][2]
    static final int DATA_COL= 0;
    static final int AGE_COL= 1;

    public Set(int setSize, int age){
        this.blocks= setSize;
        this.setArr= new int[setSize][2];
        intlBlockAgeAndData(this.setArr);
    }

    public Set(){
        this.blocks= 4;
        this.setArr= new int[4][2];
        intlBlockAgeAndData(this.setArr);
    }

    private void intlBlockAgeAndData(int[][] setArr) {
        for(int i=0; i<this.blocks; i++){
            setArr[i][AGE_COL]= 0;
            setArr[i][DATA_COL]= -1;
        }
    }

   public int setBlockData(int data){
        int blockIndexFound= -1;
        int i=0;
        //1 find vacant block & update its age
        while(blockIndexFound==-1 && i < this.blocks){
            if(setArr[i][AGE_COL] == 0){
                blockIndexFound= i;
                this.setArr[i][DATA_COL]= data;
                this.setArr[i][AGE_COL]= 1;
                this.mostReccentlyUsed= i;
            }
            i++;
        }
        //if no vacancy
        if(blockIndexFound== -1){
            blockIndexFound= this.mostReccentlyUsed;
            setArr[blockIndexFound][DATA_COL]= data;
            setArr[blockIndexFound][AGE_COL]++;
        }
        return blockIndexFound;
   }

   public int incBlockAge(int foundBlockIndex){
        this.mostReccentlyUsed= foundBlockIndex;
        return this.setArr[foundBlockIndex][AGE_COL]++;
   }

   public int findSequence(int sequenceData) {
        int blockIndexFound= -1;
        int i=0;
        //1 find vacant block & update its age
        while(blockIndexFound==-1 && i < this.blocks){
            if(setArr[i][DATA_COL] == sequenceData){
                blockIndexFound= i;
                this.setArr[i][AGE_COL]++;
                this.mostReccentlyUsed= i;
            }
            i++;
        }

        return blockIndexFound;
   }
    
    public int getBlocks() {
        return blocks;
    }

    public int[][] getSetArr() {
        return setArr;
    }
    
}
