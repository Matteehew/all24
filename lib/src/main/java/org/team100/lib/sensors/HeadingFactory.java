package org.team100.lib.sensors;

import org.team100.lib.config.Identity;
import org.team100.lib.motion.drivetrain.SwerveModuleCollectionInterface;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

public class HeadingFactory {

    public static HeadingInterface get(
            Identity identity,
            SwerveDriveKinematics kinematics,
            SwerveModuleCollectionInterface collection) {
        switch (identity) {
            case BLANK:
                // for simulation
                return new SimulatedHeading(kinematics, collection);
            default:
                RedundantGyroInterface ahrsclass = new RedundantGyro.Factory(identity).get();
                return new Heading(ahrsclass);
        }
    }
}