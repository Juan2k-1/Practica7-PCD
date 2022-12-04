/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.CanvasGenerador;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author juald
 */
public class Tunel {

    public static String TUNEL_CENTRAL = "Tunel Central";
    public static String TUNEL_IZQUIERDO = "Tunel Izquierdo";
    public static String TUNEL_DERECHO = "Tunel Derecho";
    public static String COCHE = "Coche";
    public static String FURGONETA = "Furgoneta";
    private final HashMap<String, String> diccionario;
    
    private Lock mutex;
    private Condition coches;
    private Condition furgos;
    
    private int numLibres = 3;
    private int nFurgos = 0;
    private CanvasGenerador cv;

    public Tunel(CanvasGenerador cv) {
        this.diccionario = new HashMap<>();
        this.mutex = new ReentrantLock();
        this.coches = this.mutex.newCondition();
        this.furgos = this.mutex.newCondition();
        this.diccionario.put(TUNEL_CENTRAL, "");
        this.diccionario.put(TUNEL_DERECHO, "");
        this.diccionario.put(TUNEL_IZQUIERDO, "");
        this.cv = cv;
    }

    public void EntraCoche(int id) throws InterruptedException {

        this.mutex.lock();
        try {
            while (esperaCoche()) {
                this.coches.await();
            }
            if (this.diccionario.get(TUNEL_CENTRAL).equals("")) {
                this.diccionario.put(TUNEL_CENTRAL, COCHE);
                this.cv.insertaTunel(id, this.diccionario);
                System.out.println("Soy el coche " + id + ", he entrado en el tunel central");
            } else if (this.diccionario.get(TUNEL_DERECHO).equals("")) {
                this.diccionario.put(TUNEL_DERECHO, COCHE);
                this.cv.insertaTunel(id, this.diccionario);
                System.out.println("Soy el coche " + id + ", he entrado en el tunel derecho");
            } else {
                this.diccionario.put(TUNEL_IZQUIERDO, COCHE);
                this.cv.insertaTunel(id, this.diccionario);
                System.out.println("Soy el coche " + id + ", he entrado en el tunel izquierdo");
            }
            this.numLibres--;
        } finally {
            this.mutex.unlock();
        }
    }

    public void SaleCoche() {

        this.mutex.lock();
        try {
            if (this.diccionario.get(TUNEL_CENTRAL).equals(COCHE)) {
                this.diccionario.put(TUNEL_CENTRAL, "");
            } else if (this.diccionario.get(TUNEL_DERECHO).equals(COCHE)) {
                this.diccionario.put(TUNEL_DERECHO, "");
            } else {
                this.diccionario.put(TUNEL_IZQUIERDO, "");
            }
            this.numLibres++; 
            this.coches.signal();
        } finally {
            this.mutex.unlock();
        }
    }
    
    public boolean esperaCoche() {
        if (this.numLibres == 0) {
            return true;
        }
        
        if (this.diccionario.get(TUNEL_DERECHO).equals(COCHE) && this.diccionario.get(TUNEL_IZQUIERDO).equals(COCHE)) {
            return true;
        }
      
        if (this.diccionario.get(TUNEL_CENTRAL).equals(COCHE) && (this.diccionario.get(TUNEL_DERECHO).equals(COCHE) || this.diccionario.get(TUNEL_IZQUIERDO).equals(COCHE))) {
            return true;
        }
        return false;
    }

    public void EntraFurgo(int id) throws InterruptedException {

        this.mutex.lock();
        nFurgos++;
        try {
            while (esperaFurgo()) {
                this.furgos.await();
            }
            if (this.diccionario.get(TUNEL_CENTRAL).equals("")) {
                this.diccionario.put(TUNEL_CENTRAL, FURGONETA);
                this.cv.insertaTunel(id, this.diccionario);
                System.out.println("Soy la furgo " + id + ", he entrado en el tunel central");
            } else if (this.diccionario.get(TUNEL_DERECHO).equals("")) {
                this.diccionario.put(TUNEL_DERECHO, FURGONETA);
                this.cv.insertaTunel(id, this.diccionario);
                System.out.println("Soy la furgo " + id + ", he entrado en el tunel derecho");
            } else {
                this.diccionario.put(TUNEL_IZQUIERDO, FURGONETA);
                this.cv.insertaTunel(id, this.diccionario);
                System.out.println("Soy la furgo " + id + ", he entrado en el tunel izquierdo");
            }
            this.numLibres--;
        } finally {
            this.mutex.unlock();
        }

    }

    public boolean esperaFurgo() {
        if (this.numLibres == 0) {
            return true;
        }
        
        /*
        if (this.diccionario.get(TUNEL_DERECHO).equals(FURGONETA) && this.diccionario.get(TUNEL_IZQUIERDO).equals(FURGONETA)) {
            return true;
        }
      
        if (this.numLibres > 0 && this.diccionario.get(TUNEL_CENTRAL).equals(FURGONETA)) {
            return true;
        }

        if (this.diccionario.get(TUNEL_DERECHO).equals(FURGONETA) || this.diccionario.get(TUNEL_IZQUIERDO).equals(FURGONETA)) {
            return true;
        }
         */
        return false;
    }

    public void SaleFurgo() {

        this.mutex.lock();
        nFurgos--;
        try {
            if (this.diccionario.get(TUNEL_CENTRAL).equals(FURGONETA)) {
                this.diccionario.put(TUNEL_CENTRAL, "");
            } else if (this.diccionario.get(TUNEL_DERECHO).equals(FURGONETA)) {
                this.diccionario.put(TUNEL_DERECHO, "");
            } else {
                this.diccionario.put(TUNEL_IZQUIERDO, "");
            }
            this.numLibres++;
            if (this.nFurgos > 0) {
                this.furgos.signal();
            }
            else
                this.coches.signal();
            
        } finally {
            this.mutex.unlock();
        }
    }

    public HashMap<String, String> getDiccionario() {
        return this.diccionario;
    }
}
