package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }
    
    @Test
    public void saldoPalauttaaOikean() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeJosRahaaTarpeeksi() {
        kortti.otaRahaa(8);
        assertEquals("saldo: 0.2", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(100);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void palauttaaTrueJosRahatRiittaa() {
        assertEquals(true, kortti.otaRahaa(8));
    }
    @Test
    public void palauttaaFalseJosRahatEiRiita() {
        assertEquals(false, kortti.otaRahaa(100));
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
}
