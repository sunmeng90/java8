package org.meng.java8.concurrent.mlb;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class GamePageLinksSupplierTest {

    @Test
    public void testGetGameLinks() {
        LocalDate year2017 = LocalDate.of(2017, 10, 1);
        GamePageLinksSupplier supplier = new GamePageLinksSupplier(year2017, 2);
        List<String> links = supplier.getGameLinks(year2017);
        assertNotNull(links);
        assertTrue(links.size() > 0);
        assertTrue(links.contains("gid_2017_10_01_arimlb_kcamlb_1/"));
    }


    @Test
    public void testGet() {
        LocalDate year2017 = LocalDate.of(2017, 10, 1);
        GamePageLinksSupplier supplier = new GamePageLinksSupplier(year2017, 5);
        List<String> links = supplier.get();
        assertNotNull(links);
        assertTrue(links.size() > 0);
        assertTrue(links.contains("gid_2017_10_01_arimlb_kcamlb_1/"));
        assertTrue(links.contains("gid_2017_10_03_awcmlb_adomlb_1/"));
    }

}