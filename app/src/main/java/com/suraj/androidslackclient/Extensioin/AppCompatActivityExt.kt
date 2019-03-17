package com.suraj.androidslackclient.Extensioin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.suraj.androidslackclient.Utilities.ViewModelFactory

/**
 * Created by bpluim on 3/2/18.
 */

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3


/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
  ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)


