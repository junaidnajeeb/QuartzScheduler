package com.quartz.scheduler.exception;

import org.quartz.ObjectAlreadyExistsException;

public class DuplicateJobKeyException extends Exception {

  private static final long serialVersionUID = 4782254737824128797L;

  public DuplicateJobKeyException(String group, String name, ObjectAlreadyExistsException e) {
    super("already exists a job in group '" + group + "' with name '" + name + "'.", e);
  }
}
