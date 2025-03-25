package org.campus02;

import java.util.ArrayList;
import java.util.Collections;

public class EinwohnerDemo {

    public static void main(String[] args) throws DataFileException {
        EinwohnerManager em = new EinwohnerManager();
        ArrayList<Einwohner> einwohnerList = em.load();
        System.out.println(einwohnerList);

        Collections.sort(einwohnerList);
        System.out.println(einwohnerList);
    }
}
