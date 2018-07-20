package com.example.registerusers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar);
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

    //메뉴가 선택되었을 때 호출됨. 선택된 메뉴를 파라미터로 전달함.
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId){
//            R.id.add_user -> {
//                val intent : Intent = Intent(this, SaveUserActivity::class.java)
//                startActivity(intent)
//            }
//        }

        return super.onOptionsItemSelected(item)
    }
}
