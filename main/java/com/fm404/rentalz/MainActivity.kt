package com.fm404.rentalz

// import statements enable more functionality of Java/Kotlin to be made available for this Android app

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by macaronlover22 on 02/12/2018
 */

class MainActivity : Activity // ':' enables the subclass to inherit public and protected methods + variables of the superclass. Also allows methods to be overriden (@Override).
(), DatePickerDialog.OnDateSetListener, // listener used to indicate the user has finished selecting a date
        TimePickerDialog.OnTimeSetListener, // listener used to indicate the user has finished selecting a time
        AdapterView.OnItemSelectedListener {  // used to provide callbacks for scenarios that consist of a failed attempt when connecting the user to the service

    // Public variables, are variables that are visible to all classes.
    // Private variables, are visible only to the class to which they belong.
    // Protected variables, are visible only to the class to which they belong, and any subclasses.
    private var checkboxTermsAndConditions: CheckBox? = null // private variable named 'checkboxTermsAndConditions' to represent a CheckBox element
    private var edittextMonthlyRent: EditText? = null // private variable named 'edittextMonthlyRent' to represent an EditText element
    private var edittextDescription: EditText? = null // private variable named 'edittextDescription' to represent an EditText element
    private var edittextReporterName: EditText? = null // private variable named 'edittextReporterName' to represent an EditText element
    private var edittextNearestSchool: EditText? = null // private variable named 'edittextNearestSchool' to represent an EditText element
    private var edittextNearestStation: EditText? = null // private variable named 'edittextNearestStation' to represent an EditText element
    private var imgviewPropertyPhoto: ImageView? = null // private variable named 'imgviewPropertyPhoto' to represent an ImageView element
    private var containerNearestSchools: LinearLayout? = null // private variable named 'containerNearestSchools' to represent a LinearLayout element
    private var containerNearestStations: LinearLayout? = null // private variable named 'containerNearestStations' to represent a LinearLayout element
    private var radiogroupPetsAllowed: RadioGroup? = null // private variable named 'radiogroupPetsAllowed' to represent a RadioGroup element
    private var radiogroupWheelchairAccessible: RadioGroup? = null // private variable named 'radiogroupWheelchairAccessible' to represent a RadioGroup element
    private var seekbarBathrooms: SeekBar? = null // private variable named 'seekbarBathrooms' to represent a SeekBar element
    private var seekbarBedrooms: SeekBar? = null // private variable named 'seekbarBedrooms' to represent a SeekBar element
    private var seekbarEnergyEfficiencyRating: SeekBar? = null // private variable named 'seekbarEnergyEfficiencyRating' to represent a SeekBar element
    private var seekbarEnvironmentalImpactRating: SeekBar? = null // private variable named 'seekbarEnvironmentalImpactRating' to represent a SeekBar element
    private var spinnerPropertyType: Spinner? = null // private variable named 'spinnerPropertyType' to represent a Spinner element
    private var spinnerFurnitureType: Spinner? = null // private variable named 'spinnerFurnitureType' to represent a Spinner element
    private var textviewErrorPropertyType: TextView? = null // private variable named 'textviewErrorPropertyType' to represent a TextView element
    private var textviewErrorBedrooms: TextView? = null // private variable named 'textviewErrorBedrooms' to represent a TextView element
    private var textviewErrorPropertyAdditionDate: TextView? = null // private variable named 'textviewErrorPropertyAdditionDate' to represent a TextView element
    private var textviewErrorPropertyAdditionTime: TextView? = null // private variable named 'textviewErrorPropertyAdditionTime' to represent a TextView element
    private var textviewErrorMonthlyRent: TextView? = null // private variable named 'textviewErrorMonthlyRent' to represent a TextView element
    private var textviewErrorReporterName: TextView? = null // private variable named 'textviewErrorReporterName' to represent a TextView element
    private var textviewPropertyAdditionDate: TextView? = null // private variable named 'textviewPropertyAdditionDate' to represent a TextView element
    private var textviewPropertyAdditionTime: TextView? = null // private variable named 'textviewPropertyAdditionTime' to represent a TextView element
    private var textviewSeekbarBathrooms: TextView? = null // private variable named 'textviewSeekbarBathrooms' to represent a TextView element
    private var textviewSeekbarBedrooms: TextView? = null // private variable named textviewSeekbarBedrooms to represent a TextView element
    private var textviewSeekbarEnergyEfficiencyRating: TextView? = null // private variable named 'textviewSeekbarEnergyEfficiencyRating' to represent a TextView element
    private var textviewSeekbarEnvironmentalImpactRating: TextView? = null // private variable named 'textviewSeekbarEnvironmentalImpactRating' to represent a TextView element
    private var videoviewPropertyVideo: VideoView? = null // private variable named 'videoviewPropertyVideo' to represent a VideoView element


    // required method to adjust status bar background colour
    private fun getFactorColor(color: Int, factor: Float): Int {
        var color = color
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= factor
        color = Color.HSVToColor(hsv)
        return color
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // set content of activity to the ‘activity_main.xml’ layout file

        val toolbarColour = "#0098D4" // light blue colour

        // set status bar background colour
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        getWindow().statusBarColor = getFactorColor(Color.parseColor(toolbarColour), 0.8f)

        // Accessing the action bar
        val actionbar = actionBar

        // Setting up Action bar background colour using # color code.
        actionbar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(toolbarColour)))

        /*
         * start of 'Property type' spinner code
         */
        // 'Property type' Spinner element
        spinnerPropertyType = findViewById(R.id.spinner_propertytype)

        // 'Property type' Spinner click listener
        spinnerPropertyType!!.onItemSelectedListener = this

        // 'Property type' Spinner Drop down elements
        val propertytypeCategories = ArrayList<String>()
        propertytypeCategories.add(getString(R.string.empty_placeholder))
        propertytypeCategories.add(getString(R.string.apartment_flat))
        propertytypeCategories.add(getString(R.string.bungalow))
        propertytypeCategories.add(getString(R.string.chalet))
        propertytypeCategories.add(getString(R.string.cottage))
        propertytypeCategories.add(getString(R.string.duplex))
        propertytypeCategories.add(getString(R.string.garage))
        propertytypeCategories.add(getString(R.string.house))
        propertytypeCategories.add(getString(R.string.maisonette))
        propertytypeCategories.add(getString(R.string.mews))
        propertytypeCategories.add(getString(R.string.penthouse))
        propertytypeCategories.add(getString(R.string.triplex))

        // Creating adapter for spinner
        val ptDataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, propertytypeCategories)

        // Drop down layout style - list view with radio button
        ptDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to spinner
        spinnerPropertyType!!.adapter = ptDataAdapter

        ptDataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        /*
         * end of 'Property type' Spinner code
         */


        /*
         * start of 'Furniture type' spinner code
         */
        // 'Furniture type' Spinner element
        spinnerFurnitureType = findViewById(R.id.spinner_furtnituretype)

        // 'Furniture type' Spinner click listener
        spinnerFurnitureType!!.onItemSelectedListener = this

        // 'Furniture type' Spinner Drop down elements
        val furnituretypeCategories = ArrayList<String>()
        furnituretypeCategories.add(getString(R.string.empty_placeholder))
        furnituretypeCategories.add(getString(R.string.furnished))
        furnituretypeCategories.add(getString(R.string.unfurnished))
        furnituretypeCategories.add(getString(R.string.partfurnished))

        // Creating adapter for Spinner
        val ftDataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, furnituretypeCategories)

        // Drop down layout style - ListView with radio button
        ftDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // attaching data adapter to Spinner
        spinnerFurnitureType!!.adapter = ftDataAdapter

        ftDataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        /*
         * end of 'Furniture type' Spinner code
         */


        val iconFont = FontManager.getTypeface(applicationContext, FontManager.FONTAWESOME) // FontManager class must be accessed first before text views can be set as image views
        val tvimgviewPropertyType = findViewById<TextView>(R.id.imgView_propertytype) // get access to the 'Property type' ImageView (text view using image font)
        tvimgviewPropertyType.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewBedrooms = findViewById<TextView>(R.id.imgView_bedrooms) // get access to the 'Bedrooms' ImageView (text view using image font)
        tvimgviewBedrooms.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewBathrooms = findViewById<TextView>(R.id.imgView_bathrooms) // get access to the 'Bathrooms' ImageView (text view using image font)
        tvimgviewBathrooms.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewPropertyAdditionDate = findViewById<TextView>(R.id.imgView_propertadditiondate) // get access to the 'Property addition date' ImageView (text view using image font)
        tvimgviewPropertyAdditionDate.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewPropertyAdditionTime = findViewById<TextView>(R.id.imgView_propertadditiontime) // get access to the 'Property addition time' ImageView (text view using image font)
        tvimgviewPropertyAdditionTime.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewMonthlyRent = findViewById<TextView>(R.id.imgView_monthlyrent) // get access to the 'Monthly rent' ImageView (text view using image font)
        tvimgviewMonthlyRent.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewFurnitureType = findViewById<TextView>(R.id.imgView_furnituretype) // get access to the 'Furniture type' ImageView (text view using image font)
        tvimgviewFurnitureType.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewNotes = findViewById<TextView>(R.id.imgView_notes) // get access to the 'Notes' ImageView (text view using image font)
        tvimgviewNotes.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewDescription = findViewById<TextView>(R.id.imgView_description) // get access to the 'Property Description' ImageView (text view using image font)
        tvimgviewDescription.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewReporterName = findViewById<TextView>(R.id.imgView_reportername) // get access to the 'Reporter name' ImageView (text view using image font)
        tvimgviewReporterName.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewTermsAndConditions = findViewById<TextView>(R.id.imgView_termsandconditions) // get access to the 'Terms and Conditions' ImageView (text view using image font)
        tvimgviewTermsAndConditions.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewPetsAllowed = findViewById<TextView>(R.id.imgView_petsallowed) // get access to the 'Pets allowed' ImageView (text view using image font)
        tvimgviewPetsAllowed.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewWheelchairAccessible = findViewById<TextView>(R.id.imgView_wheelchairaccessible) // get access to the 'Wheelchair accessible' ImageView (text view using image font)
        tvimgviewWheelchairAccessible.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewEnergyEfficiencyRating = findViewById<TextView>(R.id.imgView_energyefficiencyrating) // get access to the 'Energy efficiency rating' ImageView (text view using image font)
        tvimgviewEnergyEfficiencyRating.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewEnvironmentalImpactRating = findViewById<TextView>(R.id.imgView_environmentalimpactrating) // get access to the 'Environmental impact rating' ImageView (text view using image font)
        tvimgviewEnvironmentalImpactRating.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewPropertyImage = findViewById<TextView>(R.id.imgView_propertyimage) // get access to the 'Property image' ImageView (text view using image font)
        tvimgviewPropertyImage.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewPropertyVideo = findViewById<TextView>(R.id.imgView_propertyvideo) // get access to the 'Property video' ImageView (text view using image font)
        tvimgviewPropertyVideo.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewNearestStations = findViewById<TextView>(R.id.imgView_neareststations) // get access to the 'Nearest stations' ImageView (text view using image font)
        tvimgviewNearestStations.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)
        val tvimgviewNearestSchools = findViewById<TextView>(R.id.imgView_nearestschools) // get access to the 'Nearest schools' ImageView (text view using image font)
        tvimgviewNearestSchools.typeface = iconFont // set typeface of textview to FontAwesome font (to enable use of vector images in TextView)

        val btnAddNearestSchool = findViewById<Button>(R.id.btn_addnearestschool) // get access to the 'Add nearest school' button
        val btnAddNearestStation = findViewById<Button>(R.id.btn_addneareststation) // get access to the 'Add nearest station' button
        val btnBathroomsMinus = findViewById<Button>(R.id.btn_bathroomsminus) // get access to the 'Bathrooms minus' button
        val btnBathroomsPlus = findViewById<Button>(R.id.btn_bathroomsplus) // get access to the 'Bathrooms plus' button
        val btnBedroomsMinus = findViewById<Button>(R.id.btn_bedroomsminus) // get access to the 'Bedrooms minus' button
        val btnBedroomsPlus = findViewById<Button>(R.id.btn_bedroomsplus) // get access to the 'Bedrooms plus' button
        val btnChoosePhoto = findViewById<Button>(R.id.btn_choosephoto) // get access to the 'Choose a photo' button
        val btnChooseVideo = findViewById<Button>(R.id.btn_choosevideo) // get access to the 'Choose a video' button
        val btnEnergyEfficiencyRatingMinus = findViewById<Button>(R.id.btn_energyefficiencyratingminus) // get access to the 'Energy Efficiency Rating minus' button
        val btnEnergyEfficiencyRatingPlus = findViewById<Button>(R.id.btn_energyefficiencyratingplus) // get access to the 'Energy Efficiency Rating plus' button
        val btnEnvironmentalImpactRatingMinus = findViewById<Button>(R.id.btn_environmentalimpactratingminus) // get access to the 'Environmental Impact Rating minus' button
        val btnEnvironmentalImpactRatingPlus = findViewById<Button>(R.id.btn_environmentalimpactratingplus) // get access to the 'Environmental Impact Rating plus' button
        val btnRecordVideo = findViewById<Button>(R.id.btn_recordvideo) // get access to the 'Record a video' button
        val btnRemovePropertyVideo = findViewById<Button>(R.id.btn_removevideo) // get access to the 'Remove location' button
        val btnTakePhoto = findViewById<Button>(R.id.btn_takephoto) // get access to the 'Take a photo' button

        checkboxTermsAndConditions = findViewById(R.id.checkbox_TandCs) // get access to the 'Terms and Conditions' CheckBox
        containerNearestSchools = findViewById(R.id.container_nearestschools) // get access to the 'Nearest school' LinearLayout
        containerNearestStations = findViewById(R.id.container_neareststations) // get access to the 'Nearest stations' LinearLayout
        edittextNearestSchool = findViewById(R.id.editText_nearestschool) // get access to the 'Nearest school' EditText
        edittextNearestStation = findViewById(R.id.editText_neareststation) // get access to the 'Nearest station' EditText
        textviewPropertyAdditionDate = findViewById(R.id.editText_date) // get access to the 'Property Addition Date' EditText
        textviewPropertyAdditionTime = findViewById(R.id.editText_time) // get access to the 'Property Addition Time' EditText
        edittextMonthlyRent = findViewById(R.id.editText_monthlyrent) // get access to the 'Monthly Rent' EditText
        edittextDescription = findViewById(R.id.editText_description) // get access to the 'Notes' EditText
        edittextReporterName = findViewById(R.id.editText_reportername) // get access to the 'Reporter Name' EditText
        imgviewPropertyPhoto = findViewById(R.id.imgView_propertyphoto) // get access to the 'Property photo' ImageView
        radiogroupPetsAllowed = findViewById(R.id.radiogroup_petsallowed) // get access to the 'Pets allowed' RadioGroup
        radiogroupWheelchairAccessible = findViewById(R.id.radiogroup_wheelchairaccessible) // get access to the 'Wheelchair accessible' RadioGroup
        seekbarBathrooms = findViewById(R.id.seekbar_bathrooms) // get access to the 'Bathrooms' SeekBar
        seekbarBedrooms = findViewById(R.id.seekbar_bedrooms) // get access to the 'Bedrooms' SeekBar
        seekbarEnergyEfficiencyRating = findViewById(R.id.seekbar_energyefficiencyrating) // get access to the 'Energy efficiency rating' SeekBar
        seekbarEnvironmentalImpactRating = findViewById(R.id.seekbar_environmentalimpactrating) // get access to the 'Environmental impact rating' SeekBar
        spinnerPropertyType = findViewById(R.id.spinner_propertytype) // get access to the 'Property Type' Spinner
        spinnerFurnitureType = findViewById(R.id.spinner_furtnituretype) // get access to the 'Furniture Type' Spinner
        textviewErrorPropertyType = findViewById(R.id.textViewerror_propertytype) // get access to the 'textViewerror_propertytype' TextView
        textviewErrorBedrooms = findViewById(R.id.textViewerror_bedrooms) // get access to the 'textViewerror_bedrooms' TextView
        textviewErrorPropertyAdditionDate = findViewById(R.id.textViewerror_date) // get access to the 'textViewerror_date' TextView
        textviewErrorPropertyAdditionTime = findViewById(R.id.textViewerror_time) // get access to the 'textViewerror_time' TextView
        textviewErrorMonthlyRent = findViewById(R.id.textViewerror_monthlyrent) // get access to the 'textViewerror_monthlyrent' TextView
        textviewErrorReporterName = findViewById(R.id.textViewerror_reportername) // get access to the 'textViewerror_reportername' TextView
        textviewSeekbarBathrooms = findViewById(R.id.textView_seekbarbathroomcount) // get access to the 'textView_seekbarbathroomcount' TextView
        textviewSeekbarBedrooms = findViewById(R.id.textView_seekbarbedroomcount) // get access to the 'textView_seekbarbedroomcount' TextView
        textviewSeekbarEnergyEfficiencyRating = findViewById(R.id.textView_seekbarenergyefficiencyrating) // get access to the 'textView_seekbarenergyefficiencyrating' TextView
        textviewSeekbarEnvironmentalImpactRating = findViewById(R.id.textView_seekbarenvironmentalimpactrating) // get access to the 'textView_seekbarenvironmentalimpactrating' TextView
        videoviewPropertyVideo = findViewById(R.id.videoView_propertyvideo) // get access to the 'Property video' VideoView

        textviewErrorPropertyType!!.visibility = View.INVISIBLE // make 'textViewerror_propertytype' text view INVISIBLE (not GONE) to keep space between items
        textviewErrorBedrooms!!.visibility = View.INVISIBLE // make 'textViewerror_bedrooms' text view INVISIBLE (not GONE) to keep space between items
        textviewErrorPropertyAdditionDate!!.visibility = View.INVISIBLE // make 'textViewerror_propertytype' text view INVISIBLE (not GONE) to keep space between items
        textviewErrorPropertyAdditionTime!!.visibility = View.INVISIBLE // make 'textviewErrorPropertyAdditionTime' text view INVISIBLE (not GONE) to keep space between items
        textviewErrorMonthlyRent!!.visibility = View.INVISIBLE // make 'textviewErrorMonthlyRent' text view INVISIBLE (not GONE) to keep space between items
        textviewErrorReporterName!!.visibility = View.INVISIBLE // make 'textviewErrorReporterName' text view INVISIBLE (not GONE) to keep space between items

        btnBathroomsMinus.isEnabled = false // disable 'Bathrooms minus' button when activity is first created
        btnBedroomsMinus.isEnabled = false // disable 'Bedrooms minus' button when activity is first created
        btnEnergyEfficiencyRatingMinus.isEnabled = false // disable 'Energy Efficiency Rating minus' button when activity is first created
        btnEnvironmentalImpactRatingMinus.isEnabled = false // disable 'Environmental Impact Rating minus' button when activity is first created
