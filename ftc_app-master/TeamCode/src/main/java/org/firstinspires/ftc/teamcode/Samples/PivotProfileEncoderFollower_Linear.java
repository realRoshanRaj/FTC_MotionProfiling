package org.firstinspires.ftc.teamcode.Samples;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware;

import java.util.Timer;
import java.util.TimerTask;

import motionProfileGenerator.ftc.tools.Config;
import motionProfileGenerator.ftc.tools.PathFollower;
import motionProfileGenerator.ftc.tools.PivotProfileGenerator;
import motionProfileGenerator.ftc.tools.WheelTrajectory;

/**
 * This file illustrates the concept of driving a motion profiling path using encoders and periods of time
 * <p>
 * This class uses encoders to help with more precise movement
 * <p>
 * The code assumes that you do have encoders on the wheels,
 *
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * <P>
 * The hardware file constants need to be correctly configured for your robot
 */
@Autonomous(name = "MPDrivePivot", group = "MPBot")
@Disabled
public class PivotProfileEncoderFollower_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware robot = new Hardware();   // Use a bot's hardware

    //Constants are inside the hardware file

    Timer time = new Timer();

    Config config;
    WheelTrajectory trajectory;
    PathFollower follower;

    int index = 0;
    boolean isRunning = false;

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);


        //Sets both motors to brake mode
        robot.leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        config = new Config(robot.dt, robot.max_velocity, robot.max_acceleration);


        //Generate Trajectory by entering the degree you want to turn
        generateTrajectory(45);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        if (opModeIsActive()) {
            follower.configureEncoder(robot.rightDrive.getCurrentPosition(), robot.leftDrive.getCurrentPosition(), robot.ticks_per_revolution, robot.wheel_diameter);
            follower.configurePV(robot.kP_Pivot, robot.kV_Drive);
            time.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (opModeIsActive()) {
                        isRunning = true;
                        int currLeftPos = robot.leftDrive.getCurrentPosition();
                        int currRightPos = robot.leftDrive.getCurrentPosition();

                        double left = follower.calculateLeftPower(index, currLeftPos);
                        double right = follower.calculateRightPower(index, currRightPos);

                        // Normalize the values so neither exceed +/- 1.0
                        double max = Math.max(Math.abs(left), Math.abs(right));
                        if (max > 1.0) {
                            left /= max;
                            right /= max;
                        }

                        robot.leftDrive.setPower(left);
                        robot.rightDrive.setPower(right);
                    } else {
                        isRunning = false;
                        robot.leftDrive.setPower(0);
                        robot.rightDrive.setPower(0);
                        time.cancel();
                    }
                    if (index >= trajectory.getLeftTrajectory().length()) {
                        isRunning = false;
                        robot.leftDrive.setPower(0);
                        robot.rightDrive.setPower(0);
                        time.cancel();
                    }

                    index++;
                }
            }, 0, (long) (robot.dt * 1000));
        }

        while (opModeIsActive()) {
            telemetry.addData("Left Power", robot.leftDrive.getPower());
            telemetry.addData("Right Power", robot.rightDrive.getPower());
            telemetry.addData("Current Segment", index);
            telemetry.update();
        }

        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
    }

    /**
     * Counter-Clockwise is negative
     * Clockwise is positive
     */
    private void generateTrajectory(double angle) {
        angle = PathFollower.boundHalfDegrees(angle);
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Generating ", "Trajectory");
        telemetry.update();
        trajectory = PivotProfileGenerator.generateTrajectory(config, robot.wheelbase_width, angle);
        follower = new PathFollower(trajectory);
    }


}
