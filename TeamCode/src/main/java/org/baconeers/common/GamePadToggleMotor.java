package org.baconeers.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstglobal.FgCommon.FGOpMode;
import org.firstglobal.FgCommon.OpModeComponent;
import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * Operation to assist with Gamepad actions on DCMotors
 */
public class GamePadToggleMotor extends BaconComponent {

    public enum Control {
        DPAD_UP,
        DPAD_DOWN,
        DPAD_LEFT,
        DPAD_RIGHT,
        A,
        B,
        X,
        Y,
        START,
        BACK,
        LEFT_BUMPER,
        RIGHT_BUMPER,
        LEFT_STICK_BUTTON,
        RIGHT_STICK_BUTTON
    }

    private final Control control;
    private final DcMotor motor;
    private final Gamepad gamepad;
    private final float motorPower;
    private boolean motorOn = false;
    private boolean lastButtonState = false;
    private final Telemetry.Item item;


    /**
     * Constructor for operation.  Telemetry enabled by default.
     *
     * @param opMode
     * @param gamepad Gamepad
     * @param motor   DcMotor to operate on
     * @param control {@link GamePadToggleMotor.Control}
     * @param power   power to apply when using gamepad buttons
     */
    public GamePadToggleMotor(BaconOpMode opMode, Gamepad gamepad, DcMotor motor, Control control, float power) {
        super(opMode);

        this.gamepad = gamepad;
        this.motor = motor;
        this.control = control;
        this.motorPower = power;

        item = opMode.telemetry.addData("Control " + control.name(), 0.0f);
        item.setRetained(true);
    }


    /**
     * Update motors with latest gamepad state
     */
    public void update() {
        // Only toggle when the button state changes from false to true, ie when the
        // button is pressed down (and not when the button comes back up)
        boolean pressed = buttonPressed();
        if (pressed && lastButtonState != pressed) {
            motorOn = !motorOn;
            float power = motorOn ? motorPower : 0.0f;
            motor.setPower(power);
            item.setValue(power);
        }
        lastButtonState = pressed;
    }

    private boolean buttonPressed() {
        boolean buttonPressed = false;
        switch (control) {

            case DPAD_UP:
                buttonPressed = gamepad.dpad_up;
                break;
            case DPAD_DOWN:
                buttonPressed = gamepad.dpad_down;
                break;
            case DPAD_LEFT:
                buttonPressed = gamepad.dpad_left;
                break;
            case DPAD_RIGHT:
                buttonPressed = gamepad.dpad_right;
                break;
            case A:
                // ignore if start is also pressed to avoid triggering when initialising the
                // controllers
                if (!gamepad.start) {
                    buttonPressed = gamepad.a;
                }
                break;
            case B:
                // ignore if start is also pressed to avoid triggering when initialising the
                // controllers
                if (!gamepad.start) {
                    buttonPressed = gamepad.b;
                }
                break;
            case X:
                buttonPressed = gamepad.x;
                break;
            case Y:
                buttonPressed = gamepad.y;
                break;
            case START:
                buttonPressed = gamepad.start;
                break;
            case BACK:
                buttonPressed = gamepad.back;
                break;
            case LEFT_BUMPER:
                buttonPressed = gamepad.left_bumper;
                break;
            case RIGHT_BUMPER:
                buttonPressed = gamepad.right_bumper;
                break;
            case LEFT_STICK_BUTTON:
                buttonPressed = gamepad.left_stick_button;
                break;
            case RIGHT_STICK_BUTTON:
                buttonPressed = gamepad.right_stick_button;
                break;
        }

        return buttonPressed;
    }


}
