package frc.robot;

import edu.wpi.first.math.kinematics.*; //TODO Odometry
import edu.wpi.first.math.geometry.*;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrivetrain extends SubsystemBase {
    private final double WIDTH = 11.2; // Inches? TODO Add dimensions
    private final double HEIGHT = 10.5; //

    private SwerveModule TL = new SwerveModule(4, 3);
    private SwerveModule TR = new SwerveModule(8, 7);
    private SwerveModule BL = new SwerveModule(2, 1);
    private SwerveModule BR = new SwerveModule(6, 5);
    public AHRS gyro = new AHRS(SPI.Port.kMXP);

    private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(WIDTH, HEIGHT),
            new Translation2d(WIDTH, -HEIGHT),
            new Translation2d(-WIDTH, HEIGHT),
            new Translation2d(-WIDTH, -HEIGHT));

    public void Setup() {
        gyro.calibrate();
    }

    public void Drive(Translation2d xyspeed, double zrotation) {
        ChassisSpeeds fieldOrientedXYSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(xyspeed.getX(), xyspeed.getY(),
                zrotation,
                Rotation2d.fromDegrees(-gyro.getAngle())); // Gyro is upside down? TODO Invert gyro properly

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(fieldOrientedXYSpeeds);

        // Set angles
        // TODO This could be refactored
        TL.SetTarget(states[0].angle.getDegrees());
        TR.SetTarget(states[1].angle.getDegrees());
        BL.SetTarget(states[2].angle.getDegrees());
        BR.SetTarget(states[3].angle.getDegrees());

        // Set speeds
        TL.Drive(states[0].speedMetersPerSecond);
        TR.Drive(states[1].speedMetersPerSecond);
        BL.Drive(states[2].speedMetersPerSecond);
        BR.Drive(states[3].speedMetersPerSecond);
    }

    public void Home() {
        TL.SetTarget(0);
        TR.SetTarget(0);
        BL.SetTarget(0);
        BR.SetTarget(0);
    }

    public Rotation2d gyroAngle = new Rotation2d();
    public SwerveDriveOdometry odometry = new SwerveDriveOdometry(kinematics, gyroAngle);
    Pose2d OdometryOutPose;

    public void Odometry() {
        SwerveModuleState[] states = new SwerveModuleState[4];
        states[0] = TL.GetState();
        states[1] = TR.GetState();
        states[2] = BL.GetState();
        states[3] = BR.GetState();

        gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());
        odometry.update(gyroAngle, states);
        OdometryOutPose = odometry.getPoseMeters();

        double x_formatted = Math.floor(OdometryOutPose.getX() * 1000) / 1000;
        double Y_formatted = Math.floor(OdometryOutPose.getY() * 1000) / 1000;

        SmartDashboard.putString("ODOM_POSE", ("x: " + x_formatted + ", y: " + Y_formatted));
    }

    @Override
    public void periodic() {
        Odometry();
    }
}
