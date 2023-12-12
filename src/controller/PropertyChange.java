package controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class PropertyChange implements PropertyChangeEnable {
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
    private PropertyChangeSupport myPcs = new PropertyChangeSupport(this);
    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);
    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);
    }
}
