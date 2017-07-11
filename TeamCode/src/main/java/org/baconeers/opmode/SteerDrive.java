package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.baconeers.common.BaconOpMode;
import org.baconeers.common.ButtonControl;
import org.baconeers.common.GamePadDualMotorSteerDrive;
import org.baconeers.common.GamePadDualMotorSteerDrive2;
import org.baconeers.common.GamePadSafeDualMotor;
import org.baconeers.common.GamePadToggleCRServo;
import org.baconeers.common.GamePadToggleMotor;
import org.baconeers.common.GamePadToggleServo;
import org.baconeers.common.KanaloaBallSorter;
import org.baconeers.common.WhileGamePadCRServo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.baconeers.configurations.KanaloaBase;


@TeleOp(name = "SteerDrive2")
//@Disabled
public class SteerDrive extends BaconOpMode {

    private KanaloaBase robot;
    private GamePadDualMotorSteerDrive2 drive;
    private GamePadSafeDualMotor winch;
    private GamePadToggleMotor harvesterPrimary;
    private GamePadToggleMotor harvesterSecondary;
    private WhileGamePadCRServo crServo;
    private Telemetry.Item avgItem;
    private KanaloaBallSorter kanaloaBallSorter;
    private GamePadToggleServo redServo;



    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = KanaloaBase.newConfig(hardwareMap, telemetry);

        drive = new GamePadDualMotorSteerDrive2(this, gamepad1,
                robot.driveLeftLeft,robot.driveLeftRight,
                robot.driveRightLeft,robot.driveRightRight);

        winch = new GamePadSafeDualMotor(this, gamepad2, robot.winchLeft, robot.winchRight, ButtonControl.DPAD_UP, ButtonControl.RIGHT_BUMPER, 1f, true);

        kanaloaBallSorter = new KanaloaBallSorter(this,  robot.sorterColorSensor,robot.sorterServo);

        crServo = new WhileGamePadCRServo(this, gamepad2, robot.bluecrservo,ButtonControl.LEFT_BUMPER,1.0f,false );
        redServo = new GamePadToggleServo(this,gamepad2,robot.redservo);

        harvesterPrimary = new GamePadToggleMotor(this,gamepad2,robot.harvesterPrimary, ButtonControl.A,1.0f,false);
        harvesterSecondary = new GamePadToggleMotor(this,gamepad2,robot.harvesterSecondary, ButtonControl.B,1.0f,false);


        avgItem = telemetry.addData("Avg", "%.3f ms", 0.0);
        avgItem.setRetained(true);


    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        kanaloaBallSorter.init();
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the drive motors with the gamepad  values
        drive.update();

        // Update the Winch
        winch.update();

        // Update the Harvester
        harvesterPrimary.update();
        harvesterSecondary.update();

        //Update the servo's
        crServo.update();
        redServo.update();

        //Update the Ball Sorter
        if (loopCount % 5 ==0) {
            kanaloaBallSorter.update();
        }
        movingAverageTimer.update();
        avgItem.setValue("%.3f ms", movingAverageTimer.movingAverage());

    }

}
