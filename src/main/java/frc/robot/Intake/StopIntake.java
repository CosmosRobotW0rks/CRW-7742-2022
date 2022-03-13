package frc.robot.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StopIntake extends CommandBase {
    private final Intake intake;

    public StopIntake(Intake itk) {
        intake = itk;
    }

    @Override
    public void initialize() {
        intake.SetSpeed(0);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
