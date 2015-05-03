package vienan.app.contacts;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;

import vienan.app.vienan.app.util.ContactsTable;
import vienan.app.vienan.app.util.User;

/**
 * Created by lenovo on 2015/4/23.
 */
public class UpdateContacts extends Activity {
    private EditText username,unit,mobile,qq,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("修改信息");
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView() {
        username= (EditText) findViewById(R.id.username);
        unit= (EditText) findViewById(R.id.unit);
        mobile= (EditText) findViewById(R.id.mobile);
        qq= (EditText) findViewById(R.id.qq);
        address= (EditText) findViewById(R.id.address);
        Bundle localBundle=getIntent().getExtras();
        int id=localBundle.getInt("user_id");
        ContactsTable ct=new ContactsTable(this);
        User user=ct.getUserbyId(id);
        username.setText(user.getUsername());
        mobile.setText(user.getMobile());
        qq.setText(user.getQq());
        unit.setText(user.getUnit());
        address.setText(user.getAddress());
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(Menu.NONE,1,Menu.NONE,"save");
        menu.add(Menu.NONE,2,Menu.NONE,"exit");
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case 1:
                if(!username.getText().toString().trim().equals(""))
                {
                    User user=new User();
                    user.setUsername(username.getText().toString());
                    user.setUnit(unit.getText().toString());
                    user.setMobile(mobile.getText().toString());
                    user.setQq(qq.getText().toString());
                    user.setAddress(address.getText().toString());
                    ContactsTable cTable=new ContactsTable(UpdateContacts.this);

                    if(cTable.addDate(user)){
                        Toast.makeText(UpdateContacts.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(UpdateContacts.this,"修改失败！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UpdateContacts.this,"数据不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
