package controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Abstract class that handles property change listeners.
 * @author Jonathan Abrams, Madison Pope, Martha Emerson.
 */
public abstract class PropertyChange implements PropertyChangeEnable {
    private final PropertyChangeSupport myPcs = new PropertyChangeSupport(this);
    /**
     * Fire event to observers.
     * @param theEvent the event from PropertyChangeEnableFight
     */
    protected void fireEvent(final String theEvent) {
        myPcs.firePropertyChange(theEvent, null, true);
    }

    /**
     * Fire event to observers with initial and ending value.
     * @param theEvent the event from PropertyChangeEnableFight
     * @param theStart the initial value
     * @param theEnd the changed value
     */
    protected void fireEvent(final String theEvent, final int theStart, final int theEnd) {
        myPcs.firePropertyChange(theEvent, theStart, theEnd);
    }

    /**
     * Add a property change listener when given a listener.
     * @param theListener The PropertyChangeListener to be added.
     */
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    /**
     * Add a property change listener when given a listener and a property name.
     * @param thePropertyName The name of the property to listen on.
     * @param theListener The PropertyChangeListener to be added.
     */
    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    /**
     * Remove a property change listener when given a listener.
     * @param theListener The PropertyChangeListener to be removed.
     */
    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    /**
     * Remove a property change listener when given a listener and a property name.
     * @param thePropertyName The name of the property that was listened on.
     * @param theListener The PropertyChangeListener to be removed.
     */
    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }
}
