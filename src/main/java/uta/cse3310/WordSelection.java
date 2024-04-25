public class WordSelection 
{
    public int xOne = 0;
    public int yOne = 0;
    public int xTwo = 0;
    public int yTwo = 0;

    public int xOneAnswer = 0;
    public int yOneAnswer = 0;
    public int xTwoAnswer = 0;
    public int yTwoAnswer = 0;

    public WordSelection(int x1, int y1, int x2, int y2,
                          int x1Answer, int y1Answer, int x2Answer, int y2Answer) 
    {
        this.xOne = x1;
        this.yOne = y1;
        this.xTwo = x2;
        this.yTwo = y2;

        this.xOneAnswer = x1Answer;
        this.yOneAnswer = y1Answer;
        this.xTwoAnswer = x2Answer;
        this.yTwoAnswer = y2Answer;
    }

    public boolean compareCoordinates() 
  {
        if ((this.xOne == this.xOneAnswer && this.yOne == this.yOneAnswer &&
                this.xTwo == this.xTwoAnswer && this.yTwo == this.yTwoAnswer) ||
                (this.xOne == this.xTwoAnswer && this.yOne == this.yTwoAnswer &&
                        this.xTwo == this.xOneAnswer && this.yTwo == this.yOneAnswer)) 
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }
}
