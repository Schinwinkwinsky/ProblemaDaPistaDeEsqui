/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
    boolean leftTriple = false;
    boolean rightTriple = false;

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
            try {
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
                    while(vazio > 0){
                        if (filas.getLeftSingle().size() > 0) {
                            filas.getLeftSingle().remove();
                            vazio--;
                        }
                        
                        if(filas.getRightSingle().size() > 0){
                            filas.getRightSingle().remove();
                            vazio--;
                        }
                    }
                    
                    while (filas.getLeftSingle().size() > 0
                            && vazio > 0) {
                        filas.getLeftSingle().remove();
                        vazio--;
                    }

                    while (filas.getRightSingle().size() > 0
                            && vazio > 0) {
                        filas.getRightSingle().remove();
                        vazio--;
                    }
                }else{
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
                
                leftTriple = true;
                rightTriple = true;

                imprimirElevador();

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
