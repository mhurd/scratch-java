package com.mhurd.scratch;

import java.math.BigDecimal;
import java.util.function.BiFunction;

public class EffectivelyFinal {

    private BiFunction<Integer, BigDecimal, BigDecimal> getTotalBrokerageForTrade(BigDecimal brokerageAmount) {
        // Java is pass-by-value, the argument here is a *copy* of the *pointer* to the BigDecimal object in the heap.
        // In this scope no modification to this pointer is made so it's effectively-final. Note that if you reassigned
        // this variable within this method after the lambda is created you'd get the expected compile error.
        return (key, value) -> value != null ? value.add(brokerageAmount) : brokerageAmount;
    }

    private void run() {
        // this works OK
        BigDecimal brokerageAmount1 = BigDecimal.ONE;
        BiFunction<Integer, BigDecimal, BigDecimal> bf1 = getTotalBrokerageForTrade(brokerageAmount1);
        // this re-assignment only affects the pointer in *this* scope (a *copy* of this
        // pointer was passed into the getTotalBrokerageForTrade method and remains valid).
        brokerageAmount1 = BigDecimal.TEN;
        // this doesn't work as the same brokerage amount pointer used in the Lambda *is* being modified
        BigDecimal brokerageAmount2 = BigDecimal.ONE;
        BiFunction<Integer, BigDecimal, BigDecimal> bf2 = (key, value) -> value != null ? value.add(brokerageAmount2) : brokerageAmount2;
        brokerageAmount2 = BigDecimal.TEN;
        // its worth reviewing this: http://javadude.com/articles/passbyvalue.htm
    }

    public static void main(String[] args) {
        new EffectivelyFinal().run();
    }

}
