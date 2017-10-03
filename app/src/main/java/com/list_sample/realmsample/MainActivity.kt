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
import me.mattak.moment.Moment

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
        val dateList: RealmResults<CurrentTimeModel> = mRealm.where(CurrentTimeModel::class.java).findAll()

        Log.d("Realm", "realm read is $dateList")

        // RecyclerViewのセットアップ
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        adapter = RecyclerViewAdapter(dateList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter


        // Todo 後でクリックイベントに変更
        writeRealm()


    }

    override fun onDestroy() {
        super.onDestroy()

        mRealm.close()
    }

    fun writeRealm() {
        mRealm.executeTransaction {
            val currentTime = mRealm.createObject(CurrentTimeModel::class.java)
            // LocalDateTime.now()がMIN_APIで使えないので、KotlinMomentを使用
            currentTime.currentTime = Moment().toString()
            mRealm.copyToRealm(currentTime)
        }

        adapter.notifyDataSetChanged()
    }
}
