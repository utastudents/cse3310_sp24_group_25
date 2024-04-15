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
        String fName = new String();
        if (gridChoice == 1){
            fName = "grid1.txt";
            totalWords = 50;
        }
        if (gridChoice == 2){
            fName = "grid2.txt";
            totalWords = 50;
        }
        if (gridChoice == 3){
            fName = "grid3.txt";
            totalWords = 20;
        }
        if (gridChoice == 4){
            fName = "grid4.txt";
            totalWords = 40;
        }
        if (gridChoice == 5){
            fName = "grid5.txt";
            totalWords = 30;
        }
        FileReader gridFile = new FileReader(fName);     //not sure if this is the path
        BufferedReader br = new BufferedReader(gridFile);
        for(int y = 0; y <= 25; y++){
            for(int x = 0; x <= 25; x++){
                    grid[y][x] = (char)br.read();         //grabing grid
                    br.skip(1);                     //skip space
            }
        }

        br.skip(1);                             //skip spacer line
        for(int x = 0; x <= totalWords; x++){
                words[x] = br.readLine();           //grabing word list
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

        for(int w = 0; w <= totalWords; w++){               //go through the words array ingnore case and compare
                if(words[w].equalsIgnoreCase(s.toString())){tf = true;}
            }

        return tf;
    }
    // Professor recommended to just check the position of the word in the grid
}
