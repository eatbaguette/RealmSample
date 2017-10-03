package com.list_sample.realmsample

import io.realm.RealmObject

/**
 * Created by monkey on 2017/10/03.
 */
open class CurrentDateTimeModel(
        open var currentTime: String = ""
): RealmObject() {}