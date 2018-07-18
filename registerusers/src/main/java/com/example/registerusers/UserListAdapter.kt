package com.example.registerusers

import android.content.Context
import android.database.AbstractCursor
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

data class ViewHolder(val profile: ImageView,
                      val name: TextView,
                      val tel_num: TextView,
                      val del_item: ImageButton)

class UserListAdapter(context: Context?, cursor: Cursor) : CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER){
    val mCtx = context



    /**
     * 1. Adapter에 뷰를 설정하기 위해 호출되는 메소드
     */
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mainView = inflater.inflate(R.layout.layout_user_list, parent, false)
        var holder : ViewHolder = ViewHolder(
                mainView.findViewById(R.id.profile) as ImageView,
                mainView.findViewById(R.id.name) as TextView,
                mainView.findViewById(R.id.tel_num) as TextView,
                mainView.findViewById(R.id.del_item) as ImageButton)
        mainView.tag = holder
        return mainView
    }


    /**
     * 2. 새로 생성된 뷰가 화면에 보여질 때 호출되는 메소드
     */
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val holder : ViewHolder = view?.
    }

}