package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**

 * Created by nathan on 2/5/2017.

 */

@TeleOp(name="normdrive(test)", group="Practice-Bot")

public class normdrive extends Opmode {

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

        double left = 0.0;
        double right = 0.0;
        double power = 0;
        double turning = 0;

        leftMotor1 = hardwareMap.dcMotor.get("left1_drive");
        leftMotor2 = hardwareMap.dcMotor.get("left2_drive");


        rightMotor1 = hardwareMap.dcMotor.get("right1_drive");
        rightMotor2 = hardwareMap.dcMotor.get("right2_drive");


        rightMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);

// Set all motors to zero power

        leftMotor1.setPower(0);

        leftMotor2.setPower(0);

        rightMotor1.setPower(0);

        rightMotor2.setPower(0);




// Send telemetry message to signify robot waiting;

        telemetry.addData("Say", "ready"); //

        telemetry.update();

// Wait for the game to start (driver presses PLAY)

        waitForStart();


        try {

// run until the end of the match (driver presses STOP)

            while (opModeIsActive()) {

                double rotation1 = -gamepad1.left_stick_x;
                double power1 = gamepad1.right_trigger - gamepad1.left_trigger;
                left = power1 + rotation1;
                right = power1 - rotation1;


                leftMotor1.setPower(left);
                rightMotor1.setPower(right);
                leftMotor2.setPower(left);
                rightMotor2.setPower(right);

                telemetry.addData("left: ",left  );
                telemetry.addData("right: ", right);
                telemetry.update();




// Pause for metronome tick. 40 mS each cycle = update 25 times a second.

                waitForTick(40);

            }

        }

        catch (java.lang.InterruptedException exc)

        {

            return;

        }

        finally {

            leftMotor1.setPower(0); //set power back to 0 when stopped

            rightMotor1.setPower(0);

            leftMotor2.setPower(0);

            rightMotor2.setPower(0);

        }

    }

}






