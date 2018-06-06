package io.github.syzygy2048.radcloud;

public class SpiralUtil {

    //number of spirals 40 and chord 10 improves quality at the cost of speed

    final static int numberOfSpirals = 20;
    final static double thetaMax = numberOfSpirals * 2 * Math.PI;

    //distance from center to outside
    final static int radius = 700;
    final static double awayStep = radius / thetaMax;

    //point density
    final static double chord = 50;


    final static double theta = chord / awayStep;

    public static DocumentManager.Vec2 calculateSpiral(int iteration){
        double deltaTheta = theta;
        double away =  awayStep * deltaTheta;
        for(int i = iteration; i > 0; i--) {
            away = awayStep * deltaTheta;
            deltaTheta += chord / away;
        }
        double around = deltaTheta + 1;
        // Convert 'around' and 'away' to X and Y.
        float x = (float) (0 + Math.cos(around) * away);
        float y = (float) (0 + Math.sin(around) * away);

        return new DocumentManager.Vec2(x,y);
    }
}
