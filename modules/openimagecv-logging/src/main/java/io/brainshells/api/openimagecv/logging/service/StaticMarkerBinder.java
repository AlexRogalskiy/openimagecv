package io.brainshells.api.openimagecv.logging.service;

import org.slf4j.IMarkerFactory;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MarkerFactoryBinder;

/**
 * The binding of {@link MarkerFactory} class with an actual instance of {@link
 * IMarkerFactory} is performed using information returned by this class.
 */
public class StaticMarkerBinder implements MarkerFactoryBinder {

    /**
     * The unique instance of this class.
     */
    public static final StaticMarkerBinder SINGLETON = new StaticMarkerBinder();

    final IMarkerFactory markerFactory = new BasicMarkerFactory();

    private StaticMarkerBinder() {
    }

    /**
     * Return the singleton of this class.
     *
     * @return the StaticMarkerBinder singleton
     * @since 1.7.14
     */
    public static StaticMarkerBinder getSingleton() {
        return SINGLETON;
    }

    /**
     * Currently this method always returns an instance of {@link
     * BasicMarkerFactory}.
     */
    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }

    /**
     * Currently, this method returns the class name of {@link
     * BasicMarkerFactory}.
     */
    public String getMarkerFactoryClassStr() {
        return BasicMarkerFactory.class.getName();
    }
}
