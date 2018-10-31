package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * StopwatchImplement is thread-safe actual implementation of the Stopwatch interface. 
 * A Stopwatch can start, stop, reset, lap. To construct a stopwatch, an id is required.
 * @author Dongxu Lin
 */
public class StopwatchImplement implements Stopwatch{
 
  private final String id;
  
//  the field to store the previous time for calculating time interval
  private long previousLapTime;
  private boolean isRunning;
  
//  the list to store time interval
  private List<Long> lapList;
  private Object lock = new Object();
  
  
  /**create a new Stopwatch object
   * @param id
   * @throws IllegalStateException thrown when the id is null or empty
   */
  public StopwatchImplement(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("id cannot be null or empty");
    }
    this.id = id;
    previousLapTime = -1;
    isRunning = false;
    lapList = new ArrayList<>();
  }
  
  /**
   * Returns the Id of this stopwatch
   * @return the Id of this stopwatch.  Will never be empty or null.
   */
  @Override
  public String getId() {
    return id;
  }
  
  /**
   * Starts the stopwatch.
   * @throws IllegalStateException thrown when the stopwatch is already running
   */
  @Override
  public void start() {
    synchronized (lock) {
      if (isRunning) {
        throw new IllegalStateException("stopwatch is running already");
      }
      previousLapTime = System.currentTimeMillis();
      isRunning = true;
    }
  }
  
  /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap.
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized(lock) {
      if (!isRunning) {
        throw new IllegalStateException("the stopwatch isn't running");
      }
      long curTime = System.currentTimeMillis();
      lapList.add(curTime - previousLapTime);
      previousLapTime = curTime;
    }
  }
  
  /**
   * Stops the stopwatch (and records one final lap).
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void stop() {
    synchronized (lock) {
      if (!isRunning) {
        throw new IllegalStateException("the stopwatch isn't running");
      }
      lap();
      isRunning = false;
    }
  }
  
  /**
   * Resets the stopwatch.  If the stopwatch is running, this method stops the
   * watch and resets it.  This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (lock) {
      previousLapTime = -1;
      isRunning = false;
      lapList.clear();
    }
  }
  
  /**
   * Returns a list of lap times (in milliseconds).  This method can be called at
   * any time and will not throw an exception.
   * @return a list of recorded lap times or an empty list.
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      return new ArrayList<>(lapList);
    }
  }
  
  @Override
  public int hashCode() {
    return this.id.hashCode();
  }
  
  /* 
   * if 2 stopwatch have the same id, they are equal.
   */
  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Stopwatch)) {
      return false;
    }
    Stopwatch theOtherWatch = (Stopwatch)object;
    if (id.equals(theOtherWatch.getId())) {
      return true;
    }
    return false;
  }
  
  @Override
  public String toString() {
    synchronized (lock) {
      return "stop watch: " + this.id + ", previous lap time: " + previousLapTime + ", is Running: " + isRunning;
    }
  }
}
