package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.baconeers.common.BaconOpMode;
import org.baconeers.common.ButtonControl;
import org.baconeers.common.GamePadDualMotorSteerDrive;
import org.baconeers.common.GamePadToggleMotor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.baconeers.configurations.KanaloaBase;


@TeleOp(name = "SteerDrive2")
//@Disabled
public class SteerDrive extends BaconOpMode {

    private KanaloaBase robot;
    private GamePadDualMotorSteerDrive drive;
    private GamePadToggleMotor harvesterPrimary;
    private GamePadToggleMotor harvesterSecondary;
    private Telemetry.Item avgItem;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = KanaloaBase.newConfig(hardwareMap, telemetry);

        drive = new GamePadDualMotorSteerDrive(this, gamepad1,
                robot.driveLeftLeft,robot.driveLeftRight,
                robot.driveRightLeft,robot.driveRightRight);

        harvesterPrimary = new GamePadToggleMotor(this,gamepad1,robot.harvesterPrimary, ButtonControl.A,1.0f,false);
        harvesterSecondary = new GamePadToggleMotor(this,gamepad1,robot.harvesterSecondary, ButtonControl.B,1.0f,false);


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

    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the drive motors with the gamepad  values
        drive.update();

        // Update the Harvester
        harvesterPrimary.update();
        harvesterSecondary.update();

        movingAverageTimer.update();
        avgItem.setValue("%.3f ms", movingAverageTimer.movingAverage());

    }

}
