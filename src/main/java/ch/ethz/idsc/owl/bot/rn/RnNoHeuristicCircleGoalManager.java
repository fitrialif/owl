// code by jph and jl
package ch.ethz.idsc.owl.bot.rn;

import java.util.List;

import ch.ethz.idsc.owl.data.Lists;
import ch.ethz.idsc.owl.glc.adapter.CatchyTrajectoryRegionQuery;
import ch.ethz.idsc.owl.glc.core.GlcNode;
import ch.ethz.idsc.owl.glc.core.GoalInterface;
import ch.ethz.idsc.owl.math.flow.Flow;
import ch.ethz.idsc.owl.math.region.SphericalRegion;
import ch.ethz.idsc.owl.math.state.StateTime;
import ch.ethz.idsc.owl.math.state.TimeInvariantRegion;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Sign;

/** objective is minimum path length
 * path length is measured in Euclidean distance */
public class RnNoHeuristicCircleGoalManager extends CatchyTrajectoryRegionQuery implements GoalInterface {
  /** constructor creates a spherical region in R^n with given center and radius.
   * distance measure is Euclidean distance.
   * 
   * @param center vector with length == n
   * @param radius positive */
  public RnNoHeuristicCircleGoalManager(Tensor center, Scalar radius) {
    super(new TimeInvariantRegion(new SphericalRegion(center, Sign.requirePositive(radius))));
  }

  @Override
  public Scalar costIncrement(GlcNode glcNode, List<StateTime> trajectory, Flow flow) {
    StateTime from = glcNode.stateTime();
    return Norm._2.between(from.state(), Lists.getLast(trajectory).state());
  }

  @Override
  public Scalar minCostToGoal(Tensor x) {
    return RealScalar.ZERO;
  }
}