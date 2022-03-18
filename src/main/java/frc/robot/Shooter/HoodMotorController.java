package frc.robot.Shooter;

import java.util.Date;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class HoodMotorController {
    private final double ENCODER_TICKS_PER_ROTATION = 71 * 7 * 2 * (22d / 26d); // gear ratio * encoder ticks * pulley ratio
    private VictorSPX hoodMotor = new VictorSPX(11);
    private EncoderComms Encoders;
    private PIDController pidController = new PIDController(0, 2, 0);
    private long prevms = 0;
    private long prevEncoder = 0;

    public double targetRevs = 45d / 360d;

    public long encoderOffset = 30;

    HoodMotorController(EncoderComms encoders) {
        Encoders = encoders;

        VictorSPXConfiguration config = new VictorSPXConfiguration();
        config.peakOutputForward = 0.2;
        config.peakOutputReverse = -1;

        hoodMotor.configAllSettings(config);
        hoodMotor.set(ControlMode.PercentOutput, 0);

        prevms = new Date().getTime();
    }

    public boolean isAtTarget() {
        return Math.abs(((Encoders.hoodPos - encoderOffset) / ENCODER_TICKS_PER_ROTATION) - (targetRevs + 0.05)) < 0.0175;
    }

    public void UpdateMotor() {
        long ms = new Date().getTime();
        double dt = (double) (ms - prevms) / 1000d;
        double output = 0;
        prevms = ms;

        double pos = (Encoders.hoodPos - encoderOffset) / ENCODER_TICKS_PER_ROTATION;

        SmartDashboard.putNumber("Hood target", targetRevs);
        SmartDashboard.putNumber("Hood pos", (Encoders.hoodPos - encoderOffset) / ENCODER_TICKS_PER_ROTATION);
        encoderOffset = (long)SmartDashboard.getNumber("Hood encoder offset", 50);
        SmartDashboard.putBoolean("Hood target reached", isAtTarget());

        if (Robot.isdisabled) {
            hoodMotor.set(ControlMode.PercentOutput, 0);
            pidController.reset();
            return;
        }

        if (isAtTarget())
            return;

        // output += pidController.calculate(((Encoders.hoodPos - encoderOffset) /
        // ENCODER_TICKS_PER_ROTATION), targetRevs) * dt;
        // output = Math.max(Math.min(output, 1), -1);

        double targetRPS = 0.1;

        if (!isAtTarget()) {
            if (pos < (targetRevs + 0.05))
                targetRPS = 0.1;
            else
                targetRPS = -0.05;
        } //else
           //targetRPS = 0.0125;

        long encoder = Encoders.hoodPos;
        double encoderDelta = encoder - prevEncoder;
        prevEncoder = encoder;

        double RPS = encoderDelta / dt / ENCODER_TICKS_PER_ROTATION;

        output = pidController.calculate(RPS, targetRPS);
        output = Math.max(Math.min(output, 1), -1);

        SmartDashboard.putNumber("Hood output", output);
        //hoodMotor.set(ControlMode.PercentOutput, -output);
    }

    public void SetAngle(double targetAngle) {

    }
}
