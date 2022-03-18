package frc.robot.Shooter.Commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Autonomous.KeepLookingAt;
import frc.robot.Shooter.Shooter;

public class ShootCommand extends SequentialCommandGroup {
    public ShootCommand(Shooter shooter, double speed) {
        addCommands(
                new SpinUp(shooter, speed),
                new HoldSpin(shooter, speed),
                new MoveBallToShooter(shooter),
                new StopSpin(shooter));
    }
}
