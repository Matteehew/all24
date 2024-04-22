package org.team100.sim;

import org.dyn4j.geometry.Vector2;

/**
 * Foes try to pick from the source and score in the amp corner.
 * 
 * Foes should have human-readable names.
 * 
 * TODO: add defensive behavior
 * TODO: add two kinds of scoring
 * TODO: coordinate "lanes" among alliance-mates
 * TODO: spin away if close to an opponent
 */
public class Foe extends RobotBody {
    private static final int kForce = 200;
    private static final int kTolerance = 2;
    private static final Vector2 kSource = new Vector2(0, 0);
    /** This is the robot center when facing the amp */
    private static final Vector2 kAmpSpot = new Vector2(14.698, 8.204)
            .sum(0, -kRobotSize / 2);
    /** Shoot from about 3 meters away */
    private static final Vector2 kShootingSpot = new Vector2(13.5, 5.5);
    private static final double kShootingAngle = 0;

    public Foe(String id, SimWorld world, Goal initialGoal) {
        super(id, world, initialGoal);
    }

    @Override
    boolean friend(RobotBody other) {
        // only foes are friends
        return other instanceof Foe;
    }

    @Override
    Vector2 ampPosition() {
        return kAmpSpot;
    }

    @Override
    Vector2 shootingPosition() {
        return kShootingSpot;
    }

    @Override
    double shootingAngle() {
        return kShootingAngle;
    }

    @Override
    public void act() {
        Vector2 position = getWorldCenter();
        switch (m_goal) {
            case PICK:
                Vector2 toPick = position.to(kSource);
                if (toPick.getMagnitude() < kTolerance) {
                    // successful pick, now go score
                    nextGoal();
                } else {
                    // keep trying
                    applyForce(toPick.setMagnitude(kForce));
                }
                break;
            case SCORE_AMP:
                driveToAmp();
                break;
            case SCORE_SPEAKER:
                driveToSpeaker();
                break;
            default:
                // do nothing
                break;
        }

        // look for nearby notes, brute force
        for (Body100 body : m_world.getBodies()) {
            if (body instanceof Note) {
                double distance = position.distance(body.getWorldCenter());
                if (distance > 0.3)
                    continue;
                // System.out.printf("%s %5.3f\n",
                // body.getClass().getSimpleName(), distance);
                // TODO: pick up?
            }
        }

        // TODO: defense

        avoidRobots();

    }
}
