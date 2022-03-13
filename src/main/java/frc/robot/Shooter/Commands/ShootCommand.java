package frc.robot.Shooter.Commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Autonomous.KeepLookingAt;
import frc.robot.Shooter.Shooter;

public class ShootCommand extends SequentialCommandGroup {
    public ShootCommand(Shooter shooter, double speed, double angle) {
        addCommands(
                new SetHood(shooter, angle),
                new SpinUp(shooter, speed),
                new HoldSpin(shooter, speed),
                new MoveBallToShooter(shooter),
                new StopSpin(shooter),
                new SetHood(shooter, 0));
    }

    public ShootCommand(Shooter shooter) {
        double angle = shooter.physx.shootAngle.getDouble(0);
        double speed = shooter.physx.shootSpeed.getDouble(0);

        addCommands(
                new KeepLookingAt(shooter.driver, shooter.drivetrain, new Translation2d(0, 0)),
                new SetHood(shooter, angle),
                new SpinUp(shooter, speed),
                new HoldSpin(shooter, speed),
                new MoveBallToShooter(shooter),
                new StopSpin(shooter),
                new SetHood(shooter, 0));
    }
}
