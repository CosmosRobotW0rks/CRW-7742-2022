package frc.robot.ControlSystem;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;
import frc.robot.Commands.HomeWheels;
import frc.robot.Commands.Autonomous.KeepLookingAt;
import frc.robot.Commands.Autonomous.TeleopHome;
import frc.robot.Intake.ApplyIntakeSpeed;
import frc.robot.Intake.Intake;
import frc.robot.Intake.StopIntake;
import frc.robot.Pneumatics.Pneumatics;
import frc.robot.Pneumatics.SetPistons;
import frc.robot.Shooter.Shooter;
import frc.robot.Shooter.Commands.ApplyConveyorSpeed;
import frc.robot.Shooter.Commands.HoldSpin;
import frc.robot.Shooter.Commands.ShootCommand;
import frc.robot.Shooter.Commands.StopConveyor;
import frc.robot.Shooter.Commands.StopSpin;

public class DashboardControl extends SubsystemBase {
    private SwerveDrivetrain drivetrain;
    private AutopilotDriver driver;
    private Pneumatics pneumatics;
    private Shooter shooter;

    public DashboardControl(SwerveDrivetrain sw, AutopilotDriver dr, Pneumatics pn, Shooter sh, Intake intk){
        drivetrain = sw;
        driver = dr;
        pneumatics = pn;
        shooter = sh;


        SmartDashboard.putData("Home wheels", new HomeWheels(drivetrain));
        SmartDashboard.putData("Navigate to Home", new TeleopHome(drivetrain, driver));

        SmartDashboard.putData("SHOOOT", new ShootCommand(shooter));
        SmartDashboard.putData("Activate SPEEEEN", new HoldSpin(shooter, 14.6));
        SmartDashboard.putData("STOP SHOOTER", new StopSpin(shooter));

        SmartDashboard.putData("CLIMB UP!", new SetPistons(pneumatics, true));
        SmartDashboard.putData("CLIMB down", new SetPistons(pneumatics, false));

        SmartDashboard.putData("Set conveyor speed", new ApplyConveyorSpeed(shooter.conveyor));
        SmartDashboard.putData("Stop conveyor", new StopConveyor(shooter.conveyor));

        SmartDashboard.putData("Set intake speed", new ApplyIntakeSpeed(intk));
        SmartDashboard.putData("Stop intake", new StopIntake(intk));

        SmartDashboard.putData("Look at Hub", new KeepLookingAt(dr, sw, new Translation2d(0, 0)));
    }

    @Override
    public void periodic(){

    }
}
