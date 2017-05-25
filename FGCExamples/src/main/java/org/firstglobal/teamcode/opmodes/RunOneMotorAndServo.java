package org.firstglobal.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstglobal.FgCommon.FGOpMode;
import org.firstglobal.FgCommon.GamePadMotor;
import org.firstglobal.FgCommon.GamePadServo;
import org.firstglobal.teamcode.configurations.MotorAndServoRobot;


@TeleOp (group = "MotorAndServoRobot")
//@Disabled
public class RunOneMotorAndServo extends FGOpMode {

    private MotorAndServoRobot robot;
    private GamePadMotor motorFromStick;
    private GamePadServo xbButtonServo;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = MotorAndServoRobot.newConfig(hardwareMap, telemetry);

    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        motorFromStick = new GamePadMotor(
                this,
                gamepad1,
                robot.motor0,
                GamePadMotor.Control.LEFT_STICK_Y);

        double initialPosition = 0.3;
        xbButtonServo = new GamePadServo(
                this,gamepad1,
                robot.servo0,
                GamePadServo.Control.X_B ,
                initialPosition );

        //use the below instead when configuring a Continuous Rotation Servo
        //xbButtonServo = new GamePadServo(
        // this,gamepad1,robot.servo0, GamePadServo.Control.X_B  );
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the motor with the gamepad joystick values
        motorFromStick.update();
        //update the servo with the gamepad y/a button values
        xbButtonServo.update();

    }

}
