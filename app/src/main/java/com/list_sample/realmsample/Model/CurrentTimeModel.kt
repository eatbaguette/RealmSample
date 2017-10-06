package com.list_sample.realmsample.Model

import io.realm.RealmObject

/**
 * Created by monkey on 2017/10/06.
 */
open class CurrentTimeModel(
        open var currentTime: String = ""
): RealmObject() {}