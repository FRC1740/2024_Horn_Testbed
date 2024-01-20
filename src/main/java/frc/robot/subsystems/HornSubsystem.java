// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.constants.HornConstants;

// import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class HornSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final CANSparkMax m_LeftMotor = new CANSparkMax(HornConstants.kLeftMotorPort, CANSparkLowLevel.MotorType.kBrushless);    
  private final CANSparkMax m_RightMotor = new CANSparkMax(HornConstants.kRightMotorPort, CANSparkLowLevel.MotorType.kBrushless);     
  private final RelativeEncoder m_LeftMotorEncoder;
  private final RelativeEncoder m_RightMotorEncoder;
  private double m_LeftMotorSetSpeed = HornConstants.kLeftMotorSpeakerScoreSpeed;
  private double m_RightMotorSetSpeed = HornConstants.kRightMotorSpeakerScoreSpeed;
  private double m_speed;

  public HornSubsystem() {
    m_LeftMotor.setInverted(true);
    m_RightMotor.setInverted(false);
    m_LeftMotorEncoder = m_LeftMotor.getEncoder();
    m_RightMotorEncoder = m_RightMotor.getEncoder();
    m_speed = HornConstants.kLeftMotorSpeakerScoreSpeed;
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command shootNoteCommand(double speed) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    m_speed = speed;
    return runOnce(
        () -> {
          /* one-time action goes here */
          // Shoot(HornConstants.kLeftMotorSpeakerScoreSpeed * speed, HornConstants.kRightMotorSpeakerScoreSpeed * speed);
          Shoot(m_speed, m_speed);
        });
  }

  public Command stopHornCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
          stopHorn();
        });
  }
  
  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    // This could be a limit switch that is engaged when a note is detected in the shooter
    return true;
  }

  public Command increaseSpeedCommand() {
    m_speed = m_speed * 1.1;
    System.out.println(m_speed);
    return runOnce(
        () -> {
          /* one-time action goes here */
          ShootNote(m_speed);
        });
  }

  public Command decreaseSpeedCommand() {
    m_speed = m_speed * .9;
    System.out.println(m_speed);
    return runOnce(
        () -> {
          /* one-time action goes here */
          ShootNote(m_speed);
        });
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void ShootNote(double speed) {
    m_speed = speed;
    setHornSpeed(m_speed, m_speed);
  }

  public void Shoot(double lspeed, double rspeed) {
    m_speed = lspeed;
    setHornSpeed(lspeed, rspeed);
  }

  public void stopHorn() {
    m_LeftMotor.set(0.0);
    m_RightMotor.set(0.0);
  }

  public double getLeftvelocity() {
    return m_LeftMotorEncoder.getVelocity(); 
  }

  public double getRightvelocity() {
    return m_RightMotorEncoder.getVelocity(); 
  }

  private void setHornSpeed(double lspeed, double rspeed) {
    m_LeftMotorSetSpeed = lspeed;
    m_RightMotorSetSpeed = rspeed;
    m_speed = lspeed;
    m_LeftMotor.set(m_LeftMotorSetSpeed);
    m_RightMotor.set(m_RightMotorSetSpeed);
  }

}
