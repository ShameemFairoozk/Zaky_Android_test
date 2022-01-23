package com.example.zaky_android_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.zaky_android_test.models.CategoryAdapter;
import com.example.zaky_android_test.models.CategoryGS;
import com.example.zaky_android_test.models.ItemListGS;
import com.example.zaky_android_test.models.ModelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity{
    GridLayoutManager categoryManager, manager,SubCategoryManager;
    RecyclerView CategoryRecycleView;
    RecyclerView SubCategoryRecycleView;
    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    CategoryAdapter subCategoryAdapter;
    ModelAdapter modelAdapter;
    ArrayList<ItemListGS> itemListGS = new ArrayList<>();
    ArrayList<CategoryGS> categoryGSs = new ArrayList<>();
    ArrayList<CategoryGS> SubCategoryGSs = new ArrayList<>();

    Boolean isLoading=false;
    Integer offset=0;
    GifImageView pb_loading;

    int CurrentCategoryPosition = 0;
    int CurrentSubCategoryPosition = 0;
    String categoryID="0";
    String subCategoryID="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CategoryRecycleView = findViewById(R.id.categoryRecyclerView);
        SubCategoryRecycleView = findViewById(R.id.subcategoryRecyclerView);
        recyclerView = findViewById(R.id.recyclerView);
        pb_loading = findViewById(R.id.pb_loading);
        categoryManager = new GridLayoutManager(this, 1);
        categoryManager.setOrientation(RecyclerView.HORIZONTAL);
        SubCategoryManager = new GridLayoutManager(this, 1);
        SubCategoryManager.setOrientation(RecyclerView.HORIZONTAL);
        manager = new GridLayoutManager(this, 1);
        manager.setOrientation(RecyclerView.VERTICAL);
        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="https://sta.farawlah.sa/api/mobile/categories";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray array  = new JSONArray(response);
                            CategoryGS s =new CategoryGS();
                            s.setId("0");
                            s.setCategoryName("All");
                            s.setIsSelected("True");

                            categoryGSs.add(s);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject itemObject = array.getJSONObject(i);
                                CategoryGS categoryGs =new CategoryGS();
                                categoryGs.setId(itemObject.getString("id"));
                                categoryGs.setCategoryName(itemObject.getString("name"));
                                categoryGs.setIsSelected("False");

                                categoryGSs.add(categoryGs);
                            }
                            categoryAdapter = new CategoryAdapter(categoryGSs, MainActivity.this);
                            CategoryRecycleView.setLayoutManager(categoryManager);
                            CategoryRecycleView.setAdapter(categoryAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);


        fetchData(offset,categoryID,subCategoryID);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)  && !isLoading) {
                    offset=offset+1;
                    isLoading = true;
                    pb_loading.setVisibility(View.VISIBLE);
                    fetchData(offset,categoryID,subCategoryID);
                }
            }
        });

        CategoryRecycleView.addOnItemTouchListener(new CategoryAdapter.RecyclerItemClickListener(this, new CategoryAdapter.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                 String id=categoryGSs.get(position).getId();
                 categoryID=id;
                CurrentCategoryPosition = position;
                int j = 0;
                for (CategoryGS cat : categoryGSs) {

                    if (j == CurrentCategoryPosition) {
                        cat.setIsSelected("True");
                    } else {
                        cat.setIsSelected("False");
                    }
                    j++;


                }
                categoryAdapter.notifyDataSetChanged();

                offset=0;
                subCategoryID="0";
                isLoading = true;
                pb_loading.setVisibility(View.VISIBLE);
                itemListGS.clear();
                fetchData(offset,categoryID,subCategoryID);
                fetchSubCategory(id);


            }
        }));
        SubCategoryRecycleView.addOnItemTouchListener(new CategoryAdapter.RecyclerItemClickListener(this, new CategoryAdapter.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id=SubCategoryGSs.get(position).getId();
                subCategoryID=id;
                CurrentSubCategoryPosition = position;
                int j = 0;
                for (CategoryGS cat : SubCategoryGSs) {

                    if (j == CurrentSubCategoryPosition) {
                        cat.setIsSelected("True");
                    } else {
                        cat.setIsSelected("False");
                    }
                    j++;


                }
                subCategoryAdapter.notifyDataSetChanged();
                isLoading = true;
                pb_loading.setVisibility(View.VISIBLE);
                offset=0;
                itemListGS.clear();
                fetchData(offset,categoryID,subCategoryID);


            }
        }));



    }

    private void fetchSubCategory(String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://sta.farawlah.sa/api/mobile/subcategories?parent_id="+id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray array  = new JSONArray(response);
                            SubCategoryGSs.clear();
                            CategoryGS s =new CategoryGS();
                            s.setId("0");
                            s.setCategoryName("All");
                            s.setIsSelected("True");
                            SubCategoryGSs.add(s);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject itemObject = array.getJSONObject(i);
                                CategoryGS categoryGs =new CategoryGS();
                                categoryGs.setId(itemObject.getString("id"));
                                categoryGs.setCategoryName(itemObject.getString("name"));
                                categoryGs.setIsSelected("False");

                                SubCategoryGSs.add(categoryGs);
                            }
                            subCategoryAdapter = new CategoryAdapter(SubCategoryGSs, MainActivity.this);
                            SubCategoryRecycleView.setLayoutManager(SubCategoryManager);
                            SubCategoryRecycleView.setAdapter(subCategoryAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }


    private void fetchData(Integer offset,String categoryID,String subCategoryID) {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="https://sta.farawlah.sa/api/mobile/products?parent_category_id="+categoryID+"&category_id="+subCategoryID+"&store_id=2&offset="+offset.toString()+"&limit=20&sort_by=sale_price&sort_type=DESC";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array  = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject itemObject = array.getJSONObject(i);
                                ItemListGS e=new ItemListGS();
                                e.setItemName(itemObject.getString("name"));
                                e.setItemRate(itemObject.getString("sort_price"));

                                itemListGS.add(e);

                            }
                            isLoading = false;
                            pb_loading.setVisibility(View.GONE);
                            if (offset==0){
                                modelAdapter = new ModelAdapter(itemListGS, MainActivity.this);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(modelAdapter);
                            }else{
                                modelAdapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);
    }


}