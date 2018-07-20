package com.example.registerusers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.registerusers.db.DBHandler_Anko
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: UserListAdapter
    public lateinit var mDBHandler: DBHandler_Anko
    companion object {
        val REQUEST_ADD_USER = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar);

        mDBHandler = DBHandler_Anko(this)
        val newOne = mDBHandler.getUserAllWithCursor()
        if ( newOne.count != 0 ) {
            mAdapter = UserListAdapter(this, newOne)  //불필요한 메모리 낭비를 막기 위한 Adapter 조건부 생성
            user_list.adapter = mAdapter
        }
    }

    //메모리 누수를 막기 위한 Adapter, DBHandler 자원 반환
    override fun onDestroy() {
        super.onDestroy()
        mAdapter?.cursor.close()
        mDBHandler.close()
    }
    //Toolbar 에 메뉴를 만들 때 호출됨
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    //onCreateOptionsMenu가 호출된 다음 호출되는 메소드. 코드상에서 변수값을 참조하여 메뉴를 보여주거나 숨기는 경우 사용할 수 있음
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        return super.onPrepareOptionsMenu(menu)
    }

//    메뉴가 선택되었을 때 호출됨. 선택된 메뉴를 파라미터로 전달함.
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when ( item?.itemId ) {
            R.id.add_user -> {
                val intent: Intent = Intent(this, SaveUserActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD_USER)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when ( requestCode ) {
            REQUEST_ADD_USER -> {
                val newOne = mDBHandler.getUserAllWithCursor()
                if ( !::mAdapter.isInitialized ) {
                    mAdapter = UserListAdapter(applicationContext, newOne)
                    user_list.adapter = mAdapter
                }
                //기존의 Cursor Data는 제거하고 새로운 Cursor데이터로 변경함
                //Data 변경 후notifyDataSetInvalidated 호출이 반드시 필요함
                mAdapter.changeCursor(newOne)

                //CursorAdapter 의 데이터가 변경되어야 함을 알려줌.
                mAdapter.notifyDataSetInvalidated()
            }
        }
    }

    fun onClickDelete(view: View) {
        mDBHandler.deleteUser(view.tag as Long)
        val newOne = mDBHandler.getUserAllWithCursor()
        mAdapter.changeCursor(newOne)
        mAdapter.notifyDataSetInvalidated()
    }
}
