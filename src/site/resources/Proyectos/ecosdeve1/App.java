package com.ecos.ecosdeve1;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    private static boolean valido;
    private static Scanner in = new Scanner(System.in);
    private static CargarArchivos carga = new CargarArchivos();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String continuar;
        do {
            int op = 0;
            System.out.println("Calculo de media y desviacion estandar");
            System.out.println("**************MENU**************");
            System.out.println("********************************");
            System.out.println("1.Lista Estatica de datos");
            System.out.println("2.Ingreso manual de datos");
            System.out.println("3.Datos desde archivo");
            System.out.println("********************************");
            System.out.println("Digite la opcion que desee para capturar los datos :");
            do {
                op = capturarInt();
                if (op > 3 || op < 1) {
                    System.out.println("opcion invalida");
                    valido = false;
                }
            } while (!valido);
            CalcularMediaDesviacion calculos = new CalcularMediaDesviacion();
            switch (op) {
                case 1:
                    break;
                case 2:
                    LinkedList<BigDecimal> manuales = new LinkedList<BigDecimal>();
                    int size = 0,
                     index = 1;
                    System.out.println("Digite el numero de datos que desea capturar :");
                    do {
                        size = capturarInt();
                        if (size <= 1) {
                            System.out.println("Debe ingresar minimo 2 datos para calcular media y desviacion estandar");
                            valido = false;
                        }
                    } while (!valido);
                    System.out.println("digite acontinuacion " + size + " numeros :");
                    while (index <= size) {
                        do {
                            manuales.add(capturarDatos());
                        } while (!valido);
                        index++;
                    }
                    calculos.setList(manuales);
                    break;
                case 3:
                    String nombre;
                    System.out.println("Por favor almacene un archivo .txt con los datos numericos separados por lineas en la ruta " + carga.getsDirectorioTrabajo());
                    System.out.println("Ingrese el nombre del archivo con la extensión almacenado en la ruta espcificada :");
                    do {
                        nombre = in.next();
                        calculos.setList(cargarArchivo(nombre));
                    } while (!valido);
                    break;
                default:
                    System.out.println("opcion invalida");
                    break;
            }
            try {
                calculos.calcularMedia();
                calculos.calculardesviacion();
            } catch (Exception e) {
                System.out.println("Ocurrio un error: " + e.getMessage());
            }
            if (calculos.getMedia() != null && calculos.getDesviacion() != null) {
                System.out.println("Impresion de datos Datos");
                for (BigDecimal dato : calculos.getList()) {
                    System.out.println(dato);
                }
                System.out.println("Media :" + calculos.getMedia());
                System.out.println("Desviacion Estandar :" + calculos.getDesviacion());
            }
            System.out.println("Digite 1 para continuar");
            continuar = in.next();
        } while (continuar.equals("1"));
    }

    private static int capturarInt() {
        int numero = 0;
        String entrada;
        try {
            entrada = in.next();
            numero = Integer.valueOf(entrada.trim());
            valido = true;
        } catch (Exception e) {
            valido = false;
            System.out.println("Ocurrio un error: " + e.getMessage());
        }
        return numero;
    }

    private static BigDecimal capturarDatos() {
        BigDecimal numero = BigDecimal.ZERO;
        String entrada;
        try {
            entrada = in.next();
            numero = new BigDecimal(entrada.trim());
            valido = true;
        } catch (Exception e) {
            valido = false;
            System.out.println("Ocurrio un error: " + e.getMessage());
        }
        return numero;
    }

    private static LinkedList<BigDecimal> cargarArchivo(String nombre) {
        LinkedList<BigDecimal> lista = new LinkedList<BigDecimal>();
        try {
            lista = carga.leerArchivo(nombre);
            valido = true;
        } catch (Exception e) {
            valido = false;
            System.out.println(e.getMessage());
        }
        return lista;
    }
}
