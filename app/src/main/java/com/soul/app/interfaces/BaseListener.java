package com.soul.app.interfaces;

/**
 * Created by ashishkumar on 16/6/16.
 */
public interface BaseListener {

    /**
     * Method to return the activities layout
     */
    int setLayout();

    /**
     * Method to initialize ui parameters
     */
    void initUi();

    /**
     * Method to perform action on header arrow click
     * Default functionality is finish()
     */
    void onArrowPress();

    /**
     * Method to show a progress dialog on some background task
     */
    void showProgressDialog(boolean iShow);

    /**
     * Method to show a progress bar on some background task
     */
    void showProgressBar(boolean iShow);

    /**
     * Method to return the class name identifier
     */
    String getName();

    /**
     * Method to perform some action on done
     */
    public void onDone();
}
