package org.github.foolishboy.compare;

import org.github.foolishboy.compare.comparator.Action;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppStartTest {

    // you can config your path
    private static final String PATH = "";

    @Test
    public void testDiffKeyYml() {
        String[] args = new String[3];
        args[0] = PATH + "application-pre.yml";
        args[1] = PATH + "application-prd.yml";
        args[2] = Action.DIFF_KEY.getValue();
        AppStart.main(args);
    }

    @Test
    public void testDiffValueYml() {
        String[] args = new String[3];
        args[0] = PATH + "application-pre.yml";
        args[1] = PATH + "application-prd.yml";
        args[2] = Action.DIFF_VALUE.getValue();
        AppStart.main(args);

    }

    @Test
    public void testDiffKeyProperties() {
        String[] args = new String[3];
        args[0] = PATH + "application-pre.yml";
        args[1] = PATH + "application-prd.yml";
        args[2] = Action.DIFF_KEY.getValue();
        AppStart.main(args);
    }

    @Test
    public void testDiffValueProperties() {
        String[] args = new String[3];
        args[0] = PATH + "application-pre.properties";
        args[1] = PATH + "application-prd.properties";
        args[2] = Action.DIFF_VALUE.getValue();
        AppStart.main(args);

    }

    @Test
    public void testDiffKeyJson() {
        String[] args = new String[3];
        args[0] = PATH + "application-pre.json";
        args[1] = PATH + "application-prd.json";
        args[2] = Action.DIFF_KEY.getValue();
        AppStart.main(args);
    }

    @Test
    public void testDiffValueJson() {
        String[] args = new String[3];
        args[0] = PATH + "application-pre.json";
        args[1] = PATH + "application-prd.json";
        args[2] = Action.DIFF_VALUE.getValue();
        AppStart.main(args);

    }


}
