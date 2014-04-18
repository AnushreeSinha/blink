package com.blink.service;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;


import com.blink.util.ClassFinder;

public class BlinkServiceTest {

	@Test
	public void test() {
		try {
			List<Class<?>> classes = new ClassFinder().getClasses("com.blink.designer.model");
			for (Class<?> clazz : classes) {
				System.out.println(clazz);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
