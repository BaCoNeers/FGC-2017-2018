package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**

 * Created by nathan on 2/5/2017.

 */

@TeleOp(name="color sensor calibration test", group="Practice-Bot")

public class color_sensor_calibration extends Opmode {

    private ElapsedTime period = new ElapsedTime();

    /***

     *

     * waitForTick implements a periodic delay. However, this acts like a metronome

     * with a regular periodic tick. This is used to compensate for varying

     * processing times for each cycle. The function looks at the elapsed cycle time,

     * and sleeps for the remaining time interval.

     *

     * @param periodMs Length of wait cycle in mSec.

    FIRST Global Java SDK Startup Guide - Rev 0 Copyright 2017 REV Robotics, LLC 18

     */

    private void waitForTick(long periodMs) throws java.lang.InterruptedException {

        long remaining = periodMs - (long)period.milliseconds();

// sleep for the remaining portion of the regular cycle period.

        if (remaining > 0) {

            Thread.sleep(remaining);

        }

// Reset the cycle clock for the next pass.

        period.reset();

    }

    @Override

    public void runOpMode() {


        double red,green,blue;
        double ambred,ambgreen,ambblue;
        double tiptime;


        ColorSensor = hardwareMap.colorSensor.get("color");
        Servo = hardwareMap.servo.get("servo");

// Set all motors to zero power


        Servo.setPosition(0);

        ColorSensor.enableLed(true);

        ambred = ColorSensor.red();
        ambgreen = ColorSensor.green();
        ambblue = ColorSensor.blue();


// Send telemetry message to signify robot waiting;

        telemetry.setAutoClear(false);
        Telemetry.Item redItem = telemetry.addData("red","%5.1f", 0.0);
        Telemetry.Item greenItem = telemetry.addData("green","%5.1f", 0.0);
        Telemetry.Item blueItem = telemetry.addData("blue","%5.1f", 0.0);
        Telemetry.Item ambredItem = telemetry.addData("ambred","%5.1f", 0.0);
        Telemetry.Item ambgreenItem = telemetry.addData("ambred", "%5.1f", 0.0);
        Telemetry.Item ambblueItem = telemetry.addData("ambblue","%5.1f",0.0);


// Wait for the game to start (driver presses PLAY)

        waitForStart();
         tiptime = time;

        try {

// run until the end of the match (driver presses STOP)

            while (opModeIsActive()) {

                red = ColorSensor.red();
                blue = ColorSensor.blue();
                green = ColorSensor.green();

                if (tiptime < (time + 0.5)) {
                    if (ambred + 20 > red && ambred - 20 < red && ambblue + 20 > blue && ambblue - 20 < blue) {
                        Servo.setPosition(0.5);
                    } else if (ambred + 50 < red && red > blue) {
                        Servo.setPosition(1);
                        tiptime = time;
                    } else if (ambblue + 50 < blue && blue > red) {
                        Servo.setPosition(0);
                        tiptime = time;
                    }

                }
                if (gamepad2.a) {
                    ambblue = ColorSensor.blue();
                    ambred = ColorSensor.red();
                }


                redItem.setValue("%5.1f",red);
                greenItem.setValue("%5.1f",green);
                blueItem.setValue("%5.1f",blue);
                ambredItem.setValue("%5.1f",ambred);
                ambgreenItem.setValue("%5.1f",ambgreen);
                ambblueItem.setValue("%5.1f",ambblue);
                telemetry.update();




// Pause for metronome tick. 40 mS each cycle = update 25 times a second.

                idle();

            }

        }

        catch (Exception exc)

        {

            exc.printStackTrace();

        }

        finally {



        }

    }

}






