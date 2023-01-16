package com.soham.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.soham.unitconverter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.calculateButton.setOnClickListener(v -> {
            short from = (short) binding.toUnit.getSelectedItemId();
            short to = (short) binding.fromUnit.getSelectedItemId();
            double ans = Double.parseDouble(binding.inputValue.getText().toString())*Math.pow(10,to-from);
            binding.calculateButton.setText(String.format("%.4f",ans));
        });
    }
}