package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TestAutoSequential extends SequentialCommandGroup {

  public TestAutoSequential(SwerveDrivetrain drivetrain, AutonomousWaypointDriver driver) {
    addCommands(

        new AutonomousWaypointDriveCommand(driver, drivetrain, new Pose2d(1, 0, Rotation2d.fromDegrees(180))),
        new AutonomousWaypointDriveCommand(driver, drivetrain, new Pose2d(1, 1, Rotation2d.fromDegrees(270))),
        new AutonomousWaypointDriveCommand(driver, drivetrain, new Pose2d(0, 1, Rotation2d.fromDegrees(360))),
        new AutonomousWaypointDriveCommand(driver, drivetrain, new Pose2d(0, 1, Rotation2d.fromDegrees(360))),
        new AutonomousWaypointDriveCommand(driver, drivetrain, new Pose2d(1, 1, Rotation2d.fromDegrees(360))),
        new AutonomousWaypointDriveCommand(driver, drivetrain, new Pose2d(1, 0, Rotation2d.fromDegrees(360))),
        new AutonomousWaypointDriveCommand(driver, drivetrain, new Pose2d(0, 0, Rotation2d.fromDegrees(360)))

    );
  }
}