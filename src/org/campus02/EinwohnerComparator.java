package org.campus02;

import java.util.Comparator;

public class EinwohnerComparator implements Comparator<Einwohner> {
    @Override
    public int compare(Einwohner o1, Einwohner o2) {
        int res = Integer.compare(o2.getGeburtsjahr(), o1.getGeburtsjahr());
        if (res == 0) {
            res = o1.getName().compareTo(o2.getName());
        }
        return res;
    }
}
