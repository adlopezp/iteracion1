package com.ecos.tspistatusquo.model;

import java.math.BigInteger;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Dev
 */
public class CalcularLocTest extends TestCase{
    
    private static String sSeparator = System.getProperty("file.separator");

    public CalcularLocTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of testCalcularValoresDefecto method, of class CalcularSimpsonRule.
     *
     * @throws Exception
     */
    public void testCalcularProgramas() throws Exception {
        BigInteger expResult=BigInteger.ONE;
        CalcularLoc programa = new CalcularLoc();
        programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve1" + sSeparator, sSeparator);//m
        assertEquals(expResult.intValue(),programa.getPaquetes().size());
        expResult=BigInteger.valueOf(4);
        assertEquals(expResult.intValue(),programa.getNombreClases().size());
        expResult=BigInteger.valueOf(169);
        assertEquals(expResult,programa.getContadorLoc());
        programa = new CalcularLoc();
        programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve3" + sSeparator, sSeparator);
        expResult=BigInteger.valueOf(4);
        assertEquals(expResult.intValue(),programa.getPaquetes().size());
        expResult=BigInteger.valueOf(5);
        assertEquals(expResult.intValue(),programa.getNombreClases().size());
        expResult=BigInteger.valueOf(357);
        assertEquals(expResult,programa.getContadorLoc());
        programa = new CalcularLoc();
        programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve4" + sSeparator, sSeparator);
        expResult=BigInteger.valueOf(4);
        assertEquals(expResult.intValue(),programa.getPaquetes().size());
        expResult=BigInteger.valueOf(5);
        assertEquals(expResult.intValue(),programa.getNombreClases().size());
        expResult=BigInteger.valueOf(357);
        assertEquals(expResult,programa.getContadorLoc());
        programa = new CalcularLoc();
        programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve5" + sSeparator, sSeparator);
        expResult=BigInteger.valueOf(4);
        assertEquals(expResult.intValue(),programa.getPaquetes().size());
        expResult=BigInteger.valueOf(4);
        assertEquals(expResult.intValue(),programa.getNombreClases().size());
        expResult=BigInteger.valueOf(299);
        assertEquals(expResult,programa.getContadorLoc());
        programa = new CalcularLoc();
        programa.leerRuta("src" + sSeparator + "site" + sSeparator + "resources" + sSeparator + "Proyectos" + sSeparator + "ecosdeve6" + sSeparator, sSeparator);
        expResult=BigInteger.valueOf(4);
        assertEquals(expResult.intValue(),programa.getPaquetes().size());
        expResult=BigInteger.valueOf(4);
        assertEquals(expResult.intValue(),programa.getNombreClases().size());
        expResult=BigInteger.valueOf(369);
        assertEquals(expResult,programa.getContadorLoc());
    }
}
