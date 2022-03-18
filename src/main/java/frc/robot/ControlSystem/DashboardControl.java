package frc.robot.ControlSystem;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;
import frc.robot.Commands.HomeWheels;
import frc.robot.Commands.ResetOdometry;
import frc.robot.Commands.Autonomous.KeepLookingAt;
import frc.robot.Commands.Autonomous.TeleopHome;
import frc.robot.Intake.ApplyIntakeSpeed;
import frc.robot.Intake.Intake;
import frc.robot.Intake.StopIntake;
import frc.robot.Pneumatics.Pneumatics;
import frc.robot.Pneumatics.SetCompressor;
import frc.robot.Pneumatics.SetPistons;
import frc.robot.Shooter.Shooter;
import frc.robot.Shooter.Commands.ApplyConveyorSpeed;
import frc.robot.Shooter.Commands.HoldSpin;
import frc.robot.Shooter.Commands.HoldSpinBlocking;
import frc.robot.Shooter.Commands.MoveBallBack;
import frc.robot.Shooter.Commands.SetShooter;
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

        //SmartDashboard.putData("SHOOOT", new ShootCommand(shooter, 27, 8.5));
        SmartDashboard.putData("Activate SPEEEEN", new HoldSpinBlocking(shooter, 30));
        SmartDashboard.putData("KARPUZLAMA", new HoldSpinBlocking(shooter, SmartDashboard.getNumber("TARMAC speed", 9.5)));
        SmartDashboard.putData("SHOOTMODE", new SetShooter(shooter));
        SmartDashboard.putData("STOP SHOOTER", new StopSpin(shooter));

        SmartDashboard.putData("CLIMB UP!", new SetPistons(pneumatics, true));
        SmartDashboard.putData("CLIMB down", new SetPistons(pneumatics, false));

        SmartDashboard.putData("Enable compressor", new SetCompressor(pneumatics, true));
        SmartDashboard.putData("Disable compressor", new SetCompressor(pneumatics, false));

        SmartDashboard.putData("Set conveyor speed", new ApplyConveyorSpeed(shooter.conveyor));
        SmartDashboard.putData("Stop conveyor", new StopConveyor(shooter.conveyor));
        SmartDashboard.putData("Move Ball", new MoveBallBack(shooter));

        SmartDashboard.putData("Set intake speed", new ApplyIntakeSpeed(intk));
        SmartDashboard.putData("Stop intake", new StopIntake(intk));

        SmartDashboard.putData("Look at Hub", new KeepLookingAt(dr, sw, new Translation2d(0, 0)));
        SmartDashboard.putData("RESET 1.2, 0", new ResetOdometry(drivetrain, new Pose2d(-1.2, 0, Rotation2d.fromDegrees(0))));
    }

    @Override
    public void periodic() {

    }
}
