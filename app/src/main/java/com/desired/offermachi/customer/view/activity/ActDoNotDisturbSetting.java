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
//    String userid= "1";
    String dndid = "";//2
//    private static final String ROOT_URL = "http://offermachi.in/api/common_dnd_services";
//    private static final String ROOT_URL1 = "http://offermachi.in/api/common_dnd_services_start";
    ImageView imageViewback;
    private DonoDisturbPresenter presenter;
//    AppData appData;
    String idholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_do_not_dis_setting);
        context=this;
//        appData=new AppData();
        presenter = new DonoDisturbPresenter(this, this);
        User user = UserSharedPrefManager.getInstance(this).getCustomer();
        idholder= user.getId();
        InitCompo();
        Listner();
        CallAPi(1);
    }
    public void CallAPi(int pos)
    {
        if (isNetworkConnected(ActDoNotDisturbSetting.this)) {
            switch (pos)
            {
                case 1:
                    presenter.GetDoNoDisDataList();
                    break;
               case 2:
                   if(Valid())
                   {
                       Toast.makeText(context, "dndid= "+dndid+"idholder= "+idholder, Toast.LENGTH_SHORT).show();
//                       presenter.SubmitDoNoDisData(idholder,dndid);
                   }

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
//                SubmitButtonData();
                CallAPi(2);
            }
        });
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void InitCompo() {
        imageViewback = findViewById(R.id.imageback);
        hours_recyclerview = (RecyclerView)findViewById(R.id.hours_recyclerview_id);
        hours_recyclerview.setHasFixedSize(true);
        hours_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        days_recyclerview = (RecyclerView)findViewById(R.id.days_recyclerview_id);
        days_recyclerview.setHasFixedSize(true);
        days_recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        linear_visible = (LinearLayout)findViewById(R.id.linear_visible);
        submit_button = (Button)findViewById(R.id.submit_dayhours_button_id);
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

   /* private void SubmitButtonData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Button", "onResponse: " + response);
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            String status=obj.getString("status");
                            String message=obj.getString("message");
                            if (status.equals("200")){
//                                String result=obj.getString("result");
//                                Intent intent =new Intent(getApplicationContext(),SecondMainActivity.class);
//                                startActivity(intent);
                                Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                                finish();

                            }else {
                                Toast.makeText(ActDoNotDisturbSetting.this, "Success"+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "onErrorResponse: " + error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", userid);
                params.put("dndid", dndid);
                Log.e("surbhi", "params" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ActDoNotDisturbSetting.this);
        requestQueue.add(stringRequest);

    }*/
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
        for(int i=0;i<daysdata.size();i++)
        {
            if(i==position)
            {
                daysdata.get(position).setSelected(true);
            }
            else {
                daysdata.get(position).setSelected(false);
            }
        }
        daysDistrubAdapter.notifyDataSetChanged();

//        if((daysdata.size()-1)!=position) {
//
//        }
//        else
//        {
//            dndid="";
//        }
        Toast.makeText(context, "dndid= "+dndid, Toast.LENGTH_SHORT).show();
    }


    public class HoursDistrubAdapter extends RecyclerView.Adapter<HoursDistrubAdapter.MyViewHolder> {

        private  Context mContext;
        private Object ProductDetailsActivity;
        LinearLayout second_linear_visible ;
        RadioGroup radioGroup;
        RadioButton radioButton;
        private Context context;
        private Object ArrayList;
        private Object first_model;

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

        }
        else
        {
            linear_visible.setVisibility(View.GONE);
//            dndid = "";
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


    /*public class DaysDistrubAdapter extends RecyclerView.Adapter<DaysDistrubAdapter.MyViewHolder> {

        private ArrayList<days_model> daysdata;
        private final Context mContext;
        private CheckBox lastChecked = null;
        private  int lastCheckedPos = 0;


        public DaysDistrubAdapter(Context context, ArrayList<days_model> data){
            this.daysdata = data;
            this.mContext = context;
//            this.itemClick = cateClick;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adptr_disturb_second_layout, parent, false);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView textView = holder.textView;
            CheckBox checkBox = holder.checkBox;

            textView.setText(daysdata.get(listPosition).getTittle());
            holder.checkBox.setChecked(daysdata.get(listPosition).isSelected());
            holder.checkBox.setTag(new Integer(listPosition));

            if(listPosition == 0 && daysdata.get(0).isSelected() && holder.checkBox.isChecked())
            {
                lastChecked = holder.checkBox;
                lastCheckedPos = 0;
            }

            holder.checkBox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    CheckBox cb = (CheckBox)v;
                    int clickedPos = ((Integer)cb.getTag()).intValue();

                    if(cb.isChecked())
                    {
                        if(lastChecked != null)
                        {
                            lastChecked.setChecked(false);
                            daysdata.get(lastCheckedPos).setSelected(false);
                        }

                        lastChecked = cb;
                        lastCheckedPos = clickedPos;
                    }
                    else
                        lastChecked = null;
                    daysdata.get(clickedPos).setSelected(cb.isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {
            return daysdata.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            CheckBox checkBox;
            TextView textView;

            public MyViewHolder(View itemView) {

                super(itemView);
                this.checkBox=(CheckBox)itemView.findViewById(R.id.days_check_box_id);
                this.textView =(TextView)itemView.findViewById(R.id.days_textview_id);
            }
        }

    }*/
}
