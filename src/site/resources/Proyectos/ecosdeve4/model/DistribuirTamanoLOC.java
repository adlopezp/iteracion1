/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.ecosdeve4.model;

import com.ecos.ecosdeve4.controller.exceptions.ExceptionApp;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Dev
 */
public class DistribuirTamanoLOC extends CalcularMediaDesviacion {

    private List<String> clases = new ArrayList<String>();
    private List<BigDecimal> loc = new ArrayList<BigDecimal>();
    private List<BigDecimal> numeroMetodos = new ArrayList<BigDecimal>();
    private List<String> capitulos = new ArrayList<String>();
    private List<BigDecimal> paginas = new ArrayList<BigDecimal>();
    private List<BigDecimal> locXmetodos = new ArrayList<BigDecimal>();
    private List<BigDecimal> paginasXcapitulo = new ArrayList<BigDecimal>();

    public DistribuirTamanoLOC() {
        clases.add("each_char");
        clases.add("string_read");
        clases.add("single_character");
        clases.add("each_line");
        clases.add("single_char");
        clases.add("tring_builder");
        clases.add("string_manager");
        clases.add("list_clump");
        clases.add("list_clip");
        clases.add("string_decrementer");
        clases.add("Char");
        clases.add("Character");
        clases.add("Converter");
        loc.add(new BigDecimal(18));
        loc.add(new BigDecimal(18));
        loc.add(new BigDecimal(25));
        loc.add(new BigDecimal(31));
        loc.add(new BigDecimal(37));
        loc.add(new BigDecimal(82));
        loc.add(new BigDecimal(82));
        loc.add(new BigDecimal(87));
        loc.add(new BigDecimal(89));
        loc.add(new BigDecimal(230));
        loc.add(new BigDecimal(85));
        loc.add(new BigDecimal(87));
        loc.add(new BigDecimal(558));
        numeroMetodos.add(new BigDecimal(3));
        numeroMetodos.add(new BigDecimal(3));
        numeroMetodos.add(new BigDecimal(3));
        numeroMetodos.add(new BigDecimal(3));
        numeroMetodos.add(new BigDecimal(3));
        numeroMetodos.add(new BigDecimal(5));
        numeroMetodos.add(new BigDecimal(4));
        numeroMetodos.add(new BigDecimal(4));
        numeroMetodos.add(new BigDecimal(4));
        numeroMetodos.add(new BigDecimal(10));
        numeroMetodos.add(new BigDecimal(3));
        numeroMetodos.add(new BigDecimal(3));
        numeroMetodos.add(new BigDecimal(10));
        capitulos.add("Preface");
        capitulos.add("Chapter 1");
        capitulos.add("Chapter 2");
        capitulos.add("Chapter 3");
        capitulos.add("Chapter 4");
        capitulos.add("Chapter 5");
        capitulos.add("Chapter 6");
        capitulos.add("Chapter 7");
        capitulos.add("Chapter 8");
        capitulos.add("Chapter 9");
        capitulos.add("Appendix A");
        capitulos.add("Appendix B");
        capitulos.add("Appendix C");
        capitulos.add("Appendix D");
        capitulos.add("Appendix E");
        capitulos.add("Appendix F");
        paginas.add(new BigDecimal(7));
        paginas.add(new BigDecimal(12));
        paginas.add(new BigDecimal(10));
        paginas.add(new BigDecimal(12));
        paginas.add(new BigDecimal(10));
        paginas.add(new BigDecimal(12));
        paginas.add(new BigDecimal(12));
        paginas.add(new BigDecimal(12));
        paginas.add(new BigDecimal(12));
        paginas.add(new BigDecimal(8));
        paginas.add(new BigDecimal(8));
        paginas.add(new BigDecimal(8));
        paginas.add(new BigDecimal(20));
        paginas.add(new BigDecimal(14));
        paginas.add(new BigDecimal(18));
        paginas.add(new BigDecimal(12));
    }

    public void carlcularMediaDesviacionLnX(List<BigDecimal> loc,List<BigDecimal> metodos) throws Exception {
        for (int x = 0; x < loc.size(); x++) {
            getList().add(new BigDecimal(Math.log(loc.get(x).divide(metodos.get(x), MathContext.DECIMAL64).doubleValue())));
        }
        try {
            calcularMedia();
            calculardesviacion();
        } catch (Exception ex) {
            throw new ExceptionApp("Error al calcular la media y desviacion " + ex.getMessage());
        }
    }
    
    public void carlcularMediaDesviacionLnX(List<BigDecimal> pag) throws Exception {
        for (int x = 0; x < pag.size(); x++) {
            getList().add(new BigDecimal(Math.log(pag.get(x).doubleValue())));
        }
        try {
            calcularMedia();
            calculardesviacion();
        } catch (Exception ex) {
            throw new ExceptionApp("Error al calcular la media y desviacion " + ex.getMessage());
        }
    }
    
    public void calcularTamanosLoc()throws Exception{
        setList(new LinkedList<BigDecimal>());
        carlcularMediaDesviacionLnX(getLoc(),getNumeroMetodos());
        BigDecimal lnT=getMedia().subtract(getDesviacion().multiply(new BigDecimal(2)));
        locXmetodos.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia().subtract(getDesviacion());
        locXmetodos.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia();
        locXmetodos.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia().add(getDesviacion());
        locXmetodos.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia().add(getDesviacion().multiply(new BigDecimal(2)));
        locXmetodos.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        setList(new LinkedList<BigDecimal>());
        carlcularMediaDesviacionLnX(getPaginas());
        lnT=getMedia().subtract(getDesviacion().multiply(new BigDecimal(2)));
        paginasXcapitulo.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia().subtract(getDesviacion());
        paginasXcapitulo.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia();
        paginasXcapitulo.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia().add(getDesviacion());
        paginasXcapitulo.add(new BigDecimal(Math.exp(lnT.doubleValue())));
        lnT=getMedia().add(getDesviacion().multiply(new BigDecimal(2)));
        paginasXcapitulo.add(new BigDecimal(Math.exp(lnT.doubleValue())));
    }

    public List<BigDecimal> getLocXmetodos() {
        return locXmetodos;
    }

    public void setLocXmetodos(List<BigDecimal> locXmetodos) {
        this.locXmetodos = locXmetodos;
    }

    public List<String> getClases() {
        return clases;
    }

    public void setClases(List<String> clases) {
        this.clases = clases;
    }

    public List<BigDecimal> getLoc() {
        return loc;
    }

    public void setLoc(List<BigDecimal> loc) {
        this.loc = loc;
    }

    public List<BigDecimal> getNumeroMetodos() {
        return numeroMetodos;
    }

    public void setNumeroMetodos(List<BigDecimal> numeroMetodos) {
        this.numeroMetodos = numeroMetodos;
    }

    public List<String> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<String> capitulos) {
        this.capitulos = capitulos;
    }

    public List<BigDecimal> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<BigDecimal> paginas) {
        this.paginas = paginas;
    }

    public List<BigDecimal> getPaginasXcapitulo() {
        return paginasXcapitulo;
    }

    public void setPaginasXcapitulo(List<BigDecimal> paginasXcapitulo) {
        this.paginasXcapitulo = paginasXcapitulo;
    }
}
