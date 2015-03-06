/*
 * Copyright (C) 2012-2015 Christian Sterzl <christian.sterzl@gmail.com>
 *
 * This file is part of poker.
 *
 * V-Collaborate Poker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * V-Collaborate Poker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with V-Collaborate Poker.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.foldem.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Utility class with methods appropriate for resource reading.
 *
 * @author Robert Strack
 */
public final class ResourceReader {

  /**
   * The class is a utility class (non-instantiable).
   */
  private ResourceReader() {
  }

  /**
   * Reads resources.
   * 
   * @param <T>
   *          return type
   * @param name
   *          resource URI
   * @return loaded resource
   * @throws ResourceLoadFailureException
   *           if resources cannot be loaded
   */
  @SuppressWarnings("unchecked")
  public static <T extends Object> T loadResource(String name) throws ResourceLoadFailureException {
    try {
      InputStream resourceStream = ResourceReader.class.getResourceAsStream(name);
      if (resourceStream != null) {
        ObjectInputStream objectStream = new ObjectInputStream(resourceStream);
        return (T) objectStream.readObject();
      } else {
        throw new ResourceLoadFailureException("resource " + name + " does not exist");
      }
    } catch (IOException e) {
      throw new ResourceLoadFailureException("cannot read resource " + name, e);
    } catch (ClassNotFoundException e) {
      throw new ResourceLoadFailureException("unknown class for resource " + name, e);
    } catch (Exception e) {
      throw new ResourceLoadFailureException("cannot load resource " + name, e);
    }
  }

}
