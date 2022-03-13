package frc.robot.ControlSystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.SwerveDrivetrain;
import frc.robot.Intake.ApplyIntakeSpeed;
import frc.robot.Intake.Intake;
import frc.robot.Intake.StopIntake;
import frc.robot.Pneumatics.Pneumatics;
import frc.robot.Pneumatics.SetPistons;
import frc.robot.Shooter.*;
import frc.robot.Shooter.Commands.ApplyConveyorSpeed;
import frc.robot.Shooter.Commands.ShootCommand;
import frc.robot.Shooter.Commands.StopConveyor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class JoystickDriver extends SubsystemBase {
    private Joystick joystick = new Joystick(0);
    private SwerveDrivetrain drivetrain;
    private Pneumatics pneumatics;

    public boolean TranslationXEnabled = false;
    public boolean TranslationYEnabled = false;
    public boolean RotationZEnabled = false;

    public JoystickDriver(SwerveDrivetrain drive, Shooter shooter, Pneumatics pn, Intake itk) {
        drivetrain = drive;
        drivetrain.joyDriver = this;
        pneumatics = pn;

        
        new JoystickButton(joystick, 1).whenPressed(new ShootCommand(shooter));
        new JoystickButton(joystick, 5).whenPressed(new ApplyIntakeSpeed(itk));
        new JoystickButton(joystick, 3).whenPressed(new StopIntake(itk));
        new JoystickButton(joystick, 6).whenPressed(new ApplyConveyorSpeed(shooter.conveyor));
        new JoystickButton(joystick, 4).whenPressed(new StopConveyor(shooter.conveyor));

        /*
        new JoystickButton(joystick, 8).whenPressed(new TeleopHome(drivetrain, driver));
        new JoystickButton(joystick, 7).whenPressed(new HomeWheels(drivetrain));
        new JoystickButton(joystick, 10).whenPressed(new ResetOdometryWithJoystick(drivetrain, joystick));
        new JoystickButton(joystick, 1).whenPressed(new ShootCommand(shooter));
        new JoystickButton(joystick, 9).whenPressed(new HoldSpin(shooter, 14.6));
        */

        new JoystickButton(joystick, 12).whenPressed(new SetPistons(pneumatics, true));
        new JoystickButton(joystick, 11).whenPressed(new SetPistons(pneumatics, false));
        

    }

    @Override
    public void periodic() {
        TranslationXEnabled = SmartDashboard.getBoolean("Joystick X Enabled", true);
        TranslationYEnabled = SmartDashboard.getBoolean("Joystick X Enabled", true);
        RotationZEnabled = SmartDashboard.getBoolean("Joystick R Enabled", true);

        double xSpeed = TranslationXEnabled ? -joystick.getRawAxis(1) / 1 : 0;
        double ySpeed = TranslationYEnabled ? -joystick.getRawAxis(0) / 1 : 0;
        double rot = RotationZEnabled ? joystick.getRawAxis(2) / 16 : 0;

        SmartDashboard.putString("Input Speed: ", ("x: " + xSpeed + ", y: " + ySpeed + ", rot: " + rot));

        xSpeed = Math.abs(xSpeed) > 0.2 ? xSpeed : 0;
        ySpeed = Math.abs(ySpeed) > 0.2 ? ySpeed : 0;
        rot = Math.abs(rot) > 0.03125 ? rot : 0;

        double speed_decrease = joystick.getRawButton(2) ? 0.25 : 1; // TODO Speed decrease button
        double speed_increase = (1 + (1 + joystick.getRawAxis(3)) * 2.5);
        double speed_normal = 0.5;
        double speed = speed_increase * speed_normal * speed_decrease;

        if (speed > 1)
            joystick.setRumble(RumbleType.kLeftRumble, speed - 1);

        xSpeed *= speed;
        ySpeed *= speed;
        rot *= 0.5;

        if (TranslationXEnabled || TranslationYEnabled || RotationZEnabled)
            drivetrain.SetSpeed(new Translation2d(xSpeed, ySpeed), rot);
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