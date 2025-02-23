package CC14_Group_02_Assignment_1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StandardDeviation {

    private ArrayList<Float> numArray;


    public  StandardDeviation (ArrayList<Float> numArray ) {
        this.numArray= numArray;
    }

    public static float calculateSD(ArrayList<Float> numArray) {
        float sum = 0.0F, standardDeviation = 0.0F;
        int length = numArray.size();

        for(float num : numArray) {
            sum += num;
        }

        float mean = sum/length;

        for(float num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return (float) Math.sqrt(standardDeviation/length);
    }


}
