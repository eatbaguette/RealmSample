package com.list_sample.realmsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {
    lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val realmConfig = RealmConfiguration.Builder(baseContext).build()
        val realm = Realm.getInstance(realmConfig)

        mRealm = realm

        mRealm.executeTransaction {
            val todoModel = mRealm.createObject(Todo::class.java)
            todoModel.status = 0
            todoModel.name = "takashi"
            mRealm.copyToRealm(todoModel)
        }

        val props: RealmResults<Todo> = mRealm.where(Todo::class.java).findAll()

        Log.d("Realm", "realm read is $props")
    }

    override fun onDestroy() {
        super.onDestroy()

        mRealm.close()
    }
}
