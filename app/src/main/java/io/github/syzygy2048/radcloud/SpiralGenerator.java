package io.github.syzygy2048.radcloud;


/**
 * This class is used to equidistant points on a spiral.
 * Source: https://stackoverflow.com/questions/13894715/draw-equidistant-points-on-a-spiral
 */
public class SpiralGenerator {

    //number of spirals 40 and chord 10 improves quality at the cost of speed
    //number of spirals 20 and chord 50 improves speed at the cost of quality

    /**
     * How many coils to reach the radius.
     */
    final static int numberOfSpirals = 20;


    /**
     * The angle between the last two points.
     */
    final static double thetaMax = numberOfSpirals * 2 * Math.PI;

    /**
     * The radius of the spiral.
     */
    final static int radius = 2000;

    /**
     * How far to step away from the center at the first step.
     */
    final static double awayStep = radius / thetaMax;

    /**
     * Distance between each point.
     */
    final static double chord = 30;

    /**
     * The angle between the first points.
     */
    final static double theta = chord / awayStep;

    //TODO: consider settings chord, radius and numberOfSpirals based on text size

    /**
     * Calculate an return a single point on an equidistant spiral. The points are equidistant and move outwarsd along the coils from the center (0/0).
     *
     * @param iteration - indicates how many points removed from the center the point should be calculated at.
     * @return DocumentManager.Vec2 - The x/y coordinates of the calculates point.
     */
    public static DocumentManager.Vec2 calculateSpiral(int iteration) {
        double deltaTheta = theta;
        double away = awayStep * deltaTheta;
        for (int i = iteration; i > 0; i--) {
            away = awayStep * deltaTheta;
            deltaTheta += chord / away;
        }
        double around = deltaTheta + 1;
        // Convert 'around' and 'away' to X and Y.
        float x = (float) (0 + Math.cos(around) * away);
        float y = (float) (0 + Math.sin(around) * away);

        return new DocumentManager.Vec2(x, y);
    }
}
