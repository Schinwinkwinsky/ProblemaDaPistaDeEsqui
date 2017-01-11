/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cleiton
 */
public class Elevador implements Runnable {

    Filas filas;
    int vazio = 4;
    int contadorElevador = 1;

    public Elevador(Filas f) {
        filas = f;
    }

    public Filas getFilas() {
        return filas;
    }

    public void setFilas(Filas filas) {
        this.filas = filas;
    }

    @Override
    public synchronized void run() {
        while (true) {
            boolean leftTriple = false;
            boolean rightTriple = false;

            if (filas.getLeftTriple().size() > 2
                    && vazio > 2) {
                for (int i = 2; i >= 0; i--) {
                    filas.getLeftTriple().remove();
                    vazio--;
                }
                leftTriple = true;
            }

            if (filas.getRightTriple().size() > 2
                    && vazio > 2) {
                for (int i = 2; i >= 0; i--) {
                    filas.getRightTriple().remove();
                    vazio--;
                }
                rightTriple = true;
            }

            //Caso não tenha embarcado ninguém das filas anteriores.
            if (!leftTriple && !rightTriple) {

                //Utilizada para alternar nas filas.
                boolean chave = true;

                while (vazio > 0
                        && (filas.getLeftSingle().size() > 0
                        || filas.getRightSingle().size() > 0)) {

                    if (filas.getLeftSingle().size() > 0
                            && chave) {
                        filas.getLeftSingle().remove();
                        vazio--;
                        chave = false;
                    }

                    if (filas.getRightSingle().size() > 0
                            && !chave) {
                        filas.getRightSingle().remove();
                        vazio--;
                        chave = true;
                    }
                }
            } else {
                if (leftTriple
                        && filas.getRightSingle().size() > 0) {
                    filas.getRightSingle().remove();
                    vazio--;
                }

                if (rightTriple
                        && filas.getLeftSingle().size() > 0) {
                    filas.getLeftSingle().remove();
                    vazio--;
                }
            }

            imprimirElevador();

            try {
                this.wait(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Elevador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void imprimirElevador() {

        System.out.println();
        System.out.println("Elevador: " + contadorElevador);
        switch (vazio) {
            case 4:
                System.out.println("----");
                break;
            case 3:
                System.out.println("*---");
                break;
            case 2:
                System.out.println("**--");
                break;
            case 1:
                System.out.println("***-");
                break;
            default:
                System.out.println("****");
                break;
        }

        System.out.println();
        System.out.println("Filas após a ida do elevador:");
        System.out.println("LeftSingle: " + filas.getLeftSingle().size());
        System.out.println("RightSingle: " + filas.getRightSingle().size());
        System.out.println("LeftTriple: " + filas.getLeftTriple().size());
        System.out.println("RightTriple: " + filas.getRightTriple().size());
        System.out.println("============================================");

        contadorElevador++;
        vazio = 4;
    }
}
