package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.nyu.pqs.stopwatch.api.Stopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects.
 * It maintains references to all created Stopwatch objects and provides a
 * convenient method for getting a list of those objects.
 * @author Dongxu Lin
 */
public class StopwatchFactory {
  private static Map<String, Stopwatch> stopwatchMap = new HashMap<>();
  
  /**
   * Creates and returns a new Stopwatch object
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or
   *     already taken.
   */
  public static Stopwatch getStopwatch(String id) {
    if (id == null || id.length() == 0) {
      throw new IllegalArgumentException("illegal input string");
    }
    synchronized (stopwatchMap) {
      if (stopwatchMap.containsKey(id)) {
        throw new IllegalArgumentException("the id has been used");
      }
      Stopwatch newStopwatch = new StopwatchImplement(id);
      stopwatchMap.put(id, newStopwatch);
      return newStopwatch;
    }
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of al creates Stopwatch objects.  Returns an empty
   * list if no Stopwatches have been created.
   */
  public static List<Stopwatch> getStopwatches() {
    synchronized (stopwatchMap) {
      List<Stopwatch> list = new ArrayList<>();
      for (String str: stopwatchMap.keySet()) {
        list.add(stopwatchMap.get(str));
      }
      return list;
    }
  }
}
