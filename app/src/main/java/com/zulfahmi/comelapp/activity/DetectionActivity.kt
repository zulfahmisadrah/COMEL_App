package com.zulfahmi.comelapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.zulfahmi.comelapp.R
import com.zulfahmi.comelapp.adapter.DetectionAdapter
import com.zulfahmi.comelapp.adapter.RecyclerViewAdapter
import com.zulfahmi.comelapp.model.Detection
import com.zulfahmi.comelapp.util.Commons
import com.zulfahmi.comelapp.util.Constants
import com.zulfahmi.comelapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_detection.*

class DetectionActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var database: DatabaseReference
    private lateinit var rvAdapter: DetectionAdapter

    private val listDetection = ArrayList<Detection>()
    private val childName = Constants.FIREBASE_DETECTIONS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Detections"

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        setProgressbarVisibility(true)

        database = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DETECTIONS)
        swipe_refresh_layout.setOnRefreshListener {
            refreshData()
        }

        val linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
    }

    override fun onResume() {
        super.onResume()
        observeData()
    }

    private fun observeData() {
        mainViewModel.getAllDetection().observe(this, Observer {
            rvAdapter = DetectionAdapter(it) { item ->
                val detection = item as Detection
                val id = detection.userId
                Commons.showSelector(this, id, arrayOf("Delete")){ _, _ ->
                      Commons.deleteItemDialog(this, childName, id)
                }
            }

            recycler_view.adapter = rvAdapter
            rvAdapter.notifyDataSetChanged()
            setProgressbarVisibility(false)
            if(it.isEmpty()) setEmptyTextVisibility(true)
            else{
                listDetection.clear()
                listDetection.addAll(it)
                setEmptyTextVisibility(false)
            }
        })
    }

    private fun refreshData() {
        listDetection.clear()
        setProgressbarVisibility(true)
        observeData()
        swipe_refresh_layout.isRefreshing = false
        setProgressbarVisibility(false)
    }

    private fun setEmptyTextVisibility(state: Boolean){
        if (state) tv_empty.visibility = View.VISIBLE
        else tv_empty.visibility = View.GONE
    }

    private fun setProgressbarVisibility(state: Boolean){
        if (state) progressbar.visibility = View.VISIBLE
        else progressbar.visibility = View.INVISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
