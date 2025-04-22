package com.example.komuter_tiket

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var etNik: EditText
    private lateinit var etNama: EditText
    private lateinit var etUsia: EditText
    private lateinit var etPhone: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var spinnerKereta: Spinner
    private lateinit var etHarga: EditText
    private lateinit var etDateTime: EditText
    private lateinit var btnPesan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mengatur status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        etNik = findViewById(R.id.et_nik)
        etNama = findViewById(R.id.et_nama)
        etUsia = findViewById(R.id.et_usia)
        etPhone = findViewById(R.id.et_phone)
        rgGender = findViewById(R.id.rg_gender)
        spinnerKereta = findViewById(R.id.spinner_kereta)
        etHarga = findViewById(R.id.et_harga)
        etDateTime = findViewById(R.id.et_date_time)
        btnPesan = findViewById(R.id.btn_pesan)

        // Mengatur spinner dengan data kereta dan harga
        val keretaList = arrayOf("Pilih Kereta","KOMUTER BANDUNG", "KOMUTER GARUT", "KOMUTER PURWAKARTA")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, keretaList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKereta.adapter = adapter

        // Listener untuk spinner
        spinnerKereta.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                // Mengambil harga dari pilihan spinner
                val selectedKereta = keretaList[position]
                val harga = when (selectedKereta) {
                    "KOMUTER BANDUNG" -> "2000"
                    "KOMUTER GARUT" -> "4000"
                    "KOMUTER PURWAKARTA" -> "8000"
                    else -> ""
                }
                etHarga.setText(harga) // Update EditText HARGA
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                etHarga.setText("") // Kosongkan jika tidak ada yang dipilih
            }
        })

        // Mengatur tanggal dan waktu
        etDateTime.setOnClickListener {
            showDateTimePicker()
        }

        // Menangani tombol pemesanan
        btnPesan.setOnClickListener {
            handlePesan()
        }
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val timePickerDialog = TimePickerDialog(this, { _, hourOfDay, minute ->
                val selectedDateTime = "$dayOfMonth/${month + 1}/$year $hourOfDay:$minute"
                etDateTime.setText(selectedDateTime)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            timePickerDialog.show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        datePickerDialog.show()
    }

    private fun handlePesan() {
        val nik = etNik.text.toString()
        val nama = etNama.text.toString()
        val usia = etUsia.text.toString()
        val phone = etPhone.text.toString()
        val genderId = rgGender.checkedRadioButtonId
        val gender = findViewById<RadioButton>(genderId)?.text.toString()
        val kereta = spinnerKereta.selectedItem.toString()
        val harga = etHarga.text.toString() // Ambil harga dari EditText HARGA
        val dateTime = etDateTime.text.toString()

        // Validasi input
        if (nik.isEmpty() || nama.isEmpty() || usia.isEmpty() || phone.isEmpty() || dateTime.isEmpty()) {
            Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            return
        }

        // Menampilkan informasi pemesanan
        val pesan = "Tiket kereta telah dipesan:\n" +
                "NIK: $nik\n" +
                "Nama: $nama\n" +
                "Usia: $usia\n" +
                "Nomor Telepon: $phone\n" +
                "Gender: $gender\n" +
                "Kereta: $kereta\n" +
                "Harga: $harga\n" +
                "Tanggal dan Waktu: $dateTime"

        Toast.makeText(this, pesan, Toast.LENGTH_LONG).show()
    }
}