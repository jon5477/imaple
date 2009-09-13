/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package imaple.util;

/**
 *
 * @author Sactual
 */

public interface TimerManagerMBean {
	public boolean isTerminated();
	public boolean isShutdown();
	public long getCompletedTaskCount();
	public long getActiveCount();
	public long getTaskCount();
	public int getQueuedTasks();
}
