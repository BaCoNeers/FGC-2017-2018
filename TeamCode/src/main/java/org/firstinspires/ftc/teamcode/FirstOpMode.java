package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**

 * Created by nathan on 2/5/2017.

 */

@TeleOp(name="HI", group="Practice-Bot")

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

        double red,green,blue;
        boolean boharvester, boreharvester;

        leftMotor1 = hardwareMap.dcMotor.get("left1_drive");
        leftMotor2 = hardwareMap.dcMotor.get("left2_drive");


        rightMotor1 = hardwareMap.dcMotor.get("right1_drive");
        rightMotor2 = hardwareMap.dcMotor.get("right2_drive");

        Harvester = hardwareMap.dcMotor.get("Harvester");
        Harvester2 = hardwareMap.dcMotor.get("harvester2");
        winch = hardwareMap.dcMotor.get("Winch");
        winch2 = hardwareMap.dcMotor.get("winch2");
        ColorSensor = hardwareMap.colorSensor.get("color");
        Servo = hardwareMap.servo.get("servo");
        LeftServo = hardwareMap.servo.get("LeftServo");
        RightServo = hardwareMap.servo.get("RightServo");

        rightMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        winch2.setDirection(DcMotorSimple.Direction.REVERSE);

// Set all motors to zero power

        leftMotor1.setPower(0);

        leftMotor2.setPower(0);

        rightMotor1.setPower(0);

        rightMotor2.setPower(0);

        Harvester.setPower(0);

        Servo.setPosition(0);

        LeftServo.setPosition(0);

        RightServo.setPosition(0);

        ColorSensor.enableLed(true);
        boharvester = false;
        boreharvester = false;




// Send telemetry message to signify robot waiting;

        telemetry.addData("Say", "ready"); //

        telemetry.update();

// Wait for the game to start (driver presses PLAY)

        waitForStart();


        try {

// run until the end of the match (driver presses STOP)

            while (opModeIsActive()) {

                double rotation1 = -gamepad1.left_stick_x /2;
                double power1 = gamepad1.right_trigger - gamepad1.left_trigger;
                left = power1 + rotation1;
                right = power1 - rotation1;

                if (gamepad1.a)
                {
                    sleep(50);
                    boharvester ^= true;
                    if (boreharvester == true)
                    {
                        boreharvester = false;
                    }
                }

                if (gamepad1.left_bumper & gamepad1.a)
                {
                    sleep(50);
                    boreharvester ^= true;
                    if (boharvester == true)
                    {
                        boharvester = false;
                    }
                }


                if (boharvester == true)
                {
                    Harvester.setPower(1);
                    Harvester2.setPower(-1);
                }
                if (boharvester == false && boreharvester == false)
                {
                    Harvester.setPower(0);
                    Harvester2.setPower(0);
                }
                if (boreharvester == true)
                {
                    Harvester.setPower(-1);
                    Harvester2.setPower(1);
                }

                if (gamepad1.x)
                {
                    left = left/2;
                    right = right/2;

                }


                leftMotor1.setPower(left);
                rightMotor1.setPower(right);
                leftMotor2.setPower(left);
                rightMotor2.setPower(right);


                winchpower = gamepad2.right_trigger;
                winch.setPower(winchpower);
                winch2.setPower(winchpower);


                red = ColorSensor.red();
                blue = ColorSensor.blue();
                green = ColorSensor.green();

                if (40< red && red > blue)
                {
                    Servo.setPosition(0);
                }
                if (40 < blue && blue > red)
                {
                    Servo.setPosition(1);
                }

                if (gamepad2.dpad_left)
                {
                    LeftServo.setPosition(1);
                }
                if (gamepad2.dpad_right)
                {
                    RightServo.setPosition(1);
                }

                telemetry.addData("left: ",left  );
                telemetry.addData("right: ", right);
                telemetry.addData("red: ", red);
                telemetry.addData("green: ", green);
                telemetry.addData("blue: ", blue);
                telemetry.addData("Harister: ", boharvester);
                telemetry.addData("reHarvester",boreharvester);
                telemetry.addData("Harvester", Harvester.getPower());
                telemetry.addData("winch", winchpower);
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

            leftMotor1.setPower(0);

            rightMotor1.setPower(0);

            leftMotor2.setPower(0);

            rightMotor2.setPower(0);

            Harvester.setPower(0);
            Harvester2.setPower(0);
            winch.setPower(0);
            winch2.setPower(0);

        }

    }

}






