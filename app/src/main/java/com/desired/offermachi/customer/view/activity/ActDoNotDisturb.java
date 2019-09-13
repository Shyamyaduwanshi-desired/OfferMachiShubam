package com.desired.offermachi.customer.view.activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.NotificationModel;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.presenter.CustomerNotificationPresenter;
import com.desired.offermachi.customer.view.adapter.CustomerNotificationAdapter;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActDoNotDisturb extends AppCompatActivity implements View.OnClickListener {
    ImageView imageViewback;
    String idholder;
    ListView listView;

    ArrayAdapter adapter;
    String[] version = {"Aestro","Blender","CupCake","Donut","Eclair","Froyo","GingerBread","HoneyComb","IceCream Sandwich",
            "Jelly Bean","Kitkat","Lolipop","Marshmallow","Nought","Oreo"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_do_not_disturb);
        init();
        Listner();
        GetData();
    }

    private void Listner() {
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(ActDoNotDisturb.this, "Selected -> " + version[i], Toast.LENGTH_SHORT).show();


            }
        });
    }
public void SetView(int pos)
{
    for(int i=0;i<list.size();i++)
    {
        if(i==pos)
        {

        }

    }
}
    private void init(){
        listView = findViewById(R.id.rv_do_not_disturb);

    }
    ArrayList<String>list=new ArrayList();
    public void GetData()
    {
      list = new ArrayList<>();
        for (int i = 0;i<version.length;i++){
            list.add(version[i]);

        }
        SetAdpater();
    }
    public void SetAdpater()
        {
                adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice,list);
                listView.setAdapter(adapter);
        }
    @Override
    public void onClick(View v) {
        if (v==imageViewback){
            onBackPressed();
        }

    }

}
