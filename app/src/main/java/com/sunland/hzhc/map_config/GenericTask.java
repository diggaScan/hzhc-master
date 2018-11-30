package com.sunland.hzhc.map_config;

import java.util.Observable;
import java.util.Observer;

import android.os.AsyncTask;



public abstract class GenericTask extends
        AsyncTask<TaskParams, Object, TaskResult> implements Observer
{
	private boolean hasCancelAble = true;
	private TaskListener mListener = null;

	protected abstract TaskResult _doInBackground(TaskParams... params);

	public void setTaskListener(TaskListener listener)
	{
		mListener = listener;
	}

	public void doPublishProgress(Object... values)
	{
		super.publishProgress(values);
	}

	@Override
	protected TaskResult doInBackground(TaskParams... params)
	{
		TaskResult result = _doInBackground(params);
		return result;
	}

	@Override
	protected void onCancelled()
	{
		super.onCancelled();

		if (null != mListener)
		{
			mListener.onCancelled(this);
		}
	}

	@Override
	protected void onPostExecute(TaskResult result)
	{
		super.onPostExecute(result);

		if (null != mListener)
		{
			mListener.onPostExecute(this, result);
		}
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();

		if (null != mListener)
		{
			mListener.onPreExecute(this);
		}
	}

	@Override
	protected void onProgressUpdate(Object... values)
	{
		super.onProgressUpdate(values);
		if (null != mListener)
		{
			if (null != values && values.length > 0)
			{
				mListener.onProgressUpdate(this, values[0]);
			}
		}
	}

	@Override
	public void update(Observable observable, Object data)
	{
		// TODO Auto-generated method stub
		if (TaskManager.CANCEL_ALL == (Integer) data && hasCancelAble == true)
		{
			if (getStatus() == Status.RUNNING)
			{
				cancel(true);
			}
		}
	}

	public void setCancelAble(boolean hasCancelAble)
	{
		this.hasCancelAble = hasCancelAble;
	}
}
