package com.nick.wood.graphics_library;

import com.nick.wood.maths.objects.vector.Vec3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowInitialisationParameters {

	private final boolean resizable;
	private final boolean decorated;
	private final boolean lockCursor;
	private final boolean fullScreen;
	private final int windowWidth;
	private final int windowHeight;
	private final String title;
	private boolean debug;

	public WindowInitialisationParameters(boolean resizable,
	                                      boolean decorated,
	                                      boolean lockCursor,
	                                      boolean fullScreen,
	                                      int windowWidth,
	                                      int windowHeight,
	                                      String title,
	                                      boolean debug) {
		this.resizable = resizable;
		this.decorated = decorated;
		this.lockCursor = lockCursor;
		this.fullScreen = fullScreen;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.title = title;
		this.debug = debug;
	}

	public long accept(Window window) {
		// the window will be resizable
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);

		// remove window bar
		glfwWindowHint(GLFW_DECORATED, decorated ? GLFW_TRUE : GLFW_FALSE);

		// Create the window
		long windowHandler = glfwCreateWindow(windowWidth, windowHeight, title, NULL, NULL);

		// this locks cursor to center so can always look about
		org.lwjgl.glfw.GLFW.glfwSetInputMode(windowHandler, GLFW_CURSOR, lockCursor ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);

		window.setScreenDimensions(windowWidth, windowHeight);
		window.setTitle(title);

		return windowHandler;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isPicking() {
		return false;
	}
}
