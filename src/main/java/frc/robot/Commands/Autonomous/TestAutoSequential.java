package frc.robot.Commands.Autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.AutopilotCommand;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;

public class TestAutoSequential extends SequentialCommandGroup {

  public TestAutoSequential(SwerveDrivetrain drivetrain, AutopilotDriver driver) {
    addCommands(

        new AutopilotCommand(driver, drivetrain, new Pose2d(1, 0, Rotation2d.fromDegrees(180))),
        new AutopilotCommand(driver, drivetrain, new Pose2d(1, 1, Rotation2d.fromDegrees(270))),
        new AutopilotCommand(driver, drivetrain, new Pose2d(0, 1, Rotation2d.fromDegrees(360))),
        new AutopilotCommand(driver, drivetrain, new Pose2d(0, 1, Rotation2d.fromDegrees(360))),
        new AutopilotCommand(driver, drivetrain, new Pose2d(1, 1, Rotation2d.fromDegrees(360))),
        new AutopilotCommand(driver, drivetrain, new Pose2d(1, 0, Rotation2d.fromDegrees(360))),
        new AutopilotCommand(driver, drivetrain, new Pose2d(0, 0, Rotation2d.fromDegrees(360)))

    );
  }
}