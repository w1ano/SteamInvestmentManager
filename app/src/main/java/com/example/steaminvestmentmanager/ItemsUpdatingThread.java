package com.example.steaminvestmentmanager;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.steaminvestmentmanager.utilclasses.*;
import com.google.gson.Gson;

public class ItemsUpdatingThread extends Thread {
    private SteamItem[] steamItems;
    private boolean isFirstRun = true;
    private Gson gson = new Gson();

    @Override
    public void run() {
        while (!isInterrupted()) {
            SteamGetURLCreator steamGetURLCreator = new SteamGetURLCreator();
            steamItems = MainActivity.getSteamItems();
            if (steamItems != null) {
                for (int i = 0; i < steamItems.length; i++) {
                    if (!CurrencyData.getCurrencyChar().equals(CurrencyData.getSpecificCurrencyChar(steamItems[i].getFirstInitializationCurrency()))) {
                        String priceoverviewURL = steamGetURLCreator.getURL(steamItems[i], true);
                        String jsonPriceoverviewSteamItem = new DownloadingPageHtmlCode(priceoverviewURL).call();
                        PriceoverviewSteamItem priceoverviewSteamItem = gson.fromJson(jsonPriceoverviewSteamItem, PriceoverviewSteamItem.class);
                        if (priceoverviewSteamItem.isSuccess()) {
                            steamItems[i].setLowest_price(priceoverviewSteamItem.getLowest_price());
                        }
                        String priceoverviewRightCurrencyURL = steamGetURLCreator.getURL(steamItems[i], true);
                        String jsonPriceoverviewSteamItemRightCurrency = new DownloadingPageHtmlCode(priceoverviewRightCurrencyURL).call();
                        PriceoverviewSteamItem priceoverviewRightCurrencySteamItem = gson.fromJson(jsonPriceoverviewSteamItemRightCurrency, PriceoverviewSteamItem.class);
                        if (priceoverviewRightCurrencySteamItem.isSuccess()) {
                            steamItems[i].setFirstInitializationCurrencyLowestPrice(priceoverviewSteamItem.getLowest_price());
                        }
                    }else {
                        String priceoverviewURL = steamGetURLCreator.getURL(steamItems[i], true);
                        String jsonPriceoverviewSteamItem = new DownloadingPageHtmlCode(priceoverviewURL).call();
                        PriceoverviewSteamItem priceoverviewSteamItem = gson.fromJson(jsonPriceoverviewSteamItem, PriceoverviewSteamItem.class);
                        if (priceoverviewSteamItem.isSuccess()) {
                            steamItems[i].setLowest_price(priceoverviewSteamItem.getLowest_price());
                            steamItems[i].setFirstInitializationCurrencyLowestPrice(priceoverviewSteamItem.getLowest_price());
                        }
                    }
                    System.out.println(steamItems[i].getFirstInitializationCurrencyLowestPrice() + " " + steamItems[i].getLowest_price());
                }
            }else {
                System.out.println("Error");
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
