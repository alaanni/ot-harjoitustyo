package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti maksukortti;
        
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
    }
    
    @Test
    public void alussaRahaaOikein() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void alussaNollaMyytyaLounasta() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOstoToimiiJosMaksuRiittava() {
        int vaihtoraha = kassapaate.syoEdullisesti(250);
        assertEquals(10, vaihtoraha);
        assertEquals(100240, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanOstoToimiiJosMaksuRiittava() {
        int vaihtoraha = kassapaate.syoMaukkaasti(400);
        assertEquals(0, vaihtoraha);
        assertEquals(100400, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOstoEiToimiJosMaksuEiRiittava() {
        int vaihtoraha = kassapaate.syoEdullisesti(200);
        assertEquals(200, vaihtoraha);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaanLounaanOstoEiToimiJosMaksuEiRiittava() {
        int vaihtoraha = kassapaate.syoMaukkaasti(300);
        assertEquals(300, vaihtoraha);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void josKortillaRiittavastiRahaa() {
        maksukortti = new Maksukortti(1000);
        boolean edullinenLounasMaksettu = kassapaate.syoEdullisesti(maksukortti);
        boolean maukasLounasMaksettu = kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(true, edullinenLounasMaksettu);
        assertEquals(true, maukasLounasMaksettu);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(1000-240-400, maksukortti.saldo());
    }
    
    @Test
    public void josKortillaEiRiittavastiRahaa() {
        maksukortti = new Maksukortti(1);
        boolean edullinenLounasMaksettu = kassapaate.syoEdullisesti(maksukortti);
        boolean maukasLounasMaksettu = kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(false, edullinenLounasMaksettu);
        assertEquals(false, maukasLounasMaksettu);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(1, maksukortti.saldo());
    }
    
    @Test
    public void lataaRahaaKortilleToimii() {
        maksukortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(100000+1000, kassapaate.kassassaRahaa());
        assertEquals(1000, maksukortti.saldo());
    }
    
    @Test
    public void kortilleEiVoiLadataNegatiivistaSummaa() {
        maksukortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(maksukortti, -5);
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, maksukortti.saldo());
    }
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @After
    public void tearDown() {
    }

}
