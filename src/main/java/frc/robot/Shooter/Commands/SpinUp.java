package frc.robot.Shooter.Commands;

import java.util.Date;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;

public class SpinUp extends CommandBase {
    private final Shooter shooter;
    private final double targetRPS;

    double MPS_PER_RPS = 0.51;
    double ENG_COEFF = 1.2;

    int hold = 0;
    int holdtgt = 15;
    long pms = 0;

    public SpinUp(Shooter sh, double tMPS) {
        shooter = sh;
        targetRPS = (tMPS / MPS_PER_RPS) * ENG_COEFF;
    }

    @Override
    public void initialize() {
        System.out.println("SPEEEEN");
        shooter.shooterMotorController.targetRPS = targetRPS;

        pms = new Date().getTime();
        hold = 0;
    }

    @Override
    public void execute() {
        if(new Date().getTime() - pms > 100){
            pms = new Date().getTime();
            Check();
        }
    }

    public void Check(){
        if (shooter.shooterMotorController.reachedTarget)
            hold++;
        else
            hold = 0;

        System.out.println(hold);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.shooterMotorController.targetRPS = 0;
        hold = 0;
    }

    @Override
    public boolean isFinished() {
        if (hold >= holdtgt) {
            System.out.println("Spun Up!");
            return true;
        }
        return false;
    }
}
