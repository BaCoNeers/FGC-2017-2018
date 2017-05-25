package org.firstglobal.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstglobal.teamcode.configurations.TwoMotorRobot;
import org.firstglobal.FgCommon.FGOpMode;
import org.firstglobal.FgCommon.GamePadMotor;


@TeleOp (group = "TwoMotorRobot")
//@Disabled
public class DriveTwoMotors extends FGOpMode {

    private TwoMotorRobot robot;
    private GamePadMotor  leftWheel;
    private GamePadMotor  rightWheel;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = TwoMotorRobot.newConfig(hardwareMap, telemetry);

    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        leftWheel = new GamePadMotor(this,  gamepad1, robot.motor0, GamePadMotor.Control.LEFT_STICK_Y);
        rightWheel = new GamePadMotor(this,  gamepad1, robot.motor1, GamePadMotor.Control.RIGHT_STICK_Y);
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the motor with the gamepad joystick values
        leftWheel.update();
        //update the servo with the gamepad y/a button values
        rightWheel.update();


    }

}
