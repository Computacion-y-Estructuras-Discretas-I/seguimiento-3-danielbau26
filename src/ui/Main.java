package ui;

import java.util.HashSet;
import java.util.Scanner;
import structures.PilaGenerica;
import structures.TablasHash;

public class Main {

    private Scanner sc;

    public Main() {
        sc = new Scanner(System.in);
    }

    public void ejecutar() throws Exception {
        while (true) {
            System.out.println("\nSeleccione la opcion:");
            System.out.println("1. Punto 1, Verificar balanceo de expresión");
            System.out.println("2. Punto 2, Encontrar pares con suma objetivo");
            System.out.println("3. Salir del programa");
            System.out.print("Opcion: ");

            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese expresion a verificar:");
                    String expresion = sc.nextLine();
                    boolean resultado = verificarBalanceo(expresion);
                    System.out.println("Resultado: " + (resultado ? "TRUE" : "FALSE"));
                    break;

                case 2:
                    System.out.println("Ingrese numeros separados por espacio: ");
                    String lineaNumeros = sc.nextLine();
                    System.out.println("Ingrese suma objetivo: ");
                    int objetivo = Integer.parseInt(sc.nextLine());

                    String[] partes = lineaNumeros.trim().split("\\s+");
                    int[] numeros = new int[partes.length];
                    for (int i = 0; i < partes.length; i++) {
                        numeros[i] = Integer.parseInt(partes[i]);
                    }

                    encontrarParesConSuma(numeros, objetivo);
                    break;

                case 3:
                    System.out.println("Chao");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opcion no permitida");
            }
        }
    }

    /**
     * Verifica si la expresion esta balanceada usando PilaGenerica
     * @param s expresion a verificar
     * @return true si esta balanceada, false si no
     */
    public boolean verificarBalanceo(String s) {
        PilaGenerica<Character> pila = new PilaGenerica<>(s.length());
        char[] letras = s.toCharArray();

        if(letras.length == 0){
            return true;
        }

        for(int i=0; i<s.length(); i++){
            char c = letras[i];
            if(c == '(' || c == '[' || c == '{' ){
                pila.Push(c);
            }else if(c == '}' || c == ']' || c == ')' ){
                char cabeza = pila.Pop();
                if(c== ')' && cabeza != '(' || c== ']' && cabeza != '[' || c== '}' && cabeza != '{'){
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Encuentra y muestra todos los pares unicos de numeros que sumen objetivo usando TablasHash.
     * @param numeros arreglo de numeros enteros
     * @param objetivo suma objetivo
     */
    public void encontrarParesConSuma(int[] numeros, int objetivo) throws Exception {
        int size = numeros.length;
        TablasHash th = new TablasHash(size);

        for(int i=0; i<size; i++){
            int n = numeros[i];
            try {
                th.insert(n, n);
            } catch (Exception e) {

            }
            
        }

        HashSet<String> paresUnicos = new HashSet<>();

        for(int i=0; i<size; i++){
            String par = "";
            int n = numeros[i];

            int numeroFaltante = objetivo - n;
            boolean sr = false;
            try {
                sr = th.search(numeroFaltante, numeroFaltante);
            } catch (Exception e) {
            }

            if(sr && numeroFaltante!=n){
                if(n>numeroFaltante){
                    par = "(" + numeroFaltante + ", " + n + ")";
                }else{
                    par = "(" + n + ", " + numeroFaltante + ")";
                }

                paresUnicos.add(par);
            }
        }

        if(paresUnicos.size() == 0){
            System.out.println("Ningún par encontrado");
        }else{
            System.out.println(paresUnicos);
        }


    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        app.ejecutar();
    }
}
