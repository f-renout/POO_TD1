package exo2;

import java.util.Scanner;

public class Fibbo2 {
    private static long compteur =0;

    private final int nombre;
    private final long[] tampon;

    public Fibbo2(int nombre) {
        this.nombre=nombre;
        this.tampon = new long[nombre+1];
        tampon[1]=1;
    }

    public long fib(){
        return innerFib(nombre);
    }

    private long innerFib(int n){
        compteur ++;
        if(n<2){
            return n;
        }
        if(tampon[n]==0){
            tampon[n]=innerFib(n-1)+innerFib(n-2);
        }
        return tampon[n];

    }

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.print("entrer le nombre: ");
        int num= Integer.parseInt(sc.nextLine());
        Fibbo2 fib = new Fibbo2(num);
        System.out.printf("Fib(%d)=%d en %d appels%n",num, fib.fib(), compteur);
    }
}
