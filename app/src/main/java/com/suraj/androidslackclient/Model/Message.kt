package com.suraj.androidslackclient.Model




data class MessageResponse(var ok:Any?,var error:String?,var messages:ArrayList<Message>)
data class Message(var type:String,var text:String,var user:String,var ts:Any)
