package org.firstglobal.teamcode.configurations;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstglobal.FgCommon.RobotConfiguration;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * It is assumed that there is a configuration that is currently activated on the robot controller
 * (run menu / Configure Robot ) with the same name as this class.
 * It is also assumed that the device names in the 'init()' method below are the same as the devices
 * named on the activated configuration on the robot.
 */
public class TwoMotorRobot extends RobotConfiguration {

    //motors
    public DcMotor motor0;
    public DcMotor motor1;


    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetry
     */
    @Override
    protected void init(HardwareMap hardwareMap, Telemetry telemetry) {

        setTelemetry(telemetry);

        motor0 = (DcMotor) getHardwareOn("motor0", hardwareMap.dcMotor);
        motor0.setDirection(DcMotorSimple.Direction.REVERSE);

        motor1 = (DcMotor) getHardwareOn("motor1", hardwareMap.dcMotor);

    }


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetry
     * @return
     */
    public static TwoMotorRobot newConfig(HardwareMap hardwareMap, Telemetry telemetry) {

        TwoMotorRobot config = new TwoMotorRobot();
        config.init(hardwareMap, telemetry);
        return config;

    }


}
