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
    private View view;

    //"Print" statements
    private ArrayList<String> simLog;

    public Cache (int blockCount, int cacheLine, String readPolicy, int memoryBlockCount, int setCount){
        //initialize cache specs
        this.blockCount = blockCount;
        this.cacheLine= cacheLine;
        this.view = view;
        this.readPolicy= ReadPolicy.valueOf(readPolicy);
        this.memoryBlockCount= memoryBlockCount;
        this.setSize= setCount;
        this.cache= new Set [this.blockCount / this.setSize];
        intlCacheArray();

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
        intlCacheArray();
        //initialize outputs
        this.memoryAccessCount= 0;
        this.hitCount= 0;
        this.missCount= 0;
        this.hitRate= 0;
        this.missRate= 0;
        this.avgMemoryAccessTime= 0;
        this.totalMemoryAccessTime= 0;
    }

    private void intlCacheArray () {
        for (int i = 0; i < this.cache.length; i++) {
            this.cache[i] = new Set();
        }
    }

    public ArrayList<String> simulateCache(int[] sequence) {
        simLog = new ArrayList<String>(); // treat as print statements
        int setIndex, foundBlockIndex, sequenceData;

        for (int i = 0; i < sequence.length; i++) {
            setIndex = sequence[i] % this.setSize;
            sequenceData = sequence[i];

            foundBlockIndex = cache[setIndex].findSequence(sequence[i]); // is sequence data in the set?

            if (foundBlockIndex != -1) {
                // add info to the log array
                simLog.add("Data: " + sequenceData + " was found in Set " + setIndex + ", Block " + foundBlockIndex);
                this.hitCount++;
            } else {
                // add info to the log array
                simLog.add("Data: " + sequenceData + " not in cache. Added to Set " + setIndex);
                this.missCount++;
            }
            // update GUI
            if (view != null) {
                view.updateGUI(sequenceData, setIndex, foundBlockIndex);
            }
        }

        simLog.add(calculateOutputs());
        return simLog;
    }

    public String calculateOutputs(){
        float totalCount = hitCount + missCount;
        hitRate = hitCount / totalCount;
        missRate = missCount / totalCount;
        int missPenalty = 1 + cacheLine * 10 + 1;

        // CHANGE TO PROPER FORMULA
        avgMemoryAccessTime = hitRate + (1 - hitRate) * missPenalty;
        totalMemoryAccessTime = avgMemoryAccessTime * blockCount;

        return String.format("Hit Rate: %.2f\nMiss Rate: %.2f\nAvg Memory Access Time: %.2f\nTotal Memory Access Time: %.2f",
                            hitRate, missRate, avgMemoryAccessTime, totalMemoryAccessTime);
    }

    public ArrayList<String> getSimLog() {
        return simLog;
    }

}
