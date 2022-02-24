package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SwerveDrivetrain;

public class HomeWheels extends CommandBase {

    private final SwerveDrivetrain Drivetrain;

    public HomeWheels(SwerveDrivetrain drivetrain) {
        Drivetrain = drivetrain;
        addRequirements(Drivetrain);
    }

    @Override
    public void initialize() {
        Drivetrain.Home();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
