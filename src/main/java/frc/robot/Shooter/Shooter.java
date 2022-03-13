package frc.robot.Shooter;

public class Shooter {
    public Conveyor conveyor = new Conveyor();
    public EncoderComms encoders = new EncoderComms();
    public ShooterMotorController shooterMotorController = new ShooterMotorController(encoders);
    public HoodMotorController hoodMotorController = new HoodMotorController(encoders);

    public void Setup(){
    }

    public void SlowUpdate(){
        shooterMotorController.UpdateMotor();
        hoodMotorController.UpdateMotor();
        encoders.readEncoders();
    }
}
