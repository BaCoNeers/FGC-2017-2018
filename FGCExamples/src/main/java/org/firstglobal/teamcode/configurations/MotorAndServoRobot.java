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
public class MotorAndServoRobot extends RobotConfiguration {

    //motors
    public DcMotor motor0;
    public Servo servo0;
    //use the below instead when configuring a Continuous Rotation Servo
    //public CRServo serv0;

    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetry
     */
    @Override
    protected void init(HardwareMap hardwareMap, Telemetry telemetry) {

        setTelemetry(telemetry);

        servo0 = (Servo) getHardwareOn("servo0", hardwareMap.servo);
        //use the below instead when configuring a Continuous Rotation Servo
        //servo0 = (CRServo) getHardwareOn("servo1", hardwareMap.crservo);

        motor0 = (DcMotor) getHardwareOn("motor0", hardwareMap.dcMotor);


    }


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetry
     * @return
     */
    public static MotorAndServoRobot newConfig(HardwareMap hardwareMap, Telemetry telemetry) {

        MotorAndServoRobot config = new MotorAndServoRobot();
        config.init(hardwareMap, telemetry);
        return config;

    }



}
