/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Schinwinkwinsky
 */
public class Esquiador implements Runnable {

    Filas filas;
    long tempoEntrada;

    public Esquiador(Filas f) {
        this.filas = f;
    }

    public Filas getFilas() {
        return filas;
    }

    public void setFilas(Filas filas) {
        this.filas = filas;
    }

    public long tempoEmFila() {
        long tempoSaida = System.currentTimeMillis();

        return tempoSaida - tempoEntrada;
    }

    @Override
    public synchronized void run() {
        if (filas.getLeftSingle().size() < filas.getRightSingle().size()
                && filas.getLeftSingle().size() < (filas.getLeftTriple().size() * 2)
                && filas.getLeftSingle().size() < (filas.getRightTriple().size() * 2)) {
            filas.getLeftSingle().add(this);
            tempoEntrada = System.currentTimeMillis();

            System.out.println();
            System.out.println("Esquiador entrou na fila: LeftSingle");

        } else if (filas.getRightSingle().size() <= filas.getLeftSingle().size()
                && filas.getRightSingle().size() < (filas.getRightTriple().size() * 2)
                && filas.getRightSingle().size() < (filas.getLeftTriple().size() * 2)) {
            filas.getRightSingle().add(this);
            tempoEntrada = System.currentTimeMillis();

            System.out.println();
            System.out.println("Esquiador entrou na fila: RightSingle");
        } else if (filas.getLeftTriple().size() <= filas.getRightTriple().size()) {
            filas.getLeftTriple().add(this);
            tempoEntrada = System.currentTimeMillis();

            System.out.println();
            System.out.println("Esquiador entrou na fila: LeftTriple");
        } else {
            filas.getRightTriple().add(this);
            tempoEntrada = System.currentTimeMillis();

            System.out.println();
            System.out.println("Esquiador entrou na fila: RightTriple");
        }

        System.out.println();
        System.out.println("Total de esquiadores por fila:");
        System.out.println("LeftSingle: " + filas.getLeftSingle().size());
        System.out.println("RightSingle: " + filas.getRightSingle().size());
        System.out.println("LeftTriple: " + filas.getLeftTriple().size());
        System.out.println("RightTriple: " + filas.getRightTriple().size());
    }
}
