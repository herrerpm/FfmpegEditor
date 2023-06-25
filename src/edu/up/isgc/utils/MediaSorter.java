package edu.up.isgc.utils;

import java.util.Comparator;

public class MediaSorter implements Comparator<Media> {

    /**
     * A compare method to compare Media by date.
     * @param media1 the first object to be compared.
     * @param media2 the second object to be compared.
     * @return An int representing the lexicographic order
     */
    @Override
    public int compare(final Media media1, final Media media2) {
        return media1.getDate().compareTo(media2.getDate());
    }
}
