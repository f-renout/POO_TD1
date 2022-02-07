package exo1;

import java.util.Scanner;

public class Factorielle1 {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.print("entrer le nombre: ");
        int num= Integer.parseInt(sc.nextLine());
        long result = 1;
        for(int i=1; i<=num; i++){
            result*=i;
        }
        System.out.printf("%d!=%d%n",num,result);
    }
}
