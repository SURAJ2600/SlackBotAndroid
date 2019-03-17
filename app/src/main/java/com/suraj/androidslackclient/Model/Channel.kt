package com.suraj.androidslackclient.Model

import allbegray.slack.type.Topic

///* id=CAN1QRPUG,
//    name=general,
//    is_channel=true,
//    created=1526133103,
//    creator=UANS9BSNR,
//    is_archived=false,
//    is_general=true,
//    is_member=true,
//    members=[
//      UANS9BSNR,
//      UB4RJR6NN,
//      UH0UGLR6W,
//      UH189BC2H
//    ],
//    topic=Topic[
//      value=Company-wideannouncementsandwork-basedmatters,
//      creator=UANS9BSNR,
//      last_set=1526133103
//    ],*/
data  class Channel(var id:String,var name:String,var is_channel:Any?,var members:List<String>,var topic: Any?)
data class Topic(var value:String,var creator:String)