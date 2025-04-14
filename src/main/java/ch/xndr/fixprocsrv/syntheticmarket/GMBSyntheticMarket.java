package ch.xndr.fixprocsrv.syntheticmarket;

import java.math.BigDecimal;
import java.util.Random;



public class GMBSyntheticMarket {

    public final String symbol;
    private final Double sigma;
    private final Double mu;
    private final Double deltaT; //Delta T
    private final BigDecimal x0;
    private Random rand;
    private BigDecimal prevValue;

    public GMBSyntheticMarket(String symbol, Double sigma, Double mu, Double deltaT, BigDecimal x0) {
        this.symbol = symbol;
        this.sigma = sigma;
        this.mu = mu;
        this.deltaT = deltaT;
        this.x0 = x0;
        this.prevValue = x0;
        rand = new Random();
    }

    public BigDecimal generateNextValue() {
        double gaussian = rand.nextGaussian();
        double drift = mu * deltaT;
        double diffusion = sigma * Math.sqrt(deltaT) * gaussian;
        double multiplier = 1 + drift + diffusion;

        prevValue = prevValue.multiply(BigDecimal.valueOf(multiplier));
        return prevValue;
    }
}
