package com.list_sample.realmsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class MainActivity : AppCompatActivity() {
    lateinit var mRealm: Realm
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recycler_view)

        // Realmのセットアップ

        val realmConfig = RealmConfiguration.Builder(baseContext).build()
        val realm = Realm.getInstance(realmConfig)

        mRealm = realm

        // Realmを読み込み
        val props: RealmResults<Todo> = mRealm.where(Todo::class.java).findAll()
        val propsName = props.get(3).name

        Log.d("Realm", "realm read is $propsName")

        // RecyclerViewのセットアップ
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        adapter = RecyclerViewAdapter(props)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter


    }

    override fun onDestroy() {
        super.onDestroy()

        mRealm.close()
    }

    fun writeRealm() {
        mRealm.executeTransaction {
            val todoModel = mRealm.createObject(Todo::class.java)
            todoModel.status = 0
            todoModel.name = "takashi"
            mRealm.copyToRealm(todoModel)
        }
    }
}
