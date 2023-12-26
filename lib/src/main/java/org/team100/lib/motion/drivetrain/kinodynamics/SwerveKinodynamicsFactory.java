package org.team100.lib.motion.drivetrain.kinodynamics;

import org.team100.lib.config.Identity;

/**
 * Each drivetrain should be tuned, and the values here should be the physical
 * maxima.
 * 
 * FYI according to their 2022 code, 254's max speed in 2022 was 5.05 m/s, which
 * is about the same as ours, but their max acceleration was 4.4 m/s^2, which is
 * crazy quick.
 * 
 * TODO: tune the limits below
 * 
 * Tune these limits to match the absolute maximum possible performance of the
 * drivetrain, not what seems "comfortable."
 * 
 * Do not use this class to configure driver preferences, use a command or
 * control instead.
 * 
 * In particular, the maximum spin rate is likely to seem quite high. Do not
 * lower it here.
 */
public class SwerveKinodynamicsFactory {
    /**
     * TODO: remove show mode, use a velocity multiplier in the command or
     * subsystem for that.
     * 
     * @param showMode is for younger drivers to drive the robot slowly.
     */
    public static SwerveKinodynamics get(Identity identity, boolean showMode) {
        switch (identity) {
            case COMP_BOT:
                if (showMode)
                    return new SwerveKinodynamics(4, 2, 3, 13, 0.491, 0.765, 0.3);
                return new SwerveKinodynamics(4, 2, 3, 13, 0.491, 0.765, 0.3);
            case SWERVE_TWO:
                return new SwerveKinodynamics(4, 2, 2, 13, 0.380, 0.445, 0.3);
            case SWERVE_ONE:
                return new SwerveKinodynamics(4, 2, 2, 13, 0.449, 0.464, 0.3);
            case BLANK:
                return new SwerveKinodynamics(4, 2, 3, 13, 0.5, 0.5, 0.3);
            default:
                System.out.println("WARNING: using default kinodynamics");
                return new SwerveKinodynamics(4, 2, 2, 13, 0.5, 0.5, 0.3);
        }
    }

    /**
     * This contains garbage values, not for anything real.
     * 
     * In particular, the steering rate is *very* slow, which might be useful if
     * you're wanting to allow for steering delay.
     */
    public static SwerveKinodynamics forTest() {
        return new SwerveKinodynamics(1, 1, 1, 1, 0.5, 0.5, 0.3);
    }

    //////////////////////////////////////////
    //
    // below are specific test cases. try to minimize their number

    public static SwerveKinodynamics highDecelAndCapsize() {
        return new SwerveKinodynamics(5, 2, 300, 5, 0.5, 0.5, 0.001); // 1mm vcg
    }

    public static SwerveKinodynamics decelCase() {
        return new SwerveKinodynamics(1, 1, 10, 5, 0.5, 0.5, 0.3);
    }

    public static SwerveKinodynamics highCapsize() {
        return new SwerveKinodynamics(5, 10, 10, 5, 0.5, 0.5, 0.1);
    }

    public static SwerveKinodynamics lowCapsize() {
        return new SwerveKinodynamics(5, 10, 10, 5, 0.5, 0.5, 2); // 2m vcg
    }

    public static SwerveKinodynamics limiting() {
        return new SwerveKinodynamics(5, 10, 10, 5, 0.5, 0.5, 0.3);
    }

    public static SwerveKinodynamics highAccelLowDecel() {
        return new SwerveKinodynamics(4, 1, 10, 5, 0.5, 0.5, 0.3);
    }

    private SwerveKinodynamicsFactory() {
        //
    }
}
