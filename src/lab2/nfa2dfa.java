package lab2;

public class nfa2dfa {
    public static void main(String[] args) {
        String[] Q = {"q0","q1","q2"};
        String[] L = {"a","b"};
        String S = "q0";
        String F = "q2";
        String[] rules ={ "s(q0,a) = q0",
                "s(q0,a) = q1",
                "s(q1,b) = q2",
                "s(q0,b) = q0",
                "s(q2,b) = q2",
                "s(q1,a) = q0"};
        String[][] matrix = createMatrix(Q,L,S,F,rules);
        System.out.println("The NFA: ");
        for(int i = 0; i< 4; i++){
            for(int j = 0; j< 4; j++){
                System.out.print(" " + matrix[i][j] + " ");
                System.out.print("|");
            }
            System.out.println();
        }
        dfa_building(matrix);
        System.out.println();

        System.out.println("The DFA : ");
        System.out.println("    |   | a | b |");
        for(int i = 1; i < 6; i=i+2){
            for(int j = 0; j< 4; j++){
                System.out.print(" " + dfa[i][j] + " ");
                System.out.print("|");
            }
            System.out.println();
        }
    }

    public static int a=0;
    public static String[][] dfa = new String[50][4];
    public static String[][] createMatrix(String[] Vn, String[] Vt,String S, String F, String[] P){
        String[][] matrix = new String[4][4];
        for (int i= 0 ; i< 4; i++){
            for (int j = 0; j< 4; j++){
                matrix[i][j] = "0";
            }
        }
        matrix[0][0] = " "; matrix[0][1] = " ";
        for(int i = 1; i<4; i++){
            matrix[i][1] = Vn[i-1];
            if(Vn[i-1].equals(F))
                matrix[i][0] = "*";
            else if(Vn[i-1].equals(S))
                matrix[i][0] = "->";
        }
        for(int j = 2; j<4;j++){
            matrix[0][j]=Vt[j-2];
        }
        for(int i=0;i<6;i++){
            int m,n;
            switch (P[i].charAt(3)) {
                case '0':
                    m = 1;
                    break;
                case '1':
                    m = 2;
                    break;
                case '2':
                    m = 3;
                    break;
                default:
                    m = 0;
            }

            switch (P[i].charAt(5)) {
                case 'a':
                    n = 2;
                    break;
                case 'b':
                    n = 3;
                    break;
                default:
                    n = 0;
            }

            if(matrix[m][n].equals("0"))
                matrix[m][n] = "q" + P[i].charAt(11);
            else
                matrix[m][n] += "q" + P[i].charAt(11);
        }
        return matrix;
    }
    public static void input(String[][] matrix,int l){
        String from = dfa[l][1];
        while (from != null){
            String q ;
            if(from.length() == 2){
                q = from;
                from = null;
            } else{
                q = from.substring(0,2);
                from = from.substring(2);
            }

            int n = 0;
            switch (q) {
                case "q0":
                    n = 1;
                    break;
                case "q1":
                    n = 2;
                    break;
                case "q2":
                    n = 3;
                    break;
                default:
            }
            if(!matrix[n][2].equals("0")){
                if(dfa[l][2] == null){
                    dfa[l][2] = matrix[n][2];
                }else if (!dfa[l][2].contains(matrix[n][2]))
                    dfa[l][2] += matrix[n][2];}
            if(!matrix[n][3].equals("0")){
                if(dfa[l][3] == null){
                    dfa[l][3] = matrix[n][3];
                }else if (!dfa[l][3].contains(matrix[n][3]))
                    dfa[l][3] += matrix[n][3];}
        }
    }

    public static boolean exist(char o,String str){
        for(int i=0; i<str.length();i++){
            if(str.charAt(i)==o)
                return true;
        }
        return false;
    }

    public static boolean exists(String main, int n){
        int i = 0;
        int count ;
        while (i<n-1){
            if(main.length() != dfa[i][1].length()){
                i++;
            }
            else {
                count = 0;
                for (int j=0; j < main.length();j++){
                    if(exist(main.charAt(j),dfa[i][1])){
                        count++;
                    }
                }
                if(count==main.length())
                    return true;
                else i++;
            }
        }
        return false;
    }


    public static void dfa_building(String[][] matrix){
        int i =0;
        //filling in the first line
        while(i<2){
            for(int j =0;j<4;j++){
                dfa[a][j] = matrix[i][j];
            }
            a++;
            i++;}
        i=2;
        a=1;
        //i for the second column, k for the third and fourth
        while (dfa[a][2] != null) {
            // if the second column does not exist, add a row
            if (!dfa[a][2].equals("0")) {
                if (!exists(dfa[a][2], i)) {
                    dfa[i][1] = dfa[a][2];
                    input(matrix, i);
                    i++;
                }
            }
            // if the third column does not exist, add a row
            if (!dfa[a][3].equals("0")) {
                if (!exists(dfa[a][3], i)) {
                    dfa[i][1] = dfa[a][3];
                    input(matrix, i);
                    i++;
                }
            }
            a++;
        }
    }


}
