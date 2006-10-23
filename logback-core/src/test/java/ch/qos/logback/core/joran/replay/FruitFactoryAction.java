/**
 * Logback: the generic, reliable, fast and flexible logging framework for Java.
 * 
 * Copyright (C) 2000-2006, QOS.ch
 * 
 * This library is free software, you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation.
 */

package ch.qos.logback.core.joran.replay;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.event.InPlayListener;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;

public class FruitFactoryAction extends Action implements InPlayListener {

  List<SaxEvent> seList = new ArrayList<SaxEvent>();

  @Override
  public void begin(InterpretationContext ec, String name, Attributes attributes)
      throws ActionException {
    ec.addInPlayListener(this);
  }

  @Override
  public void end(InterpretationContext ec, String name) throws ActionException {
    ec.removeInPlayListener(this);
    
    Object o = ec.peekObject();
    if(o instanceof FruitShell) {
      FruitShell fs = (FruitShell) o;
      FruitFactory fruitFactory = new FruitFactory();
      fruitFactory.setEventList(new ArrayList<SaxEvent>(seList));
      fs.setFruitFactory(fruitFactory);
    }
  }

  public void inPlay(SaxEvent event) {
    seList.add(event);
  }

  public List<SaxEvent> getSeList() {
    return seList;
  }

}
