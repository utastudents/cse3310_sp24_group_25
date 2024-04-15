package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

//XSolis

public class WordGridTest
    extends TestCase
    {
        public WordGridTest (String testName){
            super(testName);
        }

        public static Test suite(){
            return new TestSuite(WordGridTest.class);
        }

        public void WordGridTest(){
            WordGrid w = new WordGrid(3); //choosing lvl 3
            WordSelection t = new WordSelection(0, 1, 0, 7); // word 'wether' should be in this position

            assertTrue(w.CheckWord(t)); //should be tru
        }
    }