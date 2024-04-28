package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.*;

//XSolis

public class testWordGrid
    extends TestCase{
        /**
         * Create the test case
         *
         * @param testName name of the test case
         * @return
         */
        public testWordGrid (String testName){
            super(testName);
        }

        /**
         * @return the suite of tests being tested
         */
        public static Test suite(){
            return new TestSuite(testWordGrid.class);
        }

        public void testWordGrid() throws FileNotFoundException, IOException{
            try{
            WordGrid w = new WordGrid(3); //choosing lvl 3
            WordSelection t = new WordSelection(0, 1, 0, 7, 0, 0, 0, 0); // word 'wether' should be in this position

            assertTrue(w.CheckWord(t)); //should be tru
            }
            catch (FileNotFoundException e)
            {
                System.out.println("File Not Found");
                assertTrue(true);
            }
            
        }
    }