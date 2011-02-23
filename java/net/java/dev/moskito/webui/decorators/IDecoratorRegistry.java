/*
 * $Id: IDecoratorRegistry.java 3471 2009-11-01 13:54:12Z another $
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.java.dev.moskito.webui.decorators;

import java.util.List;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.producers.IStats;

/**
 * Internally used registry for decorator management.
 * @author lrosenberg
 *
 */
public interface IDecoratorRegistry {
	/**
	 * Returns the decorator for the given stats object.
	 * @param stats
	 * @return
	 */
	IDecorator getDecorator(IStats stats);
	/**
	 * Returns all known decorators.
	 * @return
	 */
	List<IDecorator> getDecorators();

	void addDecorator(Class <? extends AbstractStats> clazz, IDecorator decorator);
}
