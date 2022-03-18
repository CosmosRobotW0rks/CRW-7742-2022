package frc.robot.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;

public class Shooter {
    public SwerveDrivetrain drivetrain;
    public AutopilotDriver driver;

    public Conveyor conveyor = new Conveyor();
    public EncoderComms encoders = new EncoderComms();
    public ShooterMotorController shooterMotorController = new ShooterMotorController(encoders);
    public HoodMotorController hoodMotorController = new HoodMotorController(encoders);
    public PhysicsProcessor physx;

    public Shooter(SwerveDrivetrain drive, AutopilotDriver drvr){
        drivetrain = drive;
        driver = drvr;
        physx = new PhysicsProcessor(drivetrain, this);

        SmartDashboard.putNumber("Hood encoder offset", 50);
    }

    public void Setup(){
        encoders.Setup();
		physx.Setup();
    }

    public void SlowUpdate(){
        shooterMotorController.UpdateMotor();
        hoodMotorController.UpdateMotor();
        encoders.readEncoders();
        physx.UpdateData();
    }
}
