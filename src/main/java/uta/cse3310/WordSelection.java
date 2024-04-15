package uta.cse3310;
//XSolis

public class WordSelection {
    int x1;
    int x2;
    int y1;
    int y2;

    public WordSelection(int x, int y, int i, int j){
        x1 = x;
        y1 = y;
        x2 = i;
        y2 = j;
    }

    public void changeSelection (int x, int y, int i, int j){
        x1 = x;
        y1 = y;
        x2 = i;
        y2 = j;
    }
}