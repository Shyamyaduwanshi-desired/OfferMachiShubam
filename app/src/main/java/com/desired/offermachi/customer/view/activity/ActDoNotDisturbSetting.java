package com.desired.offermachi.customer.view.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.R;
import com.desired.offermachi.customer.constant.UserSharedPrefManager;
import com.desired.offermachi.customer.model.User;
import com.desired.offermachi.customer.model.days_model;
import com.desired.offermachi.customer.model.hours_model;
import com.desired.offermachi.customer.presenter.DonoDisturbPresenter;
import com.desired.offermachi.customer.presenter.HomePresenter;
import com.desired.offermachi.customer.view.adapter.DaysDistrubAdapter;
import com.desired.offermachi.customer.view.fragment.HomeFragment;
import com.desired.offermachi.retalier.constant.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ActDoNotDisturbSetting extends AppCompatActivity implements DonoDisturbPresenter.DoNotDisInfo,DaysDistrubAdapter.DaysAdapterClick {

    RecyclerView hours_recyclerview,days_recyclerview ;
    LinearLayout linear_visible;
    Button submit_button;
    private Context context;
    private HoursDistrubAdapter hoursDistrubAdapter;
    private DaysDistrubAdapter daysDistrubAdapter;
    private List<hours_model> hoursdata = new ArrayList<>();
    private List<days_model> daysdata = new ArrayList<>();
    String dndid = "";
    ImageView imageViewback,info;
    private DonoDisturbPresenter presenter;
    String idholder,preSetDndId="",dndStatus="",dnd_start_time="",dnd_end_time="";;
TextView tvPreSetDND;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_do_not_dis_setting);
        context=this;
//        appData=new AppData();

        preSetDndId=getIntent().getStringExtra("dnd_id");
        dndStatus=getIntent().getStringExtra("dnd_status");
        dnd_start_time=getIntent().getStringExtra("dnd_start_time");
        dnd_end_time=getIntent().getStringExtra("dnd_end_time");

        presenter = new DonoDisturbPresenter(this, this);
        User user = UserSharedPrefManager.getInstance(this).getCustomer();
        idholder= user.getId();
        InitCompo();
        Listner();
        CallAPi(1);

//        CallAPi(3);
    }


    public void CallAPi(int pos)
    {
        if (isNetworkConnected(ActDoNotDisturbSetting.this)) {
            switch (pos)
            {
                case 1:
                    if(TextUtils.isEmpty(preSetDndId))
                    {
                        preSetDndId="";
                    }
                    presenter.GetDoNoDisDataList(preSetDndId);
                    break;
               case 2:
                   if(Valid())
                   {
//                       Toast.makeText(context, "dndid= "+dndid+" idholder= "+idholder, Toast.LENGTH_SHORT).show();

                       presenter.SubmitDoNoDisData(idholder,dndid);
                   }
                    break;
                case 3:
                    presenter.GetDoNoDisStatus(idholder);
                    break;
            }
        } else {
            ShowAlert(this, "Please connect to internet.");
        }

    }

    private boolean Valid() {
        boolean checkflag=true;
        if(TextUtils.isEmpty(idholder))
        {
            Toast.makeText(context, "Not find user id", Toast.LENGTH_SHORT).show();
            checkflag=false;
        }
        else if(TextUtils.isEmpty(dndid))
        {
            Toast.makeText(context, "Please select atleast one time ", Toast.LENGTH_SHORT).show();
            checkflag=false;
        }

        return checkflag;
    }

    private void Listner() {
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallAPi(2);
            }
        });
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(ActDoNotDisturbSetting.this, InfoActivity.class);
                    startActivity(intent);

            }
        });
    }

    private void InitCompo() {
        tvPreSetDND = findViewById(R.id.tv_preset_dnd);
        imageViewback = findViewById(R.id.imageback);
        info=findViewById(R.id.info_id);
        hours_recyclerview = (RecyclerView)findViewById(R.id.hours_recyclerview_id);
        hours_recyclerview.setHasFixedSize(true);
        hours_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        days_recyclerview = (RecyclerView)findViewById(R.id.days_recyclerview_id);
        days_recyclerview.setHasFixedSize(true);
        days_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        linear_visible = (LinearLayout)findViewById(R.id.linear_visible);
        submit_button = (Button)findViewById(R.id.submit_dayhours_button_id);

        if(!TextUtils.isEmpty(preSetDndId))
        {
            tvPreSetDND.setVisibility(View.VISIBLE);
            tvPreSetDND.setText("DND Start From= "+AppData.ConvertDate5(dnd_start_time)+" To= "+AppData.ConvertDate5(dnd_end_time));
        }
        else
        {
            tvPreSetDND.setVisibility(View.GONE);
        }
    }

    private void displayDatasecond() {
        daysDistrubAdapter = new DaysDistrubAdapter(ActDoNotDisturbSetting.this, (ArrayList<days_model>) daysdata,this);
        days_recyclerview.setAdapter(daysDistrubAdapter);

        if (daysDistrubAdapter.getItemCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
        } else {

        }

    }
    private void displayData() {
        hoursDistrubAdapter = new HoursDistrubAdapter(ActDoNotDisturbSetting.this, (ArrayList<hours_model>) hoursdata);
        hours_recyclerview.setAdapter(hoursDistrubAdapter);
        if (hoursDistrubAdapter.getItemCount() ==0) {
            Toast.makeText(getApplicationContext(), "NO Data", Toast.LENGTH_SHORT).show();
        } else {

        }
    }
