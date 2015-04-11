/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.ecosdeve3.model;

import com.ecos.ecosdeve3.Exeptions.ExceptionApp;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;

/**
 *
 * @author Dev
 */
public class CalcularProbe extends CalcularMediaDesviacion {

    private BigDecimal b0 = BigDecimal.ZERO;
    private BigDecimal b1 = BigDecimal.ZERO;
    private BigDecimal cuadradoLista = BigDecimal.ZERO;
    private BigDecimal sumaXY = BigDecimal.ZERO;
    private BigDecimal sumaLista = BigDecimal.ZERO;
    private BigDecimal correlacion = BigDecimal.ZERO;
    private BigDecimal correlacionCuadrado = BigDecimal.ZERO;
    private BigDecimal prediccion = BigDecimal.ZERO;
    private BigDecimal estimado = BigDecimal.ZERO;

    public CalcularProbe() {
    }

    public CalcularProbe(int numeroLista, BigDecimal estimado) {
        LinkedList<BigDecimal> list = new LinkedList<BigDecimal>();
        if (numeroLista == 1) {
            list.add(BigDecimal.valueOf(130));
            list.add(BigDecimal.valueOf(650));
            list.add(BigDecimal.valueOf(99));
            list.add(BigDecimal.valueOf(150));
            list.add(BigDecimal.valueOf(128));
            list.add(BigDecimal.valueOf(302));
            list.add(BigDecimal.valueOf(95));
            list.add(BigDecimal.valueOf(945));
            list.add(BigDecimal.valueOf(368));
            list.add(BigDecimal.valueOf(961));
        } else if (numeroLista == 2) {
            list.clear();
            list.add(BigDecimal.valueOf(163));
            list.add(BigDecimal.valueOf(765));
            list.add(BigDecimal.valueOf(141));
            list.add(BigDecimal.valueOf(166));
            list.add(BigDecimal.valueOf(137));
            list.add(BigDecimal.valueOf(355));
            list.add(BigDecimal.valueOf(136));
            list.add(BigDecimal.valueOf(1206));
            list.add(BigDecimal.valueOf(433));
            list.add(BigDecimal.valueOf(1130));
        } else if (numeroLista == 3) {
            list.clear();
            list.add(BigDecimal.valueOf(186));
            list.add(BigDecimal.valueOf(699));
            list.add(BigDecimal.valueOf(132));
            list.add(BigDecimal.valueOf(272));
            list.add(BigDecimal.valueOf(291));
            list.add(BigDecimal.valueOf(331));
            list.add(BigDecimal.valueOf(199));
            list.add(BigDecimal.valueOf(1890));
            list.add(BigDecimal.valueOf(788));
            list.add(BigDecimal.valueOf(1601));
        } else if (numeroLista == 4) {
            list.clear();
            list.add(BigDecimal.valueOf(15.0));
            list.add(BigDecimal.valueOf(69.9));
            list.add(BigDecimal.valueOf(6.5));
            list.add(BigDecimal.valueOf(22.4));
            list.add(BigDecimal.valueOf(28.4));
            list.add(BigDecimal.valueOf(65.9));
            list.add(BigDecimal.valueOf(19.4));
            list.add(BigDecimal.valueOf(198.7));
            list.add(BigDecimal.valueOf(38.8));
            list.add(BigDecimal.valueOf(138.2));
        }
        setList(list);
        this.estimado = (estimado);
    }

    public void calcularAcumuladores() throws Exception {
        BigDecimal sumaDatos = BigDecimal.ZERO;
        BigDecimal sumacuadrado = BigDecimal.ZERO;
        for (BigDecimal dato : getList()) {
            sumacuadrado=sumacuadrado.add(dato.pow(2));
            sumaDatos=sumaDatos.add(dato);
        }
        setCuadradoLista(sumacuadrado);
        setSumaLista(sumaDatos);
    }

    public void calcularProductoXY(CalcularMediaDesviacion y) throws Exception {
        BigDecimal sumaProductoXY = BigDecimal.ZERO;
        for (int i = 0; i < getList().size(); i++) {
            sumaProductoXY = sumaProductoXY.add(getList().get(i).multiply(y.getList().get(i)));
        }
        setSumaXY(sumaProductoXY);
    }

    public void calcularPrediccion(CalcularProbe y) throws Exception {
        try{
        if (getList().size() == y.getList().size()) {
            BigDecimal size = BigDecimal.valueOf(getList().size());
            calcularAcumuladores();
            y.calcularAcumuladores();
            calcularProductoXY(y);
            calcularMedia();
            y.calcularMedia();
            BigDecimal formula1 = size.multiply(getMedia()).multiply(y.getMedia());
            BigDecimal formula2 = size.multiply(getMedia().pow(2));
            BigDecimal formula3 = getSumaXY().subtract(formula1);
            BigDecimal formula4 = getCuadradoLista().subtract(formula2);
            setB1(formula3.divide(formula4,MathContext.DECIMAL64));
            setB0(y.getMedia().subtract(getB1().multiply(getMedia())));
            formula1 = size.multiply(getSumaXY());
            formula2 = getSumaLista().multiply(y.getSumaLista());
            formula3 = new BigDecimal(Math.sqrt(size.multiply(getCuadradoLista()).subtract(getSumaLista().pow(2)).doubleValue()));
            formula4 = new BigDecimal(Math.sqrt(size.multiply(y.getCuadradoLista()).subtract(y.getSumaLista().pow(2)).doubleValue()));
            setCorrelacion(formula1.subtract(formula2).divide(formula3.multiply(formula4), MathContext.DECIMAL64));
            setCorrelacionCuadrado(getCorrelacion().pow(2));
            setPrediccion(getB0().add(getB1().multiply(getEstimado())));
        } else {
            throw new ExceptionApp("Las getListas deben tener el mismo numero de elementos");
        }
        }catch(Exception ex){
            throw new ExceptionApp("Revice la coherencia de los datos ingresados. "+ex.getMessage());
        }
    }

    public BigDecimal getB0() {
        return b0;
    }

    public void setB0(BigDecimal b0) {
        this.b0 = b0;
    }

    public BigDecimal getB1() {
        return b1;
    }

    public void setB1(BigDecimal b1) {
        this.b1 = b1;
    }

    public BigDecimal getCuadradoLista() {
        return cuadradoLista;
    }

    public void setCuadradoLista(BigDecimal cuadradoLista) {
        this.cuadradoLista = cuadradoLista;
    }

    public BigDecimal getSumaXY() {
        return sumaXY;
    }

    public void setSumaXY(BigDecimal sumaXY) {
        this.sumaXY = sumaXY;
    }

    public BigDecimal getSumaLista() {
        return sumaLista;
    }

    public void setSumaLista(BigDecimal sumaLista) {
        this.sumaLista = sumaLista;
    }

    public BigDecimal getCorrelacion() {
        return correlacion;
    }

    public void setCorrelacion(BigDecimal correlacion) {
        this.correlacion = correlacion;
    }

    public BigDecimal getPrediccion() {
        return prediccion;
    }

    public void setPrediccion(BigDecimal prediccion) {
        this.prediccion = prediccion;
    }

    public BigDecimal getCorrelacionCuadrado() {
        return correlacionCuadrado;
    }

    public void setCorrelacionCuadrado(BigDecimal correlacionCuadrado) {
        this.correlacionCuadrado = correlacionCuadrado;
    }

    public BigDecimal getEstimado() {
        return estimado;
    }

    public void setEstimado(BigDecimal estimado) {
        this.estimado = estimado;
    }
}
