package com.danigelabert.cyclingclimbs.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.danigelabert.cyclingclimbs.R
import com.danigelabert.cyclingclimbs.databinding.ActivityMapBinding
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapActivity : AppCompatActivity() {

    lateinit var mMap: MapView
    lateinit var controller: IMapController
    lateinit var mMyLocationOverlay: MyLocationNewOverlay
    lateinit var mapListener: MapListener

    private val defaultLocation = GeoPoint(41.978362, 2.817863)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        mMap = binding.osmmap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())

        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        controller = mMap.controller

        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.runOnFirstFix {
            runOnUiThread {
                controller.setCenter(mMyLocationOverlay.myLocation)
                controller.animateTo(mMyLocationOverlay.myLocation)
            }
        }

        controller.setZoom(6.0)

        mMap.overlays.add(mMyLocationOverlay)

        mapListener = object : MapListener {
            override fun onScroll(event: ScrollEvent?): Boolean {
                // Implement your logic here
                return false
            }

            override fun onZoom(event: ZoomEvent?): Boolean {
                return false
            }
        }

        mMap.addMapListener(mapListener)

        if (mMyLocationOverlay.lastFix == null) {
            controller.setCenter(defaultLocation)
            controller.setZoom(10.0)
        }

        val poiItems = mutableListOf<OverlayItem>()
        poiItems.add(OverlayItem("Els Angels", "11km / 3.9% / 434m", GeoPoint(41.960267, 2.848554)))
        poiItems.add(OverlayItem("Cladells", "20km / 3.4% / 676m", GeoPoint(41.874848, 2.633904)))
        poiItems.add(OverlayItem("Turó de l'Home", "25.6km / 5.8% / 1493m", GeoPoint(41.756877, 2.453083)))
        poiItems.add(OverlayItem("Vallter", "12.1km / 7.3% / 886m", GeoPoint(42.379460, 2.301829)))
        poiItems.add(OverlayItem("Collada de Toses", "21.3km / 3.2% / 687m", GeoPoint(42.309170, 2.161208)))
        poiItems.add(OverlayItem("St. Martí Sacalm", "8.3km / 7.3% / 606m", GeoPoint(42.011760, 2.595772)))
        poiItems.add(OverlayItem("RocaCorba", "12.3km / 6.5% / 797m", GeoPoint(42.097968, 2.741137)))

        val poiMarkers = ItemizedIconOverlay(this, poiItems, object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
            override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                val intent = Intent(this@MapActivity, DetallePuntoActivity::class.java)
                intent.putExtra("titulo", item?.title)
                intent.putExtra("descripcion", item?.snippet)
                startActivity(intent)
                return true
            }

            override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                return false
            }
        })

        mMap.overlays.add(poiMarkers)
    }
}
