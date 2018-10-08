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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * This is NOT an opmode.
 * <p>
 * This class can be used to define all the specific hardware for a single robot.
 * <p>
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 * <p>
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 *
 */
public class Hardware {
    /* Public OpMode members. */
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    // The IMU sensor object
    public BNO055IMU imu;

    public final int ticks_per_revolution = 1440;
    public final double wheel_diameter = 4.0;
    public final double wheelbase_width = 12;

    public final double dt = 0.1; // Time between each cycle
    // Use kinematic test to get max vel and acceleration
    public final double max_velocity = 10;
    public final double max_acceleration = 10;

    /*The three values below need to be adjusted/tuned for your robot*/
    public final double kP_DriveForward = 0.1;
    public final double kP_Pivot = 0.1;
    public final double kP_TurnAdjustment = 0.1;

    public final double kV_Drive =  1/max_velocity;


    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public Hardware() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        imu = hwMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;

        imu.initialize(parameters);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); //comment out if robot doesn't use encoders
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // comment out if robot doesn't use encoders

        //Set motor direction
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);


        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}

