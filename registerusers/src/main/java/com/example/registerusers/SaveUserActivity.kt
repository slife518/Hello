package com.example.registerusers

import android.content.CursorLoader
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import com.example.registerusers.db.DBHandler_Anko
import com.example.registerusers.db.UserInfo
import kotlinx.android.synthetic.main.activity_save_user.*

class SaveUserActivity : AppCompatActivity() {

    val mDBHandler: DBHandler_Anko = DBHandler_Anko(this)
    companion object {
        val PICK_IMAGE: Int = 1010
        val REQ_PERMISSION: Int = 1011
    }
    var mSelectedImgId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_user)
        setSupportActionBar(toolbar)
        sel_image.setOnClickListener {view ->
            val writeStorage: String = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
            val check = ActivityCompat.checkSelfPermission(this, writeStorage)
            if ( check != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, arrayOf(writeStorage), REQ_PERMISSION)
            }
            else {
                val uri: Uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                var intent: Intent = Intent(Intent.ACTION_PICK, uri)
                startActivityForResult(intent, PICK_IMAGE)
            }

        }
        btn_add.setOnClickListener {view ->
            val user: UserInfo = UserInfo(
                    edit_name.text.toString(),
                    edit_age.text.toString(),
                    edit_tel.text.toString(),
                    mSelectedImgId.toString())
            mDBHandler.addUser(user)
            mDBHandler.close()
            finish()
        }
    }


    //권한􀀁요청􀀁응답􀀁코드􀀁작성􀀁(사용자가􀀁권한􀀁사용을􀀁거부했을􀀁경우)
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when ( requestCode ) {
            REQ_PERMISSION -> {
                for ( i in 0..permissions.size-1 ) {
                    if ( grantResults[i] != PackageManager.PERMISSION_GRANTED ) {
                        var rationale: Boolean =
                                ActivityCompat.shouldShowRequestPermissionRationale(this,
                                        permissions[i])
                        if ( rationale ) {
                            AlertDialog.Builder(this)
                                    .setTitle("권한 설정")
                                    .setMessage("이미지 썸네일을 만들기 위해서 저장권한이 필요합니다. 승인하지 않으면 이미지를 설정할 수 없습니다.")
                                            .setCancelable(true)
                                            .setPositiveButton("설정하러 가기") {
                                                dialog, which -> showSetting()
                                            }.create().show()
                        }
                    }
                }
            }
        }
    }

    //사진선택후 처리작업하기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when ( requestCode ) {
            PICK_IMAGE -> {
                val uri = data?.data
                uri ?: return
                mSelectedImgId = getImageID(uri)
                if ( mSelectedImgId == -1L ) return
                val bitmap: Bitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver,
                        mSelectedImgId, MediaStore.Images.Thumbnails.MICRO_KIND,
                        null)
                val sel_image: ImageView = findViewById(R.id.sel_image) as ImageView
                sel_image.setImageBitmap(bitmap)
            }
        }
    }

    fun getImageID(uri: Uri): Long {
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val cursor = CursorLoader(this, uri, projection, null, null, null).loadInBackground()
        var columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
        if ( columnIndex == -1 ) return -1
        cursor.moveToFirst()
        val id = cursor.getLong(columnIndex)
        cursor.close()
        return id
    }


    fun showSetting() {
        val intent: Intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
