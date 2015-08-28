package com.example.android.ccontrol;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //Geo Car position
    public LatLng geoCarPosition = new LatLng(3.186816, 101.704833);
    //Zone C position
    //Zone B position
    public LatLng geoZoneB = new LatLng(3.174537, 101.604095);


    public float zoomTrackCar = 10;
    public static float polygonPoints = 10;

    public LatLng lowspeedreg1;
    public LatLng lowspeedreg2;
    public LatLng lowspeedreg3;
    public LatLng lowspeedreg4;
    public LatLng highspeedreg6;

    //Zone E
    public LatLng zone1;
    public LatLng zone2;
    public LatLng zone3;
    public LatLng zoneEcent;


    //Zone


    public float speed_limit = 40;
    public float zone_c_speed_limit = 70;

    public void Fence_high_Speed() {

        highspeedreg6 = new LatLng(3.112158, 101.713027);

    }

    public void Fence_Low_Speed() {
        lowspeedreg1 = new LatLng(3.196678, 101.724074);//RoyalSelangor
        lowspeedreg2 = new LatLng(3.162570, 101.691458);//Jln Tun Razak
        lowspeedreg3 = new LatLng(3.184337, 101.662962);//Duta Mas

    }

    public void raining_zone() {
        zone1 = new LatLng(3.087910, 101.606761);
        zone2 = new LatLng(2.974047, 101.724375);
        zone3 = new LatLng(2.913469, 101.625501);
        zoneEcent = new LatLng(3.010157, 101.654735);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Fence_Low_Speed();
        Fence_high_Speed();
        raining_zone();
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        //  MarkerOptions snippet = new MarkerOptions().position(geoCarPosition).title("E33").snippet("Speed currently limited to :" + speed_stringlm + " km/hr");//add marker
        SetUpCurrentMarker(geoCarPosition, "E33", speed_limit);
        SetUpRainingMarker(zoneEcent, "Bewarn Raining Ahead", 50);
        RegionalMarker(highspeedreg6, "Zone C", 70);
        RegionalMarker(geoZoneB, "Zone Shower Activity", 90);
        //drawPolyLines();
        //Draw polygons
        drawPolyGons(100, 255, 251, 0, Color.YELLOW, lowspeedreg2, lowspeedreg1, lowspeedreg3, lowspeedreg2);
        drawPolyGons(100, 0, 214, 182, Color.CYAN, zone1, zone2, zone3, zone1);
        drawCircle(highspeedreg6, 6000, Color.BLUE, 100, 31, 34, 183);
        drawCircle(geoZoneB, 7000, Color.GREEN, 100, 0, 214, 14);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoCarPosition, zoomTrackCar));//move marker
    }

    private void SetUpCurrentMarker(LatLng pos, String title, float speedlm) {
        String speed_stringlm = Float.toString(speedlm);
        mMap.addMarker(new MarkerOptions().position(pos).title(title).snippet("Speed currently limited to :" + speed_stringlm + " km/hr")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.proton2));//add marker
    }

    private void SetUpRainingMarker(LatLng pos, String title, float speedlm) {
        String speed_stringlm = Float.toString(speedlm);
        mMap.addMarker(new MarkerOptions().position(pos).title(title).snippet("Speed currently limited to :" + speed_stringlm + " km/hr")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.rain_showers2));//add marker
    }

    private void RegionalMarker(LatLng pos, String title, float speedlm) {
        String speed_stringlm = Float.toString(speedlm);
        mMap.addMarker(new MarkerOptions().position(pos).title(title).snippet("Speed currently limited to :" + speed_stringlm + " km/hr"));//add marker
    }

    private void drawPolyLines() {
        PolylineOptions regionalLine = new PolylineOptions().add(lowspeedreg2).add(lowspeedreg1).add(lowspeedreg3).add(lowspeedreg2).width(10).color(Color.YELLOW).geodesic(true);
        mMap.addPolyline(regionalLine);

    }

    private void drawPolyGons(int a, int r, int g, int b, int Stroke, LatLng a1, LatLng a2, LatLng a3, LatLng a4) {
        PolygonOptions polygonOptions = new PolygonOptions().fillColor(Color.argb(a, r, g, b)).strokeWidth(3).strokeColor(Stroke).add(a1).add(a2).add(a3).add(a4);
        mMap.addPolygon(polygonOptions);

    }

    private void drawCircle(LatLng circ, int rad, int col, int a, int r, int g, int b) {
        CircleOptions circleOptions = new CircleOptions().center(circ).radius(rad).strokeColor(col).fillColor(Color.argb(a, r, g, b)).strokeWidth(3);

        mMap.addCircle(circleOptions);


    }

    private void InfoMap() {

    }

}
