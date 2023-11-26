package mvc;

import java.util.*; 

public class Cache {
    //Cache specs 
    private int blockCount; //default= 32 aka Cache size 
    private int cacheLine; //default=  16 words aka block size
    private int memoryBlockCount; //user input, Main memory size (in blocks)
    private int setSize; //default= 4 (4-way bsa)
    private Set [] cache; // [block num / setSize]
    private int [] mainMemory; // [user input]
    public enum ReadPolicy {
        NLT,
        LT
    }
     private ReadPolicy readPolicy;//default= non load through

    //Outputs 
    private int memoryAccessCount;
    private int hitCount;
    private int missCount;
    private float hitRate;
    private float missRate;
    private float avgMemoryAccessTime;
    private float totalMemoryAccessTime;

    public Cache (int blockCount, int cacheLine, String readPolicy, int memoryBlockCount, int setCount){
        //initialize cache specs 
        this.blockCount = blockCount; 
        this.cacheLine= cacheLine; 
        this.readPolicy= ReadPolicy.valueOf(readPolicy);
        this.memoryBlockCount= memoryBlockCount;
        this.setSize= setCount;
        this.cache= new Set [this.blockCount / this.setSize]; 

        //initialize outputs 
        this.memoryAccessCount= 0; 
        this.hitCount= 0;
        this.missCount= 0;
        this.hitRate= 0;
        this.missRate= 0;
        this.avgMemoryAccessTime= 0;
        this.totalMemoryAccessTime= 0;
    }

    //Overriden constructor: default values 
    public Cache (int memoryBlockCount){
        //initialize specs 
        this.blockCount = 32; //blocks
        this.memoryBlockCount= memoryBlockCount;
        this.mainMemory= new int [memoryBlockCount];
        this.cacheLine= 16; //words
        this.readPolicy= ReadPolicy.valueOf("NLT"); 
        this.setSize= 4;//blocks
        this.cache= new Set [this.blockCount / this.setSize]; 
        //initialize outputs 
        this.memoryAccessCount= 0; 
        this.hitCount= 0;
        this.missCount= 0;
        this.hitRate= 0;
        this.missRate= 0;
        this.avgMemoryAccessTime= 0;
        this.totalMemoryAccessTime= 0;
    }

    public ArrayList<String> simulateCache (int [] sequence) {
        ArrayList<String> simLog = new ArrayList <String> (); //treat as print statements 
        int setIndex, foundBlockIndex, sequenceData;

        for(int i=0; i<sequence.length; i++){            
            setIndex= sequence[i] % this.setSize; 
            sequenceData= sequence[i];
            
            foundBlockIndex= findSequence(sequence[i]); //Is sequence data in the set?
            
            if (foundBlockIndex != 0){ 
                simLog.add(sequenceData + "was found.");
                simLog.add("In set "+setIndex+"block "+foundBlockIndex);
                simLog.add("hits: "+ this.hitCount+ "| new block age: "+ cache[setIndex].incBlockAge(foundBlockIndex));
                this.hitCount++;
            }else{ 
                //logic for MRU handled in Set 
                simLog.add(sequenceData + "not in cache.");
                simLog.add("Added to set "+setIndex+"block "+ cache[setIndex].setBlockData(sequenceData));
                simLog.add("misses: "+ this.missCount);
                this.missCount++;
            }
        }

        simLog.add(calculateOutputs());
        return simLog;
    }
}