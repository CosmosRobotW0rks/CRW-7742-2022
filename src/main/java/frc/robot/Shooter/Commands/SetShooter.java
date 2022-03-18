package frc.robot.Shooter.Commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Autonomous.KeepLookingAt;
import frc.robot.Shooter.Shooter;

public class SetShooter extends CommandBase {
    private final Shooter shooter;

    public SetShooter(Shooter sh) {
        shooter = sh;
    }

    @Override
    public void execute() {
        new HoldSpin(shooter, shooter.physx.shootSpeed.getDouble(0)).schedule();
        new SetHood(shooter, 90 - shooter.physx.shootAngle.getDouble(0)).schedule();
    }

    @Override
    public void end(boolean interrupted) {
        new HoldSpin(shooter, 0).schedule();
        new SetHood(shooter, 0).schedule();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
