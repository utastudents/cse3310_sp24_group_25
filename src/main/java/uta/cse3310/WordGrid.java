package uta.cse3310;

import java.io.File;
import java.io.*;
//import java.stringbuilder??
//XSolis

public class WordGrid {
    public int length = 25;
    public int width = 25;
    public int totalWords = 50;
    public int wordsFound = 0;
    public int gridChoice = 0;
    public char grid[][] = new char[26][26];
    public String words[] = new String[51];

    public WordGrid(int gridChoice) throws FileNotFoundException, IOException {
        if (gridChoice == 1){
            FileReader grid1 = new FileReader("grid1.txt");     //not sure if this is the path
            BufferedReader br = new BufferedReader(grid1);
            for(int y = 0; y <= 25; y++){
                for(int x = 0; x <= 25; x++){
                    grid[y][x] = (char)br.read();         //grabing grid
                    br.skip(1);                     //skip space
                }
            }

            br.skip(1);                             //skip spacer line
            for(int x = 0; x <= 50; x++){
                words[x] = br.readLine();           //grabing word list
            }
        }
    }

    public boolean CheckWord(WordSelection a) {
        boolean tf = false;
        StringBuilder s = new StringBuilder();
        if(a.x1 == a.x2){                           //vertical word
            for(int y = a.y1; y <= a.y2; y++){
                s.append(grid[y][a.x1]);
            }
            
        }
        else if (a.y1 == a.y2){                     //horizontal word
            for(int x = a.x1; x <= a.x2; x++){
                s.append(grid[a.y1][x]);
            }
        }
        else{                                       //diagonal word
            int x = a.x1;
            for(int y = a.y1; y <= a.y2; y++){
                s.append(grid[y][x]);
                x++;
            }
        }

        for(int w = 0; w <= 50; w++){               //go through the words array ingnore case and compare
                if(words[w].equalsIgnoreCase(s.toString())){tf = true;}
            }

        return tf;
    }
    // Professor recommended to just check the position of the word in the grid
}
