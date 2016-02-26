package lmd.boh.glassgame;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * The class performs a "random walk" through all the possible states of
 * the game until it finds a solution. A solution is when at least one
 * of the glasses contains exactly the goal quantity.
 * @author Luigi Maria Damato
 */
public class Solver {

    /**
     * Helper inner class to keep track of the steps.
     * @author Luigi Maria Damato
     */
    private static class Node {
        /**
         * The current state
         */
        final GlassState state;

        /**
         * The previous node
         */
        final Node previous;

        /**
         * Create new node, with current state and previous node passed as
         * arguments
         * @param s the current state
         * @param p the previous node
         */
        private Node(final GlassState s, final Node p) {
            state = s;
            previous = p;
        }
    }

    /**
     * The number of moves needed to solve the game
     */
    private final int moves;

    /**
     * The list of steps from start to the solution
     */
    private final Iterable<Node> result;

    /**
     * Solve a game with a goal quantity and several glasses with their
     * capacities
     * @param goal the goal quantity
     * @param capacities the capacities of the glasses
     */
    public Solver(final int goal, final int... capacities) {
        if (goal < 0 || capacities == null || capacities.length == 0) {
            throw new IllegalArgumentException();
        }

        Set<Glass> s = new HashSet<Glass>();

        // ensure at least one glass with capacity larger than goal
        boolean isProbablySolvable = false;
        for (int i = 0; i < capacities.length; i++) {
            if (goal < capacities[i]) {
                isProbablySolvable = true;
            }
            s.add(new Glass(capacities[i], 0));
        }

        if (isProbablySolvable) {
            GlassState initial = new GlassState(s);

            //keeps track of the previous states
            Set<GlassState> history = new HashSet<GlassState>();
            history.add(initial);

            Queue<Node> q = new LinkedList<Node>();
            q.add(new Node(initial, null));

            // random walk
            while (!q.isEmpty() && !isSolution(q.peek().state, goal)) {
                Node n = q.poll();

                for (GlassState gs : nextStates(n.state)) {
                    if (!history.contains(gs)) {
                        q.add(new Node(gs, n));
                        history.add(gs);
                    }
                }
            }

            if (q.isEmpty()) {
                moves = -1;
                result = null;
            } else {
                // reconstruct solution path
                Node end = q.peek();
                Deque<Node> d = new LinkedList<Node>();
                while (end != null) {
                    d.addFirst(end);
                    end = end.previous;
                }
                result = d;
                moves = d.size() - 1;
            }
        } else {
            moves = -1;
            result = null;
        }
    }

    /**
     * Returns a string representation of the solution
     * @return a string representation of the solution
     */
    public final String result() {
        if (moves == -1) {
            return "No solution possible.";
        }

        StringBuilder sb = new StringBuilder("# moves: " + moves + "\n");
        int i = 0;
        for (Node n : result) {
            sb.append("Step " + (i++) + ":\n");
            sb.append(n.state.toString() + "\n");
        }
        return sb.toString();
    }

    /**
     * Returns a collection of all the possible states following the input one.
     * @param state the current state
     * @return a collection of all the possible states following the input one
     */
    private Set<GlassState> nextStates(final GlassState state) {
        Set<GlassState> res = new HashSet<GlassState>();

        Set<Glass> glasses = new HashSet<Glass>();
        for (Glass g : state) {
            glasses.add(g);
        }

        // empty the glass g
        for (Glass g : glasses) {
            if (g.getContent() != 0) {
                Set<Glass> temp = new HashSet<Glass>(glasses);
                temp.remove(g);
                temp.add(Glass.empty(g));
                res.add(new GlassState(temp));
            }
        }

        // fill the glass g
        for (Glass g : glasses) {
            if (g.getContent() < g.getMaxCapacity()) {
                Set<Glass> temp = new HashSet<Glass>(glasses);
                temp.remove(g);
                temp.add(Glass.fill(g));
                res.add(new GlassState(temp));
            }
        }

        // pour from glass g to glass h
        for (Glass g : glasses) {
            for (Glass h : glasses) {
                if (g != h && g.getContent() > 0
                        && h.getContent() < h.getMaxCapacity()) {

                    Set<Glass> temp = new HashSet<Glass>(glasses);
                    temp.remove(g);
                    temp.remove(h);
                    temp.addAll(Glass.pour(g, h));
                    res.add(new GlassState(temp));
                }
            }
        }

        return res;
    }

    /**
     * Check whether a given state is a solution
     * @param n the input state
     * @param goal the goal quantity
     * @return true if the state is a solution, false otherwise
     */
    private boolean isSolution(final GlassState n, final int goal) {
        for (Glass g : n) {
            if (g.getContent() == goal) {
                return true;
            }
        }
        return false;
    }

    /**
     * Testing.
     * @param args
     */
    public static final void main(String... args) {
        int[] capacities = { 4, 9, 17, 51 };
        long time = System.currentTimeMillis();
        Solver s = new Solver(41, capacities);
        time = System.currentTimeMillis() - time;
        
        System.out.println(s.result() + "\n\nTime: " + time);
    }
}
