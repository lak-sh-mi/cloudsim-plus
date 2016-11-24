/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class implements the future event queue used by {@link CloudSim}.
 * The event queue uses a {@link TreeSet} in order to store the events.
 *
 * @author Marcos Dias de Assuncao
 * @since CloudSim Toolkit 1.0
 * @see java.util.TreeSet
 *
 * @todo It would be used a common interface for queues
 * such as this one and {@link DeferredQueue}
 */
public class FutureQueue {

	/** The sorted set of events. */
	private final SortedSet<CloudSimEvent> sortedSet = new TreeSet<>();

	/** A incremental number used for {@link CloudSimEvent#serial} event attribute.
         */
	private long serial = 0;

	/**
	 * Adds a new event to the queue. Adding a new event to the queue preserves the temporal order of
	 * the events in the queue.
	 *
	 * @param newEvent The event to be put in the queue.
	 */
	public void addEvent(CloudSimEvent newEvent) {
		newEvent.setSerial(serial++);
		sortedSet.add(newEvent);
	}

	/**
	 * Adds a new event to the head of the queue.
	 *
	 * @param newEvent The event to be put in the queue.
	 */
	public void addEventFirst(CloudSimEvent newEvent) {
		newEvent.setSerial(0);
		sortedSet.add(newEvent);
	}

	/**
	 * Returns an iterator to the queue.
	 *
	 * @return the iterator
	 */
	public Iterator<CloudSimEvent> iterator() {
		return sortedSet.iterator();
	}

	/**
	 * Returns the size of this event queue.
	 *
	 * @return the size
	 */
	public int size() {
		return sortedSet.size();
	}

        /**
         * Checks if the queue is empty.
         * @return true if the queue is empty, false otherwise
         */
        public boolean isEmpty(){
            return sortedSet.isEmpty();
        }

	/**
	 * Removes the event from the queue.
	 *
	 * @param event the event
	 * @return true, if successful
	 */
	public boolean remove(CloudSimEvent event) {
            return sortedSet.remove(event);
	}

        /**
         * Gets the first element of the queue.
         * @return the first element
         * @throws NoSuchElementException when the queue is empty
         */
        public CloudSimEvent first() throws NoSuchElementException {
            return sortedSet.first();
        }

	/**
	 * Removes all the events from the queue.
	 *
	 * @param events the events
	 * @return true, if successful
	 */
	public boolean removeAll(Collection<CloudSimEvent> events) {
		return sortedSet.removeAll(events);
	}

	/**
	 * Clears the queue.
	 */
	public void clear() {
		sortedSet.clear();
	}

}
