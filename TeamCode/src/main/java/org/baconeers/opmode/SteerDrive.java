package org.baconeers.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.baconeers.common.BaconOpMode;
import org.baconeers.common.ButtonControl;
import org.baconeers.common.ColorSensorThread;
import org.baconeers.common.GamePadDualMotorSteerDrive2;
import org.baconeers.common.GamePadSafeDualMotor;
import org.baconeers.common.GamePadSafeDualMotorwinch;
import org.baconeers.common.GamePadToggleMotor;
import org.baconeers.common.GamePadToggleMotorWithRevers;
import org.baconeers.common.GamePadToggleServo;
import org.baconeers.common.KanaloaBallSorter;
import org.baconeers.common.WhileGamePadCRServo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.baconeers.configurations.KanaloaBase;

import java.util.concurrent.TimeUnit;


@TeleOp(name = "H2O flow without telemetry")
//@Disabled
public class SteerDrive extends BaconOpMode {

    private KanaloaBase robot;
    private GamePadDualMotorSteerDrive2 drive;
    private GamePadSafeDualMotor winch;
    private GamePadSafeDualMotorwinch winch2;
    private GamePadToggleMotorWithRevers harvesterPrimary;
    private GamePadToggleMotor harvesterSecondary;
    private WhileGamePadCRServo blueServo;
    private WhileGamePadCRServo redServo;
    private Telemetry.Item avgItem;
    private Telemetry.Item maxItem;
    private Telemetry.Item cpuItem;
    private KanaloaBallSorter kanaloaBallSorter;
    private ColorSensorThread colorSensor = null;






    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = KanaloaBase.newConfig(hardwareMap, telemetry);

        drive = new GamePadDualMotorSteerDrive2(this, gamepad1,
                robot.driveLeftLeft,robot.driveLeftRight,
                robot.driveRightLeft,robot.driveRightRight);

        winch = new GamePadSafeDualMotor(this, gamepad2, robot.winchLeft, robot.winchRight, ButtonControl.LEFT_BUMPER, ButtonControl.RIGHT_BUMPER, 1f, false);
        winch2 = new GamePadSafeDualMotorwinch(this,gamepad2, robot.winchLeft, robot.winchRight, ButtonControl.LEFT_BUMPER,ButtonControl.RIGHT_BUMPER, 1f, 0.5f,false);

        robot.sorterColorSensor.enableLed(true);
        colorSensor = new ColorSensorThread(this, robot.sorterColorSensor, 20, 100, TimeUnit.MILLISECONDS);
        kanaloaBallSorter = new KanaloaBallSorter(this,colorSensor,robot.sorterServo, false);



        blueServo = new WhileGamePadCRServo(this, gamepad2, robot.blueCRServo,ButtonControl.DPAD_UP,ButtonControl.DPAD_DOWN,1.0f,false );
        redServo = new WhileGamePadCRServo(this,gamepad2,robot.redCRServo,ButtonControl.DPAD_LEFT,ButtonControl.DPAD_RIGHT,1.0f,false);

        harvesterPrimary = new GamePadToggleMotorWithRevers(this,gamepad2,robot.harvesterPrimary, ButtonControl.A,ButtonControl.X,1.0f,false);
        harvesterSecondary = new GamePadToggleMotor(this,gamepad2,robot.harvesterSecondary, ButtonControl.B, 1.0f,false);


        avgItem = telemetry.addData("Avg", "%.3f ms", 0.0);
        avgItem.setRetained(true);

        maxItem = telemetry.addData("Max", "%.3f ms", 0.0);
        maxItem.setRetained(true);

        cpuItem = telemetry.addData("Cpu","%2f",0.0);
        cpuItem.setRetained(true);

    }

    /**
     * Implement this method to define the code to run when the Play button is pressed on the Driver station.
     * This code will run once.
     */
    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        kanaloaBallSorter.init();
        colorSensor.onStart();

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

        winch2.update();

        // Update the Harvester
        harvesterPrimary.update();
        harvesterSecondary.update();

        //Update the servo's
        blueServo.update();
        redServo.update();

        //Update the Ball Sorter

            kanaloaBallSorter.update();

        movingAverageTimer.update();
        avgItem.setValue("%.3f ms", movingAverageTimer.movingAverage());
        maxItem.setValue("%.3f ms", movingAverageTimer.maxLoopTime());


    }


    protected void onStop() throws InterruptedException {
        super.onStop();
        colorSensor.onStop();

        colorSensor.enableLed(false);
    }

}
