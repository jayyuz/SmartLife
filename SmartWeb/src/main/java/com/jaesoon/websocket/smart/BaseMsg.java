/**
 * @author Jaesoon
 * @version ����ʱ�䣺2016��7��16�� ����8:33:07 
 */
package com.jaesoon.websocket.smart;

import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * @author Jaesoon
 *
 */
@SuppressWarnings("serial")
public class BaseMsg implements Serializable {
	@Override
	public String toString() {
		return new GsonBuilder().create().toJson(this);
	}
}
