/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.io;

import jahmm.observables.Observation;
import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * Reads an observation up to (and including) the semi-colon.
 * <p>
 * The syntax of each observation must follow the rules given in
 * {@link ObservationSequencesReader ObservationSequencesReader} (<i>e.g.</i>
 * the backslash character is only used to escape a new line and can't appear in
 * an observation).
 *
 * @param <O>
 */
public abstract class ObservationReader<O extends Observation> {

    /**
     * Reads an {@link be.ac.ulg.montefiore.run.jahmm.Observation Observation}
     * (followed by a semi-colon) out of a {@link java.io.StreamTokenizer
     * StreamTokenizer}.
     * <p>
     * The stream tokenizer syntax table must be set according to of <code>ObservationSequencesReader.initSyntaxTable(StreamTokenizer)
     * </code> before the call to this method and reset to this state when it
     * returns.
     *
     * @param st A stream tokenizer.
     * @return An ObservationInteger.
     * @throws java.io.IOException
     * @throws be.ac.ulg.montefiore.run.jahmm.io.FileFormatException
     */
    public abstract O read(StreamTokenizer st)
            throws IOException, FileFormatException;
}
