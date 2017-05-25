package org.firstglobal.teamcode.configurations;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstglobal.FgCommon.RobotConfiguration;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * It is assumed that there is a configuration that is currently activated on the robot controller
 * (run menu / Configure Robot ) with the same name as this class.
 * It is also assumed that the device names in the 'init()' method below are the same as the devices
 * named on the activated configuration on the robot.
 */
public class PushBotWithClaw extends RobotConfiguration {

    //motors
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public DcMotor arm;
    public Servo claw;



    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetry
     */
    @Override
    protected void init(HardwareMap hardwareMap, Telemetry telemetry) {

        setTelemetry(telemetry);

        backLeft = (DcMotor) getHardwareOn("motor0", hardwareMap.dcMotor);
        frontLeft = (DcMotor) getHardwareOn("motor1", hardwareMap.dcMotor);

        frontRight = (DcMotor) getHardwareOn("motor2", hardwareMap.dcMotor);
        frontRight.setDirection(DcMotor.Direction.REVERSE);

        backRight = (DcMotor) getHardwareOn("motor3", hardwareMap.dcMotor);
        backRight.setDirection(DcMotor.Direction.REVERSE);


        arm = (DcMotor) getHardwareOn("motor5", hardwareMap.dcMotor);
        arm.setDirection(DcMotor.Direction.REVERSE);

        claw = (Servo) getHardwareOn("servo0", hardwareMap.servo);

    }


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetry
     * @return
     */
    public static PushBotWithClaw newConfig(HardwareMap hardwareMap, Telemetry telemetry) {

        PushBotWithClaw config = new PushBotWithClaw();
        config.init(hardwareMap, telemetry);
        return config;

    }


}
