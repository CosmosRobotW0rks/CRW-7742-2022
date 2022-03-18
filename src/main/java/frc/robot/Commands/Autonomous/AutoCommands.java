package frc.robot.Commands.Autonomous;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;
import frc.robot.Commands.ResetOdometry;
import frc.robot.Shooter.Commands.ShootCommand;

public class AutoCommands extends SequentialCommandGroup {

  public AutoCommands(SwerveDrivetrain drivetrain, AutopilotDriver driver) {
    addCommands(
        // new ShootCommand(sh, 12)
        new ResetOdometry(drivetrain, new Pose2d(0, 0, Rotation2d.fromDegrees(0))),
        new AutopilotCommand(driver, drivetrain, new Pose2d(0, 2, Rotation2d.fromDegrees(0))));
  }
}