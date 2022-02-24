package frc.robot.Commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SwerveDrivetrain;

public class ResetOdometry extends CommandBase {

    private final SwerveDrivetrain Drivetrain;
    public final Pose2d pose;

    public ResetOdometry(SwerveDrivetrain drivetrain, Pose2d p) {
        Drivetrain = drivetrain;
        addRequirements(Drivetrain);

        pose = p;
    }

    @Override
    public void initialize() {
        Drivetrain.odometry.resetPosition(pose, Rotation2d.fromDegrees(-Drivetrain.gyro.getAngle()));
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
