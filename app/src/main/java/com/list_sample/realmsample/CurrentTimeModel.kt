package com.list_sample.realmsample

import io.realm.RealmObject

/**
 * Created by monkey on 2017/10/03.
 */
open class CurrentTimeModel(
        open var currentTime: String = ""
): RealmObject() {}