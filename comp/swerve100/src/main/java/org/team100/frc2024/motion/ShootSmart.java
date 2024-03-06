// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.team100.frc2024.motion;

import org.team100.frc2024.SensorInterface;
import org.team100.frc2024.motion.drivetrain.ShooterUtil;
import org.team100.frc2024.motion.intake.Intake;
import org.team100.frc2024.motion.shooter.Shooter;
import org.team100.lib.barcode.Sensor;
import org.team100.lib.motion.drivetrain.SwerveDriveSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class ShootSmart extends Command {
  /** Creates a new ShootSmart. */

  SensorInterface m_sensor;
  FeederSubsystem m_feeder;
  Shooter m_shooter;
  Intake m_intake;
  Timer m_timer;
  boolean atVelocity = false;
  boolean finished = false;
  SwerveDriveSubsystem m_drive;
  boolean m_isPreload;

  public ShootSmart(SensorInterface sensor, Shooter shooter, Intake intake, FeederSubsystem feeder, SwerveDriveSubsystem drive, boolean isPreload) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = intake;
    m_sensor = sensor;
    m_feeder = feeder;
    m_shooter = shooter;
    m_timer = new Timer();
    m_drive = drive;
    m_isPreload  = isPreload;

    addRequirements(m_intake, m_feeder, m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    double distance = m_drive.getPose().getTranslation().getDistance(ShooterUtil.getSpeakerTranslation());
    m_timer.reset();
    m_shooter.forward();
    m_shooter.setAngle(ShooterUtil.getAngle(distance));
    m_intake.intake();
    m_feeder.feed();
    m_timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double distance = m_drive.getPose().getTranslation().getDistance(ShooterUtil.getSpeakerTranslation());

    m_shooter.setAngle(ShooterUtil.getAngle(distance));

    if(!m_sensor.getFeederSensor()){

      m_intake.stop();
      m_feeder.stop();

      if(m_shooter.atVelocitySetpoint()){
        // if(Math.abs(m_shooter.getPivotPosition() - ShooterUtil.getAngle(m_drive.getPose().getX())) < 1 ){
            atVelocity = true;
            m_timer.start();
          // }
        } 
    }

    if(atVelocity){

      m_feeder.feed();
      m_intake.intake();

      if(m_timer.get() > 1){
        finished = true;
      }
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    atVelocity = false;
    finished = false;
    m_timer.stop();
    m_shooter.stop();
    m_intake.stop();
    m_feeder.stop();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
    // return false;
  }
}
