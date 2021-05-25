package lab4;

import java.util.ArrayList;

public class First_Follow {
    static ArrayList<Production> grammar;
    static ArrayList<Production> First = new ArrayList<>();
    static ArrayList<Production> Follow = new ArrayList<>();

    public First_Follow (ArrayList<Production> grammar){
        this.grammar=grammar;
    }

    public ArrayList<Production> FirstCreation(){
        for (Production production: grammar) {
            if(production.getDestination().charAt(0) >= 'A' && production.getDestination().charAt(0) <= 'Z'){
                ArrayList<Character> symbols = Search(production.getDestination().charAt(0),'i');
                for (char t:symbols) {
                    First.add(new Production(production.getSource(), Character.toString(t)));
                }
            }
            if(production.getDestination().charAt(0) >= 'a' && production.getDestination().charAt(0) <= 'z'){
                First.add(new Production(production.getSource(),Character.toString(production.getDestination().charAt(0))));
            }
            if(production.getDestination().charAt(0) == '&'){
                First.add(new Production(production.getSource(),"&"));
            }

        }
        return First;
    }

    public ArrayList<Character> Search(char source,char ind){
        ArrayList<Character> symbols = new ArrayList<>();
        for (Production production: grammar) {
            if (production.getSource() == source){
                if(production.getDestination().charAt(0) >= 'A' && production.getDestination().charAt(0) <= 'Z'){
                    return Search(production.getDestination().charAt(0),ind);
                }
                if(production.getDestination().charAt(0) >= 'a' && production.getDestination().charAt(0) <= 'z'){
                    symbols.add(production.getDestination().charAt(0));
                }
                if(production.getDestination().charAt(0) == '&'){
                    if(ind == 'i'){
                        symbols.add('&');
                    }
                    if(ind == 'o'){
                        symbols.add('$');
                    }
                }
            }
        }
        return symbols;
    }

    public ArrayList<Production> FollowCreation (){
        boolean bool=false;
        for (Production production: grammar){
            for (Production productionJ:grammar){
                if(productionJ.getDestination().contains(Character.toString(production.getSource()))){
                    bool=true;
                    int followIndex = productionJ.getDestination().indexOf(production.getSource())+1;
                    if (productionJ.getDestination().endsWith(Character.toString(productionJ.getDestination().charAt(followIndex-1)))){
                        boolean bool2 = false;
                        for(int i = 0;i < Follow.size(); i++){
                            if (Follow.get(i).getSource() == productionJ.getSource()){
                                bool2=true;
                                Follow.add(new Production(production.getSource(),Follow.get(i).getDestination()));
                            }
                        }
                        break;
                    }
                    else {
                        if(productionJ.getDestination().charAt(followIndex) >= 'A' && productionJ.getDestination().charAt(followIndex) <= 'Z'){
                            ArrayList<Character> symbols = Search(productionJ.getDestination().charAt(followIndex),'o');
                            for (char t:symbols){
                                Follow.add(new Production(production.getSource(),Character.toString(t)));
                            }
                            break;
                        }
                        if(productionJ.getDestination().charAt(followIndex) >= 'a' && productionJ.getDestination().charAt(followIndex) <= 'z'){
                            Follow.add(new Production(production.getSource(),Character.toString(productionJ.getDestination().charAt(followIndex))));
                            break;
                        }

                    }
                }
            }
            if(!bool) Follow.add(new Production(production.getSource(), "$"));
        }
        LogicalMistake();
        Validation();
        return Follow;
    }

    public void Validation(){
        for (int i = 0; i < Follow.size(); i++){
            for (int j = i+1; j< Follow.size(); j++){
                if(Follow.get(i).getSource() == Follow.get(j).getSource() && Follow.get(i).getDestination() == Follow.get(j).getDestination())
                    Follow.remove(Follow.get(j));
            }
        }
    }

    public void LogicalMistake(){
        Follow.add(new Production('E',"d"));
    }
}