package exo1;

import java.util.Scanner;

public class Factorielle2 {
    public long fact(int n){
        if(n<2){
            return 1;
        }
        return n*fact(n-1);
    }

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.print("entrer le nombre: ");
        int num= Integer.parseInt(sc.nextLine());
        Factorielle2 f2 = new Factorielle2();
        System.out.printf("%d!=%d%n",num,f2.fact(num));
    }
}
