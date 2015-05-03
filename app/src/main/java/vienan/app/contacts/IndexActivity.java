package vienan.app.contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.Date;

import vienan.app.vienan.app.util.ContactsTable;
import vienan.app.vienan.app.util.User;


public class IndexActivity extends ActionBarActivity {
    ListView list;
    public BaseAdapter myAdapter;
    private User[] users;
    private int selectItem=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list= (ListView) findViewById(R.id.list);
        setTitle("通讯录");
        loadContacts();
        

    }

    @Override
    protected void onResume() {
        super.onResume();
        ContactsTable ct=new ContactsTable(this);
        users=ct.getAllUser();
        myAdapter.notifyDataSetChanged();
    }

    private void loadContacts() {
        ContactsTable ct=new ContactsTable(this);
        users=ct.getAllUser();
        myAdapter=new BaseAdapter() {
            LayoutInflater mInflater;
            @Override
            public int getCount() {
                return users.length;
            }

            @Override
            public Object getItem(int position) {
                return users[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            //在外面先定义，ViewHolder静态类
            class ViewHolder
            {
                public TextView info;
            }
            //然后重写getView
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView == null)
                {
                    TextView textView=new TextView(IndexActivity.this);
                    textView.setTextSize(22);
                    convertView=textView;
                }
                ((TextView)convertView).setText(users[position].getUsername()+"-----" +
                        users[position].getMobile());
                if(position==selectItem){
                    convertView.setBackgroundColor(Color.YELLOW);
                }else{
                    convertView.setBackgroundColor(0);
                }
            return convertView;
        }
        };
        list.setAdapter(myAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem=position;
                myAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(menu.NONE,1,menu.NONE,"添加");
        menu.add(menu.NONE,2,menu.NONE,"编辑");
        menu.add(menu.NONE,3,menu.NONE,"查看信息");
        menu.add(menu.NONE,4,menu.NONE,"删除");
        menu.add(menu.NONE,5,menu.NONE,"查询");
        menu.add(menu.NONE,6,menu.NONE,"导入到手机电话簿");
        menu.add(menu.NONE,7,menu.NONE,"退出");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case 1:
                Intent intent=new Intent(IndexActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case 2:
                if(users[selectItem].getDB_id()>0)
                {
                    intent=new Intent(IndexActivity.this,UpdateContacts.class);
                    intent.putExtra("user_id",users[selectItem].getDB_id());
                    startActivity(intent);
                }else {
                    Toast.makeText(IndexActivity.this,"无结果记录，无法操作！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if(users[selectItem].getDB_id()>0){
                    intent=new Intent(IndexActivity.this,MessageActivity.class);
                    intent.putExtra("user_id",users[selectItem].getDB_id());
                    startActivity(intent);
                }else{
                    Toast.makeText(IndexActivity.this,"无结果记录，无法操作！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                if(users[selectItem].getDB_id()>0){
                    delete();
                }else{
                    Toast.makeText(IndexActivity.this,"无结果记录，无法操作！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 5:
                new FindDialog(this).show();
                break;
            case 6:
                if(users[selectItem].getDB_id()>0){
                    importPhone(users[selectItem].getUsername(),users[selectItem].getMobile());
                    Toast.makeText(IndexActivity.this,"已经成功导入" +
                            users[selectItem].getUsername()+"到手机电话簿！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(IndexActivity.this,"无结果记录，无法操作！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 7:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public class FindDialog extends Dialog {

        public FindDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.find);
            setTitle("联系人查询");
            final EditText findKey= (EditText) findViewById(R.id.findkey);
            Button search= (Button) findViewById(R.id.search);
            Button cancel= (Button) findViewById(R.id.cancel);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key=findKey.getText().toString().trim();
                    if(!key.equals("")){
                        ContactsTable ct=new ContactsTable(IndexActivity.this);
                        User[] users=ct.findUserByKey(key);
                        for(int i=0;i<users.length;i++){
                            User user=users[i];
                        }
                        dismiss();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    private void importPhone(String username, String mobile) {
        Uri phoneUri= ContactsContract.Data.CONTENT_URI;
        ContentValues values=new ContentValues();
        Uri rawContactUri=this.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI,values);
        long rawContactId= ContentUris.parseId(rawContactUri);
        //向data表插入姓名
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,username);
        this.getContentResolver().insert(phoneUri,values);
        //向data表插入电话号码
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID,rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,mobile);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        this.getContentResolver().insert(phoneUri,values);
    }

    private void delete() {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("系统信息");
        alert.setMessage("删除联系人？");
        alert.setPositiveButton("是",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContactsTable ct=new ContactsTable(IndexActivity.this);
                if(ct.deleteByName(users[selectItem])){
                    users=ct.getAllUser();
                    myAdapter.notifyDataSetChanged();
                    selectItem=0;
                    Toast.makeText(IndexActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(IndexActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton("否",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
}
