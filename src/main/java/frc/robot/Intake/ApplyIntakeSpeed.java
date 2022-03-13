package frc.robot.Intake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Shooter.Conveyor;

public class ApplyIntakeSpeed extends CommandBase {
    private final Intake intake;

    public ApplyIntakeSpeed(Intake itk) {
        intake = itk;
        SmartDashboard.putNumber("Intake Power", 0);
    }

    @Override
    public void initialize() {
        intake.SetSpeed(SmartDashboard.getNumber("Intake Power", 0));
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
