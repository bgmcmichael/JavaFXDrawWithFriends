package sample;

/**
 * Created by fenji on 9/1/2016.
 */
public class Stroke {
    public double x = 0, y = 0;
    public int strokeSize = 10;

    public Stroke(){

    }

    public void IncreaseStrokeSize(){
        strokeSize += 1;
        if (strokeSize > 20){
            strokeSize = 20;
        }
    }

    public void DecreaseStrokeSoze(){
        strokeSize -= 1;
        if (strokeSize < 2){
            strokeSize = 2;
        }
    }
}