//call get data
    @Override
    public void success(ArrayList<days_model> dayResponse, ArrayList<hours_model> hoursResponse, String status) {

        switch (status)
        {
            case "1":
                hoursdata.clear();
                daysdata.clear();
                hoursdata=hoursResponse;
                daysdata=dayResponse;
                displayData();
                displayDatasecond();
                break;
           case "2":
               Toast.makeText(context, "You have successfully set do not disturb setting", Toast.LENGTH_SHORT).show();
               finish();
                break;

        }

    }
//only for dnd get data
//String dndStatus="0",dnd_start_time="",dnd_end_time="";
    @Override
    public void successDND(JSONObject objJson, String status) {
//        tvPreSetDND.setVisibility(View.GONE);
//        switch (status)
//        {
//            case "3":
//                try {
////                    JSONObject objJson =new JSONObject(status);
//                    String id = objJson.getString("id");
//                    String dnd_id = objJson.getString("dnd_id");
//                    dnd_start_time = objJson.getString("dnd_start_time");
//                    dnd_end_time = objJson.getString("dnd_end_time");
//                    dndStatus = objJson.getString("status");
//                    tvPreSetDND.setVisibility(View.VISIBLE);
//                    tvPreSetDND.setText("From= "+dnd_start_time+" To="+dnd_end_time);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }

    }

    @Override
    public void error(String response) {

    }

    @Override
    public void fail(String response) {

    }
//day adapter click



    @Override
    public void onDaysClick(int position) {
        dndid = daysdata.get(position).getId();
        Log.e("","position= "+position);
        for(int i=0;i<daysdata.size();i++)
        {
            if(i==position)
            {
                daysdata.get(i).setSelected(true);
            }
            else {
                daysdata.get(i).setSelected(false);
            }
        }
        daysDistrubAdapter.notifyDataSetChanged();

//        Toast.makeText(context, "dndid= "+dndid, Toast.LENGTH_SHORT).show();
    }


    public class HoursDistrubAdapter extends RecyclerView.Adapter<HoursDistrubAdapter.MyViewHolder> {

        private ArrayList<hours_model> hoursdata = new ArrayList<hours_model>();

        public HoursDistrubAdapter(Context applicationContext, ArrayList<hours_model> firstList) {
            this.hoursdata = firstList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public  View linearLayoutparent;
            RadioGroup radioGroup;
            RadioButton radioButton;
            TextView textView;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.radioGroup=(RadioGroup)itemView.findViewById(R.id.hours_radio_group_id);
                this.radioButton=(RadioButton)itemView.findViewById(R.id.radio_button_hours_id);
                this.textView =(TextView)itemView.findViewById(R.id.hours_text_id);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adptr_radio_do_not_disturb, parent, false);

          MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView textView = holder.textView;
            final RadioButton radioButton = holder.radioButton;
            LinearLayout linearLayoutparent= (LinearLayout) holder.linearLayoutparent;

            textView.setText(hoursdata.get(listPosition).getTittle());
            if(hoursdata.get(listPosition).selected)
            {
                radioButton.setChecked(true);
            }

            else
            {
                radioButton.setChecked(false);

            }
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setVisiblility(listPosition);
                    notifyDataSetChanged();
                }
            });
        }
        @Override
        public int getItemCount() {
            return hoursdata.size();
        }
    }
    private void setVisiblility(int pos) {

        for(int i=0;i<hoursdata.size();i++)
        {
            if(i==pos)
            {
                hoursdata.get(i).setSelected(true);
                dndid = hoursdata.get(pos).getId();
            }
            else
            {
                hoursdata.get(i).setSelected(false);
            }
        }

        if(pos==(hoursdata.size()-1))
        {
            linear_visible.setVisibility(View.VISIBLE);
            dndid = "";
        }
        else
        {
            linear_visible.setVisibility(View.GONE);

            for(int i=0;i<daysdata.size();i++)
            {
                daysdata.get(i).setSelected(false);
            }
            daysDistrubAdapter.notifyDataSetChanged();

        }

    }
    PrettyDialog prettyDialog=null;
    public void ShowAlert(Context context, String message) {
        if(prettyDialog!=null)
        {
            prettyDialog.dismiss();
        }
        prettyDialog = new PrettyDialog(context);
        prettyDialog.setCanceledOnTouchOutside(false);
        TextView title = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_title);
        TextView tvmessage = (TextView) prettyDialog.findViewById(libs.mjn.prettydialog.R.id.tv_message);
        title.setTextSize(15);
        tvmessage.setTextSize(15);
        prettyDialog.setIconTint(R.color.colorPrimary);
        prettyDialog.setIcon(R.drawable.pdlg_icon_info);
        prettyDialog.setTitle("");
        prettyDialog.setMessage(message);
        prettyDialog.setAnimationEnabled(false);
        prettyDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        prettyDialog.addButton("Cancel", R.color.black, R.color.white, new PrettyDialogCallback() {
            @Override
            public void onClick() {
                prettyDialog.dismiss();
            }
        }).show();
    }
       boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
