package org.firstglobal.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstglobal.teamcode.configurations.PushBotWithClaw;
import org.firstglobal.FgCommon.FGOpMode;
import org.firstglobal.FgCommon.GamePadMotor;
import org.firstglobal.FgCommon.GamePadServo;


@TeleOp ( group = "PushBotWithClaw")
//@Disabled
public class DrivePushBotFromTwoGamepads extends FGOpMode {

    private PushBotWithClaw robot;
    private GamePadMotor frontRightStick;
    private GamePadMotor frontLeftStick;
    private GamePadMotor backRightStick;
    private GamePadMotor backLeftStick;

    private GamePadMotor armUpDown;
    private GamePadServo xbButtonServo;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = PushBotWithClaw.newConfig(hardwareMap, telemetry);

    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        //GAMEPAD 1
        frontRightStick = new GamePadMotor(this,  gamepad1, robot.frontRight, GamePadMotor.Control.RIGHT_STICK_Y);
        backRightStick = new GamePadMotor(this,  gamepad1, robot.backRight, GamePadMotor.Control.RIGHT_STICK_Y);

        frontLeftStick = new GamePadMotor(this,  gamepad1, robot.frontLeft, GamePadMotor.Control.LEFT_STICK_Y);
        backLeftStick = new GamePadMotor(this,  gamepad1, robot.backLeft, GamePadMotor.Control.LEFT_STICK_Y);

        //GAMEPAD 2
        armUpDown = new GamePadMotor(this, gamepad2, robot.arm, GamePadMotor.Control.UP_DOWN_BUTTONS);
        xbButtonServo = new GamePadServo(this,gamepad2,robot.claw, GamePadServo.Control.X_B , .5 );

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the motor with the gamepad joystick values
        frontLeftStick.update();
        frontRightStick.update();
        backLeftStick.update();
        backRightStick.update();
        armUpDown.update();
        xbButtonServo.update();


    }

}
