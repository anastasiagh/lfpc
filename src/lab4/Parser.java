package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Parser {

    static ArrayList<Production> grammar;
    static ArrayList<Production> First;
    static ArrayList<Production> Follow;
    static ArrayList<Character> terminal;
    static HashSet<Character> nonTerminal;
    static ArrayList<Character> nonTerm = new ArrayList<>();

    public Parser (ArrayList<Production> grammar, ArrayList<Production> First, ArrayList<Production> Follow, ArrayList<Character> terminal, HashSet<Character> nonTerminal){
        this.grammar=grammar;
        this.First = First;
        this.Follow = Follow;
        this.terminal=terminal;
        this.nonTerminal=nonTerminal;
    }

    public String[][] Matrix(){
        int ter = terminal.size();
        int non =nonTerminal.size();
        nonTerm.addAll(nonTerminal);
        String [][] matrix = new String [non][ter];
        for (String[] row: matrix) {
            Arrays.fill(row, " ");
        }
        for (Production production: First){
            int sourceIndex = nonTerm.indexOf(production.getSource());
            int destinationIndex;

            if(production.getDestination().equals("&")){
                for (Production productionK : Follow){
                    if (productionK.getSource() == production.getSource()){
                        destinationIndex=terminal.indexOf(productionK.getDestination().charAt(0));
                        matrix[sourceIndex][destinationIndex] = "&";
                        break;
                    }
                }
            }
            else {
                for (Production productionJ : grammar) {
                    if (productionJ.getSource() == production.getSource()){
                        destinationIndex = terminal.indexOf(production.getDestination().charAt(0));
                        matrix[sourceIndex][destinationIndex] = productionJ.getDestination();
                        break;
                    }
                }
            }
        }
        return matrix;
    }

    public void Parsing (String[][] matrix, StringBuilder input){
        StringBuilder action = new StringBuilder("S$");
        StringBuilder finalResult = new StringBuilder("");
        while ((action.compareTo(finalResult)!=0) && (input.compareTo(finalResult)!=0)){
            if(action.charAt(0) == input.charAt(0)) {
                input.deleteCharAt(0);
                action.deleteCharAt(0);
                System.out.println(action + " " + input);
            }
            else{
                int non = nonTerm.indexOf(action.charAt(0));
                int term = terminal.indexOf(input.charAt(0));
                if(matrix[non][term].equals(" ")){
                    System.out.println("Word is not valid");
                    break;
                }
                else {
                    if (matrix[non][term].equals("&")) {
                        action.deleteCharAt(0);
                        System.out.println(action + " " + input);
                    } else {
                        action.replace(0, 1, matrix[non][term]);
                        System.out.println(action + " " + input);
                    }
                }
            }
        }
        if ((action.compareTo(finalResult)==0) && (input.compareTo(finalResult)==0)){
            System.out.println("Word is valid");
        }
    }

}