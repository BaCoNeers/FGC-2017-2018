package org.baconeers.common;

/**
 * Base class for components used in BaconOpModes
 */

public class BaconComponent {

    private BaconOpMode opMode;

    public BaconComponent(BaconOpMode baconOpMode) {
        opMode = baconOpMode;
    }

    protected BaconOpMode getOpMode() {
        return opMode;
    }

}
