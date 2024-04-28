package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;

import static org.junit.Test;
import static org.junit.Assert.*;

public class WordSelectionTest 
{
    public void testCompareCoordinates() 
    {
        WordSelection ws = new WordSelection(1, 2, 3, 4, 1, 2, 3, 4);
        boolean result = ws.compareCoordinates();
        assertTrue(result);

        WordSelection ws2 = new WordSelection(1, 2, 3, 4, 3, 4, 1, 2);
        boolean result2 = ws2.compareCoordinates();
        assertTrue(result2);

        WordSelection ws3 = new WordSelection(1, 2, 3, 4, 3, 4, 5, 6);
        boolean result3 = ws3.compareCoordinates();
        assertTrue(result3);

        WordSelection ws4 = new WordSelection(1, 2, 3, 4, 5, 6, 1, 2);
        boolean result4 = ws4.compareCoordinates();
        assertTrue(result4);
    }
}