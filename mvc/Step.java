package mvc;

public class Step {
    private int sequenceData;
    private int setIndex;
    private int foundBlockIndex;
    private int sequenceIndex;

    public Step (int sequenceData, int setIndex, int foundBlockIndex, int sequenceIndex){
        this.sequenceData= sequenceData;
        this.setIndex= setIndex;
        this.foundBlockIndex= foundBlockIndex;
        this.sequenceIndex= sequenceIndex;
    }

    public int getSequenceData() {
        return sequenceData;
    }

    public int getSetIndex() {
        return setIndex;
    }

    public int getFoundBlockIndex() {
        return foundBlockIndex;
    }

    public int getSequenceIndex() {
        return sequenceIndex;
    }
}
