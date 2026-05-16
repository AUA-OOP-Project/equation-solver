package com.equationsolver.display;

/**
 * Implemented by any object that can render itself as a human-readable string.
 *
 * <p>Used by {@link com.equationsolver.model.Solution} to produce formatted output
 * for display in the CLI and GUI.
 */
public interface Displayable {

    /**
     * Returns a human-readable string representation of this object.
     *
     * @return formatted display string, never {@code null}
     */
    String display();
}