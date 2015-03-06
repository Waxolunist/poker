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

package com.foldem;

/**
 * The root of all exceptions used by the Foldem library.
 *
 * @author Robert Strack
 */
public abstract class FoldemException extends RuntimeException {

  private static final long serialVersionUID = -4234742188011350882L;

  public FoldemException() {
    super();
  }

  public FoldemException(String message, Throwable cause) {
    super(message, cause);
  }

  public FoldemException(String message) {
    super(message);
  }

  public FoldemException(Throwable cause) {
    super(cause);
  }

}
