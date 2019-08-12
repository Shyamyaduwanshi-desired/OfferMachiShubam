package com.desired.offermachi.retalier.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.desired.offermachi.retalier.constant.AppData;
import com.desired.offermachi.retalier.model.BrandModel;
import com.desired.offermachi.retalier.model.CategoryModel;
import com.desired.offermachi.retalier.model.OfferTypeModel;
import com.desired.offermachi.retalier.model.ViewOfferModel;
import com.desired.offermachi.retalier.view.activity.RetalierOtpActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TypeBrandCategoryPresenter {
    private Context context;
    private TypeBrandCategory typeBrandCategory;

    public TypeBrandCategoryPresenter(Context context, TypeBrandCategory typeBrandCategory) {
        this.context = context;
        this.typeBrandCategory = typeBrandCategory;
    }

    public interface TypeBrandCategory {
        void successtype(ArrayList<OfferTypeModel> response);

        void successbrand(ArrayList<BrandModel> response);

        void successcategory(ArrayList<CategoryModel> response);

        void error(String response);

        void fail(String response);
    }

    public void sentRequest() {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Please Wait..");
        progress.setCancelable(false);
        progress.show();
        final ArrayList<OfferTypeModel> offertype= new ArrayList<OfferTypeModel>();
        final ArrayList<BrandModel> brandname= new ArrayList<BrandModel>();
        final ArrayList<CategoryModel> category= new ArrayList<CategoryModel>();
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppData.url + "select_all_offer_types", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.dismiss();
                try {
                    JSONObject reader = new JSONObject(response);
                    int status = reader.getInt("status");
                    if (status == 200) {
                        offertype.clear();
                        brandname.clear();
                        category.clear();

                        OfferTypeModel offerTypeModel1 = new OfferTypeModel(
                               "0",
                                "Select Offer",
                               "0"
                        );
                        offertype.add(offerTypeModel1);
                        CategoryModel categoryModel1=new CategoryModel(
                              "0",
                              "Select Category",
                             "0"
                        );
                        category.add(categoryModel1);
                        BrandModel brandModel1=new BrandModel(
                               "0",
                               "Select Brand",
                               "0"
                        );
                        brandname.add(brandModel1);
                        String result = reader.getString("result");
                        JSONObject jsonObject = new JSONObject(result);
                        String offer_types=jsonObject.getString("offer_types");
                        String offer_categories=jsonObject.getString("offer_categories");
                        String offer_brands=jsonObject.getString("offer_brands");
                        Log.e("fetchdata", "offer_types ="+offer_types );
                        JSONArray jsonArray=new JSONArray(offer_types);
                        JSONObject object;
                        for (int count = 0; count < jsonArray.length(); count++) {
                            object = jsonArray.getJSONObject(count);
                            OfferTypeModel offerTypeModel = new OfferTypeModel(
                                    object.getString("id"),
                                    object.getString("offer_type"),
                                     object.getString("status")
                            );

                            offertype.add(offerTypeModel);
                        }
                        typeBrandCategory.successtype(offertype);
                        JSONArray jsonArray2=new JSONArray(offer_categories);
                        JSONObject object2;
                        for (int count = 0; count < jsonArray2.length(); count++) {
                            object2 = jsonArray2.getJSONObject(count);
                            CategoryModel categoryModel=new CategoryModel(
                                    object2.getString("id"),
                            object2.getString("category_name"),
                            object2.getString("status")
                            );
                            category.add(categoryModel);

                        }
                        typeBrandCategory.successcategory(category);
                        JSONArray jsonArray3=new JSONArray(offer_brands);
                        JSONObject object3;
                        for (int count = 0; count < jsonArray3.length(); count++) {
                            object3 = jsonArray3.getJSONObject(count);
                            BrandModel brandModel=new BrandModel(
                                    object3.getString("id"),
                            object3.getString("brand_name"),
                            object3.getString("status")
                            );
                            brandname.add(brandModel);

                        }
                        typeBrandCategory.successbrand(brandname);

                    } else if (status == 404) {
                        typeBrandCategory.error(reader.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    typeBrandCategory.fail("Something went wrong. Please try after some time.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                typeBrandCategory.fail("Server Error.\n Please try after some time.");
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(postRequest);
    }
}