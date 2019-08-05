package com.desired.offermachi.retalier.fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.R;
import com.desired.offermachi.retalier.SharedPrefManagerLogin;
import com.desired.offermachi.retalier.UserModel;
import com.desired.offermachi.retalier.ui.RetalierDashboard;
import com.desired.offermachi.retalier.ui.RetalierOtpActivity;
import com.desired.offermachi.retalier.ui.RetalierRegistration;
import com.desired.offermachi.retalier.ui.RetalierViewOfferDiscount;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistrationStoreDetailsFrgment extends Fragment {
    View view;
    EditText storename,storecontact,storeaddress,cityedt,days,hours,aboutstore;
    LinearLayout shopimage;
    Button registerbutton;
    String shop_name,shop_contact_number,address,city,about_store,shop_hours,shop_day_hours;
    String name,mobile,email;
    String userid,group,role,username,password,salt,shop_name_slug,shop_logo;





    private static final String ROOT_URL = "http://offermachi.in/api/retailer_signup";


    public RegistrationStoreDetailsFrgment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.retalier_store_activity, container, false);

        Button registerbutton=(Button)view.findViewById(R.id.registerbutton_button_id);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RetalierDashboard.class);
                startActivity(intent);
            }
        });



















        if (SharedPrefManagerLogin.getInstance(getContext()).isLoggedIn()) {
            startActivity(new Intent(getContext(), RetalierDashboard.class));
        }

        UserModel user = SharedPrefManagerLogin.getInstance(getContext()).getUser();
        name=user.getName();
        mobile=user.getPhone();
        email=user.getEmail();


        final EditText start_Time = (EditText)view.findViewById(R.id.time_view_start);
        start_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shop_hours ="start";
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        start_Time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        final EditText end_Time = (EditText)view.findViewById(R.id.time_view_end);

        end_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                shop_hours = "end";
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        end_Time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        storename=(EditText)view.findViewById(R.id.store_name_edt_id);
        storecontact=(EditText)view.findViewById(R.id.store_contact_edt_id);
        storeaddress=(EditText)view.findViewById(R.id.store_address_edt_id);
        cityedt=(EditText)view.findViewById(R.id.city_edt_id);
        days=(EditText)view.findViewById(R.id.days_edt_id);
        aboutstore=(EditText)view.findViewById(R.id.about_store_name_id);
        shopimage=(LinearLayout)view.findViewById(R.id.store_image_id);
        registerbutton=(Button)view.findViewById(R.id.registerbutton_button_id);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getContext().getSharedPreferences("prefrence", Context.MODE_PRIVATE);
                String restoredText = sharedPreferences.getString("text", null);
                if (restoredText != null) {
                    name=sharedPreferences.getString("username",null);
                    email = sharedPreferences.getString("email", null);
                    mobile = sharedPreferences.getString("mobile", null);
                }
                Registraionvalid();
            }
        });
        return  view;
    }
    private void Registraionvalid() {
        shop_name = storename.getText().toString();
        if (TextUtils.isEmpty(shop_name)){
            Toast.makeText(getContext(), "Please enter your shop name", Toast.LENGTH_SHORT).show();
            return;
        }
        shop_contact_number = storecontact.getText().toString().trim();
        if (TextUtils.isEmpty(shop_contact_number) || !TextUtils.isDigitsOnly(shop_contact_number) || shop_contact_number.length() < 10 || shop_contact_number.length() > 10) {
            Toast.makeText(getContext(), "Please enter your mobile", Toast.LENGTH_SHORT).show();
            return;
        }
        address = storeaddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getContext(), "Please enter your address", Toast.LENGTH_SHORT).show();
            return;
        }
        city = cityedt.getText().toString();
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(getContext(), "Please enter your city", Toast.LENGTH_SHORT).show();
            return;
        }
        about_store= aboutstore.getText().toString();
        if (TextUtils.isEmpty(about_store)) {
            Toast.makeText(getContext(), "Please enter your About Store", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(getContext(), "Register", Toast.LENGTH_SHORT).show();
            registraionParse();
        }
    }
    private void registraionParse() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (null == response || response.length() == 0) {
                            Toast.makeText(getContext(), "No Response", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                String status=obj.getString("status");
                                String message=obj.getString("message");
                                if (status.equals("Success")) {
                                    JSONObject  obj1 = obj.getJSONObject("result");

                                    UserModel user = new UserModel(
                                            obj1.getInt("id"),
                                            obj1.getString("username"),
                                            obj1.getString("email"),
                                            obj1.getString("mobile"),
                                            obj1.getString("shop_name"),
                                            obj1.getString("shop_day_hours"),
                                            obj1.getString("address"),
                                            obj1.getString("shop_contact_number"),
                                            obj1.getString("about_store"),
                                            obj1.getString("city")
                                    );
                                    SharedPrefManagerLogin.getInstance(getContext()).userLogin(user);
                                    startActivity(new Intent(getContext(), RetalierDashboard.class));

                                } else {
                                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", name);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("shop_name", shop_name);
                params.put("shop_contact_number",shop_contact_number);
                params.put("address",address);
                params.put("city",city);
                params.put("shop_logo",shop_logo);
                params.put("about_store",about_store);
                Log.e("surbhi","params"+params);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}


