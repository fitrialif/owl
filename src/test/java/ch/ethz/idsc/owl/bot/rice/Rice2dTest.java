// code by jph
package ch.ethz.idsc.owl.bot.rice;

import java.util.List;

import ch.ethz.idsc.owl.bot.util.RegionRenders;
import ch.ethz.idsc.owl.data.Stopwatch;
import ch.ethz.idsc.owl.glc.adapter.Expand;
import ch.ethz.idsc.owl.glc.adapter.GlcExpand;
import ch.ethz.idsc.owl.glc.adapter.GlcNodes;
import ch.ethz.idsc.owl.glc.adapter.GlcTrajectories;
import ch.ethz.idsc.owl.glc.core.GlcNode;
import ch.ethz.idsc.owl.glc.core.TrajectoryPlanner;
import ch.ethz.idsc.owl.gui.ren.TrajectoryRender;
import ch.ethz.idsc.owl.gui.win.OwlyFrame;
import ch.ethz.idsc.owl.gui.win.OwlyGui;
import ch.ethz.idsc.owl.math.state.TrajectorySample;
import junit.framework.TestCase;

public class Rice2dTest extends TestCase {
  public void testExpand() throws InterruptedException {
    TrajectoryPlanner trajectoryPlanner = Rice2dDemo.createInstance();
    Stopwatch stopwatch = Stopwatch.started();
    int iters = Expand.maxSteps(trajectoryPlanner, 1000); // 153 0.368319228
    assertTrue(stopwatch.display_seconds() < 1.5);
    assertTrue(iters < 500);
    OwlyFrame owlyFrame = OwlyGui.glc(trajectoryPlanner);
    GlcNode glcNode = trajectoryPlanner.getBest().get();
    GlcNodes.getPathFromRootTo(glcNode);
    List<TrajectorySample> samples = GlcTrajectories.detailedTrajectoryTo(Rice2dDemo.STATE_INTEGRATOR, glcNode);
    owlyFrame.addBackground(new TrajectoryRender(samples));
    owlyFrame.addBackground(RegionRenders.create(Rice2dDemo.ELLIPSOID_REGION));
    Thread.sleep(120);
    owlyFrame.jFrame.setVisible(false);
  }

  public void testGlcExpand() throws InterruptedException {
    TrajectoryPlanner trajectoryPlanner = Rice2dDemo.createInstance();
    Stopwatch stopwatch = Stopwatch.started();
    int iters = GlcExpand.maxSteps(trajectoryPlanner, 1000, () -> true); // 220 0.283809941
    assertTrue(stopwatch.display_seconds() < 1.5);
    assertTrue(iters < 500);
    OwlyFrame owlyFrame = OwlyGui.glc(trajectoryPlanner);
    GlcNode glcNode = trajectoryPlanner.getBest().get();
    GlcNodes.getPathFromRootTo(glcNode);
    List<TrajectorySample> samples = GlcTrajectories.detailedTrajectoryTo(Rice2dDemo.STATE_INTEGRATOR, glcNode);
    owlyFrame.addBackground(new TrajectoryRender(samples));
    owlyFrame.addBackground(RegionRenders.create(Rice2dDemo.ELLIPSOID_REGION));
    Thread.sleep(120);
    owlyFrame.jFrame.setVisible(false);
  }
}
