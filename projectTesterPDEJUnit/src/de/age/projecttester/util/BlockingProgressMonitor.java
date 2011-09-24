package de.age.projecttester.util;

import org.eclipse.core.runtime.IProgressMonitor;

public class BlockingProgressMonitor implements IProgressMonitor {

	private boolean done = false;

	@Override
	public void beginTask(String name, int totalWork) {
	}

	@Override
	public void done() {
		done = true;
	}

	@Override
	public void internalWorked(double work) {
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
	}

	@Override
	public void setTaskName(String name) {
	}

	@Override
	public void subTask(String name) {
	}

	@Override
	public void worked(int work) {
	}

	public void blockUntilDone() {
		while (!done) {
			Thread.yield();
		}
		done = false;
	}

}