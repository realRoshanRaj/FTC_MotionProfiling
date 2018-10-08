/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Samples;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware;

import motionProfileGenerator.ftc.tools.PathFollower;

/**
 * This file illustrates the concept of driving/turning in a path based on time/gyro.
 * <p>
 * This class uses the REV imu to help with more accurate turns
 * <p>
 * The code assumes that you do NOT have encoders on the wheels,
 * otherwise you would use: StraightProfileEncoderFollower;
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name = "Drive By Time/Gyro", group = "SimpleBot")
@Disabled
public class DriveByGyroOnly_Linear extends LinearOpMode {

    private static final double FORWARD_SPEED = 0.6;
    private static final double TURN_SPEED = 0.5;
    /* Declare OpMode members. */
    private Hardware robot = new Hardware();   // Use Hardware
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Step 1:  Drive forward for 3 seconds
        robot.leftDrive.setPower(FORWARD_SPEED);
        robot.rightDrive.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 2:  Spin left for 1.3 seconds
        robot.leftDrive.setPower(-TURN_SPEED);
        robot.rightDrive.setPower(TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Stop and wait for 1 second
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        sleep(1000);

        // Step 3: Turn 90 degrees CW
        double targetAngle = 90;
        double tolerance = 2; //How many degrees of tolerance do you have
        double timeout = 5; //How long do you want to run this before it exits the loop
        runtime.reset(); //Reset time

        targetAngle = PathFollower.boundHalfDegrees(targetAngle);
        Orientation angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        robot.leftDrive.setPower(TURN_SPEED); // Make this negative to go CCW
        robot.rightDrive.setPower(-TURN_SPEED); //Make this positive to go CCW
        while (opModeIsActive() && (Math.abs(angle.firstAngle - targetAngle) < tolerance) && (runtime.seconds() < timeout)) {
            telemetry.addData("Current Heading", angle.firstAngle);
            telemetry.addData("Target Heading", targetAngle);
            telemetry.update();
            angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        }


        // Step 4:  Stop
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
