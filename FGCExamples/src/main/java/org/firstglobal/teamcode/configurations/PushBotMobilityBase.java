package org.firstglobal.teamcode.configurations;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstglobal.FgCommon.RobotConfiguration;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * It is assumed that there is a configuration that is currently activated on the robot controller
 * (run menu / Configure Robot ) with the same name as this class.
 * It is also assumed that the device names in the 'init()' method below are the same as the devices
 * named on the activated configuration on the robot.
 */
public class PushBotMobilityBase extends RobotConfiguration {

    //motors
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;


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

    }


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetry
     * @return
     */
    public static PushBotMobilityBase newConfig(HardwareMap hardwareMap, Telemetry telemetry) {

        PushBotMobilityBase config = new PushBotMobilityBase();
        config.init(hardwareMap, telemetry);
        return config;

    }


}
