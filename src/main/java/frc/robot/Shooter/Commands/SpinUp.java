package frc.robot.Shooter.Commands;

import java.util.Date;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Shooter;
import frc.robot.Shooter.ShooterMotorController;

public class SpinUp extends CommandBase {
    private final Shooter shooter;
    private final double targetRPS;

    int hold = 0;
    int holdtgt = 20;
    long pms = 0;

    public SpinUp(Shooter sh, double tRPS) {
        shooter = sh;
        targetRPS = tRPS;
    }

    @Override
    public void initialize() {
        System.out.println("SPEEEEN");
        shooter.shooterMotorController.targetRPS = targetRPS;

        pms = new Date().getTime();
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
