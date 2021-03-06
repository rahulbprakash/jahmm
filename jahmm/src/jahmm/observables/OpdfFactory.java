/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.observables;

import jutils.FactoryMethod;

/**
 * Classes implementing this interface are able to generate observation
 * probability distribution functions. The classes implementing
 * <code>OpdfFactory</code> are supposed to generate only a certain kind of
 * distribution (e.g. Gaussian).
 *
 * @param <D>
 */
public interface OpdfFactory<D extends Opdf<?>> extends FactoryMethod<D> {

    /**
     * Generates a new observation probability distribution function.
     *
     * @return The new opdf.
     */
    @Override
    public D generate();
}
