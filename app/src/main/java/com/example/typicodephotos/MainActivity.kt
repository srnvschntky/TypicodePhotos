package com.example.typicodephotos

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_dialogue_box.*
import kotlinx.android.synthetic.main.bottom_dialogue_box.view.*
import java.util.ArrayList



@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ItemOnClickListener {
    var list: ArrayList<PhotosList> = ArrayList()
    lateinit var myadapter: MyAdapter
    private val  TAG ="Activity"

    //    var adapter =MyAdapter(list,this)
//    var array=Array(1000){PhotosList()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG,"main onCreate()")

        Async(this).execute()
        LocalBroadcastManager.getInstance(this).registerReceiver(register, IntentFilter("result"))
        initRecyclerView()


    }


    override fun onStart() {
        super.onStart()
        Log.i(TAG,"main onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG,"main onRestart()")

    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG,"main onResume() ")

    }


    override fun onPause() {
        super.onPause()
        Log.i(TAG,"main onPause()")

    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"main onStop()")

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"main onDestroy()")

    }



//    private fun addData(itemOnClickListener: ItemOnClickListener) {
//        myadapter.getData(list,itemOnClickListener)
//        myadapter.notifyDataSetChanged()
//
//    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            myadapter = MyAdapter()
            adapter = myadapter
        }

    }

    private val register = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val json = intent?.getStringExtra("Result").toString()
            sendtoGson(json)
        }

    }

    private fun sendtoGson(json: String) {
        list = Gson().fromJson(json, Array<PhotosList>::class.java)
            .toMutableList() as ArrayList<PhotosList>
            sendData(list)

//        sendtoAdapter(list)
    }

    private  fun sendData(list: ArrayList<PhotosList>) {

            myadapter.todo(list, this)
            myadapter.notifyDataSetChanged()
        //            myadapter.getData(item, this)
    }

//    private fun sendtoAdapter(list: ArrayList<PhotosList>) {
//            adapter =MyAdapter(list,this)
//            adapter.notifyDataSetChanged()
//            recyclerView.adapter=adapter
//
//    }


    override fun onClickListener(position: Int) {
        bottomDialog(position, this)
//        list.removeAt(position)
//        adapter.notifyDataSetChanged()
//        confirmItemRemoval(position,this)
    }

    private fun bottomDialog(position: Int, context: Context) {

        val builder = BottomSheetDialog(context, R.style.Bottom_Sheet_Dialogue)
        val view =
            LayoutInflater.from(context).inflate(R.layout.bottom_dialogue_box, bottom_dialogue)
        val id = "Id: ${list[position].id.toString()}"
        view.deleteIdTxtView.text = id
        view.yesButton.setOnClickListener {
            list.removeAt(position)
            myadapter.notifyDataSetChanged()
            builder.dismiss()
            Toast.makeText(context, "Successfully deleted: ${id}", Toast.LENGTH_SHORT).show()

//            confirmDelete(position, context)

        }
        view.noButton.setOnClickListener {
            builder.dismiss()
        }
//        builder.setCancelable(false)
        builder.setContentView(view)
        builder.show()
    }



//    private fun confirmDelete(position: Int, context: Context) {
//        val builder = AlertDialog.Builder(context)
//
//        builder.setPositiveButton("Yes") { _, _ ->
//            val id = list[position].id
//            list.removeAt(position)
//            myadapter.notifyDataSetChanged()
//            Toast.makeText(context, "Successfully deleted: ${id}", Toast.LENGTH_SHORT).show()
//        }
//        builder.setNegativeButton("No") { _, _ -> }
//        builder.setTitle("Delete '${list[position].id}'?")
//        builder.setMessage("Are you sure you want to delete '${list[position].id}'?")
//        builder.setIcon(R.drawable.ic_delete)
//        builder.setCancelable(false)
//        builder.create().show()
//
//    }


//
//    private fun confirmItemRemoval() {
//        val builder = AlertDialog.Builder()
//        builder.setPositiveButton("Yes") { _, _ ->
//            mToDoViewModel.deleteItem(args.currentItem)
//            Toast.makeText(
//                requireContext(),
//                "Successfully Removed: ${args.currentItem.title}",
//                Toast.LENGTH_SHORT
//            ).show()
//            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
//        }
//        builder.setNegativeButton("No") { _, _ -> }
//        builder.setTitle("Delete '${args.currentItem.title}'?")
//        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
//        builder.create().show()
//    }
}