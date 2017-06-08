package org.firstglobal.FgCommon;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstglobal.teamcode.BuildConfig;


/**
 * Operation to assist with Gamepad actions on DCMotors
 */
public class GamePadDualMotor extends OpModeComponent {


    // amount to change the servo position by

    public enum Control {
        LEFT_STICK_X,
        LEFT_STICK_Y,
        RIGHT_STICK_X,
        RIGHT_STICK_Y,
        UP_DOWN_BUTTONS,
        LEFT_RIGHT_BUTTONS,
        LB_RB_BUTTONS,
        Y_BUTTON,
        A_BUTTON,
        X_BUTTON,
        B_BUTTON,
        LEFT_BUMPER,
        RIGHT_BUMPER
    }

    final private Control control;
    final private DcMotor motor1;
    final private DcMotor motor2;
    final private Gamepad gamepad;
    private static final float defaultButtonPower = 0.3f;
    private float buttonPower;

    /**
     * Constructor for operation.  Telemetry enabled by default.
     *
     * @param opMode  opmode
     * @param gamepad Gamepad
     * @param motor1  DcMotor to operate on
     * @param motor2  DcMotor to operate on
     * @param control {@link GamePadDualMotor.Control}
     */
    public GamePadDualMotor(FGOpMode opMode, Gamepad gamepad, DcMotor motor1, DcMotor motor2, Control control) {

        this(opMode, gamepad, motor1, motor2, control, defaultButtonPower);

    }

    /**
     * Constructor for operation.  Telemetry enabled by default.
     *
     * @param opMode      opmode
     * @param gamepad     Gamepad
     * @param motor1      DcMotor to operate on
     * @param motor2      DcMotor to operate on
     * @param control     {@link GamePadDualMotor.Control}
     * @param buttonPower power to apply when using gamepad buttons
     */
    public GamePadDualMotor(FGOpMode opMode, Gamepad gamepad, DcMotor motor1, DcMotor motor2, Control control, float buttonPower) {

        super(opMode);
        this.gamepad = gamepad;
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.control = control;
        this.buttonPower = buttonPower;

        // Check that the motors are the inverse of each other
        if (this.motor1.getDirection() == this.motor2.getDirection()) {
            throw new RuntimeException(String.format("motor1(%s) and motor2(%s) are configured with the same direction", motor1.getDeviceName(), motor2.getDeviceName()));
        }
    }


    /**
     * Update motors with latest gamepad state
     */
    public void update() {

        float power;

        //note that if y equal -1 then joystick is pushed all of the way forward.
        switch (control) {
            case LEFT_STICK_Y:
                power = scaleMotorPower(-gamepad.left_stick_y);
                break;
            case RIGHT_STICK_Y:
                power = scaleMotorPower(-gamepad.right_stick_y);
                break;
            case LEFT_STICK_X:
                power = scaleMotorPower(gamepad.left_stick_x);
                break;
            case RIGHT_STICK_X:
                power = scaleMotorPower(gamepad.right_stick_x);
                break;
            default:
                power = motorPowerFromButtons();
                break;
        }

        getOpMode().telemetry.addData("setting power: " + control.toString(), power);

        motor1.setPower(power);
        motor2.setPower(power);

    }

    public void startRunMode(DcMotor.RunMode runMode) throws InterruptedException {
        motor1.setMode(runMode);
        motor2.setMode(runMode);
        getOpMode().idle();
    }

    private float motorPowerFromButtons() {

        float powerToReturn = 0f;
        boolean y = gamepad.y;
        boolean a = gamepad.a;
        boolean x = gamepad.x;
        boolean b = gamepad.b;
        boolean lb = gamepad.left_bumper;
        boolean rb = gamepad.right_bumper;

        if (((control == Control.UP_DOWN_BUTTONS) && a) ||
                ((control == Control.LEFT_RIGHT_BUTTONS) && x)) {
            powerToReturn = -buttonPower;
        }
        if ((control == Control.LB_RB_BUTTONS) && lb) {
            powerToReturn = -buttonPower;
        } else {
            switch (control) {
                case UP_DOWN_BUTTONS:
                    if (y) powerToReturn = buttonPower;
                case Y_BUTTON:
                    if (y) powerToReturn = buttonPower;
                    break;
                case X_BUTTON:
                    if (x) powerToReturn = buttonPower;
                    break;
                case A_BUTTON:
                    if (a) powerToReturn = buttonPower;
                    break;
                case B_BUTTON:
                    if (b) powerToReturn = buttonPower;
                    break;
                case LB_RB_BUTTONS:
                    if (rb) powerToReturn = buttonPower;
                    break;
                case LEFT_BUMPER:
                    if (lb) powerToReturn = buttonPower;
                    break;
                case RIGHT_BUMPER:
                    if (rb) powerToReturn = buttonPower;
                    break;
                default:
                    powerToReturn = 0f;
                    break;
            }
        }

        return powerToReturn;


    }

    private static float[] power_curve =
            {0.00f, 0.05f, 0.09f, 0.10f, 0.12f
            , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
            , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
            , 1.00f, 1.00f
    };

    /**
     * Taken from FTC SDK PushBotWithClaw example
     * The DC motors are scaled to make it easier to control them at slower speeds
     * Obtain the current values of the joystick controllers.
     * Note that x and y equal -1 when the joystick is pushed all of the way
     * forward (i.e. away from the human holder's body).
     * The clip method guarantees the value never exceeds the range +-1.
     */
    private float scaleMotorPower(float p_power) {

        // Ensure the values are legal.
        float clipped_power = Range.clip(p_power, -1, 1);

        // Remember if this is positive or negative
        float sign = Math.signum(clipped_power);

        // Work only with positive numbers for simplicity
        float abs_power = Math.abs(clipped_power);

        // Map the power value [0..1.0] to a power curve index
        int index = (int) (abs_power * (power_curve.length - 1));

        float scaled_power = sign * power_curve[index];

        return scaled_power;

    }


}
