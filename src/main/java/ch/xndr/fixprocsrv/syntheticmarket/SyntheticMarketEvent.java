package ch.xndr.fixprocsrv.syntheticmarket;

public class SyntheticMarketEvent {

    private final SyntheticMarketTickDto data;
    public SyntheticMarketEvent(SyntheticMarketTickDto data) {
        this.data = data;
    }
    public SyntheticMarketTickDto getData() { return data; }

}
