package com.rasset.wflaunch.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.rasset.wflaunch.R
import com.rasset.wflaunch.core.AppConst
import com.rasset.wflaunch.model.DiagnoseInfo
import com.rasset.wflaunch.ui.fragments.BaseFragment
import com.rasset.wflaunch.ui.mapapi.GpsInfo
import com.rasset.wflaunch.ui.mapapi.Item
import com.rasset.wflaunch.ui.mapapi.OnFinishSearchListener
import com.rasset.wflaunch.ui.mapapi.Searcher
import com.rasset.wflaunch.utils.Logger
import com.rasset.wflaunch.utils.showToast
import kotlinx.android.synthetic.main.activity_diagnose_attention.*
import kotlinx.android.synthetic.main.custom_appbarlayout.*
import net.daum.mf.map.api.*
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap


class DiagAttentionActivity : BaseActivity() ,MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.OpenAPIKeyAuthenticationResultListener {
    companion object {

        fun newIntent(context: Context,keyword: String): Intent {
            val intent = Intent(context, DiagAttentionActivity::class.java)
            // TODO 이벤트버스?? 다른방법으로 Object전달 리서치할것
            intent.putExtra("keyword",keyword)
            return intent
        }
    }
    enum class SubFrags(val idx: Int,val title: String, var fragment:BaseFragment?) {
        DIAG_CUSTOER_INFO(0,AppConst.FRAG_NAME_DIAG_CUSTOMER_INFO, null)
        , DIAG_INFO_STEP1(1,AppConst.FRAG_NAME_DIAG_INFO_STEP1, null)
        , DIAG_INFO_STEP2(2,AppConst.FRAG_NAME_DIAG_INFO_STEP2, null)
        , DIAG_COMPLETE(3,AppConst.FRAG_NAME_DIAG_COMPLETED, null)
    }

    internal var isInitialized = true
    lateinit var mMapView: MapView
    private val mTagItemMap = HashMap<Int, Item>()

    internal var apikey = "d70e8a8a32c7ffb6f3c225221c3d4e35"
    internal var query = "음식점"
    internal var latitude = 0.0 // 위도
    internal var longitude = 0.0 // 경도
    internal var radius = 6000 // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
    internal var page = 1 // 페이지 번호 (1 ~ 3). 한페이지에 15개

    internal var spin_mart_filter: Spinner? = null
    // GPSTracker class
    private var gps: GpsInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnose_attention)

        query = intent.getStringExtra("keyword")

        mMapView = MapView(this)
        map_view.addView(mMapView)
//        mMapView.setDaumMapApiKey("d2d578f087b1ac748047106130fe6763")
        mMapView.setMapViewEventListener(this)
        mMapView.setPOIItemEventListener(this)
// 줌 레벨 변경
        mMapView.setCalloutBalloonAdapter(CustomCalloutBalloonAdapter())

        TV_APPBAR_TEXT.append(" : $query")
    }

    private fun startGoogleMap (){
        val uri = Uri.parse("daummaps://search?q=$query&p=$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    @Synchronized
    private fun doSearcher() {

        val searcher = Searcher() // net.daum.android.map.openapi.search.Searcher
        searcher.searchKeyword(applicationContext, query, latitude, longitude, radius, page, apikey, object : OnFinishSearchListener {
            override  fun onSuccess(itemList: List<Item>) {
                mMapView.removeAllPOIItems() // 기존 검색 결과 삭제
                    showResult(itemList) // 검색 결과 보여줌
            }

            override fun onFail() {
                map_view.post{
                    showToast {
                        "검색을 실패했습니다."
                    }
                }
            }
        })

    }

    internal inner class CustomCalloutBalloonAdapter : CalloutBalloonAdapter {

        private val mCalloutBalloon: View

        init {
            mCalloutBalloon = layoutInflater.inflate(R.layout.custom_callout_balloon, null)
        }

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View? {
            if (poiItem == null) return null
            val item = mTagItemMap[poiItem.tag] ?: return null
            val imageViewBadge = mCalloutBalloon.findViewById(R.id.badge) as ImageView
            val textViewTitle = mCalloutBalloon.findViewById(R.id.title) as TextView
            textViewTitle.text = item.title
            val textViewDesc = mCalloutBalloon.findViewById(R.id.desc) as TextView
            textViewDesc.text = item.address

            TV_LABEL_NAME.post {
                TV_VALUE_TXT.text = item.title
                TV_VALUE_PHONE.text = item.phone
            }
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem): View? {
            return null
        }

    }

    private fun showResult(itemList: List<Item>) {
        val mapPointBounds = MapPointBounds()

        // TODO 이마트 , 홈플러스 , 롯데마트 , 코스트코
        // 이외 다른 검색된것들 제외.  클릭할대 체크하여 넘길수있는 지점만 디테일화면 이동.
        for (i in itemList.indices) {
            val item = itemList[i]
            if (item.title != null && item.title.contains(query)
                    && item.category.contains("음식점")) {

                val poiItem = MapPOIItem()
                poiItem.itemName = item.title
                poiItem.tag = i
                val mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude)
                poiItem.mapPoint = mapPoint
                mapPointBounds.add(mapPoint)
                poiItem.markerType = MapPOIItem.MarkerType.CustomImage
                poiItem.customImageResourceId = R.drawable.map_pin_blue
                poiItem.selectedMarkerType = MapPOIItem.MarkerType.CustomImage
                poiItem.customSelectedImageResourceId = R.drawable.map_pin_red
                poiItem.isCustomImageAutoscale = true
                poiItem.setCustomImageAnchor(0.5f, 1.0f)

                mMapView.addPOIItem(poiItem)
                mTagItemMap.put(poiItem.tag, item)
            }
        }

        //            mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));


        if (isInitialized) {
            isInitialized = false
            val poiItems = mMapView.getPOIItems()
            if (poiItems.size > 0) {
                mMapView.selectPOIItem(poiItems[0], true)
            }
        }
    }

    private fun createDrawableFromUrl(url: String): Drawable? {
        try {
            val `is` = this.fetch(url) as InputStream
            return Drawable.createFromStream(`is`, "src")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    @Throws(MalformedURLException::class, IOException::class)
    private fun fetch(address: String): Any {
        val url = URL(address)
        return url.content
    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewInitialized(p0: MapView?) {
        gps = GpsInfo(this@DiagAttentionActivity)
        // GPS 사용유무 가져오기
        gps?.let {
            if (it.isGetLocation) {
                latitude = it.latitude
                longitude = it.longitude
                mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(latitude, longitude), 1, true)
                doSearcher()
//                startGoogleMap()
            } else {
                // GPS 를 사용할수 없으므로
                it.showSettingsAlert()
            }
        }

    }
    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, zoomLevel: Int) {
        Logger.d("MartGPSSearch", "onMapViewZoomLevelChanged zoomLevel $zoomLevel")
    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?, p2: MapPOIItem.CalloutBalloonButtonType?) {
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onDaumMapOpenAPIKeyAuthenticationResult(p0: MapView?, p1: Int, p2: String?) {
    }
}


