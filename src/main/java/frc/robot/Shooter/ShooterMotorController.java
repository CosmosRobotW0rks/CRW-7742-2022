package frc.robot.Shooter;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

import edu.wpi.first.math.controller.PIDController;
import java.util.Date;

public class ShooterMotorController extends SubsystemBase {
    private final int ENCODER_CLICKS_PER_ROTATION = 800;

    private VictorSPX ShooterMotor = new VictorSPX(15);
    private PIDController ShooterPID = new PIDController(0.5, 0, 0.05);

    private long prevMillis;
    private long prevEncoder;

    public double targetRPS = 0;
    public double tolerance = 3;
    public boolean reachedTarget = false;

    double[] prevRPS = new double[15];

    EncoderComms Encoders;

    public ShooterMotorController(EncoderComms encoders){
        Encoders = encoders;
        prevMillis = new Date().getTime();

        VictorSPXConfiguration config = new VictorSPXConfiguration();
        config.peakOutputForward = 1;
        config.peakOutputReverse = 0;

        ShooterMotor.configAllSettings(config);
    }
    

    public double GetRPS() {
        Date date = new Date();
        long millis = date.getTime();
        double dt = (millis - prevMillis) / 1000f;
        prevMillis = millis;

        long encoder = Encoders.shooterPos;
        double encoderDelta = encoder - prevEncoder;
        prevEncoder = encoder;

        double RPS = encoderDelta / dt / ENCODER_CLICKS_PER_ROTATION;

        prevRPS[0] = RPS;
        double avg = 0;
        for (int i = 13; i >= 0; i--) {
            prevRPS[i + 1] = prevRPS[i];
            avg += prevRPS[i];
        }
        return avg / 15d;
    }

    public void UpdateMotor() {
        double RPS = GetRPS();
        SmartDashboard.putNumber("Shooter RPS", RPS);

        double shooterPwr = ShooterPID.calculate(RPS, targetRPS);
        shooterPwr = targetRPS >= 5 ? Math.max(Math.min(shooterPwr, 0.85), -0.85) : 0;

        SmartDashboard.putNumber("Shooter Power", shooterPwr);
        ShooterMotor.set(ControlMode.PercentOutput, shooterPwr);
        reachedTarget = shooterPwr != 0 ? Math.abs(RPS - targetRPS) < tolerance : false;
    }

    void SetTargetRPS(double target_RPS) {
        targetRPS = target_RPS;
    }

    @Override
    public void periodic() {
        //ShooterPID.setP(SmartDashboard.getNumber("Shooter PID P", 1));
        //ShooterPID.setD(SmartDashboard.getNumber("Shooter PID D", 0.1));
    }
}