//        imgviewPropertyPhoto.isFocusable = false

        textviewPropertyAdditionDate!!.isFocusable = false
        textviewPropertyAdditionDate!!.isFocusableInTouchMode = false // do nothing when user touches widget on phone with touch screen
        textviewPropertyAdditionDate!!.isClickable = false // do nothing when user navigates with wheel and selects widget

        textviewPropertyAdditionTime!!.isFocusable = false
        textviewPropertyAdditionTime!!.isFocusableInTouchMode = false // do nothing when user touches widget on phone with touch screen
        textviewPropertyAdditionTime!!.isClickable = false // do nothing when user navigates with wheel and selects widget

        textviewSeekbarEnergyEfficiencyRating!!.text = (seekbarEnergyEfficiencyRating!!.progress + 1).toString() + "\n" + getString(R.string.level_g)
        textviewSeekbarEnvironmentalImpactRating!!.text = (seekbarEnvironmentalImpactRating!!.progress + 1).toString() + "\n" + getString(R.string.level_g)

        val seekbarbathroomsinitialValue = seekbarBathrooms!!.progress.toString() // String to represent the progress value of the 'Bathrooms' SeekBar
        val seekbarbedroomsinitialValue = seekbarBedrooms!!.progress.toString() // String to represent the progress value of the 'Bedrooms' SeekBar
        textviewSeekbarBathrooms!!.text = seekbarbathroomsinitialValue // set text of 'textviewSeekbarBathrooms' TextView to the initial progress value of the 'Bathrooms' SeekBar - i.e. 0
        textviewSeekbarBedrooms!!.text = seekbarbedroomsinitialValue // set text of 'textviewSeekbarBedrooms' TextView to the initial progress value of the 'Bedrooms' SeekBar - i.e. 0

        btnTakePhoto.isFocusable = false
        videoviewPropertyVideo!!.isFocusable = false


        // callback to notify the user when the 'Bathrooms' progress level has been changed
        seekbarBathrooms!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                val seekbarbathroomsValue = progress.toString()
                textviewSeekbarBathrooms!!.text = seekbarbathroomsValue

                when (progress) {
                    0 -> btnBathroomsMinus.isEnabled = false
                    1 -> btnBathroomsMinus.isEnabled = true
                    3 -> btnBathroomsPlus.isEnabled = false
                    else -> {
                        btnBathroomsMinus.isEnabled = true
                        btnBathroomsPlus.isEnabled = true
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        // callback to notify the user when the 'Bedrooms' progress level has been changed
        seekbarBedrooms!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                val seekbarbedroomsValue = progress.toString()
                val seekbarbedroomsValueMinus1 = (progress - 1).toString()
                textviewSeekbarBedrooms!!.text = seekbarbedroomsValue

                when (progress) {
                    0 -> btnBedroomsMinus.isEnabled = false
                    1 -> {
                        textviewSeekbarBedrooms!!.text = getString(R.string.studio)
                        btnBedroomsMinus.isEnabled = true
                    }
                    9 -> {
                        btnBedroomsPlus.isEnabled = false
                        textviewSeekbarBedrooms!!.text = seekbarbedroomsValueMinus1
                    }
                    else -> {
                        btnBedroomsMinus.isEnabled = true
                        btnBedroomsPlus.isEnabled = true
                        textviewSeekbarBedrooms!!.text = seekbarbedroomsValueMinus1
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        // callback to notify the user when the 'Energy efficiency rating' progress level has been changed
        seekbarEnergyEfficiencyRating!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                when (progress) {
                    0 -> {
                        textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_g)
                        btnEnergyEfficiencyRatingMinus.isEnabled = false
                    }
                    in 1..19 -> {
                        textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_g)
                        btnEnergyEfficiencyRatingMinus.isEnabled = true
                    }
                    in 20..37 -> textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_f)
                    in 38..53 -> textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_e)
                    in 54..67 -> textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_d)
                    in 68..79 -> textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_c)
                    in 80..90 -> textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_b)
                    in 91..98 -> {
                        textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_a)
                        btnEnergyEfficiencyRatingPlus.isEnabled = true
                    }
                    99 -> {
                        textviewSeekbarEnergyEfficiencyRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_a)
                        btnEnergyEfficiencyRatingPlus.isEnabled = false
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        // callback to notify the user when the 'Environmental impact rating' progress level has been changed
        seekbarEnvironmentalImpactRating!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, b: Boolean) {
                when (progress) {
                    0 -> {
                        textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_g)
                        btnEnvironmentalImpactRatingMinus.isEnabled = false
                    }
                    in 1..19 -> {
                        textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_g)
                        btnEnvironmentalImpactRatingMinus.isEnabled = true
                    }
                    in 20..37 -> textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_f)
                    in 38..53 -> textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_e)
                    in 54..67 -> textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_d)
                    in 68..79 -> textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_c)
                    in 80..90 -> textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_b)
                    in 91..98 -> {
                        textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_a)
                        btnEnvironmentalImpactRatingPlus.isEnabled = true
                    }
                    99 -> {
                        textviewSeekbarEnvironmentalImpactRating!!.text = (progress + 1).toString() + "\n" + getString(R.string.level_a)
                        btnEnvironmentalImpactRatingPlus.isEnabled = false
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        // click event for 'bathrooms minus' button
        btnBathroomsMinus.setOnClickListener {
            // perform all of the following functions when the 'bathrooms minus' button is clicked
            if (seekbarBathrooms!!.progress == 0) {
                btnBathroomsMinus.isEnabled = false // disable the 'btnBathroomsMinus' button
                seekbarBathrooms!!.progress = seekbarBathrooms!!.progress - 1 // decrement the 'seekbarBathrooms' SeekBar value by 1
            } else { // otherwise do the following
                btnBathroomsMinus.isEnabled = true // enable the 'btnBathroomsMinus' button
                btnBathroomsPlus.isEnabled = true // enable the 'btnBathroomsPlus' button
                seekbarBathrooms!!.progress = seekbarBathrooms!!.progress - 1 // decrement the 'seekbarBathrooms' SeekBar value by 1
            }
        }


        // click event for 'bathrooms plus' button
        btnBathroomsPlus.setOnClickListener {
            // perform all of the following functions when the 'bathrooms plus' button is clicked
            if (seekbarBathrooms!!.progress == 3) {
                seekbarBathrooms!!.progress = seekbarBathrooms!!.progress + 1 // increment the 'seekbarBathrooms' SeekBar value by 1
                btnBathroomsPlus.isEnabled = false // disable the 'btnBathroomsPlus' button
            } else { // otherwise do the following
                btnBathroomsMinus.isEnabled = true // enable the 'btnBathroomsMinus' button
                btnBathroomsPlus.isEnabled = true // enable the 'btnBathroomsPlus' button
                seekbarBathrooms!!.progress = seekbarBathrooms!!.progress + 1 // increment the 'seekbarBathrooms' SeekBar value by 1
            }
        }


        // click event for 'bedrooms minus' button
        btnBedroomsMinus.setOnClickListener {
            // perform all of the following functions when the 'bedrooms minus' button is clicked
            if (seekbarBedrooms!!.progress == 1) {
                btnBedroomsMinus.isEnabled = false // disable the 'btnBathroomsMinus' button
                seekbarBedrooms!!.progress = seekbarBedrooms!!.progress - 1 // decrement the 'seekbarBedrooms' SeekBar value by 1
            } else { // otherwise do the following
                btnBedroomsMinus.isEnabled = true // enable the 'btnBathroomsMinus' button
                btnBedroomsPlus.isEnabled = true // enable the 'btnBathroomsPlus' button
                seekbarBedrooms!!.progress = seekbarBedrooms!!.progress - 1 // decrement the 'seekbarBedrooms' SeekBar value by 1
            }
        }


        // click event for 'bedrooms plus' button
        btnBedroomsPlus.setOnClickListener {
            // perform all of the following functions when the 'bedrooms plus' button is clicked
            if (seekbarBedrooms!!.progress == 8) {
                btnBedroomsPlus.isEnabled = false // disable the 'btnBathroomsPlus' button
                seekbarBedrooms!!.progress = seekbarBedrooms!!.progress + 1 // increment the 'seekbarBedrooms' SeekBar value by 1
            } else {
                btnBedroomsMinus.isEnabled = true // enable the 'btnBathroomsMinus' button
                btnBedroomsPlus.isEnabled = true // enable the 'btnBathroomsPlus' button
                seekbarBedrooms!!.progress = seekbarBedrooms!!.progress + 1 // increment the 'seekbarBedrooms' SeekBar value by 1
            }
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // click event for 'choose date' button
        val btnChooseDate = this.findViewById<Button>(R.id.btn_choosedate)
        btnChooseDate.setOnClickListener {
            // perform all of the following functions when the 'choose date' button is clicked
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view: DatePicker?, mYear: Int, mMonth: Int, mDay: Int ->
                textviewPropertyAdditionDate!!.setText(""+ mDay +"/"+ mMonth +"/"+ mYear)
            }, year, month, day)

            // show the DatePickerDialog
            dpd.show()
        }



        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // click event for 'choose time' button
        val btnChooseTime = this.findViewById<Button>(R.id.btn_choosetime)
        btnChooseTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textviewPropertyAdditionTime!!.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
