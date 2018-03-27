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
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);
    }

    @Test
    public void kortinSaldoOikein() {
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void saldoKasvaa() {
        kortti.lataaRahaa(10);
        assertEquals(20, kortti.saldo());
    }

    @Test
    public void saldoVahenee() {
        kortti.otaRahaa(7);
        assertEquals(3, kortti.saldo());
    }

    @Test
    public void saldoEiMuutuYlimennessa() {
        kortti.otaRahaa(20);
        assertEquals(10, kortti.saldo());
    }

    @Test
    public void varattomallaEpatosi() {
        assertFalse(kortti.otaRahaa(20));
    }

    @Test
    public void varallisellaTosi() {
        assertTrue(kortti.otaRahaa(10));
    }

    @Test
    public void toStringToimii() {
        kortti.lataaRahaa(1000);
        assertEquals("saldo: 10.10", kortti.toString());
    }
}
