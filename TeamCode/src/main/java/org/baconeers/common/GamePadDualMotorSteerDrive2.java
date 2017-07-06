package org.baconeers.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class GamePadDualMotorSteerDrive2 extends BaconComponent {

    final private DcMotor leftLeftMotor;
    final private DcMotor leftRightMotor;
    final private DcMotor rightLeftMotor;
    final private DcMotor rightRightMotor;
    final private Gamepad gamepad;
    private Telemetry.Item leftPowerItem = null;
    private Telemetry.Item rightPowerItem = null;
    private Telemetry.Item steerPowerItem = null;
    private Telemetry.Item rawPowerItem = null;


    public GamePadDualMotorSteerDrive2(BaconOpMode opMode, Gamepad gamepad,
                                       DcMotor leftLeftMotor, DcMotor leftRightMotor,
                                       DcMotor rightLeftMotor, DcMotor rightRightMotor) {
        super(opMode);

        this.gamepad = gamepad;
        this.leftLeftMotor = leftLeftMotor;
        this.leftRightMotor = leftRightMotor;
        this.rightLeftMotor = rightLeftMotor;
        this.rightRightMotor = rightRightMotor;

        leftPowerItem = getOpMode().telemetry.addData("Left power", "%.2f", 0.0f);
        leftPowerItem.setRetained(true);
        rightPowerItem = getOpMode().telemetry.addData("Right power", "%.2f", 0.0f);
        rightPowerItem.setRetained(true);
        steerPowerItem = getOpMode().telemetry.addData("steer power", "%.2f", 0.0f);
        steerPowerItem.setRetained(true);
        rawPowerItem = getOpMode().telemetry.addData("raw power", "%.2f", 0.0f);
        rawPowerItem.setRetained(true);
    }

    /*
     * Update the motor power based on the gamepad state
     */
    public void update() {

        float triggerPower;
//        float scalePower = scaleTriggerPower(gamepad.left_stick_y);

        boolean rightTriggerOn = gamepad.right_trigger > 0;
        boolean rightBumperOn = gamepad.right_bumper;

        boolean leftTriggerOn = gamepad.left_trigger > 0;
        boolean leftBumperOn = gamepad.left_bumper;

        float slowPower = 0.3f;
        float mediumPower = 0.6f;
        float fastPower = 1.0f;

        if (rightTriggerOn && rightBumperOn) {
            triggerPower = -mediumPower;
        }
        else if (rightTriggerOn) {
            triggerPower = -fastPower;
        }
        else if (rightBumperOn){
            triggerPower = -slowPower;
        }
        else {
            if (leftTriggerOn && leftBumperOn){
                triggerPower = mediumPower;
            }
            else if (leftTriggerOn){
                triggerPower = fastPower;
            }
            else if (leftBumperOn){
                triggerPower = slowPower;
            }
            else {
                triggerPower = 0.0f;
            }
        }

        float steer = scaleSteerPower(gamepad.left_stick_x);
        float leftPower;
        float rightPower;
        if (triggerPower == 0.0f) {
            leftPower = -steer;
            rightPower = steer;
        }
        else {
            leftPower = triggerPower * ((steer > 0) ? 1.0f - steer : 1.0f);
            rightPower = triggerPower * ((steer < 0) ? 1.0f + steer : 1.0f);
        }


        leftLeftMotor.setPower(leftPower);
        leftRightMotor.setPower(leftPower);
        rightLeftMotor.setPower(rightPower);
        rightRightMotor.setPower(rightPower);

        leftPowerItem.setValue("%.2f", leftPower);
        rightPowerItem.setValue("%.2f", rightPower);
        steerPowerItem.setValue("%.2f", steer);
        rawPowerItem.setValue("%.2f", triggerPower);
    }

    private static float[] steer_curve =
            {0.00f, 0.1f, 0.2f, 0.3f, 0.4f, 0.4f, 0.4f, 0.5f, 0.5f, 0.7f};

    /**
     * The DC motors are scaled to make it easier to control them at slower speeds
     * The clip method guarantees the value never exceeds the range 0-1.
     */

    private float scaleSteerPower(float p_power) {

        // Ensure the values are legal.
        float clipped_power = Range.clip(p_power, -1, 1);

        // Remember if this is positive or negative
        float sign = Math.signum(clipped_power);

        // Work only with positive numbers for simplicity
        float abs_power = Math.abs(clipped_power);

        // Map the power value [0..1.0] to a power curve index
        int index = (int) (abs_power * (steer_curve.length - 1));

        float scaled_power = sign * steer_curve[index];

        return scaled_power;

    }

}
