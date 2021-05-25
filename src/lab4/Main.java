package lab4;

import java.util.ArrayList;
import java.util.HashSet;

//Variant 10

public class Main {
    static ArrayList<Production> grammar = new ArrayList();
    static ArrayList<Production> First = new ArrayList<>();
    static ArrayList<Production> Follow = new ArrayList<>();
    public static void main(String[] args) {
        String inputGrammar = "S->A;A->C;A->AcC;C->a;C->b;C->dD;D->Ae";
        StringBuilder input = new StringBuilder("dacbcbeca$");
        String terminals = "abcde";

        ArrayList<Character> terminal = new ArrayList<>();
        HashSet<Character> nonTerminal = new HashSet<>();
        for (int i = 0; i < terminals.length(); i++) {
            terminal.add(terminals.charAt(i));
        }

        String[] array = inputGrammar.split(";");
        introduceGrammar(array);

        System.out.println("\n" + "Grammar:");
        for (Production production : grammar)
        {
            System.out.println(production.getSource() + "->" + production.getDestination());
        }

        System.out.println("\n" + "Left Recursion:");
        LeftRecursion();
        System.out.println("\n" + "Left Factoring:");
        LeftFactoring();
        First_Follow first_follow = new First_Follow(grammar);
        First = first_follow.FirstCreation();

        System.out.println("\n" + "First:");
        for (Production production : First)
        {
            System.out.println(production.getSource() + "->" + production.getDestination());
        }

        System.out.println("\n" + "Follow:");
        Follow = first_follow.FollowCreation();
        for (Production production : Follow)
        {
            System.out.println(production.getSource() + "->" + production.getDestination());
        }

        for (Production production:grammar) {
            nonTerminal.add(production.getSource());
        }

        terminal.add('$');
        Parser parser = new Parser(grammar,First,Follow,terminal,nonTerminal);
        String[][] matrix = parser.Matrix();

        System.out.println("\n" + "Predictive Matrix:");
        for (int i = 0; i < nonTerminal.size(); i++) {
            for (int j = 0; j < terminal.size(); j++) {
                System.out.print(matrix [i][j]+" ");
            }
            System.out.println();
        }

        System.out.println("\n" + "Parser:");
        parser.Parsing(matrix, input);

    }

    public static void introduceGrammar(String[] array){
        for (String gr:array) {
            grammar.add(new Production(gr.charAt(0),gr.substring(3)));
        }
    }


    public static void LeftRecursion (){
        char newSource = RandomAlphabet();
        for (int i = 0; i <= grammar.size()-1; i++) {
            if(grammar.get(i).getSource() == grammar.get(i).getDestination().charAt(0)){
                String alpha = grammar.get(i).getDestination().substring(1);
                for ( int j = 0; j <= grammar.size()-1; j++) {
                    if(grammar.get(j).getSource()== grammar.get(i).getSource() && grammar.get(j).getSource()!=grammar.get(j).getDestination().charAt(0) ){
                        String beta = grammar.get(j).getDestination();
                        Production betaProduction = new Production(grammar.get(i).getSource(),beta.concat(Character.toString(newSource)));
                        grammar.add(betaProduction);
                        System.out.println(betaProduction.getSource() + "->" + betaProduction.getDestination());
                        Production alphaProduction = new Production(newSource,alpha.concat(Character.toString(newSource)));
                        grammar.add(alphaProduction);
                        System.out.println(alphaProduction.getSource() + "->" + alphaProduction.getDestination());
                        Production newProduction = new Production(newSource,"&");
                        grammar.add(newProduction);
                        System.out.println(newProduction.getSource() + "->" + newProduction.getDestination());
                        grammar.remove(grammar.get(i));
                        break;
                    }
                }
            }
        }
    }

    public static void LeftFactoring (){
        for (int i = 0; i <= grammar.size()-1; i++) {
            for (int j = i+1; j <= grammar.size()-1; j++) {
                if(grammar.get(i).getSource()==grammar.get(j).getSource() && grammar.get(i).getDestination().charAt(0) == grammar.get(j).getDestination().charAt(0)){
                    char newSource = RandomAlphabet();
                    Production firstProduction = new Production(newSource,grammar.get(j).getDestination().substring(1));
                    grammar.add(firstProduction);
                    System.out.println(firstProduction.getSource() + "->" + firstProduction.getDestination());
                    Production secondProduction = new Production(newSource, "&");
                    grammar.add(secondProduction);
                    System.out.println(secondProduction.getSource() + "->" + secondProduction.getDestination());
                    grammar.get(i).setDestination(grammar.get(i).getDestination().concat(Character.toString(newSource)));
                    grammar.remove(grammar.get(j));
                }
            }
        }
    }


    public static char RandomAlphabet(){

        ArrayList<Character> alphabet = new ArrayList();
        for(char i = 'A'; i <= 'Z'; ++i) {
            boolean bool= false;
            for (Production production:grammar) {
                if (production.getSource()==i) bool=true;
            }
            if (bool ==false) alphabet.add(i);
        }
        return alphabet.get(0);
    }
}
