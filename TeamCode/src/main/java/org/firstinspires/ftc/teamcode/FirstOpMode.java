package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.utils.MovingAverageTimer;

/**

 * Created by nathan on 2/5/2017.

 */

@TeleOp(name="H2O FLOW", group="Practice-Bot")

public class FirstOpMode extends TeamOpMode {

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

        harvesterHorizontal = hardwareMap.dcMotor.get("Harvister");
        harvesterVertical = hardwareMap.dcMotor.get("harvister2");
        winch = hardwareMap.dcMotor.get("Winch");
        winch2 = hardwareMap.dcMotor.get("winch2");
        colorSensor = hardwareMap.colorSensor.get("color");
        servoSorter = hardwareMap.servo.get("servo");
        leftServo = hardwareMap.servo.get("LeftServo");
        backServo = hardwareMap.crservo.get("RightServo");

        rightMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        winch2.setDirection(DcMotorSimple.Direction.REVERSE);

// Set all motors to zero power

        leftMotor1.setPower(0);
        leftMotor2.setPower(0);
        rightMotor1.setPower(0);
        rightMotor2.setPower(0);

        harvesterVertical.setPower(0);
        harvesterHorizontal.setPower(0);

        servoSorter.setPosition(0);
        leftServo.setPosition(0);
        backServo.setPower(0);

        colorSensor.enableLed(true);
        boharvister = false;
        boreharvister = false;

        tiptime = time;
        ambred = colorSensor.red();
        ambblue = colorSensor.blue();


// Send telemetry message to signify robot waiting;

        telemetry.addData("Say", "ready");
        telemetry.update();

        telemetry.setAutoClear(false);
		
        Telemetry.Item avgItem = telemetry.addData("average" , "%12.3f", 0.0);
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

        // Create a MovingAverageTimer object so that we can time each iteration of the loop
        MovingAverageTimer avg = new MovingAverageTimer();
		
        try {

// run until the end of the match (driver presses STOP)

            while (opModeIsActive()) {
                // Update and recalculate the average
                avg.update();

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
                    harvesterHorizontal.setPower(1);
                    harvesterVertical.setPower(-1);
                }
                if (boharvister == false && boreharvister == false)
                {
                    harvesterHorizontal.setPower(0);
                    harvesterVertical.setPower(0);
                }
                if (boreharvister == true)
                {
                    harvesterHorizontal.setPower(-1);
                    harvesterVertical.setPower(1);
                }

                if (gamepad1.x)
                {
                    left = left/2;
                    right = right/2;

                }
                red = colorSensor.red();
                blue = colorSensor.blue();
                green = colorSensor.green();

                if (tiptime < (time + 0.5)) {
                    if (ambred + 40 > red && ambred - 40 < red && ambblue + 40 > blue && ambblue - 40 < blue) {
                        servoSorter.setPosition(0.5);
                    } else if (ambred + 50 < red && red > blue) {
                        servoSorter.setPosition(1);
                        tiptime = time;
                    } else if (ambblue + 50 < blue && blue > red) {
                        servoSorter.setPosition(0);
                        tiptime = time;
                    }

                }
                if (gamepad2.a) {
                    ambblue = colorSensor.blue();
                    ambred = colorSensor.red();
                }


                leftMotor1.setPower(left);
                rightMotor1.setPower(right);
                leftMotor2.setPower(left);
                rightMotor2.setPower(right);


                winchpower = gamepad2.right_trigger;
                winch.setPower(winchpower);
                winch2.setPower(winchpower);



                if (gamepad2.dpad_left)
                {
                    leftServo.setPosition(1);
                }

                if (gamepad2.dpad_right)
                {
                    backServo.setPower(1);
                }
                if (gamepad2.dpad_up)
                {
                    leftServo.setPosition(0);
                    rightServo.setPosition(0);
                }

                avgItem.setValue("%12.3f",avg.average());
                leftItem.setValue("%5.1f",left);
                rightItem.setValue("%5.1f",right);
                redItem.setValue("%5.1f",red);
                greenItem.setValue("%5.1f",green);
                blueItem.setValue("%5.1f",blue);
                ambredItem.setValue("%5.1f",ambred);
                ambblueItem.setValue("%5.1f",ambblue);
                harvisterItem.setValue("%s",boharvister?"true":"false");
                reharvisterItem.setValue("%s",boreharvister?"true":"false");
                pwharvisterItem.setValue("%5.1f",harvesterHorizontal.getPower());
                winchItem.setValue("%5.1f",winchpower);
                telemetry.update();

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

            harvesterHorizontal.setPower(0);
            harvesterVertical.setPower(0);
            winch.setPower(0);
            winch2.setPower(0);
            backServo.setPower(0);

        }

    }

}






