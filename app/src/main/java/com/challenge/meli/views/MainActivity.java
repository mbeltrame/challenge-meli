package com.challenge.meli.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.challenge.meli.R;
import com.challenge.meli.domain.adapters.AdapterProducts;
import com.challenge.meli.domain.interfaces.ListenerProduct;
import com.challenge.meli.views.utils.Constants;
import com.challenge.meli.viewmodels.ProductViewModel;

public class MainActivity extends AppCompatActivity implements ListenerProduct {

    private AdapterProducts adapterProducts;

    private RecyclerView recyclerProducts;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapterProducts = new AdapterProducts(this);
        String nameProduct = getIntent().getExtras().getString(Constants.NAME_PRODUCT);

        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAll(nameProduct).observe(this, products -> {
            progressBar.setVisibility(View.GONE);
            adapterProducts.setProducts(products);
            recyclerProducts.setVisibility(View.VISIBLE);
        });

        initializeComponents();
    }

    private void initializeComponents() {
        progressBar = findViewById(R.id.progressBar);
        recyclerProducts = findViewById(R.id.recycler_product);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerProducts.setAdapter(adapterProducts);
    }

    @Override
    public void selectProduct(String idProduct) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Constants.ID_PRODUCT, idProduct);
        startActivity(intent);
    }
}
