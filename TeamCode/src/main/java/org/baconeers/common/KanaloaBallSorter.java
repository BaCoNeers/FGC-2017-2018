package org.baconeers.common;


import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * Operation to assist with Gamepad actions on DCMotors
 */
public class KanaloaBallSorter extends BaconComponent {



    private final Servo servo;
    private final Telemetry.Item item;
    private double red,blue;
    private ColorSensorThread colorSensor = null;
    private boolean showTelemetry;




    public KanaloaBallSorter(BaconOpMode opMode,ColorSensorThread colorSensor, Servo servo, boolean showTelemetry) {
        super(opMode);


        this.servo = servo;
        this.showTelemetry = showTelemetry;

        this.colorSensor = colorSensor;

        if (showTelemetry) {
            item = opMode.telemetry.addData("red", 0.0f);
            item.setRetained(true);
        } else {
            item = null;
        }
    }
    public KanaloaBallSorter(BaconOpMode opMode,ColorSensorThread colorSensor, Servo servo) {
        this(opMode,colorSensor, servo,true);
    }

    public void init() {

    }

    /**
     * Update motors with latest gamepad state
     */
    public void update() {
        // Only toggle when the button state changes from false to true, ie when the
        // button is pressed down (and not when the button comes back up)
        red = colorSensor.red;
        blue = colorSensor.blue;

        if (red > blue)
        {
            servo.setPosition(0.95);

        }else if(blue > red){
            servo.setPosition(0.85);
        }
            if (item != null) {
                item.setValue(red);
            }
            if (showTelemetry) {
                getOpMode().telemetry.log().add("%s red value: %.1f blue value: %.1f %.3f", "color", red, blue, getOpMode().time);
            }
    }


}
