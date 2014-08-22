package com.example.zsolt.slidemenu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public enum Categories implements Iterable<Category> {
    INSTANCE;

    private final List<Category> elements = new ArrayList<Category>();

    Categories() {
        final Integer[] categoryResourceImage = {R.drawable.kat1_ingatlan, R.drawable.kat2_jarmu,
                R.drawable.kat3_othon, R.drawable.kat4_muszakicikk, R.drawable.kat5_sport, R.drawable.kat6_hasznalati,
                R.drawable.kat7_munka, R.drawable.kat8_uzlet, R.drawable.kat9_egyebb};
        final String[] categoryNames = {"Inagtalan", "Jármű", "Othon, háztartás", "Műszaki cikkek", "Szabadidő, sport",
                "Használati tárgyak", "Állás, munka", "Üzlet ", "Egyéb"};
        for (int position = 1; position < categoryNames.length; position++) {
            elements.add(new Category(categoryNames[position], categoryResourceImage[position], String.valueOf(position)));
        }
    }

    public Category get(int at) {
        return elements.get(at - 1);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public Iterator<Category> iterator() {
        return elements.iterator();
    }
}
