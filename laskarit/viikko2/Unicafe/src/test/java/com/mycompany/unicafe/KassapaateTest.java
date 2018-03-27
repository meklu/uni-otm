
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate paate;
    Maksukortti kortti;

    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }

    // Alkutila

    @Test
    public void alkuSaldoOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void alkuEdullisiaLounaitaNolla() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void alkuMaukkaitaLounaitaNolla() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    // Käteisostot

    @Test
    public void edullinenKateisostoVaihtorahaOikein() {
        assertEquals(20, paate.syoEdullisesti(260));
    }

    @Test
    public void edullinenKateisostoLounasmaaraOikein() {
        paate.syoEdullisesti(240);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullinenKateisostoKassaOikein() {
        paate.syoEdullisesti(240);
        assertEquals(100240, paate.kassassaRahaa());
    }

    @Test
    public void edullinenKateisostoVaratonVaihtoraha() {
        assertEquals(10, paate.syoEdullisesti(10));
    }

    @Test
    public void edullinenKateisostoVaratonLounasmaara() {
        paate.syoEdullisesti(10);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullinenKateisostoVaratonKassa() {
        paate.syoEdullisesti(10);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void maukasKateisostoVaihtorahaOikein() {
        assertEquals(20, paate.syoMaukkaasti(420));
    }

    @Test
    public void maukasKateisostoLounasmaaraOikein() {
        paate.syoMaukkaasti(400);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukasKateisostoKassaOikein() {
        paate.syoMaukkaasti(400);
        assertEquals(100400, paate.kassassaRahaa());
    }

    @Test
    public void maukasKateisostoVaratonVaihtoraha() {
        assertEquals(10, paate.syoMaukkaasti(10));
    }

    @Test
    public void maukasKateisostoVaratonLounasmaara() {
        paate.syoMaukkaasti(10);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukasKateisostoVaratonKassa() {
        paate.syoMaukkaasti(10);
        assertEquals(100000, paate.kassassaRahaa());
    }

    // Korttiostot

    @Test
    public void edullinenKorttiostoLapi() {
        assertTrue(paate.syoEdullisesti(kortti));
    }

    @Test
    public void edullinenKorttiostoVeloitettu() {
        paate.syoEdullisesti(kortti);
        assertEquals(1000 - 240, kortti.saldo());
    }

    @Test
    public void edullinenKorttiostoLounasmaaraOikein() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullinenKorttiostoKassaOikein() {
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void edullinenKorttiostoVaraton() {
        kortti.otaRahaa(900);
        assertFalse(paate.syoEdullisesti(kortti));
    }

    @Test
    public void edullinenKorttiostoVaratonLounasmaara() {
        kortti.otaRahaa(900);
        paate.syoEdullisesti(kortti);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasKorttiostoLapi() {
        assertTrue(paate.syoMaukkaasti(kortti));
    }

    @Test
    public void maukasKorttiostoVeloitettu() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1000 - 400, kortti.saldo());
    }

    @Test
    public void maukasKorttiostoLounasmaaraOikein() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukasKorttiostoKassaOikein() {
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void maukasKorttiostoVaraton() {
        kortti.otaRahaa(900);
        assertFalse(paate.syoMaukkaasti(kortti));
    }

    @Test
    public void maukasKorttiostoVaratonLounasmaara() {
        kortti.otaRahaa(900);
        paate.syoMaukkaasti(kortti);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    // Kortin lataus

    @Test
    public void kortinLatausMeneeKortille() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(1100, kortti.saldo());
    }

    @Test
    public void kortinLatausMeneeKassaan() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, paate.kassassaRahaa());
    }

    @Test
    public void kortilleEiLadataNegatiivista() {
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(1000, kortti.saldo());
    }

    @Test
    public void kortinNegatiivinenLatausEiMeneKassaan() {
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, paate.kassassaRahaa());
    }
}
