package org.baconeers.configurations;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
public class KanaloaBase extends RobotConfiguration {
    // Left and right is viewed in the frame of the robot with the front being the harvester

    // Left motors
    public DcMotor driveLeftLeft;
    public DcMotor driveLeftRight;

    // Right motors
    public DcMotor driveRightLeft;
    public DcMotor driveRightRight;

    // Winch motors
    public DcMotor winchLeft;
    public DcMotor winchRight;

    // Harvester motors
    public DcMotor harvesterPrimary;
    public DcMotor harvesterSecondary;

    // Color Sensor
    public ColorSensor sorterColorSensor;
    public Servo sorterServo;

    // Ball release left
    public Servo orangeBallRelease;

    // Ball release rear
    public CRServo blueBallRelease;


    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetry
     */
    @Override
    protected void init(HardwareMap hardwareMap, Telemetry telemetry) {

        setTelemetry(telemetry);

        driveLeftLeft = (DcMotor) getHardwareOn("left1_drive", hardwareMap.dcMotor);
        driveLeftLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveLeftRight = (DcMotor) getHardwareOn("left2_drive", hardwareMap.dcMotor);

        driveRightLeft = (DcMotor) getHardwareOn("right1_drive", hardwareMap.dcMotor);
        driveRightLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveRightRight = (DcMotor) getHardwareOn("right2_drive", hardwareMap.dcMotor);

        winchLeft = hardwareMap.dcMotor.get("Winch");
        winchLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        winchRight = hardwareMap.dcMotor.get("winch2");

        harvesterPrimary = hardwareMap.dcMotor.get("Harvister");
        harvesterSecondary = hardwareMap.dcMotor.get("harvister2");
        harvesterSecondary.setDirection(DcMotorSimple.Direction.REVERSE);

        sorterColorSensor = hardwareMap.colorSensor.get("color");
        sorterServo = hardwareMap.servo.get("servo");

        orangeBallRelease = hardwareMap.servo.get("LeftServo");
        blueBallRelease = hardwareMap.crservo.get("BackServo");
        blueBallRelease.setDirection(CRServo.Direction.REVERSE);
    }


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetry
     * @return
     */
    public static KanaloaBase newConfig(HardwareMap hardwareMap, Telemetry telemetry) {

        KanaloaBase config = new KanaloaBase();
        config.init(hardwareMap, telemetry);
        return config;
    }


}
