package mvc;

import java.util.*;

public class Controller {
    private View view;

    public Controller(View view) {
        this.view = view;
        // Register the controller with the view
        this.view.registerController(this);
    }

    // Method to start the simulation
    public void startSimulation(int[] sequence) {
        // User input from the view
        int blockCount = view.getBlockCount();
        int cacheLine = view.getCacheLine();
        String readPolicy = view.getReadPolicy();
        int memoryBlockCount = view.getMemoryBlockCount();
        int setCount = view.getSetCount();

        // Create a Cache object with user input
        Cache cache = new Cache(blockCount, cacheLine, readPolicy, memoryBlockCount, setCount);

        // Simulate the cache using the provided sequence
        cache.simulateCache(sequence);

        // Retrieve simulation log from the Cache model
        ArrayList<String> simLog = cache.getSimLog();
    }

}
