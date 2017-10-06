package com.list_sample.realmsample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.list_sample.realmsample.Model.CurrentTimeModel
import com.list_sample.realmsample.R
import com.list_sample.realmsample.adapter.RecyclerViewAdapter
import com.list_sample.realmsample.ui.view.DividerItemDecoration
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import me.mattak.moment.Moment


class MainActivity : AppCompatActivity() {
    lateinit var mRealm: Realm
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_recycler_view)

        // Realmのセットアップ
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.deleteRealm(realmConfig)
        mRealm = Realm.getInstance(realmConfig)
        Log.d("hoge", mRealm.configuration.path)

        // Realmを読み込み
        val dateList: RealmResults<CurrentTimeModel> = mRealm.where(CurrentTimeModel::class.java).findAll()

        // RecyclerViewのセットアップ
        mRecyclerView = findViewById(R.id.recycler_view) as RecyclerView
        mAdapter = RecyclerViewAdapter(dateList)
        val layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addItemDecoration(DividerItemDecoration(this))

        // ボタンのセットアップ
        val fabAddCurrentDateTime = findViewById(R.id.fab_add_current_date_time)
        fabAddCurrentDateTime.setOnClickListener { addCurrentDateTime() }

        val fabDeleteAllRecords = findViewById(R.id.fab_delete_all_records)
        fabDeleteAllRecords.setOnClickListener { deleteAllRecords() }

    }

    override fun onDestroy() {
        super.onDestroy()

        mRealm.close()
    }

    fun addCurrentDateTime() {
        mRealm.executeTransaction {
            val currentDateTime = mRealm.createObject(CurrentTimeModel::class.java)
            // LocalDateTime.now()がMIN_APIで使えないので、KotlinMomentを使用
            currentDateTime.currentTime = Moment().toString()
            mRealm.copyToRealm(currentDateTime)
        }

        mAdapter.notifyDataSetChanged()
    }

    fun deleteAllRecords() {
        mRealm.executeTransaction {
            mRealm.where(CurrentTimeModel::class.java)
                    .findAll()
                    .deleteAllFromRealm()
        }

        mAdapter.notifyDataSetChanged()
    }
}
