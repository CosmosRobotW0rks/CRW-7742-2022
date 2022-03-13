package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;

public class HoldSpin extends CommandBase {
    private final Shooter shooter;
    private final double targetRPS;

    public HoldSpin(Shooter sh, double tRPS) {
        shooter = sh;
        targetRPS = tRPS;
    }

    @Override
    public void initialize() {
        shooter.shooterMotorController.targetRPS = targetRPS;
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
