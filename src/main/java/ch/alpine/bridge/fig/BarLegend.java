package ch.alpine.bridge.fig;

import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.sca.Clip;

public record BarLegend(Clip clip, ScalarTensorFunction colorDataGradient) {
}
