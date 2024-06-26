package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WordSelectionTest extends TestCase
{

    //@Test
    public void testCompareCoordinates() 
    {
        WordSelection ws = new WordSelection(1, 2, 3, 4, 1, 2, 3, 4);
        boolean result = ws.compareCoordinates();
        assertEquals(true, result);

        WordSelection ws2 = new WordSelection(1, 2, 3, 4, 3, 4, 1, 2);
        boolean result2 = ws2.compareCoordinates();
        assertEquals(true, result2);

        WordSelection ws3 = new WordSelection(1, 2, 3, 4, 3, 4, 5, 6);
        boolean result3 = ws3.compareCoordinates();
        assertEquals(false, result3);

        WordSelection ws4 = new WordSelection(1, 2, 3, 4, 5, 6, 1, 2);
        boolean result4 = ws4.compareCoordinates();
        assertEquals(false, result4);
    }
}
