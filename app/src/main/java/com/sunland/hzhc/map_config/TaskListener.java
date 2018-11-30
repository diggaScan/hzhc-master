package com.sunland.hzhc.map_config;

public interface TaskListener {

    void onPreExecute(GenericTask task);


    void onPostExecute(GenericTask task, TaskResult result);


    void onProgressUpdate(GenericTask task, Object param);


    void onCancelled(GenericTask task);

}
