package com.sunland.hzhc.map_config;



public abstract class TaskAdapter implements TaskListener
{

	public  String getName(){
		return "";
	}

	@Override
	public void onPreExecute(GenericTask task)
	{
	}

	@Override
	public void onPostExecute(GenericTask task, TaskResult result)
	{

	}

	@Override
	public void onProgressUpdate(GenericTask task, Object param)
	{

	}

	@Override
	public void onCancelled(GenericTask task)
	{
	}

}
