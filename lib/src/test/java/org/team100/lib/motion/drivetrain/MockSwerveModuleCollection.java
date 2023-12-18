package org.team100.lib.motion.drivetrain;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class MockSwerveModuleCollection implements SwerveModuleCollectionInterface {
    SwerveModuleState[] m_targetModuleStates;
    boolean stopped = false;

    @Override
    public void setDesiredStates(SwerveModuleState[] targetModuleStates) {
        m_targetModuleStates = targetModuleStates;
    }

    @Override
    public SwerveModulePosition[] positions() {
        return new SwerveModulePosition[] {
                new SwerveModulePosition(),
                new SwerveModulePosition(),
                new SwerveModulePosition(),
                new SwerveModulePosition()
        };
    }

    @Override
    public SwerveModuleState[] states() {
        return m_targetModuleStates;
    }

    @Override
    public boolean[] atSetpoint() {
        return new boolean[] { true, true, true, true };
    }

    @Override
    public void periodic() {
        //
    }

    @Override
    public void stop() {
        stopped = true;
    }

    @Override
    public void close() {
        //
    }

    @Override
    public void setRawDesiredStates(SwerveModuleState[] targetModuleStates) {
        m_targetModuleStates = targetModuleStates;
    }
}