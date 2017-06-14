package org.firstglobal.FgCommon;


public class TimerComponent  extends OpModeComponent {

    private double startTime;
    private boolean isRunning;
    private FGOpMode opmode;

    /**
     * Constructor for component
     *
     * @param opMode
     */
    public TimerComponent(FGOpMode opMode) {
        super(opMode);
    }


    /**
     * @return boolean designates whether the timer matches the target time
     * @param
     */
    public boolean timeReached(double targetTime) {

        if (  isRunning ) {
            boolean reached =  (getOpMode().getRuntime() - startTime) >= targetTime ;
            if ( reached) {
                isRunning = false;
            }
            return reached;
        }

        startTime = getOpMode().getRuntime();
        isRunning = true;
        return false;
    }



}
