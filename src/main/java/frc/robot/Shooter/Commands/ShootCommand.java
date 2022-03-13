package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Shooter.Shooter;

public class ShootCommand extends SequentialCommandGroup{
    public ShootCommand(Shooter shooter) {
        addCommands(
            new SpinUp(shooter, 13),
            new HoldSpin(shooter, 13),
            new MoveBallToShooter(shooter),
            new StopSpin(shooter)
        );
      }
}
