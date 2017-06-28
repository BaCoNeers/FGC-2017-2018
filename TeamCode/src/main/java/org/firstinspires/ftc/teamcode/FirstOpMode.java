package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**

 * Created by nathan on 2/5/2017.

 */

@TeleOp(name="H2O FLOW", group="Practice-Bot")

public class FirstOpMode extends Opmode {

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

        double left;
        double right;
        double winchpower;
        double ambred,ambblue;
        double tiptime;

        double red,green,blue;

        boolean boharvister, boreharvister;

        leftMotor1 = hardwareMap.dcMotor.get("left1_drive");
        leftMotor2 = hardwareMap.dcMotor.get("left2_drive");


        rightMotor1 = hardwareMap.dcMotor.get("right1_drive");
        rightMotor2 = hardwareMap.dcMotor.get("right2_drive");

        Harvister = hardwareMap.dcMotor.get("Harvister");
        Harvister2 = hardwareMap.dcMotor.get("harvister2");
        whinch = hardwareMap.dcMotor.get("Winch");
        whinch2 = hardwareMap.dcMotor.get("winch2");
        ColorSensor = hardwareMap.colorSensor.get("color");
        Servo = hardwareMap.servo.get("servo");
        LeftServo = hardwareMap.servo.get("LeftServo");
        RightServo = hardwareMap.servo.get("RightServo");

        rightMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        whinch2.setDirection(DcMotorSimple.Direction.REVERSE);

// Set all motors to zero power

        leftMotor1.setPower(0);

        leftMotor2.setPower(0);

        rightMotor1.setPower(0);

        rightMotor2.setPower(0);

        Harvister.setPower(0);

        Servo.setPosition(0);

        LeftServo.setPosition(0);

        RightServo.setPosition(1);

        ColorSensor.enableLed(true);
        boharvister = false;
        boreharvister = false;

        tiptime = time;
        ambred = ColorSensor.red();

        ambblue = ColorSensor.blue();




// Send telemetry message to signify robot waiting;

        telemetry.addData("Say", "ready"); //

        telemetry.update();

        telemetry.setAutoClear(false);

        Telemetry.Item leftItem = telemetry.addData("left","%5.1f", 0.0);
        Telemetry.Item rightItem = telemetry.addData("right","%5.1f", 0.0);
        Telemetry.Item redItem = telemetry.addData("red","%5.1f", 0.0);
        Telemetry.Item greenItem = telemetry.addData("green","%5.1f", 0.0);
        Telemetry.Item blueItem = telemetry.addData("blue","%5.1f", 0.0);
        Telemetry.Item ambredItem = telemetry.addData("ambred","%5.1f", 0.0);
        Telemetry.Item ambgreenItem = telemetry.addData("ambred", "%5.1f", 0.0);
        Telemetry.Item ambblueItem = telemetry.addData("ambblue","%5.1f",0.0);
        Telemetry.Item harvisterItem = telemetry.addData("harvister","%5.1f", 0.0);
        Telemetry.Item reharvisterItem = telemetry.addData("reharvister","%5.1f", 0.0);
        Telemetry.Item pwharvisterItem = telemetry.addData("pwharvister","%5.1f", 0.0);
        Telemetry.Item winchItem = telemetry.addData("winch","%5.1f", 0.0);





// Wait for the game to start (driver presses PLAY)

        waitForStart();


        try {

// run until the end of the match (driver presses STOP)

            while (opModeIsActive()) {

                double rotation1 = -gamepad1.left_stick_x /2;
                double power1 = gamepad1.left_trigger - gamepad1.right_trigger;
                left = power1 + rotation1;
                right = power1 - rotation1;

                if (gamepad1.a)
                {
                    sleep(50);
                    boharvister ^= true;
                    if (boreharvister == true)
                    {
                        boreharvister = false;
                    }
                }

                if (gamepad1.left_bumper & gamepad1.a)
                {
                    sleep(50);
                    boreharvister ^= true;
                    if (boharvister == true)
                    {
                        boharvister = false;
                    }
                }


                if (boharvister == true)
                {
                    Harvister.setPower(1);
                    Harvister2.setPower(-1);
                }
                if (boharvister == false && boreharvister == false)
                {
                    Harvister.setPower(0);
                    Harvister2.setPower(0);
                }
                if (boreharvister == true)
                {
                    Harvister.setPower(-1);
                    Harvister2.setPower(1);
                }

                if (gamepad1.x)
                {
                    left = left/2;
                    right = right/2;

                }
                red = ColorSensor.red();
                blue = ColorSensor.blue();
                green = ColorSensor.green();

                if (tiptime < (time + 0.5)) {
                    if (ambred + 40 > red && ambred - 40 < red && ambblue + 40 > blue && ambblue - 40 < blue) {
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


                leftMotor1.setPower(left);
                rightMotor1.setPower(right);
                leftMotor2.setPower(left);
                rightMotor2.setPower(right);


                winchpower = gamepad2.right_trigger;
                whinch.setPower(winchpower);
                whinch2.setPower(winchpower);



                if (gamepad2.dpad_left)
                {
                    LeftServo.setPosition(1);
                }

                if (gamepad2.dpad_right)
                {
                    RightServo.setPosition(1);
                }
                if (gamepad2.dpad_up)
                {
                    LeftServo.setPosition(0);
                    RightServo.setPosition(0);
                }


                leftItem.setValue("%5.1f",left);
                rightItem.setValue("%5.1f",right);
                redItem.setValue("%5.1f",red);
                greenItem.setValue("%5.1f",green);
                blueItem.setValue("%5.1f",blue);
                ambredItem.setValue("%5.1f",ambred);
                ambblueItem.setValue("%5.1f",ambblue);
                harvisterItem.setValue("%s",boharvister?"true":"false");
                reharvisterItem.setValue("%s",boreharvister?"true":"false");
                pwharvisterItem.setValue("%5.1f",Harvister.getPower());
                winchItem.setValue("%5.1f",winchpower);
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

            leftMotor1.setPower(0);

            rightMotor1.setPower(0);

            leftMotor2.setPower(0);

            rightMotor2.setPower(0);

            Harvister.setPower(0);
            Harvister2.setPower(0);
            whinch.setPower(0);
            whinch2.setPower(0);

        }

    }

}






