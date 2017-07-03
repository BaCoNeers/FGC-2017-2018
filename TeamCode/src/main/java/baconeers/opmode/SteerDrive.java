package baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import baconeers.common.BaconOpMode;
import baconeers.common.GamePadDualMotorSteerDrive;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import baconeers.configurations.KanaloaBase;


@TeleOp(group = "Steer Drive")
//@Disabled
public class SteerDrive extends BaconOpMode {

    private KanaloaBase robot;
    private GamePadDualMotorSteerDrive drive;
    private Telemetry.Item avgItem;


    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = KanaloaBase.newConfig(hardwareMap, telemetry);

    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        drive = new GamePadDualMotorSteerDrive(this, gamepad1,
                robot.driveLeftLeft,robot.driveLeftRight,
                robot.driveRightLeft,robot.driveRightRight);
        avgItem = telemetry.addData("Avg", "%.3f ms", 0.0);
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called in a loop on each hardware cycle
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //update the drive motors with the gamepad  values
        drive.update();

        movingAverageTimer.update();
        avgItem.setValue("%.3f ms", movingAverageTimer.movingAverage());

    }

}
