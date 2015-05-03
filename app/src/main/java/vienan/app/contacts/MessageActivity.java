package vienan.app.contacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import vienan.app.vienan.app.util.ContactsTable;
import vienan.app.vienan.app.util.User;

/**
 * Created by lenovo on 2015/4/26.
 */
public class MessageActivity extends Activity{
    private TextView tvName,tvUnit,tvMobile,tvQQ,tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        setTitle("通讯录");
        initView();
    }

    private void initView() {
        tvName= (TextView) findViewById(R.id.tvnName);
        tvUnit= (TextView) findViewById(R.id.tvUnit);
        tvMobile= (TextView) findViewById(R.id.tvMobile);
        tvQQ= (TextView) findViewById(R.id.tvQQ);
        tvAddress= (TextView) findViewById(R.id.tvAddress);
        Bundle localBundle=getIntent().getExtras();
        int id=localBundle.getInt("user_id");
        ContactsTable ct=new ContactsTable(this);
        User user=ct.getUserbyId(id);
        tvName.setText("姓名："+user.getUsername());
        tvUnit.setText("单位："+user.getUnit());
        tvMobile.setText("电话："+user.getMobile());
        tvQQ.setText(" Q Q ："+user.getQq());
        tvAddress.setText("地址："+user.getAddress());
    }
}
