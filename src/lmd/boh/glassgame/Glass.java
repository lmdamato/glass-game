package lmd.boh.glassgame;

import java.util.HashSet;
import java.util.Set;

/**
 * Immutable class defining the Glass object, with a
 * certain capacity and current quantity
 * @author Luigi Maria Damato
 *
 */
public class Glass {

    /**
     * The maximum quantity the glass can contain
     */
    private final int maxCapacity;

    /**
     * The quantity contained by the glass
     */
    private final int content;

    /**
     * Instantiate new glass, with specified max capacity and content
     * @param max The maximum capacity of the glass
     * @param cur The current content of the glass
     */
    public Glass(final int max, final int cur) {
        if (max <= 0 || cur < 0 || cur > max) {
            throw new IllegalArgumentException();
        }
        maxCapacity = max;
        content = cur;
    }

    /**
     * Returns the max capacity of the glass
     * @return the max capacity of the glass
     */
    public final int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Returns the content of the glass
     * @return the content of the glass
     */
    public final int getContent() {
        return content;
    }

    /**
     * Static method that returns a new empty glass with the same capacity of
     * the glass passed as argument
     * @param g the glass to be emptied
     * @return a new, empty glass
     */
    public static Glass empty(final Glass g) {
        if (g == null) {
            throw new NullPointerException();
        }
        if (g.content == 0) {
            return g;
        }
        return new Glass(g.maxCapacity, 0);
    }

    /**
     * Static method that returns a new full glass with the same capacity of the
     * glass passed as argument
     * @param g the glass to be filled
     * @return a new, full glass
     */
    public static Glass fill(final Glass g) {
        if (g == null) {
            throw new NullPointerException();
        }
        if (g.content == g.maxCapacity) {
            return g;
        }
        return new Glass(g.maxCapacity, g.maxCapacity);
    }

    /**
     * Pours from the first glass to the second, until h is full or g is empty
     * @param g the glass from which we pour
     * @param h the glass to which we pour
     * @return a set containing exactly two glasses, equivalent to the input
     *         ones after the pouring
     */
    public static Set<Glass> pour(final Glass g, final Glass h) {
        if (g == null || h == null) {
            throw new NullPointerException();
        }

        Set<Glass> res = new HashSet<Glass>();

        if (h.getContent() == h.getMaxCapacity()
        || g.getContent() == 0) {
            res.add(g);
            res.add(h);
            return res;
        }

        int q = Math.min(g.getContent(),
        h.getMaxCapacity() - h.getContent());

        res.add(new Glass(h.getMaxCapacity(), h.getContent() + q));
        res.add(new Glass(g.getMaxCapacity(), g.getContent() - q));

        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object o) {
        if (o == null || !(o instanceof Glass)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        Glass g = (Glass) o;
        return this.maxCapacity == g.maxCapacity
        && this.content == g.content;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        return "Capacity: " + maxCapacity + ", content: " + content;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
        int p = 31;
        int hash1 = ((Integer) maxCapacity).hashCode();
        int hash2 = ((Integer) content).hashCode();
        return p * hash1 + hash2;
    }
}
