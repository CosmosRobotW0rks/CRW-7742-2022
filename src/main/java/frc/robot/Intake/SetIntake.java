package frc.robot.Intake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetIntake extends CommandBase {
    private final Intake intake;
    private final double speed;

    public SetIntake(Intake itk, double spd) {
        intake = itk;
        speed = spd;
    }

    @Override
    public void initialize() {
        intake.SetSpeed(speed);
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
