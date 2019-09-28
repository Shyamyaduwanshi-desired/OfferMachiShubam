package com.desired.offermachi.customer.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.desired.offermachi.R;
import com.desired.offermachi.customer.view.activity.DashBoardActivity;
import com.desired.offermachi.retalier.model.FAQ;
import com.desired.offermachi.retalier.view.activity.RetalierHelpActivity;
import com.desired.offermachi.retalier.view.adapter.FaqAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HelpFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    private ProgressDialog pDialog;
    private static final String ROOT_URL = "http://offermachi.in/api/help_faq";
    private ArrayList<FAQ> faqList;
//    TextView txtphone,txtemail;
    public HelpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.help_activity, container, false);
        ((DashBoardActivity)getActivity()).setToolTittle("Help & Faqs",2);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        faqList=new ArrayList<>();
        init();
        return  view;
    }

    private void init(){
//        txtemail=view.findViewById(R.id.email);
//        txtphone=view.findViewById(R.id.phonenumber);
        recyclerView=(RecyclerView)view.findViewById(R.id.liveshiplist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(isNetworkConnected()){
            Fetchfaq();
        }else {
            Toast.makeText(getActivity(), "Please connect to internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void Fetchfaq() {
        showpDialog();
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024);

        // Use HttpURLConnection as the HTTP client
        Network network = new BasicNetwork(new HurlStack());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hidepDialog();
                        if (null == response || response.length() == 0) {
                            Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                JSONObject mainJson = new JSONObject(response);
                                String status=mainJson.getString("status");
                                String message=mainJson.getString("message");
                                if (status.equals("200")) {
                                    String result = mainJson.getString("result");
//                                    JSONObject mainJson1 = new JSONObject(result);
//                                    String contact_number=mainJson1.getString("contact_number");
//                                    txtphone.setText(contact_number);
//                                    String contact_email= mainJson1.getString("contact_email");
//                                    txtemail.setText(contact_email);


//                                    String question_answer=mainJson1.getString("question_answer");

                                    JSONArray jsonArray = new JSONArray(result);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jobj = jsonArray.getJSONObject(i);
                                        String id = jobj.getString("id");
                                        String post_title = jobj.getString("post_title");
                                        String post_body = jobj.getString("post_body");
                                        String post_type = jobj.getString("post_type");
                                        String statuss = jobj.getString("status");
                                        FAQ faq = new FAQ(
                                                id,post_title, post_body

                                        );

                                        faqList.add(faq);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            displayData1();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "customer");
                return params;
            }
        };
        RequestQueue queue = new RequestQueue(cache, network);
        queue.start();
        queue.add(stringRequest);

        /*RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);*/
    }
    private void displayData1() {
        FaqAdapter faqAdapter = new FaqAdapter(getContext(),faqList);
        recyclerView.setAdapter(faqAdapter);
        if (faqAdapter.getItemCount() == 0) {
            Toast.makeText(getContext(), "No surbhi", Toast.LENGTH_SHORT).show();
        } else {

        }

    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}



