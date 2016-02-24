package lmd.boh.glassgame;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Immutable lass defining a single state in the glass pouring game
 * @author Luigi Maria Damato
 *
 */
public class GlassState implements Iterable<Glass> {

    /**
     * Set containing all the glasses in the current status
     */
    private final Set<Glass> glasses;

    /**
     * Create a new empty state
     */
    private GlassState() {
        glasses = new HashSet<Glass>();
    }

    /**
     * Create a new state with the glasses passed as argument
     * @param g the glasses in this state
     */
    public GlassState(Collection<Glass> g) {
        this();
        glasses.addAll(g);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GlassState)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        GlassState g = (GlassState) o;
        if (this.glasses.size() != g.glasses.size()) {
            return false;
        }
        if (this == g) {
            return true;
        }
        return this.glasses.containsAll(g.glasses)
                && g.glasses.containsAll(this.glasses);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return glasses.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Glass g : glasses) {
            sb.append(g.toString() + "\n");
        }
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Glass> iterator() {
        return glasses.iterator();
    }
}
