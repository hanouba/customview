package com.hansen.serverlibrary;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        double d = 5.3;
        float f = (float) 11.1;
        int i = (int) 0.0;
        Double od = Double.valueOf(3);
        Double odd = 3.0;
        new SuperTest().test();
    }

    public class SuperTest extends Date {
        private void  test() {
            System.out.println(super.getClass().getName());
        }
    }
}