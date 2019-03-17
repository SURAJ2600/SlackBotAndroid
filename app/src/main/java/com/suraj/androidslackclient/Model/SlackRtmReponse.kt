package com.suraj.androidslackclient.Model



data  class SlackRtmReponse(var ok :Boolean,var url :String?,var team:Team?,var self: self?,var error:String?)
data class Team(var id:String,var name:String,var domain:String)
data class self(var id:String,var name: String)