//            // perform all of the following functions when the 'choose time' button is clicked
////            val timePicker = TimePickerFragment()
////            timePicker.show(fragmentManager, "time picker")
//
//            // perform all of the following functions when the 'choose date' button is clicked
//            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, mHourOfDay, mMinute ->
//                textviewPropertyAdditionTime!!.setText(mHourOfDay +":"+ mMinute)
//            }, hour, minute)
//
//            // show to DatePickerDialog
//            tpd.show()
        }


        // click event for 'Energy efficiency rating minus' button
        btnEnergyEfficiencyRatingMinus.setOnClickListener {
            // perform all of the following functions when the 'Energy efficiency rating minus' button is clicked
            if (seekbarEnergyEfficiencyRating!!.progress == 1) {
                seekbarEnergyEfficiencyRating!!.progress = seekbarEnergyEfficiencyRating!!.progress - 1
                btnEnergyEfficiencyRatingMinus.isEnabled = false
            } else {
                btnEnergyEfficiencyRatingMinus.isEnabled = true
                btnEnergyEfficiencyRatingPlus.isEnabled = true
                seekbarEnergyEfficiencyRating!!.progress = seekbarEnergyEfficiencyRating!!.progress - 1
            }
        }


        // click event for 'Energy efficiency rating plus' button
        btnEnergyEfficiencyRatingPlus.setOnClickListener {
            // perform all of the following functions when the 'Energy efficiency rating plus' button is clicked
            seekbarEnergyEfficiencyRating!!.progress = seekbarEnergyEfficiencyRating!!.progress + 1
        }


        // click event for 'Environmental Impact Rating minus' button
        btnEnvironmentalImpactRatingMinus.setOnClickListener {
            // perform all of the following functions when the 'Energy efficiency rating minus' button is clicked
            seekbarEnvironmentalImpactRating!!.progress = seekbarEnvironmentalImpactRating!!.progress - 1
        }


        // click event for 'Environmental Impact Rating plus' button
        btnEnvironmentalImpactRatingPlus.setOnClickListener {
            // perform all of the following functions when the 'Energy efficiency rating plus' button is clicked
            seekbarEnvironmentalImpactRating!!.progress = seekbarEnvironmentalImpactRating!!.progress + 1
        }


        // click event for 'Take a photo' button
        btnTakePhoto.setOnClickListener {
            // perform all of the following functions when the 'Take a photo' button is clicked
            if (imgviewPropertyPhoto!!.drawable != null) {
                // Show dialog if image exists in image view
                val adbT = AlertDialog.Builder(this@MainActivity)
                adbT.setTitle(getString(R.string.take_a_photo)) // Title of alert dialog
                adbT.setIcon(android.R.drawable.ic_dialog_alert) // Icon of alert dialog (warning sign)
                adbT.setCancelable(false) // Prevent user from tapping outside dialog to cancel
                adbT.setMessage(getString(R.string.replacephoto_warning)) // Message of alert dialog

                adbT.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    //                        imgviewPropertyPhoto.setVisibility(View.GONE); // Remove image from 'Property Photo' ImageView

                    val photoCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(photoCaptureIntent, REQUEST_PHOTO_CAPTURE)
                }

                adbT.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss() // Dismiss the dialog
                }

                adbT.show() // Show the alert dialog
            } else {
                val photoCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(photoCaptureIntent, REQUEST_PHOTO_CAPTURE)
            }
        }


        // click event for 'Choose a photo' button
        btnChoosePhoto.setOnClickListener {
            // perform all of the following functions when the 'Choose a photo' button is clicked
            if (imgviewPropertyPhoto!!.drawable != null) {
                // Show dialog if image exists in image view
                val adbR = AlertDialog.Builder(this@MainActivity)
                adbR.setTitle(getString(R.string.choose_a_photo)) // Title of alert dialog
                adbR.setIcon(android.R.drawable.ic_dialog_alert) // Icon of alert dialog (warning sign)
                adbR.setCancelable(false) // Prevent user from tapping outside dialog to cancel
                adbR.setMessage(getString(R.string.replacephoto_warning)) // Message of alert dialog

                adbR.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    //                            imgviewPropertyPhoto.setVisibility(View.GONE); // Remove image from 'Property Photo' ImageView

                    // Launch image chooser
                    val choosephotoIntent = Intent()
                    choosephotoIntent.type = "image/*"
                    choosephotoIntent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(choosephotoIntent, getString(R.string.select_a_photo)), REQUEST_PHOTO_PICKER)
                }

                adbR.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss() // Dismiss the dialog
                }

                adbR.show() // Show the alert dialog
            } else {
                val choosephotoIntent = Intent()
                choosephotoIntent.type = "image/*"
                choosephotoIntent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(choosephotoIntent, getString(R.string.select_a_photo)), REQUEST_PHOTO_PICKER)
            }
        }


        // click event for 'Remove photo' button
        val btnRemovePhoto = this.findViewById<Button>(R.id.btn_removephoto)
        btnRemovePhoto.setOnClickListener {
            // perform all of the following functions when the 'Remove photo' button is clicked
            if (imgviewPropertyPhoto!!.drawable != null) {
                // Show dialog if image exists in image view
                val adbD = AlertDialog.Builder(this@MainActivity)
                adbD.setTitle(getString(R.string.remove_photo)) // Title of alert dialog
                adbD.setIcon(android.R.drawable.ic_dialog_alert) // Icon of alert dialog (warning sign)
                adbD.setCancelable(false) // Prevent user from tapping outside dialog to cancel
                adbD.setMessage(getString(R.string.removephoto_warning)) // Message of alert dialog

                adbD.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    // Show toast stating that Image was successfully deleted
                    Toast.makeText(this@MainActivity, R.string.image_removed_successfully, Toast.LENGTH_LONG).show() // show a toast

                    imgviewPropertyPhoto!!.visibility = View.GONE // Remove image from 'Property Photo' ImageView
                }

                adbD.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss() // Dismiss the dialog
                }

                adbD.show() //Show the alert dialog
            } else {
                // If image doesn't exist in image view, show toast stating that Image doesn´t exist
                Toast.makeText(this@MainActivity, R.string.image_doesnt_exist, Toast.LENGTH_SHORT).show() // show a toast
            }
        }

        // click event for 'Record a video' button
        btnRecordVideo.setOnClickListener {
            // perform all of the following functions when the 'Record a video' button is clicked
            // check if video exists in the VideoView
            if (videoviewPropertyVideo!!.visibility == View.VISIBLE) {
                // Show dialog if video exists in VideoView
                val adbD = AlertDialog.Builder(this@MainActivity)
                adbD.setTitle(getString(R.string.record_a_video)) // Title of alert dialog
                adbD.setIcon(android.R.drawable.ic_dialog_alert) // Icon of alert dialog (warning sign)
                adbD.setCancelable(false) // Prevent user from tapping outside dialog to cancel
                adbD.setMessage(getString(R.string.replacevideo_warning)) // Message of alert dialog

                adbD.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    // If OK button is clicked, launch video capture intent
                    dispatchTakeVideoIntent()
                }

                adbD.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss() // Dismiss the dialog
                }

                adbD.show() // Show the alert dialog
            } else {
                // If video doesn't exist in VideoView, launch video capture intent
                dispatchTakeVideoIntent()
            }
        }


        // click event for 'Choose a video' button
        btnChooseVideo.setOnClickListener {
            // perform all of the following functions when the 'Choose a photo' button is clicked
            // check if video exists in the VideoView
            if (videoviewPropertyVideo!!.visibility == View.VISIBLE) {
                // Show dialog if video exists in VideoView
                val adbD = AlertDialog.Builder(this@MainActivity)
                adbD.setTitle(getString(R.string.choose_a_video)) //Title of alert dialog
                adbD.setIcon(android.R.drawable.ic_dialog_alert) // Icon of alert dialog (warning sign)
                adbD.setCancelable(false) // Prevent user from tapping outside dialog to cancel
                adbD.setMessage(getString(R.string.replacevideo_warning)) // Message of alert dialog

                adbD.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    // If OK button is clicked, launch video capture intent
                    val choosevideoIntent = Intent()
                    choosevideoIntent.type = "video/*"
                    choosevideoIntent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(choosevideoIntent, getString(R.string.select_a_video)), REQUEST_VIDEO_PICKER)
                }

                adbD.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss() // Dismiss the dialog
                }

                adbD.show() // Show the alert dialog
            } else {
                // If video doesn't exist in VideoView, launch video picker intent
                val choosevideoIntent = Intent()
                choosevideoIntent.type = "video/*"
                choosevideoIntent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(choosevideoIntent, getString(R.string.select_a_video)), REQUEST_VIDEO_PICKER)
            }
        }


        // click event for 'Remove video' button
        btnRemovePropertyVideo.setOnClickListener {
            // perform all of the following functions when the 'Remove video' button is clicked
            // check if video exists in the VideoView
            if (videoviewPropertyVideo!!.visibility == View.VISIBLE) {
                // Show dialog if video exists in VideoView
                val adbD = AlertDialog.Builder(this@MainActivity)
                adbD.setTitle(getString(R.string.remove_video)) // Title of alert dialog
                adbD.setIcon(android.R.drawable.ic_dialog_alert) // Icon of alert dialog (warning sign)
                adbD.setCancelable(false) // Prevent user from tapping outside dialog to cancel
                adbD.setMessage(getString(R.string.removevideo_warning)) // Message of alert dialog

                adbD.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    // Show toast stating that Video was successfully deleted
                    Toast.makeText(this@MainActivity, R.string.video_removed_successfully, Toast.LENGTH_LONG).show() // show a toast

                    videoviewPropertyVideo!!.visibility = View.GONE //Remove video from 'Property Video' ImageView
                }

                adbD.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    dialog.dismiss() // Dismiss the dialog
                }

                adbD.show() //Show the alert dialog
            } else {
                // If text doesn't exist in image view, show toast stating that video doesn´t exist
                Toast.makeText(this@MainActivity, R.string.video_doesnt_exist, Toast.LENGTH_SHORT).show() // show a toast
            }
        }


        // click event for 'Add nearest station' button
        btnAddNearestStation.setOnClickListener { it ->
            // perform all of the following functions when the 'Add a station' button is clicked
            val layoutInflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val addView = layoutInflater.inflate(R.layout.nearests_row, null)
            val textOut = addView.findViewById<TextView>(R.id.textView_nearestS)
            textOut.text = edittextNearestStation!!.text.toString()
            val buttonRemove = addView.findViewById<Button>(R.id.btn_removeS)
            buttonRemove.setOnClickListener { (addView.parent as LinearLayout).removeView(addView) }

            containerNearestStations!!.addView(addView, 0)

            edittextNearestStation!!.text = null // clear text from the 'edittextNearestStation' EditText field
        }


        // click event for 'Add nearest school' button
        btnAddNearestSchool.setOnClickListener { it ->
            // perform all of the following functions when the 'Add a station' button is clicked
            val layoutInflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val addView = layoutInflater.inflate(R.layout.nearests_row, null)
            val textOut = addView.findViewById<TextView>(R.id.textView_nearestS)
            textOut.text = edittextNearestSchool!!.text.toString()

            val buttonRemove = addView.findViewById<Button>(R.id.btn_removeS)
            buttonRemove.setOnClickListener { (addView.parent as LinearLayout).removeView(addView) }

            containerNearestSchools!!.addView(addView, 0)

            edittextNearestSchool!!.text = null // clear text from the 'edittextNearestSchool' EditText field
        }


        // click event for 'submit' button
        val buttonSubmit = this.findViewById<Button>(R.id.btn_submit)
        buttonSubmit.setOnClickListener {
            // perform all of the following functions when the 'submit' button is clicked

            hideKeyboard() // hide the on-screen keyboard, because the Android system doesn't do this automatically

            // 'Property type' Spinner decision making
            if (spinnerPropertyType!!.selectedItemPosition == 0) { // if the selected item of 'spinnerPropertytype' spinner is 'choose bedroom quantity...',
                textviewErrorPropertyType!!.visibility = View.VISIBLE // ...make the 'textviewErrorPropertyType' text view visible
            } else {
                textviewErrorPropertyType!!.visibility = View.INVISIBLE // otherwise, make the 'textviewErrorPropertyType' text view invisible
            }

            // 'Bedrooms' SeekBar decision making
            if (seekbarBedrooms!!.progress == 0) { // if the progress of 'seekbarBedrooms' SeekBar is '0',
                textviewErrorBedrooms!!.visibility = View.VISIBLE // make the 'textviewErrorBedrooms' text view visible
            } else {
                textviewErrorBedrooms!!.visibility = View.INVISIBLE // otherwise, make the 'textviewErrorBedrooms' text view invisible
            }

            // 'Property addition date' EditText decision making
            if (textviewPropertyAdditionDate!!.text.length == 0) { // if the 'textviewPropertyAdditionDate' EditText field is empty (contains 0 characters),
                textviewErrorPropertyAdditionDate!!.visibility = View.VISIBLE // make the 'textviewErrorPropertyAdditionDate' text view visible
            } else {
                textviewErrorPropertyAdditionDate!!.visibility = View.INVISIBLE // otherwise, make the 'textviewErrorPropertyAdditionDate' text view invisible
            }

            // 'Property addition time' EditText decision making
            if (textviewPropertyAdditionTime!!.text.length == 0) { // if the 'textviewPropertyAdditionTime' EditText field is empty (contains 0 characters),
                textviewErrorPropertyAdditionTime!!.visibility = View.VISIBLE // make the 'textviewErrorPropertyAdditionTime' text view visible
            } else {
                textviewErrorPropertyAdditionTime!!.visibility = View.INVISIBLE // otherwise, make the 'textviewErrorPropertyAdditionTime' text view invisible
            }

            // 'Monthly rent' EditText decision making
            if (edittextMonthlyRent!!.text.length == 0) { // if the 'edittextMonthlyRent' EditText field is empty (contains 0 characters),
                textviewErrorMonthlyRent!!.visibility = View.VISIBLE // make the 'textviewErrorMonthlyRent' text view visible
            } else {
                textviewErrorMonthlyRent!!.visibility = View.INVISIBLE // otherwise, make the 'textviewErrorMonthlyRent' text view invisible
            }

            // 'Reporter name' EditText decision making
            if (edittextReporterName!!.text.length < 2) { // if the 'edittextReporterName' EditText field has less than 2 characters,
                textviewErrorReporterName!!.visibility = View.VISIBLE // make the 'textviewErrorReporterName' text view visible
            } else {
                textviewErrorReporterName!!.visibility = View.INVISIBLE // otherwise, make the 'textviewErrorReporterName' text view invisible
            }

            // 'Terms and Conditions' CheckBox decision making
            if (!checkboxTermsAndConditions!!.isChecked) { // if the 'Terms and Conditions' CheckBox is not checked
                Toast.makeText(this@MainActivity, R.string.you_must_agree_to_termsandconditions, Toast.LENGTH_LONG).show() // show a toast
            } else {
                showalerdialogDetailsEntered()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance() //new Calendar instance
        c.set(Calendar.YEAR, year) //show current year of device (data passed by 'int year' variable)
        c.set(Calendar.MONTH, month) //show current month of device (data passed by 'int month' variable)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth) //show current day (of the month) of device (data passed by 'int dayOfMonth' variable)
        val currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(c.time) // use the short date format when displaying the date

        textviewPropertyAdditionDate!!.setText(currentDateString) // set text of 'textviewPropertyAdditionDate' EditText to the 'currentDateString' variable
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        textviewPropertyAdditionTime!!.setText(String.format("%02d:%02d", hourOfDay, minute)) // set text of 'textviewPropertyAdditionDate' EditText to the 'currentDateString' variable
        // '%02d:%02d' prevents any zeros from being omitted upon time selection
    }

    private fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    //show user-defined details from all field in AlertDialog
    private fun showalerdialogDetailsEntered() {
        val adbDE = AlertDialog.Builder(this)
        adbDE.setTitle(getString(R.string.details_entered)) // Title of alert dialog
        adbDE.setIcon(android.R.drawable.ic_dialog_info) // Icon of alert dialog (information sign)
        adbDE.setCancelable(false) // Prevent user from tapping outside dialog to cancel
        adbDE.setMessage(getString(R.string.property_type_) + "\u0020" + spinnerPropertyType!!.selectedItem.toString() + "\n\n" +
                getString(R.string.bedrooms_) + "\u0020" + textviewSeekbarBedrooms!!.text + "\n\n" +
                getString(R.string.property_addition_date_) + "\u0020" + textviewPropertyAdditionDate!!.text + "\n\n" +
                getString(R.string.property_addition_time_) + "\u0020" + textviewPropertyAdditionTime!!.text + "\n\n" +
                getString(R.string.monthly_rent_) + "\u0020" + edittextMonthlyRent!!.text + "\n\n" +
                getString(R.string.furniture_type_) + "\u0020" + spinnerFurnitureType!!.selectedItem.toString() + "\n\n" +
                getString(R.string.property_description_) + "\u0020" + edittextDescription!!.text + "\n\n" +
                getString(R.string.reporter_name_) + "\u0020" + edittextReporterName!!.text
        ) // Message of alert dialog


        adbDE.setNeutralButton(getString(R.string.ok)) { dialog, which ->
            dialog.dismiss() // Dismiss the dialog
        }

        adbDE.show() // Show the alert dialog
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    private fun dispatchTakeVideoIntent() {
        val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (takeVideoIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // 'super.onActivityResult' isn't necessary unless using a FragmentActivity

        // check to see if the result is from Camera intent | ensure the Camera request was successful
        if (requestCode == REQUEST_PHOTO_CAPTURE && resultCode == Activity.RESULT_OK) {
            //hearing back from the camera
            val extras = data!!.extras
            val bitmap = extras!!.get("data") as Bitmap

            imgviewPropertyPhoto!!.visibility = View.VISIBLE

            //at this point, image has been obtained from the camera
            imgviewPropertyPhoto!!.setImageBitmap(bitmap)
        }

        // check to see if the result is from Gallery intent | ensure the Gallery request was successful
        if (requestCode == REQUEST_PHOTO_PICKER && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

                imgviewPropertyPhoto!!.visibility = View.VISIBLE

                imgviewPropertyPhoto!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        // check to see if the result is from Camera intent | ensure the video recording request was successful
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == Activity.RESULT_OK) {
            val videoUri = data!!.data
            videoviewPropertyVideo!!.setVideoURI(videoUri)

            videoviewPropertyVideo!!.visibility = View.VISIBLE

            videoviewPropertyVideo!!.start()
        }

        // check to see if the result is from Gallery intent | ensure the Gallery request was successful
        if (requestCode == REQUEST_VIDEO_PICKER && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val videoUri = data.data
            videoviewPropertyVideo!!.setVideoURI(videoUri)

            videoviewPropertyVideo!!.visibility = View.VISIBLE

            videoviewPropertyVideo!!.start()
        }
    }

    companion object {

        /* the name of a 'static final' variables should always be in uppercase (according to Java coding convention) + value cannot be altered after it is first initialised */
        /* 'static' variables stay in the memory for the whole lifetime of an app, and is initialised during the loading of a class */
        /* each integer value represents a request code (each is unique), which is used to identify the particular Intent that the user came back from */
        private val REQUEST_PHOTO_CAPTURE = 1 // private static final variable named 'REQUEST_PHOTO_CAPTURE' assigned with the value of 1
        private val REQUEST_PHOTO_PICKER = 2 // private static final variable named 'REQUEST_PHOTO_PICKER' assigned with the value of 2
        private val REQUEST_VIDEO_CAPTURE = 3 // private static final variable named 'REQUEST_VIDEO_CAPTURE' assigned with the value of 3
        private val REQUEST_VIDEO_PICKER = 4 // private static final variable named 'REQUEST_VIDEO_PICKER' assigned with the value of 4
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // Return true so Android will know that we want to display the menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_reset -> {
                // User chose the "Reset" item, show confirm dialog ...

                val adb = AlertDialog.Builder(this)
                adb.setTitle(getString(R.string.reset_form)) // Title of alert dialog
                adb.setIcon(android.R.drawable.ic_dialog_alert) // Icon of alert dialog (warning sign)
                adb.setCancelable(false) // Prevent user from tapping outside dialog to cancel
                adb.setMessage(getString(R.string.reset_warning)) // Message of alert dialog

                adb.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    Toast.makeText(this@MainActivity, R.string.all_entered_information_cleared_and_reset_successfully, Toast.LENGTH_LONG).show() // show a toast

                    spinnerPropertyType!!.setSelection(0) // set 'Property Type' Spinner position to 0
                    seekbarBedrooms!!.progress = 0 // set progress of 'Bedrooms' SeekBar to 0
                    seekbarBathrooms!!.progress = 0 // set progress of 'Bathrooms' SeekBar to 0
                    textviewPropertyAdditionDate!!.setText("") // Clear text from 'Property Addition Date' TextView field
                    textviewPropertyAdditionTime!!.setText("") // Clear text from 'Property Addition Time' TextView field
                    edittextMonthlyRent!!.setText("") // Clear text from 'Monthly Rent' EditText field
                    spinnerFurnitureType!!.setSelection(0) // set 'Furniture Type' Spinner position to 0
                    edittextDescription!!.setText("") // Clear text from 'Notes' EditText field
                    edittextReporterName!!.setText("") // Clear text from 'Reporter Name' EditText field
                    radiogroupPetsAllowed!!.clearCheck() // Clear selection from 'Pets allowed' RadioGroup
                    radiogroupWheelchairAccessible!!.clearCheck() // Clear selection from 'Wheelchair accessible' RadioGroup
                    seekbarEnergyEfficiencyRating!!.progress = 0 // set 'Energy Efficiency Rating' SeekBar position to 0
                    seekbarEnvironmentalImpactRating!!.progress = 0 // set progress of 'Environmental Impact Rating' SeekBar to 0
                    imgviewPropertyPhoto!!.setImageDrawable(null) // Remove image from 'Property Photo' ImageView
                    edittextNearestStation!!.setText("") // Clear text from 'Nearest Stations' EditText field
                    edittextNearestSchool!!.setText("") // Clear text from 'Nearest Schools' EditText field

                    // if the 'Terms and Conditions' CheckBox is checked, uncheck it
                    if (checkboxTermsAndConditions!!.isChecked) {
                        checkboxTermsAndConditions!!.isChecked = false
                    }

                    textviewErrorPropertyType!!.visibility = View.INVISIBLE // make 'textViewerror_propertytype' text view INVISIBLE (not GONE) to keep space between items
                    textviewErrorBedrooms!!.visibility = View.INVISIBLE // make 'textViewerror_bedrooms' text view INVISIBLE (not GONE) to keep space between items
                    textviewErrorPropertyAdditionDate!!.visibility = View.INVISIBLE // make 'textViewerror_propertytype' text view INVISIBLE (not GONE) to keep space between items
                    textviewErrorPropertyAdditionTime!!.visibility = View.INVISIBLE // make 'textviewErrorPropertyAdditionTime' text view INVISIBLE (not GONE) to keep space between items
                    textviewErrorMonthlyRent!!.visibility = View.INVISIBLE // make 'textviewErrorMonthlyRent' text view INVISIBLE (not GONE) to keep space between items
                    textviewErrorReporterName!!.visibility = View.INVISIBLE // make 'textviewErrorReporterName' text view INVISIBLE (not GONE) to keep space between items
                }

                adb.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    Toast.makeText(this@MainActivity, R.string.all_entered_information_has_not_been_cleared_nor_reset, Toast.LENGTH_LONG).show() // show a toast
                    dialog.dismiss() // Dismiss the dialog
                }

                adb.show() // Show the alert dialog

                return true
            }

            else ->
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
        }
    }
}

