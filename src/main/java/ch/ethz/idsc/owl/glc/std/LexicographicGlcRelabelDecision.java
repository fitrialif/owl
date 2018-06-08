// code by ynager
package ch.ethz.idsc.owl.glc.std;

import ch.ethz.idsc.owl.bot.util.RelaxedLexicographic;
import ch.ethz.idsc.owl.glc.core.GlcNode;
import ch.ethz.idsc.owl.math.VectorScalar;
import ch.ethz.idsc.tensor.Tensor;

public class LexicographicGlcRelabelDecision implements RelabelDecisionInterface<GlcNode> {
  private final RelaxedLexicographic relaxedLexicographic;

  public LexicographicGlcRelabelDecision(Tensor slack) {
    relaxedLexicographic = new RelaxedLexicographic(slack);
  }

  @Override
  public boolean doRelabel(GlcNode newNode, GlcNode formerNode) {
    Tensor newMerit = ((VectorScalar) newNode.merit()).vector();
    Tensor formerMerit = ((VectorScalar) formerNode.merit()).vector();
    return relaxedLexicographic.compare(newMerit, formerMerit) == -1;
  }
}
