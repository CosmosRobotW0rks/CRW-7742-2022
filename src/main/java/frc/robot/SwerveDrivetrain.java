package frc.robot;

import edu.wpi.first.math.kinematics.*; //TODO Odometry
import edu.wpi.first.math.geometry.*;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;

public class SwerveDrivetrain {
    private final double WIDTH = 11.2; // Inches? TODO Add dimensions
    private final double HEIGHT = 10.5; //

    private SwerveModule TL = new SwerveModule(4, 3);
    private SwerveModule TR = new SwerveModule(8, 7);
    private SwerveModule BL = new SwerveModule(2, 1);
    private SwerveModule BR = new SwerveModule(6, 5);
    private AHRS gyro = new AHRS(SPI.Port.kMXP);

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
}
