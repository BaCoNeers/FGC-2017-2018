package org.firstglobal.FgCommon;


/**
 * OpModeComponent for reading and data from the Optical distance sensor
 */

public class OpModeComponent {

    private FGOpMode opMode;

    /**
     * Constructor for component
     * @param opMode
     */
    public OpModeComponent(FGOpMode opMode) {

        this.opMode = opMode;

    }

    protected FGOpMode getOpMode() {
        return opMode;
    }


}
