package lab4;

public class Production {
    char source;
    String destination;

    public Production(char source, String destination){
        this.source = source;
        this.destination = destination;
    }

    public char getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}