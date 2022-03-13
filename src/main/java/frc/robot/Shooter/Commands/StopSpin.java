package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;

public class StopSpin extends CommandBase {
    private final Shooter shooter;

    public StopSpin(Shooter sh) {
        shooter = sh;
    }

    @Override
    public void initialize() {
        shooter.shooterMotorController.targetRPS = 0;
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
