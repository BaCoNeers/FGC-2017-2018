package baconeers.configurations;

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
public class KanaloaBase extends RobotConfiguration {
    // Left and right is viewed in the frame of the robot with the front being the harvester

    // Left motors
    public DcMotor LeftLeft;
    public DcMotor LeftRight;

    // Right motors
    public DcMotor RightLeft;
    public DcMotor RightRight;

    // Winch motors
    public DcMotor WinchLeft;
    public DcMotor WinchRight;

    // Harvester motors
    public DcMotor HarvesterPrimary;
    public DcMotor HarvesterSecondary;




    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetry
     */
    @Override
    protected void init(HardwareMap hardwareMap, Telemetry telemetry) {

        setTelemetry(telemetry);

        LeftLeft = (DcMotor) getHardwareOn("motor0", hardwareMap.dcMotor);
        LeftRight = (DcMotor) getHardwareOn("motor1", hardwareMap.dcMotor);
        LeftRight.setDirection(DcMotor.Direction.REVERSE);

        RightLeft = (DcMotor) getHardwareOn("motor2", hardwareMap.dcMotor);
        RightRight = (DcMotor) getHardwareOn("motor3", hardwareMap.dcMotor);
        RightRight.setDirection(DcMotor.Direction.REVERSE);



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
