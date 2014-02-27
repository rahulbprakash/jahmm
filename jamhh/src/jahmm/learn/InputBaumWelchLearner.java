package jahmm.learn;

import jahmm.InputHmm;
import jahmm.observables.InputObservationTuple;
import jahmm.observables.Observation;

/**
 *
 * @author kommusoft
 * @param <TObs>
 * @param <TInp>
 */
public interface InputBaumWelchLearner<TObs extends Observation, TInp extends Enum<TInp>> extends BaumWelchLearner<TObs, InputObservationTuple<TInp, TObs>, InputHmm<TObs, TInp>> {

}