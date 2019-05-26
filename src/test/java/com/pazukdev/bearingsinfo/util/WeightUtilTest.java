package com.pazukdev.bearingsinfo.util;

import com.pazukdev.bearingsinfo.util.WeightUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author Siarhei Sviarkaltsau
 */
public class WeightUtilTest {

    @Test
    public void kgToG() {
        final Integer kg = 1;
        final Integer g = WeightUtil.toG(kg);
        Assert.assertTrue(g == 1000);
    }

    @Test
    public void gToKg() {
        Integer g;
        BigDecimal actualKg;
        BigDecimal expectedKg;

        g = 5;
        actualKg = WeightUtil.toKg(g);
        assertEquals("0.01", actualKg.toString());

        g = 100;
        actualKg = WeightUtil.toKg(g);
        assertEquals("0.10", actualKg.toString());

        g = 1;
        actualKg = WeightUtil.toKg(g);
        assertEquals("0.00", actualKg.toString());
    }

}
