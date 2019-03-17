package com.suraj.androidslackclient.Model
///*{
//  "ok": true,
//  "access_token": "xoxp-362211397057-579281386085-578968703906-66eb15e481afe8bebd5272e9cdd12c74",
//  "scope": "identify,bot,incoming-webhook,channels:history,channels:read,users.profile:read,channels:write,chat:write:bot",
//  "user_id": "UH189BC2H",
//  "team_name": "Foo Bar",
//  "team_id": "TAN67BP1P"
//}*/
data class AccessToken(var ok:Boolean,var access_token:String?,var user_id:String?,var error:String?)
