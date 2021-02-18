package lab1;

import java.util.*;

//varianta 23
public class fa {
    String start;
    Set<String> ends;
    Map<String, Map<Character, List<String>>> transitions; // state -> (character -> [next states])

    fa(String[] ss, String[] ts) {
        ends = new TreeSet<String>();
        transitions = new TreeMap<String, Map<Character,List<String>>>();

        // States
        for (String v : ss) {
            String[] pieces = v.split(",");
            if (pieces.length>1) {
                if (pieces[1].equals("S")) start = pieces[0];
                else if (pieces[1].equals("E")) ends.add(pieces[0]);
            }
        }

        // Transitions
        for (String e : ts) {
            String[] pieces = e.split(",");
            String from = pieces[0],
                    to = pieces[1];
            if (!transitions.containsKey(from)) transitions.put(from, new TreeMap<Character,List<String>>());
            for (int i=2; i<pieces.length; i++) {
                char c = pieces[i].charAt(0);
                if (!transitions.get(from).containsKey(c)) transitions.get(from).put(c, new ArrayList<String>());
                transitions.get(from).get(c).add(to);
            }
        }

        System.out.println("Transitions: " + transitions);
    }

    public boolean match(String s) {
        Set<String> currStates = new TreeSet<String>();
        currStates.add(start);
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            Set<String> nextStates = new TreeSet<String>();

            for (String state : currStates)
                if (transitions.get(state).containsKey(c))
                    nextStates.addAll(transitions.get(state).get(c));
            if (nextStates.isEmpty()) return false;
            currStates = nextStates;
        }

        for (String state : currStates) {
            if (ends.contains(state)) return true;
        }
        return false;
    }

    public void test(String name, String[] inputs) {
        System.out.println("Test strings:");
        for (String s : inputs)
            System.out.println(s + ": " + match(s));
    }

    public static void main(String[] args) {


        String[] ss = { "S,S", "B", "C", "X,E" };
        String[] ts = { "S,B,a", "B,C,a", "C,B,b", "C,X,c", "C,S,a", "B,B,b" };
        fa nfa2 = new fa(ss, ts);

        String[] test = { "abbabbabababbac", "aababaaaaaabac", "acacbaca"};
        nfa2.test("test", test);

    }

}
