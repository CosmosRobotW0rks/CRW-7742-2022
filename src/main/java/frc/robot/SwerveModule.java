package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;

public class SwerveModule {
    private CANSparkMax AngleSpark;
    private CANSparkMax DriveSpark;
    private RelativeEncoder AngleEncoder;

    private SparkMaxPIDController angleController;

    public SwerveModule(int AngleCANID, int DriveCANID) {
        AngleSpark = new CANSparkMax(AngleCANID, CANSparkMaxLowLevel.MotorType.kBrushless);
        DriveSpark = new CANSparkMax(DriveCANID, CANSparkMaxLowLevel.MotorType.kBrushless);

        AngleEncoder = AngleSpark.getEncoder();
        AngleEncoder.setPosition(0);
        AngleEncoder.setPositionConversionFactor(2.0 * Math.PI / (18 / 1));

        angleController = AngleSpark.getPIDController();
        angleController.setP(1.5);
        angleController.setI(0);
        angleController.setD(0.5);
    }

    double TargetAngle = 0;

    public void Drive(double Speed) {
        DriveSpark.set(Speed);
    }

    public double Angle() {
        return AngleEncoder.getPosition();
    }

    public void SetTarget(double angle) {
        TargetAngle = Math.toRadians(angle);

        double currentAngle = AngleEncoder.getPosition();
        // Clamp currentAngle
        double currentAngleMod = currentAngle % (2.0 * Math.PI);
        if (currentAngleMod < 0.0)
            currentAngleMod += 2.0 * Math.PI;

        // Find shortest route
        double newTarget = TargetAngle + currentAngle - currentAngleMod;
        if (TargetAngle - currentAngleMod > Math.PI)
            newTarget -= 2.0 * Math.PI;
        if (TargetAngle - currentAngleMod < -Math.PI)
            newTarget += 2.0 * Math.PI;

        angleController.setReference(newTarget, ControlType.kPosition);
    }
}