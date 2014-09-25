package mhurd.scratch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericsTest {

    private static interface IGeneralTradeElement {
        public int getTradeRef();
    }

    private static class CacheTradeLeg implements IGeneralTradeElement {
        public int getTradeRef() {
            return 1;
        }
    }

    private static class CacheFxoTradeLeg implements IGeneralTradeElement {
        public int getTradeRef() {
            return 2;
        }
    }

    private static class CacheNdfTradeLeg implements IGeneralTradeElement {
        public int getTradeRef() {
            return 3;
        }
    }

    private static class CacheCompleteTradeGroup {

        private static Map CACHE = new HashMap();

        static {
            List<CacheTradeLeg> cacheTradeLegs = new ArrayList<CacheTradeLeg>();
            cacheTradeLegs.add(new CacheTradeLeg());
            CACHE.put(CacheTradeLeg.class, cacheTradeLegs);
            List<CacheFxoTradeLeg> cacheFxoTradeLegs = new ArrayList<CacheFxoTradeLeg>();
            cacheFxoTradeLegs.add(new CacheFxoTradeLeg());
            CACHE.put(CacheFxoTradeLeg.class, cacheFxoTradeLegs);
            List<IGeneralTradeElement> cacheNdfTradeLegs = new ArrayList<IGeneralTradeElement>();
            cacheNdfTradeLegs.add(new CacheNdfTradeLeg());
            CACHE.put(CacheNdfTradeLeg.class, cacheNdfTradeLegs);
        }

        private <T> List<T> getTradeElements(Class<T> elementClass) {
            return (List<T>)CACHE.get(elementClass);
        }

    }

    private <T extends IGeneralTradeElement> Map<Integer, List<T>> createTradeElementLookup(CacheCompleteTradeGroup ctg, Class<T> elementClass) {
        Map<Integer, List<T>> results = new HashMap();
        List<T> cacheTradeLegs = ctg.getTradeElements(elementClass);
        for (T ctl : cacheTradeLegs) {
            if (results.containsKey(ctl.getTradeRef())) {
                results.get(ctl.getTradeRef()).add(ctl);
            } else {
                List<T> legs = new ArrayList();
                legs.add(ctl);
                results.put(ctl.getTradeRef(), legs);
            }
        }
        return results;
    }

    public void enrichUsi(CacheCompleteTradeGroup ctg) {
        Map<Integer, List<CacheTradeLeg>> cacheTradeLegs = createTradeElementLookup(ctg, CacheTradeLeg.class);
        Map<Integer, List<CacheFxoTradeLeg>> cacheFxoTradeLegs = createTradeElementLookup(ctg, CacheFxoTradeLeg.class);
        Map<Integer, List<CacheNdfTradeLeg>> cacheNdfTradeLegs = createTradeElementLookup(ctg, CacheNdfTradeLeg.class);
        for (Integer key : cacheTradeLegs.keySet()) {
            for (CacheTradeLeg ctl : cacheTradeLegs.get(key)) {
                System.out.println("TradeRef: " + key + " -> " + ctl);
            }
        }
        for (Integer key : cacheFxoTradeLegs.keySet()) {
            for (CacheFxoTradeLeg ctl : cacheFxoTradeLegs.get(key)) {
                System.out.println("TradeRef: " + key + " -> " + ctl);
            }
        }
        for (Integer key : cacheNdfTradeLegs.keySet()) {
            for (CacheNdfTradeLeg ctl : cacheNdfTradeLegs.get(key)) {
                System.out.println("TradeRef: " + key + " -> " + ctl);
            }
        }
    }

    public static void main(String[] args) {
        GenericsTest gt = new GenericsTest();
        gt.enrichUsi(new CacheCompleteTradeGroup());
    }

}
