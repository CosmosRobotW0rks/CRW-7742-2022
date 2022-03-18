package frc.robot.ControlSystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.AutopilotDriver;
import frc.robot.SwerveDrivetrain;
import frc.robot.Commands.Autonomous.KeepLookingAt;
import frc.robot.Intake.ApplyIntakeSpeed;
import frc.robot.Intake.Intake;
import frc.robot.Intake.SetIntake;
import frc.robot.Intake.StopIntake;
import frc.robot.Pneumatics.Pneumatics;
import frc.robot.Pneumatics.SetPistons;
import frc.robot.Shooter.*;
import frc.robot.Shooter.Commands.ApplyConveyorSpeed;
import frc.robot.Shooter.Commands.HoldSpin;
import frc.robot.Shooter.Commands.HoldSpinBlocking;
import frc.robot.Shooter.Commands.MoveBallBack;
import frc.robot.Shooter.Commands.SetConveyor;
import frc.robot.Shooter.Commands.ShootCommand;
import frc.robot.Shooter.Commands.StopConveyor;
import frc.robot.Shooter.Commands.StopSpin;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class JoystickDriver extends SubsystemBase {
    private Joystick joystick = new Joystick(0);
    private SwerveDrivetrain drivetrain;
    private Pneumatics pneumatics;
    private Shooter sht;

    public boolean TranslationXEnabled = false;
    public boolean TranslationYEnabled = false;
    public boolean RotationZEnabled = false;

    KeepLookingAt lookCMD;

    public JoystickDriver(SwerveDrivetrain drive, AutopilotDriver driver, Shooter shooter, Pneumatics pn, Intake itk) {
        drivetrain = drive;
        drivetrain.joyDriver = this;
        pneumatics = pn;
        sht = shooter;

        // new JoystickButton(joystick, 1).whenPressed(new ShootCommand(shooter, 27,
        // 8.5));
        lookCMD = new KeepLookingAt(driver, drive, new Translation2d(0, 0));

        new JoystickButton(joystick, 8).whenPressed(new SetIntake(itk, 0.75));
        new JoystickButton(joystick, 3).whenPressed(new StopIntake(itk));
        new JoystickButton(joystick, 4).whenPressed(new SetConveyor(shooter.conveyor, 0.5));
        new JoystickButton(joystick, 2).whenPressed(new StopConveyor(shooter.conveyor));

        new JoystickButton(joystick, 6).whenPressed(lookCMD);

        new JoystickButton(joystick, 5).whileActiveOnce(new HoldSpinBlocking(shooter, SmartDashboard.getNumber("OUTER speed", 15)));
        new JoystickButton(joystick, 1).whileActiveOnce(new HoldSpinBlocking(shooter, SmartDashboard.getNumber("TARMAC speed", 11)));
        new JoystickButton(joystick, 9).whenPressed(new StopSpin(shooter));
        new JoystickButton(joystick, 10).whileActiveOnce(new HoldSpinBlocking(shooter, -5));


        /*
         * new JoystickButton(joystick, 8).whenPressed(new TeleopHome(drivetrain,
         * driver));
         * new JoystickButton(joystick, 7).whenPressed(new HomeWheels(drivetrain));
         * new JoystickButton(joystick, 10).whenPressed(new
         * ResetOdometryWithJoystick(drivetrain, joystick));
         * new JoystickButton(joystick, 1).whenPressed(new ShootCommand(shooter));
         * new JoystickButton(joystick, 9).whenPressed(new HoldSpin(shooter, 14.6));
         */

        new JoystickButton(joystick, 7).whenPressed(new MoveBallBack(shooter));

    }

    @Override
    public void periodic() {
        TranslationXEnabled = SmartDashboard.getBoolean("Joystick X Enabled", true);
        TranslationYEnabled = SmartDashboard.getBoolean("Joystick Y Enabled", true);
        RotationZEnabled = SmartDashboard.getBoolean("Joystick R Enabled", true);

        double xSpeed = TranslationXEnabled ? -joystick.getRawAxis(1) / 1 : 0;
        double ySpeed = TranslationYEnabled ? -joystick.getRawAxis(0) / 1 : 0;
        double rot = RotationZEnabled ? joystick.getRawAxis(4) / 8 : 0;

        SmartDashboard.putString("Input Speed: ", ("x: " + xSpeed + ", y: " + ySpeed + ", rot: " + rot));

        xSpeed = Math.abs(xSpeed) > 0.2 ? xSpeed : 0;
        ySpeed = Math.abs(ySpeed) > 0.2 ? ySpeed : 0;
        rot = Math.abs(rot) > 0.03125 ? rot : 0;

        double speed_decrease = joystick.getRawButton(6) ? 0.25 : 1; // TODO Speed decrease button
        double speed_increase = (1 + joystick.getRawAxis(3) * 5);
        double speed_normal = 0.8125;
        double speed = speed_increase * speed_normal * speed_decrease;

        joystick.setRumble(RumbleType.kLeftRumble, speed - 1.5);

        xSpeed *= speed;
        ySpeed *= speed;
        rot *= 0.5 * speed_decrease;

        if (TranslationXEnabled || TranslationYEnabled || RotationZEnabled)
            drivetrain.SetSpeed(new Translation2d(xSpeed, ySpeed), rot);

        if (joystick.getPOV() == 0)
            new SetPistons(pneumatics, true).schedule();
        if (joystick.getPOV() == 180)
            new SetPistons(pneumatics, false).schedule();

        if (!joystick.getRawButton(1) && !joystick.getRawButton(5) && !joystick.getRawButton(10)){
            new StopSpin(sht);
        }

        if (joystick.getRawAxis(2) > 0.2)
            sht.shooterMotorController.ShooterMotor.set(VictorSPXControlMode.PercentOutput, joystick.getRawAxis(2));
        else if (joystick.getRawAxis(2) > 0.15)
            sht.shooterMotorController.ShooterMotor.set(VictorSPXControlMode.PercentOutput, 0);
        else if (sht.hoodMotorController.targetRevs == 0)
            sht.shooterMotorController.ShooterMotor.set(VictorSPXControlMode.PercentOutput, 0);

    }

    public void EnableAll() {
        TranslationXEnabled = true;
        TranslationYEnabled = true;
        RotationZEnabled = true;

        SmartDashboard.putBoolean("Joystick X Enabled", true);
        SmartDashboard.putBoolean("Joystick Y Enabled", true);
        SmartDashboard.putBoolean("Joystick R Enabled", true);
    }

    public void Disable() {
        TranslationXEnabled = false;
        TranslationYEnabled = false;
        RotationZEnabled = false;

        SmartDashboard.putBoolean("Joystick X Enabled", false);
        SmartDashboard.putBoolean("Joystick Y Enabled", false);
        SmartDashboard.putBoolean("Joystick R Enabled", false);
    }
}