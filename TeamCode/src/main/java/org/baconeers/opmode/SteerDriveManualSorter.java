package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.baconeers.common.BaconOpMode;
import org.baconeers.common.ButtonControl;
import org.baconeers.common.GamePadDualMotorSteerDrive2;
import org.baconeers.common.GamePadManualBallSorter;
import org.baconeers.common.GamePadSafeDualMotor;
import org.baconeers.common.GamePadToggleMotor;
import org.baconeers.common.GamePadToggleServo;
import org.baconeers.common.GamePadManualBallSorter;
import org.baconeers.common.WhileGamePadCRServo;
import org.baconeers.configurations.KanaloaBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp(name = "H2O flow (Manual Sorter)")
//@Disabled
public class SteerDriveManualSorter extends BaconOpMode {

    private KanaloaBase robot;
    private GamePadDualMotorSteerDrive2 drive;
    private GamePadSafeDualMotor winch;
    private GamePadToggleMotor harvesterPrimary;
    private GamePadToggleMotor harvesterSecondary;
    private WhileGamePadCRServo crServo;
    private Telemetry.Item avgItem;
    private Telemetry.Item maxItem;
    private GamePadToggleServo redServo;
    private GamePadManualBallSorter sorterServo;



    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = KanaloaBase.newConfig(hardwareMap, telemetry);

        drive = new GamePadDualMotorSteerDrive2(this, gamepad1,
                robot.driveLeftLeft,robot.driveLeftRight,
                robot.driveRightLeft,robot.driveRightRight);

        winch = new GamePadSafeDualMotor(this, gamepad2, robot.winchLeft, robot.winchRight, ButtonControl.DPAD_UP, ButtonControl.RIGHT_BUMPER, 1f, false);

        crServo = new WhileGamePadCRServo(this, gamepad2, robot.bluecrservo,ButtonControl.LEFT_BUMPER,1.0f,false );
        redServo = new GamePadToggleServo(this,gamepad2,robot.redservo);
        sorterServo = new GamePadManualBallSorter(this, gamepad2, robot.sorterServo);

        harvesterPrimary = new GamePadToggleMotor(this,gamepad2,robot.harvesterPrimary, ButtonControl.A,1.0f,false);
        harvesterSecondary = new GamePadToggleMotor(this,gamepad2,robot.harvesterSecondary, ButtonControl.B,1.0f,false);


        avgItem = telemetry.addData("Avg", "%.3f ms", 0.0);
        avgItem.setRetained(true);

        maxItem = telemetry.addData("Max", "%.3f ms", 0.0);
        maxItem.setRetained(true);

    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
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

        movingAverageTimer.update();
        avgItem.setValue("%.3f ms", movingAverageTimer.movingAverage());
        maxItem.setValue("%.3f ms", movingAverageTimer.maxLoopTime());

    }

}
