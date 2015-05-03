package vienan.app.contacts;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import vienan.app.vienan.app.util.ContactsTable;
import vienan.app.vienan.app.util.User;


public class MainActivity extends ActionBarActivity {
    private EditText username,unit,mobile,qq,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        username= (EditText) findViewById(R.id.username);
        unit= (EditText) findViewById(R.id.unit);
        mobile= (EditText) findViewById(R.id.mobile);
        qq= (EditText) findViewById(R.id.qq);
        address= (EditText) findViewById(R.id.address);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(Menu.NONE,1,Menu.NONE,"save");
        menu.add(Menu.NONE,2,Menu.NONE,"exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
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
                    ContactsTable cTable=new ContactsTable(MainActivity.this);
                    if(cTable.addDate(user)){
                        Toast.makeText(MainActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"添加失败！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"请先输入数据",Toast.LENGTH_SHORT).show();
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
