package com.example.registerusers

import android.content.Context
import android.database.AbstractCursor
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
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
        val holder: ViewHolder = view?.tag as ViewHolder
        holder.name.text = String.format("%s (%d)",
                cursor?.getString(1),
                cursor?.getInt(2))
        holder.tel_num.text = cursor?.getString(3)
        val picture: Drawable? = getPicture(cursor?.getString(4) ?: "0") ?:
        context?.getDrawable(android.R.drawable.ic_menu_gallery)
        holder.profile.background = picture
        holder.del_item.tag = cursor?.getLong(0)
    }

    private fun getPicture(path: String): Drawable? {
        val imgId: Long = path.toLong()
        if ( imgId == 0L ) return null
        val bitmap: Bitmap = MediaStore.Images.
                Thumbnails.getThumbnail(this.mCtx?.contentResolver,
                imgId,
                MediaStore.Images.Thumbnails.MICRO_KIND,
                null)
        bitmap ?: return null
        return BitmapDrawable(mCtx?.resources, bitmap)
    }
}