package frc.robot.Shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;

public class HoldSpinBlocking extends CommandBase {
    private final Shooter shooter;
    private final double targetRPS;

    double MPS_PER_RPS = 0.51;
    double ENG_COEFF = 1.25;

    public HoldSpinBlocking(Shooter sh, double tMPS) {
        shooter = sh;
        targetRPS = (tMPS / MPS_PER_RPS) * ENG_COEFF;
    }

    @Override
    public void initialize() {
        shooter.shooterMotorController.SetTargetRPS(targetRPS);;
    }

    @Override
    public void end(boolean interrupted) {
        shooter.shooterMotorController.SetTargetRPS(0);;
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
